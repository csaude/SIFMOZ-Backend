package mz.org.fgh.sifmoz.backend.reports.common

class ReportProcessMonitor {

    String id
    String reportId
    String msg
    double progress

    ReportProcessMonitor() {
    }

    ReportProcessMonitor(String reportId, String msg, double progress) {
        this.reportId = reportId
        this.msg = msg
        this.progress = progress
    }

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
    }
}
