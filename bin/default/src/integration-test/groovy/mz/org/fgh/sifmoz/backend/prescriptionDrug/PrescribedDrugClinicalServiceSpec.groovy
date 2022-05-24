package mz.org.fgh.sifmoz.backend.prescriptionDrug

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PrescribedDrugClinicalServiceSpec extends Specification {

    IPrescribedDrugService prescribedDrugService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PrescribedDrug(...).save(flush: true, failOnError: true)
        //new PrescribedDrug(...).save(flush: true, failOnError: true)
        //PrescribedDrug prescribedDrug = new PrescribedDrug(...).save(flush: true, failOnError: true)
        //new PrescribedDrug(...).save(flush: true, failOnError: true)
        //new PrescribedDrug(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //prescribedDrug.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        prescribedDrugService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PrescribedDrug> prescribedDrugList = prescribedDrugService.list(max: 2, offset: 2)

        then:
        prescribedDrugList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        prescribedDrugService.count() == 5
    }

    void "test delete"() {
        Long prescribedDrugId = setupData()

        expect:
        prescribedDrugService.count() == 5

        when:
        prescribedDrugService.delete(prescribedDrugId)
        datastore.currentSession.flush()

        then:
        prescribedDrugService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PrescribedDrug prescribedDrug = new PrescribedDrug()
        prescribedDrugService.save(prescribedDrug)

        then:
        prescribedDrug.id != null
    }
}
