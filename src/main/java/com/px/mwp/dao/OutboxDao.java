
package com.px.mwp.dao;

import com.px.mwp.entity.Outbox;
import com.px.share.dao.GenericDao;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;

/**
 *
 * @author Mali
 */
public interface OutboxDao extends GenericDao<Outbox, Integer>{ 
    //List<Outbox> listByUserProfileFolderId(List<Integer> listUserProfileFolderId,int offset,int limit,String sort,String dir);
    List<Outbox> listByStructureFolderId(List<Integer> listStructureFolderId,int offset,int limit,String sort,String dir);
    List<Outbox> listByUserProfileFolderIdAll(List<Integer> listUserProfileFolderId,String sort,String dir);
    List<Outbox> listByStructureFolderIdAll(List<Integer> listUserProfileFolderId,String sort,String dir);
    List<Outbox> listAll(String sort,String dir);    
    Integer countAll();  
    Conjunction createConjunctionFormSearchUser(MultivaluedMap<String, String> queryParams,List<Integer> listUserProfileFolderId);
    Conjunction createConjunctionFormSearchStructure(MultivaluedMap<String, String> queryParams,List<Integer> listStructureId);
    Conjunction createConjunctionFormSearchBin(MultivaluedMap<String, String> queryParams,int userID);
    List<Outbox> searchOutboxUser(MultivaluedMap<String, String> queryParams,List<Integer> listUserProfileFolderId,int offset,int limit,String sort,String dir);
    List<Outbox> searchOutboxStructure(MultivaluedMap<String, String> queryParams,List<Integer> listStructureId,int offset,int limit,String sort,String dir);
    List<Outbox> searchOutboxBin(MultivaluedMap<String, String> queryParams,int userID,int offset,int limit,String sort,String dir);
    int countSearchOutboxUser(MultivaluedMap<String, String> queryParams,List<Integer> listUserProfileFolderId);
    int countSearchOutboxStructure(MultivaluedMap<String, String> queryParams,List<Integer> listStructureId);
    int countSearchOutboxBin(MultivaluedMap<String, String> queryParams,int userID);
    Outbox getByWorkflowId(int workflowId);

}
