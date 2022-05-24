package mz.org.fgh.sifmoz.backend.nationalClinic

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class NationalClinicClinicalServiceSpec extends Specification {

    NationalClinicService nationalClinicService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new NationalClinic(...).save(flush: true, failOnError: true)
        //new NationalClinic(...).save(flush: true, failOnError: true)
        //NationalClinic nationalClinic = new NationalClinic(...).save(flush: true, failOnError: true)
        //new NationalClinic(...).save(flush: true, failOnError: true)
        //new NationalClinic(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //nationalClinic.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        nationalClinicService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<NationalClinic> nationalClinicList = nationalClinicService.list(max: 2, offset: 2)

        then:
        nationalClinicList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        nationalClinicService.count() == 5
    }

    void "test delete"() {
        Long nationalClinicId = setupData()

        expect:
        nationalClinicService.count() == 5

        when:
        nationalClinicService.delete(nationalClinicId)
        datastore.currentSession.flush()

        then:
        nationalClinicService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        NationalClinic nationalClinic = new NationalClinic()
        nationalClinicService.save(nationalClinic)

        then:
        nationalClinic.id != null
    }
}
