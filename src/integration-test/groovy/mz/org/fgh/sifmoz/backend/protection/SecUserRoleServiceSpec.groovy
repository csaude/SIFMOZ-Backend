package mz.org.fgh.sifmoz.backend.protection

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class SecUserRoleServiceSpec extends Specification {

    SecUserRoleService secUserRoleService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new SecUserRole(...).save(flush: true, failOnError: true)
        //new SecUserRole(...).save(flush: true, failOnError: true)
        //SecUserRole secUserRole = new SecUserRole(...).save(flush: true, failOnError: true)
        //new SecUserRole(...).save(flush: true, failOnError: true)
        //new SecUserRole(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //secUserRole.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        secUserRoleService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SecUserRole> secUserRoleList = secUserRoleService.list(max: 2, offset: 2)

        then:
        secUserRoleList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        secUserRoleService.count() == 5
    }

    void "test delete"() {
        Long secUserRoleId = setupData()

        expect:
        secUserRoleService.count() == 5

        when:
        secUserRoleService.delete(secUserRoleId)
        datastore.currentSession.flush()

        then:
        secUserRoleService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SecUserRole secUserRole = new SecUserRole()
        secUserRoleService.save(secUserRole)

        then:
        secUserRole.id != null
    }
}
