package com.px.authority.daoimpl;

import com.px.admin.entity.Module;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.authority.dao.ModuleUserAuthDao;
import com.px.authority.entity.ModuleUserAuth;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class ModuleUserAuthDaoImpl extends GenericDaoImpl<ModuleUserAuth, Integer> implements ModuleUserAuthDao {

    public ModuleUserAuthDaoImpl() {
        super(ModuleUserAuth.class);
    }

    @Override
    public List<ModuleUserAuth> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<ModuleUserAuth> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<ModuleUserAuth> listByAuthorityStructure(String authority, Structure structure, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (authority != null && structure != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("structure", structure));
            conjunction.add(Restrictions.eq("authority", authority));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByAuthorityStructure(String authority, List<Structure> structure, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (authority != null && structure != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("structure", structure));
            conjunction.add(Restrictions.eq("authority", authority));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByAuthorityUserProfile(String authority, UserProfile userProfile, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (authority != null && userProfile != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.eq("authority", authority));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByAuthorityUserProfile(String authority, List<UserProfile> userProfile, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (authority != null && userProfile != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.eq("authority", authority));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByModule(Module module, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (module != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("module", module));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByModule(List<Module> module, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (module != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("module", module));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByModuleStructure(Module module, Structure structure, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (module != null && structure != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("structure", structure));
            conjunction.add(Restrictions.eq("module", module));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByModuleStructure(Module module, List<Structure> structure, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (module != null && structure != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("structure", structure));
            conjunction.add(Restrictions.eq("module", module));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByModuleUserProfile(Module module, UserProfile userProfile, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (module != null && userProfile != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.eq("module", module));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByModuleUserProfile(Module module, List<UserProfile> userProfile, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (module != null && userProfile != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.eq("module", module));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByStructure(Structure structure, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (structure != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("structure", structure));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByStructure(List<Structure> structure, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (structure != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("structure", structure));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByUserProfile(UserProfile userProfile, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (userProfile != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<ModuleUserAuth> listByUserProfile(List<UserProfile> userProfile, String sort, String dir) {
        List<ModuleUserAuth> result = new ArrayList();
        if (userProfile != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("userProfile", userProfile));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public Integer countAll() {
        return this.listAll("", "").size();
    }

    @Override
    public ModuleUserAuth getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(ModuleUserAuth.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

}
