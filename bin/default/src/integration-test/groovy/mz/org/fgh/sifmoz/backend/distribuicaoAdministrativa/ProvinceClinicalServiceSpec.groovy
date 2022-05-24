package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ProvinceClinicalServiceSpec extends Specification {

    ProvinceService provinceService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Province(...).save(flush: true, failOnError: true)
        //new Province(...).save(flush: true, failOnError: true)
        //Province province = new Province(...).save(flush: true, failOnError: true)
        //new Province(...).save(flush: true, failOnError: true)
        //new Province(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //province.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        provinceService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Province> provinceList = provinceService.list(max: 2, offset: 2)

        then:
        provinceList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        provinceService.count() == 5
    }

    void "test delete"() {
        Long provinceId = setupData()

        expect:
        provinceService.count() == 5

        when:
        provinceService.delete(provinceId)
        datastore.currentSession.flush()

        then:
        provinceService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Province province = new Province()
        provinceService.save(province)

        then:
        province.id != null
    }
}
