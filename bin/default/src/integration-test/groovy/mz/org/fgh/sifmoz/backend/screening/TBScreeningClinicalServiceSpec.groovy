package mz.org.fgh.sifmoz.backend.screening

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TBScreeningClinicalServiceSpec extends Specification {

    TBScreeningService TBScreeningService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TBScreening(...).save(flush: true, failOnError: true)
        //new TBScreening(...).save(flush: true, failOnError: true)
        //TBScreening TBScreening = new TBScreening(...).save(flush: true, failOnError: true)
        //new TBScreening(...).save(flush: true, failOnError: true)
        //new TBScreening(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //TBScreening.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        TBScreeningService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TBScreening> TBScreeningList = TBScreeningService.list(max: 2, offset: 2)

        then:
        TBScreeningList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        TBScreeningService.count() == 5
    }

    void "test delete"() {
        Long TBScreeningId = setupData()

        expect:
        TBScreeningService.count() == 5

        when:
        TBScreeningService.delete(TBScreeningId)
        datastore.currentSession.flush()

        then:
        TBScreeningService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TBScreening TBScreening = new TBScreening()
        TBScreeningService.save(TBScreening)

        then:
        TBScreening.id != null
    }
}
