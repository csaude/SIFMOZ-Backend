package mz.org.fgh.sifmoz.backend.packagedDrug

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.stock.Stock

class PackagedDrugStock extends BaseEntity {
    String id
    Drug drug
    int quantitySupplied
    Date creationDate
    Stock stock
    static belongsTo = [packagedDrug: PackagedDrug]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,stockMenuCode,dashboardMenuCode))
        }
        return menus
    }
}
