package mz.org.fgh.sifmoz.backend.therapeuticRegimen

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class TherapeuticRegimen extends BaseEntity {

    String id
    String regimenScheme
    boolean active
    String code
    String description
    String openmrsUuid
    @JsonBackReference
    ClinicalService clincalService
    static belongsTo = [ClinicalService]
    @JsonIgnore
    static hasMany = [drugs: Drug]

    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        regimenScheme nullable: false
        description nullable: true
        clincalService nullable: true
        openmrsUuid nullable: true
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,dashboardMenuCode,administrationMenuCode,homeMenuCode))
        }
        return menus
    }
}
