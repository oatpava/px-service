package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.WordBriefDaoImpl;
import com.px.admin.entity.WordBrief;
import com.px.admin.model.WordBriefModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class WordBriefService implements GenericService<WordBrief, WordBriefModel>{
    private static final Logger LOG = Logger.getLogger(WordBriefService.class.getName());
    private final WordBriefDaoImpl wordBriefDaoImpl;
    
    public WordBriefService() {
        this.wordBriefDaoImpl = new WordBriefDaoImpl();
    }

    @Override
    public WordBrief create(WordBrief wordBrief) {
        checkNotNull(wordBrief, "wordBrief entity must not be null");
        checkNotNull(wordBrief.getWordBriefName(), "wordBrief name must not be null");
        checkNotNull(wordBrief.getCreatedBy(),"create by must not be null");
        wordBrief = wordBriefDaoImpl.create(wordBrief);
        if(wordBrief.getOrderNo()==0){
            wordBrief.setOrderNo(wordBrief.getId());
            wordBrief = update(wordBrief);
        }
        return wordBrief;
    }

    @Override
    public WordBrief getById(int id) {
        checkNotNull(id, "wordBrief id entity must not be null");
        return wordBriefDaoImpl.getById(id);
    }

    @Override
    public WordBrief update(WordBrief wordBrief) {
        checkNotNull(wordBrief, "wordBrief entity must not be null");
        checkNotNull(wordBrief.getWordBriefName(), "wordBrief name must not be null");
        checkNotNull(wordBrief.getUpdatedBy(),"update by must not be null");
        wordBrief.setUpdatedDate(LocalDateTime.now());
        return wordBriefDaoImpl.update(wordBrief);
    }

    @Override
    public WordBrief remove(int id, int userId) {
        checkNotNull(id, "wordBrief id must not be null");
        WordBrief wordBrief = getById(id);
        checkNotNull(wordBrief, "wordBrief entity not found in database.");
        wordBrief.setRemovedBy(userId);
        wordBrief.setRemovedDate(LocalDateTime.now());
        return wordBriefDaoImpl.update(wordBrief);
    }

    @Override
    public List<WordBrief> listAll(String sort, String dir) {
        return wordBriefDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<WordBrief> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return wordBriefDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public WordBriefModel tranformToModel(WordBrief wordBrief) {
        WordBriefModel wordBriefModel = null;
        if(wordBrief!=null){
            wordBriefModel = new WordBriefModel();
            wordBriefModel.setId(wordBrief.getId());
            wordBriefModel.setName(wordBrief.getWordBriefName());
        }
        return wordBriefModel;
    }

    @Override
    public int countAll() {
        return wordBriefDaoImpl.countAll();
    }

    @Override
    public List<WordBrief> search(MultivaluedMap<String, String> queryWordBriefs, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryWordBriefs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public WordBrief getByIdNotRemoved(int id) {
        checkNotNull(id, "WordBrief id entity must not be null");
        return wordBriefDaoImpl.getByIdNotRemoved(id);
    }
}
