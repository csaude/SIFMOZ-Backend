package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import com.fasterxml.jackson.annotation.JsonBackReference
import grails.rest.Resource

class District {
    String id
    String code
    String description

    @JsonBackReference
    Province province

    static belongsTo = [Province]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: ['province']
        description nullable: false
    }
}
