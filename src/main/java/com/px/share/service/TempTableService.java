package com.px.share.service;

import com.px.admin.service.TitleService;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.daoimpl.TempTableDaoImpl;
import com.px.share.entity.TempTable;
import com.px.share.model.TempTableModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
public class TempTableService implements GenericService<TempTable, TempTableModel> {

    private static final Logger LOG = Logger.getLogger(TitleService.class.getName());
    private final TempTableDaoImpl tempTableDaoImpl;

    public TempTableService() {
        this.tempTableDaoImpl = new TempTableDaoImpl();
    }

    @Override
    public TempTable create(TempTable tempTable) {
        checkNotNull(tempTable, "tempTable entity must not be null");
        checkNotNull(tempTable.getCreatedBy(), "create by must not be null");
        return tempTableDaoImpl.create(tempTable);
    }

    @Override
    public TempTable getById(int id) {
        return tempTableDaoImpl.getById(id);
    }

    @Override
    public TempTable getByIdNotRemoved(int id) {
        return tempTableDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public TempTable update(TempTable tempTable) {
        checkNotNull(tempTable, "tempTable entity must not be null");
        checkNotNull(tempTable.getUpdatedBy(), "update by must not be null");
        tempTable.setUpdatedDate(LocalDateTime.now());
        return tempTableDaoImpl.update(tempTable);
    }

    @Override
    public TempTable remove(int id, int userId) {
        checkNotNull(id, "wfFolder id must not be null");
        TempTable TempTable = getById(id);
        checkNotNull(TempTable, "TempTable entity not found in database.");
        TempTable.setRemovedBy(userId);
        TempTable.setRemovedDate(LocalDateTime.now());
        return tempTableDaoImpl.update(TempTable);
    }

    public void delete(TempTable tempTable) {
        checkNotNull(tempTable, "tempTable must not be null");
        tempTableDaoImpl.delete(tempTable);
    }

    @Override
    public List<TempTable> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TempTable> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TempTable> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TempTableModel tranformToModel(TempTable tempTable) {
        TempTableModel tempTableModel = null;
        if (tempTable != null) {
            tempTableModel = new TempTableModel();
            tempTableModel.setId(tempTable.getId());
            tempTableModel.setCreatedBy(tempTable.getCreatedBy());
            tempTableModel.setComputerName(tempTable.getComputerName());
            tempTableModel.setJobType(tempTable.getJobType());
            tempTableModel.setInt01(tempTable.getInt01());
            tempTableModel.setInt02(tempTable.getInt02());
            tempTableModel.setInt03(tempTable.getInt03());
            tempTableModel.setInt04(tempTable.getInt04());
            tempTableModel.setInt05(tempTable.getInt05());
            tempTableModel.setStr01(tempTable.getStr01());
            tempTableModel.setStr02(tempTable.getStr02());
            tempTableModel.setStr03(tempTable.getStr03());
            tempTableModel.setStr04(tempTable.getStr04());
            tempTableModel.setStr05(tempTable.getStr05());
            tempTableModel.setText01(tempTable.getText01());
            tempTableModel.setText02(tempTable.getText02());
            tempTableModel.setText03(tempTable.getText03());
            tempTableModel.setText04(tempTable.getText04());
            tempTableModel.setText05(tempTable.getText05());
            tempTableModel.setText06(tempTable.getText06());
            tempTableModel.setText07(tempTable.getText07());
            tempTableModel.setText08(tempTable.getText08());
            tempTableModel.setText09(tempTable.getText09());
            tempTableModel.setText10(tempTable.getText10());

        }
        return tempTableModel;
    }

    public List<TempTable> listByComputerNameAndJobType(int userID, String computerName, String jobType, String sort, String dir) {
        checkNotNull(userID, "userID must not be null");
        checkNotNull(computerName, "computerName must not be null");
        checkNotNull(jobType, "jobType must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return tempTableDaoImpl.listByComputerNameAndJobType(userID, computerName, jobType, sort, dir);

    }
    
    public List<TempTable> listByJobType(int userID, String jobType, String sort, String dir) {
        checkNotNull(userID, "userID must not be null");
        checkNotNull(jobType, "jobType must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return tempTableDaoImpl.listByJobType(userID, jobType, sort, dir);
        
    }
}
