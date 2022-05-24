package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class StockDestructionAdjustmentServiceSpec extends Specification {

    StockDestructionAdjustmentService stockDestructionAdjustmentService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new StockDestructionAdjustment(...).save(flush: true, failOnError: true)
        //new StockDestructionAdjustment(...).save(flush: true, failOnError: true)
        //StockDestructionAdjustment stockDestructionAdjustment = new StockDestructionAdjustment(...).save(flush: true, failOnError: true)
        //new StockDestructionAdjustment(...).save(flush: true, failOnError: true)
        //new StockDestructionAdjustment(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //stockDestructionAdjustment.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        stockDestructionAdjustmentService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<StockDestructionAdjustment> stockDestructionAdjustmentList = stockDestructionAdjustmentService.list(max: 2, offset: 2)

        then:
        stockDestructionAdjustmentList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        stockDestructionAdjustmentService.count() == 5
    }

    void "test delete"() {
        Long stockDestructionAdjustmentId = setupData()

        expect:
        stockDestructionAdjustmentService.count() == 5

        when:
        stockDestructionAdjustmentService.delete(stockDestructionAdjustmentId)
        datastore.currentSession.flush()

        then:
        stockDestructionAdjustmentService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        StockDestructionAdjustment stockDestructionAdjustment = new StockDestructionAdjustment()
        stockDestructionAdjustmentService.save(stockDestructionAdjustment)

        then:
        stockDestructionAdjustment.id != null
    }
}
