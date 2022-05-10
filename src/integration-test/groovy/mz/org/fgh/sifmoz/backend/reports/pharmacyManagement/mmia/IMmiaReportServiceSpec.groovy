package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class IMmiaReportServiceSpec extends Specification {

    IMmiaReportService mmiaReportService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MmiaReport(...).save(flush: true, failOnError: true)
        //new MmiaReport(...).save(flush: true, failOnError: true)
        //MmiaReport mmiaReport = new MmiaReport(...).save(flush: true, failOnError: true)
        //new MmiaReport(...).save(flush: true, failOnError: true)
        //new MmiaReport(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //mmiaReport.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        mmiaReportService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MmiaReport> mmiaReportList = mmiaReportService.list(max: 2, offset: 2)

        then:
        mmiaReportList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        mmiaReportService.count() == 5
    }

    void "test delete"() {
        Long mmiaReportId = setupData()

        expect:
        mmiaReportService.count() == 5

        when:
        mmiaReportService.delete(mmiaReportId)
        datastore.currentSession.flush()

        then:
        mmiaReportService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MmiaReport mmiaReport = new MmiaReport()
        mmiaReportService.save(mmiaReport)

        then:
        mmiaReport.id != null
    }
}
