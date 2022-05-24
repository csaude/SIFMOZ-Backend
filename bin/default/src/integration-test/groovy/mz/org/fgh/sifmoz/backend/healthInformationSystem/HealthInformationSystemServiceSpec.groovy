package mz.org.fgh.sifmoz.backend.healthInformationSystem

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class HealthInformationSystemServiceSpec extends Specification {

    HealthInformationSystemService healthInformationSystemService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new HealthInformationSystem(...).save(flush: true, failOnError: true)
        //new HealthInformationSystem(...).save(flush: true, failOnError: true)
        //HealthInformationSystem healthInformationSystem = new HealthInformationSystem(...).save(flush: true, failOnError: true)
        //new HealthInformationSystem(...).save(flush: true, failOnError: true)
        //new HealthInformationSystem(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //healthInformationSystem.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        healthInformationSystemService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<HealthInformationSystem> healthInformationSystemList = healthInformationSystemService.list(max: 2, offset: 2)

        then:
        healthInformationSystemList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        healthInformationSystemService.count() == 5
    }

    void "test delete"() {
        Long healthInformationSystemId = setupData()

        expect:
        healthInformationSystemService.count() == 5

        when:
        healthInformationSystemService.delete(healthInformationSystemId)
        datastore.currentSession.flush()

        then:
        healthInformationSystemService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        HealthInformationSystem healthInformationSystem = new HealthInformationSystem()
        healthInformationSystemService.save(healthInformationSystem)

        then:
        healthInformationSystem.id != null
    }
}
