package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

class PostoAdministrativo {
    String id
    String code
    String description
    static belongsTo = [district: District]
    static hasMany = [localidades: Localidade]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
