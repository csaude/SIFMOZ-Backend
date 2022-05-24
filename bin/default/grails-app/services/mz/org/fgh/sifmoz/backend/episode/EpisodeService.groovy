package mz.org.fgh.sifmoz.backend.episode

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.reports.referralManagement.IReferredPatientsReportService
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason

@Transactional
@Service(Episode)
abstract class EpisodeService implements IEpisodeService{

    IReferredPatientsReportService referredPatientsReportService

    @Override
    List<Episode> getAllByClinicId(String clinicId, int offset, int max) {
        return Episode.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
    @Override
    List<Episode> getAllByIndentifier(String identifierId, int offset, int max) {
        def episodes = Episode.findAllWhere(patientServiceIdentifier: PatientServiceIdentifier.findById(identifierId))
        return episodes
    }

    @Override
    List<Episode> getEpisodeOfReferralOrBackReferral(Clinic clinic, ClinicalService clinicalService, String startStopReasonCode, Date startDate, Date endDate) {

        StartStopReason startStop = StartStopReason.findByCode(startStopReasonCode)
       // render Episode.findAllByStartStopReasonAndPatientServiceIdentifierInListAndEpisodeDateBetween(startStop,patients,startDate,endDate)
        return Episode.executeQuery("select ep from Episode ep " +
                "inner join ep.startStopReason stp " +
                "inner join ep.patientServiceIdentifier psi " +
                "inner join psi.patient p "+
                "inner join ep.clinic c " +
                "where c.id= ?0 and stp.id = ?1 and psi.service.id = ?2 and ep.episodeDate >= ?3 and ep.episodeDate <= ?4 and ep.episodeDate = (select max(ep2.episodeDate) " +
                "from Episode ep2 " +
                "inner join ep2.patientServiceIdentifier psi2 " +
                "inner join psi2.patient p2 " +
                "inner join ep2.clinic c2 " +
                "where p.id = p2.id and psi.id = psi2.id and c2.id = ?0 and ep2.episodeDate >= ?3 and ep2.episodeDate <= ?4) ", [clinic.id, startStop.id, clinicalService.id, startDate, endDate])
    }

    @Override
    Episode getEpisodeOfReferralByPatientServiceIdentfierAndBelowEpisodeDate(PatientServiceIdentifier patientServiceIdentifier, Date episodeDate) {
       // StartStopReason startStop = StartStopReason.findByCode("REFERIDO_PARA")

        def episodes = Episode.executeQuery("select ep from Episode ep " +
                "inner join ep.startStopReason stp " +
                "inner join ep.patientServiceIdentifier psi " +
                "inner join psi.patient p "+
                "inner join ep.clinic c " +
                "where psi = :patientServiceIdentifier and stp.code = 'REFERIDO_PARA' and ep.episodeDate <= :episodeDate order by ep.episodeDate desc", [patientServiceIdentifier:patientServiceIdentifier, episodeDate:episodeDate])

        return episodes.get(0)
    }

}
