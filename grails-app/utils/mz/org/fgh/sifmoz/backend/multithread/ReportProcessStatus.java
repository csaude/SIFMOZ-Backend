package mz.org.fgh.sifmoz.backend.multithread;

public class ReportProcessStatus {
    private String reportId;
    private String msg;
    private int processedRecs;
    private int qtyToProcess;

    public ReportProcessStatus() {
    }

    public ReportProcessStatus(String reportId, String msg, int processedRecs, int qtyToProcess) {
        this.reportId = reportId;
        this.msg = msg;
        this.processedRecs = processedRecs;
        this.qtyToProcess = qtyToProcess;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getProgress() {
        if (this.qtyToProcess == 0) return 0;
        return (this.processedRecs / this.qtyToProcess) * 100;
    }

    public int getProcessedRecs() {
        return processedRecs;
    }

    public void setProcessedRecs(int processedRecs) {
        this.processedRecs = processedRecs;
    }

    public int getQtyToProcess() {
        return qtyToProcess;
    }

    public void setQtyToProcess(int qtyToProcess) {
        this.qtyToProcess = qtyToProcess;
    }

    @Override
    public String toString() {
        return "{" +
                "reportId='" + reportId + '\'' +
                ", msg='" + msg + '\'' +
                ", processedRecs=" + processedRecs +
                ", qtyToProcess=" + qtyToProcess +
                ", progress=" + getProgress() +
                '}';
    }
}
