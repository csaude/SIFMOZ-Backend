package mz.org.fgh.sifmoz.backend.therapeuticRegimen

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TherapeuticRegimenClinicalServiceSpec extends Specification {

    TherapeuticRegimenService therapeuticRegimenService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TherapeuticRegimen(...).save(flush: true, failOnError: true)
        //new TherapeuticRegimen(...).save(flush: true, failOnError: true)
        //TherapeuticRegimen therapeuticRegimen = new TherapeuticRegimen(...).save(flush: true, failOnError: true)
        //new TherapeuticRegimen(...).save(flush: true, failOnError: true)
        //new TherapeuticRegimen(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //therapeuticRegimen.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        therapeuticRegimenService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TherapeuticRegimen> therapeuticRegimenList = therapeuticRegimenService.list(max: 2, offset: 2)

        then:
        therapeuticRegimenList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        therapeuticRegimenService.count() == 5
    }

    void "test delete"() {
        Long therapeuticRegimenId = setupData()

        expect:
        therapeuticRegimenService.count() == 5

        when:
        therapeuticRegimenService.delete(therapeuticRegimenId)
        datastore.currentSession.flush()

        then:
        therapeuticRegimenService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TherapeuticRegimen therapeuticRegimen = new TherapeuticRegimen()
        therapeuticRegimenService.save(therapeuticRegimen)

        then:
        therapeuticRegimen.id != null
    }
}
