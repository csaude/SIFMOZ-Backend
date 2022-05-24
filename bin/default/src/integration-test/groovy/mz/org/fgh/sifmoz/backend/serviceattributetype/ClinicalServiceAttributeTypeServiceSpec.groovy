package mz.org.fgh.sifmoz.backend.serviceattributetype

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ClinicalServiceAttributeTypeServiceSpec extends Specification {

    ClinicalServiceAttributeTypeService clinicalServiceAttributeTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ClinicalServiceAttributeType(...).save(flush: true, failOnError: true)
        //new ClinicalServiceAttributeType(...).save(flush: true, failOnError: true)
        //ClinicalServiceAttributeType clinicalServiceAttributeType = new ClinicalServiceAttributeType(...).save(flush: true, failOnError: true)
        //new ClinicalServiceAttributeType(...).save(flush: true, failOnError: true)
        //new ClinicalServiceAttributeType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //clinicalServiceAttributeType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        clinicalServiceAttributeTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ClinicalServiceAttributeType> clinicalServiceAttributeTypeList = clinicalServiceAttributeTypeService.list(max: 2, offset: 2)

        then:
        clinicalServiceAttributeTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        clinicalServiceAttributeTypeService.count() == 5
    }

    void "test delete"() {
        Long clinicalServiceAttributeTypeId = setupData()

        expect:
        clinicalServiceAttributeTypeService.count() == 5

        when:
        clinicalServiceAttributeTypeService.delete(clinicalServiceAttributeTypeId)
        datastore.currentSession.flush()

        then:
        clinicalServiceAttributeTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ClinicalServiceAttributeType clinicalServiceAttributeType = new ClinicalServiceAttributeType()
        clinicalServiceAttributeTypeService.save(clinicalServiceAttributeType)

        then:
        clinicalServiceAttributeType.id != null
    }
}
