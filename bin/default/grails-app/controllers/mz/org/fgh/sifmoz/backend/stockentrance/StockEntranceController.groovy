package mz.org.fgh.sifmoz.backend.stockentrance

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.stockcenter.StockCenter
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class StockEntranceController extends RestfulController{

    IStockEntranceService stockEntranceService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    StockEntranceController() {
        super(StockCenter)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(stockEntranceService.list(params)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(stockEntranceService.get(id)) as JSON
    }

    @Transactional
    def save(StockEntrance stockEntrance) {
        if (stockEntrance == null) {
            render status: NOT_FOUND
            return
        }
        if (stockEntrance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockEntrance.errors
            return
        }

        try {
            stockEntranceService.save(stockEntrance)
        } catch (ValidationException e) {
            respond stockEntrance.errors
            return
        }

        respond stockEntrance, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(StockEntrance stockEntrance) {
        if (stockEntrance == null) {
            render status: NOT_FOUND
            return
        }
        if (stockEntrance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockEntrance.errors
            return
        }

        try {
            stockEntranceService.save(stockEntrance)
        } catch (ValidationException e) {
            respond stockEntrance.errors
            return
        }

        respond stockEntrance, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || stockEntranceService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getByClinicId(String clinicId, int offset, int max) {
        respond stockEntranceService.getAllByClinicId(clinicId, offset, max)
    }
}
