package mz.org.fgh.sifmoz.backend.attributeType

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PatientAttributeTypeServiceSpec extends Specification {

    PatientAttributeTypeService patientAttributeTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PatientAttributeType(...).save(flush: true, failOnError: true)
        //new PatientAttributeType(...).save(flush: true, failOnError: true)
        //PatientAttributeType patientAttributeType = new PatientAttributeType(...).save(flush: true, failOnError: true)
        //new PatientAttributeType(...).save(flush: true, failOnError: true)
        //new PatientAttributeType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patientAttributeType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patientAttributeTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PatientAttributeType> patientAttributeTypeList = patientAttributeTypeService.list(max: 2, offset: 2)

        then:
        patientAttributeTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patientAttributeTypeService.count() == 5
    }

    void "test delete"() {
        Long patientAttributeTypeId = setupData()

        expect:
        patientAttributeTypeService.count() == 5

        when:
        patientAttributeTypeService.delete(patientAttributeTypeId)
        datastore.currentSession.flush()

        then:
        patientAttributeTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PatientAttributeType patientAttributeType = new PatientAttributeType()
        patientAttributeTypeService.save(patientAttributeType)

        then:
        patientAttributeType.id != null
    }
}
