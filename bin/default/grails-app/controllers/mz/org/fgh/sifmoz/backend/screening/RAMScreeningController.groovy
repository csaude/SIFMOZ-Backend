package mz.org.fgh.sifmoz.backend.screening

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class RAMScreeningController extends RestfulController{

    RAMScreeningService RAMScreeningService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    RAMScreeningController() {
        super(RAMScreening)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(RAMScreeningService.list(params)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(RAMScreeningService.get(id)) as JSON
    }

    @Transactional
    def save(RAMScreening RAMScreening) {
        if (RAMScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (RAMScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond RAMScreening.errors
            return
        }

        try {
            RAMScreeningService.save(RAMScreening)
        } catch (ValidationException e) {
            respond RAMScreening.errors
            return
        }

        respond RAMScreening, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(RAMScreening RAMScreening) {
        if (RAMScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (RAMScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond RAMScreening.errors
            return
        }

        try {
            RAMScreeningService.save(RAMScreening)
        } catch (ValidationException e) {
            respond RAMScreening.errors
            return
        }

        respond RAMScreening, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || RAMScreeningService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
