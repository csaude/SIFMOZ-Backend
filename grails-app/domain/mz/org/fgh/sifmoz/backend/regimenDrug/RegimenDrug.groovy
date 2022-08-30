package mz.org.fgh.sifmoz.backend.regimenDrug

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class RegimenDrug extends BaseEntity {
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

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList('01'))
        }
        return menus
    }
}
