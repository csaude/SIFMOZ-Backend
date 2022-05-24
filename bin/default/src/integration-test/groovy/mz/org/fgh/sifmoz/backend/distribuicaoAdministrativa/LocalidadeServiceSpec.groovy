package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class LocalidadeServiceSpec extends Specification {

    LocalidadeService localidadeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Localidade(...).save(flush: true, failOnError: true)
        //new Localidade(...).save(flush: true, failOnError: true)
        //Localidade localidade = new Localidade(...).save(flush: true, failOnError: true)
        //new Localidade(...).save(flush: true, failOnError: true)
        //new Localidade(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //localidade.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        localidadeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Localidade> localidadeList = localidadeService.list(max: 2, offset: 2)

        then:
        localidadeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        localidadeService.count() == 5
    }

    void "test delete"() {
        Long localidadeId = setupData()

        expect:
        localidadeService.count() == 5

        when:
        localidadeService.delete(localidadeId)
        datastore.currentSession.flush()

        then:
        localidadeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Localidade localidade = new Localidade()
        localidadeService.save(localidade)

        then:
        localidade.id != null
    }
}
