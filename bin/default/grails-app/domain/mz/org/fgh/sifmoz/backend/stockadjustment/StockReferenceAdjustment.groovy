package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockdestruction.DestroyedStock
import mz.org.fgh.sifmoz.backend.stockrefered.ReferedStockMoviment

class StockReferenceAdjustment extends StockAdjustment{
    String id
    static hasOne = [reference: ReferedStockMoviment]

    StockReferenceAdjustment() {
    }

    StockReferenceAdjustment(ReferedStockMoviment reference, Stock adjustedStock) {
        super(adjustedStock)
        this.reference = reference
    }

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        reference nullable: false
    }
}
