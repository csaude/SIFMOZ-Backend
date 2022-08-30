package mz.org.fgh.sifmoz.backend.clinic

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.facilityType.FacilityType
import mz.org.fgh.sifmoz.backend.nationalClinic.NationalClinic
import mz.org.fgh.sifmoz.backend.protection.Menu

class Clinic extends BaseEntity {
    String id
    String code
    String notes
    String telephone
    String clinicName
    Province province
    District district
    FacilityType facilityType
    boolean mainClinic
    boolean active
    String uuid
    // Long matchId

    static belongsTo = [nationalClinic: NationalClinic]
    static hasMany = [sectors: ClinicSector]

    static mapping = {
        id generator: "assigned"
    }
    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false
        notes nullable: true, blank: true
        telephone nullable: true, matches: /\d+/, maxSize: 12, minSize: 9
        clinicName nullable: false, unique: ['province','district']
        sectors nullable: true
        nationalClinic nullable: true
        uuid unique: true
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Clinic clinic = (Clinic) o

        if (clinicName != clinic.clinicName) return false
        if (code != clinic.code) return false
        if (id != clinic.id) return false

        return true
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
           // menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,stockMenuCode,dashboardMenuCode,reportsMenuCode,administrationMenuCode))
        }
        return menus
    }
}
