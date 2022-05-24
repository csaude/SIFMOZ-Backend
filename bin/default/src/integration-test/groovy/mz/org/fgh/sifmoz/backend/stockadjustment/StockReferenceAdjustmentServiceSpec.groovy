package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class StockReferenceAdjustmentServiceSpec extends Specification {

    StockReferenceAdjustmentService stockReferenceAdjustmentService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new StockReferenceAdjustment(...).save(flush: true, failOnError: true)
        //new StockReferenceAdjustment(...).save(flush: true, failOnError: true)
        //StockReferenceAdjustment stockReferenceAdjustment = new StockReferenceAdjustment(...).save(flush: true, failOnError: true)
        //new StockReferenceAdjustment(...).save(flush: true, failOnError: true)
        //new StockReferenceAdjustment(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //stockReferenceAdjustment.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        stockReferenceAdjustmentService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<StockReferenceAdjustment> stockReferenceAdjustmentList = stockReferenceAdjustmentService.list(max: 2, offset: 2)

        then:
        stockReferenceAdjustmentList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        stockReferenceAdjustmentService.count() == 5
    }

    void "test delete"() {
        Long stockReferenceAdjustmentId = setupData()

        expect:
        stockReferenceAdjustmentService.count() == 5

        when:
        stockReferenceAdjustmentService.delete(stockReferenceAdjustmentId)
        datastore.currentSession.flush()

        then:
        stockReferenceAdjustmentService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        StockReferenceAdjustment stockReferenceAdjustment = new StockReferenceAdjustment()
        stockReferenceAdjustmentService.save(stockReferenceAdjustment)

        then:
        stockReferenceAdjustment.id != null
    }
}
