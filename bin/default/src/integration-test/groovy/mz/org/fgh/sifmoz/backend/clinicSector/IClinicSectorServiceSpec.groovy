package mz.org.fgh.sifmoz.backend.clinicSector

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class IClinicSectorServiceSpec extends Specification {

    IClinicSectorService clinicSectorService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ClinicSector(...).save(flush: true, failOnError: true)
        //new ClinicSector(...).save(flush: true, failOnError: true)
        //ClinicSector clinicSector = new ClinicSector(...).save(flush: true, failOnError: true)
        //new ClinicSector(...).save(flush: true, failOnError: true)
        //new ClinicSector(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //clinicSector.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        clinicSectorService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ClinicSector> clinicSectorList = clinicSectorService.list(max: 2, offset: 2)

        then:
        clinicSectorList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        clinicSectorService.count() == 5
    }

    void "test delete"() {
        Long clinicSectorId = setupData()

        expect:
        clinicSectorService.count() == 5

        when:
        clinicSectorService.delete(clinicSectorId)
        datastore.currentSession.flush()

        then:
        clinicSectorService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ClinicSector clinicSector = new ClinicSector()
        clinicSectorService.save(clinicSector)

        then:
        clinicSector.id != null
    }
}
