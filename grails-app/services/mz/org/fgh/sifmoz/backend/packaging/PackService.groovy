package mz.org.fgh.sifmoz.backend.packaging

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.service.ClinicalService

@Transactional
@Service(Pack)
abstract class PackService implements IPackService{

    @Override
    int countPacksByDispenseTypeAndServiceOnPeriod(DispenseType dispenseType, ClinicalService service, Clinic clinic, Date startDate, Date endDate) {
        int value = 0
        def count =Pack.executeQuery("select count(*) " +
                "from Pack as pk " +
                "inner join pk.clinic as cl " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.prescription as pr " +
                "inner join pvd.episode as ep " +
                "inner join pr.prescriptionDetails as prd " +
                "inner join ep.patientServiceIdentifier as pid " +
                "inner join pid.service as svc " +
                "where pk.pickupDate >= :startDate " +
                "       and pk.pickupDate <= :endDate " +
                "       and cl.id = :clinic " +
                "       and prd.dispenseType.code = :serviceCode " +
                "       and ep.patientServiceIdentifier.service.code = :serviceCode ",
                [startDate:startDate, endDate:endDate, serviceCode: service.getCode(), clinic: clinic.getId()])
        value = Integer.valueOf(count.get(0).toString())
        return value
    }

    @Override
    List<Pack> getPacksByServiceOnPeriod(ClinicalService service, Clinic clinic, Date startDate, Date endDate) {
        List<Pack> packList = Pack.executeQuery("select pk " +
                "from Pack as pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as rsn " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.prescription as pr " +
                "inner join pr.prescriptionDetails as prd " +
                "inner join pv.patient as pt " +
                "inner join pt.identifiers as pid " +
                "inner join pid.service as svc " +
                "where pk.pickupDate >= :startDate " +
                "       and pk.pickupDate <= :endDate " +
                "       and pk.clinic= :clinic " +
                "       and ep.patientServiceIdentifier.service.code = :serviceCode ",
                [startDate:startDate, endDate:endDate, serviceCode: service.getCode(), clinic: clinic])
        return packList
    }

    @Override
    int countPacksByServiceOnPeriod(ClinicalService service, Clinic clinic, Date startDate, Date endDate) {
        int value = 0
        def count = Pack.executeQuery("select count(*) " +
                "from Pack as pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as rsn " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.prescription as pr " +
                "inner join pr.prescriptionDetails as prd " +
                "inner join pv.patient as pt " +
                "inner join pt.identifiers as pid " +
                "inner join pid.service as svc " +
                "where pk.pickupDate >= :startDate " +
                "       and pk.pickupDate <= :endDate " +
                "       and pk.clinic = :clinic " +
                "       and ep.patientServiceIdentifier.service.code = :serviceCode ",
                [startDate:startDate, endDate:endDate, serviceCode: service.getCode(), clinic: clinic])
        value = Integer.valueOf(count.get(0).toString())
        return value
    }
}
