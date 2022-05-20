package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.historicoLevantamento

import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams

@Transactional
interface IHistoricoLevantamentoReportService {
    HistoricoLevantamentoReport get(Serializable id)

    List<HistoricoLevantamentoReport> list(Map args)

    Long count()

    HistoricoLevantamentoReport delete(Serializable id)

    List<HistoricoLevantamentoReport> processamentoDados (ReportSearchParams reportSearchParams)

    HistoricoLevantamentoReport save(HistoricoLevantamentoReport historicoLevantamentoReport)

    List<HistoricoLevantamentoReport> getReportDataByReportId(String reportId)

    // void doSave(List<HistoricoLevantamentoReport> historicoLevantamentoReports)
}
