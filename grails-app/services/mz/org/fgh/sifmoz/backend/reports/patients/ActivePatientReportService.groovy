package mz.org.fgh.sifmoz.backend.reports.patients

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.reports.common.IReportProcessMonitorService
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import java.text.SimpleDateFormat

@Transactional
@Service(ActivePatientReport)
abstract class ActivePatientReportService implements IActivePatientReportService {
    @Autowired
    IReportProcessMonitorService reportProcessMonitorService

    @Autowired
    SessionFactory sessionFactory

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")

    @Override
    ActivePatientReport get(Serializable id) {
        return ActivePatientReport.findById(id as String)
    }

    @Override
    List<ActivePatientReport> list(Map args) {
        return null
    }

    @Override
    Long count() {
        return null
    }

    @Override
    ActivePatientReport delete(Serializable id) {
        return null
    }

    @Override
    void doSave(List<ActivePatientReport> patients) {
        for (patient in patients) {
            save(patient)
        }
    }

    @Override
    List<ActivePatientReport> getReportDataByReportId(String reportId) {
        return ActivePatientReport.findAllByReportId(reportId)
    }

    @Override
    List<ActivePatientReport> processamentoDados(ReportSearchParams reportSearchParams, ReportProcessMonitor processMonitor) {

        List<ActivePatientReport> resultList = new ArrayList<>()
//
        String reportId = reportSearchParams.getId()
        //---------------
        String clinicalService = ClinicalService.findById(reportSearchParams.clinicalService).code
        Clinic clinic = Clinic.findById(reportSearchParams.clinicId)
        String province_id = clinic.province.id

        //---------------

        def queryString =
                "SELECT pat.first_names, " +
                        "pat.middle_names, " +
                        "pat.last_names, " +
                        "pat.gender, " +
                        "pat.cellphone, " +
                        "psi.prefered, " +
                        "psi.value, " +
                        "idt.id as nid, " +
                        "pack.pickup_date, " +
                        "pack.next_pick_up_date, " +
                        "pred.reason_for_update, " +
                        "regime.description as regime_desc, " +
                        "lt.description as linha_terap_desc, " +
                        "dt.description as dt_desc, " +
                        "pat.date_of_birth, " +
                        "pre.patient_type, " +
                        "ep.episode_date, " +
                        "pre.prescription_date " +
                        "FROM Patient pat " +
                        "INNER JOIN patient_service_identifier psi ON psi.patient_id = pat.id " +
                        "INNER JOIN clinical_service cs ON psi.service_id = cs.id  and cs.code = :clinicalService " +
                        "INNER JOIN identifier_type idt ON psi.identifier_type_id = idt.id " +
                        "INNER JOIN ( SELECT patient_service_identifier_id, start_stop_reason_id, id, max(episode_date) as episode_date " +
                        "from episode " +
                        "   Group by 1,2,3 ) as ep on ep.patient_service_identifier_id = psi.id and ep.episode_date <= :endDate " +
                        "INNER JOIN start_stop_reason ststreason ON ep.start_stop_reason_id =  ststreason.id and ststreason.is_start_reason = true " +
                        "INNER JOIN ( SELECT patient_id, max(visit_date) as visit_date " +
                        "from patient_visit " +
                        "   Group by 1 ) as pvAux on pvAux.patient_id = pat.id " +
                        "INNER JOIN patient_visit pv on pv.visit_date = pvAux.visit_date and pv.patient_id = pvAux.patient_id " +
                        "INNER JOIN patient_visit_details pvd  ON pvd.episode_id = ep.id and pvd.patient_visit_id = pv.id " +
                        "INNER JOIN pack on pvd.pack_id = pack.id " +
                        "INNER JOIN ( SELECT id, clinic_id, patient_type, max(prescription.prescription_date) as prescription_date " +
                        "from prescription " +
                        "   Group by 1,2,3 ) as pre on pvd.prescription_id = pre.id and pre.prescription_date <= pack.pickup_date " +
                        "INNER JOIN prescription_detail pred " +
                        "ON pred.prescription_id = pre.id " +
                        "INNER JOIN therapeutic_regimen regime " +
                        "ON pred.therapeutic_regimen_id = regime.id " +
                        "INNER JOIN dispense_type dt " +
                        "ON pred.dispense_type_id = dt.id " +
                        "INNER JOIN therapeutic_line lt " +
                        "ON pred.therapeutic_line_id = lt.id " +
                        "where DATE(pack.next_pick_up_date) + :days >= :endDate " +
                        "group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("endDate", reportSearchParams.endDate)
        query.setParameter("days", 3)
        query.setParameter("clinicalService", clinicalService)
        List<Object[]> result = query.list()

        System.out.println(result.size())

        if (Utilities.listHasElements(result as ArrayList<?>)) {
            double percUnit = 100 / result.size()

            for (item in result) {
                ActivePatientReport activePatientReport = setGenericInfo(reportSearchParams, clinic, item[14] as Date)
                processMonitor.setProgress(processMonitor.getProgress() + percUnit)
                reportProcessMonitorService.save(processMonitor)
                generateAndSaveActivePacient(item as List, reportSearchParams, activePatientReport, reportId)
                println(activePatientReport)
                resultList.add(activePatientReport)
            }

            processMonitor.setProgress(100)
            processMonitor.setMsg("Processamento terminado")
            reportProcessMonitorService.save(processMonitor)

            return resultList
        } else {
            processMonitor.setProgress(100)
            processMonitor.setMsg("Processamento terminado")
            reportProcessMonitorService.save(processMonitor)
            return new ArrayList<ActivePatientReport>()
        }
    }


