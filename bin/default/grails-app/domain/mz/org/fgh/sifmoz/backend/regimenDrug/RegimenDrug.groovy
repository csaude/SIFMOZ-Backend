package mz.org.fgh.sifmoz.backend.regimenDrug

import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class RegimenDrug {
    String id
  //  double amPerTime
   // int timesPerDay
   // boolean modified
   // String notes
    // static belongsTo = [drug: Drug,therapeuticRegimen:TherapeuticRegimen]
 //   Drug drug
  //  TherapeuticRegimen therapeuticRegimen

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
