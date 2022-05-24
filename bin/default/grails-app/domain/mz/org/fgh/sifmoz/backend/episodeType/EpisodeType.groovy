package mz.org.fgh.sifmoz.backend.episodeType

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic

class EpisodeType {
    String id
    String code
    String description

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false
        description nullable: false
    }
}
