package mz.org.fgh.sifmoz.backend.interoperabilityType

class InteroperabilityType {

    String code
    String description

    static mapping = {
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
