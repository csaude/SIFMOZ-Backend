package mz.org.fgh.sifmoz.backend.healthInformationSystem

import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.interoperabilityAttribute.InteroperabilityAttribute

class HealthInformationSystem {

    String id
    String abbreviation
    String description
    boolean active

    @JsonManagedReference
    static hasMany = [interoperabilityAttributes: InteroperabilityAttribute]

    static mapping = {
        id generator: "uuid"
        interoperabilityAttributes (cascade: "all-delete-orphan")
    }
    static constraints = {
        abbreviation nullable: false, unique: true
        description nullable: false
    }
}
