package mz.org.fgh.sifmoz.backend.nationalClinic

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.facilityType.FacilityType

class NationalClinic {
    String id
    String code
    String facilityName
    String telephone
    FacilityType facilityType
    boolean active

    static belongsTo = [province: Province]
    static hasMany = [clinics: Clinic]
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        facilityName nullable: false, blank: false, unique: ['province']
        telephone nullable: true, matches: /\d+/, maxSize: 12, minSize: 9
    }
}
