package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.VStructureDaoImpl;
import com.px.admin.entity.VStructure;
import com.px.admin.model.VStructureModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author PRAXiS
 */
public class VStructureService implements GenericService<VStructure, VStructureModel>{
    private static final Logger LOG = Logger.getLogger(VStructureService.class.getName());
    private final VStructureDaoImpl vStructureDaoImpl;

    public VStructureService() {
        this.vStructureDaoImpl = new VStructureDaoImpl();
    }

    @Override
    public VStructure create(VStructure vStructure) {
        checkNotNull(vStructure, "vStructure entity must not be null");
        checkNotNull(vStructure.getStructureName(), "vStructure name must not be null");
        checkNotNull(vStructure.getCreatedBy(),"create by must not be null");
        vStructure = vStructureDaoImpl.create(vStructure);
        if(vStructure.getOrderNo()==0){
            vStructure.setOrderNo(vStructure.getId());
            vStructure = update(vStructure);
        }
        return vStructure;    
    }

    @Override
    public VStructure getById(int id) {
        checkNotNull(id, "vStructure id entity must not be null");
        return vStructureDaoImpl.getById(id);
    }
    
    public VStructure getByCode(String code) {
        checkNotNull(code, "vStructure id entity must not be null");
        return vStructureDaoImpl.getByCode(code);
    }

    @Override
    public VStructure getByIdNotRemoved(int id) {
        checkNotNull(id, "vStructure id entity must not be null");
        return vStructureDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public VStructure update(VStructure vStructure) {
        checkNotNull(vStructure, "vStructure entity must not be null");
        checkNotNull(vStructure.getStructureName(), "vStructure name must not be null");
        checkNotNull(vStructure.getUpdatedBy(),"update by must not be null");
//        title.setUpdatedDate(Common.dateThaiToLocalDateTime("10/11/2559 15:45:35"));
        vStructure.setUpdatedDate(LocalDateTime.now());
        return vStructureDaoImpl.update(vStructure);
    }

    @Override
    public VStructure remove(int id, int userId) {
        checkNotNull(id, "vStructure id must not be null");
        VStructure vStructure = getById(id);
        checkNotNull(vStructure, "vStructure entity not found in database.");
        vStructure.setRemovedBy(userId);        
        vStructure.setRemovedDate(LocalDateTime.now());
        return vStructureDaoImpl.update(vStructure);
    }
    
    public void delete(VStructure vStructure) {
        vStructureDaoImpl.delete(vStructure);
    }

    @Override
    public List<VStructure> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return vStructureDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<VStructure> listAll(String sort, String dir) {
        return vStructureDaoImpl.listAll(sort, dir);
    }
    
    public List<VStructure> listAllNotRemove(String sort, String dir) {
        return vStructureDaoImpl.listAllNotRemove(sort, dir);
    }

    @Override
    public int countAll() {
        return vStructureDaoImpl.countAll();
    }

    @Override
    public List<VStructure> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VStructureModel tranformToModel(VStructure vStructure) {
        VStructureModel vStructureModel = null;
        if(vStructure!=null){
            vStructureModel = new VStructureModel();
            vStructureModel.setId(vStructure.getId());
            vStructureModel.setCode(vStructure.getStructureCode());
            vStructureModel.setName(vStructure.getStructureName());
            vStructureModel.setShortName(vStructure.getStructureShortName());
            vStructureModel.setDetail(vStructure.getStructureDetail());
        }
        return vStructureModel;
    }

}
