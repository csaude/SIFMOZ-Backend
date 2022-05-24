package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PatientServiceIdentifierServiceSpec extends Specification {

    PatientServiceIdentifierService patientServiceIdentifierService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PatientServiceIdentifier(...).save(flush: true, failOnError: true)
        //new PatientServiceIdentifier(...).save(flush: true, failOnError: true)
        //PatientServiceIdentifier patientServiceIdentifier = new PatientServiceIdentifier(...).save(flush: true, failOnError: true)
        //new PatientServiceIdentifier(...).save(flush: true, failOnError: true)
        //new PatientServiceIdentifier(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patientServiceIdentifier.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patientServiceIdentifierService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PatientServiceIdentifier> patientServiceIdentifierList = patientServiceIdentifierService.list(max: 2, offset: 2)

        then:
        patientServiceIdentifierList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patientServiceIdentifierService.count() == 5
    }

    void "test delete"() {
        Long patientServiceIdentifierId = setupData()

        expect:
        patientServiceIdentifierService.count() == 5

        when:
        patientServiceIdentifierService.delete(patientServiceIdentifierId)
        datastore.currentSession.flush()

        then:
        patientServiceIdentifierService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PatientServiceIdentifier patientServiceIdentifier = new PatientServiceIdentifier()
        patientServiceIdentifierService.save(patientServiceIdentifier)

        then:
        patientServiceIdentifier.id != null
    }
}
