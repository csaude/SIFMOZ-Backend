package mz.org.fgh.sifmoz.backend.stockoperation

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class StockOperationTypeServiceSpec extends Specification {

    StockOperationTypeService stockOperationTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new StockOperationType(...).save(flush: true, failOnError: true)
        //new StockOperationType(...).save(flush: true, failOnError: true)
        //StockOperationType stockOperationType = new StockOperationType(...).save(flush: true, failOnError: true)
        //new StockOperationType(...).save(flush: true, failOnError: true)
        //new StockOperationType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //stockOperationType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        stockOperationTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<StockOperationType> stockOperationTypeList = stockOperationTypeService.list(max: 2, offset: 2)

        then:
        stockOperationTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        stockOperationTypeService.count() == 5
    }

    void "test delete"() {
        Long stockOperationTypeId = setupData()

        expect:
        stockOperationTypeService.count() == 5

        when:
        stockOperationTypeService.delete(stockOperationTypeId)
        datastore.currentSession.flush()

        then:
        stockOperationTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        StockOperationType stockOperationType = new StockOperationType()
        stockOperationTypeService.save(stockOperationType)

        then:
        stockOperationType.id != null
    }
}
