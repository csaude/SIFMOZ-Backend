package mz.org.fgh.sifmoz.backend.screening

import com.fasterxml.jackson.annotation.JsonBackReference
import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class VitalSignsScreening {
    String id
    int distort;
    String imc;
    double weight;
    int systole;
    double height;

    @JsonBackReference
    PatientVisit visit

    static belongsTo = [PatientVisit]
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        distort (nullable: false)
    }

    @Override
    public String toString() {
        return "VitalSignsScreening{" +
                "distort=" + distort +
                ", imc='" + imc + '\'' +
                ", weight=" + weight +
                ", systole=" + systole +
                ", height=" + height +
                '}';
    }
}
