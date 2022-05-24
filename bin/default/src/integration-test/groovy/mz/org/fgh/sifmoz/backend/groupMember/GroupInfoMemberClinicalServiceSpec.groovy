package mz.org.fgh.sifmoz.backend.groupMember

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class GroupInfoMemberClinicalServiceSpec extends Specification {

    GroupMemberService groupMemberService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new GroupMember(...).save(flush: true, failOnError: true)
        //new GroupMember(...).save(flush: true, failOnError: true)
        //GroupMember groupMember = new GroupMember(...).save(flush: true, failOnError: true)
        //new GroupMember(...).save(flush: true, failOnError: true)
        //new GroupMember(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //groupMember.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        groupMemberService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<GroupMember> groupMemberList = groupMemberService.list(max: 2, offset: 2)

        then:
        groupMemberList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        groupMemberService.count() == 5
    }

    void "test delete"() {
        Long groupMemberId = setupData()

        expect:
        groupMemberService.count() == 5

        when:
        groupMemberService.delete(groupMemberId)
        datastore.currentSession.flush()

        then:
        groupMemberService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        GroupMember groupMember = new GroupMember()
        groupMemberService.save(groupMember)

        then:
        groupMember.id != null
    }
}
