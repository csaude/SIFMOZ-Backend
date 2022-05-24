package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PostoAdministrativoServiceSpec extends Specification {

    PostoAdministrativoService postoAdministrativoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PostoAdministrativo(...).save(flush: true, failOnError: true)
        //new PostoAdministrativo(...).save(flush: true, failOnError: true)
        //PostoAdministrativo postoAdministrativo = new PostoAdministrativo(...).save(flush: true, failOnError: true)
        //new PostoAdministrativo(...).save(flush: true, failOnError: true)
        //new PostoAdministrativo(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //postoAdministrativo.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        postoAdministrativoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PostoAdministrativo> postoAdministrativoList = postoAdministrativoService.list(max: 2, offset: 2)

        then:
        postoAdministrativoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        postoAdministrativoService.count() == 5
    }

    void "test delete"() {
        Long postoAdministrativoId = setupData()

        expect:
        postoAdministrativoService.count() == 5

        when:
        postoAdministrativoService.delete(postoAdministrativoId)
        datastore.currentSession.flush()

        then:
        postoAdministrativoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PostoAdministrativo postoAdministrativo = new PostoAdministrativo()
        postoAdministrativoService.save(postoAdministrativo)

        then:
        postoAdministrativo.id != null
    }
}