    private ActivePatientReport setGenericInfo(ReportSearchParams searchParams, Clinic clinic, Date dateOfBirth) {
        ActivePatientReport activePatientReport = new ActivePatientReport()
        activePatientReport.setClinic(clinic.getClinicName())
        activePatientReport.setStartDate(searchParams.startDate)
        activePatientReport.setEndDate(searchParams.endDate)
        activePatientReport.setReportId(searchParams.id)
        activePatientReport.setPeriodType(searchParams.periodType)
        activePatientReport.setReportId(searchParams.id)
        activePatientReport.setYear(searchParams.year)
        activePatientReport.setAge(ConvertDateUtils.getAge(dateOfBirth).intValue() as String)

//        activePatientReport.setProvince(Province.findById(searchParams.provinceId).description)
        activePatientReport.setProvince(Province.findById(clinic.province.id).description)
        clinic.district == null? activePatientReport.setDistrict("") : activePatientReport.setDistrict(District.findById(clinic.district.id).description)
        return activePatientReport
    }

    void generateAndSaveActivePacient(List item, ReportSearchParams reportSearchParams, ActivePatientReport activePatientReport, String reportId) {

        activePatientReport.setReportId(reportId)
        activePatientReport.setFirstNames(item[0].toString())
        activePatientReport.setMiddleNames(item[1].toString())
        activePatientReport.setLastNames(item[2].toString())
        activePatientReport.setGender(item[3].toString())
        item[4] == null? activePatientReport.setCellphone("") : activePatientReport.setCellphone(item[4].toString())
        activePatientReport.setTherapeuticRegimen(item[11].toString())
        activePatientReport.setTherapeuticLine(item[12].toString())
        activePatientReport.setDispenseType(item[13].toString())
        item[15] == null? activePatientReport.setPatientType('') : activePatientReport.setPatientType(item[15].toString())

        // set PickUpDate
        if (item[8] != null) {
            Date pickUpDate = formatter.parse(item[8].toString())
            activePatientReport.setPickupDate(pickUpDate)
        }
        // set nextPickUpDate
        if (item[9] != null) {
            Date nextPickUpDate = formatter.parse(item[9].toString())
            activePatientReport.setNextPickUpDate(nextPickUpDate)
        }

        // Setting nid
        if (item[5] != null) {
            if (item[5]) {
                activePatientReport.setNid(item[6].toString())
            } else {
                activePatientReport.setNid(item[7].toString())
            }
        }


        save(activePatientReport)

    }
}
