package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.reports.monitoringAndEvaluation.DrugQuantityTemp
import mz.org.fgh.sifmoz.backend.stock.Stock

@Transactional
@Service(PatientVisitDetails)
abstract class PatientVisitDetailsService implements IPatientVisitDetailsService {
    @Override
    List<PatientVisitDetails> getAllByClinicId(String clinicId, int offset, int max) {
        return PatientVisitDetails.findAllByClinic(Clinic.findById(clinicId), [offset: offset, max: max])
    }

    @Override
    List<PatientVisitDetails> getAllByEpisodeId(String episodeId, int offset, int max) {
        def patientVisitDetails = PatientVisitDetails.findAllByEpisode(Episode.findById(episodeId))
        return patientVisitDetails
    }

    @Override
    PatientVisitDetails getByPack(Pack pack) {
        return PatientVisitDetails.findByPack(pack)
    }

    @Override
    List<PatientVisitDetails> getARVDailyReport(String clinicId, Date startDate, Date endDate, String clincalServiceId) {
        List<PatientVisitDetails> list = Stock.executeQuery("select  pvd from PatientVisitDetails  pvd " +
                " inner join pvd.pack  p" +
                " inner join pvd.prescription pr" +
                " inner join pr.prescriptionDetails prd" +
                " inner join prd.dispenseType dt" +
                " inner join pvd.episode ep" +
                " inner join ep.startStopReason sst" +
                " inner join ep.patientServiceIdentifier psi" +
                " inner join psi.patient pat" +
                " inner join prd.therapeuticRegimen tr" +
                " inner join prd.therapeuticLine tl" +
                " where p.pickupDate BETWEEN  :startDate AND :endDate  AND " +
                " p.clinic.id =:clinicId",
                [startDate: startDate, endDate: endDate, clinicId: clinicId]);
        // AND"+
        // " psi.service.id =:clincalServiceId
        return list
    }


    List<DrugQuantityTemp> getProducts(String patientVisitDetailId, String clinicId, Date startDate, Date endDate) {
        List<DrugQuantityTemp> listDrugTemp = new ArrayList<>()

        def list = Stock.executeQuery("select  " +
                "d.name , sum(pd.quantitySupplied)  from PatientVisitDetails pvd " +
                "inner join pvd.pack p  " +
                "inner join p.packagedDrugs pd  " +
                "inner join pd.drug d  " +
                " where pvd.id =:patientVisitDetailId And " +
                " p.pickupDate BETWEEN  :startDate AND :endDate  And " +
                " p.clinic.id =:clinicId " +
                " group by d.name "
                , [patientVisitDetailId: patientVisitDetailId, startDate: startDate, endDate: endDate, clinicId: clinicId])
        list.each { item ->
            def drugName = item[0]
            def quantity = item[1]
            listDrugTemp.add(new DrugQuantityTemp(String.valueOf(drugName), Long.valueOf(quantity)))
        }
        return listDrugTemp
    }


}
