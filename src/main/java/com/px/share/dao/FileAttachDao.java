package com.px.share.dao;

import com.px.share.entity.FileAttach;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface FileAttachDao extends GenericDao<FileAttach, Integer>{
    List<FileAttach> list(int offset,int limit,String sort,String dir);
    List<FileAttach> listAll(String sort,String dir);    
    Integer countAll();
}
