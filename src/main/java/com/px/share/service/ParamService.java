package com.px.share.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.daoimpl.ParamDaoImpl;
import com.px.share.entity.Param;
import com.px.share.model.ParamModel;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class ParamService implements GenericService<Param, ParamModel>{
    private static final Logger LOG = Logger.getLogger(ParamService.class.getName());
    private final ParamDaoImpl paramDaoImpl;
    
    public ParamService() {
        this.paramDaoImpl = new ParamDaoImpl();
    }

    @Override
    public Param create(Param param) {
        checkNotNull(param, "param entity must not be null");
        checkNotNull(param.getParamName(), "param name must not be null");
        checkNotNull(param.getParamValue(), "param value must not be null");
        checkNotNull(param.getCreatedBy(),"create by must not be null");
        param = paramDaoImpl.create(param);
        if(param.getOrderNo()==0){
            param.setOrderNo(param.getId());
            param = update(param);
        }
        return param;
    }

    @Override
    public Param getById(int id) {
        checkNotNull(id, "param id entity must not be null");
        return paramDaoImpl.getById(id);
    }

    @Override
    public Param update(Param param) {
        checkNotNull(param, "param entity must not be null");
        checkNotNull(param.getParamName(), "param name must not be null");
        checkNotNull(param.getParamValue(), "param value must not be null");
        checkNotNull(param.getUpdatedBy(),"update by must not be null");
        param.setUpdatedDate(LocalDateTime.now());
        return paramDaoImpl.update(param);
    }

    @Override
    public Param remove(int id, int userId) {
        checkNotNull(id, "param id must not be null");
        Param param = getById(id);
        checkNotNull(param, "param entity not found in database.");
        param.setRemovedBy(userId);
        param.setRemovedDate(LocalDateTime.now());
        return paramDaoImpl.update(param);
    }

    @Override
    public List<Param> listAll(String sort, String dir) {
        return paramDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<Param> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return paramDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public ParamModel tranformToModel(Param param) {
        ParamModel paramModel = null;
        if(param!=null){
            paramModel = new ParamModel();
            paramModel.setId(param.getId());
            paramModel.setParamName(param.getParamName());
            paramModel.setParamValue(param.getParamValue());
        }
        return paramModel;
    }

    public Param getByParamName(String paramName) {
        checkNotNull(paramName, "paramName entity must not be null");
        return paramDaoImpl.getByParamName(paramName);
    }
    
    public List<Param> getByName(String name) {
        checkNotNull(name, "paramName entity must not be null");
        return paramDaoImpl.getByName(name);
    }

    @Override
    public int countAll() {
        return paramDaoImpl.countAll();
    }

    @Override
    public List<Param> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        return paramDaoImpl.search(queryParams,offset,limit, sort, dir);
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Param getByIdNotRemoved(int id) {
        checkNotNull(id, "Param id entity must not be null");
        return paramDaoImpl.getByIdNotRemoved(id);
    }
    
    public List<Param> listByParamName(String paramName,int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "paramName must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return paramDaoImpl.listByParamName(paramName,offset, limit, sort, dir);
    }
}
