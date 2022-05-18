package mz.org.fgh.sifmoz.backend.reports.patients

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.dispenseMode.DispenseMode
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Localidade
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.PostoAdministrativo
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import org.hibernate.query.Query

import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.time.LocalDate

@Transactional
@Service(ActiveOrFaltosoPatientReport)
abstract class ActiveOrFaltososPatientsReportService implements IActiveOrFaltososPatientsReportService {

    @Override
    ActiveOrFaltosoPatientReport get(Serializable id) {
        return ActiveOrFaltosoPatientReport.findById(id as String)
    }

    @Override
    List<ActiveOrFaltosoPatientReport> list(Map args) {
        return null
    }

    @Override
    Long count() {
        return null
    }

    @Override
    ActiveOrFaltosoPatientReport delete(Serializable id) {
        return null
    }

    @Override
    void doSave(List<ActiveOrFaltosoPatientReport> patients) {
        System.out.println(patients.size())
        for (patient in patients) {
            save(patient)
        }
//        for (patient in patients) {
//            System.out.println(patient.save().toString())
//        }
    }

    @Override
    List<ActiveOrFaltosoPatientReport> getReportDataByReportId(String reportId) {
        return ActiveOrFaltosoPatientReport.findAllByReportId(reportId)
    }

    @Override
    List<ActiveOrFaltosoPatientReport> processamentoDados(ReportSearchParams reportSearchParams) {
        reportSearchParams.setReportType("ACTIVE_PATIENT")
        Prescription prescription
        PrescriptionDetail prescriptionDetail
        Date dateOfBirth
        Date pickupDate
        Date nextPickUpDate
        ActiveOrFaltosoPatientReport activeOrFaltosoPatientReport

        List<ActiveOrFaltosoPatientReport> resultList = new ArrayList<>()
        Date startDate = reportSearchParams.getStartDate()
        Date endDate = reportSearchParams.getEndDate()
        String clinic_id = reportSearchParams.getClinicId()
        String reportId = reportSearchParams.getId()
        String province_id = reportSearchParams.getProvinceId()
        String periodType = reportSearchParams.getPeriodType()
        int year = reportSearchParams.getYear()
        String reportType = reportSearchParams.getReportType()
        String clinicalService = reportSearchParams.getClinicalService()

        // subtract days to date
        if (reportSearchParams.getReportType().contains("ACTIVE_PATIENT")) {
            endDate = ConvertDateUtils.addDaysDate(endDate, -33)
        }
        if (reportSearchParams.getReportType().contains("FALTOSO")) {
            endDate = ConvertDateUtils.addDaysDate(endDate, -3)
        }
        System.out.println(clinic_id + " - " + clinicalService + " - " + endDate + " - " + province_id)
//        def result = Patient.executeQuery(
//                "select p.firstNames as firstNames, p.middleNames as middleNames, p.lastNames as lastNames " +
//                        ", p.gender as gender, p.dateOfBirth as dateOfBirth, p.cellphone as cellPhone, p.alternativeCellphone as alternativeCellphone, p.address as address, p.addressReference as addressReference," +
//                        " p.province.id as provinceId, p.district.id as districtId, p.bairro.id as bairroId, p.postoAdministrativo.id as postoAdministrativoId, p.clinic.clinicName as clinicName, " +
//                        "i.identifierType.id as identifierId, i.value as valueForNid, i.prefered as preferedValue, pvd.pack.pickupDate as pickupDate, pvd.pack.nextPickUpDate as nextPickUpDate, " +
//                        "pvd.pack.dispenseMode.id as dispenseModeId, pvd.prescription.id as prescriptionId " +
//                        "from Patient p " +
//                        "inner join p.identifiers i " +
//                        "inner join i.episodes ep " +
//                        "inner join ep.patientVisitDetails pvd"
//        )
        //Active Patients
        def result = Patient.executeQuery(
                "SELECT  pat.firstNames, pat.middleNames, pat.lastNames,  pat.cellphone, pat.gender, psi.prefered, psi.value, idt.id as prefered, pack.pickupDate, pack.nextPickUpDate, pred.reasonForUpdate, regime.description, dt.description, lt.description " +
                        "FROM Patient pat " +
                        "INNER JOIN PatientServiceIdentifier psi " +
                        "ON psi.patient.id = pat.id and pat.province.id = :province_id and psi.clinic.id = :clinic_id " +
                        "INNER JOIN ClinicalService cs " +
                        "ON psi.service.id = cs.id and cs.code = :clinicalService " +
                        "INNER JOIN IdentifierType idt " +
                        "ON psi.identifierType.id = idt.id " +
                        "INNER JOIN Episode ep " +
                        "ON ep.patientServiceIdentifier.id = psi.id " +
                        "INNER JOIN StartStopReason ststreason " +
                        "ON ep.startStopReason.id =  ststreason.id and ststreason.isStartReason = true " +
                        "INNER JOIN PatientVisitDetails pvd " +
                        "ON pvd.episode.id = ep.id " +
                        "INNER JOIN Pack pack " +
                        "ON pvd.pack.id = pack.id and DATE(pack.nextPickUpDate) + :days >= :endDate " +
                        "INNER JOIN Prescription pre " +
                        "ON pvd.prescription.id = pre.id " +
                        "INNER JOIN PrescriptionDetail pred " +
                        "ON pred.prescription.id = pre.id " +
                        "INNER JOIN TherapeuticRegimen regime " +
                        "ON pred.therapeuticRegimen.id = regime.id " +
                        "INNER JOIN DispenseType dt " +
                        "ON pred.dispenseType.id = dt.id " +
                        "INNER JOIN TherapeuticLine lt " +
                        "ON pred.therapeuticLine.id = lt.id",
                [clinic_id: clinic_id, clinicalService: clinicalService, endDate: endDate, province_id: province_id, days: 3]
        )

//        System.out.println(result.getString("firstNames"))

//        def result = Patient.executeQuery(
//                "select p.firstNames, p.middleNames, p.lastNames, p.gender, p.dateOfBirth, p.cellphone, p.alternativeCellphone, p.address, p.addressReference, p.province.description, " +
//                        "p.district.description, p.bairro.description, p.postoAdministrativo.description, p.clinic.clinicName, i.identifierType.id, i.value, i.prefered, pvd.pack.pickupDate, pvd.pack.nextPickUpDate, " +
//                        "pvd.pack.dispenseMode.id, pvd.prescription.id " +
//                        "from Patient p " +
//                        "inner join p.identifiers i " +
//                        "inner join i.episodes ep " +
//                        "inner join ep.patientVisitDetails pvd " +
//                        "where p.id = i.patient and " +
//                        "i.service.code = :clinicalService and " +
//                        "i.clinic.id = :clinic_id and " +
//                        "i.id = ep.patientServiceIdentifier.id and " +
//                        "ep.startStopReason.isStartReason = true and " + //false
//                        "ep.id = pvd.episode.id and " +
//                        "pvd.pack.nextPickUpDate  < :realEndDate and " + // >=
//                        "p.province.id = :province_id",
//                [clinic_id: clinic_id, clinicalService: clinicalService, realEndDate: endDate, province_id: province_id]
//        )

        //Preenchimento e registo de cada objecto do relatorio
        for (patientItem in result) {
             System.out.println(patientItem.toString())
//            activeOrFaltosoPatientReport = new ActiveOrFaltosoPatientReport(
//                    reportId, patientItem[0].toString(), patientItem[1].toString(), patientItem[2].toString(),
//                    patientItem[3].toString(), patientItem[5].toString(), patientItem[6].toString(),
//                    patientItem[7].toString(), patientItem[8].toString(), patientItem[20].toString(), reportType)
//
//            //Setting district
//            if (patientItem[10] != null) {
//                activeOrFaltosoPatientReport.setDistrict(District.findById(patientItem[10].toString()).getDescription())
//            }
//
//            //Setting Pharmacy
//            if (patientItem[13] != null) {
//                activeOrFaltosoPatientReport.setClinic(patientItem[13].toString())
//            }
//            //Setting DispenseMode
//            if (patientItem[19] != null) {
//                activeOrFaltosoPatientReport.setDispenseMode(DispenseMode.findById(patientItem[19].toString()).getDescription())
//            }
//
//            //Setting Posto Administrativo
//            if (patientItem[12] != null) {
//                activeOrFaltosoPatientReport.setPostoAdministrativo(PostoAdministrativo.findById(patientItem[12].toString()).getDescription())
//            }
//
//            //Setting Province
//            if (patientItem[9] != null) {
//                activeOrFaltosoPatientReport.setProvince(Province.findById(patientItem[9].toString()).getDescription())
//            }
//
//            //Setting Bairro
//            if (patientItem[11] != null) {
//                activeOrFaltosoPatientReport.setBairro(Localidade.findById(patientItem[11].toString()).getDescription())
//            }
//
//            // Data de nascimento
//            if (patientItem[4] != null) {
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")
//                dateOfBirth = formatter.parse(patientItem[4].toString())
//                activeOrFaltosoPatientReport.setDateOfBirth(dateOfBirth)
//
//                // Setting age
//                //activePatientsReport.setAgeOnEndDate(calcularIdade(dateOfBirth, endDate))
//            }
//
//            // Data do ultimo levantamento
//            if (patientItem[17] != null) {
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")
//                pickupDate = formatter.parse(patientItem[17].toString())
//                activeOrFaltosoPatientReport.setPickupDate(pickupDate)
//            }
//
//            // Data do proximo levantamento
//            if (patientItem[18] != null) {
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")
//                nextPickUpDate = formatter.parse(patientItem[18].toString())
//                activeOrFaltosoPatientReport.setNextPickUpDate(nextPickUpDate)
//            }
//
//            if (startDate != null)
//                activeOrFaltosoPatientReport.setStartDate(startDate)
//
//            if (endDate != null)
//                activeOrFaltosoPatientReport.setEndDate(endDate)
//
//            //-- Setting therapeuticRegimen and dispenseType
//            if (patientItem[20] != null) {
//                prescription = Prescription.findById(patientItem[20].toString())
//                if (prescription != null) {
//                    prescriptionDetail = prescription.prescriptionDetails[0]
//                    if (prescription != null) {
//                        activeOrFaltosoPatientReport.setTherapeuticRegimen(TherapeuticRegimen.findById(prescriptionDetail.therapeuticRegimen.id.toString()).getDescription())
//                        activeOrFaltosoPatientReport.setDispenseType(prescriptionDetail.dispenseType.description.toString())
//                    }
//                }
//
//                //-- Setting nid
//                if (patientItem[16] != null) {
//                    if (patientItem[16]) {
//                        activeOrFaltosoPatientReport.setNid(patientItem[15])
//                    } else {
//                        activeOrFaltosoPatientReport.setNid(patientItem[14])
//                    }
//                }
//
//                activeOrFaltosoPatientReport.setPeriodType(periodType)
//
//                activeOrFaltosoPatientReport.setReportId("12345")
//
//                activeOrFaltosoPatientReport.setYear(year)
//
//                activeOrFaltosoPatientReport.setClinic(Clinic.findById(reportSearchParams.getClinicId()).toString())
//
////                activeOrFaltosoPatientReport.setAgeOnEndDate("25")
//
//                //-- Setting ReportType
//                activeOrFaltosoPatientReport.setReportType(reportType)
//
//                System.println(activeOrFaltosoPatientReport.toString())
//                System.println(resultList.size())
//                resultList.add(activeOrFaltosoPatientReport)
//            }
//            return resultList
        }
    }

}
