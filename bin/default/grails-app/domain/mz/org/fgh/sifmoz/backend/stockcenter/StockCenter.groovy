package mz.org.fgh.sifmoz.backend.stockcenter

import mz.org.fgh.sifmoz.backend.clinic.Clinic

class StockCenter {
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
}
