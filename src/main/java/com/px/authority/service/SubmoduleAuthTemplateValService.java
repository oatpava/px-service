package com.px.authority.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.authority.daoimpl.SubmoduleAuthTemplateValDaoImpl;
import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.authority.entity.SubmoduleAuthTemplateVal;
import com.px.authority.model.SubmoduleAuthTemplateValModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class SubmoduleAuthTemplateValService implements GenericService<SubmoduleAuthTemplateVal, SubmoduleAuthTemplateValModel>{
    private static final Logger LOG = Logger.getLogger(SubmoduleAuthTemplateValService.class.getName());
    private final SubmoduleAuthTemplateValDaoImpl submoduleAuthTemplateValDaoImpl;

    public SubmoduleAuthTemplateValService() {
        this.submoduleAuthTemplateValDaoImpl = new SubmoduleAuthTemplateValDaoImpl();
    }

    @Override
    public SubmoduleAuthTemplateVal create(SubmoduleAuthTemplateVal submoduleAuthTemplateVal) {
        checkNotNull(submoduleAuthTemplateVal, "submoduleAuthTemplateVal entity must not be null");
        checkNotNull(submoduleAuthTemplateVal.getSubmoduleAuthTemplate(), "submoduleAuthTemplateVal submoduleAuthTemplate must not be null");
        checkNotNull(submoduleAuthTemplateVal.getSubmoduleAuth(), "submoduleAuthTemplateVal submoduleAuth must not be null");
        checkNotNull(submoduleAuthTemplateVal.getAuthority(), "submoduleAuthTemplateVal authority must not be null");
        checkNotNull(submoduleAuthTemplateVal.getCreatedBy(),"create by must not be null");
        submoduleAuthTemplateVal = submoduleAuthTemplateValDaoImpl.create(submoduleAuthTemplateVal);
        if(submoduleAuthTemplateVal.getOrderNo()==0){
            submoduleAuthTemplateVal.setOrderNo(submoduleAuthTemplateVal.getId());
            submoduleAuthTemplateVal = submoduleAuthTemplateValDaoImpl.update(submoduleAuthTemplateVal);
        }
        
        return submoduleAuthTemplateVal;
    }

    @Override
    public SubmoduleAuthTemplateVal getById(int id) {
        checkNotNull(id, "submoduleAuthTemplateVal id entity must not be null");
        return submoduleAuthTemplateValDaoImpl.getById(id);
    }

    @Override
    public SubmoduleAuthTemplateVal update(SubmoduleAuthTemplateVal submoduleAuthTemplateVal) {
        checkNotNull(submoduleAuthTemplateVal, "submoduleAuthTemplateVal entity must not be null");
        checkNotNull(submoduleAuthTemplateVal.getSubmoduleAuthTemplate(), "submoduleAuthTemplateVal submoduleAuthTemplate must not be null");
        checkNotNull(submoduleAuthTemplateVal.getSubmoduleAuth(), "submoduleAuthTemplateVal submoduleAuth must not be null");
        checkNotNull(submoduleAuthTemplateVal.getAuthority(), "submoduleAuthTemplateVal authority must not be null");
        checkNotNull(submoduleAuthTemplateVal.getUpdatedBy(),"update by must not be null");
        submoduleAuthTemplateVal.setUpdatedDate(LocalDateTime.now());
        return submoduleAuthTemplateValDaoImpl.update(submoduleAuthTemplateVal);
    }

    @Override
    public SubmoduleAuthTemplateVal remove(int id, int userId) {
        checkNotNull(id, "submoduleAuthTemplateVal id must not be null");
        SubmoduleAuthTemplateVal submoduleAuthTemplateVal = getById(id);
        checkNotNull(submoduleAuthTemplateVal, "submoduleAuthTemplateVal entity not found in database.");
        submoduleAuthTemplateVal.setRemovedBy(userId);
        submoduleAuthTemplateVal.setRemovedDate(LocalDateTime.now());
        return submoduleAuthTemplateValDaoImpl.update(submoduleAuthTemplateVal);
    }

    @Override
    public List<SubmoduleAuthTemplateVal> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return submoduleAuthTemplateValDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<SubmoduleAuthTemplateVal> listAll(String sort, String dir) {
        return submoduleAuthTemplateValDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return submoduleAuthTemplateValDaoImpl.countAll();
    }

    @Override
    public List<SubmoduleAuthTemplateVal> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubmoduleAuthTemplateValModel tranformToModel(SubmoduleAuthTemplateVal submoduleAuthTemplateVal) {
        SubmoduleAuthTemplateValModel submoduleAuthTemplateValModel = null;
        if(submoduleAuthTemplateVal!=null){
            SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            
            submoduleAuthTemplateValModel = new SubmoduleAuthTemplateValModel();
            submoduleAuthTemplateValModel.setId(submoduleAuthTemplateVal.getId());
            submoduleAuthTemplateValModel.setSubmoduleAuthTemplate(submoduleAuthTemplateService.tranformToModel(submoduleAuthTemplateVal.getSubmoduleAuthTemplate()));
            submoduleAuthTemplateValModel.setSubmoduleAuth(submoduleAuthService.tranformToModel(submoduleAuthTemplateVal.getSubmoduleAuth()));
            submoduleAuthTemplateValModel.setLinkId(submoduleAuthTemplateVal.getLinkId());
            submoduleAuthTemplateValModel.setAuthority(submoduleAuthTemplateVal.getAuthority());
        }
        return submoduleAuthTemplateValModel;
    }
    
    public List<SubmoduleAuthTemplateVal> listBySubmoduleAuthTemplate(SubmoduleAuthTemplate submoduleAuthTemplate,String sort, String dir) {
        checkNotNull(submoduleAuthTemplate, "submoduleAuthTemplate entity must not be null");
        return submoduleAuthTemplateValDaoImpl.listBySubmoduleAuthTemplate(submoduleAuthTemplate, sort, dir);
    }
    
    @Override
    public SubmoduleAuthTemplateVal getByIdNotRemoved(int id) {
        checkNotNull(id, "SubmoduleAuthTemplateVal id entity must not be null");
        return submoduleAuthTemplateValDaoImpl.getByIdNotRemoved(id);
    }
    
}
