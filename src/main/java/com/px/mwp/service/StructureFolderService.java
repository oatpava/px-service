package com.px.mwp.service;

import com.px.mwp.daoimpl.StructureFolderDaoImpl;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.mwp.model.StructureFolderModel;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.mwp.entity.StructureFolder;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class StructureFolderService implements GenericService<StructureFolder, StructureFolderModel> {
    
    private static final Logger log = Logger.getLogger(UserProfileService.class.getName());
    private final StructureFolderDaoImpl structureFolderDaoImpl;
    
    public StructureFolderService() {
        this.structureFolderDaoImpl = new StructureFolderDaoImpl();
    }
    
    @Override
    public StructureFolder create(StructureFolder structureFolder) {
        checkNotNull(structureFolder, "structureFolder entity must not be null");
        checkNotNull(structureFolder.getCreatedBy(), "create by must not be null");
        return structureFolderDaoImpl.create(structureFolder);
    }
    
    @Override
    public StructureFolder getById(int id) {
        return structureFolderDaoImpl.getById(id);
    }
    
    @Override
    public StructureFolder update(StructureFolder structureFolder) {
        checkNotNull(structureFolder, "structureFolder entity must not be null");
        checkNotNull(structureFolder.getUpdatedBy(), "update by must not be null");
        structureFolder.setUpdatedDate(LocalDateTime.now());
        return structureFolderDaoImpl.update(structureFolder);
    }
    
    @Override
    public StructureFolder remove(int id, int userId) {
        checkNotNull(id, "inbox id must not be null");
        StructureFolder structureFolder = getById(id);
        checkNotNull(structureFolder, "structureFolder entity not found in database.");
        structureFolder.setRemovedBy(userId);
        structureFolder.setRemovedDate(LocalDateTime.now());
        return structureFolderDaoImpl.update(structureFolder);
    }
    
    @Override
    public List<StructureFolder> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<StructureFolder> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<StructureFolder> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public StructureFolderModel tranformToModel(StructureFolder structureFolder) {
        StructureFolderModel structureFolderModel = null;
        if (structureFolder != null) {
            StructureService structureService = new StructureService();
            structureFolderModel = new StructureFolderModel();
            structureFolderModel.setId(structureFolder.getId());
            structureFolderModel.setCreatedBy(structureFolder.getCreatedBy());
            structureFolderModel.setCreatedDate(Common.localDateTimeToString(structureFolder.getCreatedDate()));
            structureFolderModel.setOrderNo((float) structureFolder.getOrderNo());
            structureFolderModel.setUpdatedBy(structureFolder.getUpdatedBy());
            structureFolderModel.setUpdatedDate(Common.localDateTimeToString(structureFolder.getUpdatedDate()));
            structureFolderModel.setRemovedBy(structureFolder.getRemovedBy());
            structureFolderModel.setRemovedDate(Common.localDateTimeToString(structureFolder.getRemovedDate()));
            structureFolderModel.setStructureFolderDetail(structureFolder.getStructureFolderDetail());
            structureFolderModel.setStructureFolderLinkId(structureFolder.getStructureFolderLinkId());
            structureFolderModel.setStructureFolderName(structureFolder.getStructureFolderName());
            structureFolderModel.setStructureFolderType(structureFolder.getStructureFolderType());
            structureFolderModel.setStructureId(structureFolder.getStructureId());
//            structureFolderModel.setStructure(structureService.tranformToModel(structureFolder.getStructure()));
        }
        return structureFolderModel;
    }
    
    public List<StructureFolder> listByStructureId(int structureId) {
        checkNotNull(structureId, "structureId must not be null");
        return structureFolderDaoImpl.listByStructureId(structureId);
    }
    
    public List<StructureFolder> listByStructureId(int structureId,String type) {
        checkNotNull(structureId, "structureId must not be null");
        checkNotNull(type, "type must not be null");
        return structureFolderDaoImpl.listByStructureId(structureId,type);
    }
    
    @Override
    public StructureFolder getByIdNotRemoved(int id) {
        checkNotNull(id, "StructureFolder id entity must not be null");
        return structureFolderDaoImpl.getByIdNotRemoved(id);
    }
    
    public StructureFolder getByStructureId(int structureId, String type) {
        return structureFolderDaoImpl.getByStructureId(structureId, type);
    }
}
