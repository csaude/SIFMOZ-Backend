package mz.org.fgh.sifmoz.backend.appointment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.protection.Menu

class Appointment extends BaseEntity{
    String id
    Date appointmentDate
    Date visitDate
    Clinic clinic

    static belongsTo = [patient: Patient]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        appointmentDate(nullable: true, blank: true, validator: { appointmentDate, urc ->
            return appointmentDate > new Date()
        })
        visitDate(nullable: true, blank: true, validator: { visitDate, urc ->
            return visitDate <= new Date()
        })
    }

    @Override
    List<Menu> hasMenus() {

        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode))
        }
        return menus
    }
}
