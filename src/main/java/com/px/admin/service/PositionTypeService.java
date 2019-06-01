package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.PositionTypeDaoImpl;
import com.px.admin.entity.PositionType;
import com.px.admin.model.PositionTypeModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class PositionTypeService implements GenericService<PositionType, PositionTypeModel>{
    private static final Logger LOG = Logger.getLogger(PositionTypeService.class.getName());
    private final PositionTypeDaoImpl positionTypeDaoImpl;
    
    public PositionTypeService() {
        this.positionTypeDaoImpl = new PositionTypeDaoImpl();
    }

    @Override
    public PositionType create(PositionType positionType) {
        checkNotNull(positionType, "positionType entity must not be null");
        checkNotNull(positionType.getPositionTypeName(), "positionType name must not be null");
        checkNotNull(positionType.getCreatedBy(),"create by must not be null");
        positionType = positionTypeDaoImpl.create(positionType);
        if(positionType.getOrderNo()==0){
            positionType.setOrderNo(positionType.getId());
            positionType = update(positionType);
        }
        return positionType;
    }

    @Override
    public PositionType getById(int id) {
        checkNotNull(id, "positionType id entity must not be null");
        return positionTypeDaoImpl.getById(id);
    }

    @Override
    public PositionType update(PositionType positionType) {
        checkNotNull(positionType, "positionType entity must not be null");
        checkNotNull(positionType.getPositionTypeName(), "positionType name must not be null");
        checkNotNull(positionType.getUpdatedBy(),"update by must not be null");
        positionType.setUpdatedDate(LocalDateTime.now());
        return positionTypeDaoImpl.update(positionType);
    }

    @Override
    public PositionType remove(int id, int userId) {
        checkNotNull(id, "positionType id must not be null");
        PositionType positionType = getById(id);
        checkNotNull(positionType, "positionType entity not found in database.");
        positionType.setRemovedBy(userId);
        positionType.setRemovedDate(LocalDateTime.now());
        return positionTypeDaoImpl.update(positionType);
    }

    @Override
    public List<PositionType> listAll(String sort, String dir) {
        return positionTypeDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<PositionType> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return positionTypeDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public PositionTypeModel tranformToModel(PositionType positionType) {
        PositionTypeModel positionTypeModel = null;
        if(positionType!=null){
            positionTypeModel = new PositionTypeModel();
            positionTypeModel.setId(positionType.getId());
            positionTypeModel.setAbbr(positionType.getPositionTypeAbbr());
            positionTypeModel.setName(positionType.getPositionTypeName());
        }
        return positionTypeModel;
    }

    @Override
    public int countAll() {
        return positionTypeDaoImpl.countAll();
    }

    @Override
    public List<PositionType> search(MultivaluedMap<String, String> queryPositionTypes, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryPositionTypes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public PositionType getByIdNotRemoved(int id) {
        checkNotNull(id, "PositionType id entity must not be null");
        return positionTypeDaoImpl.getByIdNotRemoved(id);
    }
    
}
