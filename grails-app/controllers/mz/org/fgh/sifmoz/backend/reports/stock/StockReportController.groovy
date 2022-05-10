package mz.org.fgh.sifmoz.backend.reports.stock


import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams

import static org.springframework.http.HttpStatus.*

class StockReportController extends MultiThreadRestReportController {


    IStockReportService stockReportService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    StockReportController() {
        super(StockReportTemp)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockReportService.list(params), model: [referredPatientsReportCount: stockReportService.count()]
    }

    def show(Long id) {
        respond stockReportService.get(id)
    }

    @Transactional
    def save(StockReportTemp stockReport) {
        if (stockReport == null) {
            render status: NOT_FOUND
            return
        }
        if (stockReport.hasErrors()) {
            System.out.println("Ocorreu erro: " + stockReport.errors)
            transactionStatus.setRollbackOnly()
            respond stockReport.errors
            return
        }

        try {
            System.out.println("Iniciando Salvar..: " + stockReport.errors)
            stockReportService.save(stockReport)
            System.out.println("Salvou..: " + stockReport.errors)
        } catch (ValidationException e) {
            System.out.println("Ocorreu erro2: " + stockReport.errors)
            respond stockReport.errors
            return
        }

        respond stockReport, [status: CREATED, view: "show"]
    }


    @Transactional
    def update(StockReportTemp stockReport) {
        if (stockReport == null) {
            render status: NOT_FOUND
            return
        }
        if (stockReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockReport.errors
            return
        }

        try {
            IStockReportService.save(stockReport)
        } catch (ValidationException e) {
            respond stockReport.errors
            return
        }

        respond stockReport, [status: OK, view: "show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || IStockReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def initReportProcess(ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
        respond status: OK, view: "show"
        render qtyToProcess: qtyRecordsToProcess
        doProcessReport()
    }
    /**
     * process report
     */
    @Override
    protected int countProcessedRecs() {
        return 0
    }

    @Override
    int countRecordsToProcess() {
        return 0
    }

    @Override
    protected String getProcessingStatusMsg() {
        return null
    }

    @Override
    void printReport(String reportId, String fileType) {

    }

    @Override
    void run() {
        stockReportService.processReportRecords(searchParams)

    }
}
