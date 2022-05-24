package mz.org.fgh.sifmoz.backend.episode

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class EpisodeServiceSpec extends Specification {

    EpisodeService episodeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Episode(...).save(flush: true, failOnError: true)
        //new Episode(...).save(flush: true, failOnError: true)
        //Episode episode = new Episode(...).save(flush: true, failOnError: true)
        //new Episode(...).save(flush: true, failOnError: true)
        //new Episode(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //episode.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        episodeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Episode> episodeList = episodeService.list(max: 2, offset: 2)

        then:
        episodeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        episodeService.count() == 5
    }

    void "test delete"() {
        Long episodeId = setupData()

        expect:
        episodeService.count() == 5

        when:
        episodeService.delete(episodeId)
        datastore.currentSession.flush()

        then:
        episodeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Episode episode = new Episode()
        episodeService.save(episode)

        then:
        episode.id != null
    }
}
