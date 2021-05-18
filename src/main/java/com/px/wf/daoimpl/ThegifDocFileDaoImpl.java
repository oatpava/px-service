package com.px.wf.daoimpl;

import com.px.wf.dao.ThegifDocFileDao;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.wf.entity.ThegifDocFile;
import com.px.share.util.Common;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mali
 */
public class ThegifDocFileDaoImpl extends GenericDaoImpl<ThegifDocFile, Integer> implements ThegifDocFileDao {

    public ThegifDocFileDaoImpl() {
        super(ThegifDocFile.class);

    }

    @Override
    public ThegifDocFile getByIdNotRemoved(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ThegifDocFile> listByThegifId(int thegifId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("thegifId", thegifId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(ThegifDocFile.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

}
