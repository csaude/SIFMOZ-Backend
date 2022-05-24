package mz.org.fgh.sifmoz.backend.therapeuticLine

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TherapeuticLineServiceSpec extends Specification {

    TherapeuticLineService therapeuticLineService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TherapeuticLine(...).save(flush: true, failOnError: true)
        //new TherapeuticLine(...).save(flush: true, failOnError: true)
        //TherapeuticLine therapeuticLine = new TherapeuticLine(...).save(flush: true, failOnError: true)
        //new TherapeuticLine(...).save(flush: true, failOnError: true)
        //new TherapeuticLine(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //therapeuticLine.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        therapeuticLineService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TherapeuticLine> therapeuticLineList = therapeuticLineService.list(max: 2, offset: 2)

        then:
        therapeuticLineList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        therapeuticLineService.count() == 5
    }

    void "test delete"() {
        Long therapeuticLineId = setupData()

        expect:
        therapeuticLineService.count() == 5

        when:
        therapeuticLineService.delete(therapeuticLineId)
        datastore.currentSession.flush()

        then:
        therapeuticLineService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TherapeuticLine therapeuticLine = new TherapeuticLine()
        therapeuticLineService.save(therapeuticLine)

        then:
        therapeuticLine.id != null
    }
}
