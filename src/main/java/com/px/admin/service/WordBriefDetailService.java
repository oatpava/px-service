package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.WordBriefDetailDaoImpl;
import com.px.admin.entity.WordBriefDetail;
import com.px.admin.model.WordBriefDetailModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class WordBriefDetailService implements GenericService<WordBriefDetail, WordBriefDetailModel>{
    private static final Logger LOG = Logger.getLogger(WordBriefDetailService.class.getName());
    private final WordBriefDetailDaoImpl wordBriefDetailDaoImpl;
    
    public WordBriefDetailService() {
        this.wordBriefDetailDaoImpl = new WordBriefDetailDaoImpl();
    }

    @Override
    public WordBriefDetail create(WordBriefDetail wordBriefDetail) {
        checkNotNull(wordBriefDetail, "wordBriefDetail entity must not be null");
        checkNotNull(wordBriefDetail.getWordBriefDetailName(), "wordBriefDetail name must not be null");
        checkNotNull(wordBriefDetail.getCreatedBy(),"create by must not be null");
        wordBriefDetail = wordBriefDetailDaoImpl.create(wordBriefDetail);
        if(wordBriefDetail.getOrderNo()==0){
            wordBriefDetail.setOrderNo(wordBriefDetail.getId());
            wordBriefDetail = update(wordBriefDetail);
        }
        return wordBriefDetail;
    }

    @Override
    public WordBriefDetail getById(int id) {
        checkNotNull(id, "wordBriefDetail id entity must not be null");
        return wordBriefDetailDaoImpl.getById(id);
    }

    @Override
    public WordBriefDetail update(WordBriefDetail wordBriefDetail) {
        checkNotNull(wordBriefDetail, "wordBriefDetail entity must not be null");
        checkNotNull(wordBriefDetail.getWordBriefDetailName(), "wordBriefDetail name must not be null");
        checkNotNull(wordBriefDetail.getUpdatedBy(),"update by must not be null");
        wordBriefDetail.setUpdatedDate(LocalDateTime.now());
        return wordBriefDetailDaoImpl.update(wordBriefDetail);
    }

    @Override
    public WordBriefDetail remove(int id, int userId) {
        checkNotNull(id, "wordBriefDetail id must not be null");
        WordBriefDetail wordBriefDetail = getById(id);
        checkNotNull(wordBriefDetail, "wordBriefDetail entity not found in database.");
        wordBriefDetail.setRemovedBy(userId);
        wordBriefDetail.setRemovedDate(LocalDateTime.now());
        return wordBriefDetailDaoImpl.update(wordBriefDetail);
    }

    @Override
    public List<WordBriefDetail> listAll(String sort, String dir) {
        return wordBriefDetailDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<WordBriefDetail> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return wordBriefDetailDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public WordBriefDetailModel tranformToModel(WordBriefDetail wordBriefDetail) {
        WordBriefDetailModel wordBriefDetailModel = null;
        if(wordBriefDetail!=null){
            wordBriefDetailModel = new WordBriefDetailModel();
            wordBriefDetailModel.setId(wordBriefDetail.getId());
            wordBriefDetailModel.setName(wordBriefDetail.getWordBriefDetailName());
        }
        return wordBriefDetailModel;
    }

    @Override
    public int countAll() {
        return wordBriefDetailDaoImpl.countAll();
    }

    @Override
    public List<WordBriefDetail> search(MultivaluedMap<String, String> queryWordBriefDetails, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryWordBriefDetails) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<WordBriefDetail> listByWordBriefId(int wordBriefId,String sort, String dir){
        return wordBriefDetailDaoImpl.listByWordBriefId(wordBriefId, sort, dir);
    }
    
    public List<WordBriefDetail> listByWordBrief(String wordBrief,String sort, String dir){
        return wordBriefDetailDaoImpl.listByWordBrief(wordBrief, sort, dir);
    }
    
    @Override
    public WordBriefDetail getByIdNotRemoved(int id) {
        checkNotNull(id, "WordBriefDetail id entity must not be null");
        return wordBriefDetailDaoImpl.getByIdNotRemoved(id);
    }
    
}
