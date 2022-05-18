package mz.org.fgh.sifmoz.backend.reports.stock

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import io.micronaut.core.util.ArrayUtils
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.report.ReportGenerator

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
        respond status: OK, view: "show"
        render qtyToProcess: qtyRecordsToProcess
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
        if (ArrayUtils.isNotEmpty(itemsReport) && itemsReport != null) {
            UsedStockReportTemp headerObj = itemsReport.get(0)
            map.put("path", "/home/erciliofrancisco/Documents/local/dev/idmed/SIFMOZ-Backend/src/main/webapp/reports/")
            map.put("reportId", reportId)
            map.put("facilityName", headerObj.getPharmacyId() == null ? "" : Clinic.findById(headerObj.getPharmacyId()).getClinicName())
            map.put("endDate", headerObj.getEndDate())
            map.put("startDate", headerObj.getStartDate())
            map.put("province", headerObj.getProvinceId() == null ? "" : Province.findById(headerObj.getProvinceId()).getDescription())
            map.put("district", headerObj.getDistrictId() == null ? "" : District.findById(headerObj.getDistrictId()).getDescription())
            byte[] report = ReportGenerator.generateReport(map, fileType,itemsReport, "/home/erciliofrancisco/Documents/local/dev/idmed/SIFMOZ-Backend/src/main/webapp/reports/stock/UsedStockReport.jrxml")
            render(file: report, contentType: 'application/pdf')
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

    @Override
    protected String getProcessingStatusMsg() {
        return null
    }
}