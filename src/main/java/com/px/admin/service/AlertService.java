package com.px.admin.service;

import com.px.admin.daoimpl.AlertDaoImpl;
import com.px.admin.entity.Alert;
import com.px.admin.model.AlertModel;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Oat
 */
public class AlertService implements GenericService<Alert, AlertModel> {

    private static final Logger LOG = Logger.getLogger(AlertService.class.getName());
    private final AlertDaoImpl alertDaoImpl;

    public AlertService() {
        this.alertDaoImpl = new AlertDaoImpl();
    }

    @Override
    public Alert create(Alert alert) {
        alert = alertDaoImpl.create(alert);
//        if (alert.getOrderNo() == 0) {
//            alert.setOrderNo(alert.getId());
//            alert = update(alert);
//        }
        return alert;
    }

    @Override
    public Alert getById(int id) {
        return alertDaoImpl.getById(id);
    }

    @Override
    public Alert getByIdNotRemoved(int id) {
        return alertDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public Alert update(Alert alert) {
        alert.setUpdatedDate(LocalDateTime.now());
        return alertDaoImpl.update(alert);
    }

    @Override
    public Alert remove(int id, int userId) {
        Alert alert = getById(id);
        alert.setRemovedBy(userId);
        alert.setRemovedDate(LocalDateTime.now());
        return alertDaoImpl.update(alert);
    }

    public List<Alert> list(Boolean active, int offset, int limit, String sort, String dir) {
        return alertDaoImpl.list(active, offset, limit, sort, dir);
    }

    public List<Alert> listAll(Boolean active, String sort, String dir) {
        return alertDaoImpl.listAll(active, sort, dir);
    }

    public List<Alert> listAllCurrent(List<Integer> listExcludeId) {
        return alertDaoImpl.listAllCurrent(listExcludeId);
    }

    public int countAll(Boolean active) {
        return alertDaoImpl.countAll(active);
    }

    @Override
    public AlertModel tranformToModel(Alert alert) {
        AlertModel alertModel = null;
        if (alert != null) {
            alertModel = new AlertModel();
            alertModel.setId(alert.getId());
            alertModel.setStartDate(Common.localDateTimeToString4(alert.getStartDate()));
            alertModel.setEndDate(Common.localDateTimeToString4(alert.getEndDate()));
            alertModel.setMessage(alert.getMessage());
            alertModel.setActive(alert.getActive().equalsIgnoreCase("Y"));
        }
        return alertModel;
    }

    @Override
    public List<Alert> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Alert> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Alert> search(MultivaluedMap<String, String> queryAlerts, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryAlerts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
