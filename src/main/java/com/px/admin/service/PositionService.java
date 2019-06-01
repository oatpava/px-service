package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.PositionDaoImpl;
import com.px.admin.entity.Position;
import com.px.admin.model.PositionModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class PositionService implements GenericService<Position, PositionModel>{
    private static final Logger LOG = Logger.getLogger(PositionService.class.getName());
    private final PositionDaoImpl positionDaoImpl;
    
    public PositionService() {
        this.positionDaoImpl = new PositionDaoImpl();
    }

    @Override
    public Position create(Position position) {
        checkNotNull(position, "position entity must not be null");
        checkNotNull(position.getPositionName(), "position name must not be null");
        checkNotNull(position.getCreatedBy(),"create by must not be null");
        position = positionDaoImpl.create(position);
        if(position.getOrderNo()==0){
            position.setOrderNo(position.getId());
            position = update(position);
        }
        return position;
    }

    @Override
    public Position getById(int id) {
        checkNotNull(id, "position id entity must not be null");
        return positionDaoImpl.getById(id);
    }
    
    public Position getByPositionName(String name) {
//        checkNotNull(id, "position id entity must not be null");
        return positionDaoImpl.getByPositionName(name);
    }

    @Override
    public Position update(Position position) {
        checkNotNull(position, "position entity must not be null");
        checkNotNull(position.getPositionName(), "position name must not be null");
        checkNotNull(position.getUpdatedBy(),"update by must not be null");
        position.setUpdatedDate(LocalDateTime.now());
        return positionDaoImpl.update(position);
    }

    @Override
    public Position remove(int id, int userId) {
        checkNotNull(id, "position id must not be null");
        Position position = getById(id);
        checkNotNull(position, "position entity not found in database.");
        position.setRemovedBy(userId);
        position.setRemovedDate(LocalDateTime.now());
        return positionDaoImpl.update(position);
    }

    @Override
    public List<Position> listAll(String sort, String dir) {
        return positionDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<Position> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return positionDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public PositionModel tranformToModel(Position position) {
        PositionModel positionModel = null;
        
        if(position!=null){
            positionModel = new PositionModel();
            positionModel.setId(position.getId());
            positionModel.setName(position.getPositionName());
            positionModel.setNameEng(position.getPositionNameEng());
            positionModel.setNameExtra(position.getPositionNameExtra());
        }
        return positionModel;
    }

    @Override
    public int countAll() {
        return positionDaoImpl.countAll();
    }

    @Override
    public List<Position> search(MultivaluedMap<String, String> queryPositions, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryPositions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Position getByIdNotRemoved(int id) {
        checkNotNull(id, "Position id entity must not be null");
        return positionDaoImpl.getByIdNotRemoved(id);
    }
    
}
