/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.dao;

import com.px.dms.entity.DmsFolder;
import com.px.share.dao.GenericTreeDao;

/**
 *
 * @author TOP
 */
public interface DmsFolderDao extends GenericTreeDao<DmsFolder, Integer>{
String[] getListChildFolder(int folderId);
   
    
}
