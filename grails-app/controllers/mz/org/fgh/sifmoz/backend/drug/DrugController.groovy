package mz.org.fgh.sifmoz.backend.drug

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class DrugController extends RestfulController{

    IDrugService drugService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    DrugController() {
        super(Drug)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(drugService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(drugService.get(id)) as JSON
    }

    @Transactional
    def save(Drug drug) {
        if (drug == null) {
            render status: NOT_FOUND
            return
        }
        if (drug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond drug.errors
            return
        }

        try {
            drugService.save(drug)
        } catch (ValidationException e) {
            respond drug.errors
            return
        }

        respond drug, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Drug drug) {
        if (drug == null) {
            render status: NOT_FOUND
            return
        }
        if (drug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond drug.errors
            return
        }

        try {
            drugService.save(drug)
        } catch (ValidationException e) {
            respond drug.errors
            return
        }

        respond drug, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || drugService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
