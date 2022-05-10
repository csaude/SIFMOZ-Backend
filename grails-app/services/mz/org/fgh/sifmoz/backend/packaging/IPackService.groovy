package mz.org.fgh.sifmoz.backend.packaging

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.service.ClinicalService


interface IPackService {
    Pack get(Serializable id)

    List<Pack> list(Map args)

    Long count()

    Pack delete(Serializable id)

    Pack save(Pack pack)

    int countPacksByDispenseTypeAndServiceOnPeriod(DispenseType dispenseType, ClinicalService service, Clinic clinic, Date startDate, Date endDate)

    List<Pack> getPacksByServiceOnPeriod(ClinicalService service, Clinic clinic, Date startDate, Date endDate)

    int countPacksByServiceOnPeriod(ClinicalService service, Clinic clinic, Date startDate, Date endDate)

}
