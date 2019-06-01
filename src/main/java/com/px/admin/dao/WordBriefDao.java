package com.px.admin.dao;

import com.px.admin.entity.WordBrief;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface WordBriefDao extends GenericDao<WordBrief, Integer>{
    List<WordBrief> list(int offset,int limit,String sort,String dir);
    List<WordBrief> listAll(String sort,String dir);    
    Integer countAll();
}
