package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.packaging.IPackService
import mz.org.fgh.sifmoz.backend.reports.common.IReportProcessMonitorService
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import org.springframework.beans.factory.annotation.Autowired

@Transactional
@Service(AbsentPatientsReport)
abstract class AbsentPatientsReportService implements IAbsentPatientsReportService{
    @Autowired
    IPackService packService
    @Autowired
    IReportProcessMonitorService reportProcessMonitorService

    public static final String PROCESS_STATUS_PROCESSING_FINISHED = "Processamento terminado"
    @Override
    void processReportAbsentDispenseRecords(ReportSearchParams searchParams, ReportProcessMonitor processMonitor) {
        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        List absentReferredPatients = packService.getAbsentPatientsByClinicalServiceAndClinicOnPeriod(clinicalService,clinic,searchParams.startDate,searchParams.endDate)
        double percentageUnit = 100/absentReferredPatients.size()
        for (int i = 0; i < absentReferredPatients.size(); i ++) {
            Object item = absentReferredPatients[i]
            Episode episode = (Episode) item[0]
            AbsentPatientsReport absentPatient = new AbsentPatientsReport()
            absentPatient.setPharmacyId(clinic.id)
            absentPatient.setStartDate(searchParams.startDate)
            absentPatient.setEndDate(searchParams.endDate)
            absentPatient.setClinicalServiceId(episode.patientServiceIdentifier.service.code)
            absentPatient.setReportId(searchParams.id)
            absentPatient.setPeriodType(searchParams.periodType)
            absentPatient.setReportId(searchParams.id)
            absentPatient.setNid(episode.patientServiceIdentifier.value)
            absentPatient.setName(episode.patientServiceIdentifier.patient.firstNames +' '+episode.patientServiceIdentifier.patient.lastNames)

            absentPatient.setDateMissedPickUp(item[1] as Date)
            Date abandonmentDate = ConvertDateUtils.addDaysDate(absentPatient.dateMissedPickUp,60)
            if(searchParams.endDate.after(abandonmentDate)) {
                absentPatient.setDateIdentifiedAbandonment(abandonmentDate)
            }
            if(item[2] != null){
                absentPatient.setContact(String.valueOf(item[2]))
            }
            if(item[3] != null) {
                absentPatient.setReturnedPickUp(item[3] as Date)
            }
            save(absentPatient)
            reportProcessMonitorService.save(processMonitor)
            if (100 == processMonitor.progress.intValue()) {
                processMonitor.setMsg(PROCESS_STATUS_PROCESSING_FINISHED)
            }
        }
    }
}
