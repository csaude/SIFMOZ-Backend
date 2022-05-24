package mz.org.fgh.sifmoz.backend.doctor

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DoctorClinicalServiceSpec extends Specification {

    DoctorService doctorService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Doctor(...).save(flush: true, failOnError: true)
        //new Doctor(...).save(flush: true, failOnError: true)
        //Doctor doctor = new Doctor(...).save(flush: true, failOnError: true)
        //new Doctor(...).save(flush: true, failOnError: true)
        //new Doctor(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //doctor.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        doctorService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Doctor> doctorList = doctorService.list(max: 2, offset: 2)

        then:
        doctorList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        doctorService.count() == 5
    }

    void "test delete"() {
        Long doctorId = setupData()

        expect:
        doctorService.count() == 5

        when:
        doctorService.delete(doctorId)
        datastore.currentSession.flush()

        then:
        doctorService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Doctor doctor = new Doctor()
        doctorService.save(doctor)

        then:
        doctor.id != null
    }
}
