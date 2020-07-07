package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.OrganizeDaoImpl;
import com.px.admin.entity.Organize;
import com.px.admin.model.OrganizeModel;
import com.px.share.service.GenericTreeService;
import com.px.share.util.TreeUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author PRAXiS
 */
public class OrganizeService implements GenericTreeService<Organize, OrganizeModel> {

    private static final Logger LOG = Logger.getLogger(OrganizeService.class.getName());
    private final OrganizeDaoImpl organizeDaoImpl;

    public OrganizeService() {
        this.organizeDaoImpl = new OrganizeDaoImpl();
    }

    @Override
    public String generateParentKey(Organize organize) {
        String result = "";
        try {
            result = TreeUtil.generateParentKey(organize);
        } catch (Exception e) {
            LOG.error("Exception : " + e);
        }
        return result;
    }

    @Override
    public Organize getParent(Organize organize) {
        checkNotNull(organize, "Organize entity must not be null");
        checkNotNull(organize.getParentId(), "Organize parent id must not be null");
        return organizeDaoImpl.findParent(organize.getParentId());
    }

    @Override
    public List<Organize> listChild(Organize organize) {
        checkNotNull(organize, "Organize entity must not be null");
        checkNotNull(organize.getId(), "Organize id must not be null");
        return organizeDaoImpl.findChild(organize.getId());
    }

    public List<Organize> listChild(Organize organize, String sort, String dir) {
        checkNotNull(organize, "Organize entity must not be null");
        checkNotNull(organize.getId(), "Organize id must not be null");
        return organizeDaoImpl.findChild(organize.getId(), sort, dir);
    }

    public boolean checkDup(String code, String name) {
        boolean result = false;
        int countOfStucture = organizeDaoImpl.countDup(code, name);
        if (countOfStucture != 0) {
            result = true;
        }
        return result;
    }

    @Override
    public List<Organize> listFromParentKey(Organize organize) {
        checkNotNull(organize, "Organize entity must not be null");
        checkNotNull(organize.getParentId(), "Organize parent id must not be null");
        ArrayList<Organize> result = new ArrayList();
        try {
            List parentList = TreeUtil.getListFromParentKey(organize);
            for (Object tmp : parentList) {
                result.add((Organize) tmp);
            }
        } catch (Exception e) {
            LOG.error("Exception : " + e);
        }

        return result;
    }

    @Override
    public Organize create(Organize organize) {
        checkNotNull(organize, "Organize entity must not be null");
        checkNotNull(organize.getOrganizeName(), "Organize name must not be null");
        checkNotNull(organize.getCreatedBy(), "create by must not be null");
        organize = organizeDaoImpl.create(organize);
        organize.setParentKey(generateParentKey(organize));
        organize.setNodeLevel(TreeUtil.generateNodeLevel(organize));
        if (organize.getOrderNo() == 0) {
            organize.setOrderNo(organize.getId());
        }
        organize = update(organize);
        return organize;
    }

    @Override
    public Organize getById(int id) {
        checkNotNull(id, "Organize id entity must not be null");
        return organizeDaoImpl.getById(id);
    }

    @Override
    public Organize getByIdNotRemoved(int id) {
        checkNotNull(id, "Organize id entity must not be null");
        return organizeDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public Organize update(Organize organize) {
        checkNotNull(organize, "Organize entity must not be null");
        checkNotNull(organize.getOrganizeName(), "Organize name must not be null");
        checkNotNull(organize.getUpdatedBy(), "update by must not be null");
        organize.setParentKey(generateParentKey(organize));
        organize.setNodeLevel(TreeUtil.generateNodeLevel(organize));
        organize.setUpdatedDate(LocalDateTime.now());
        return organizeDaoImpl.update(organize);
    }

    @Override
    public Organize remove(int id, int userId) {
        checkNotNull(id, "Organize id must not be null");
        Organize organize = getById(id);
        checkNotNull(organize, "Organize entity not found in database.");
        organize.setRemovedBy(userId);
        organize.setRemovedDate(LocalDateTime.now());
        return organizeDaoImpl.update(organize);
    }

    @Override
    public List<Organize> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return organizeDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<Organize> listAll(String sort, String dir) {
        return organizeDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return organizeDaoImpl.countAll();
    }

    @Override
    public List<Organize> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrganizeModel tranformToModel(Organize organize) {
        OrganizeModel organizeModel = null;
        if (organize != null) {
            organizeModel = new OrganizeModel();
            organizeModel.setId(organize.getId());
            organizeModel.setDetail(organize.getOrganizeDetail());
            organizeModel.setName(organize.getOrganizeName());
            organizeModel.setShortName(organize.getOrganizeShortName());
            organizeModel.setNodeLevel(organize.getNodeLevel());
            organizeModel.setParentId(organize.getParentId());
            organizeModel.setParentKey(organize.getParentKey());
            organizeModel.setCode(organize.getOrganizeCode());
        }
        return organizeModel;
    }

    public List<Organize> listByName(String organizeName) {
        return organizeDaoImpl.listByName(organizeName);
    }
    
    public Organize getLatest(int parentId) {
        return organizeDaoImpl.getLatest(parentId);
    }
    
    public double findOrderNo(int id) {
        checkNotNull(id, "organize id must not be null");
        Organize org2 = this.getByIdNotRemoved(id);
        Organize org = organizeDaoImpl.getPrevOrderBy(id);
        double orderNoNum = 0;
        if (org != null && org.getOrderNo() == (org2.getOrderNo() - 1)) {
            orderNoNum = (org.getOrderNo() + org2.getOrderNo()) / 2;
        } else {
            orderNoNum = org2.getOrderNo() - 1;
        }
        return orderNoNum;
    }

}
