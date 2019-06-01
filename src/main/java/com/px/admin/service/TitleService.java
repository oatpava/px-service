package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.TitleDaoImpl;
import com.px.admin.entity.Title;
import com.px.admin.model.TitleModel;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class TitleService implements GenericService<Title, TitleModel>{
    private static final Logger LOG = Logger.getLogger(TitleService.class.getName());
    private final TitleDaoImpl titleDaoImpl;
    
    public TitleService() {
        this.titleDaoImpl = new TitleDaoImpl();
    }

    @Override
    public Title create(Title title) {
        checkNotNull(title, "title entity must not be null");
        checkNotNull(title.getTitleName(), "title name must not be null");
        checkNotNull(title.getCreatedBy(),"create by must not be null");
        title = titleDaoImpl.create(title);
        if(title.getOrderNo()==0){
            title.setOrderNo(title.getId());
            title = update(title);
        }
        return title;
    }

    @Override
    public Title getById(int id) {
        checkNotNull(id, "title id entity must not be null");
        return titleDaoImpl.getById(id);
    }

    @Override
    public Title update(Title title) {
        checkNotNull(title, "title entity must not be null");
        checkNotNull(title.getTitleName(), "title name must not be null");
        checkNotNull(title.getUpdatedBy(),"update by must not be null");
//        title.setUpdatedDate(Common.dateThaiToLocalDateTime("10/11/2559 15:45:35"));
        title.setUpdatedDate(LocalDateTime.now());
        return titleDaoImpl.update(title);
    }

    @Override
    public Title remove(int id, int userId) {
        checkNotNull(id, "title id must not be null");
        Title title = getById(id);
        checkNotNull(title, "title entity not found in database.");
        title.setRemovedBy(userId);        
        title.setRemovedDate(LocalDateTime.now());
        return titleDaoImpl.update(title);
    }

    @Override
    public List<Title> listAll(String sort, String dir) {
        return titleDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<Title> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return titleDaoImpl.list(offset, limit, sort, dir);
    }
    
    public Title getByTitleName(String name) {
//        checkNotNull(id, "position id entity must not be null");
        return titleDaoImpl.getByTitleName(name);
    }

    @Override
    public TitleModel tranformToModel(Title title) {
        TitleModel titleModel = null;
        if(title!=null){
            titleModel = new TitleModel();
            titleModel.setId(title.getId());
            titleModel.setName(title.getTitleName());
            titleModel.setNameEng(title.getTitleNameEng());
            titleModel.setCreatedDate(Common.localDateTimeToString(title.getCreatedDate()));
        }
        return titleModel;
    }

    @Override
    public int countAll() {
        return titleDaoImpl.countAll();
    }

    @Override
    public List<Title> search(MultivaluedMap<String, String> queryTitles, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryTitles) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Title getByIdNotRemoved(int id) {
        checkNotNull(id, "Title id entity must not be null");
        return titleDaoImpl.getByIdNotRemoved(id);
    }
}
