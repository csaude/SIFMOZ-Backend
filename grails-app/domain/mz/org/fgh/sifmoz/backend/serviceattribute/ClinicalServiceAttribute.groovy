package mz.org.fgh.sifmoz.backend.serviceattribute

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.serviceattributetype.ClinicalServiceAttributeType

class ClinicalServiceAttribute extends BaseEntity {
    String id
    @JsonManagedReference
    ClinicalServiceAttributeType clinicalServiceAttributeType

    @JsonIgnore
    ClinicalService clinicalService
    static belongsTo = [ClinicalService]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
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
