package mz.org.fgh.sifmoz.backend.patient

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.interoperabilityAttribute.InteroperabilityAttribute
import mz.org.fgh.sifmoz.backend.interoperabilityType.InteroperabilityType
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.reports.patients.ActiveOrFaltosoPatientReport
import mz.org.fgh.sifmoz.backend.restUtils.RestOpenMRSClient
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import java.text.SimpleDateFormat
import java.time.LocalDate

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientController extends RestfulController {

    IPatientService patientService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientController() {
        super(Patient)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(patientService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(patientService.get(id)) as JSON
    }

        @Transactional
        def save(Patient patient) {
            if (patient == null) {
                render status: NOT_FOUND
                return
            }
            if (patient.hasErrors()) {
                transactionStatus.setRollbackOnly()
                respond patient.errors
                return
            }

            try {
                patientService.save(patient)
            } catch (ValidationException e) {
                respond patient.errors
                return
            }

            respond patient, [status: CREATED, view: "show"]
        }

        @Transactional
        def update(Patient patient) {
            if (patient == null) {
                render status: NOT_FOUND
                return
            }
            if (patient.hasErrors()) {
                transactionStatus.setRollbackOnly()
                respond patient.errors
                return
            }

            try {
                patientService.save(patient)
            } catch (ValidationException e) {
                respond patient.errors
                return
            }

            respond patient, [status: OK, view: "show"]
        }

        @Transactional
        def delete(Long id) {
            if (id == null || patientService.delete(id) == null) {
                render status: NOT_FOUND
                return
            }

            render status: NO_CONTENT
        }

        def getByClinicId(String clinicId, int offset, int max) {
            render JSONSerializer.setObjectListJsonResponse(patientService.getAllByClinicId(clinicId, offset, max)) as JSON
            //respond patientService.getAllByClinicId(clinicId, offset, max)
        }


    def getOpenMRSSession(String interoperabilityId, String username, String password) {

        HealthInformationSystem healthInformationSystem = HealthInformationSystem.get(interoperabilityId)
        InteroperabilityType interoperabilityType = InteroperabilityType.findByCode("URL_BASE")
        InteroperabilityAttribute interoperabilityAttribute = InteroperabilityAttribute.findByHealthInformationSystemAndInteroperabilityType(healthInformationSystem, interoperabilityType)

       render RestOpenMRSClient.getResponseOpenMRSClient(username, password, null, interoperabilityAttribute.value, "session", "GET")

    }

    def getOpenMRSPatient(String interoperabilityId, String nid, String username, String password) {

        HealthInformationSystem healthInformationSystem = HealthInformationSystem.get(interoperabilityId)
        InteroperabilityType interoperabilityType = InteroperabilityType.findByCode("URL_BASE")
        InteroperabilityAttribute interoperabilityAttribute = InteroperabilityAttribute.findByHealthInformationSystemAndInteroperabilityType(healthInformationSystem, interoperabilityType)

        String urlPath = "patient?q="+nid.replaceAll("-","/") + "&v=full&limit=1000"

        render RestOpenMRSClient.getResponseOpenMRSClient(username, password, null, interoperabilityAttribute.value, urlPath, "GET")

    }

    def getReportActiveByServiceCode () {


        Date startDate = new Date()
        Date endDate = new Date()

        // Take a date
        LocalDate date = LocalDate.parse("2021-08-03")

        // subtract days to date
        LocalDate newDate = date.minusDays(33)
        Date realEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate.toString())

        def service_id = "ff8081817c699611017c6b6bc36f0003"
        def clinic_id = "ff8081817c668dcc017c66dc3d330002"

        String reportId = "00001"
        Prescription prescription
        PrescriptionDetail prescriptionDetail
        Date dateOfBirth
        Date pickupDate
        Date nextPickUpDate
        ActiveOrFaltosoPatientReport activePatientsReport
        def reportType = "ACTIVE_PATIENT"
        def age = ""

        /*
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
                        "pvd.pack.nextPickUpDate  < :realEndDate", // >=
                [clinic_id: clinic_id, service_id: service_id, realEndDate: realEndDate]
        )*/
        def result = Patient.executeQuery(
                "select p.firstNames, p.middleNames, p.lastNames, p.gender, p.dateOfBirth " +
                        "from Patient p "
        )

        for (p in result){
            System.out.println(p)
        }

        // O NID eh 'value' se prefered=true, caso contrario o NID eh identifierType

//        Map<String, Object> map = new HashMap<>()
//        map.put("path", "/home/muhammad/IdeaProjects/SIFMOZ-Backend-New/src/main/webapp/reports");
//        map.put("clinic", clinic.getClinicName())
//        map.put("clinicid", clinic.getId())
//        Map<String, Object> map1 = new HashMap<String, Object>()
//        map1.put("clinicname", clinic.getClinicName())
//           List<Map<String, Object>> reportObjects = new ArrayList<>()
//        //    List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>()
//        for (PatientServiceIdentifier patient:patients) {
//            Map<String, Object> reportObject = new HashMap<String, Object>()
//            reportObject.put("nid", patient.value)
//            reportObject.put("name", patient.patient.firstNames)
//            reportObject.put("gender", patient.patient.gender)
//            reportObject.put("birthDate", patient.patient.dateOfBirth)
//            reportObject.put("initTreatmentDate", patient.startDate)
//            reportObjects.add(reportObject)
//        }
//        reportObjects.add(map1)
//        byte [] report = ReportGenerator.generateReport(map,reportObjects,"/home/muhammad/IdeaProjects/SIFMOZ-Backend-New/src/main/webapp/reports/RelatorioPacientesActivos.jrxml")
//        render(file: report, contentType: 'application/pdf')

        return  result
    }

}
