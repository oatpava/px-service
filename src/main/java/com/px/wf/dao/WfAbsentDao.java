package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.WfAbsent;
import java.util.List;

/**
 *
 * @author CHALERMPOL
 */
public interface WfAbsentDao extends GenericDao<WfAbsent, Integer>{
    List<WfAbsent> list(int offset,int limit,String sort,String dir);
    List<WfAbsent> listAll(String sort,String dir);    
    Integer countAll();
}
