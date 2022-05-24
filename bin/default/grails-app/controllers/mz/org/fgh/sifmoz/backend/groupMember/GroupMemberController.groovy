package mz.org.fgh.sifmoz.backend.groupMember

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class GroupMemberController extends RestfulController{

    GroupMemberService groupMemberService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    GroupMemberController() {
        super(GroupMember)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(groupMemberService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(groupMemberService.get(id)) as JSON
    }

    @Transactional
    def save(GroupMember groupMember) {
        if (groupMember == null) {
            render status: NOT_FOUND
            return
        }
        if (groupMember.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupMember.errors
            return
        }

        try {
            groupMemberService.save(groupMember)
        } catch (ValidationException e) {
            respond groupMember.errors
            return
        }

        respond groupMember, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(GroupMember groupMember) {
        if (groupMember == null) {
            render status: NOT_FOUND
            return
        }
        if (groupMember.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupMember.errors
            return
        }

        try {
            groupMemberService.save(groupMember)
        } catch (ValidationException e) {
            respond groupMember.errors
            return
        }

        respond groupMember, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || groupMemberService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
