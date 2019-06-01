/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.dao;

import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.borrow;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author top
 */
public interface borrowDao extends GenericDao<borrow, Integer>{
    
     public borrow lend(DmsDocument document,int numDate,int lendID,int handlerId);
     public borrow Returned (DmsDocument document,String returnDate,int returnedID,int handlerId);
     List< borrow> history(DmsDocument document);
      List< borrow> getView(DmsDocument document);
     
     
     
}
