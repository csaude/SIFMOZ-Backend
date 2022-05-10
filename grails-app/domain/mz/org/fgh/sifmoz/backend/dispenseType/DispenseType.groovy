package mz.org.fgh.sifmoz.backend.dispenseType

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic

class DispenseType {
    public static final String DM = "DM"
    public static final String DT = "DT"
    public static final String DS = "DS"
    public static final String DA = "DA"
    String id
    String code
    String description

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false, blank: false

    }

    boolean isDM () {
        return this.code.equals(DM)
    }

    boolean isDT () {
        return this.code.equals(DT)
    }

    boolean isDS () {
        return this.code.equals(DS)
    }

    boolean isDA () {
        return this.code.equals(DA)
    }
}
