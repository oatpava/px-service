
package com.px.mwp.dao;

import com.px.share.dao.GenericDao;
import com.px.mwp.entity.UserProfileFolder;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface UserProfileFolderDao extends GenericDao<UserProfileFolder, Integer>{
    List<UserProfileFolder> listByUserProfileId(int userProfileId);
    List<UserProfileFolder> listByUserProfileId(int userProfileId,String type);
}
