package mz.org.fgh.sifmoz.backend.facilityType

import grails.rest.Resource

class FacilityType {
    String id
    String code
    String description

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
