package mz.org.fgh.sifmoz.backend.group

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class GroupInfoController extends RestfulController{

    IGroupService groupService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    GroupInfoController() {
        super(GroupInfo)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(groupService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(groupService.get(id)) as JSON
    }

    @Transactional
    def save(GroupInfo group) {
        if (group == null) {
            render status: NOT_FOUND
            return
        }
        if (group.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond group.errors
            return
        }

        try {
            groupService.save(group)
        } catch (ValidationException e) {
            respond group.errors
            return
        }

        respond group, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(GroupInfo group) {
        if (group == null) {
            render status: NOT_FOUND
            return
        }
        if (group.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond group.errors
            return
        }

        try {
            groupService.save(group)
        } catch (ValidationException e) {
            respond group.errors
            return
        }

        respond group, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || groupService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getByClinicId(String clinicId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(groupService.getAllByClinicId(clinicId, offset, max)) as JSON
        //respond patientService.getAllByClinicId(clinicId, offset, max)
    }
}
