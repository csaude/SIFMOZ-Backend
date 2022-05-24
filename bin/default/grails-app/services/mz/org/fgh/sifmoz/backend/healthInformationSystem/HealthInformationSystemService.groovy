package mz.org.fgh.sifmoz.backend.healthInformationSystem

import grails.gorm.services.Service

@Service(HealthInformationSystem)
interface HealthInformationSystemService {

    HealthInformationSystem get(Serializable id)

    List<HealthInformationSystem> list(Map args)

    Long count()

    HealthInformationSystem delete(Serializable id)

    HealthInformationSystem save(HealthInformationSystem healthInformationSystem)

}
