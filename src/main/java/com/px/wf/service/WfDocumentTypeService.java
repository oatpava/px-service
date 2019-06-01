package com.px.wf.service;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.service.GenericTreeService;
import com.px.share.util.TreeUtil;
import com.px.wf.daoimpl.WfDocumentTypeDaoImpl;
import com.px.wf.entity.WfDocumentType;
import com.px.wf.model.WfDocumentTypeModel;
import java.time.LocalDateTime;

/**
 *
 * @author CHALERMPOL
 */
public class WfDocumentTypeService implements GenericTreeService<WfDocumentType, WfDocumentTypeModel> {

    private static final Logger LOG = Logger.getLogger(WfDocumentTypeService.class.getName());
    private final WfDocumentTypeDaoImpl documentTypeDaoImpl;

    public WfDocumentTypeService() {
        this.documentTypeDaoImpl = new WfDocumentTypeDaoImpl();
    }

    @Override
    public String generateParentKey(WfDocumentType documentType) {
        String result = "";
        try {
            result = TreeUtil.generateParentKey(documentType);
        } catch (Exception e) {
            LOG.error("Exception : " + e);
        }

        return result;
    }

    @Override
    public WfDocumentType getParent(WfDocumentType documentType) {
        checkNotNull(documentType, "DocumentType entity must not be null");
        checkNotNull(documentType.getParentId(), "DocumentType parent id must not be null");
        return documentTypeDaoImpl.findParent(documentType.getParentId());
    }

    @Override
    public List<WfDocumentType> listChild(WfDocumentType documentType) {
        checkNotNull(documentType, "DocumentType entity must not be null");
        checkNotNull(documentType.getId(), "DocumentType id must not be null");
        return documentTypeDaoImpl.findChild(documentType.getId());
    }

    public List<WfDocumentType> listChild(WfDocumentType documentType, String sort, String dir) {
        checkNotNull(documentType, "DocumentType entity must not be null");
        checkNotNull(documentType.getId(), "DocumentType id must not be null");
        return documentTypeDaoImpl.findChild(documentType.getId(), sort, dir);
    }

    @Override
    public List<WfDocumentType> listFromParentKey(WfDocumentType documentType) {
        checkNotNull(documentType, "DocumentType entity must not be null");
        checkNotNull(documentType.getParentId(), "DocumentType parent id must not be null");
        ArrayList<WfDocumentType> result = new ArrayList();
        try {
            List parentList = TreeUtil.getListFromParentKey(documentType);
            for (Object tmp : parentList) {
                result.add((WfDocumentType) tmp);
            }
        } catch (Exception e) {
            LOG.error("Exception : " + e);
        }

        return result;
    }

    @Override
    public WfDocumentType create(WfDocumentType documentType) {
        checkNotNull(documentType, "DocumentType entity must not be null");
        checkNotNull(documentType.getDocumentTypeName(), "DocumentType name must not be null");
        checkNotNull(documentType.getCreatedBy(), "create by must not be null");
        documentType = documentTypeDaoImpl.create(documentType);
        documentType.setParentKey(generateParentKey(documentType));
        documentType.setNodeLevel(TreeUtil.generateNodeLevel(documentType));
        String code = "";
        switch (String.valueOf(documentType.getId()).length()) {
            case 1:
                code += "00" + documentType.getId();
                break;
            case 2:
                code += "0" + documentType.getId();
                break;
            case 3:
                code += documentType.getId();
                break;
        }
        documentType.setDocumentTypeCode(documentType.getDocumentTypeCode() + code);
        if (documentType.getOrderNo() == 0) {
            documentType.setOrderNo(documentType.getId());
        }
        documentType = update(documentType);
        return documentType;
    }

    @Override
    public WfDocumentType getById(int id) {
        checkNotNull(id, "DocumentType id entity must not be null");
        return documentTypeDaoImpl.getById(id);
    }

    @Override
    public WfDocumentType update(WfDocumentType documentType) {
        checkNotNull(documentType, "DocumentType entity must not be null");
        checkNotNull(documentType.getDocumentTypeName(), "DocumentType name must not be null");
        checkNotNull(documentType.getUpdatedBy(), "update by must not be null");
        documentType.setUpdatedDate(LocalDateTime.now());
        return documentTypeDaoImpl.update(documentType);
    }

