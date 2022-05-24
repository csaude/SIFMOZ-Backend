package mz.org.fgh.sifmoz.backend.identifierType

class IdentifierType {
    String id
    String code
    String description
    String pattern

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        pattern nullable: true
    }
}
