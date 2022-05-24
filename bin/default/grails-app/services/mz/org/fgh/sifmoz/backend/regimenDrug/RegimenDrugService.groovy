package mz.org.fgh.sifmoz.backend.regimenDrug

import grails.gorm.services.Service

@Service(RegimenDrug)
interface RegimenDrugService {

    RegimenDrug get(Serializable id)

    List<RegimenDrug> list(Map args)

    Long count()

    RegimenDrug delete(Serializable id)

    RegimenDrug save(RegimenDrug regimenDrug)

}
