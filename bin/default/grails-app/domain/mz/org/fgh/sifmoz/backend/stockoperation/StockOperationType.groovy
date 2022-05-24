package mz.org.fgh.sifmoz.backend.stockoperation

class StockOperationType {
    String id
    String description
    String code

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        description(nullable: false, maxSize: 50, blank: false)
        code(nullable: false, maxSize: 50, blank: false, unique: true)
    }

    @Override
    public String toString() {
        return "StockOperationType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
