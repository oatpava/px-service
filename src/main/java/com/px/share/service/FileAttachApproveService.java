package com.px.share.service;

import com.px.share.daoimpl.FileAttachApproveDaoImpl;
import com.px.admin.service.UserProfileService;
import com.px.share.entity.FileAttach;
import com.px.share.entity.FileAttachApprove;
import com.px.share.model.FileAttachApproveModel;
import com.px.share.util.Common;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Oat
 */
public class FileAttachApproveService implements GenericService<FileAttachApprove, FileAttachApproveModel> {

    private static final Logger LOG = Logger.getLogger(FileAttachApproveService.class.getName());
    private final FileAttachApproveDaoImpl fileAttachApproveDaoImpl;

    public FileAttachApproveService() {
        this.fileAttachApproveDaoImpl = new FileAttachApproveDaoImpl();
    }

    @Override
    public FileAttachApprove create(FileAttachApprove fileAttachApprove) {
        fileAttachApprove = fileAttachApproveDaoImpl.create(fileAttachApprove);
        if (fileAttachApprove.getOrderNo() == 0) {
            fileAttachApprove.setOrderNo(fileAttachApprove.getId());
            fileAttachApprove = update(fileAttachApprove);
        }
        return fileAttachApprove;
    }

    @Override
    public FileAttachApprove update(FileAttachApprove fileAttachApprove) {
        fileAttachApprove.setUpdatedDate(LocalDateTime.now());
        return fileAttachApproveDaoImpl.update(fileAttachApprove);
    }

    @Override
    public FileAttachApprove remove(int id, int userId) {
        FileAttachApprove fileAttachApprove = getById(id);
        fileAttachApprove.setRemovedBy(userId);
        fileAttachApprove.setRemovedDate(LocalDateTime.now());
        return fileAttachApproveDaoImpl.update(fileAttachApprove);
    }

    @Override
    public FileAttachApprove getById(int id) {
        return fileAttachApproveDaoImpl.getById(id);
    }

    @Override
    public FileAttachApprove getByIdNotRemoved(int id) {
        return fileAttachApproveDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public List<FileAttachApprove> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<FileAttachApprove> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<FileAttachApprove> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FileAttachApproveModel tranformToModel(FileAttachApprove fileAttachApprove) {
        FileAttachApproveModel fileAttachApproveModel = null;
        if (fileAttachApprove != null) {
            fileAttachApproveModel = new FileAttachApproveModel();
            fileAttachApproveModel.setId(fileAttachApprove.getId());
            fileAttachApproveModel.setCreatedDate(Common.localDateTimeToString2(fileAttachApprove.getCreatedDate()));
            fileAttachApproveModel.setFileAttachId(fileAttachApprove.getFileAttach().getId());
            fileAttachApproveModel.setUserProfileId(fileAttachApprove.getUserProfile().getId());
            fileAttachApproveModel.setUserProfile(new UserProfileService().tranformToModel(fileAttachApprove.getUserProfile()));
        }
        return fileAttachApproveModel;
    }

    public List<FileAttachApprove> listAll(String sort, String dir, int fileAttachId) {
        return fileAttachApproveDaoImpl.listAll(sort, dir, fileAttachId);
    }

    public void delete(FileAttachApprove fileAttachApprove) {
        fileAttachApproveDaoImpl.delete(fileAttachApprove);
    }

}
