package mz.org.fgh.sifmoz.backend.groupType

import grails.rest.Resource

class GroupType {
    String id
    String code
    String description

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }


    @Override
    public String toString() {
        return "GroupType{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
