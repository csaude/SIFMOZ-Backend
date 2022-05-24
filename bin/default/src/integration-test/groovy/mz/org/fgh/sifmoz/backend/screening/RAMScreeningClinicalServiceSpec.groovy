package mz.org.fgh.sifmoz.backend.screening

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class RAMScreeningClinicalServiceSpec extends Specification {

    RAMScreeningService RAMScreeningService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new RAMScreening(...).save(flush: true, failOnError: true)
        //new RAMScreening(...).save(flush: true, failOnError: true)
        //RAMScreening RAMScreening = new RAMScreening(...).save(flush: true, failOnError: true)
        //new RAMScreening(...).save(flush: true, failOnError: true)
        //new RAMScreening(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //RAMScreening.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        RAMScreeningService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<RAMScreening> RAMScreeningList = RAMScreeningService.list(max: 2, offset: 2)

        then:
        RAMScreeningList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        RAMScreeningService.count() == 5
    }

    void "test delete"() {
        Long RAMScreeningId = setupData()

        expect:
        RAMScreeningService.count() == 5

        when:
        RAMScreeningService.delete(RAMScreeningId)
        datastore.currentSession.flush()

        then:
        RAMScreeningService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        RAMScreening RAMScreening = new RAMScreening()
        RAMScreeningService.save(RAMScreening)

        then:
        RAMScreening.id != null
    }
}
