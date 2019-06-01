/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.admin.dao;

import com.px.admin.entity.VStructure;
import java.util.List;

/**
 *
 * @author PRAXiS
 */
public interface VStructureDao {

    Integer countAll();

    List<VStructure> list(int offset, int limit, String sort, String dir);

    List<VStructure> listAll(String sort, String dir);
    
}
