package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.VUserProfileDaoImpl;
import com.px.admin.entity.VUserProfile;
import com.px.admin.model.VUserProfileModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author PRAXiS
 */
public class VUserProfileService implements GenericService<VUserProfile, VUserProfileModel>{
    private static final Logger LOG = Logger.getLogger(VUserProfileService.class.getName());
    private final VUserProfileDaoImpl vUserProfileDaoImpl;

    public VUserProfileService() {
        this.vUserProfileDaoImpl = new VUserProfileDaoImpl();
    }

    @Override
    public VUserProfile create(VUserProfile vUserProfile) {
//        checkNotNull(vUserProfile, "vStructure entity must not be null");
//        checkNotNull(vUserProfile.getUserProfileFirstName(), "vStructure name must not be null");
//        checkNotNull(vUserProfile.getCreatedBy(),"create by must not be null");
        vUserProfile = vUserProfileDaoImpl.create(vUserProfile);
        if(vUserProfile.getOrderNo()==0){
            vUserProfile.setOrderNo(vUserProfile.getId());
            vUserProfile = update(vUserProfile);
        }
        return vUserProfile;    
    }

    @Override
    public VUserProfile getById(int id) {
        checkNotNull(id, "vStructure id entity must not be null");
        return vUserProfileDaoImpl.getById(id);
    }
    
    public VUserProfile getByCode(String code) {
        checkNotNull(code, "vStructure id entity must not be null");
        return vUserProfileDaoImpl.getByCode(code);
    }

    @Override
    public VUserProfile getByIdNotRemoved(int id) {
        checkNotNull(id, "vStructure id entity must not be null");
        return vUserProfileDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public VUserProfile update(VUserProfile vUserProfile) {
        checkNotNull(vUserProfile, "vStructure entity must not be null");
        checkNotNull(vUserProfile.getUserProfileFirstName(), "vStructure name must not be null");
        checkNotNull(vUserProfile.getUpdatedBy(),"update by must not be null");
//        title.setUpdatedDate(Common.dateThaiToLocalDateTime("10/11/2559 15:45:35"));
        vUserProfile.setUpdatedDate(LocalDateTime.now());
        return vUserProfileDaoImpl.update(vUserProfile);
    }

    @Override
    public VUserProfile remove(int id, int userId) {
        checkNotNull(id, "vStructure id must not be null");
        VUserProfile vUserProfile = getById(id);
        checkNotNull(vUserProfile, "vStructure entity not found in database.");
        vUserProfile.setRemovedBy(userId);        
        vUserProfile.setRemovedDate(LocalDateTime.now());
        return vUserProfileDaoImpl.update(vUserProfile);
    }
    
    public void delete(VUserProfile vUserProfile) {
        vUserProfileDaoImpl.delete(vUserProfile);
    }

    @Override
    public List<VUserProfile> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return vUserProfileDaoImpl.list(offset, limit, sort, dir);
    }
    
    @Override
    public List<VUserProfile> listAll(String sort, String dir) {
        return vUserProfileDaoImpl.listAll(sort, dir);
    }
    
    public List<VUserProfile> listAllNotRemove(String sort, String dir) {
        return vUserProfileDaoImpl.listAllNotRemove(sort, dir);
    }
    
    public List<VUserProfile> listByName(String name, String sort, String dir) {
        return vUserProfileDaoImpl.listByName(name, sort, dir);
    }

    @Override
    public int countAll() {
        return vUserProfileDaoImpl.countAll();
    }

    @Override
    public List<VUserProfile> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VUserProfileModel tranformToModel(VUserProfile vUserProfile) {
        VUserProfileModel vUserProfileModel = null;
        if(vUserProfile!=null){
            vUserProfileModel = new VUserProfileModel();
            vUserProfileModel.setId(vUserProfile.getId());
            vUserProfileModel.setStructure(new StructureService().tranformToModel(vUserProfile.getStructure()));
            vUserProfileModel.setTitle(new TitleService().tranformToModel(vUserProfile.getTitle()));
            vUserProfileModel.setCode(vUserProfile.getUserProfileCode());
            vUserProfileModel.setFirstName(vUserProfile.getUserProfileFirstName());
            vUserProfileModel.setLastName(vUserProfile.getUserProfileLastName());
            vUserProfileModel.setFullName(vUserProfile.getUserProfileFullName());
            vUserProfileModel.setEmail(vUserProfile.getUserProfileEmail());
            vUserProfileModel.setUserProfileType(new UserProfileTypeService().tranformToModel(vUserProfile.getUserProfileType()));
            vUserProfileModel.setTel(vUserProfile.getUserProfileTel());
            vUserProfileModel.setFirstNameEng(vUserProfile.getUserProfileFirstNameEng());
            vUserProfileModel.setLastNameEng(vUserProfile.getUserProfileLastNameEng());
            vUserProfileModel.setFullNameEng(vUserProfile.getUserProfileFullNameEng());
            vUserProfileModel.setIdCard(vUserProfile.getUserProfileCardId());
            vUserProfileModel.setAddress(vUserProfile.getUserProfileAddress());
            vUserProfileModel.setPosition(new PositionService().tranformToModel(vUserProfile.getPosition()));
            vUserProfileModel.setPositionType(new PositionTypeService().tranformToModel(vUserProfile.getPositionType()));
            vUserProfileModel.setPositionLevel(vUserProfile.getPositionLevel());
            vUserProfileModel.setUserStatus(new UserStatusService().tranformToModel(vUserProfile.getUserProfileStatus()));
        }
        return vUserProfileModel;
    }
    
    
}
