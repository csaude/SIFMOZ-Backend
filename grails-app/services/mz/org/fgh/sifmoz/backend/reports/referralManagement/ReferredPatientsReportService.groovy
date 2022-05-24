package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.episode.IEpisodeService
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.packaging.IPackService
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.prescription.IPrescriptionService
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.reports.common.IReportProcessMonitorService
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor

import mz.org.fgh.sifmoz.backend.reports.referralManagement.IReferredPatientsReportService
import mz.org.fgh.sifmoz.backend.reports.referralManagement.ReferredPatientsReport
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import org.springframework.beans.factory.annotation.Autowired

@Transactional
@Service(ReferredPatientsReport)
abstract class ReferredPatientsReportService implements IReferredPatientsReportService{
    @Autowired
    IPrescriptionService prescriptionService
    @Autowired
    IPackService packService
    @Autowired
    IEpisodeService episodeService
    @Autowired
    IReportProcessMonitorService reportProcessMonitorService

    public static final String PROCESS_STATUS_PROCESSING_FINISHED = "Processamento terminado"

    @Override
    void processReferredAndBackReferredReportRecords(ReportSearchParams searchParams, ReportProcessMonitor processMonitor) {

        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        Map<String , Prescription> prescriptionMap= prescriptionService.getLastPrescriptionsByClinicAndClinicalServiceAndEndDate(clinic,clinicalService,searchParams.endDate)
        List<Episode> episodes = episodeService.getEpisodeOfReferralOrBackReferral(clinic,clinicalService,searchParams.reportType,searchParams.startDate,searchParams.endDate)
        println(episodes)
        double percentageUnit
        if (episodes.size() == 0) {
            setProcessMonitor(processMonitor)
            reportProcessMonitorService.save(processMonitor)
        }  else{
            percentageUnit = 100/episodes.size()
        }
        List<ReferredPatientsReport> referencesToCreate = new ArrayList<>()
        for (Episode episode:episodes) {
            Prescription lastPrescription = prescriptionMap.get(episode.patientServiceIdentifier.patient.id)
            PrescriptionDetail prescriptionDetail = lastPrescription.prescriptionDetails.getAt(0)

            ReferredPatientsReport referredPatient = setGenericInfo(searchParams,clinic,episode)
            if (searchParams.reportType.equals('REFERIDO_PARA')) {
                referredPatient.setReferrenceDate(episode.episodeDate)
                referredPatient.setLastPrescriptionDate(lastPrescription.prescriptionDate)
                referredPatient.setDispenseType(prescriptionDetail.dispenseType.description)
                referredPatient.setTherapeuticalRegimen(prescriptionDetail.therapeuticRegimen.description)
                referredPatient.setNextPickUpDate(lastPrescription.patientVisitDetails.getAt(0).pack.nextPickUpDate)
            } else {
                referredPatient.setDateBackUs(episode.episodeDate)
                referredPatient.setReferrenceDate(episodeService.getEpisodeOfReferralByPatientServiceIdentfierAndBelowEpisodeDate(episode.patientServiceIdentifier,episode.episodeDate).episodeDate)
                referredPatient.setLastPickUpDate(lastPrescription.patientVisitDetails.getAt(0).pack.pickupDate)
                referredPatient.setNotes(episode.notes)
            }
            referencesToCreate.add(referredPatient)
            processMonitor.setProgress(processMonitor.getProgress() + percentageUnit)
            if (100 == processMonitor.progress.intValue() || 99 == processMonitor.progress.intValue()) {
                setProcessMonitor(processMonitor)
            }
            reportProcessMonitorService.save(processMonitor)
            save(referredPatient)
        }
    }


