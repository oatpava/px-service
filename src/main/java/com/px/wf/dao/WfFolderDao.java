
package com.px.wf.dao;

import com.px.share.dao.GenericTreeDao;
import com.px.wf.entity.WfFolder;
import java.util.List;

/**
 *
 * @author Oat
 */
public interface WfFolderDao extends GenericTreeDao<WfFolder, Integer>{
    List<WfFolder> listByParentId(int folderParentId);
    List<WfFolder> listShortcutByUserProfileId(int userProfileId);
    List<WfFolder> listByContentTypeId(String folderType, int contentTypeId,int contentType2Id);
}
 
