package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.historicoLevantamento

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.reports.common.IReportProcessMonitorService
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor
import mz.org.fgh.sifmoz.backend.reports.patients.ActivePatientReport
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import java.text.SimpleDateFormat

@Transactional
@Service(HistoricoLevantamentoReport)
abstract class HistoricoLevantamentoReportService implements IHistoricoLevantamentoReportService {

    @Autowired
    IReportProcessMonitorService reportProcessMonitorService

    @Autowired
    SessionFactory sessionFactory

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
    List<HistoricoLevantamentoReport> processamentoDados(ReportSearchParams reportSearchParams, ReportProcessMonitor processMonitor) {
        Clinic clinic = Clinic.findById(reportSearchParams.clinicId)
        String clinicalService = ClinicalService.findById(reportSearchParams.clinicalService).code
        def queryString =
                "SELECT    " +
                        "    pat.id,    " +
                        "    pat.first_names,    " +
                        "    pat.middle_names,    " +
                        "    pat.last_names,    " +
                        "    pat.cellphone,    " +
                        "    pd.reason_for_update,    " +
                        "    str.reason,    " +
                        "    tr.description as regime_terapeutico,    " +
                        "    dt.description as redispense_type,    " +
                        "    dispMode.description,    " +
                        "    cs.code,    " +
                        "    pack.pickup_date,    " +
                        "    pack.next_pick_up_date,    " +
                        "    pat.date_of_birth,    " +
                        "    psi.prefered,    " +
                        "    psi.value,    " +
                        "    idt.id as prefered_idt,    " +
                        "    pre.patient_type    " +
                        "   FROM patient pat    " +
                        "   INNER JOIN patient_service_identifier psi ON psi.patient_id = pat.id    " +
                        "   INNER JOIN clinical_service cs ON psi.service_id = cs.id and cs.code = :serv_clinico   " +
                        "   INNER JOIN identifier_type idt ON psi.identifier_type_id = idt.id    " +
                        "   inner join episode ep on ep.patient_service_identifier_id = psi.id    " +
                        "   INNER JOIN start_stop_reason str ON ep.start_stop_reason_id =  str.id    " +
                        "   INNER JOIN ( SELECT patient_id, max(visit_date) as visit_date    " +
                        "   from patient_visit    " +
                        "   Group by 1 ) as pvAux on pvAux.patient_id = pat.id    " +
                        "   INNER JOIN patient_visit pv on pv.visit_date = pvAux.visit_date and pv.patient_id = pvAux.patient_id    " +
                        "   INNER JOIN patient_visit_details pvd  ON pvd.episode_id = ep.id and pvd.patient_visit_id = pv.id    " +
                        "   INNER JOIN pack on pvd.pack_id = pack.id    " +
                        "   INNER JOIN dispense_mode dispMode ON pack.dispense_mode_id = dispMode.id    " +
                        "   INNER JOIN prescription pre  ON pvd.prescription_id = pre.id    " +
                        "   INNER JOIN prescription_detail pd ON pd.prescription_id = pre.id    " +
                        "   INNER JOIN therapeutic_regimen tr ON pd.therapeutic_regimen_id = tr.id    " +
                        "   INNER JOIN dispense_type dt ON pd.dispense_type_id = dt.id    " +
                        "   where Date(pack.pickup_date) BETWEEN :stDate AND :endDate"


        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("stDate", reportSearchParams.startDate)
        query.setParameter("endDate", reportSearchParams.endDate)
        query.setParameter("serv_clinico", clinicalService)
        List<Object[]> result = query.list()

        if (Utilities.listHasElements(result as ArrayList<?>)) {
            double percUnit = 100 / result.size()

            for (item in result) {
                HistoricoLevantamentoReport historicoLevantamentoReport = setGenericInfo(reportSearchParams, clinic, item[13] as Date)
                processMonitor.setProgress(processMonitor.getProgress() + percUnit)
                reportProcessMonitorService.save(processMonitor)
                generateAndSaveHistory(item as List, reportSearchParams, historicoLevantamentoReport)
            }

            processMonitor.setProgress(100)
            processMonitor.setMsg("Processamento terminado")
            reportProcessMonitorService.save(processMonitor)

            return result
        } else {
            processMonitor.setProgress(100)
            processMonitor.setMsg("Processamento terminado")
            reportProcessMonitorService.save(processMonitor)
            return new ArrayList<ActivePatientReport>()
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
        def province = Province.findById(clinic.province.id.toString())
        province == null? historicoLevantamentoReport.setProvince(" "):historicoLevantamentoReport.setProvince(province.description)
        historicoLevantamentoReport.setProvince(province.description)
        def district = District.findById(clinic.district)
        district == null? historicoLevantamentoReport.setDistrict(""):historicoLevantamentoReport.setDistrict(district.description)
        return historicoLevantamentoReport
    }

    void generateAndSaveHistory(List item, ReportSearchParams reportSearchParams, HistoricoLevantamentoReport historicoLevantamentoReport) {

        item[1] == null? historicoLevantamentoReport.setFirstNames("") : historicoLevantamentoReport.setFirstNames(item[1].toString())
        item[2] == null? historicoLevantamentoReport.setMiddleNames("") : historicoLevantamentoReport.setMiddleNames(item[2].toString())
        item[3] == null? historicoLevantamentoReport.setLastNames("") : historicoLevantamentoReport.setLastNames(item[3].toString())
        item[4] == null? historicoLevantamentoReport.setCellphone(" ") : historicoLevantamentoReport.setCellphone(item[4].toString())
        item[5] == null? historicoLevantamentoReport.setTipoTarv(" ") : historicoLevantamentoReport.setTipoTarv(item[5].toString())
        item[6] == null? historicoLevantamentoReport.setStartReason("") : historicoLevantamentoReport.setStartReason(item[6].toString())
        item[7] == null? historicoLevantamentoReport.setTherapeuticalRegimen("") : historicoLevantamentoReport.setTherapeuticalRegimen(item[7].toString())
        item[8] == null? historicoLevantamentoReport.setDispenseType("") : historicoLevantamentoReport.setDispenseType(item[8].toString())
        item[9] == null? historicoLevantamentoReport.setDispenseMode("") : historicoLevantamentoReport.setDispenseMode(item[9].toString())
        item[10] == null? historicoLevantamentoReport.setClinicalService(" ") : historicoLevantamentoReport.setClinicalService(item[10].toString())
        item[17] == null? historicoLevantamentoReport.setPatientType(" ") : historicoLevantamentoReport.setPatientType(item[17].toString())

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
