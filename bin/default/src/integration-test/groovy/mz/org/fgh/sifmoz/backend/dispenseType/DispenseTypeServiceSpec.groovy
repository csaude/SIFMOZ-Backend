package mz.org.fgh.sifmoz.backend.dispenseType

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DispenseTypeServiceSpec extends Specification {

    DispenseTypeService dispenseTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DispenseType(...).save(flush: true, failOnError: true)
        //new DispenseType(...).save(flush: true, failOnError: true)
        //DispenseType dispenseType = new DispenseType(...).save(flush: true, failOnError: true)
        //new DispenseType(...).save(flush: true, failOnError: true)
        //new DispenseType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //dispenseType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        dispenseTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DispenseType> dispenseTypeList = dispenseTypeService.list(max: 2, offset: 2)

        then:
        dispenseTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        dispenseTypeService.count() == 5
    }

    void "test delete"() {
        Long dispenseTypeId = setupData()

        expect:
        dispenseTypeService.count() == 5

        when:
        dispenseTypeService.delete(dispenseTypeId)
        datastore.currentSession.flush()

        then:
        dispenseTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DispenseType dispenseType = new DispenseType()
        dispenseTypeService.save(dispenseType)

        then:
        dispenseType.id != null
    }
}
