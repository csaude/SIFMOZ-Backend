package mz.org.fgh.sifmoz.backend.reports.stock

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import io.micronaut.core.util.ArrayUtils
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import mz.org.fgh.sifmoz.backend.utilities.Utilities

import static org.springframework.http.HttpStatus.*


class UsedStockReportController extends MultiThreadRestReportController {


    IUsedStockReportService usedStockReportService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    UsedStockReportController() {
        super(StockReportTemp)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond usedStockReportService.list(params), model: [referredPatientsReportCount: stockReportService.count()]
    }

    def show(Long id) {
        respond usedStockReportService.get(id)
    }

    @Transactional
    def save(StockReportTemp stockReport) {
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
            usedStockReportService.save(stockReport)
        } catch (ValidationException e) {
            respond stockReport.errors
            return
        }

        respond stockReport, [status: CREATED, view: "show"]
    }


    @Transactional
    def update(UsedStockReportTemp usedStockReport) {
        if (usedStockReport == null) {
            render status: NOT_FOUND
            return
        }
        if (usedStockReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usedStockReport.errors
            return
        }

        try {
            IUsedStockReportService.save(usedStockReport)
        } catch (ValidationException e) {
            respond usedStockReport.errors
            return
        }

        respond usedStockReport, [status: OK, view: "show"]
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
        render JSONSerializer.setJsonObjectResponse(this.processStatus) as JSON
        doProcessReport()
    }
    /**
     * process report
     */
    long getRecordsQtyToProcess() {
        return 0
    }

    void getProcessedRecordsQty(String reportId) {

    }

    def printReport(String reportId, String fileType) {
        List<StockReportTemp> itemsReport = usedStockReportService.getReportDataByReportId(reportId)
        Map<String, Object> map = new HashMap<>()
        if (ArrayUtils.isNotEmpty(itemsReport)) {
            map.put("facilityName", itemsReport.get(0).getPharmacyId()==null? "":Clinic.findById(itemsReport.get(0).getPharmacyId()).getClinicName())
            map.put("endDate", itemsReport.get(0).getEndDate())
            map.put("startDate", itemsReport.get(0).getStartDate())
            map.put("province", itemsReport.get(0).getProvinceId()==null? "": Province.findById(itemsReport.get(0).getProvinceId()).getDescription())
            map.put("district", itemsReport.get(0).getDistrictId()==null? "":District.findById(itemsReport.get(0).getDistrictId()).getDescription())
            byte[] report = super.printReport(reportId, fileType, getReportsPath()+"stock/UsedStockReport.jrxml", map,itemsReport)
            render(file: report, contentType: 'application/'+fileType.equalsIgnoreCase("PDF")? 'pdf' : 'xls')
        }

    }


    @Override
    void run() {
        usedStockReportService.processReportUsedStockRecords(searchParams)
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