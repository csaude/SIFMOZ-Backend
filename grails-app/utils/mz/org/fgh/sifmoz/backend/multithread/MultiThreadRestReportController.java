package mz.org.fgh.sifmoz.backend.multithread;

import grails.rest.RestfulController;

import java.util.concurrent.ExecutorService;

public abstract class MultiThreadRestReportController<T> extends RestfulController<T> implements ReportExecutor {
    protected ReportSearchParams searchParams;
    private static ExecutorService executor;
    protected String processStage;
    protected ReportProcessStatus processStatus;
    public static final String PROCESS_STATUS_INITIATING = "Iniciando processamento";
    public static final String PROCESS_STATUS_PROCESSING_FINISHED = "Processamento terminado";

    public MultiThreadRestReportController(Class<T> resource) {
        super(resource);
        executor = ExecutorThreadProvider.getInstance().getExecutorService();
    }

    public ReportSearchParams getSearchParams() {
        return searchParams;
    }

    protected void initReportParams(ReportSearchParams searchParams) {
        this.searchParams = searchParams;
        this.searchParams.determineStartEndDate();
        this.processStatus = new ReportProcessStatus(getSearchParams().getId(), getProcessingStatusMsg(), 0, countRecordsToProcess());
    }

    /**
     * Processa o relatório
     */
    protected void doProcessReport() {
        executor.execute(this);
        processStage = PROCESS_STATUS_PROCESSING_FINISHED;
        updateProcessingStatus();
    }

    protected void updateProcessingStatus() {
        this.processStatus.setProcessedRecs(countProcessedRecs());
        this.processStatus.setMsg(getProcessingStatusMsg());
    }

    public ReportProcessStatus getProcessStatus() {
        updateProcessingStatus();
        return processStatus;
    }

    protected abstract int countProcessedRecs();

    protected abstract int countRecordsToProcess();

    protected abstract String getProcessingStatusMsg();

    public abstract void printReport(String reportId, String fileType);
}
