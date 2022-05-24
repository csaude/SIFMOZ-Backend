package mz.org.fgh.sifmoz.backend.patientAttribute

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PatientAttributeServiceSpec extends Specification {

    PatientAttributeService patientAttributeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PatientAttribute(...).save(flush: true, failOnError: true)
        //new PatientAttribute(...).save(flush: true, failOnError: true)
        //PatientAttribute patientAttribute = new PatientAttribute(...).save(flush: true, failOnError: true)
        //new PatientAttribute(...).save(flush: true, failOnError: true)
        //new PatientAttribute(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patientAttribute.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patientAttributeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PatientAttribute> patientAttributeList = patientAttributeService.list(max: 2, offset: 2)

        then:
        patientAttributeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patientAttributeService.count() == 5
    }

    void "test delete"() {
        Long patientAttributeId = setupData()

        expect:
        patientAttributeService.count() == 5

        when:
        patientAttributeService.delete(patientAttributeId)
        datastore.currentSession.flush()

        then:
        patientAttributeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PatientAttribute patientAttribute = new PatientAttribute()
        patientAttributeService.save(patientAttribute)

        then:
        patientAttribute.id != null
    }
}
