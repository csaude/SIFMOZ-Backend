package mz.org.fgh.sifmoz.backend.group

import grails.gorm.services.Service

interface IGroupService {

    GroupInfo get(Serializable id)

    List<GroupInfo> list(Map args)

    Long count()

    GroupInfo delete(Serializable id)

    GroupInfo save(GroupInfo group)

    List<GroupInfo> getAllByClinicId(String clinicId, int offset, int max)

}
