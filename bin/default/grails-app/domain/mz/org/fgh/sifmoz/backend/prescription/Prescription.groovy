package mz.org.fgh.sifmoz.backend.prescription

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.duration.Duration
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.prescriptionDrug.PrescribedDrug
import mz.org.fgh.sifmoz.backend.utilities.Utilities

class Prescription {

    String id
    Date prescriptionDate
    Date expiryDate
    boolean current
    String notes
    String prescriptionSeq
    String patientType
    String patientStatus
    boolean modified
    Clinic clinic
    Doctor doctor
    Duration duration

    static hasMany = [prescribedDrugs: PrescribedDrug, prescriptionDetails: PrescriptionDetail, patientVisitDetails: PatientVisitDetails]

    static mapping = {
        id generator: "uuid"
        patientVisitDetails lazy: true
        prescribedDrugs lazy: true
        prescriptionDetails lazy: true
    }
    static constraints = {
        prescriptionDate(nullable: false, blank: false,  validator: { prescriptionDate, urc ->
            return ((prescriptionDate <= new Date()))})
        expiryDate(nullable: true, blank: true)
        notes(nullable: true, maxSize: 500)
        prescriptionSeq(nullable: true)
        duration(nullable: false, blank: false)
        patientType nullable: true
    }

    public void generateNextSeq() {
        long lastSeq = 0;
        try{
            if (Utilities.stringHasValue(this.prescriptionSeq)){
                lastSeq = Long.parseLong(this.prescriptionSeq);
            }else {
                lastSeq = 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        setPrescriptionSeq(String.valueOf(Utilities.garantirXCaracterOnNumber(lastSeq+1, 4)));
    }
}
