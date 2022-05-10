package mz.org.fgh.sifmoz.backend.drug

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DrugClinicalServiceSpec extends Specification {

    IDrugService drugService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Drug(...).save(flush: true, failOnError: true)
        //new Drug(...).save(flush: true, failOnError: true)
        //Drug drug = new Drug(...).save(flush: true, failOnError: true)
        //new Drug(...).save(flush: true, failOnError: true)
        //new Drug(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //drug.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        drugService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Drug> drugList = drugService.list(max: 2, offset: 2)

        then:
        drugList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        drugService.count() == 5
    }

    void "test delete"() {
        Long drugId = setupData()

        expect:
        drugService.count() == 5

        when:
        drugService.delete(drugId)
        datastore.currentSession.flush()

        then:
        drugService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Drug drug = new Drug()
        drugService.save(drug)

        then:
        drug.id != null
    }
}
