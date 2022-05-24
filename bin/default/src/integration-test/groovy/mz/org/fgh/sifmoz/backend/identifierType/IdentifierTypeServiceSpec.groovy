package mz.org.fgh.sifmoz.backend.identifierType

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class IdentifierTypeServiceSpec extends Specification {

    IdentifierTypeService identifierTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new IdentifierType(...).save(flush: true, failOnError: true)
        //new IdentifierType(...).save(flush: true, failOnError: true)
        //IdentifierType identifierType = new IdentifierType(...).save(flush: true, failOnError: true)
        //new IdentifierType(...).save(flush: true, failOnError: true)
        //new IdentifierType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //identifierType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        identifierTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<IdentifierType> identifierTypeList = identifierTypeService.list(max: 2, offset: 2)

        then:
        identifierTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        identifierTypeService.count() == 5
    }

    void "test delete"() {
        Long identifierTypeId = setupData()

        expect:
        identifierTypeService.count() == 5

        when:
        identifierTypeService.delete(identifierTypeId)
        datastore.currentSession.flush()

        then:
        identifierTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        IdentifierType identifierType = new IdentifierType()
        identifierTypeService.save(identifierType)

        then:
        identifierType.id != null
    }
}
