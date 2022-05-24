package mz.org.fgh.sifmoz.backend.appointment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient

class Appointment {
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
}
