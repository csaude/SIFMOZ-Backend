package mz.org.fgh.sifmoz.backend.screening

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AdherenceScreeningServiceSpec extends Specification {

    AdherenceScreeningService adherenceScreeningService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AdherenceScreening(...).save(flush: true, failOnError: true)
        //new AdherenceScreening(...).save(flush: true, failOnError: true)
        //AdherenceScreening adherenceScreening = new AdherenceScreening(...).save(flush: true, failOnError: true)
        //new AdherenceScreening(...).save(flush: true, failOnError: true)
        //new AdherenceScreening(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //adherenceScreening.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        adherenceScreeningService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AdherenceScreening> adherenceScreeningList = adherenceScreeningService.list(max: 2, offset: 2)

        then:
        adherenceScreeningList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        adherenceScreeningService.count() == 5
    }

    void "test delete"() {
        Long adherenceScreeningId = setupData()

        expect:
        adherenceScreeningService.count() == 5

        when:
        adherenceScreeningService.delete(adherenceScreeningId)
        datastore.currentSession.flush()

        then:
        adherenceScreeningService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AdherenceScreening adherenceScreening = new AdherenceScreening()
        adherenceScreeningService.save(adherenceScreening)

        then:
        adherenceScreening.id != null
    }
}
