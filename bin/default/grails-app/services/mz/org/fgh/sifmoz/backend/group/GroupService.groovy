package mz.org.fgh.sifmoz.backend.group

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic

@Transactional
@Service(GroupInfo)
abstract class GroupService implements IGroupService{

    @Override
    List<GroupInfo> getAllByClinicId(String clinicId, int offset, int max) {
        return GroupInfo.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
}
