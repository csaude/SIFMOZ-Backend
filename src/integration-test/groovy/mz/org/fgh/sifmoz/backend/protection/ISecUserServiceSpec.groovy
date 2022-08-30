package mz.org.fgh.sifmoz.backend.protection

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ISecUserServiceSpec extends Specification {

    ISecUserService secUserService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new SecUser(...).save(flush: true, failOnError: true)
        //new SecUser(...).save(flush: true, failOnError: true)
        //SecUser secUser = new SecUser(...).save(flush: true, failOnError: true)
        //new SecUser(...).save(flush: true, failOnError: true)
        //new SecUser(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //secUser.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        secUserService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SecUser> secUserList = secUserService.list(max: 2, offset: 2)

        then:
        secUserList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        secUserService.count() == 5
    }

    void "test delete"() {
        Long secUserId = setupData()

        expect:
        secUserService.count() == 5

        when:
        secUserService.delete(secUserId)
        datastore.currentSession.flush()

        then:
        secUserService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SecUser secUser = new SecUser()
        secUserService.save(secUser)

        then:
        secUser.id != null
    }
}
