package mz.org.fgh.sifmoz.backend.clinic

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.nationalClinic.NationalClinic

@JsonInclude(JsonInclude.Include.NON_NULL)
class Clinic {
    String id
    String code
    String notes
    String telephone
    String clinicName
    @JsonIgnore
    Province province
    @JsonIgnore
    District district
    boolean mainClinic
    boolean active
    String uuid = UUID.randomUUID().toString()

    static belongsTo = [nationalClinic: NationalClinic]
    static hasMany = [sectors: ClinicSector]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false
        notes nullable: true, blank: true
        district nullable: true
        telephone nullable: true, matches: /\d+/, maxSize: 12, minSize: 9
        clinicName nullable: false, unique: ['province']
        sectors nullable: true
        nationalClinic nullable: true
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Clinic clinic = (Clinic) o

        if (clinicName != clinic.clinicName) return false
        if (code != clinic.code) return false
        if (id != clinic.id) return false

        return true
    }

    int hashCode() {
        int result
        result = id.hashCode()
        result = 31 * result + code.hashCode()
        result = 31 * result + clinicName.hashCode()
        return result
    }
}