    @Override
    public WfDocumentType remove(int id, int userId) {
        checkNotNull(id, "documentType id must not be null");
        WfDocumentType documentType = getById(id);
        checkNotNull(documentType, "documentType entity not found in database.");
        documentType.setRemovedBy(userId);
        documentType.setRemovedDate(LocalDateTime.now());
        return documentTypeDaoImpl.update(documentType);
    }

    @Override
    public List<WfDocumentType> listAll(String sort, String dir) {
        return documentTypeDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<WfDocumentType> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return documentTypeDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public WfDocumentTypeModel tranformToModel(WfDocumentType documentType) {
        WfDocumentTypeModel documentTypeModel = null;
        if (documentType != null) {
            documentTypeModel = new WfDocumentTypeModel();
            documentTypeModel.setId(documentType.getId());
            documentTypeModel.setDetail(documentType.getDocumentTypeDetail());
            documentTypeModel.setName(documentType.getDocumentTypeName());
            documentTypeModel.setNodeLevel(documentType.getNodeLevel());
            documentTypeModel.setParentId(documentType.getParentId());
            documentTypeModel.setParentKey(documentType.getParentKey());
            documentTypeModel.setCode(documentType.getDocumentTypeCode());
        }
        return documentTypeModel;
    }

    @Override
    public int countAll() {
        return documentTypeDaoImpl.countAll();
    }

    @Override
    public List<WfDocumentType> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        return documentTypeDaoImpl.search(queryParams, offset, limit, sort, dir);
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryWfDocumentTypes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfDocumentType getByIdNotRemoved(int id) {
        checkNotNull(id, "WfDocumentType id entity must not be null");
        return documentTypeDaoImpl.getByIdNotRemoved(id);
    }

    public List<WfDocumentType> searchByNameOrCode(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        return documentTypeDaoImpl.searchByNameOrCode(queryParams, offset, limit, sort, dir);
    }

    public List<WfDocumentType> listAllParent(String sort, String dir) {
        return documentTypeDaoImpl.listAllParent(sort, dir);
    }

    public List<WfDocumentType> listChildByParentId(String sort, String dir, int parentId) {
        return documentTypeDaoImpl.listChildByParentId(sort, dir, parentId);
    }

    public String getMaxCode(int parentId) {
        String code = "000";
        Integer documentTypId = documentTypeDaoImpl.getMaxCode(parentId);
        if (documentTypId != null) {
            WfDocumentType wfDocumentType = documentTypeDaoImpl.getById(documentTypId);
            Integer docCode = Integer.valueOf(wfDocumentType.getDocumentTypeCode()) + 1;
            String docCodeStr = String.valueOf(docCode);
            if (docCodeStr.length() < 3) {
                switch (docCodeStr.length()) {
                    case 1:
                        code = "00" + docCodeStr;
                        break;
                    case 2:
                        code = "0" + docCodeStr;
                        break;
                }
            } else {
                code = docCodeStr.substring(docCodeStr.length() - 3);
            }
        }
        return code;
    }

    public void updateDocumentCodeChildByParent(List<List<WfDocumentType>> listChild, List<Integer> parentId, List<String> parentDocCode) {
        List<List<WfDocumentType>> newChild = new ArrayList<List<WfDocumentType>>();
        List<Integer> listParentId = new ArrayList<Integer>();
        List<String> listParentCode = new ArrayList<String>();
        for (int i = 0; i < listChild.size(); i++) {
            for (int j = 0; j < listChild.get(i).size(); j++) {
                listChild.get(i).get(j).setParentKey(generateParentKey(listChild.get(i).get(j)));
                listChild.get(i).get(j).setNodeLevel(TreeUtil.generateNodeLevel(listChild.get(i).get(j)));
                listChild.get(i).get(j).setParentId(parentId.get(i));
                listChild.get(i).get(j).setDocumentTypeCode(parentDocCode.get(i) + listChild.get(i).get(j).getDocumentTypeCode().substring(listChild.get(i).get(j).getDocumentTypeCode().length() - 3));
                documentTypeDaoImpl.update(listChild.get(i).get(j));
                newChild.add(listChildByParentId("", "", listChild.get(i).get(j).getId()));
                listParentId.add(listChild.get(i).get(j).getId());
                listParentCode.add(listChild.get(i).get(j).getDocumentTypeCode());
            }
        }
        if (!newChild.isEmpty()) {
            updateDocumentCodeChildByParent(newChild, listParentId, listParentCode);
        }
    }
}
