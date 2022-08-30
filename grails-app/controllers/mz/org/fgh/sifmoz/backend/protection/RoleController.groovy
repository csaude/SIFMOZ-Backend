package mz.org.fgh.sifmoz.backend.protection

import grails.artefact.DomainClass
import grails.converters.JSON
import grails.core.GrailsApplication
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import org.apache.commons.lang.StringUtils
import org.apache.commons.text.CaseUtils

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class RoleController {

    RoleService roleService

    RequestmapService requestmapService

    GrailsApplication grailsApplication
    SpringSecurityService springSecurityService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(roleService.list(params)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(roleService.get(id)) as JSON
    }

    @Transactional
    def save(Role role) {
        if (role == null) {
            render status: NOT_FOUND
            return
        }
        if (role.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond role.errors7
            return
        }
7
        try {77

            if(role.active == true) {
                for(Menu menu: role.menus) {
                    List<Class> clazzes =  grailsApplication.getArtefacts("Domain")*.clazz

                    for(Class clazz : clazzes) {
                        if(!java.lang.reflect.Modifier.isAbstract(clazz.getModifiers()) && clazz.newInstance() instanceof BaseEntity) {
                            List<Menu> menus = clazz.newInstance().hasMenus()
                            if(menus.contains(menu)) {
                             //   String name = '%'+clazz.simpleName+'%'
                              //  String camelSimpleName = CaseUtils.toCamelCase(clazz.simpleName,false)
                                def name = '%'+'/api'+'/'+clazz.simpleName+'/**'+'%'
                                Requestmap requestMap = Requestmap.findByUrlIlike(name)
                                println(name)
                                println(requestMap)
                                String actualConfigAttribute = requestMap.configAttribute
                                String newConfigAttribute = role.authority+","+actualConfigAttribute
                                requestMap.setConfigAttribute(newConfigAttribute)
                                requestmapService.save(requestMap)
                            }
                        }
                    }
                }
            }
            roleService.save(role)

        } catch (ValidationException e) {
            respond role.errors
            return
        }
        springSecurityService.clearCachedRequestmaps()
        respond role, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Role role) {
        if (role == null) {
            render status: NOT_FOUND
            return
        }
        if (role.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond role.errors
            return
        }

        try {
            roleService.save(role)
        } catch (ValidationException e) {
            respond role.errors
            return
        }

        respond role, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || roleService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
