package mz.org.fgh.sifmoz.backend.patientVisit

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.screening.AdherenceScreening
import mz.org.fgh.sifmoz.backend.screening.PregnancyScreening
import mz.org.fgh.sifmoz.backend.screening.RAMScreening
import mz.org.fgh.sifmoz.backend.screening.TBScreening
import mz.org.fgh.sifmoz.backend.screening.VitalSignsScreening
import org.springframework.cglib.proxy.Mixin

class PatientVisit extends BaseEntity {
    String id
    Date visitDate
    @JsonIgnore
    Clinic clinic

    @JsonIgnore
    Patient patient
    static belongsTo = [Patient]

    @JsonManagedReference
    static hasMany = [patientVisitDetails: PatientVisitDetails, adherenceScreening: AdherenceScreening, vitalSigns: VitalSignsScreening,
                      pregnancyScreening : PregnancyScreening, tbScreening: TBScreening, ramScreening: RAMScreening]


    static mapping = {
        id generator: "uuid"
    }



    static constraints = {
        patientVisitDetails nullable: true
        adherenceScreening nullable: true
        vitalSigns nullable: true
        pregnancyScreening nullable: true
        tbScreening nullable: true
        ramScreening nullable: true
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,dashboardMenuCode))
        }
        return menus
    }
}
