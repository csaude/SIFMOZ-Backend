package mz.org.fgh.sifmoz.backend.patient

import grails.gorm.services.Service


interface IPatientService {

    Patient get(Serializable id)

    List<Patient> list(Map args)

    Long count()

    Patient delete(Serializable id)

    Patient save(Patient patient)

    List<Patient> search(Patient patient)

    List<Patient> getAllByClinicId(String clinicId, int offset, int max)

    Long count(Patient patient)

}
