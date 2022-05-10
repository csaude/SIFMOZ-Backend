package mz.org.fgh.sifmoz.backend.reports.patients

class ActiveOrFaltosoPatientReport {
    String id
    String reportId
    String firstNames
    String middleNames
    String lastNames
    String gender
    String ageOnEndDate
    Date dateOfBirth
    String cellphone
    String alternativeCellphone
    String address
    String addressReference
    String provinceId
    String districtId
    String bairroId
    String postoAdministrativoId
    String pharmacyId //clinic
    String nid
    String reportType
    Date pickupDate
    Date nextPickUpDate
    String dispenseModeId
    String prescriptionId
    String therapeuticRegimen
    String dispenseType
    Date startDate // Report Parameter
    Date endDate // Report Parameter
    String periodType
    int year

    ActiveOrFaltosoPatientReport(){
    }

    ActiveOrFaltosoPatientReport(String reportId, String firstNames, String middleNames, String lastNames, String gender, String cellphone, String alternativeCellphone, String address, String addressReference,
                                 String provinceId, String districtId, String bairroId, String postoAdministrativoId, String pharmacyId, String dispenseModeId, String prescriptionId, String reportType) {
        this.reportId = reportId
        this.firstNames = firstNames
        this.middleNames = middleNames
        this.lastNames = lastNames
        this.gender = gender
        this.cellphone = cellphone
        this.alternativeCellphone = alternativeCellphone
        this.address = address
        this.addressReference = addressReference
        this.provinceId = provinceId
        this.districtId = districtId
        this.bairroId = bairroId
        this.postoAdministrativoId = postoAdministrativoId
        this.pharmacyId = pharmacyId
        this.dispenseModeId = dispenseModeId
        this.prescriptionId = prescriptionId
        this.reportType = reportType
    }
    static constraints = {
        id generator: "uuid"
        pharmacyId nullable: true
        provinceId nullable: true
        districtId nullable: true
        periodType nullable: false , inList: ['MONTH','QUARTER','SEMESTER','ANNUAL']
        reportType nullable: false , inList: ['ACTIVE_PATIENT','FALTOSO']
        startDate nullable: true
        endDate nullable: true
        ageOnEndDate nullable: true
        year nullable: true
    }

    static mapping = {
        id generator: "uuid"
    }


    @Override
    public String toString() {
        return "ActiveOrFaltosoPatientReport{" +
                " Id='" + id + '\'' +
                ", reportId='" + reportId + '\'' +
                ", firstNames='" + firstNames + '\'' +
                ", middleNames='" + middleNames + '\'' +
                ", lastNames='" + lastNames + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", cellphone='" + cellphone + '\'' +
                ", alternativeCellphone='" + alternativeCellphone + '\'' +
                ", address='" + address + '\'' +
                ", addressReference='" + addressReference + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", bairroId='" + bairroId + '\'' +
                ", postoAdministrativoId='" + postoAdministrativoId + '\'' +
                ", pharmacyId='" + pharmacyId + '\'' +
                ", nid='" + nid + '\'' +
                ", pickupDate=" + pickupDate +
                ", nextPickUpDate=" + nextPickUpDate +
                ", dispenseModeId='" + dispenseModeId + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", therapeuticRegimen='" + therapeuticRegimen + '\'' +
                ", dispenseType='" + dispenseType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", periodType='" + periodType + '\'' +
                '}';
    }
}
