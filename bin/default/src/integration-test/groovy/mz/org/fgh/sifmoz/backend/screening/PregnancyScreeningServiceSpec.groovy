package mz.org.fgh.sifmoz.backend.screening

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PregnancyScreeningServiceSpec extends Specification {

    PregnancyScreeningService pregnancyScreeningService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PregnancyScreening(...).save(flush: true, failOnError: true)
        //new PregnancyScreening(...).save(flush: true, failOnError: true)
        //PregnancyScreening pregnancyScreening = new PregnancyScreening(...).save(flush: true, failOnError: true)
        //new PregnancyScreening(...).save(flush: true, failOnError: true)
        //new PregnancyScreening(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //pregnancyScreening.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        pregnancyScreeningService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PregnancyScreening> pregnancyScreeningList = pregnancyScreeningService.list(max: 2, offset: 2)

        then:
        pregnancyScreeningList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        pregnancyScreeningService.count() == 5
    }

    void "test delete"() {
        Long pregnancyScreeningId = setupData()

        expect:
        pregnancyScreeningService.count() == 5

        when:
        pregnancyScreeningService.delete(pregnancyScreeningId)
        datastore.currentSession.flush()

        then:
        pregnancyScreeningService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PregnancyScreening pregnancyScreening = new PregnancyScreening()
        pregnancyScreeningService.save(pregnancyScreening)

        then:
        pregnancyScreening.id != null
    }
}
