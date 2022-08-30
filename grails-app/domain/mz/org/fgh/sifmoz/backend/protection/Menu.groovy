package mz.org.fgh.sifmoz.backend.protection

import mz.org.fgh.sifmoz.backend.base.BaseEntity


class Menu extends BaseEntity{

    String id
    String code
    String description

    Menu(String code,String description){
       // this()
        this.code = code
        this.description = description
    }

    static constraints = {
        code nullable: false, blank: false, unique: true
        description nullable: false, blank: false, unique: true
    }

    static mapping = {
        id generator: "uuid"
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(administrationMenuCode,homeMenuCode))
        }
        return menus
    }
}
