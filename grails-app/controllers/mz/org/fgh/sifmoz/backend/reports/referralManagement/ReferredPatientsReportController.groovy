package mz.org.fgh.sifmoz.backend.reports.referralManagement

import grails.converters.JSON
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import mz.org.fgh.sifmoz.report.ReportGenerator
import org.apache.commons.lang3.ArrayUtils

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional


class ReferredPatientsReportController extends MultiThreadRestReportController{

    IReferredPatientsReportService referredPatientsReportService
    public static final String PROCESS_STATUS_PROCESSING_PACKS = "A processar pacientes"

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ReferredPatientsReportController() {
        super(ReferredPatientsReport)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond referredPatientsReportService.list(params), model:[referredPatientsReportCount: referredPatientsReportService.count()]
    }

    def show(Long id) {
        respond referredPatientsReportService.get(id)
    }

    @Transactional
    def save(ReferredPatientsReport referredPatientsReport) {
        if (referredPatientsReport == null) {
            render status: NOT_FOUND
            return
        }
        if (referredPatientsReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond referredPatientsReport.errors
            return
        }

        try {
            referredPatientsReportService.save(referredPatientsReport)
        } catch (ValidationException e) {
            respond referredPatientsReport.errors
            return
        }

        respond referredPatientsReport, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ReferredPatientsReport referredPatientsReport) {
        if (referredPatientsReport == null) {
            render status: NOT_FOUND
            return
        }
        if (referredPatientsReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond referredPatientsReport.errors
            return
        }

        try {
            referredPatientsReportService.save(referredPatientsReport)
        } catch (ValidationException e) {
            respond referredPatientsReport.errors
            return
        }

        respond referredPatientsReport, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || referredPatientsReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def initReportProcess (ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
       // render qtyToProcess: qtyRecordsToProcess
        render JSONSerializer.setJsonObjectResponse(this.processStatus) as JSON
        doProcessReport()
    }

    /**
     * Implementa toda a logica de processamento da informação do relatório
     */
    @Override
    void run() {
        if(searchParams.reportType.equals("HISTORICO_LEVANTAMENTO_PACIENTES_REFERIDOS")) {
            referredPatientsReportService.processReportReferredDispenseRecords(searchParams,this.processStatus)
        } else if (searchParams.reportType.equals("REFERIDOS_FALTOSOS_AO_LEVANTAMENTO")) {
            referredPatientsReportService.processReportAbsentReferredDispenseRecords(searchParams,this.processStatus)
        }  else {
            referredPatientsReportService.processReferredAndBackReferredReportRecords(searchParams,this.processStatus)
        }
    }

    @Override
    protected String getProcessingStatusMsg() {
        if (!Utilities.stringHasValue(processStage)) processStage = PROCESS_STATUS_INITIATING
        return processStage
    }


    def getProcessingStatus(String reportId) {
        render JSONSerializer.setJsonObjectResponse(reportProcessMonitorService.getByReportId(reportId)) as JSON
    }

    def deleteByReportId(String reportId) {
            List<ReferredPatientsReport> referredPatientsReports = ReferredPatientsReport.findAllByReportId(reportId)
        ReferredPatientsReport.deleteAll(referredPatientsReports)
        render status: NO_CONTENT
    }

    def printReport(String reportId, String reportType,String fileType) {

        String path = getReportsPath()+"referralManagement"
        List<ReferredPatientsReport> referredPatientsReports = ReferredPatientsReport.findAllByReportId(reportId)
        Map<String, Object> map = new HashMap<>()
        if (referredPatientsReports.size() > 0) {
            map.put("path", getReportsPath())
            map.put("mainfacilityname", referredPatientsReports.get(0).getPharmacyId().isEmpty() ? "" : Clinic.findById(referredPatientsReports.get(0).getPharmacyId()).getClinicName())
            map.put("dateEnd", referredPatientsReports.get(0).getEndDate())
            map.put("date", referredPatientsReports.get(0).getStartDate())

            String reportPathFile = null
            if(reportType.equals("HISTORICO_LEVANTAMENTO_PACIENTES_REFERIDOS")) {
                reportPathFile = path+"/HistoricoLevantamentosReferidosDe.jrxml"
            } else if (reportType.equals("REFERIDOS_FALTOSOS_AO_LEVANTAMENTO")) {
                reportPathFile = path+"/RelatorioAbandonosFaltososPersonalizadoReferido.jrxml"
            }  else if (reportType.equals("VOLTOU_REFERENCIA")) {
                reportPathFile = path+"/PacientesReferidosDeVolta.jrxml"
            } else {
                reportPathFile = path+"/PacientesReferidosPara.jrxml"
            }
            byte[] report = super.printReport(reportId, fileType, reportPathFile, map, referredPatientsReports)
            render(file: report, contentType: 'application/'+fileType.equals("PDF")? 'pdf' : 'xls')
        } else {
            render status: NO_CONTENT
        }
    }
}
