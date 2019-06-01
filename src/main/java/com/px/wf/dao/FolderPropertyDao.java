package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.FolderProperty;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Mali
 */
public interface FolderPropertyDao extends GenericDao<FolderProperty, Integer>{
    List<FolderProperty> listByType(int type,int folderId,String folderPropertyType);
    List<FolderProperty> listAll(String sort, String dir);
    boolean checkFolderIdNoData(int folderId);
}
