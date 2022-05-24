package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientVisitDetailsController extends RestfulController{

    IPatientVisitDetailsService patientVisitDetailsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientVisitDetailsController() {
        super(PatientVisitDetails)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(patientVisitDetailsService.get(id)) as JSON
    }

    @Transactional
    def save(PatientVisitDetails patientVisitDetails) {
        if (patientVisitDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (patientVisitDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVisitDetails.errors
            return
        }

        try {
            patientVisitDetailsService.save(patientVisitDetails)
        } catch (ValidationException e) {
            respond patientVisitDetails.errors
            return
        }

        respond patientVisitDetails, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientVisitDetails patientVisitDetails) {
        if (patientVisitDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (patientVisitDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVisitDetails.errors
            return
        }

        try {
            patientVisitDetailsService.save(patientVisitDetails)
        } catch (ValidationException e) {
            respond patientVisitDetails.errors
            return
        }

        respond patientVisitDetails, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientVisitDetailsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getAllByClinicId(String clinicId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.getAllByClinicId(clinicId, offset, max)) as JSON
    }

    def getAllByEpisodeId(String episodeId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.getAllByEpisodeId(episodeId, offset, max)) as JSON
    }
}
