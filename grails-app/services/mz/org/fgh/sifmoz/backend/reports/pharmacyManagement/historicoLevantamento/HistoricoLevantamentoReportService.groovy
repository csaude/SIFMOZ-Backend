package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.historicoLevantamento

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.packaging.Pack

import java.text.SimpleDateFormat

@Transactional
@Service(HistoricoLevantamentoReport)
abstract class HistoricoLevantamentoReportService implements IHistoricoLevantamentoReportService {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")

    @Override
    HistoricoLevantamentoReport get(Serializable id) {
        return HistoricoLevantamentoReport.findById(id as String)
    }

    @Override
    List<HistoricoLevantamentoReport> list(Map args) {
        return null
    }

    @Override
    Long count() {
        return null
    }

    @Override
    HistoricoLevantamentoReport delete(Serializable id) {
        return null
    }

    @Override
    void doSave(List<HistoricoLevantamentoReport> histryList) {
        System.out.println(histryList.size())
        for (histry in histryList) {
            save(histry)
        }
    }

    @Override
    List<HistoricoLevantamentoReport> getReportDataByReportId(String reportId) {
        return HistoricoLevantamentoReport.findAllByReportId(reportId)
    }

    void generateAndSaveHistry(List item, ReportSearchParams reportSearchParams){
        HistoricoLevantamentoReport historicoLevantamentoReport = new HistoricoLevantamentoReport(
                item[0].toString(),
                item[1].toString(),
                item[2].toString(),
                item[3].toString(),
                item[4].toString(),
                item[5].toString(),
                item[6].toString(),
                item[7].toString(),
                item[8].toString(),
                item[9].toString(),
                item[10].toString()
        )
        // set pickUpDate
        if (item[11] != null){
            Date pickUpDate = formatter.parse(item[11].toString())
            historicoLevantamentoReport.setPickUpDate(pickUpDate)
        }
        // set nextPickUpDate
        if (item[12] != null){
            Date nextPickUpDate = formatter.parse(item[12].toString())
            historicoLevantamentoReport.setNexPickUpDate(nextPickUpDate)
        }

        historicoLevantamentoReport.setReportId(reportSearchParams.getId())
        historicoLevantamentoReport.setClinicId(reportSearchParams.getClinicId())
        historicoLevantamentoReport.setPeriodType(reportSearchParams.getPeriodType())
        historicoLevantamentoReport.setPeriod(Integer.valueOf(reportSearchParams.getPeriod()))
        historicoLevantamentoReport.setYear(reportSearchParams.getYear())
        historicoLevantamentoReport.setStartDate(reportSearchParams.getStartDate())
        historicoLevantamentoReport.setEndDate(reportSearchParams.getEndDate())

        historicoLevantamentoReport.setAge("25")
        historicoLevantamentoReport.setClinic(Clinic.findById(reportSearchParams.getClinicId()).toString())

        System.out.println(historicoLevantamentoReport.toString())
        save(historicoLevantamentoReport)
    }

    @Override
    List<HistoricoLevantamentoReport> processamentoDados(ReportSearchParams reportSearchParams) {
        Date stDate = new SimpleDateFormat("dd/MM/yyyy").parse("21/05/2019")
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse("21/03/2022")
        String patientId = "402881fd7ef84863017ef9f2a2940038"
        def resultList = Pack.executeQuery(
            "SELECT DISTINCT pat.id, pat.firstNames, pat.middleNames, pat.lastNames, pat.cellphone,pd.reasonForUpdate, str.reason, pd.therapeuticRegimen.description, pd.dispenseType.description, dispMode.description, cs.code, pack.pickupDate, pack.nextPickUpDate " +
                        "FROM Pack pack " +
                        "INNER JOIN DispenseMode dispMode " +
                        "ON pack.dispenseMode.id = dispMode.id " +
                        "INNER JOIN PatientVisitDetails pvd " +
                        "ON pvd.pack.id = pack.id " +
                        "INNER JOIN Prescription pre " +
                        "ON pvd.prescription.id = pre.id " +
                        "INNER JOIN PrescriptionDetail pd " +
                        "ON pd.prescription.id = pre.id " +
                        "INNER JOIN Episode ep " +
                        "ON pvd.episode.id = ep.id " +
                        "INNER JOIN StartStopReason str " +
                        "ON str.id = ep.startStopReason.id "+
                        "INNER JOIN PatientServiceIdentifier psi " +
                        "ON psi.id = ep.patientServiceIdentifier.id " +
                        "INNER JOIN ClinicalService cs " +
                        "ON psi.service.id = cs.id and cs.code = 'TARV' " +
                        "INNER JOIN PatientVisit pv " +
                        "ON pvd.id > pv.id and pv.visitDate BETWEEN :stDate AND :endDate "+ ////////// =
                        "INNER JOIN Patient pat " +
                        "ON pv.patient.id = pat.id and pat.id = :patientId",
                [stDate: stDate, endDate: endDate, patientId: patientId]
        )
        // System.out.println(resultList.size())
        for (item in resultList) {
            generateAndSaveHistry(item as List, reportSearchParams)
            // System.out.println(item.toString())
        }
    }
}
