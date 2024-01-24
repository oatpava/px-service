package com.px.share.dao;

import com.px.share.entity.FileAttachApprove;
import java.util.List;

/**
 *
 * @author Oat
 */
public interface FileAttachApproveDao extends GenericDao<FileAttachApprove, Integer>{
    List<FileAttachApprove> listAll(String sort, String dir, int fileAttachId);
}
