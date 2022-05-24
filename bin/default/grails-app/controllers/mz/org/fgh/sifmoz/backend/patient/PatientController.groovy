package mz.org.fgh.sifmoz.backend.patient

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.interoperabilityAttribute.InteroperabilityAttribute
import mz.org.fgh.sifmoz.backend.interoperabilityType.InteroperabilityType
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.restUtils.RestOpenMRSClient
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import mz.org.fgh.sifmoz.report.ReportGenerator
import org.grails.web.json.JSONArray
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate5.SessionFactoryUtils

import java.nio.charset.StandardCharsets

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientController extends RestfulController {

    IPatientService patientService

    def SessionFactory sessionFactory

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

        Clinic clinic = Clinic.findById("ff8081817c668dcc017c66dc3d330002")
        ClinicalService clinicalService = ClinicalService.findByCode("TARV")
        SessionFactoryUtils.getDataSource(sessionFactory).getConnection()
        List<PatientServiceIdentifier> patients =   PatientServiceIdentifier.findAllByStartDateIsNotNullAndEndDateIsNullAndClinicAndService(clinic,clinicalService)

        Map<String, Object> map = new HashMap<>()
        map.put("path", "/home/muhammad/IdeaProjects/SIFMOZ-Backend-New/src/main/webapp/reports");
        map.put("clinic", clinic.getClinicName())
        map.put("clinicid", clinic.getId())
        Map<String, Object> map1 = new HashMap<String, Object>()
        map1.put("clinicname", clinic.getClinicName())
        List<Map<String, Object>> reportObjects = new ArrayList<>()
        //    List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>()
        for (PatientServiceIdentifier patient:patients) {
            Map<String, Object> reportObject = new HashMap<String, Object>()
            reportObject.put("nid", patient.value)
            reportObject.put("name", patient.patient.firstNames)
            reportObject.put("gender", patient.patient.gender)
            reportObject.put("birthDate", patient.patient.dateOfBirth)
            reportObject.put("initTreatmentDate", patient.startDate)
            reportObjects.add(reportObject)
        }
        reportObjects.add(map1)
        byte [] report = ReportGenerator.generateReport(map,reportObjects,"/home/muhammad/IdeaProjects/SIFMOZ-Backend-New/src/main/webapp/reports/RelatorioPacientesActivos.jrxml")
        render(file: report, contentType: 'application/pdf')
    }

}