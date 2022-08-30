package mz.org.fgh.sifmoz.backend.doctor

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.stock.Stock

class Doctor extends BaseEntity {
    String id
    String firstnames
    String lastname
    String gender
    Date dateofbirth
    String telephone
    String email
    @JsonIgnore
    Clinic clinic
    boolean active

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        firstnames nullable: false
        lastname nullable: false
        gender nullable: false
        dateofbirth(nullable: true, blank: true, validator: { dateofbirth, urc ->
            return dateofbirth != null ? dateofbirth <= new Date() : null
        })
        telephone(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        email nullable: true

    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,administrationMenuCode,homeMenuCode))
        }
        return menus
    }
}
