package mz.org.fgh.sifmoz.backend.group

import mz.org.fgh.sifmoz.backend.duration.Duration

class GroupPackHeader {

    String id
    Date packDate
    Duration duration
    Date nextPickUpDate
    static belongsTo = [group: GroupInfo]

    static hasMany = [groupPacks: GroupPack]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }


    @Override
    public String toString() {
        return "GroupPackHeader{" +
                "group=" + group +
                ", groupPacks=" + groupPacks +
                ", id='" + id + '\'' +
                ", packDate=" + packDate +
                ", duration=" + duration +
                ", nextPickUpDate=" + nextPickUpDate +
                '}';
    }
}
