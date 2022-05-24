package mz.org.fgh.sifmoz.backend.regimenDrug

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

class RegimenDrugController extends RestfulController{

    RegimenDrugService regimenDrugService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    RegimenDrugController() {
        super(RegimenDrug)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(regimenDrugService.list(params)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setObjectListJsonResponse(regimenDrugService.get(id)) as JSON
    }

    @Transactional
    def save(RegimenDrug regimenDrug) {
        if (regimenDrug == null) {
            render status: NOT_FOUND
            return
        }
        if (regimenDrug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond regimenDrug.errors
            return
        }

        try {
            regimenDrugService.save(regimenDrug)
        } catch (ValidationException e) {
            respond regimenDrug.errors
            return
        }

        respond regimenDrug, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(RegimenDrug regimenDrug) {
        if (regimenDrug == null) {
            render status: NOT_FOUND
            return
        }
        if (regimenDrug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond regimenDrug.errors
            return
        }

        try {
            regimenDrugService.save(regimenDrug)
        } catch (ValidationException e) {
            respond regimenDrug.errors
            return
        }

        respond regimenDrug, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || regimenDrugService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
