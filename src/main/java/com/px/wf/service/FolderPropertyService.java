package com.px.wf.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.wf.daoimpl.FolderPropertyDaoImpl;
import com.px.wf.entity.FolderProperty;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import com.px.wf.model.FolderPropertyModel;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class FolderPropertyService implements GenericService<FolderProperty, FolderPropertyModel> {

    private static final Logger log = Logger.getLogger(FolderPropertyService.class.getName());
    private final FolderPropertyDaoImpl FolderPropertyDaoImpl;

    public FolderPropertyService() {
        this.FolderPropertyDaoImpl = new FolderPropertyDaoImpl();
    }

    public List<FolderProperty> listByType(int type,int folderId,String folderPropertyType) {
        checkNotNull(type, "type must not be null");
        checkNotNull(folderId, "folderId must not be null");
        checkNotNull(folderPropertyType, "folderPropertyType must not be null");
        return FolderPropertyDaoImpl.listByType(type,folderId,folderPropertyType);
    }

    @Override
    public FolderProperty create(FolderProperty folderProperty) {
        checkNotNull(folderProperty, "folderProperty entity must not be null");
        checkNotNull(folderProperty.getCreatedBy(), "create by must not be null");
        return FolderPropertyDaoImpl.create(folderProperty);
    }

    @Override
    public FolderProperty getById(int id) {
        return FolderPropertyDaoImpl.getById(id);
    }

    @Override
    public FolderProperty update(FolderProperty folderProperty) {
        checkNotNull(folderProperty, "folderProperty entity must not be null");
        checkNotNull(folderProperty.getUpdatedBy(), "update by must not be null");
        folderProperty.setUpdatedDate(LocalDateTime.now());
        return FolderPropertyDaoImpl.update(folderProperty);
    }

    @Override
    public FolderProperty remove(int id, int userId) {
        checkNotNull(id, "folderProperty id must not be null");
        FolderProperty folderProperty = getById(id);
        checkNotNull(folderProperty, "folderProperty entity not found in database.");
        folderProperty.setRemovedBy(userId);
        folderProperty.setRemovedDate(LocalDateTime.now());
        return FolderPropertyDaoImpl.update(folderProperty);
    }

    @Override
    public List<FolderProperty> list(int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FolderProperty> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return FolderPropertyDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FolderProperty> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FolderPropertyModel tranformToModel(FolderProperty folderProperty) {
        FolderPropertyModel folderPropertyModel = null;
        if (folderProperty != null) {
            folderPropertyModel = new FolderPropertyModel(folderProperty.getId());
            folderPropertyModel.setCreatedBy(folderProperty.getCreatedBy());
            folderPropertyModel.setCreatedDate(Common.localDateTimeToString(folderProperty.getCreatedDate()));
            folderPropertyModel.setOrderNo((float) folderProperty.getOrderNo());
            folderPropertyModel.setRemovedBy(folderProperty.getRemovedBy());
            folderPropertyModel.setRemovedDate(Common.localDateTimeToString(folderProperty.getRemovedDate()));
            folderPropertyModel.setUpdatedBy(folderProperty.getUpdatedBy());
            folderPropertyModel.setUpdatedDate(Common.localDateTimeToString(folderProperty.getUpdatedDate()));
            folderPropertyModel.setFolderPropertyFolderId(folderProperty.getFolderPropertyFolderId());
            folderPropertyModel.setFolderPropertyFieldId(folderProperty.getFolderPropertyFieldId());
            folderPropertyModel.setFolderPropertyType(folderProperty.getFolderPropertyType());
            folderPropertyModel.setFolderPropertyFieldName(folderProperty.getFolderPropertyFieldName());
            folderPropertyModel.setFolderPropertyFieldDescription(folderProperty.getFolderPropertyFieldDescription());
            folderPropertyModel.setFolderPropertyFieldType(folderProperty.getFolderPropertyFieldType());
            folderPropertyModel.setFolderPropertyFieldLength(folderProperty.getFolderPropertyFieldLength());
            folderPropertyModel.setFolderPropertyPList(folderProperty.getFolderPropertyPList());
            folderPropertyModel.setFolderPropertyPListWidth(folderProperty.getFolderPropertyPListWidth());
            folderPropertyModel.setFolderPropertyPSearch(folderProperty.getFolderPropertyPSearch());
            folderPropertyModel.setFolderPropertyPView(folderProperty.getFolderPropertyPView());
            folderPropertyModel.setFolderPropertyPLookupId(folderProperty.getFolderPropertyPLookupId());
        }
        return folderPropertyModel;
    }

    @Override
    public FolderProperty getByIdNotRemoved(int id) {
        checkNotNull(id, "FolderProperty id entity must not be null");
        return FolderPropertyDaoImpl.getByIdNotRemoved(id);
    }

    public boolean checkFolderIdNoData(int folderId) {
        checkNotNull(folderId, "folderId must not be null");
        return FolderPropertyDaoImpl.checkFolderIdNoData(folderId);
    }

}
