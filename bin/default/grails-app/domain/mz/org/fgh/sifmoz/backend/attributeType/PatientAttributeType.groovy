package mz.org.fgh.sifmoz.backend.attributeType

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic

class PatientAttributeType {
    String id
    String code
    String name
    String description
    String datatype

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        name nullable: false, unique: true
        description nullable: true, blank: true
        datatype nullable: true, blank: true
    }
}
