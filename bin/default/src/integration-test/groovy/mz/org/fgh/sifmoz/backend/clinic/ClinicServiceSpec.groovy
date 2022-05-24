package mz.org.fgh.sifmoz.backend.clinic

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ClinicServiceSpec extends Specification {

    ClinicService clinicService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Clinic(...).save(flush: true, failOnError: true)
        //new Clinic(...).save(flush: true, failOnError: true)
        //Clinic clinic = new Clinic(...).save(flush: true, failOnError: true)
        //new Clinic(...).save(flush: true, failOnError: true)
        //new Clinic(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //clinic.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        clinicService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Clinic> clinicList = clinicService.list(max: 2, offset: 2)

        then:
        clinicList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        clinicService.count() == 5
    }

    void "test delete"() {
        Long clinicId = setupData()

        expect:
        clinicService.count() == 5

        when:
        clinicService.delete(clinicId)
        datastore.currentSession.flush()

        then:
        clinicService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Clinic clinic = new Clinic()
        clinicService.save(clinic)

        then:
        clinic.id != null
    }
}
