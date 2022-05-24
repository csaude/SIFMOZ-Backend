package mz.org.fgh.sifmoz.backend.stockdestruction

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DestroyedStockClinicalServiceSpec extends Specification {

    DestroyedStockService destroyedStockService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DestroyedStock(...).save(flush: true, failOnError: true)
        //new DestroyedStock(...).save(flush: true, failOnError: true)
        //DestroyedStock destroyedStock = new DestroyedStock(...).save(flush: true, failOnError: true)
        //new DestroyedStock(...).save(flush: true, failOnError: true)
        //new DestroyedStock(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //destroyedStock.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        destroyedStockService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DestroyedStock> destroyedStockList = destroyedStockService.list(max: 2, offset: 2)

        then:
        destroyedStockList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        destroyedStockService.count() == 5
    }

    void "test delete"() {
        Long destroyedStockId = setupData()

        expect:
        destroyedStockService.count() == 5

        when:
        destroyedStockService.delete(destroyedStockId)
        datastore.currentSession.flush()

        then:
        destroyedStockService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DestroyedStock destroyedStock = new DestroyedStock()
        destroyedStockService.save(destroyedStock)

        then:
        destroyedStock.id != null
    }
}
