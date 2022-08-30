package mz.org.fgh.sifmoz.backend.stockentrance

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.stock.Stock

class StockEntrance extends BaseEntity {
    String id
    String orderNumber
    Date dateReceived
    Clinic clinic
    static hasMany = [stocks: Stock]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        orderNumber(nullable: false, blank: false, unique: ['dateReceived'])
        dateReceived(nullable: false, blank: false, validator: { dateReceived, urc ->
            return ((dateReceived <= new Date()))
        })
    }

    @Override
    public String toString() {
        return "StockEntrance{" +
                "stocks=" + stocks +
                ", id='" + id + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", dateReceived=" + dateReceived +
                ", clinic=" + clinic +
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
