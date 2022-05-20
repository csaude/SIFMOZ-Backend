package mz.org.fgh.sifmoz.backend.reports.patients

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import mz.org.fgh.sifmoz.report.ReportGenerator

import static org.springframework.http.HttpStatus.*

class ActivePatientReportController extends MultiThreadRestReportController {

    IActivePatientReportService activePatientReportService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ActivePatientReportController() {
        super(ActivePatientReport)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond activePatientReportService.list(params), model:[activePatientsReportCount: activePatientReportService.count()]
    }

    def show(Long id) {
        respond activePatientReportService.get(id)
    }

    @Transactional
    def save(ActivePatientReport activePatientsReport) {
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
    def update(ActivePatientReport activePatientsReport) {
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

        respond activePatientsReport, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || activePatientReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def initReportProcess (ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
        System.out.println(searchParams.startDate.toString())
        System.out.println(searchParams.endDate.toString())
        System.out.println(searchParams.id)
        render getProcessStatus() as JSON
        doProcessReport()
    }

    /**
     * Implementa toda a logica de processamento da informação do relatório
     */
    @Override
    void run() {
        activePatientReportService.doSave(activePatientReportService.processamentoDados(getSearchParams()))
    }

    def printReport(String reportId, String fileType) {
        List<ActivePatientReport> reportObjects = activePatientReportService.getReportDataByReportId(reportId)
        ActivePatientReport activePatientReport = reportObjects[0]
        System.out.println(activePatientReport.toString())
//        String jrxmlFilePath = System.getProperty("user.home")+ File.separator + "PacientesActivos.jrxml"
        String jrxmlFilePath = getReportsPath()+"patient/PacientesActivos.jrxml"
        Map<String, Object> map = new HashMap<>()
        map.put("path", System.getProperty("user.home"))
        map.put("reportId", reportId)
        map.put("clinic", activePatientReport==null? "":Clinic.findById(activePatientReport.getId()).getClinicName())
        map.put("province", activePatientReport==null? "":activePatientReport.province)
        map.put("startDate", activePatientReport==null? "":activePatientReport.getStartDate())
        map.put("endDate", activePatientReport==null? "":activePatientReport.getEndDate())
        map.put("district", activePatientReport==null? "":activePatientReport.district)
        map.put("year", activePatientReport.getYear().toString())

        byte [] report = ReportGenerator.generateReport(map, fileType,reportObjects, jrxmlFilePath)
        render(file: report, contentType: 'application/pdf')
    }


    protected int countProcessedRecs() {
        return 0
    }

    protected int countRecordsToProcess() {
        return 0
    }


    def getProcessingStatus(String reportId) {
        render JSONSerializer.setJsonObjectResponse(reportProcessMonitorService.getByReportId(reportId)) as JSON
    }

    @Override
    protected String getProcessingStatusMsg() {
        if (!Utilities.stringHasValue(processStage)) processStage = PROCESS_STATUS_INITIATING
        return processStage
    }
}
