package mz.org.fgh.sifmoz.backend.duration

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DurationServiceSpec extends Specification {

    DurationService durationService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Duration(...).save(flush: true, failOnError: true)
        //new Duration(...).save(flush: true, failOnError: true)
        //Duration duration = new Duration(...).save(flush: true, failOnError: true)
        //new Duration(...).save(flush: true, failOnError: true)
        //new Duration(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //duration.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        durationService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Duration> durationList = durationService.list(max: 2, offset: 2)

        then:
        durationList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        durationService.count() == 5
    }

    void "test delete"() {
        Long durationId = setupData()

        expect:
        durationService.count() == 5

        when:
        durationService.delete(durationId)
        datastore.currentSession.flush()

        then:
        durationService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Duration duration = new Duration()
        durationService.save(duration)

        then:
        duration.id != null
    }
}
