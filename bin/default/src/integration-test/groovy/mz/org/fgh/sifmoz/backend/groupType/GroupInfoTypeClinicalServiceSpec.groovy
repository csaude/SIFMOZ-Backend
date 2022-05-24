package mz.org.fgh.sifmoz.backend.groupType

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class GroupInfoTypeClinicalServiceSpec extends Specification {

    GroupTypeService groupTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new GroupType(...).save(flush: true, failOnError: true)
        //new GroupType(...).save(flush: true, failOnError: true)
        //GroupType groupType = new GroupType(...).save(flush: true, failOnError: true)
        //new GroupType(...).save(flush: true, failOnError: true)
        //new GroupType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //groupType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        groupTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<GroupType> groupTypeList = groupTypeService.list(max: 2, offset: 2)

        then:
        groupTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        groupTypeService.count() == 5
    }

    void "test delete"() {
        Long groupTypeId = setupData()

        expect:
        groupTypeService.count() == 5

        when:
        groupTypeService.delete(groupTypeId)
        datastore.currentSession.flush()

        then:
        groupTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        GroupType groupType = new GroupType()
        groupTypeService.save(groupType)

        then:
        groupType.id != null
    }
}
