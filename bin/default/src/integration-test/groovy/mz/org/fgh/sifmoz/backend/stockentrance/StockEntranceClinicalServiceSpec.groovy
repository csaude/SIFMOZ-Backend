package mz.org.fgh.sifmoz.backend.stockentrance

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class StockEntranceClinicalServiceSpec extends Specification {

    IStockEntranceService stockEntranceService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new StockEntrance(...).save(flush: true, failOnError: true)
        //new StockEntrance(...).save(flush: true, failOnError: true)
        //StockEntrance stockEntrance = new StockEntrance(...).save(flush: true, failOnError: true)
        //new StockEntrance(...).save(flush: true, failOnError: true)
        //new StockEntrance(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //stockEntrance.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        stockEntranceService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<StockEntrance> stockEntranceList = stockEntranceService.list(max: 2, offset: 2)

        then:
        stockEntranceList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        stockEntranceService.count() == 5
    }

    void "test delete"() {
        Long stockEntranceId = setupData()

        expect:
        stockEntranceService.count() == 5

        when:
        stockEntranceService.delete(stockEntranceId)
        datastore.currentSession.flush()

        then:
        stockEntranceService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        StockEntrance stockEntrance = new StockEntrance()
        stockEntranceService.save(stockEntrance)

        then:
        stockEntrance.id != null
    }
}
