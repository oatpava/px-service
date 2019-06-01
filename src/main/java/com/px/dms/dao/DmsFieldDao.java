/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.dao;

import com.px.dms.entity.DmsField;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author TOP
 */
public interface DmsFieldDao extends GenericDao<DmsField, Integer> {
     List<DmsField> listDmsField();
    
}
