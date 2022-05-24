package mz.org.fgh.sifmoz.backend.packagedDrug

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.stock.Stock

class PackagedDrug {
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
        quantitySupplied(min: 1)
        nextPickUpDate nullable: true
        creationDate nullable: true
    }
}
