package mz.org.fgh.sifmoz.backend.packagedDrug

import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.stock.Stock

class PackagedDrugStock {
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
}
