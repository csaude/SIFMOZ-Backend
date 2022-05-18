package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail

class MmiaRegimenSubReport {
    String id
    String reportId
    String code
    String regimen
    String line
    String lineCode
    int totalPatients
    int cumunitaryClinic

    MmiaRegimenSubReport() {

    }

    MmiaRegimenSubReport (PrescriptionDetail detail, String reportId, boolean isReferido) {
        this.reportId = reportId
        this.code = detail.getTherapeuticRegimen().getCode()
        this.regimen = detail.getTherapeuticRegimen().getDescription()
        this.lineCode = detail.getTherapeuticLine().getCode()
        this.line = detail.getTherapeuticLine().getDescription()
        isReferido ? addpatientDC() : addpatient()
    }
    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
    }

    def addpatient() {
        this.totalPatients ++
    }

    def addpatientDC() {
        this.cumunitaryClinic ++
    }
}
