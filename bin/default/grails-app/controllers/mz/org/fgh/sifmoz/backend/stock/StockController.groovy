package mz.org.fgh.sifmoz.backend.stock

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class StockController extends RestfulController{

    IStockService stockService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    StockController() {
        super(Stock)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(stockService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(stockService.get(id)) as JSON
    }

    @Transactional
    def save(Stock stock) {
        if (stock == null) {
            render status: NOT_FOUND
            return
        }
        if (stock.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stock.errors
            return
        }

        try {
            stockService.save(stock)
        } catch (ValidationException e) {
            respond stock.errors
            return
        }

        respond stock, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Stock stock) {
        if (stock == null) {
            render status: NOT_FOUND
            return
        }
        if (stock.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stock.errors
            return
        }

        try {
            stockService.save(stock)
        } catch (ValidationException e) {
            respond stock.errors
            return
        }

        respond stock, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null || stockService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
