package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.historicoLevantamento

import grails.converters.JSON
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import mz.org.fgh.sifmoz.backend.utilities.Utilities

class HistoricoLevantamentoReportController extends MultiThreadRestReportController {
    IHistoricoLevantamentoReportService historicoLevantamentoReportService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    HistoricoLevantamentoReportController() {
        super(HistoricoLevantamentoReport)
    }

    def index() { }

    /**
     * Implementa toda a logica de processamento da informação do relatório
     */
    @Override
    void run() {
        processHistryReport()
    }

    def initReportProcess (ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
        render getProcessStatus() as JSON
        doProcessReport()
    }

    void processHistryReport() {
        historicoLevantamentoReportService.processamentoDados(getSearchParams(),  this.processStatus)
    }

    def printReport(String reportId, String fileType) {
        List<HistoricoLevantamentoReport> reportObjects = historicoLevantamentoReportService.getReportDataByReportId(reportId)
//        HistoricoLevantamentoReport historicoLevantamentoReport = reportObjects[0]
//
////        String jrxmlFilePath = System.getProperty("user.home")+ File.separator + "HistoricoDeLevantamento.jrxml"
//        String jrxmlFilePath = getReportsPath()+"pharmacyManagement/HistoricoDeLevantamento.jrxml"
//        Map<String, Object> map = new HashMap<>()
//        map.put("path", System.getProperty("user.home"))
//        map.put("reportId", reportId)
//        map.put("clinic", historicoLevantamentoReport==null? "":historicoLevantamentoReport.clinic)
//        map.put("province", historicoLevantamentoReport==null? "":historicoLevantamentoReport.province)
//        map.put("startDate", historicoLevantamentoReport==null? "":historicoLevantamentoReport.startDate)
//        map.put("endDate", historicoLevantamentoReport==null? "":historicoLevantamentoReport.endDate)
//        map.put("district", historicoLevantamentoReport==null? "":historicoLevantamentoReport.district)
//        map.put("year", historicoLevantamentoReport==null? "":historicoLevantamentoReport.year.toString())
//
//        byte [] report = ReportGenerator.generateReport(map, fileType,reportObjects, jrxmlFilePath)
//        render(file: report, contentType: 'application/pdf')

        render reportObjects as JSON
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
