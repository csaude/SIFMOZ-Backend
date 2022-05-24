package mz.org.fgh.sifmoz.backend.clinicSector

import com.fasterxml.jackson.annotation.JsonIgnore
import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic

class ClinicSector {
    String id
    String code
    String description
    boolean active

    @JsonIgnore
    Clinic clinic
    static belongsTo = [Clinic]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
