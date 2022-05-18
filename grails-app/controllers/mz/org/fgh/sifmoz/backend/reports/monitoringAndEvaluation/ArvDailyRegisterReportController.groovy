package mz.org.fgh.sifmoz.backend.reports.monitoringAndEvaluation

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.report.ReportGenerator
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.orm.hibernate5.SessionFactoryUtils

import javax.sql.DataSource
import java.sql.Connection

import static org.springframework.http.HttpStatus.*

class ArvDailyRegisterReportController extends MultiThreadRestReportController {

    IArvDailyRegisterReportService arvDailyRegisterReportService
    @Autowired
    protected SessionFactory sessionFactory;

    // def sessionFactory = SessionFactory.newInstance()

    DataSource dataSource
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ArvDailyRegisterReportController() {
        super(ArvDailyRegisterReportTemp)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond arvDailyRegisterReportService.list(params), model: [referredPatientsReportCount: arvDailyRegisterReportService.count()]
    }

    def show(Long id) {
        respond arvDailyRegisterReportService.get(id)
    }

    @Transactional
    def save(ArvDailyRegisterReportTemp reportTemp) {
        if (reportTemp == null) {
            render status: NOT_FOUND
            return
        }
        if (reportTemp.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond reportTemp.errors
            return
        }

        try {
            arvDailyRegisterReportService.save(reportTemp)
        } catch (ValidationException e) {
            respond reportTemp.errors
            return
        }

        respond reportTemp, [status: CREATED, view: "show"]
    }


    @Transactional
    def update(ArvDailyRegisterReportTemp reportTemp) {
        if (reportTemp == null) {
            render status: NOT_FOUND
            return
        }
        if (reportTemp.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond reportTemp.errors
            return
        }

        try {
            IArvDailyRegisterReportService.save(reportTemp)
        } catch (ValidationException e) {
            respond reportTemp.errors
            return
        }

        respond reportTemp, [status: OK, view: "show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || arvDailyRegisterReportService.delete(id) == null) {
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
        List<ArvDailyRegisterReportTemp> itemsReport = arvDailyRegisterReportService.getReportDataByReportId(reportId)
        Connection connection = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
        Map<String, Object> map = new HashMap<>()
        map.put("reportId", reportId)
        map.put("path", "/home/erciliofrancisco/Documents/local/dev/idmed/SIFMOZ-Backend/src/main/webapp/reports/")
        byte[] report = ReportGenerator.generateReport(map,
                "/home/erciliofrancisco/Documents/local/dev/idmed/SIFMOZ-Backend/src/main/webapp/reports/monitoring/",
                "LivroRegistoDiarioARV.jrxml",fileType, connection)
        render(file: report, contentType: 'application/pdf')
    }


    @Override
    void run() {
        arvDailyRegisterReportService.processReportRecords(searchParams)

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
