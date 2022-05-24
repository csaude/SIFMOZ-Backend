package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

class Localidade {
    String id
    String code
    String description

    static belongsTo = [postoAdministrativo: PostoAdministrativo]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
