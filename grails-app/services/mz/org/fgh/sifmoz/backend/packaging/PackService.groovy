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

    //Query Historico de Levantamento de Pacientes Referidos
    @Override
    List<Pack> getPacksOfReferredPatientsByClinicalServiceAndClinicOnPeriod(ClinicalService clinicalService,Clinic clinic, Date startDate, Date endDate) {

        return Pack.executeQuery("select pk from Pack pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as stp " +
                "inner join ep.patientServiceIdentifier as psi " +
                "inner join psi.patient as p " +
                "inner join psi.service as s " +
                "inner join ep.clinic c " +
                "where s.code = :serviceCode and c.id = :clinicId and pk.pickupDate >= :startDate and pk.pickupDate <= :endDate " +
                "and psi.id in (select psi2.id from Episode ep2 " +
                "inner join ep2.patientServiceIdentifier as psi2 " +
                "inner join ep2.startStopReason as stp2 " +
                "inner join psi2.service as s2 " +
                "where stp.code = 'REFERIDO_PARA' and s2.code = :serviceCode and ep2.episodeDate >= :startDate and ep2.episodeDate <= :endDate)",
                [serviceCode:clinicalService.code,clinicId:clinic.id,startDate:startDate,endDate:endDate])
    }

    List getAbsentReferredPatientsByClinicalServiceAndClinicOnPeriod(ClinicalService clinicalService, Clinic clinic, Date startDate, Date endDate){

        def list = Pack.executeQuery("select ep as episode," +
                "pk.nextPickUpDate as dateMissedPickUp, " +
                "p.cellphone as contact, " +
                "(select pk4.pickupDate from Pack pk4 " +
                "inner join pk4.patientVisitDetails as pvd2 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 " +
                "inner join ep3.patientServiceIdentifier as psi3 " +
                "inner join psi3.service as s3 " +
                "where psi.patient = psi3.patient and s3.code = :serviceCode and pk4.pickupDate > pk.nextPickUpDate and pk4.pickupDate <= :endDate) as returnedPickUp " +
                "from Pack pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as stp " +
                "inner join ep.patientServiceIdentifier as psi " +
                "inner join psi.patient as p " +
                "inner join psi.service as s " +
                "inner join ep.clinic c " +
                "where s.code = :serviceCode and c.id = :clinicId and pk.nextPickUpDate >= :startDate and pk.nextPickUpDate <= :endDate and DATE(pk.nextPickUpDate) + :days <= :endDate " +
                "and psi.id in (select psi2.id from Episode ep2 " +
                "inner join ep2.patientServiceIdentifier as psi2 " +
                "inner join ep2.startStopReason as stp2 " +
                "inner join psi2.service as s2 " +
                "where stp.code = 'REFERIDO_PARA' and s2.code = :serviceCode and ep2.episodeDate >= :startDate and ep2.episodeDate <= :endDate) " +
                "and pk.nextPickUpDate in (select max(pk2.nextPickUpDate) from Pack pk2 " +
                "inner join pk2.patientVisitDetails as pvd2 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 " +
                "inner join ep3.patientServiceIdentifier as psi3 " +
                "inner join psi3.service as s3 " +
                "where psi.patient = psi3.patient and s3.code = :serviceCode and pk2.nextPickUpDate  >= :startDate and pk2.nextPickUpDate <= :endDate)",
                [serviceCode:clinicalService.code,clinicId:clinic.id,startDate:startDate,endDate:endDate,days: 3])

       return list
    }
}
