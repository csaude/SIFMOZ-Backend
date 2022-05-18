package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.historicoLevantamento

class HistoricoLevantamentoReport {
    String id
    String reportId
    String clinicId
    String periodType
    int period
    int year
    Date startDate
    Date endDate

    String nid
    String firstNames
    String middleNames
    String lastNames
    String cellphone
    String tipoTarv
    Date pickUpDate
    Date nexPickUpDate
    String startReason
    String therapeuticalRegimen
    String dispenseType
    String age
    String dispenseMode
    String clinicalService
    String clinic

    public HistoricoLevantamentoReport(){

    }

    HistoricoLevantamentoReport(String nid, String firstNames, String middleNames, String lastNames, String cellphone, String tipoTarv, String startReason, String therapeuticalRegimen, String dispenseType, String dispenseMode, String clinicalService) {
        this.nid = nid
        this.firstNames = firstNames
        this.middleNames = middleNames
        this.lastNames = lastNames
        this.cellphone = cellphone
        this.tipoTarv = tipoTarv
        this.startReason = startReason
        this.therapeuticalRegimen = therapeuticalRegimen
        this.dispenseType = dispenseType
        this.dispenseMode = dispenseMode
        this.clinicalService = clinicalService
    }
    static constraints = {
        id generator: "uuid"
        periodType nullable: false , inList: ['MONTH','QUARTER','SEMESTER','ANNUAL']
        startDate nullable: true
        endDate nullable: true
    }


    @Override
    public String toString() {
        return "HistoricoLevantamentoReport{" +
                " id='" + id + '\'' +
                ", reportId='" + reportId + '\'' +
                ", clinicId='" + clinicId + '\'' +
                ", periodType='" + periodType + '\'' +
                ", period=" + period +
                ", year=" + year +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", nid='" + nid + '\'' +
                ", firstNames='" + firstNames + '\'' +
                ", middleNames='" + middleNames + '\'' +
                ", lastNames='" + lastNames + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", tipoTarv='" + tipoTarv + '\'' +
                ", pickUpDate=" + pickUpDate +
                ", nexPickUpDate=" + nexPickUpDate +
                ", startReason='" + startReason + '\'' +
                ", therapeuticalRegimen='" + therapeuticalRegimen + '\'' +
                ", dispenseType='" + dispenseType + '\'' +
                ", age='" + age + '\'' +
                ", dispenseMode='" + dispenseMode + '\'' +
                ", clinicalService='" + clinicalService + '\'' +
                ", clinic='" + clinic + '\'' +
                '}';
    }
}
