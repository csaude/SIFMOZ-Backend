package mz.org.fgh.sifmoz.backend.protection

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class SecUser implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String password
    String fullName
    String contact
    String email
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String[] roles

    static transients = ['roles']

    static hasMany = [clinics: Clinic, clinicSectors: ClinicSector]


    SecUser(String username,String password, String fullname, String contact,String email){
        this()
        this.username = username
        this.password = password
        this.fullName = fullname
        this.contact = contact
        this.email = email
    }

    Set<Role> getAuthorities() {
        (SecUserRole.findAllBySecUser(this) as List<SecUserRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        email nullable: true, blank: true
        roles bindable: true
    }

    static mapping = {
	    password column: '`password`'
    }
}
