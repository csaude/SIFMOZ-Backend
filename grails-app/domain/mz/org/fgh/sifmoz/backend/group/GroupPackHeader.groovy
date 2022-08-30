package mz.org.fgh.sifmoz.backend.group

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.duration.Duration
import mz.org.fgh.sifmoz.backend.protection.Menu

class GroupPackHeader extends BaseEntity {

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

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(groupsMenuCode))
        }
        return menus
    }
}
