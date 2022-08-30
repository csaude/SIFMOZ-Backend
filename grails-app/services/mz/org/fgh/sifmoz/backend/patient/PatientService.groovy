package mz.org.fgh.sifmoz.backend.patient

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.utilities.Utilities

@Transactional
@Service(Patient)
abstract class PatientService implements IPatientService{

    @Override
    List<Patient> search(Patient patient) {
        String mainQuery =  "select p from Patient p " +
                            " where (p.firstNames like :firstNames OR" +
                            " p.middleNames like :middleNames OR " +
                            " p.lastNames like :lastNames) " +
                            " AND p.clinic =:clinic"
        String indentifierCondition = " OR EXISTS (select psi " +
                                "                   from PatientServiceIdentifier psi inner join psi.patient pt " +
                                "                   where pt.id = p.id and psi.value like :identifier) "
        String searchQuery = mainQuery + (Utilities.listHasElements(patient.identifiers as ArrayList<?>) ? indentifierCondition : "")

        searchQuery += " order by p.firstNames "

        Clinic clinic = Clinic.findById(patient.clinic.id)

        return Patient.executeQuery(searchQuery,
                                    [firstNames: "%${patient.firstNames}%",
                                     middleNames: "%${patient.middleNames}%",
                                     lastNames: "%${patient.lastNames}%",
                                     clinic: clinic,
                                     identifier: (Utilities.listHasElements(patient.identifiers as ArrayList<?>) ? "%${patient.identifiers.getAt(0).value}%" : ""), max: 400]
                                    )
        //return Patient.findAllByFirstNamesIlikeOrMiddleNamesIlikeOrLastNamesIlike("%${patient.firstNames}%", "%${patient.middleNames}%", "%${patient.lastNames}%")
    }

    @Override
    List<Patient> search(String searchString, String clinicId) {
        String mainQuery =  "select p from Patient p " +
                " where (p.firstNames like :searchString OR" +
                " p.middleNames like :searchString OR " +
                " p.lastNames like :searchString) " +
                " AND p.clinic =:clinic"
        String indentifierCondition = " OR EXISTS (select psi " +
                "                   from PatientServiceIdentifier psi inner join psi.patient pt " +
                "                   where pt.id = p.id and psi.value like :searchString) "
        String searchQuery = mainQuery + indentifierCondition

        searchQuery += " order by p.firstNames "

        Clinic clinic = Clinic.findById(clinicId)

        return Patient.executeQuery(searchQuery,
                [searchString: "%${searchString}%",
                 clinic: clinic, max: 400]
        )
    }

    @Override
    Long count(Patient patient) {
        return Patient.countByFirstNamesIlikeOrMiddleNamesIlikeOrLastNamesIlike("%${patient.firstNames}%", "%${patient.middleNames}%", "%${patient.lastNames}%")
    }

    @Override
    List<Patient> getAllByClinicId(String clinicId, int offset, int max) {
        return Patient.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
}
