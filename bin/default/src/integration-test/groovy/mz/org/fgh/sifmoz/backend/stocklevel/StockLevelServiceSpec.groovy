package mz.org.fgh.sifmoz.backend.stocklevel

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class StockLevelServiceSpec extends Specification {

    StockLevelService stockLevelService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new StockLevel(...).save(flush: true, failOnError: true)
        //new StockLevel(...).save(flush: true, failOnError: true)
        //StockLevel stockLevel = new StockLevel(...).save(flush: true, failOnError: true)
        //new StockLevel(...).save(flush: true, failOnError: true)
        //new StockLevel(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //stockLevel.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        stockLevelService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<StockLevel> stockLevelList = stockLevelService.list(max: 2, offset: 2)

        then:
        stockLevelList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        stockLevelService.count() == 5
    }

    void "test delete"() {
        Long stockLevelId = setupData()

        expect:
        stockLevelService.count() == 5

        when:
        stockLevelService.delete(stockLevelId)
        datastore.currentSession.flush()

        then:
        stockLevelService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        StockLevel stockLevel = new StockLevel()
        stockLevelService.save(stockLevel)

        then:
        stockLevel.id != null
    }
}
