package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor


interface IAbsentPatientsReportService {

    AbsentPatientsReport get(Serializable id)

    List<AbsentPatientsReport> list(Map args)

    Long count()

    AbsentPatientsReport delete(Serializable id)

    AbsentPatientsReport save(AbsentPatientsReport absentPatientsReport)

    void processReportAbsentDispenseRecords(ReportSearchParams searchParams, ReportProcessMonitor processMonitor)

}
