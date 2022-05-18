package mz.org.fgh.sifmoz.backend.episode

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.service.ClinicalService

interface IEpisodeService {

    Episode get(Serializable id)

    List<Episode> list(Map args)

    Long count()

    Episode delete(Serializable id)

    Episode save(Episode episode)

    List<Episode> getAllByClinicId(String clinicId, int offset, int max)

    List<Episode> getAllByIndentifier(String identifierId, int offset, int max)

    List<Episode> getEpisodeOfReferralOrBackReferral(Clinic clinic, ClinicalService clinicalServiceId, String startStopReasonCode, Date startDate, Date endDate)

    Episode getEpisodeOfReferralByPatientServiceIdentfierAndBelowEpisodeDate(PatientServiceIdentifier patientServiceIdentifier, Date episodeDate)
}
