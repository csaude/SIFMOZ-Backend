package mz.org.fgh.sifmoz.backend.episodeType

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class EpisodeTypeServiceSpec extends Specification {

    EpisodeTypeService episodeTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new EpisodeType(...).save(flush: true, failOnError: true)
        //new EpisodeType(...).save(flush: true, failOnError: true)
        //EpisodeType episodeType = new EpisodeType(...).save(flush: true, failOnError: true)
        //new EpisodeType(...).save(flush: true, failOnError: true)
        //new EpisodeType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //episodeType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        episodeTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<EpisodeType> episodeTypeList = episodeTypeService.list(max: 2, offset: 2)

        then:
        episodeTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        episodeTypeService.count() == 5
    }

    void "test delete"() {
        Long episodeTypeId = setupData()

        expect:
        episodeTypeService.count() == 5

        when:
        episodeTypeService.delete(episodeTypeId)
        datastore.currentSession.flush()

        then:
        episodeTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        EpisodeType episodeType = new EpisodeType()
        episodeTypeService.save(episodeType)

        then:
        episodeType.id != null
    }
}
