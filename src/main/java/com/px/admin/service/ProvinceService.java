package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.ProvinceDaoImpl;
import com.px.admin.entity.Province;
import com.px.admin.model.ProvinceModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Oat
 */
public class ProvinceService implements GenericService<Province, ProvinceModel> {

    private static final Logger LOG = Logger.getLogger(ProvinceService.class.getName());
    private final ProvinceDaoImpl provinceDaoImpl;

    public ProvinceService() {
        this.provinceDaoImpl = new ProvinceDaoImpl();
    }

    @Override
    public Province create(Province province) {
        checkNotNull(province, "province entity must not be null");
        checkNotNull(province.getCode(), "province code must not be null");
        checkNotNull(province.getName(), "province name must not be null");
        checkNotNull(province.getCreatedBy(), "create by must not be null");
        province = provinceDaoImpl.create(province);
        if (province.getOrderNo() == 0) {
            province.setOrderNo(province.getId());
            province = update(province);
        }
        return province;
    }

    @Override
    public Province getById(int id) {
        checkNotNull(id, "province id entity must not be null");
        return provinceDaoImpl.getById(id);
    }

    public Province getByName(String name) {
        return provinceDaoImpl.getByName(name);
    }

    public Province getByCode(String code) {
        return provinceDaoImpl.getByCode(code);
    }

    @Override
    public Province update(Province province) {
        checkNotNull(province, "province entity must not be null");
        checkNotNull(province.getName(), "province name must not be null");
        checkNotNull(province.getUpdatedBy(), "update by must not be null");
        province.setUpdatedDate(LocalDateTime.now());
        return provinceDaoImpl.update(province);
    }

    @Override
    public Province remove(int id, int userId) {
        checkNotNull(id, "province id must not be null");
        Province province = getById(id);
        checkNotNull(province, "province entity not found in database.");
        province.setRemovedBy(userId);
        province.setRemovedDate(LocalDateTime.now());
        return provinceDaoImpl.update(province);
    }

    @Override
    public List<Province> listAll(String sort, String dir) {
        return provinceDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<Province> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return provinceDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public ProvinceModel tranformToModel(Province province) {
        ProvinceModel provinceModel = null;

        if (province != null) {
            provinceModel = new ProvinceModel();
            provinceModel.setId(province.getId());
            provinceModel.setCode(province.getCode());
            provinceModel.setName(province.getName());
        }
        return provinceModel;
    }

    @Override
    public int countAll() {
        return provinceDaoImpl.countAll();
    }

    @Override
    public List<Province> search(MultivaluedMap<String, String> queryProvinces, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryProvinces) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Province getByIdNotRemoved(int id) {
        checkNotNull(id, "Province id entity must not be null");
        return provinceDaoImpl.getByIdNotRemoved(id);
    }

}
