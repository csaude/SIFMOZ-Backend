package mz.org.fgh.sifmoz.backend.patientVisit

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.screening.TBScreening
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import mz.org.fgh.sifmoz.backend.utilities.Utilities

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientVisitController extends RestfulController{

    IPatientVisitService patientVisitService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientVisitController() {
        super(PatientVisit)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(patientVisitService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(patientVisitService.get(id)) as JSON
    }

    @Transactional
    def save(PatientVisit visit) {
        if (visit == null) {
            render status: NOT_FOUND
            return
        }
        if (visit.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond visit.errors
            return
        }

        try {
            patientVisitService.save(visit)
        } catch (ValidationException e) {
            respond visit.errors
            return
        }

        respond visit, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientVisit visit) {
        if (visit == null) {
            render status: NOT_FOUND
            return
        }
        if (visit.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond visit.errors
            return
        }

        try {
            patientVisitService.save(visit)
        } catch (ValidationException e) {
            respond visit.errors
            return
        }

        respond visit, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null || patientVisitService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getAllByClinicId(String clinicId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(patientVisitService.getAllByClinicId(clinicId, offset, max)) as JSON
    }

    def getByPatientId(String patientId) {
        render JSONSerializer.setObjectListJsonResponse(patientVisitService.getAllByPatientId(patientId)) as JSON
    }

    def getLastVisitOfPatient(String patientId) {
        render JSONSerializer.setJsonObjectResponse(patientVisitService.getLastVisitOfPatient(patientId)) as JSON
    }
}
