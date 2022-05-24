package mz.org.fgh.sifmoz.backend.prescriptionDetail

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PrescriptionDetailClinicalServiceSpec extends Specification {

    IPrescriptionDetailService prescriptionDetailService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PrescriptionDetail(...).save(flush: true, failOnError: true)
        //new PrescriptionDetail(...).save(flush: true, failOnError: true)
        //PrescriptionDetail prescriptionDetail = new PrescriptionDetail(...).save(flush: true, failOnError: true)
        //new PrescriptionDetail(...).save(flush: true, failOnError: true)
        //new PrescriptionDetail(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //prescriptionDetail.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        prescriptionDetailService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PrescriptionDetail> prescriptionDetailList = prescriptionDetailService.list(max: 2, offset: 2)

        then:
        prescriptionDetailList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        prescriptionDetailService.count() == 5
    }

    void "test delete"() {
        Long prescriptionDetailId = setupData()

        expect:
        prescriptionDetailService.count() == 5

        when:
        prescriptionDetailService.delete(prescriptionDetailId)
        datastore.currentSession.flush()

        then:
        prescriptionDetailService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PrescriptionDetail prescriptionDetail = new PrescriptionDetail()
        prescriptionDetailService.save(prescriptionDetail)

        then:
        prescriptionDetail.id != null
    }
}
