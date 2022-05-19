package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.historicoLevantamento

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
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
    List<HistoricoLevantamentoReport> getReportDataByReportId(String reportId) {
        return HistoricoLevantamentoReport.findAllByReportId(reportId)
    }

    @Override
    List<HistoricoLevantamentoReport> processamentoDados(ReportSearchParams reportSearchParams) {
        Clinic clinic = Clinic.findById(reportSearchParams.clinicId)

        def resultList = Pack.executeQuery(
                "SELECT DISTINCT pat.id, pat.firstNames, pat.middleNames, pat.lastNames, pat.cellphone,pd.reasonForUpdate, str.reason, pd.therapeuticRegimen.description, pd.dispenseType.description, dispMode.description, cs.code, pack.pickupDate, pack.nextPickUpDate, pat.dateOfBirth, psi.prefered, psi.value, idt.id as prefered " +
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
                        "ON str.id = ep.startStopReason.id " +
                        "INNER JOIN PatientServiceIdentifier psi " +
                        "ON psi.id = ep.patientServiceIdentifier.id " +
                        "INNER JOIN IdentifierType idt " +
                        "ON psi.identifierType.id = idt.id " +
                        "INNER JOIN ClinicalService cs " +
                        "ON psi.service.id = cs.id and cs.code = 'TARV' " +
                        "INNER JOIN PatientVisit pv " +
                        "ON pvd.id = pv.id and pv.visitDate BETWEEN :stDate AND :endDate " +
                        "INNER JOIN Patient pat " +
                        "ON pv.patient.id = pat.id",
                [stDate: reportSearchParams.getStartDate(), endDate: reportSearchParams.getEndDate()]
        )
        for (item in resultList) {
            HistoricoLevantamentoReport historicoLevantamentoReport = setGenericInfo(reportSearchParams, clinic, item[13] as Date)
            generateAndSaveHistry(item as List, reportSearchParams, historicoLevantamentoReport)
        }

    }

    private HistoricoLevantamentoReport setGenericInfo(ReportSearchParams searchParams, Clinic clinic, Date dateOfBirth) {
        HistoricoLevantamentoReport historicoLevantamentoReport = new HistoricoLevantamentoReport()
        historicoLevantamentoReport.setClinic(clinic.getClinicName())
        historicoLevantamentoReport.setStartDate(searchParams.startDate)
        historicoLevantamentoReport.setEndDate(searchParams.endDate)
        historicoLevantamentoReport.setReportId(searchParams.id)
        historicoLevantamentoReport.setPeriodType(searchParams.periodType)
        historicoLevantamentoReport.setReportId(searchParams.id)
        historicoLevantamentoReport.setYear(searchParams.year)
        historicoLevantamentoReport.setAge(ConvertDateUtils.getAge(dateOfBirth).intValue() as String)
        historicoLevantamentoReport.setProvince(Province.findById(clinic.province.id.toString()).description)

        //Provisorio
        List<District> dists = District.findAll()
        historicoLevantamentoReport.setDistrict(District.findById(dists[0].id).description)
        return historicoLevantamentoReport
    }

    void generateAndSaveHistry(List item, ReportSearchParams reportSearchParams, HistoricoLevantamentoReport historicoLevantamentoReport) {

        historicoLevantamentoReport.setFirstNames(item[1].toString())
        historicoLevantamentoReport.setMiddleNames(item[2].toString())
        historicoLevantamentoReport.setLastNames(item[3].toString())
        historicoLevantamentoReport.setCellphone(item[4].toString())
        historicoLevantamentoReport.setTipoTarv(item[5].toString())
        historicoLevantamentoReport.setStartReason(item[6].toString())
        historicoLevantamentoReport.setTherapeuticalRegimen(item[7].toString())
        historicoLevantamentoReport.setDispenseType(item[8].toString())
        historicoLevantamentoReport.setDispenseMode(item[9].toString())
        historicoLevantamentoReport.setClinicalService(item[10].toString())

        // Setting nid
        if (item[14] != null) {
            if (item[14]) {
                historicoLevantamentoReport.setNid(item[15])
            } else {
                historicoLevantamentoReport.setNid(item[16])
            }
        }

        // set pickUpDate
        if (item[11] != null) {
            Date pickUpDate = formatter.parse(item[11].toString())
            historicoLevantamentoReport.setPickUpDate(pickUpDate)
        }
        // set nextPickUpDate
        if (item[12] != null) {
            Date nextPickUpDate = formatter.parse(item[12].toString())
            historicoLevantamentoReport.setNexPickUpDate(nextPickUpDate)
        }
        save(historicoLevantamentoReport)
    }
}
