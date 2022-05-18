package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.episode.EpisodeService
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.packaging.PackService
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescription.PrescriptionService
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.reports.referralManagement.IReferredPatientsReportService
import mz.org.fgh.sifmoz.backend.reports.referralManagement.ReferredPatientsReport
import mz.org.fgh.sifmoz.backend.service.ClinicalService

@Transactional
@Service(ReferredPatientsReport)
abstract class ReferredPatientsReportService implements IReferredPatientsReportService{

    PrescriptionService prescriptionService

    PackService packService
    EpisodeService episodeService

    @Override
    void processReferredAndBackReferredReportRecords(ReportSearchParams searchParams) {

        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        Map<String , Prescription> prescriptionMap= prescriptionService.getLastPrescriptionsByClinicAndClinicalServiceAndEndDate(clinic,clinicalService,searchParams.endDate)
        List<Episode> episodes = episodeService.getEpisodeOfReferralOrBackReferral(clinic,clinicalService,searchParams.reportType,searchParams.startDate,searchParams.endDate)
        println(episodes)
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
            save(referredPatient)
        }
    }

    void processReportReferredDispenseRecords(ReportSearchParams searchParams) {
        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        List<Pack> packs = packService.getPacksOfReferredPatientsByClinicalServiceAndClinicOnPeriod(clinicalService,clinic,searchParams.startDate,searchParams.endDate)

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
        }
    }

    void processReportAbsentReferredDispenseRecords(ReportSearchParams searchParams) {
        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        List absentReferredPatients = packService.getAbsentReferredPatientsByClinicalServiceAndClinicOnPeriod(clinicalService,clinic,searchParams.startDate,searchParams.endDate)

        for (int i = 0; i < absentReferredPatients.size(); i ++) {
            Object item = absentReferredPatients[0]
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
}
