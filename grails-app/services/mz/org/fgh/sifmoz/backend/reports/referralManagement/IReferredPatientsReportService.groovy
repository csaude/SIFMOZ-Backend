package mz.org.fgh.sifmoz.backend.reports.referralManagement


import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams

interface IReferredPatientsReportService {

    ReferredPatientsReport get(Serializable id)

    List<ReferredPatientsReport> list(Map args)

    Long count()

    ReferredPatientsReport delete(Serializable id)

    ReferredPatientsReport save(ReferredPatientsReport referredPatientsReport)

    void processReferredAndBackReferredReportRecords(ReportSearchParams searchParams)

    void processReportReferredDispenseRecords(ReportSearchParams searchParams)

    void processReportAbsentReferredDispenseRecords(ReportSearchParams searchParams)

}
