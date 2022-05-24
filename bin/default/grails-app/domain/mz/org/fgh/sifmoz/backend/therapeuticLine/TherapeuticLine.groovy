package mz.org.fgh.sifmoz.backend.therapeuticLine

class TherapeuticLine {
    String id
    String code
    String description
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
