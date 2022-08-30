package mz.org.fgh.sifmoz.backend.patient

import mz.org.fgh.sifmoz.backend.appointment.Appointment
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Localidade
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.PostoAdministrativo
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.group.GroupInfo
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.patientAttribute.PatientAttribute
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReference

class Patient extends BaseEntity {
    String id
    String firstNames
    String middleNames
    String lastNames
    String gender
    Date dateOfBirth
    String cellphone
    String alternativeCellphone
    String address
    String addressReference
    boolean accountstatus
    String hisUuid
    String hisLocation
    String hisLocationName
    HealthInformationSystem his
    Province province
    Localidade bairro
    District district
    PostoAdministrativo postoAdministrativo

    Clinic clinic
    static belongsTo = [Clinic]

    static hasMany = [
            attributes: PatientAttribute,
            identifiers: PatientServiceIdentifier,
            appointments: Appointment,
            groups: GroupInfo,
            patientVisits: PatientVisit,
            patientTransReference: PatientTransReference
    ]

    static mapping = {
        id generator: "uuid"
        patientVisits: lazy:true
    }

    static constraints = {
        //firstNames unique: ['middleNames', 'lastNames', 'gender', 'dateOfBirth', 'cellphone']
        dateOfBirth(nullable: true, blank: true, validator: { dateofbirth, urc ->
            return dateofbirth != null ? dateofbirth <= new Date() : null
        })
        middleNames nullable: true
        cellphone(nullable: true )
        alternativeCellphone(nullable: true, maxSize: 12, minSize: 9)
        address nullable: true, maxSize: 750
        addressReference nullable: true, maxSize: 750
        province nullable: false
        bairro nullable: true
        postoAdministrativo nullable: true
        attributes nullable: true
        identifiers nullable: true
        appointments nullable: true
        groups nullable: true
        clinic nullable: false
        patientVisits nullable: true
        hisUuid nullable: true
        his nullable: true
        hisLocation nullable: true
        hisLocationName nullable: true
    }

//    @Override
//    public String toString() {
//        return "Patient{" +
//                "appointments=" + appointments +
//                ", identifiers=" + identifiers +
//                ", patientVisits=" + patientVisits +
//                ", groups=" + groups +
//                ", attributes=" + attributes +
//                ", clinic=" + clinic +
//                ", id='" + id + '\'' +
//                ", firstNames='" + firstNames + '\'' +
//                ", middleNames='" + middleNames + '\'' +
//                ", lastNames='" + lastNames + '\'' +
//                ", gender='" + gender + '\'' +
//                ", dateOfBirth=" + dateOfBirth +
//                ", cellphone='" + cellphone + '\'' +
//                ", alternativeCellphone='" + alternativeCellphone + '\'' +
//                ", address='" + address + '\'' +
//                ", addressReference='" + addressReference + '\'' +
//                ", accountstatus=" + accountstatus +
//                ", province=" + province +
//                ", bairro=" + bairro +
//                ", district=" + district +
//                ", postoAdministrativo=" + postoAdministrativo +
//                '}';
//    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,dashboardMenuCode))
        }
        return menus
    }
}
