package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockinventory.Inventory

class InventoryStockAdjustment extends StockAdjustment{
    String id
    static hasOne = [inventory: Inventory]

    static mapping = {
        id generator: "uuid"
    }

    InventoryStockAdjustment() {
    }

    InventoryStockAdjustment(Inventory inventory, Stock adjustedStock) {
        super(adjustedStock)
        this.inventory = inventory
    }

    static constraints = {
    }
}
