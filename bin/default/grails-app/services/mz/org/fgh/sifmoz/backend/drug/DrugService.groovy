package mz.org.fgh.sifmoz.backend.drug

import grails.gorm.services.Service

@Service(Drug)
interface DrugService {

    Drug get(Serializable id)

    List<Drug> list(Map args)

    Long count()

    Drug delete(Serializable id)

    Drug save(Drug drug)
}
