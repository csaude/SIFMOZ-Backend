package mz.org.fgh.sifmoz.backend.patient

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.interoperabilityAttribute.InteroperabilityAttribute
import mz.org.fgh.sifmoz.backend.interoperabilityType.InteroperabilityType
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.restUtils.RestOpenMRSClient
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import mz.org.fgh.sifmoz.backend.report.ReportGenerator
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate5.SessionFactoryUtils

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
        Patient patient = patientService.get(id)
        render JSONSerializer.setJsonObjectResponse(patient) as JSON
        //respond patientService.get(id)
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
            List<String> toIncludeProps = new ArrayList<>()
            toIncludeProps.add("identifiers")
            toIncludeProps.add("clinic")
            //render JSONSerializer.setLightObjectListJsonResponse(patientService.getAllByClinicId(clinicId, offset, max), toIncludeProps) as JSON
            render JSONSerializer.setObjectListJsonResponse(patientService.getAllByClinicId(clinicId, offset, max)) as JSON
            //respond patientService.getAllByClinicId(clinicId, offset, max)
        }

    def search(Patient patient) {
        List<Patient> patientList = patientService.search(patient)
        render JSONSerializer.setObjectListJsonResponse(patientList) as JSON
    }

    def searchByParam(String searchString, String clinicId) {
        List<Patient> patientList = patientService.search(searchString, clinicId)
        render JSONSerializer.setObjectListJsonResponse(patientList) as JSON
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

        String urlPath = "patient?q="+nid.replaceAll("-","/") + "&v=full&limit=100"

        render RestOpenMRSClient.getResponseOpenMRSClient(username, password, null, interoperabilityAttribute.value, urlPath, "GET")

    }

}
