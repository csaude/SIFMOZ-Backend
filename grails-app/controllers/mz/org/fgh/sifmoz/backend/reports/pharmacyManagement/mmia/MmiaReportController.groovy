package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import grails.converters.JSON
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.reports.common.IReportProcessMonitorService
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import mz.org.fgh.sifmoz.backend.utilities.Utilities

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class MmiaReportController extends MultiThreadRestReportController{

    IMmiaReportService mmiaReportService
    MmiaReport curMmiaReport
    IMmiaStockSubReportService mmiaStockSubReportService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MmiaReportController() {
        super(MmiaReport)
    }

    @Override
    protected String getProcessingStatusMsg() {
        if (!Utilities.stringHasValue(processStage)) processStage = PROCESS_STATUS_INITIATING
        return processStage
    }

<<<<<<< HEAD
=======
    def printReport(String reportId, String fileType) {
        byte [] report = super.printReport(reportId, fileType, "/home/voloide/projects/dev/workspaces/JWORKSPACE/SIFMOZ-Backend/src/main/webapp/reports/pharmacyManagement", "MmiaReport.jrxml")
        render(file: report, contentType: 'application/pdf')
    }

>>>>>>> 92963a1a071960f72c8701a71908032755213ac4
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond mmiaReportService.list(params), model:[mmiaReportCount: mmiaReportService.count()]
    }

    def show(String id) {
        respond mmiaReportService.get(id)
    }

    @Transactional
    def save(MmiaReport mmiaReport) {
        if (mmiaReport == null) {
            render status: NOT_FOUND
            return
        }
        if (mmiaReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mmiaReport.errors
            return
        }

        try {
            mmiaReportService.save(mmiaReport)
        } catch (ValidationException e) {
            respond mmiaReport.errors
            return
        }

        respond mmiaReport, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MmiaReport mmiaReport) {
        if (mmiaReport == null) {
            render status: NOT_FOUND
            return
        }
        if (mmiaReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mmiaReport.errors
            return
        }

        try {
            mmiaReportService.save(mmiaReport)
        } catch (ValidationException e) {
            respond mmiaReport.errors
            return
        }

        respond mmiaReport, [status: OK, view:"show"]
    }

    def getProcessingStatus(String reportId) {
        render JSONSerializer.setJsonObjectResponse(reportProcessMonitorService.getByReportId(reportId)) as JSON
    }

    @Transactional
    def delete(String id) {
        if (id == null || mmiaReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def initReportProcess (ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
        render JSONSerializer.setJsonObjectResponse(this.processStatus) as JSON
        doProcessReport()
    }

    @Override
    void run() {
        initMmiaReportRecord()
        searchAndProcessPacks()
        processMmiaStock()
    }

    void processMmiaStock() {
        mmiaStockSubReportService.generateMmiaStockSubReport(getSearchParams(), this.processStatus)
    }

    void searchAndProcessPacks() {
        mmiaReportService.processReport(getSearchParams(), this.curMmiaReport, this.processStatus)
    }

    private void initMmiaReportRecord() {
        this.curMmiaReport = new MmiaReport()
        this.curMmiaReport.setReportId(getSearchParams().getId())
        this.curMmiaReport.setClinicId(getSearchParams().getClinicId())
        this.curMmiaReport.setPeriodType(getSearchParams().getPeriodType())
        this.curMmiaReport.setPeriod(Integer.valueOf(getSearchParams().getPeriod()))
        this.curMmiaReport.setYear(getSearchParams().getYear())
        this.curMmiaReport.setStartDate(getSearchParams().getStartDate())
        this.curMmiaReport.setEndDate(getSearchParams().getEndDate())
    }
}
