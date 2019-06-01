
package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.WfContent;
import java.util.List;

/**
 *
 * @author Oat
 */
public interface WfContentDao extends GenericDao<WfContent, Integer>{
    List<WfContent> listByFolderId(int offset,int limit,String sort,String dir,int folderid,int wfContentYear);
    List<WfContent> listByFolderId(int folderid,int wfContentYear);
    List<WfContent> listAll(String sort,String dir);    
    Integer countAll();
    int getMaxContentNo(String wfFolderPreContentNo,int folderId,int wfFolderContentYear);
    int getMaxBookNo(String wfFolderPreBookNo,int folderId,int wfFolderContentYear);
    WfContent getByContentNo(String wfContentContentNo,int folderId);
    WfContent getByBookNo(String wfContentBookNo,int folderId);
    boolean checkWfContentInWfFolder(int folderId);
    Integer countAllListByFolderId(int folderId,int wfContentYear); 
    int getMaxContentPoint(int folderId,int wfFolderContentYear,int contentNumber);   
}
