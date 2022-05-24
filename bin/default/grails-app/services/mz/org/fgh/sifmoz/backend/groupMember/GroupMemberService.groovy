package mz.org.fgh.sifmoz.backend.groupMember

import grails.gorm.services.Service

@Service(GroupMember)
interface GroupMemberService {

    GroupMember get(Serializable id)

    List<GroupMember> list(Map args)

    Long count()

    GroupMember delete(Serializable id)

    GroupMember save(GroupMember groupMember)

}
