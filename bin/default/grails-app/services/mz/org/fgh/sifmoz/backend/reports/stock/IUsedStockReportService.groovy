package mz.org.fgh.sifmoz.backend.reports.stock


import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams

interface IUsedStockReportService {

    UsedStockReportTemp get(Serializable id)

    List<UsedStockReportTemp> list(Map args)

    Long count()

    UsedStockReportTemp delete(Serializable id)

    UsedStockReportTemp save(UsedStockReportTemp usedStockReport)

    void processReportUsedStockRecords(ReportSearchParams searchParams)

    List<UsedStockReportTemp> getReportDataByReportId(String reportId)
}
