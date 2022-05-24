package mz.org.fgh.sifmoz.backend.dispenseMode

class DispenseMode {
    String id
    String code
    String description
    String openmrsUuid

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
