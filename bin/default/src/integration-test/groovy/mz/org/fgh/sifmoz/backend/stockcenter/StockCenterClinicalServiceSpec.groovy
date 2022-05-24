package mz.org.fgh.sifmoz.backend.stockcenter

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class StockCenterClinicalServiceSpec extends Specification {

    StockCenterService stockCenterService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new StockCenter(...).save(flush: true, failOnError: true)
        //new StockCenter(...).save(flush: true, failOnError: true)
        //StockCenter stockCenter = new StockCenter(...).save(flush: true, failOnError: true)
        //new StockCenter(...).save(flush: true, failOnError: true)
        //new StockCenter(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //stockCenter.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        stockCenterService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<StockCenter> stockCenterList = stockCenterService.list(max: 2, offset: 2)

        then:
        stockCenterList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        stockCenterService.count() == 5
    }

    void "test delete"() {
        Long stockCenterId = setupData()

        expect:
        stockCenterService.count() == 5

        when:
        stockCenterService.delete(stockCenterId)
        datastore.currentSession.flush()

        then:
        stockCenterService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        StockCenter stockCenter = new StockCenter()
        stockCenterService.save(stockCenter)

        then:
        stockCenter.id != null
    }
}
