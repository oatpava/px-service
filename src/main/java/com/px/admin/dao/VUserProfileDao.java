/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.admin.dao;

import com.px.admin.entity.VUserProfile;
import java.util.List;

/**
 *
 * @author PRAXiS
 */
public interface VUserProfileDao {

    Integer countAll();

    List<VUserProfile> list(int offset, int limit, String sort, String dir);

    List<VUserProfile> listAll(String sort, String dir);
    
}
