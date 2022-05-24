package mz.org.fgh.sifmoz.backend.packagedDrug

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PackagedDrugClinicalServiceSpec extends Specification {

    IPackagedDrugService packagedDrugService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PackagedDrug(...).save(flush: true, failOnError: true)
        //new PackagedDrug(...).save(flush: true, failOnError: true)
        //PackagedDrug packagedDrug = new PackagedDrug(...).save(flush: true, failOnError: true)
        //new PackagedDrug(...).save(flush: true, failOnError: true)
        //new PackagedDrug(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //packagedDrug.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        packagedDrugService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PackagedDrug> packagedDrugList = packagedDrugService.list(max: 2, offset: 2)

        then:
        packagedDrugList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        packagedDrugService.count() == 5
    }

    void "test delete"() {
        Long packagedDrugId = setupData()

        expect:
        packagedDrugService.count() == 5

        when:
        packagedDrugService.delete(packagedDrugId)
        datastore.currentSession.flush()

        then:
        packagedDrugService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PackagedDrug packagedDrug = new PackagedDrug()
        packagedDrugService.save(packagedDrug)

        then:
        packagedDrug.id != null
    }
}
