package mz.org.fgh.sifmoz.backend.regimenDrug

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class RegimenDrugServiceSpec extends Specification {

    RegimenDrugService regimenDrugService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new RegimenDrug(...).save(flush: true, failOnError: true)
        //new RegimenDrug(...).save(flush: true, failOnError: true)
        //RegimenDrug regimenDrug = new RegimenDrug(...).save(flush: true, failOnError: true)
        //new RegimenDrug(...).save(flush: true, failOnError: true)
        //new RegimenDrug(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //regimenDrug.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        regimenDrugService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<RegimenDrug> regimenDrugList = regimenDrugService.list(max: 2, offset: 2)

        then:
        regimenDrugList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        regimenDrugService.count() == 5
    }

    void "test delete"() {
        Long regimenDrugId = setupData()

        expect:
        regimenDrugService.count() == 5

        when:
        regimenDrugService.delete(regimenDrugId)
        datastore.currentSession.flush()

        then:
        regimenDrugService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        RegimenDrug regimenDrug = new RegimenDrug()
        regimenDrugService.save(regimenDrug)

        then:
        regimenDrug.id != null
    }
}
