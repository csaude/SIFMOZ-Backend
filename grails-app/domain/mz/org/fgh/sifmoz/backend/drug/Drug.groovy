package mz.org.fgh.sifmoz.backend.drug

import com.fasterxml.jackson.annotation.JsonBackReference
import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.form.Form
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class Drug extends BaseEntity {
    String id
    int packSize
    String name
    double defaultTreatment // numero de toma
    int defaultTimes// numero de vezes a tomar
    String defaultPeriodTreatment //  periofo a tomar --commbo (dia , semana , mes, ano)
    String fnmCode
    String uuidOpenmrs
    ClinicalService clinicalService
    Form form
    static belongsTo = [Form, TherapeuticRegimen]
    boolean active
    static hasMany = [stockList: Stock, therapeuticRegimenList: TherapeuticRegimen]
    static mapping = {
        id generator: "assigned"
        form lazy: true
    }
    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        name nullable: false, blank: false
        fnmCode nullable: false, unique: true
        packSize(min: 0)
        uuidOpenmrs nullable: true
        clinicalService nullable: false
     //   defaultTreatment(min: 1.00)
        defaultTimes(min:1)
        stockList nullable: true
        therapeuticRegimenList nullable: true
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,stockMenuCode,dashboardMenuCode,administrationMenuCode,homeMenuCode))
        }
        return menus
    }

}
