package mz.org.fgh.sifmoz.backend.packagedDrug

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.stock.Stock

class PackagedDrug extends BaseEntity {
    String id
    @JsonManagedReference
    Drug drug
    int quantitySupplied
    Date nextPickUpDate
    boolean toContinue
    Date creationDate
    Pack pack
    static belongsTo = [Pack]

    static hasMany = [packagedDrugStocks: PackagedDrugStock]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        quantitySupplied(min: 0)
        nextPickUpDate nullable: true
        creationDate nullable: true
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
