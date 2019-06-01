
package com.px.mwp.dao;

import com.px.share.dao.GenericDao;
import com.px.mwp.entity.StructureFolder;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface StructureFolderDao extends GenericDao<StructureFolder, Integer>{
    List<StructureFolder> listByStructureId(int structureId);
    List<StructureFolder> listByStructureId(int structureId,String type);
}
