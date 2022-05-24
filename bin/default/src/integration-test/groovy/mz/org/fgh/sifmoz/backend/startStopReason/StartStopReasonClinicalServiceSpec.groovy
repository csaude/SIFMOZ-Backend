package mz.org.fgh.sifmoz.backend.startStopReason

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class StartStopReasonClinicalServiceSpec extends Specification {

    StartStopReasonService startStopReasonService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new StartStopReason(...).save(flush: true, failOnError: true)
        //new StartStopReason(...).save(flush: true, failOnError: true)
        //StartStopReason startStopReason = new StartStopReason(...).save(flush: true, failOnError: true)
        //new StartStopReason(...).save(flush: true, failOnError: true)
        //new StartStopReason(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //startStopReason.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        startStopReasonService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<StartStopReason> startStopReasonList = startStopReasonService.list(max: 2, offset: 2)

        then:
        startStopReasonList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        startStopReasonService.count() == 5
    }

    void "test delete"() {
        Long startStopReasonId = setupData()

        expect:
        startStopReasonService.count() == 5

        when:
        startStopReasonService.delete(startStopReasonId)
        datastore.currentSession.flush()

        then:
        startStopReasonService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        StartStopReason startStopReason = new StartStopReason()
        startStopReasonService.save(startStopReason)

        then:
        startStopReason.id != null
    }
}
