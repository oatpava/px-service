
package com.px.mwp.dao;

import com.px.mwp.entity.Inbox;
import com.px.share.dao.GenericDao;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;

/**
 *
 * @author Oat
 */
public interface InboxDao extends GenericDao<Inbox, Integer>{
    List<Inbox> listByStructureFolderId(int structureFolderId, int offset, int limit, String sort, String dir, int status);
    List<Inbox> listByUserProfileFolderId(int userProfileFolderId, int offset, int limit, String sort, String dir, int status);
    List<Inbox> listByWorkflowId(int workflowId);
    int countListByStructureFolderId(int structureFolderId, int status); 
    int countListByUserProfileFolderId(int userProfileFolderId, int status); 
}
