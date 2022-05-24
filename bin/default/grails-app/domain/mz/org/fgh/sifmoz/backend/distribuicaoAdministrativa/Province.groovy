package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference
import grails.rest.Resource

@JsonInclude(JsonInclude.Include.NON_NULL)
class Province {
    String id
    String code
    String description

    @JsonManagedReference
    static hasMany = [districts: District]
    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
