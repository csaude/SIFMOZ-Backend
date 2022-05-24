package mz.org.fgh.sifmoz.backend.screening

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class VitalSignsScreeningServiceSpec extends Specification {

    VitalSignsScreeningService vitalSignsScreeningService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new VitalSignsScreening(...).save(flush: true, failOnError: true)
        //new VitalSignsScreening(...).save(flush: true, failOnError: true)
        //VitalSignsScreening vitalSignsScreening = new VitalSignsScreening(...).save(flush: true, failOnError: true)
        //new VitalSignsScreening(...).save(flush: true, failOnError: true)
        //new VitalSignsScreening(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //vitalSignsScreening.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        vitalSignsScreeningService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<VitalSignsScreening> vitalSignsScreeningList = vitalSignsScreeningService.list(max: 2, offset: 2)

        then:
        vitalSignsScreeningList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        vitalSignsScreeningService.count() == 5
    }

    void "test delete"() {
        Long vitalSignsScreeningId = setupData()

        expect:
        vitalSignsScreeningService.count() == 5

        when:
        vitalSignsScreeningService.delete(vitalSignsScreeningId)
        datastore.currentSession.flush()

        then:
        vitalSignsScreeningService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        VitalSignsScreening vitalSignsScreening = new VitalSignsScreening()
        vitalSignsScreeningService.save(vitalSignsScreening)

        then:
        vitalSignsScreening.id != null
    }
}
