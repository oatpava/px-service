package com.px.admin.dao;

import com.px.admin.entity.WordBriefDetail;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface WordBriefDetailDao extends GenericDao<WordBriefDetail, Integer>{
    List<WordBriefDetail> list(int offset,int limit,String sort,String dir);
    List<WordBriefDetail> listAll(String sort,String dir);    
    Integer countAll();
}
