package mz.org.fgh.sifmoz.backend.serviceProgram

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ServiceIPatientVisitServiceSpec extends Specification {

    PatientProgramService patientProgramService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PatientProgram(...).save(flush: true, failOnError: true)
        //new PatientProgram(...).save(flush: true, failOnError: true)
        //PatientProgram patientProgram = new PatientProgram(...).save(flush: true, failOnError: true)
        //new PatientProgram(...).save(flush: true, failOnError: true)
        //new PatientProgram(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patientProgram.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patientProgramService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ServicePatient> patientProgramList = patientProgramService.list(max: 2, offset: 2)

        then:
        patientProgramList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patientProgramService.count() == 5
    }

    void "test delete"() {
        Long patientProgramId = setupData()

        expect:
        patientProgramService.count() == 5

        when:
        patientProgramService.delete(patientProgramId)
        datastore.currentSession.flush()

        then:
        patientProgramService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ServicePatient patientProgram = new ServicePatient()
        patientProgramService.save(patientProgram)

        then:
        patientProgram.id != null
    }
}
