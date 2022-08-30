package mz.org.fgh.sifmoz.backend.group

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.protection.Menu

class GroupPack extends BaseEntity {
    String id
    Pack pack
    static hasOne = [Pack]
    static belongsTo = [header: GroupPackHeader]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
    }

    @Override
    public String toString() {
        return "GroupPack{" +
                "id='" + id + '\'' +
                ", pack=" + pack +
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
