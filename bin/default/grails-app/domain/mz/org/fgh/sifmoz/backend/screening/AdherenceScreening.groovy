package mz.org.fgh.sifmoz.backend.screening

import com.fasterxml.jackson.annotation.JsonBackReference
import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class AdherenceScreening {
    String id
    boolean hasPatientCameCorrectDate
    int daysWithoutMedicine
    boolean patientForgotMedicine
    int lateDays
    String lateMotives

    @JsonBackReference
    PatientVisit visit

    static belongsTo = [PatientVisit]
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        lateMotives(nullable: true, maxSize: 50)
        daysWithoutMedicine(nullable: true,blank: true)
        lateDays(nullable: true, blank: true)
    }

    @Override
    public String toString() {
        return "AdherenceScreening{" +
                "hasPatientCameCorrectDate=" + hasPatientCameCorrectDate +
                ", daysWithoutMedicine=" + daysWithoutMedicine +
                ", patientForgotMedicine=" + patientForgotMedicine +
                ", lateDays=" + lateDays +
                ", lateMotives='" + lateMotives + '\'' +
                '}';
    }
}
