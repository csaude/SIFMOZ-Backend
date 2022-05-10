package mz.org.fgh.sifmoz.backend.stock

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.stockadjustment.StockAdjustment
import mz.org.fgh.sifmoz.backend.stockcenter.StockCenter
import mz.org.fgh.sifmoz.backend.stockentrance.StockEntrance

class Stock {
    String id
    Date expireDate;
    boolean modified;
    String shelfNumber
    int unitsReceived
    int stockMoviment
    String manufacture
    String batchNumber
    boolean hasUnitsRemaining
    Drug drug
    StockCenter center
    Clinic clinic
    static hasMany = [packagedDrugs: PackagedDrug, adjustments: StockAdjustment]
    static belongsTo = [entrance: StockEntrance]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        expireDate(nullable: false, blank: false)
        batchNumber(nullable: false, blank: false, unique: true)
        shelfNumber(nullable: true, maxSize: 10)
        unitsReceived(min: 1)
        stockMoviment(min: 0)
        manufacture(nullable: true, maxSize: 20)
        packagedDrugs nullable: true
        adjustments nullable: true
    }
}
