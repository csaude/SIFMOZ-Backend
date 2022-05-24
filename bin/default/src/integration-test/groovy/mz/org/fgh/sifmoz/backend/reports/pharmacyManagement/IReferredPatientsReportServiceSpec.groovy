package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import mz.org.fgh.sifmoz.backend.reports.referralManagement.IReferredPatientsReportService
import mz.org.fgh.sifmoz.backend.reports.referralManagement.ReferredPatientsReport
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class IReferredPatientsReportServiceSpec extends Specification {

    IReferredPatientsReportService referredPatientsReportService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ReferredPatientsReport(...).save(flush: true, failOnError: true)
        //new ReferredPatientsReport(...).save(flush: true, failOnError: true)
        //ReferredPatientsReport referredPatientsReport = new ReferredPatientsReport(...).save(flush: true, failOnError: true)
        //new ReferredPatientsReport(...).save(flush: true, failOnError: true)
        //new ReferredPatientsReport(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //referredPatientsReport.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        referredPatientsReportService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReferredPatientsReport> referredPatientsReportList = referredPatientsReportService.list(max: 2, offset: 2)

        then:
        referredPatientsReportList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        referredPatientsReportService.count() == 5
    }

    void "test delete"() {
        Long referredPatientsReportId = setupData()

        expect:
        referredPatientsReportService.count() == 5

        when:
        referredPatientsReportService.delete(referredPatientsReportId)
        datastore.currentSession.flush()

        then:
        referredPatientsReportService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ReferredPatientsReport referredPatientsReport = new ReferredPatientsReport()
        referredPatientsReportService.save(referredPatientsReport)

        then:
        referredPatientsReport.id != null
    }
}
