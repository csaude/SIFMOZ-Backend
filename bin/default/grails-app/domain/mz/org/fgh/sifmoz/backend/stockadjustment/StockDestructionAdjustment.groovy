package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockdestruction.DestroyedStock

class StockDestructionAdjustment extends StockAdjustment{
    String id
    static hasOne = [destruction: DestroyedStock]

    StockDestructionAdjustment() {
    }

    StockDestructionAdjustment(DestroyedStock destruction, Stock adjustedStock) {
        super(adjustedStock)
        this.destruction = destruction
    }

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
