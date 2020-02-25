package com.px.wf.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.wf.daoimpl.ThegifDocFileDaoImpl;
import com.px.share.entity.Param;
import com.px.wf.entity.ThegifDocFile;
import com.px.share.service.GenericService;
import com.px.wf.model.get.ThegifDocFileModel;
import com.px.wf.model.get.ThegifDocFileModel_andPathFile;
import com.px.share.service.ParamService;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author TUMKUNG
 */

public class ThegifDocFileService implements GenericService<ThegifDocFile, ThegifDocFileModel> {

    private final ThegifDocFileDaoImpl thegifDocFileDaoImpl;

    public ThegifDocFileService() {
        this.thegifDocFileDaoImpl = new ThegifDocFileDaoImpl();
    }

    @Override
    public ThegifDocFile create(ThegifDocFile thegifDocFile) {
        checkNotNull(thegifDocFile, "thegifDocFile entity must not be null");
        checkNotNull(thegifDocFile.getCreatedBy(), "thegifDocFile by must not be null");
        return thegifDocFileDaoImpl.create(thegifDocFile);
    }

    @Override
    public ThegifDocFile getById(int id) {
        return thegifDocFileDaoImpl.getById(id);
    }

    @Override
    public ThegifDocFile getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ThegifDocFile update(ThegifDocFile thegifDocFile) {
        checkNotNull(thegifDocFile, "thegifDocFile entity must not be null");
        checkNotNull(thegifDocFile.getUpdatedBy(), "update by must not be null");
        thegifDocFile.setUpdatedDate(LocalDateTime.now());
        return thegifDocFileDaoImpl.update(thegifDocFile);
    }

    @Override
    public ThegifDocFile remove(int id, int userId) {
        checkNotNull(id, "thegifDocFile id must not be null");
        ThegifDocFile thegifDocFile = getById(id);
        checkNotNull(thegifDocFile, "thegifDocFile entity not found in database.");
        thegifDocFile.setRemovedBy(userId);
        thegifDocFile.setRemovedDate(LocalDateTime.now());
        return thegifDocFileDaoImpl.update(thegifDocFile);
    }

    @Override
    public List<ThegifDocFile> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ThegifDocFile> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ThegifDocFile> listByThegifId(int thegifId, String sort, String dir) {
        checkNotNull(thegifId, "thegifId entity must not be null");
        checkNotNull(sort, "sort entity must not be null");
        checkNotNull(dir, "dir entity must not be null");
        return thegifDocFileDaoImpl.listByThegifId(thegifId, sort, dir);
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ThegifDocFile> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ThegifDocFileModel tranformToModel(ThegifDocFile thegifDocFile) {
        ThegifDocFileModel thegifDocFileModel = null;
        if (thegifDocFile != null) {
            thegifDocFileModel = new ThegifDocFileModel();
            thegifDocFileModel.setId(thegifDocFile.getId());
            thegifDocFileModel.setCreatedBy(thegifDocFile.getCreatedBy());
            thegifDocFileModel.setCreatedDate(Common.localDateTimeToString(thegifDocFile.getCreatedDate()));
            thegifDocFileModel.setOrderNo((float) thegifDocFile.getOrderNo());
            thegifDocFileModel.setUpdatedBy(thegifDocFile.getUpdatedBy());
            thegifDocFileModel.setUpdatedDate(Common.localDateTimeToString(thegifDocFile.getUpdatedDate()));
            thegifDocFileModel.setRemovedBy(thegifDocFile.getRemovedBy());
            thegifDocFileModel.setRemovedDate(Common.localDateTimeToString(thegifDocFile.getRemovedDate()));
            thegifDocFileModel.setThegifId(thegifDocFile.getThegifId());
            thegifDocFileModel.setThegifDocFileFileId(thegifDocFile.getThegifDocFileFileId());
            thegifDocFileModel.setThegifDocFileLetter(thegifDocFile.getThegifDocFileLetter());
            thegifDocFileModel.setThegifDocFileLetterType(thegifDocFile.getThegifDocFileLetterType());
            thegifDocFileModel.setDmsDocumentId(thegifDocFile.getDmsDocumentId());
        }
        return thegifDocFileModel;
    }
    
    public ThegifDocFileModel_andPathFile tranformToModelAndPathFile(ThegifDocFile thegifDocFile) {
        ThegifDocFileModel_andPathFile thegifDocFileModel_andPathFile = null;
        if (thegifDocFile != null) {
            thegifDocFileModel_andPathFile = new ThegifDocFileModel_andPathFile();
            thegifDocFileModel_andPathFile.setId(thegifDocFile.getId());
            thegifDocFileModel_andPathFile.setCreatedBy(thegifDocFile.getCreatedBy());
            thegifDocFileModel_andPathFile.setCreatedDate(Common.localDateTimeToString(thegifDocFile.getCreatedDate()));
            thegifDocFileModel_andPathFile.setOrderNo((float) thegifDocFile.getOrderNo());
            thegifDocFileModel_andPathFile.setUpdatedBy(thegifDocFile.getUpdatedBy());
            thegifDocFileModel_andPathFile.setUpdatedDate(Common.localDateTimeToString(thegifDocFile.getUpdatedDate()));
            thegifDocFileModel_andPathFile.setRemovedBy(thegifDocFile.getRemovedBy());
            thegifDocFileModel_andPathFile.setRemovedDate(Common.localDateTimeToString(thegifDocFile.getRemovedDate()));
            thegifDocFileModel_andPathFile.setThegifId(thegifDocFile.getThegifId());
            thegifDocFileModel_andPathFile.setThegifDocFileFileId(thegifDocFile.getThegifDocFileFileId());
            thegifDocFileModel_andPathFile.setThegifDocFileLetter(thegifDocFile.getThegifDocFileLetter());
            thegifDocFileModel_andPathFile.setThegifDocFileLetterType(thegifDocFile.getThegifDocFileLetterType());
            thegifDocFileModel_andPathFile.setDmsDocumentId(thegifDocFile.getDmsDocumentId()); 
            
            //create path file
            ParamService paramService = new ParamService();
            Param param = new Param();
            param = paramService.getByParamName("THEGIF_PAHT");
            
            String pathFile = param.getParamValue() + thegifDocFile.getThegifDocFileLetter() + thegifDocFile.getThegifDocFileLetterType();
            thegifDocFileModel_andPathFile.setPathFile(pathFile);
        }
        return thegifDocFileModel_andPathFile;
    }
}
