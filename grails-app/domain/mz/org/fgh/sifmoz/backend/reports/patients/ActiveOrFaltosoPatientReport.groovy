package mz.org.fgh.sifmoz.backend.reports.patients

class ActiveOrFaltosoPatientReport {
    String id
    String reportId
    String periodType
    int year
    Date startDate
    Date endDate

    String firstNames
    String middleNames
    String lastNames
    String gender
    String ageOnEndDate
    Date dateOfBirth
    String cellphone
    String province
    String district
    String clinic // Report Parameter
    String nid
    String reportType
    Date pickupDate
    Date nextPickUpDate
    String therapeuticRegimen
    String therapeuticLine
    String dispenseType
    String patientType

    ActiveOrFaltosoPatientReport() {
    }

    ActiveOrFaltosoPatientReport(String reportId, String firstNames, String middleNames, String lastNames, String gender, String cellphone, String therapeuticRegimen,  String therapeuticLine, String reportType) {
        this.reportId = reportId
        this.firstNames = firstNames
        this.middleNames = middleNames
        this.lastNames = lastNames
        this.gender = gender
        this.cellphone = cellphone
        this.reportType = reportType
        this.therapeuticRegimen = therapeuticRegimen
        this.therapeuticLine = therapeuticLine
    }
    static constraints = {
        id generator: "uuid"
        clinic nullable: true
        province nullable: true
        district nullable: true
        periodType nullable: false, inList: ['MONTH', 'QUARTER', 'SEMESTER', 'ANNUAL']
        reportType nullable: false, inList: ['ACTIVE_PATIENT', 'FALTOSO']
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
                ", provinceId='" + province + '\'' +
                ", districtId='" + district + '\'' +
                ", clinic='" + clinic + '\'' +
                ", nid='" + nid + '\'' +
                ", pickupDate=" + pickupDate +
                ", nextPickUpDate=" + nextPickUpDate +
                ", therapeuticRegimen='" + therapeuticRegimen + '\'' +
                ", dispenseType='" + dispenseType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", periodType='" + periodType + '\'' +
                '}';
    }
}
