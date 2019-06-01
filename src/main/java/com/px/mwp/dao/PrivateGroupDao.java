/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.dao;

import com.px.mwp.entity.PrivateGroup;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Oat
 */
public interface PrivateGroupDao extends GenericDao<PrivateGroup, Integer> {
     List<PrivateGroup> listByOwnerIdAndType(int ownerId, int type);
}
