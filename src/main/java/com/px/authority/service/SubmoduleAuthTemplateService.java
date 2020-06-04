package com.px.authority.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.Submodule;
import com.px.admin.service.SubmoduleService;
import com.px.authority.daoimpl.SubmoduleAuthTemplateDaoImpl;
import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.authority.model.SubmoduleAuthTemplateModel;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class SubmoduleAuthTemplateService implements GenericService<SubmoduleAuthTemplate, SubmoduleAuthTemplateModel>{
    private static final Logger LOG = Logger.getLogger(SubmoduleAuthTemplateService.class.getName());
    private final SubmoduleAuthTemplateDaoImpl submoduleAuthTemplateDaoImpl;

    public SubmoduleAuthTemplateService() {
        this.submoduleAuthTemplateDaoImpl = new SubmoduleAuthTemplateDaoImpl();
    }

    @Override
    public SubmoduleAuthTemplate create(SubmoduleAuthTemplate submoduleAuthTemplate) {
        checkNotNull(submoduleAuthTemplate, "submoduleAuthTemplate entity must not be null");
        checkNotNull(submoduleAuthTemplate.getSubmoduleAuthTemplateName(), "submoduleAuthTemplate name must not be null");
        checkNotNull(submoduleAuthTemplate.getSubmodule(), "submoduleAuthTemplate submodule must not be null");
        checkNotNull(submoduleAuthTemplate.getCreatedBy(),"create by must not be null");
        submoduleAuthTemplate = submoduleAuthTemplateDaoImpl.create(submoduleAuthTemplate);
        if(submoduleAuthTemplate.getOrderNo()==0){
            submoduleAuthTemplate.setOrderNo(submoduleAuthTemplate.getId());
            submoduleAuthTemplate.setUpdatedDate(LocalDateTime.now());
            submoduleAuthTemplate = submoduleAuthTemplateDaoImpl.update(submoduleAuthTemplate);
        }
        
        return submoduleAuthTemplate;
    }

    @Override
    public SubmoduleAuthTemplate getById(int id) {
        checkNotNull(id, "submoduleAuthTemplate id entity must not be null");
        return submoduleAuthTemplateDaoImpl.getById(id);
    }

    @Override
    public SubmoduleAuthTemplate update(SubmoduleAuthTemplate submoduleAuthTemplate) {
        checkNotNull(submoduleAuthTemplate, "submoduleAuthTemplate entity must not be null");
        checkNotNull(submoduleAuthTemplate.getSubmoduleAuthTemplateName(), "submoduleAuthTemplate name must not be null");
        checkNotNull(submoduleAuthTemplate.getSubmodule(), "submoduleAuthTemplate submodule must not be null");
        checkNotNull(submoduleAuthTemplate.getUpdatedBy(),"update by must not be null");
        submoduleAuthTemplate.setUpdatedDate(LocalDateTime.now());
        return submoduleAuthTemplateDaoImpl.update(submoduleAuthTemplate);
    }

    @Override
    public SubmoduleAuthTemplate remove(int id, int userId) {
        checkNotNull(id, "submoduleAuthTemplate id must not be null");
        SubmoduleAuthTemplate submoduleAuthTemplate = getById(id);
        checkNotNull(submoduleAuthTemplate, "submoduleAuthTemplate entity not found in database.");
        submoduleAuthTemplate.setRemovedBy(userId);
        submoduleAuthTemplate.setRemovedDate(LocalDateTime.now());
        return submoduleAuthTemplateDaoImpl.update(submoduleAuthTemplate);
    }

    @Override
    public List<SubmoduleAuthTemplate> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return submoduleAuthTemplateDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<SubmoduleAuthTemplate> listAll(String sort, String dir) {
        return submoduleAuthTemplateDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return submoduleAuthTemplateDaoImpl.countAll();
    }

    @Override
    public List<SubmoduleAuthTemplate> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubmoduleAuthTemplateModel tranformToModel(SubmoduleAuthTemplate submoduleAuthTemplate) {
        SubmoduleAuthTemplateModel submoduleAuthTemplateModel = null;
        if(submoduleAuthTemplate!=null){
            SubmoduleService submoduleService = new SubmoduleService();
            
            submoduleAuthTemplateModel = new SubmoduleAuthTemplateModel();
            submoduleAuthTemplateModel.setId(submoduleAuthTemplate.getId());
            submoduleAuthTemplateModel.setSubmoduleAuthTemplateName(submoduleAuthTemplate.getSubmoduleAuthTemplateName());
            submoduleAuthTemplateModel.setSubmodule(submoduleService.tranformToModel(submoduleAuthTemplate.getSubmodule()));
            submoduleAuthTemplateModel.setUpdatedDate(Common.localDateTimeToString2(submoduleAuthTemplate.getUpdatedDate()));
        }
        return submoduleAuthTemplateModel;
    }
    
    @Override
    public SubmoduleAuthTemplate getByIdNotRemoved(int id) {
        checkNotNull(id, "SubmoduleAuthTemplate id entity must not be null");
        return submoduleAuthTemplateDaoImpl.getByIdNotRemoved(id);
    }
    
    public List<SubmoduleAuthTemplate> getSubmoduleAuthTemplateBySubmodule(Submodule submodule,String sort, String dir){
        checkNotNull(submodule, "submodule list must not be null");
        return submoduleAuthTemplateDaoImpl.listBySubmodule(submodule, sort, dir);
    }
}
