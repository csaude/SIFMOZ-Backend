package mz.org.fgh.sifmoz.backend.attributeType

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PatientAttributeTypeClinicalServiceSpec extends Specification {

    PatientAttributeTypeService attributeTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AttributeType(...).save(flush: true, failOnError: true)
        //new AttributeType(...).save(flush: true, failOnError: true)
        //AttributeType attributeType = new AttributeType(...).save(flush: true, failOnError: true)
        //new AttributeType(...).save(flush: true, failOnError: true)
        //new AttributeType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //attributeType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        attributeTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PatientAttributeType> attributeTypeList = attributeTypeService.list(max: 2, offset: 2)

        then:
        attributeTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        attributeTypeService.count() == 5
    }

    void "test delete"() {
        Long attributeTypeId = setupData()

        expect:
        attributeTypeService.count() == 5

        when:
        attributeTypeService.delete(attributeTypeId)
        datastore.currentSession.flush()

        then:
        attributeTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PatientAttributeType attributeType = new PatientAttributeType()
        attributeTypeService.save(attributeType)

        then:
        attributeType.id != null
    }
}
