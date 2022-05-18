package mz.org.fgh.sifmoz.backend.multithread;

import grails.gorm.transactions.Transactional;
import grails.rest.RestfulController;
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils;
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor;
import mz.org.fgh.sifmoz.backend.reports.common.IReportProcessMonitorService;
import mz.org.fgh.sifmoz.report.ReportGenerator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionFactoryUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public abstract class MultiThreadRestReportController<T> extends RestfulController<T> implements ReportExecutor {
    protected ReportSearchParams searchParams;
    private static ExecutorService executor;
    protected String processStage;
    @Autowired
    protected SessionFactory sessionFactory;
    protected ReportProcessMonitor processStatus;
    public static final String PROCESS_STATUS_INITIATING = "Iniciando processamento";
    public static final String PROCESS_STATUS_PROCESSING_FINISHED = "Processamento terminado";
    @Autowired
    protected IReportProcessMonitorService reportProcessMonitorService;

    public MultiThreadRestReportController(Class<T> resource) {
        super(resource);
        executor = ExecutorThreadProvider.getInstance().getExecutorService();
    }

    public ReportSearchParams getSearchParams() {
        return searchParams;
    }

    @Transactional
    protected void initReportParams(ReportSearchParams searchParams) {
        this.searchParams = searchParams;
        this.searchParams.determineStartEndDate();
        this.processStatus = new ReportProcessMonitor(getSearchParams().getId(), getProcessingStatusMsg(), 0);
        reportProcessMonitorService.save(this.processStatus);
    }

    /**
     * Processa o relatório
     */
    protected void doProcessReport() {
        executor.execute(this);
        this.processStage = PROCESS_STATUS_PROCESSING_FINISHED;
        this.processStatus.setProgress(100);
        reportProcessMonitorService.save(this.processStatus);
    }

    protected void updateProcessingStatus() {
        this.processStatus.setProgress(100);
        reportProcessMonitorService.save(this.processStatus);
    }


    public ReportProcessMonitor getProcessStatus() {
        return processStatus;
    }

    protected abstract String getProcessingStatusMsg();

    protected byte[] printReport(String reportId, String fileType, String path, String report) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        Connection connection = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();

        map.put("path", path);
        map.put("reportId", reportId);
        map.put("username", "Test_user");
        map.put("dataelaboracao", ConvertDateUtils.getCurrentDate());

        return ReportGenerator.generateReport(map,path, report, connection);
    }
}
