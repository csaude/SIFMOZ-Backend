package mz.org.fgh.sifmoz.backend.groupMember


import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.group.GroupInfo
import mz.org.fgh.sifmoz.backend.group.GroupPackHeader
import mz.org.fgh.sifmoz.backend.patient.Patient

class GroupMember {
    String id
    Date startDate
    Date endDate
    GroupInfo group
    Clinic clinic

    static belongsTo = [patient: Patient]
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        patient nullable: false, unique: ['group','endDate']
        startDate(nullable: true, blank: true, validator: { startDate, urc ->
            return startDate != null ? startDate <= new Date() : null
        })
        endDate nullable: true
    }
}
