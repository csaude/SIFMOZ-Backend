package mz.org.fgh.sifmoz.backend.duration

class Duration {

    String id
    int weeks
    String description

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
