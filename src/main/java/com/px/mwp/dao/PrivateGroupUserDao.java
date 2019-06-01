/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.dao;

import com.px.mwp.entity.PrivateGroupUser;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Oat
 */
public interface PrivateGroupUserDao extends GenericDao<PrivateGroupUser, Integer> {
    List<PrivateGroupUser> listByPrivateGroupId(int privateGroupId, String sort, String dir);
}
