package mz.org.fgh.sifmoz.backend.prescriptionDetail

import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class PrescriptionDetail {
    String id
    String reasonForUpdate
    TherapeuticLine therapeuticLine
    TherapeuticRegimen therapeuticRegimen
    DispenseType dispenseType
    Prescription prescription
    static belongsTo = [Prescription]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        reasonForUpdate nullable: true
        therapeuticRegimen nullable: true
        therapeuticLine nullable: true
    }
}
