package mz.org.fgh.sifmoz.backend.stockrefered

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ReferedStockMovimentServiceSpec extends Specification {

    ReferedStockMovimentService referedStockMovimentService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ReferedStockMoviment(...).save(flush: true, failOnError: true)
        //new ReferedStockMoviment(...).save(flush: true, failOnError: true)
        //ReferedStockMoviment referedStockMoviment = new ReferedStockMoviment(...).save(flush: true, failOnError: true)
        //new ReferedStockMoviment(...).save(flush: true, failOnError: true)
        //new ReferedStockMoviment(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //referedStockMoviment.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        referedStockMovimentService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReferedStockMoviment> referedStockMovimentList = referedStockMovimentService.list(max: 2, offset: 2)

        then:
        referedStockMovimentList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        referedStockMovimentService.count() == 5
    }

    void "test delete"() {
        Long referedStockMovimentId = setupData()

        expect:
        referedStockMovimentService.count() == 5

        when:
        referedStockMovimentService.delete(referedStockMovimentId)
        datastore.currentSession.flush()

        then:
        referedStockMovimentService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ReferedStockMoviment referedStockMoviment = new ReferedStockMoviment()
        referedStockMovimentService.save(referedStockMoviment)

        then:
        referedStockMoviment.id != null
    }
}
