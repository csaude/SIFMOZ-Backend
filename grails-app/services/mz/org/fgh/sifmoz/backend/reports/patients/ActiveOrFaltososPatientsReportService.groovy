package mz.org.fgh.sifmoz.backend.reports.patients

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail

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
        for (patient in patients) {
            save(patient)
        }
//        for (patient in patients) {
//            System.out.println(patient.save().toString())
//        }
    }

    @Override
    List<ActiveOrFaltosoPatientReport> processamentoDados(ReportSearchParams reportSearchParams) {
        List<ActiveOrFaltosoPatientReport> resultList = new ArrayList<>()
        Date startDate = reportSearchParams.getStartDate()
        Date endDate = reportSearchParams.getEndDate()
        String clinic_id = reportSearchParams.getPharmacyId()
        String reportId = reportSearchParams.getReportId()
        String province_id = reportSearchParams.getProvinceId()
        String periodType = reportSearchParams.getPeriodType()
        int  year = reportSearchParams.getYear()

        // Take a date
        LocalDate date = LocalDate.parse("2021-08-03")

        // subtract days to date
        LocalDate newDate = date.minusDays(33)
        Date realEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate.toString())

        def service_id = "ff8081817c699611017c6b6bc36f0003"

        Prescription prescription
        PrescriptionDetail prescriptionDetail
        Date dateOfBirth
        Date pickupDate
        Date nextPickUpDate
        ActiveOrFaltosoPatientReport activePatientsReport
        def reportType = "ACTIVE_PATIENT"
        def age = ""


        def result = Patient.executeQuery(
                "select p.firstNames, p.middleNames, p.lastNames, p.gender, p.dateOfBirth, p.cellphone, p.alternativeCellphone, p.address, p.addressReference, p.province.id, " +
                        "p.district.id, p.bairro.id, p.postoAdministrativo.id, p.clinic.id, i.identifierType.id, i.value, i.prefered, pvd.pack.pickupDate, pvd.pack.nextPickUpDate, " +
                        "pvd.pack.dispenseMode.id, pvd.prescription.id " +
                        "from Patient p " +
                        "inner join p.identifiers i " +
                        "inner join i.episodes ep " +
                        "inner join ep.patientVisitDetails pvd " +
                        "where p.id = i.patient and " +
                        "i.service.id = :service_id and " +
                        "i.clinic.id = :clinic_id and " +
                        "i.id = ep.patientServiceIdentifier.id and " +
                        "ep.startStopReason.isStartReason = true and " + //false
                        "ep.id = pvd.episode.id and " +
                        "pvd.pack.nextPickUpDate  < :realEndDate and " + // >=
                        "p.province.id = :province_id",
                [clinic_id: clinic_id, service_id: service_id, realEndDate: realEndDate, province_id: province_id]
        )

        //Preencimento e registo de cada objecto do relatorio
        for (patientDetails in result) {
            // System.out.println(patientDetails)
            activePatientsReport = new ActiveOrFaltosoPatientReport(reportId, patientDetails[0].toString(), patientDetails[1].toString(), patientDetails[2].toString(), patientDetails[3].toString(), patientDetails[5].toString(), patientDetails[6].toString(), patientDetails[7].toString(), patientDetails[8].toString(), patientDetails[9].toString(),
            patientDetails[10].toString(), patientDetails[11].toString(), patientDetails[12].toString(), patientDetails[13].toString(), patientDetails[19].toString(),patientDetails[20].toString(),  reportType)

            // Data de nascimento
            if (patientDetails[4] != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")
                dateOfBirth = formatter.parse(patientDetails[4].toString())
                activePatientsReport.setDateOfBirth(dateOfBirth)

                // Setting age
                //activePatientsReport.setAgeOnEndDate(calcularIdade(dateOfBirth, endDate))
            }

            // Data do ultimo levantamento
            if (patientDetails[17] != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")
                pickupDate = formatter.parse(patientDetails[17].toString())
                activePatientsReport.setPickupDate(pickupDate)
            }

            // Data do proximo levantamento
            if (patientDetails[18] != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")
                nextPickUpDate = formatter.parse(patientDetails[18].toString())
                activePatientsReport.setNextPickUpDate(nextPickUpDate)
            }

            if (startDate != null)
                activePatientsReport.setStartDate(startDate)

            if (endDate != null)
                activePatientsReport.setEndDate(endDate)

            //-- Setting therapeuticRegimen and dispenseType
            if (patientDetails[20] != null){
                prescription = Prescription.findById(patientDetails[20].toString())
                prescriptionDetail = prescription.prescriptionDetails[0]
                activePatientsReport.setTherapeuticRegimen(prescriptionDetail.therapeuticRegimen.description.toString())
                activePatientsReport.setDispenseType(prescriptionDetail.dispenseType.description.toString())
            }

            //-- Setting nid
            if (patientDetails[16] != null) {
                if(patientDetails[16]){
                    activePatientsReport.setNid(patientDetails[15])
                } else {
                    activePatientsReport.setNid(patientDetails[14])
                }
            }

            activePatientsReport.setPeriodType(periodType)

            activePatientsReport.setYear(year)

            activePatientsReport.setAgeOnEndDate("25")

            //-- Setting ReportType
            activePatientsReport.setReportType(reportType)


            //System.println(activePatientsReport.toString())
            resultList.add(activePatientsReport)
        }

        return resultList
    }
}
