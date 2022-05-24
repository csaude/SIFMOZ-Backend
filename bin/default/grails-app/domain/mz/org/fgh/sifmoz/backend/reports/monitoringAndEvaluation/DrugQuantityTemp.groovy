package mz.org.fgh.sifmoz.backend.reports.monitoringAndEvaluation

class DrugQuantityTemp {
    String id
    String drugName
    long quantity
    static belongsTo = ['arvDailyRegisterReportTemp': ArvDailyRegisterReportTemp]

    DrugQuantityTemp() {

    }

    DrugQuantityTemp(String drugName, long quantity) {
        this.drugName = drugName
        this.quantity = quantity
    }

    static constraints = {
        id generator: "uuid"
    }

    @Override
    public String toString() {
        return "DrugQuantityTemp{" +
                "id=" + id +
                ", version=" + version +
                ", drugName='" + drugName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
