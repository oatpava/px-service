package com.px.wf.daoimpl;

import com.px.share.daoimpl.GenericTreeDaoImpl;
import com.px.wf.dao.WfFolderDao;
import com.px.wf.entity.WfFolder;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Oat
 */
public class WfFolderDaoImpl extends GenericTreeDaoImpl<WfFolder, Integer> implements WfFolderDao {

    private static final Logger LOG = Logger.getLogger(WfFolderDaoImpl.class.getName());

    public WfFolderDaoImpl() {
        super(WfFolder.class);
    }

    @Override
    public List<WfFolder> listByParentId(int parentId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("parentId", parentId));
        conjunction.add(Restrictions.eq("wfFolderType", "T"));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfFolder.class);
        criteria.add(conjunction).addOrder(Order.asc("wfContentType")).addOrder(Order.asc("wfContentType2")).addOrder(Order.asc("wfFolderName"));
        return this.listByCriteria(criteria);
    }

    @Override
    public List<WfFolder> listShortcutByUserProfileId(int userProfileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfFolderType", "SC"));
        conjunction.add(Restrictions.eq("wfFolderOwnerId", userProfileId));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfFolder.class);
        //criteria.add(conjunction).addOrder(Order.asc("wfFolderLinkFolderId")).addOrder(Order.asc("wfContentType")).addOrder(Order.asc("wfContentType2")).addOrder(Order.asc("wfFolderName"));
        criteria.add(conjunction).addOrder(Order.asc("orderNo")).addOrder(Order.asc("createdDate"));
        return this.listByCriteria(criteria);
    }

    @Override
    public WfFolder getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfFolder.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<WfFolder> listByContentTypeId(String folderType, int contentTypeId, int contentType2Id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfFolderType", folderType));
        conjunction.add(Restrictions.eq("ct.id", contentTypeId));
        if (contentType2Id > 0) {
            conjunction.add(Restrictions.eq("ct2.id", contentType2Id));
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(WfFolder.class);
        criteria.createCriteria("wfContentType", "ct", JoinType.INNER_JOIN);
        criteria.createCriteria("wfContentType2", "ct2", JoinType.INNER_JOIN);
        //criteria.add(conjunction).addOrder(Order.asc("wfContentType2")).addOrder(Order.asc("wfFolderName"));
        criteria.add(conjunction).addOrder(Order.asc("createdDate"));
        return this.listByCriteria(criteria);
    }
    
}
