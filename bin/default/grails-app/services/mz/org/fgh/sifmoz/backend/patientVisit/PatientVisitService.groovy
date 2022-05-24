package mz.org.fgh.sifmoz.backend.patientVisit

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.IPatientService
import mz.org.fgh.sifmoz.backend.patient.Patient

@Transactional
@Service(PatientVisit)
abstract class PatientVisitService implements IPatientVisitService{

    @Override
    List<PatientVisit> getAllByPatientId(String patientId) {
        return PatientVisit.findAllByPatient(Patient.findById(patientId))
    }

    @Override
    List<PatientVisit> getAllByClinicId(String clinicId, int offset, int max) {
        return PatientVisit.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }

    @Override
    PatientVisit getLastVisitOfPatient(String patientId) {
        List<PatientVisit> patientVisitList = PatientVisit.findAllByPatient(Patient.findById(patientId), [sort: ['visitDate': 'desc']])
        return patientVisitList.get(0)
    }
}
