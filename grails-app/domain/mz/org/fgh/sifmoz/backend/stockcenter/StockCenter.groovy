package mz.org.fgh.sifmoz.backend.stockcenter

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.protection.Menu

class StockCenter extends BaseEntity {
    String id
    String name
    boolean prefered
    Clinic clinic
    String code

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        name(nullable: false, blank: false)
        code(nullable: false, blank: false, unique: true)
    }

    @Override
    public String toString() {
        return "StockCenter{" +
                "name='" + name + '\'' +
                ", prefered=" + prefered +
                '}';
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(stockMenuCode,homeMenuCode))
        }
        return menus
    }
}
