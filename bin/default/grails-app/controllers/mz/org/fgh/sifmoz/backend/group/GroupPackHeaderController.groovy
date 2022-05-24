package mz.org.fgh.sifmoz.backend.group

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class GroupPackHeaderController extends RestfulController{

    IGroupPackHeaderService groupPackHeaderService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    GroupPackHeaderController () {
        super(GroupPackHeader)
    }
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond groupPackHeaderService.list(params), model:[groupPackHeaderCount: groupPackHeaderService.count()]
    }

    def show(String id) {
        respond groupPackHeaderService.get(id)
    }

    @Transactional
    def save(GroupPackHeader groupPackHeader) {
        if (groupPackHeader == null) {
            render status: NOT_FOUND
            return
        }
        if (groupPackHeader.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupPackHeader.errors
            return
        }

        try {
            groupPackHeaderService.save(groupPackHeader)
        } catch (ValidationException e) {
            respond groupPackHeader.errors
            return
        }

        respond groupPackHeader, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(GroupPackHeader groupPackHeader) {
        if (groupPackHeader == null) {
            render status: NOT_FOUND
            return
        }
        if (groupPackHeader.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupPackHeader.errors
            return
        }

        try {
            groupPackHeaderService.save(groupPackHeader)
        } catch (ValidationException e) {
            respond groupPackHeader.errors
            return
        }

        respond groupPackHeader, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null || groupPackHeaderService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
