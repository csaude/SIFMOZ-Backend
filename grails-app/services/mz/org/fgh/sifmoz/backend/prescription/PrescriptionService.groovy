package mz.org.fgh.sifmoz.backend.prescription

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.service.ClinicalService

@Transactional
@Service(Prescription)
abstract class PrescriptionService implements IPrescriptionService{
    @Override
    List<Prescription> getAllByClinicId(String clinicId, int offset, int max) {
        return Prescription.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
    @Override
    Prescription getByVisitIds(String pvdsId, int offset, int max) {
        // return Prescription.findAllByPatientVisitDetails(PatientVisitDetails.findById(pvdsId),[offset: offset, max: max])
        def prescription = Prescription.findByPatientVisitDetails(PatientVisitDetails.findById(pvdsId))
        System.out.println(prescription)
        return prescription
    }
    @Override
    Map<String ,Prescription> getLastPrescriptionsByClinicAndClinicalService(Clinic clinic, ClinicalService clinicalService) {
        List<Prescription> prescriptions = Prescription.executeQuery("select pk from Prescription pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.episode as ep " +
                "inner join ep.patientServiceIdentifier as psi " +
                "inner join psi.patient as p " +
                "inner join psi.service as s " +
                "inner join ep.clinic c " +
                "where c.id= ?0 and s.id = ?1 and pk.prescriptionDate = (select max(pk2.prescriptionDate) from Prescription pk2 " +
                "inner join pk2.patientVisitDetails as pvd2 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep2 " +
                "inner join ep2.patientServiceIdentifier as psi2 " +
                "inner join psi2.patient as p2 " +
                "inner join ep2.clinic c2 " +
                "inner join psi2.service as s2 " +
                "where p.id=p2.id and c2.id= ?0 and s2.id = ?1) order by pk.prescriptionDate desc ",[clinic.id, clinicalService.id])

        Map<String, Prescription> map = new HashMap<String, Prescription>()
        for  (Prescription prescription: prescriptions) {
            map.put(prescription.getPatientVisitDetails().getAt(0).patientVisit.patient.id, prescription)
        }

        return map
    }

    Map<String ,Prescription> getLastPrescriptionsByClinicAndClinicalServiceAndEndDate(Clinic clinic, ClinicalService clinicalService, Date endDate) {
        List<Prescription> prescriptions = Prescription.executeQuery("select pk from Prescription pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.episode as ep " +
                "inner join ep.patientServiceIdentifier as psi " +
                "inner join psi.patient as p " +
                "inner join psi.service as s " +
                "inner join ep.clinic c " +
                "where c.id= ?0 and s.id = ?1 and pk.prescriptionDate < ?2 and pk.prescriptionDate = (select max(pk2.prescriptionDate) from Prescription pk2 " +
                "inner join pk2.patientVisitDetails as pvd2 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep2 " +
                "inner join ep2.patientServiceIdentifier as psi2 " +
                "inner join psi2.patient as p2 " +
                "inner join ep2.clinic c2 " +
                "inner join psi2.service as s2 " +
                "where p.id=p2.id and c2.id= ?0 and s2.id = ?1 and pk2.prescriptionDate < ?2) order by pk.prescriptionDate desc ",[clinic.id, clinicalService.id,endDate])

        Map<String, Prescription> map = new HashMap<String, Prescription>()
        for  (Prescription prescription: prescriptions) {
            map.put(prescription.getPatientVisitDetails().getAt(0).patientVisit.patient.id, prescription)
        }

        return map
    }

}
