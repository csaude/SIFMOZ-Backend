package mz.org.fgh.sifmoz.backend.drug

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.stock.Stock


interface IDrugService {

    Drug get(Serializable id)

    List<Drug> list(Map args)

    Long count()

    Drug delete(Serializable id)

    Drug save(Drug drug)
}
