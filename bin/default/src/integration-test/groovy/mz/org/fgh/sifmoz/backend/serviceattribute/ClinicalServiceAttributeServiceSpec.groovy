package mz.org.fgh.sifmoz.backend.serviceattribute

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ClinicalServiceAttributeServiceSpec extends Specification {

    ClinicalServiceAttributeService clinicalServiceAttributeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ClinicalServiceAttribute(...).save(flush: true, failOnError: true)
        //new ClinicalServiceAttribute(...).save(flush: true, failOnError: true)
        //ClinicalServiceAttribute clinicalServiceAttribute = new ClinicalServiceAttribute(...).save(flush: true, failOnError: true)
        //new ClinicalServiceAttribute(...).save(flush: true, failOnError: true)
        //new ClinicalServiceAttribute(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //clinicalServiceAttribute.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        clinicalServiceAttributeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ClinicalServiceAttribute> clinicalServiceAttributeList = clinicalServiceAttributeService.list(max: 2, offset: 2)

        then:
        clinicalServiceAttributeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        clinicalServiceAttributeService.count() == 5
    }

    void "test delete"() {
        Long clinicalServiceAttributeId = setupData()

        expect:
        clinicalServiceAttributeService.count() == 5

        when:
        clinicalServiceAttributeService.delete(clinicalServiceAttributeId)
        datastore.currentSession.flush()

        then:
        clinicalServiceAttributeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ClinicalServiceAttribute clinicalServiceAttribute = new ClinicalServiceAttribute()
        clinicalServiceAttributeService.save(clinicalServiceAttribute)

        then:
        clinicalServiceAttribute.id != null
    }
}
