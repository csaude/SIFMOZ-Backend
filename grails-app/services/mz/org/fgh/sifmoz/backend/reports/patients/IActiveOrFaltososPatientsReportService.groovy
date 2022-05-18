package mz.org.fgh.sifmoz.backend.reports.patients

import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams

interface IActiveOrFaltososPatientsReportService {

    ActiveOrFaltosoPatientReport get(Serializable id)

    List<ActiveOrFaltosoPatientReport> list(Map args)

    Long count()

    ActiveOrFaltosoPatientReport delete(Serializable id)

    List<ActiveOrFaltosoPatientReport> processamentoDados (ReportSearchParams reportSearchParams)

    ActiveOrFaltosoPatientReport save(ActiveOrFaltosoPatientReport activeOrFaltosoPatientReport)

    List<ActiveOrFaltosoPatientReport> getReportDataByReportId(String reportId)

    void doSave(List<ActiveOrFaltosoPatientReport> activePatientsReport)
}
