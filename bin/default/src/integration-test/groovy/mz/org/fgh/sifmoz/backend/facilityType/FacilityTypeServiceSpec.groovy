package mz.org.fgh.sifmoz.backend.facilityType

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class FacilityTypeServiceSpec extends Specification {

    FacilityTypeService facilityTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new FacilityType(...).save(flush: true, failOnError: true)
        //new FacilityType(...).save(flush: true, failOnError: true)
        //FacilityType facilityType = new FacilityType(...).save(flush: true, failOnError: true)
        //new FacilityType(...).save(flush: true, failOnError: true)
        //new FacilityType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //facilityType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        facilityTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<FacilityType> facilityTypeList = facilityTypeService.list(max: 2, offset: 2)

        then:
        facilityTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        facilityTypeService.count() == 5
    }

    void "test delete"() {
        Long facilityTypeId = setupData()

        expect:
        facilityTypeService.count() == 5

        when:
        facilityTypeService.delete(facilityTypeId)
        datastore.currentSession.flush()

        then:
        facilityTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        FacilityType facilityType = new FacilityType()
        facilityTypeService.save(facilityType)

        then:
        facilityType.id != null
    }
}
