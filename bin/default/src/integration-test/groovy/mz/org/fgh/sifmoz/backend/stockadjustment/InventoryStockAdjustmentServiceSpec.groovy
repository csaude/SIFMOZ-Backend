package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class InventoryStockAdjustmentServiceSpec extends Specification {

    InventoryStockAdjustmentService inventoryStockAdjustmentService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new InventoryStockAdjustment(...).save(flush: true, failOnError: true)
        //new InventoryStockAdjustment(...).save(flush: true, failOnError: true)
        //InventoryStockAdjustment inventoryStockAdjustment = new InventoryStockAdjustment(...).save(flush: true, failOnError: true)
        //new InventoryStockAdjustment(...).save(flush: true, failOnError: true)
        //new InventoryStockAdjustment(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //inventoryStockAdjustment.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        inventoryStockAdjustmentService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<InventoryStockAdjustment> inventoryStockAdjustmentList = inventoryStockAdjustmentService.list(max: 2, offset: 2)

        then:
        inventoryStockAdjustmentList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        inventoryStockAdjustmentService.count() == 5
    }

    void "test delete"() {
        Long inventoryStockAdjustmentId = setupData()

        expect:
        inventoryStockAdjustmentService.count() == 5

        when:
        inventoryStockAdjustmentService.delete(inventoryStockAdjustmentId)
        datastore.currentSession.flush()

        then:
        inventoryStockAdjustmentService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        InventoryStockAdjustment inventoryStockAdjustment = new InventoryStockAdjustment()
        inventoryStockAdjustmentService.save(inventoryStockAdjustment)

        then:
        inventoryStockAdjustment.id != null
    }
}
