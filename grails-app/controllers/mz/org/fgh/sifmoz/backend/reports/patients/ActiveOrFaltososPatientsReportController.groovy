package mz.org.fgh.sifmoz.backend.reports.patients

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import mz.org.fgh.sifmoz.report.ReportGenerator

import static org.springframework.http.HttpStatus.*

class ActiveOrFaltososPatientsReportController extends MultiThreadRestReportController {

    IActiveOrFaltososPatientsReportService activeOrFaltososPatientsReportService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ActiveOrFaltososPatientsReportController() {
        super(ActiveOrFaltosoPatientReport)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond activeOrFaltososPatientsReportService.list(params), model:[activePatientsReportCount: activeOrFaltososPatientsReportService.count()]
    }

    def show(Long id) {
        respond activeOrFaltososPatientsReportService.get(id)
    }

    @Transactional
    def save(ActiveOrFaltosoPatientReport activePatientsReport) {
        if (activePatientsReport == null) {
            render status: NOT_FOUND
            return
        }
        if (activePatientsReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond activePatientsReport.errors
            return
        }

        try {
        } catch (ValidationException e) {
            respond activePatientsReport.errors
            return
        }

        respond activePatientsReport, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ActiveOrFaltosoPatientReport activePatientsReport) {
        if (activePatientsReport == null) {
            render status: NOT_FOUND
            return
        }
        if (activePatientsReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond activePatientsReport.errors
            return
        }

        try {
            // activeOrFaltososPatientsReportService.doSave(activePatientsReport)
        } catch (ValidationException e) {
            respond activePatientsReport.errors
            return
        }

        respond activePatientsReport, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || activeOrFaltososPatientsReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def initReportProcess (ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
        render getProcessStatus() as JSON
        doProcessReport()
    }

    /**
     * Implementa toda a logica de processamento da informação do relatório
     */
    @Override
    void run() {
        activeOrFaltososPatientsReportService.doSave(activeOrFaltososPatientsReportService.processamentoDados(getSearchParams()))
    }

    def printReport(ReportSearchParams searchParams) {
        String reportId = searchParams.getId()
        String jrxmlFilePath
        Map<String, Object> map = new HashMap<>()
        map.put("path", System.getProperty("user.home"))
        map.put("reportId", reportId)
        map.put("facilityName", Clinic.findById(searchParams.getClinicId()).getClinicName())
        map.put("startDate", new Date())
        map.put("endDate", new Date())
        map.put("province", Province.findById(searchParams.getProvinceId()).getDescription())
        map.put("district", District.findById(searchParams.getDistrictId()).getDescription())
        map.put("year", searchParams.getYear().toString())

        List<ActiveOrFaltosoPatientReport> reportObjects = activeOrFaltososPatientsReportService.getReportDataByReportId(reportId)
        if (searchParams.reportType.contains("ACTIVE_PATIENT")){
            jrxmlFilePath = System.getProperty("user.home")+ File.separator + "PacientesActivos.jrxml"
        } else {
            jrxmlFilePath = System.getProperty("user.home")+ File.separator + "PacientesFaltosos.jrxml"
        }

        byte [] report = ReportGenerator.generateReport(map,reportObjects,jrxmlFilePath)
        render(file: report, contentType: 'application/pdf')
    }

    @Override
    protected String getProcessingStatusMsg() {
        return null
    }
}
