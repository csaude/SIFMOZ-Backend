package mz.org.fgh.sifmoz.backend.form

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class FormServiceSpec extends Specification {

    FormService formService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Form(...).save(flush: true, failOnError: true)
        //new Form(...).save(flush: true, failOnError: true)
        //Form form = new Form(...).save(flush: true, failOnError: true)
        //new Form(...).save(flush: true, failOnError: true)
        //new Form(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //form.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        formService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Form> formList = formService.list(max: 2, offset: 2)

        then:
        formList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        formService.count() == 5
    }

    void "test delete"() {
        Long formId = setupData()

        expect:
        formService.count() == 5

        when:
        formService.delete(formId)
        datastore.currentSession.flush()

        then:
        formService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Form form = new Form()
        formService.save(form)

        then:
        form.id != null
    }
}
