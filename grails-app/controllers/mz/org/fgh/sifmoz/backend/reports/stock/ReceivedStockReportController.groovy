package mz.org.fgh.sifmoz.backend.reports.stock

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import io.micronaut.core.util.ArrayUtils
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.reports.monitoringAndEvaluation.ArvDailyRegisterReportTemp
import mz.org.fgh.sifmoz.report.ReportGenerator

import static org.springframework.http.HttpStatus.*

class ReceivedStockReportController extends MultiThreadRestReportController {


    IStockReportService stockReportService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ReceivedStockReportController() {
        super(StockReportTemp)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockReportService.list(params), model: [stockReportReportCount: stockReportService.count()]
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
            transactionStatus.setRollbackOnly()
            respond stockReport.errors
            return
        }

        try {
            stockReportService.save(stockReport)
        } catch (ValidationException e) {
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
    long getRecordsQtyToProcess() {
        return 0
    }

    void getProcessedRecordsQty(String reportId) {

    }

   def printReport(String reportId, String fileType) {
       List<StockReportTemp> itemsReport = stockReportService.getReportDataByReportId(reportId)
       Map<String, Object> map = new HashMap<>()
       if (ArrayUtils.isNotEmpty(itemsReport)) {
           map.put("path", "/home/erciliofrancisco/Documents/local/dev/idmed/SIFMOZ-Backend/src/main/webapp/reports/")
           map.put("reportId", reportId)
           map.put("facilityName", itemsReport.get(0).getPharmacyId()==null? "":Clinic.findById(itemsReport.get(0).getPharmacyId()).getClinicName())
           map.put("endDate", itemsReport.get(0).getEndDate())
           map.put("startDate", itemsReport.get(0).getStartDate())
           map.put("province", itemsReport.get(0).getProvinceId()==null? "": Province.findById(itemsReport.get(0).getProvinceId()).getDescription())
           map.put("district", itemsReport.get(0).getDistrictId()==null? "":District.findById(itemsReport.get(0).getDistrictId()).getDescription())
           byte[] report = ReportGenerator.generateReport(map, fileType,itemsReport, "/home/erciliofrancisco/Documents/local/dev/idmed/SIFMOZ-Backend/src/main/webapp/reports/stock/ReceivedStockReport.jrxml")
           render(file: report, contentType: 'application/pdf')
       }
   }

    @Override
    void run() {
        stockReportService.processReportRecords(searchParams)

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
