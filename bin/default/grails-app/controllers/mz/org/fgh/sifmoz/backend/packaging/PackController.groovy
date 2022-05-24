package mz.org.fgh.sifmoz.backend.packaging

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrugService
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrugStock
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrugStockService
import mz.org.fgh.sifmoz.backend.clinic.ClinicService
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stock.StockService
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PackController extends RestfulController{

    IPackService packService
    StockService stockService
    PackagedDrugStockService packagedDrugStockService
    PackagedDrugService packagedDrugService
    ClinicService clinicService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PackController() {
        super(Pack)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(packService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(packService.get(id)) as JSON
    }

    @Transactional
    def save(Pack pack) {
        if (pack == null) {
            render status: NOT_FOUND
            return
        }
        if (pack.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pack.errors
            return
        }

        try {
            packService.save(pack)
        } catch (ValidationException e) {
            respond pack.errors
            return
        }

        respond pack, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Pack pack) {
        if (pack == null) {
            render status: NOT_FOUND
            return
        }
        if (pack.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pack.errors
            return
        }

        try {
            for (PackagedDrug packagedDrug : pack.packagedDrugs) {
                List<PackagedDrugStock> packagedDrugStocks = PackagedDrugStock.findAllByPackagedDrug(packagedDrug)
                for (PackagedDrugStock packagedDrugStock : packagedDrugStocks) {
                    Stock stock = Stock.findById(packagedDrugStock.stock.id)
                    stock.stockMoviment = packagedDrugStock.quantitySupplied + stock.stockMoviment
                    stockService.save(stock)
                    packagedDrugStock.delete()
                }
                packagedDrug.delete()
            }
            packService.save(pack)
        } catch (ValidationException e) {
            respond pack.errors
            return
        }

        respond pack, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null || packService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getAllByClinicId(String clinicId, int offset, int max) {
        Clinic clinic = clinicService.get(clinicId)
        render JSONSerializer.setObjectListJsonResponse(Pack.findAllByClinic(clinic, offset, max)) as JSON
    }
}