    @Override
    void processReportReferredDispenseRecords(ReportSearchParams searchParams, ReportProcessMonitor processMonitor) {
        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        List<Pack> packs = packService.getPacksOfReferredPatientsByClinicalServiceAndClinicOnPeriod(clinicalService,clinic,searchParams.startDate,searchParams.endDate)
        double percentageUnit
        if (packs.size() == 0) {
            setProcessMonitor(processMonitor)
            reportProcessMonitorService.save(processMonitor)
        }  else{
            percentageUnit = 100/packs.size()
        }
        for (Pack pack:packs) {
            Prescription prescription = pack.patientVisitDetails.getAt(0).prescription
            PrescriptionDetail prescriptionDetail = prescription.prescriptionDetails.getAt(0)
            Episode episode = pack.patientVisitDetails.getAt(0).episode

            ReferredPatientsReport referredPatientDispense = setGenericInfo(searchParams,clinic,episode)
            referredPatientDispense.setDispenseType(prescriptionDetail.dispenseType.description)
            referredPatientDispense.setTherapeuticalRegimen(prescriptionDetail.therapeuticRegimen.description)
            referredPatientDispense.setPickUpDate(pack.pickupDate)
            referredPatientDispense.setNextPickUpDate(pack.nextPickUpDate)
            referredPatientDispense.setTarvType(prescription.patientType)
            save(referredPatientDispense)
            processMonitor.setProgress(processMonitor.getProgress() + percentageUnit)
            if (100 == processMonitor.progress.intValue() || 99 == processMonitor.progress.intValue()) {
                setProcessMonitor(processMonitor)
            }
            reportProcessMonitorService.save(processMonitor)
        }
    }

    @Override
    void processReportAbsentReferredDispenseRecords(ReportSearchParams searchParams, ReportProcessMonitor processMonitor) {
        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        List absentReferredPatients = packService.getAbsentReferredPatientsByClinicalServiceAndClinicOnPeriod(clinicalService,clinic,searchParams.startDate,searchParams.endDate)
        double percentageUnit
        if (absentReferredPatients.size() == 0) {
            setProcessMonitor(processMonitor)
            reportProcessMonitorService.save(processMonitor)
        }  else{
            percentageUnit = 100/absentReferredPatients.size()
        }
        for (int i = 0; i < absentReferredPatients.size(); i ++) {
            Object item = absentReferredPatients[i]
            Episode episode = (Episode) item[0]
            ReferredPatientsReport referredPatientAbsent = setGenericInfo(searchParams,clinic,episode)

            referredPatientAbsent.setDateMissedPickUp(item[1] as Date)
            Date abandonmentDate = ConvertDateUtils.addDaysDate(referredPatientAbsent.dateMissedPickUp,60)
            if(searchParams.endDate.after(abandonmentDate)) {
                referredPatientAbsent.setDateIdentifiedAbandonment(abandonmentDate)
            }
            if(item[2] != null){
                referredPatientAbsent.setContact(String.valueOf(item[2]))
            }
            if(item[3] != null) {
                referredPatientAbsent.setReturnedPickUp(item[3] as Date)
            }
            save(referredPatientAbsent)
            processMonitor.setProgress(processMonitor.getProgress() + percentageUnit)
            if (100 == processMonitor.progress.intValue() || 99 == processMonitor.progress.intValue()) {
              setProcessMonitor(processMonitor)
            }
            reportProcessMonitorService.save(processMonitor)
        }

    }

    private ReferredPatientsReport setGenericInfo(ReportSearchParams searchParams,Clinic clinic,Episode episode) {

        ReferredPatientsReport referredPatient = new ReferredPatientsReport()
        referredPatient.setPharmacyId(clinic.id)
        referredPatient.setStartDate(searchParams.startDate)
        referredPatient.setEndDate(searchParams.endDate)
        referredPatient.setClinicalServiceId(episode.patientServiceIdentifier.service.code)
        referredPatient.setReportId(searchParams.id)
        referredPatient.setPeriodType(searchParams.periodType)
        referredPatient.setReportId(searchParams.id)
        referredPatient.setAge(ConvertDateUtils.getAge(episode.patientServiceIdentifier.patient.dateOfBirth).intValue())
        referredPatient.setNid(episode.patientServiceIdentifier.value)
        referredPatient.setName(episode.patientServiceIdentifier.patient.firstNames +' '+episode.patientServiceIdentifier.patient.lastNames)
        referredPatient.setReferralPharmacy(episode.referralClinic.clinicName)
        return referredPatient
    }

    private ReportProcessMonitor setProcessMonitor(ReportProcessMonitor processMonitor) {
        processMonitor.setProgress(100)
        processMonitor.setMsg(PROCESS_STATUS_PROCESSING_FINISHED)
    }
}
