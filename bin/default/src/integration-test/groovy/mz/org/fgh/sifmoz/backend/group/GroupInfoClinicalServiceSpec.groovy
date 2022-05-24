package mz.org.fgh.sifmoz.backend.group

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class GroupInfoClinicalServiceSpec extends Specification {

    IGroupService groupService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Group(...).save(flush: true, failOnError: true)
        //new Group(...).save(flush: true, failOnError: true)
        //Group group = new Group(...).save(flush: true, failOnError: true)
        //new Group(...).save(flush: true, failOnError: true)
        //new Group(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //group.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        groupService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<GroupInfo> groupList = groupService.list(max: 2, offset: 2)

        then:
        groupList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        groupService.count() == 5
    }

    void "test delete"() {
        Long groupId = setupData()

        expect:
        groupService.count() == 5

        when:
        groupService.delete(groupId)
        datastore.currentSession.flush()

        then:
        groupService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        GroupInfo group = new GroupInfo()
        groupService.save(group)

        then:
        group.id != null
    }
}
