package mz.org.fgh.sifmoz.backend.stocklevel

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.stock.Stock

class StockLevel {
    String id
    int bacth
    int fullContainerRemaining
    int loosePillsRemaining
    Clinic clinic
    Stock stock

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }

    @Override
    public String toString() {
        return "StockLevel{" +
                "bacth=" + bacth +
                ", fullContainerRemaining=" + fullContainerRemaining +
                ", loosePillsRemaining=" + loosePillsRemaining +
                '}';
    }
}
