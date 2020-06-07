package com.px.authority.service;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import com.px.admin.entity.Structure;
import com.px.admin.entity.Submodule;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.authority.daoimpl.SubmoduleUserAuthDaoImpl;
import com.px.authority.entity.Auth;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.authority.model.AuthEnableDisableIdListModel;
import com.px.authority.model.SubmoduleUserAuthModel;
import com.px.authority.model.SubmoduleUserAuthModel_groupStructureUser;
import com.px.authority.model.SubmoduleUserAuthModel_groupStructureUser_authority;
import com.px.share.entity.BaseEntity;
import com.px.share.entity.BaseTreeEntity;
import com.px.share.service.GenericService;
import com.px.share.util.TreeUtil;
import java.time.LocalDateTime;

/**
 *
 * @author Peach
 */
public class SubmoduleUserAuthService implements GenericService<SubmoduleUserAuth, SubmoduleUserAuthModel> {

    private static final Logger LOG = Logger.getLogger(SubmoduleUserAuthService.class.getName());
    private final SubmoduleUserAuthDaoImpl submoduleUserAuthDaoImpl;
    private final String enableAuthority = "1";
    private final String disableAuthority = "2";
    private final String defaultAuthority = disableAuthority;

    public SubmoduleUserAuthService() {
        this.submoduleUserAuthDaoImpl = new SubmoduleUserAuthDaoImpl();
    }

    public String getEnableAuthValue() {
        return enableAuthority;
    }

    public String getDisableAuthValue() {
        return disableAuthority;
    }

    @Override
    public SubmoduleUserAuth create(SubmoduleUserAuth submoduleUserAuth) {
        checkNotNull(submoduleUserAuth, "submoduleUserAuth entity must not be null");
        checkNotNull(submoduleUserAuth.getSubmoduleAuth(), "submoduleUserAuth submoduleAuth must not be null");
        checkNotNull(submoduleUserAuth.getAuthority(), "submoduleUserAuth authority must not be null");
        checkNotNull(submoduleUserAuth.getCreatedBy(), "create by must not be null");
        submoduleUserAuth = submoduleUserAuthDaoImpl.create(submoduleUserAuth);
        if (submoduleUserAuth.getOrderNo() == 0) {
            submoduleUserAuth.setOrderNo(submoduleUserAuth.getId());
            submoduleUserAuth = submoduleUserAuthDaoImpl.update(submoduleUserAuth);
        }

        return submoduleUserAuth;
    }

    @Override
    public SubmoduleUserAuth getById(int id) {
        checkNotNull(id, "submoduleUserAuth id entity must not be null");
        return submoduleUserAuthDaoImpl.getById(id);
    }

    @Override
    public SubmoduleUserAuth update(SubmoduleUserAuth submoduleUserAuth) {
        checkNotNull(submoduleUserAuth, "submoduleUserAuth entity must not be null");
        checkNotNull(submoduleUserAuth.getSubmoduleAuth(), "submoduleUserAuth submoduleAuth must not be null");
        checkNotNull(submoduleUserAuth.getAuthority(), "submoduleUserAuth authority must not be null");
        checkNotNull(submoduleUserAuth.getUpdatedBy(), "update by must not be null");
        submoduleUserAuth.setUpdatedDate(LocalDateTime.now());
        return submoduleUserAuthDaoImpl.update(submoduleUserAuth);
    }

    @Override
    public SubmoduleUserAuth remove(int id, int userId) {
        checkNotNull(id, "submoduleUserAuth id must not be null");
        SubmoduleUserAuth submoduleUserAuth = getById(id);
        checkNotNull(submoduleUserAuth, "submoduleUserAuth entity not found in database.");
        submoduleUserAuth.setRemovedBy(userId);
        submoduleUserAuth.setRemovedDate(LocalDateTime.now());
        return submoduleUserAuthDaoImpl.update(submoduleUserAuth);
    }

    @Override
    public List<SubmoduleUserAuth> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return submoduleUserAuthDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<SubmoduleUserAuth> listAll(String sort, String dir) {
        return submoduleUserAuthDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return submoduleUserAuthDaoImpl.countAll();
    }

    @Override
    public List<SubmoduleUserAuth> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubmoduleUserAuthModel tranformToModel(SubmoduleUserAuth submoduleUserAuth) {
        SubmoduleUserAuthModel submoduleUserAuthModel = null;
        if (submoduleUserAuth != null) {
            StructureService structureService = new StructureService();
            UserProfileService userProfileService = new UserProfileService();
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();

            int id = -1;
            if (submoduleUserAuth.getId() != null) {
                id = submoduleUserAuth.getId();
            }

            int linkId = -1;
            if (submoduleUserAuth.getLinkId() != null) {
                linkId = submoduleUserAuth.getLinkId();
            }

            submoduleUserAuthModel = new SubmoduleUserAuthModel();
            submoduleUserAuthModel.setId(id);
            submoduleUserAuthModel.setStructure(structureService.tranformToModel(submoduleUserAuth.getStructure()));
            submoduleUserAuthModel.setUserProfile(userProfileService.tranformToModel(submoduleUserAuth.getUserProfile()));
            submoduleUserAuthModel.setSubmoduleAuth(submoduleAuthService.tranformToModel(submoduleUserAuth.getSubmoduleAuth()));
            submoduleUserAuthModel.setLinkId(linkId);
            submoduleUserAuthModel.setAuthority(submoduleUserAuth.getAuthority());
        }
        return submoduleUserAuthModel;
    }

    public List<SubmoduleUserAuthModel_groupStructureUser> tranformToModelGroupStructureUser(List<SubmoduleUserAuth> submoduleUserAuthList) {
        List<SubmoduleUserAuthModel_groupStructureUser> result = new ArrayList();

        if (submoduleUserAuthList != null) {
            StructureService structureService = new StructureService();
            UserProfileService userProfileService = new UserProfileService();
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();

            int id;
            int linkId;
            int indexResult;
            SubmoduleUserAuthModel_groupStructureUser submoduleUserAuthModel_groupStructureUser;
            SubmoduleUserAuthModel_groupStructureUser_authority submoduleUserAuthModel_groupStructureUser_authority;

            for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                if (submoduleUserAuth.getId() != null) {
                    id = submoduleUserAuth.getId();
                } else {
                    id = -1;
                }

                if (submoduleUserAuth.getLinkId() != null) {
                    linkId = submoduleUserAuth.getLinkId();
                } else {
                    linkId = -1;
                }

                //Find Index of Duplicate Structure or User in result
                indexResult = -1;
                for (int i = 0; i < result.size(); i++) {
                    SubmoduleUserAuthModel_groupStructureUser resultDetail = result.get(i);

                    if (submoduleUserAuth.getUserProfile() != null
                            && resultDetail.getUserProfile() != null) {

                        if (submoduleUserAuth.getUserProfile().getId().equals(Integer.valueOf(resultDetail.getUserProfile().getId()))) {
                            indexResult = i;
                            break;
                        }
                    }

                    if (submoduleUserAuth.getStructure() != null
                            && resultDetail.getStructure() != null) {

                        if (submoduleUserAuth.getStructure().getId().equals(Integer.valueOf(resultDetail.getStructure().getId()))) {
                            indexResult = i;
                            break;
                        }
                    }
                }

                if (indexResult == -1) {//No Duplicate new Object
                    List<SubmoduleUserAuthModel_groupStructureUser_authority> authorityList = new ArrayList();

                    submoduleUserAuthModel_groupStructureUser = new SubmoduleUserAuthModel_groupStructureUser();
                    submoduleUserAuthModel_groupStructureUser.setStructure(structureService.tranformToModel(submoduleUserAuth.getStructure()));
                    submoduleUserAuthModel_groupStructureUser.setUserProfile(userProfileService.tranformToModel(submoduleUserAuth.getUserProfile()));
                    submoduleUserAuthModel_groupStructureUser.setAuthority(authorityList);

                    indexResult = result.size();
                    result.add(submoduleUserAuthModel_groupStructureUser);
                }

                if (indexResult != -1) {//Add authority
                    submoduleUserAuthModel_groupStructureUser_authority = new SubmoduleUserAuthModel_groupStructureUser_authority();
                    submoduleUserAuthModel_groupStructureUser_authority.setId(id);
                    submoduleUserAuthModel_groupStructureUser_authority.setLinkId(linkId);
                    submoduleUserAuthModel_groupStructureUser_authority.setSubmoduleAuth(submoduleAuthService.tranformToModel(submoduleUserAuth.getSubmoduleAuth()));
                    submoduleUserAuthModel_groupStructureUser_authority.setAuthority(submoduleUserAuth.getAuthority());

                    result.get(indexResult).getAuthority().add(submoduleUserAuthModel_groupStructureUser_authority);
                }
            }
        }
        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(Submodule submodule, Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submodule, "submodule entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityByStructure(submoduleAuthList, structure);
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(Submodule submodule, List<Structure> structureList) {
        checkNotNull(structureList, "structure list must not be null");
        checkNotNull(submodule, "submodule entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityByStructure(submoduleAuthList, structureList);
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(SubmoduleAuth submoduleAuth, Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        ArrayList<SubmoduleAuth> submoduleAuthList = new ArrayList();
        submoduleAuthList.add(submoduleAuth);
        ArrayList<Structure> structureList = new ArrayList();
        structureList.add(structure);
        return this.getAuthorityByStructure(submoduleAuthList, structureList);
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(List<SubmoduleAuth> submoduleAuthList, Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        ArrayList<Structure> structureList = new ArrayList();
        structureList.add(structure);
        return this.getAuthorityByStructure(submoduleAuthList, structureList);
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(SubmoduleAuth submoduleAuth, List<Structure> structureList) {
        checkNotNull(structureList, "structure list must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        ArrayList<SubmoduleAuth> submoduleAuthList = new ArrayList();
        submoduleAuthList.add(submoduleAuth);
        return this.getAuthorityByStructure(submoduleAuthList, structureList);
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(List<SubmoduleAuth> submoduleAuthList, List<Structure> structureList) {
        checkNotNull(structureList, "structure list must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        List<SubmoduleUserAuth> result = new ArrayList();

        StructureService structureService = new StructureService();
        List<Structure> parentKeyList;
        List<SubmoduleUserAuth> submoduleUserAuthList;
        boolean hasAuth;

        for (Structure structure : structureList) {
            parentKeyList = structureService.listFromParentKey(structure);
            submoduleUserAuthList = submoduleUserAuthDaoImpl.listBySubmoduleAuthStructure(submoduleAuthList, parentKeyList, "structure", "asc");

            for (int i = parentKeyList.size() - 1; i >= 0; i--) {//Get Auth order from Child to Parent
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                    if (submoduleUserAuth.getStructure().getId().equals(parentKeyList.get(i).getId())) {
                        //Check Duplicate Result
                        hasAuth = false;
                        for (SubmoduleUserAuth resultSubmoduleUserAuth : result) {
                            if (resultSubmoduleUserAuth.getStructure().getId().equals(structure.getId())
                                    && resultSubmoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleUserAuth.getSubmoduleAuth().getId())) {
                                hasAuth = true;
                                break;
                            }
                        }

                        if (!hasAuth) {
                            SubmoduleUserAuth tmpSubmoduleUserAuth = submoduleUserAuth;
                            if (!tmpSubmoduleUserAuth.getStructure().getId().equals(structure.getId())) {
                                tmpSubmoduleUserAuth.setId(null);
                                tmpSubmoduleUserAuth.setStructure(structure);
                                tmpSubmoduleUserAuth.setUserProfile(null);
                            }
                            result.add(tmpSubmoduleUserAuth);
                        }
                    }
                }

            }
        }

        //Fill missing authority
        for (Structure structure : structureList) {
            for (SubmoduleAuth submoduleAuth : submoduleAuthList) {
                hasAuth = false;
                for (SubmoduleUserAuth submoduleUserAuth : result) {
                    if (submoduleUserAuth.getStructure().getId().equals(structure.getId())
                            && submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                        hasAuth = true;
                        break;
                    }
                }

                if (!hasAuth) {
                    SubmoduleUserAuth tmpSubmoduleUserAuth = new SubmoduleUserAuth();
                    tmpSubmoduleUserAuth.setId(null);
                    tmpSubmoduleUserAuth.setStructure(structure);
                    tmpSubmoduleUserAuth.setUserProfile(null);
                    tmpSubmoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                    tmpSubmoduleUserAuth.setAuthority(defaultAuthority);

                    result.add(tmpSubmoduleUserAuth);
                }
            }
        }

        result = orderSubmoduleUserAuthBySubmoduleAuth(submoduleAuthList, result);

        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(Submodule submodule, Structure structure, BaseEntity entity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(entity, "entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityByStructure(submoduleAuthList, structure, entity);
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(Submodule submodule, List<Structure> structureList, BaseEntity entity) {
        checkNotNull(structureList, "structure list must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(entity, "entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityByStructure(submoduleAuthList, structureList, entity);
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(List<SubmoduleAuth> submoduleAuthList, Structure structure, BaseEntity entity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(entity, "entity must not be null");
        ArrayList<Structure> structureList = new ArrayList();
        structureList.add(structure);
        return this.getAuthorityByStructure(submoduleAuthList, structureList, entity);
    }

    public List<SubmoduleUserAuth> getAuthorityByStructure(List<SubmoduleAuth> submoduleAuthList, List<Structure> structureList, BaseEntity entity) {
        checkNotNull(structureList, "structure list must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(entity, "entity must not be null");
        List<SubmoduleUserAuth> result = new ArrayList();

        StructureService structureService = new StructureService();
        List<Structure> parentKeyList;
        List<SubmoduleUserAuth> submoduleUserAuthList;
        boolean hasAuth;

        Integer linkId = entity.getId();

        for (Structure structure : structureList) {
            parentKeyList = structureService.listFromParentKey(structure);
            submoduleUserAuthList = submoduleUserAuthDaoImpl.listBySubmoduleAuthLinkIdStructure(submoduleAuthList, linkId, parentKeyList, "structure", "asc");

            for (int i = parentKeyList.size() - 1; i >= 0; i--) {//Get Auth order from Child to Parent
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                    if (submoduleUserAuth.getStructure().getId().equals(parentKeyList.get(i).getId())) {
                        //Check Duplicate Result
                        hasAuth = false;
                        for (SubmoduleUserAuth resultSubmoduleUserAuth : result) {
                            if (resultSubmoduleUserAuth.getStructure().getId().equals(structure.getId())
                                    && resultSubmoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleUserAuth.getSubmoduleAuth().getId())) {
                                hasAuth = true;
                                break;
                            }
                        }

                        if (!hasAuth) {
                            SubmoduleUserAuth tmpSubmoduleUserAuth = submoduleUserAuth;
                            if (!tmpSubmoduleUserAuth.getStructure().getId().equals(structure.getId())) {
                                tmpSubmoduleUserAuth.setId(null);
                                tmpSubmoduleUserAuth.setStructure(structure);
                                tmpSubmoduleUserAuth.setUserProfile(null);
                            }
                            result.add(tmpSubmoduleUserAuth);
                        }
                    }
                }

            }
        }

        //Fill missing authority
        for (Structure structure : structureList) {
            for (SubmoduleAuth submoduleAuth : submoduleAuthList) {
                hasAuth = false;
                for (SubmoduleUserAuth submoduleUserAuth : result) {
                    if (submoduleUserAuth.getStructure().getId().equals(structure.getId())
                            && submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                        hasAuth = true;
                        break;
                    }
                }

                if (!hasAuth) {
                    SubmoduleUserAuth tmpSubmoduleUserAuth = new SubmoduleUserAuth();
                    tmpSubmoduleUserAuth.setId(null);
                    tmpSubmoduleUserAuth.setStructure(structure);
                    tmpSubmoduleUserAuth.setUserProfile(null);
                    tmpSubmoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                    tmpSubmoduleUserAuth.setAuthority(defaultAuthority);
                    tmpSubmoduleUserAuth.setLinkId(linkId);

                    result.add(tmpSubmoduleUserAuth);
                }
            }
        }

        result = orderSubmoduleUserAuthBySubmoduleAuth(submoduleAuthList, result);

        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityNotFillMissingByStructure(List<SubmoduleAuth> submoduleAuthList, Structure structure, BaseEntity entity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(entity, "entity must not be null");
        ArrayList<Structure> structureList = new ArrayList();
        structureList.add(structure);
        return this.getAuthorityNotFillMissingByStructure(submoduleAuthList, structureList, entity);
    }

    public List<SubmoduleUserAuth> getAuthorityNotFillMissingByStructure(List<SubmoduleAuth> submoduleAuthList, List<Structure> structureList, BaseEntity entity) {
        checkNotNull(structureList, "structure list must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(entity, "entity must not be null");
        ArrayList<SubmoduleUserAuth> result = new ArrayList();

        StructureService structureService = new StructureService();
        List<Structure> parentKeyList;
        List<SubmoduleUserAuth> submoduleUserAuthList;
        boolean hasAuth;

        Integer linkId = entity.getId();

        for (Structure structure : structureList) {
            parentKeyList = structureService.listFromParentKey(structure);
            submoduleUserAuthList = submoduleUserAuthDaoImpl.listBySubmoduleAuthLinkIdStructure(submoduleAuthList, linkId, parentKeyList, "structure", "asc");

            for (int i = parentKeyList.size() - 1; i >= 0; i--) {//Get Auth order from Child to Parent
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                    if (submoduleUserAuth.getStructure().getId().equals(parentKeyList.get(i).getId())) {
                        //Check Duplicate Result
                        hasAuth = false;
                        for (SubmoduleUserAuth resultSubmoduleUserAuth : result) {
                            if (resultSubmoduleUserAuth.getStructure().getId().equals(structure.getId())
                                    && resultSubmoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleUserAuth.getSubmoduleAuth().getId())) {
                                hasAuth = true;
                                break;
                            }
                        }

                        if (!hasAuth) {
                            SubmoduleUserAuth tmpSubmoduleUserAuth = submoduleUserAuth;
                            if (!tmpSubmoduleUserAuth.getStructure().getId().equals(structure.getId())) {
                                tmpSubmoduleUserAuth.setId(null);
                                tmpSubmoduleUserAuth.setStructure(structure);
                                tmpSubmoduleUserAuth.setUserProfile(null);
                            }
                            result.add(tmpSubmoduleUserAuth);
                        }
                    }
                }

            }
        }

        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByStructure(Submodule submodule, Structure structure, BaseTreeEntity treeEntity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityFromTreeByStructure(submoduleAuthList, structure, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByStructure(Submodule submodule, List<Structure> structureList, BaseTreeEntity treeEntity) {
        checkNotNull(structureList, "structure list must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityFromTreeByStructure(submoduleAuthList, structureList, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByStructure(SubmoduleAuth submoduleAuth, Structure structure, BaseTreeEntity treeEntity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");
        ArrayList<SubmoduleAuth> submoduleAuthList = new ArrayList();
        submoduleAuthList.add(submoduleAuth);
        ArrayList<Structure> structureList = new ArrayList();
        structureList.add(structure);
        return this.getAuthorityFromTreeByStructure(submoduleAuthList, structureList, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByStructure(List<SubmoduleAuth> submoduleAuthList, Structure structure, BaseTreeEntity treeEntity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");
        ArrayList<Structure> structureList = new ArrayList();
        structureList.add(structure);
        return this.getAuthorityFromTreeByStructure(submoduleAuthList, structureList, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByStructure(List<SubmoduleAuth> submoduleAuthList, List<Structure> structureList, BaseTreeEntity treeEntity) {
        checkNotNull(structureList, "structure list must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");
        List<SubmoduleUserAuth> result = new ArrayList();

        StructureService structureService = new StructureService();
        List<Structure> parentKeyList;
        List<SubmoduleUserAuth> submoduleUserAuthList;
        boolean hasAuth;

        Integer linkId = treeEntity.getId();
        ArrayList<Integer> linkIdList = new ArrayList();

        try {
            List<BaseTreeEntity> treeEntityList = TreeUtil.getListFromParentKey(treeEntity);
            for (BaseTreeEntity baseTreeEntity : treeEntityList) {
                if (baseTreeEntity.getId() != null) {
                    linkIdList.add(baseTreeEntity.getId());
                }
            }
        } catch (Exception e) {
            LOG.info("Exception:getAuthorityByStructure = " + e);
        }

        if (linkIdList.isEmpty()) {
            linkIdList.add(linkId);
        }

        for (Structure structure : structureList) {
            parentKeyList = structureService.listFromParentKey(structure);
            submoduleUserAuthList = submoduleUserAuthDaoImpl.listBySubmoduleAuthLinkIdStructure(submoduleAuthList, linkIdList, parentKeyList, "structure", "asc");

            for (int l = linkIdList.size() - 1; l >= 0; l--) {//Get Link Auth order from Child to Parent
                for (int i = parentKeyList.size() - 1; i >= 0; i--) {//Get Structure Auth order from Child to Parent
                    for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                        if (submoduleUserAuth.getStructure().getId().equals(parentKeyList.get(i).getId())
                                && submoduleUserAuth.getLinkId().equals(linkIdList.get(l))) {
                            //Check Duplicate Result
                            hasAuth = false;
                            for (SubmoduleUserAuth resultSubmoduleUserAuth : result) {
                                if (resultSubmoduleUserAuth.getStructure().getId().equals(structure.getId())
                                        && resultSubmoduleUserAuth.getLinkId().equals(linkId)
                                        && resultSubmoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleUserAuth.getSubmoduleAuth().getId())) {
                                    hasAuth = true;
                                    break;
                                }
                            }

                            if (!hasAuth) {
                                SubmoduleUserAuth tmpSubmoduleUserAuth = submoduleUserAuth;
                                //Change to new Object if get Auth from parent
                                if (!tmpSubmoduleUserAuth.getStructure().getId().equals(structure.getId())
                                        || !tmpSubmoduleUserAuth.getLinkId().equals(linkId)) {
                                    tmpSubmoduleUserAuth.setId(null);
                                    tmpSubmoduleUserAuth.setStructure(structure);
                                    tmpSubmoduleUserAuth.setUserProfile(null);
                                    tmpSubmoduleUserAuth.setLinkId(linkId);
                                }
                                result.add(tmpSubmoduleUserAuth);
                            }
                        }
                    }
                }
            }
        }

        //Fill missing authority
        for (Structure structure : structureList) {
            for (SubmoduleAuth submoduleAuth : submoduleAuthList) {
                hasAuth = false;
                for (SubmoduleUserAuth submoduleUserAuth : result) {
                    if (submoduleUserAuth.getStructure().getId().equals(structure.getId())
                            && submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                        hasAuth = true;
                        break;
                    }
                }

                if (!hasAuth) {
                    SubmoduleUserAuth tmpSubmoduleUserAuth = new SubmoduleUserAuth();
                    tmpSubmoduleUserAuth.setId(null);
                    tmpSubmoduleUserAuth.setStructure(structure);
                    tmpSubmoduleUserAuth.setUserProfile(null);
                    tmpSubmoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                    tmpSubmoduleUserAuth.setAuthority(defaultAuthority);
                    tmpSubmoduleUserAuth.setLinkId(linkId);

                    result.add(tmpSubmoduleUserAuth);
                }
            }
        }

        result = orderSubmoduleUserAuthBySubmoduleAuth(submoduleAuthList, result);

        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(Submodule submodule, UserProfile userProfile) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submodule, "submodule entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityByUserProfile(submoduleAuthList, userProfile);
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(Submodule submodule, List<UserProfile> userProfileList) {
        checkNotNull(userProfileList, "userProfile list must not be null");
        checkNotNull(submodule, "submodule entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityByUserProfile(submoduleAuthList, userProfileList);
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(SubmoduleAuth submoduleAuth, UserProfile userProfile) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        ArrayList<SubmoduleAuth> submoduleAuthList = new ArrayList();
        submoduleAuthList.add(submoduleAuth);
        ArrayList<UserProfile> userProfileList = new ArrayList();
        userProfileList.add(userProfile);
        return this.getAuthorityByUserProfile(submoduleAuthList, userProfileList);
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(List<SubmoduleAuth> submoduleAuthList, UserProfile userProfile) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        ArrayList<UserProfile> userProfileList = new ArrayList();
        userProfileList.add(userProfile);
        return this.getAuthorityByUserProfile(submoduleAuthList, userProfileList);
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(SubmoduleAuth submoduleAuth, List<UserProfile> userProfileList) {
        checkNotNull(userProfileList, "userProfile list must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        ArrayList<SubmoduleAuth> submoduleAuthList = new ArrayList();
        submoduleAuthList.add(submoduleAuth);
        return this.getAuthorityByUserProfile(submoduleAuthList, userProfileList);
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(List<SubmoduleAuth> submoduleAuthList, List<UserProfile> userProfileList) {
        checkNotNull(userProfileList, "userProfile list must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        List<SubmoduleUserAuth> result = new ArrayList();

        List<SubmoduleUserAuth> submoduleUserAuthList;
        List<SubmoduleUserAuth> structureSubmoduleUserAuthList;

        boolean hasAuth;
        boolean hasResult;

        for (UserProfile userProfile : userProfileList) {
            submoduleUserAuthList = submoduleUserAuthDaoImpl.listBySubmoduleAuthUserProfile(submoduleAuthList, userProfile, "orderNo", "asc");

            //Check List Have all result for userProfile
            hasResult = true;
            for (SubmoduleAuth submoduleAuth : submoduleAuthList) {
                hasAuth = false;
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                    if (submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                        hasAuth = true;
                        break;
                    }
                }
                if (!hasAuth) {
                    hasResult = false;
                }
            }

            //Get Auth From Structure
            if (userProfile.getStructure() != null && !hasResult) {
                structureSubmoduleUserAuthList = this.getAuthorityByStructure(submoduleAuthList, userProfile.getStructure());
                if (!structureSubmoduleUserAuthList.isEmpty()) {
                    for (SubmoduleUserAuth structureSubmoduleUserAuth : structureSubmoduleUserAuthList) {
                        //Check Duplicate
                        hasAuth = false;
                        for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                            if (submoduleUserAuth.getSubmoduleAuth().getId().equals(structureSubmoduleUserAuth.getSubmoduleAuth().getId())) {
                                hasAuth = true;
                                break;
                            }
                        }

                        if (!hasAuth) {
                            structureSubmoduleUserAuth.setId(null);
                            structureSubmoduleUserAuth.setStructure(null);
                            structureSubmoduleUserAuth.setUserProfile(userProfile);

                            submoduleUserAuthList.add(structureSubmoduleUserAuth);
                        }
                    }
                }
            }

            if (!submoduleUserAuthList.isEmpty()) {
                for (SubmoduleUserAuth subModuleUserAuth : submoduleUserAuthList) {
                    result.add(subModuleUserAuth);
                }
            }
        }

        //Fill missing authority
        for (UserProfile userProfile : userProfileList) {
            for (SubmoduleAuth submoduleAuth : submoduleAuthList) {
                hasAuth = false;
                for (SubmoduleUserAuth submoduleUserAuth : result) {
                    if (submoduleUserAuth.getUserProfile().getId().equals(userProfile.getId())
                            && submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                        hasAuth = true;
                        break;
                    }
                }

                if (!hasAuth) {
                    SubmoduleUserAuth tmpSubmoduleUserAuth = new SubmoduleUserAuth();
                    tmpSubmoduleUserAuth.setId(null);
                    tmpSubmoduleUserAuth.setStructure(null);
                    tmpSubmoduleUserAuth.setUserProfile(userProfile);
                    tmpSubmoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                    tmpSubmoduleUserAuth.setAuthority(defaultAuthority);

                    result.add(tmpSubmoduleUserAuth);
                }
            }
        }

        result = orderSubmoduleUserAuthBySubmoduleAuth(submoduleAuthList, result);

        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(Submodule submodule, UserProfile userProfile, BaseEntity entity) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(entity, "entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityByUserProfile(submoduleAuthList, userProfile, entity);
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(Submodule submodule, List<UserProfile> userProfileList, BaseEntity entity) {
        checkNotNull(userProfileList, "userProfile list must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(entity, "entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityByUserProfile(submoduleAuthList, userProfileList, entity);
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(List<SubmoduleAuth> submoduleAuthList, UserProfile userProfile, BaseEntity entity) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(entity, "entity must not be null");
        ArrayList<UserProfile> userProfileList = new ArrayList();
        userProfileList.add(userProfile);
        return this.getAuthorityByUserProfile(submoduleAuthList, userProfileList, entity);
    }

    public List<SubmoduleUserAuth> getAuthorityByUserProfile(List<SubmoduleAuth> submoduleAuthList, List<UserProfile> userProfileList, BaseEntity entity) {
        checkNotNull(userProfileList, "userProfile list must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(entity, "entity must not be null");
        List<SubmoduleUserAuth> result = new ArrayList();

        List<SubmoduleUserAuth> submoduleUserAuthList;
        List<SubmoduleUserAuth> structureSubmoduleUserAuthList;

        Integer linkId = entity.getId();
        boolean hasAuth;
        boolean hasResult;

        List<SubmoduleAuth> missingSubmoduleAuthList = new ArrayList();

        for (UserProfile userProfile : userProfileList) {
            submoduleUserAuthList = submoduleUserAuthDaoImpl.listBySubmoduleAuthLinkIdUserProfile(submoduleAuthList, linkId, userProfile, "orderNo", "asc");

            //Check List Have all result for userProfile
            hasResult = true;
            missingSubmoduleAuthList.clear();
            for (SubmoduleAuth submoduleAuth : submoduleAuthList) {
                hasAuth = false;
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                    if (submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                        hasAuth = true;
                        break;
                    }
                }
                if (!hasAuth) {
                    hasResult = false;
                    missingSubmoduleAuthList.add(submoduleAuth);
                }
            }
            if (!hasResult) {
                //Get Auth From Structure
                if (userProfile.getStructure() != null) {
                    structureSubmoduleUserAuthList = this.getAuthorityByStructure(missingSubmoduleAuthList, userProfile.getStructure(), entity);
                    if (!structureSubmoduleUserAuthList.isEmpty()) {
                        for (SubmoduleUserAuth structureSubmoduleUserAuth : structureSubmoduleUserAuthList) {
                            //Check Duplicate
                            hasAuth = false;
                            for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                                if (submoduleUserAuth.getSubmoduleAuth().getId().equals(structureSubmoduleUserAuth.getSubmoduleAuth().getId())
                                        && submoduleUserAuth.getLinkId().equals(structureSubmoduleUserAuth.getLinkId())) {
                                    hasAuth = true;
                                    break;
                                }
                            }

                            if (!hasAuth) {
                                structureSubmoduleUserAuth.setId(null);
                                structureSubmoduleUserAuth.setStructure(null);
                                structureSubmoduleUserAuth.setUserProfile(userProfile);

                                submoduleUserAuthList.add(structureSubmoduleUserAuth);
                            }
                        }
                    }
                }

                //Fill default for missing authority
                for (SubmoduleAuth submoduleAuth : missingSubmoduleAuthList) {
                    hasAuth = false;
                    for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                        if (submoduleUserAuth.getUserProfile().getId().equals(userProfile.getId())
                                && submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                            hasAuth = true;
                            break;
                        }
                    }

                    if (!hasAuth) {
                        SubmoduleUserAuth tmpSubmoduleUserAuth = new SubmoduleUserAuth();
                        tmpSubmoduleUserAuth.setId(null);
                        tmpSubmoduleUserAuth.setStructure(null);
                        tmpSubmoduleUserAuth.setUserProfile(userProfile);
                        tmpSubmoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                        tmpSubmoduleUserAuth.setAuthority(defaultAuthority);
                        tmpSubmoduleUserAuth.setLinkId(linkId);

                        submoduleUserAuthList.add(tmpSubmoduleUserAuth);
                    }
                }
            }

            if (!submoduleUserAuthList.isEmpty()) {
                for (SubmoduleUserAuth subModuleUserAuth : submoduleUserAuthList) {
                    result.add(subModuleUserAuth);
                }
            }
        }

        /*//Fill missing authority
        for(UserProfile userProfile : userProfileList){
            for(SubmoduleAuth submoduleAuth : submoduleAuthList){
                hasAuth = false;
                for(SubmoduleUserAuth submoduleUserAuth : result){
                    if(submoduleUserAuth.getUserProfile().getId().equals(userProfile.getId())
                        && submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())){
                        hasAuth = true;
                        break;
                    }
                }
                
                if(!hasAuth){
                    SubmoduleUserAuth tmpSubmoduleUserAuth = new SubmoduleUserAuth();
                    tmpSubmoduleUserAuth.setId(null);
                    tmpSubmoduleUserAuth.setStructure(null);
                    tmpSubmoduleUserAuth.setUserProfile(userProfile);
                    tmpSubmoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                    tmpSubmoduleUserAuth.setAuthority(defaultAuthority);
                    tmpSubmoduleUserAuth.setLinkId(linkId);
                    
                    result.add(tmpSubmoduleUserAuth);
                }
            }
        }*/
        result = orderSubmoduleUserAuthBySubmoduleAuth(submoduleAuthList, result);

        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByUserProfile(Submodule submodule, UserProfile userProfile, BaseTreeEntity treeEntity) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityFromTreeByUserProfile(submoduleAuthList, userProfile, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByUserProfile(Submodule submodule, List<UserProfile> userProfileList, BaseTreeEntity treeEntity) {
        checkNotNull(userProfileList, "userProfile list must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityFromTreeByUserProfile(submoduleAuthList, userProfileList, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByUserProfile(SubmoduleAuth submoduleAuth, UserProfile userProfile, BaseTreeEntity treeEntity) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");
        ArrayList<SubmoduleAuth> submoduleAuthList = new ArrayList();
        submoduleAuthList.add(submoduleAuth);
        ArrayList<UserProfile> userProfileList = new ArrayList();
        userProfileList.add(userProfile);
        return this.getAuthorityFromTreeByUserProfile(submoduleAuthList, userProfileList, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByUserProfile(List<SubmoduleAuth> submoduleAuthList, UserProfile userProfile, BaseTreeEntity treeEntity) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");
        ArrayList<UserProfile> userProfileList = new ArrayList();
        userProfileList.add(userProfile);
        return this.getAuthorityFromTreeByUserProfile(submoduleAuthList, userProfileList, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityFromTreeByUserProfile(List<SubmoduleAuth> submoduleAuthList, List<UserProfile> userProfileList, BaseTreeEntity treeEntity) {
        checkNotNull(userProfileList, "userProfile list must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");
        List<SubmoduleUserAuth> result = new ArrayList();

        List<SubmoduleUserAuth> submoduleUserAuthList;
        List<SubmoduleUserAuth> userProfileSubmoduleUserAuthList;
        List<SubmoduleUserAuth> structureSubmoduleUserAuthList;

        boolean hasAuth;
        boolean hasResult = false;

        List<SubmoduleAuth> missingSubmoduleAuthList = new ArrayList();
        /*//Set default Missing
        for(SubmoduleAuth submoduleAuth : submoduleAuthList){
             missingSubmoduleAuthList.add(submoduleAuth);
        }*/

        Integer linkId = treeEntity.getId();
        ArrayList<Integer> linkIdList = new ArrayList();
        List<BaseTreeEntity> treeEntityList = new ArrayList();
        try {
            treeEntityList = TreeUtil.getListFromParentKey(treeEntity);
            for (BaseTreeEntity baseTreeEntity : treeEntityList) {
                if (baseTreeEntity.getId() != null) {
                    linkIdList.add(baseTreeEntity.getId());
                }
            }
        } catch (Exception e) {
            LOG.info("Exception:SubmoduleUserAuthService:getListFromParentKey = " + e);
        }

        if (linkIdList.isEmpty()) {
            LOG.info("linkId = " + linkId);
            linkIdList.add(linkId);
        }

        for (UserProfile userProfile : userProfileList) {
            userProfileSubmoduleUserAuthList = submoduleUserAuthDaoImpl.listBySubmoduleAuthLinkIdUserProfile(submoduleAuthList, linkIdList, userProfile, "orderNo", "asc");

            submoduleUserAuthList = new ArrayList();
            for (int t = treeEntityList.size() - 1; t >= 0; t--) {//Get Link Auth order from Child to Parent
                //Get From User Profile
                for (SubmoduleUserAuth userProfileSubmoduleUserAuth : userProfileSubmoduleUserAuthList) {
                    if (userProfileSubmoduleUserAuth.getLinkId().equals(treeEntityList.get(t).getId())) {
                        //Check Duplicate
                        hasAuth = false;
                        for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                            if (submoduleUserAuth.getSubmoduleAuth().getId().equals(userProfileSubmoduleUserAuth.getSubmoduleAuth().getId())) {
                                hasAuth = true;
                                break;
                            }
                        }

                        if (!hasAuth) {
                            if (!userProfileSubmoduleUserAuth.getLinkId().equals(linkId)) {
                                userProfileSubmoduleUserAuth.setId(null);
                                userProfileSubmoduleUserAuth.setStructure(null);
                                userProfileSubmoduleUserAuth.setLinkId(linkId);
                            }

                            submoduleUserAuthList.add(userProfileSubmoduleUserAuth);
                        }
                    }
                }

                //Check List Have all result for userProfile
                hasResult = true;
                missingSubmoduleAuthList.clear();
                for (SubmoduleAuth submoduleAuth : submoduleAuthList) {
                    hasAuth = false;
                    for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                        if (submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                            hasAuth = true;
                            break;
                        }
                    }
                    if (!hasAuth) {
                        hasResult = false;
                        missingSubmoduleAuthList.add(submoduleAuth);
                    }
                }

                if (!hasResult) {
                    //Get Auth From Structure
                    if (userProfile.getStructure() != null) {
                        structureSubmoduleUserAuthList = this.getAuthorityNotFillMissingByStructure(missingSubmoduleAuthList, userProfile.getStructure(), treeEntityList.get(t));
                        for (SubmoduleUserAuth structureSubmoduleUserAuth : structureSubmoduleUserAuthList) {
                            if (structureSubmoduleUserAuth.getLinkId().equals(treeEntityList.get(t).getId())) {
                                //Check Duplicate
                                hasAuth = false;
                                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                                    if (submoduleUserAuth.getSubmoduleAuth().getId().equals(structureSubmoduleUserAuth.getSubmoduleAuth().getId())) {
                                        hasAuth = true;
                                        break;
                                    }
                                }

                                if (!hasAuth) {
                                    structureSubmoduleUserAuth.setId(null);
                                    structureSubmoduleUserAuth.setStructure(null);
                                    structureSubmoduleUserAuth.setUserProfile(userProfile);
                                    structureSubmoduleUserAuth.setLinkId(linkId);

                                    submoduleUserAuthList.add(structureSubmoduleUserAuth);
                                }
                            }
                        }
                    }
                }

                //Check List Have all result for userProfile
                hasResult = true;
                for (SubmoduleAuth submoduleAuth : submoduleAuthList) {
                    hasAuth = false;
                    for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                        if (submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                            hasAuth = true;
                            break;
                        }
                    }
                    if (!hasAuth) {
                        hasResult = false;
                    }
                }

                if (hasResult) {
                    break;
                }
            }

            if (!hasResult) {
                //Fill missing authority
                for (SubmoduleAuth submoduleAuth : missingSubmoduleAuthList) {
                    hasAuth = false;
                    for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                        if (submoduleUserAuth.getUserProfile().getId().equals(userProfile.getId())
                                && submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                            hasAuth = true;
                            break;
                        }
                    }

                    if (!hasAuth) {
                        SubmoduleUserAuth tmpSubmoduleUserAuth = new SubmoduleUserAuth();
                        tmpSubmoduleUserAuth.setId(null);
                        tmpSubmoduleUserAuth.setStructure(null);
                        tmpSubmoduleUserAuth.setUserProfile(userProfile);
                        tmpSubmoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                        tmpSubmoduleUserAuth.setAuthority(defaultAuthority);
                        tmpSubmoduleUserAuth.setLinkId(linkId);

                        submoduleUserAuthList.add(tmpSubmoduleUserAuth);
                    }
                }
            }

            if (!submoduleUserAuthList.isEmpty()) {
                for (SubmoduleUserAuth moduleUserAuth : submoduleUserAuthList) {
                    result.add(moduleUserAuth);
                }
            }
        }

        /*//Fill missing authority
        for(UserProfile userProfile : userProfileList){
            for(SubmoduleAuth submoduleAuth : submoduleAuthList){
                hasAuth = false;
                for(SubmoduleUserAuth submoduleUserAuth : result){
                    if(submoduleUserAuth.getUserProfile().getId().equals(userProfile.getId())
                        && submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())){
                        hasAuth = true;
                        break;
                    }
                }
                
                if(!hasAuth){
                    SubmoduleUserAuth tmpSubmoduleUserAuth = new SubmoduleUserAuth();
                    tmpSubmoduleUserAuth.setId(null);
                    tmpSubmoduleUserAuth.setStructure(null);
                    tmpSubmoduleUserAuth.setUserProfile(userProfile);
                    tmpSubmoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                    tmpSubmoduleUserAuth.setAuthority(defaultAuthority);
                    tmpSubmoduleUserAuth.setLinkId(linkId);
                    
                    result.add(tmpSubmoduleUserAuth);
                }
            }
        }*/
        result = orderSubmoduleUserAuthBySubmoduleAuth(submoduleAuthList, result);

        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityOfChildByStructure(Submodule submodule, Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submodule, "submodule entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityOfChildByStructure(submoduleAuthList, structure);
    }

    public List<SubmoduleUserAuth> getAuthorityOfChildByStructure(SubmoduleAuth submoduleAuth, Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        ArrayList<SubmoduleAuth> submoduleAuthList = new ArrayList();
        submoduleAuthList.add(submoduleAuth);
        return this.getAuthorityOfChildByStructure(submoduleAuthList, structure);
    }

    public List<SubmoduleUserAuth> getAuthorityOfChildByStructure(List<SubmoduleAuth> submoduleAuthList, Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        List<SubmoduleUserAuth> result = new ArrayList();

        //----------------Find Structure List----------------------------
        StructureService structureService = new StructureService();
        List<Structure> childList = structureService.listChild(structure, "orderNo", "asc");
        ArrayList<Structure> structureList = new ArrayList();
        //structureList.add(structure);
        for (Structure child : childList) {
            structureList.add(child);
        }

        result = this.getAuthorityByStructure(submoduleAuthList, structureList);

        //----------------Find User Profile List----------------------------
        UserProfileService userProfileService = new UserProfileService();
        List<UserProfile> userProfileList = userProfileService.listByStructure(structure, "orderNo", "asc");
        List<SubmoduleUserAuth> resultUserProfile = this.getAuthorityByUserProfile(submoduleAuthList, userProfileList);
        for (SubmoduleUserAuth submoduleUserAuth : resultUserProfile) {
            result.add(submoduleUserAuth);
        }

        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityOfChildByStructure(Submodule submodule, Structure structure, BaseEntity entity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(entity, "entity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityOfChildByStructure(submoduleAuthList, structure, entity);
    }

    public List<SubmoduleUserAuth> getAuthorityOfChildByStructure(SubmoduleAuth submoduleAuth, Structure structure, BaseEntity entity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(entity, "entity must not be null");

        ArrayList<SubmoduleAuth> submoduleAuthList = new ArrayList();
        submoduleAuthList.add(submoduleAuth);
        return this.getAuthorityOfChildByStructure(submoduleAuthList, structure, entity);
    }

    public List<SubmoduleUserAuth> getAuthorityOfChildByStructure(List<SubmoduleAuth> submoduleAuthList, Structure structure, BaseEntity entity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(entity, "entity must not be null");
        List<SubmoduleUserAuth> result = new ArrayList();

        //----------------Find Structure List----------------------------
        StructureService structureService = new StructureService();
        List<Structure> childList = structureService.listChild(structure, "orderNo", "asc");
        ArrayList<Structure> structureList = new ArrayList();
        //structureList.add(structure);
        for (Structure child : childList) {
            structureList.add(child);
        }

        result = this.getAuthorityByStructure(submoduleAuthList, structureList, entity);

        //----------------Find User Profile List----------------------------
        UserProfileService userProfileService = new UserProfileService();
        List<UserProfile> userProfileList = userProfileService.listByStructure(structure, "orderNo", "asc");
        List<SubmoduleUserAuth> resultUserProfile = this.getAuthorityByUserProfile(submoduleAuthList, userProfileList, entity);
        for (SubmoduleUserAuth submoduleUserAuth : resultUserProfile) {
            result.add(submoduleUserAuth);
        }

        return result;
    }

    public List<SubmoduleUserAuth> getAuthorityOfChildFromTreeByStructure(Submodule submodule, Structure structure, BaseTreeEntity treeEntity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");

        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<SubmoduleAuth> submoduleAuthList = submoduleAuthService.listBySubmodule(submodule, "orderNo", "asc");

        return this.getAuthorityOfChildFromTreeByStructure(submoduleAuthList, structure, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityOfChildFromTreeByStructure(SubmoduleAuth submoduleAuth, Structure structure, BaseTreeEntity treeEntity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");

        ArrayList<SubmoduleAuth> submoduleAuthList = new ArrayList();
        submoduleAuthList.add(submoduleAuth);
        return this.getAuthorityOfChildFromTreeByStructure(submoduleAuthList, structure, treeEntity);
    }

    public List<SubmoduleUserAuth> getAuthorityOfChildFromTreeByStructure(List<SubmoduleAuth> submoduleAuthList, Structure structure, BaseTreeEntity treeEntity) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuthList, "submoduleAuth list must not be null");
        checkNotNull(treeEntity, "treeEntity must not be null");
        List<SubmoduleUserAuth> result = new ArrayList();

        //----------------Find Structure List----------------------------
        StructureService structureService = new StructureService();
        List<Structure> childList = structureService.listChild(structure, "orderNo", "asc");
        ArrayList<Structure> structureList = new ArrayList();
        //structureList.add(structure);
        for (Structure child : childList) {
            structureList.add(child);
        }

        result = this.getAuthorityFromTreeByStructure(submoduleAuthList, structureList, treeEntity);

        //----------------Find User Profile List----------------------------
        UserProfileService userProfileService = new UserProfileService();
        List<UserProfile> userProfileList = userProfileService.listByStructure(structure, "orderNo", "asc");
        List<SubmoduleUserAuth> resultUserProfile = this.getAuthorityFromTreeByUserProfile(submoduleAuthList, userProfileList, treeEntity);
        for (SubmoduleUserAuth submoduleUserAuth : resultUserProfile) {
            result.add(submoduleUserAuth);
        }

        return result;
    }

    public List getEntityByStructureAuthority(SubmoduleAuth submoduleAuth, Structure structure, String authority, List baseEntityList) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(baseEntityList, "baseEntity list must not be null");
        checkNotNull(authority, "authority must not be null");
        List result = new ArrayList();

        try {
            List<SubmoduleUserAuth> submoduleUserAuthList = this.getAuthorityByStructure(submoduleAuth, structure);
            for (Object obj : baseEntityList) {
                BaseEntity entity = (BaseEntity) obj;
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                    if (submoduleUserAuth.getLinkId().equals(entity.getId()) && submoduleUserAuth.getAuthority().equals(authority)) {
                        result.add(obj);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("Exception:getEntityByStructureAuthority = " + e);
        }

        return result;
    }

    public List getEntityByUserProfileAuthority(SubmoduleAuth submoduleAuth, UserProfile userProfile, String authority, List baseEntityList) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(baseEntityList, "baseEntity list must not be null");
        checkNotNull(authority, "authority must not be null");
        List result = new ArrayList();

        try {
            List<SubmoduleUserAuth> submoduleUserAuthList = this.getAuthorityByUserProfile(submoduleAuth, userProfile);
            for (Object obj : baseEntityList) {
                BaseEntity entity = (BaseEntity) obj;
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                    if (submoduleUserAuth.getLinkId().equals(entity.getId()) && submoduleUserAuth.getAuthority().equals(authority)) {
                        result.add(obj);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("Exception:getEntityByUserProfileAuthority = " + e);
        }

        return result;
    }

    public List getEntityFromTreeByStructureAuthority(SubmoduleAuth submoduleAuth, Structure structure, String authority, List baseTreeEntityList) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(baseTreeEntityList, "baseTreeEntity list must not be null");
        checkNotNull(authority, "authority must not be null");
        List result = new ArrayList();

        //List<SubmoduleUserAuth> submoduleUserAuthList = this.getAuthorityByStructure(submoduleAuth, structure);
        List<SubmoduleUserAuth> submoduleUserAuthList = new ArrayList();

        String separator = "฿";
        List authList = new ArrayList();
        try {
            //Find SubmoduleUserAuth List
            List<SubmoduleUserAuth> tmpList;
            for (Object obj : baseTreeEntityList) {
                BaseTreeEntity baseTreeEntity = (BaseTreeEntity) obj;
                tmpList = this.getAuthorityFromTreeByStructure(submoduleAuth, structure, baseTreeEntity);

                for (SubmoduleUserAuth submoduleUserAuth : tmpList) {
                    submoduleUserAuthList.add(submoduleUserAuth);
                }
            }

            //add equal to authority to List
            for (Object obj : baseTreeEntityList) {
                BaseTreeEntity baseTreeEntity = (BaseTreeEntity) obj;
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                    if (submoduleUserAuth.getAuthority().equals(authority)
                            && baseTreeEntity.getParentKey().contains(separator + submoduleUserAuth.getLinkId() + separator)) {

                        authList.add(obj);
                        break;
                    }
                }
            }

            if (authority.equals(disableAuthority)) {
                result = authList;
            } else {
                //remove not equal to authority from List
                boolean stillAuth;
                for (Object obj : authList) {
                    BaseTreeEntity baseTreeEntity = (BaseTreeEntity) obj;
                    stillAuth = true;
                    for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                        if (!submoduleUserAuth.getAuthority().equals(authority)
                                && baseTreeEntity.getParentKey().contains(separator + submoduleUserAuth.getLinkId() + separator)) {

                            stillAuth = false;
                            break;
                        }
                    }

                    if (stillAuth) {
                        result.add(obj);
                    }
                }
            }

        } catch (Exception e) {
            LOG.info("Exception:getEntityFromTreeByStructureAuthority = " + e);
        }

        return result;
    }

    public List getEntityFromTreeByUserProfileAuthority(SubmoduleAuth submoduleAuth, UserProfile userProfile, String authority, List baseTreeEntityList) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(baseTreeEntityList, "baseTreeEntity list must not be null");
        checkNotNull(authority, "authority must not be null");
        List result = new ArrayList();

        //List<SubmoduleUserAuth> submoduleUserAuthList = this.getAuthorityByUserProfile(submoduleAuth, userProfile);
        List<SubmoduleUserAuth> submoduleUserAuthList = new ArrayList();

        String separator = "฿";
        List authList = new ArrayList();
        try {
            //Find SubmoduleUserAuth List
            List<SubmoduleUserAuth> tmpList;
            for (Object obj : baseTreeEntityList) {
                BaseTreeEntity baseTreeEntity = (BaseTreeEntity) obj;
                tmpList = this.getAuthorityFromTreeByUserProfile(submoduleAuth, userProfile, baseTreeEntity);

                for (SubmoduleUserAuth submoduleUserAuth : tmpList) {
                    submoduleUserAuthList.add(submoduleUserAuth);
                }
            }

            //add equal to authority to List
            for (Object obj : baseTreeEntityList) {
                BaseTreeEntity baseTreeEntity = (BaseTreeEntity) obj;
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
//                    LOG.info("baseTreeEntity = "+baseTreeEntity.getParentKey()+" LinkId = "+submoduleUserAuth.getLinkId());
                    if (submoduleUserAuth.getAuthority().equals(authority)
                            && baseTreeEntity.getParentKey().contains(separator + submoduleUserAuth.getLinkId() + separator)) {

                        authList.add(obj);
                        break;
                    }
                }
            }

            if (authority.equals(disableAuthority)) {
                result = authList;
            } else {

                //remove not equal to authority from List
                boolean stillAuth;
                for (Object obj : authList) {
                    BaseTreeEntity baseTreeEntity = (BaseTreeEntity) obj;
                    stillAuth = true;
                    for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                        if (!submoduleUserAuth.getAuthority().equals(authority)
                                && baseTreeEntity.getParentKey().contains(separator + submoduleUserAuth.getLinkId() + separator)) {

                            stillAuth = false;
                            break;
                        }
                    }

                    if (stillAuth) {
                        result.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("Exception:getEntityFromTreeByUserProfileAuthority = " + e);
        }

        return result;
    }

    //--------------------------------------------------------------------------
    public List getEntityForSearchNotInParentKeyFromTreeByStructureAuthority(SubmoduleAuth submoduleAuth, Structure structure, List baseTreeEntityList) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(baseTreeEntityList, "baseTreeEntity list must not be null");
        List result = new ArrayList();

        try {
            //find List of first level
            List<BaseTreeEntity> firstLevelList = getFirstLevelFromTreeEntityList(baseTreeEntityList);
            List<BaseTreeEntity> entityList = new ArrayList();
            for (BaseTreeEntity baseTreeEntity : firstLevelList) {
                entityList = getEntityDetailForSearchNotInParentKeyFromTreeByStructureAuthority(baseTreeEntity, entityList, submoduleAuth, structure, baseTreeEntityList);
            }

            result = entityList;

        } catch (Exception e) {
            LOG.info("Exception:getEntityForSearchNotInParentKeyFromTreeByStructureAuthority = " + e);
        }

        return result;
    }

    private List getEntityDetailForSearchNotInParentKeyFromTreeByStructureAuthority(BaseTreeEntity entity, List resultList, SubmoduleAuth submoduleAuth, Structure structure, List baseTreeEntityList) {
        List result = resultList;
        try {
            boolean addEntity = false;
            //Get Entity Detail
            List<SubmoduleUserAuth> tmpList = getAuthorityFromTreeByStructure(submoduleAuth, structure, entity);
            for (SubmoduleUserAuth submoduleUserAuth : tmpList) {
                if (submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId()) && submoduleUserAuth.getAuthority().equals(disableAuthority)) {
                    result.add(entity);
                    addEntity = true;
                }
            }

            if (!addEntity) {
                //Get Child Detail
                for (Object obj : baseTreeEntityList) {
                    BaseTreeEntity baseTreeEntity = (BaseTreeEntity) obj;
                    if (entity.getId().equals(baseTreeEntity.getParentId())) {
                        result = getEntityDetailForSearchNotInParentKeyFromTreeByStructureAuthority(baseTreeEntity, result, submoduleAuth, structure, baseTreeEntityList);
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("Exception:getEntityDetailForSearchNotInParentKeyFromTreeByStructureAuthority = " + e);
        }

        return result;
    }

    public List getEntityForSearchNotInParentKeyFromTreeByUserProfileAuthority(SubmoduleAuth submoduleAuth, UserProfile userProfile, List baseTreeEntityList) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(baseTreeEntityList, "baseTreeEntity list must not be null");
        List result = new ArrayList();

        try {
            //find List of first level
            List<BaseTreeEntity> firstLevelList = getFirstLevelFromTreeEntityList(baseTreeEntityList);
            List<BaseTreeEntity> entityList = new ArrayList();
            for (BaseTreeEntity baseTreeEntity : firstLevelList) {
                entityList = getEntityDetailForSearchNotInParentKeyFromTreeByUserProfileAuthority(baseTreeEntity, entityList, submoduleAuth, userProfile, baseTreeEntityList);
            }

            result = entityList;

        } catch (Exception e) {
            LOG.info("Exception:getEntityForSearchNotInParentKeyFromTreeByStructureAuthority = " + e);
        }

        return result;
    }

    private List getEntityDetailForSearchNotInParentKeyFromTreeByUserProfileAuthority(BaseTreeEntity entity, List resultList, SubmoduleAuth submoduleAuth, UserProfile userProfile, List baseTreeEntityList) {
        List result = resultList;
        try {
            boolean addEntity = false;
            //Get Entity Detail
            List<SubmoduleUserAuth> tmpList = getAuthorityFromTreeByUserProfile(submoduleAuth, userProfile, entity);
            for (SubmoduleUserAuth submoduleUserAuth : tmpList) {
                if (submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId()) && submoduleUserAuth.getAuthority().equals(disableAuthority)) {
                    result.add(entity);
                    addEntity = true;
                }
            }

            if (!addEntity) {
                //Get Child Detail
                for (Object obj : baseTreeEntityList) {
                    BaseTreeEntity baseTreeEntity = (BaseTreeEntity) obj;
                    if (entity.getId().equals(baseTreeEntity.getParentId())) {
                        result = getEntityDetailForSearchNotInParentKeyFromTreeByUserProfileAuthority(baseTreeEntity, result, submoduleAuth, userProfile, baseTreeEntityList);
                    }
                }
            }

        } catch (Exception e) {
            LOG.info("Exception:getEntityDetailForSearchNotInParentKeyFromTreeByStructureAuthority = " + e);
        }

        return result;
    }

    //--------------------------------------------------------------------------
    public List<SubmoduleUserAuth> orderSubmoduleUserAuthBySubmoduleAuth(List<SubmoduleAuth> submoduleAuthList, List<SubmoduleUserAuth> submoduleUserAuthList) {
        List<SubmoduleUserAuth> result = new ArrayList();
        for (SubmoduleAuth submoduleAuth : submoduleAuthList) {
            for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                if (submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId())) {
                    result.add(submoduleUserAuth);
                }
            }
        }
        return result;
    }

    @Override
    public SubmoduleUserAuth getByIdNotRemoved(int id) {
        checkNotNull(id, "SubmoduleUserAuth id entity must not be null");
        return submoduleUserAuthDaoImpl.getByIdNotRemoved(id);
    }

    public List<BaseTreeEntity> getFirstLevelFromTreeEntityList(List baseTreeEntityList) {
        checkNotNull(baseTreeEntityList, "baseTreeEntity list must not be null");
        List result = new ArrayList();

        try {
            List<BaseTreeEntity> tmpList;
            List<BaseTreeEntity> firstList = new ArrayList();
            for (Object obj : baseTreeEntityList) {
                BaseTreeEntity baseTreeEntity = (BaseTreeEntity) obj;
                tmpList = TreeUtil.getListFromParentKey(baseTreeEntity);
                if (tmpList.size() > 0) {
                    boolean notIntList = true;
                    for (BaseTreeEntity tmp : firstList) {
                        if (tmp.getId().equals(tmpList.get(0).getId())) {
                            notIntList = false;
                            break;
                        }
                    }

                    if (notIntList) {
                        firstList.add(tmpList.get(0));
                    }
                }
            }

            result = firstList;

        } catch (Exception e) {
            LOG.info("Exception:getFirstLevelFromTreeEntityList = " + e);
        }

        return result;
    }

    public List<Auth> listAuthByDmsFolder(Integer dmsFolderId) {
        checkNotNull(dmsFolderId, "dmsFolder id entity must not be null");
        List<Auth> listAuth = new ArrayList<Auth>();
        List<SubmoduleUserAuth> listSubmoduleUserAuth = submoduleUserAuthDaoImpl.listByDmsFolder(dmsFolderId);
        if (!listSubmoduleUserAuth.isEmpty()) {
            int authInt = 0;
            for (SubmoduleUserAuth subModuleUserAuth : listSubmoduleUserAuth) {
                if (subModuleUserAuth.getSubmoduleAuth().getAuth().getId() != authInt) {
                    authInt = subModuleUserAuth.getSubmoduleAuth().getAuth().getId();
                    listAuth.add(subModuleUserAuth.getSubmoduleAuth().getAuth());
                }
            }
        }
        return listAuth;
    }

    public List<SubmoduleUserAuth> listByDmsFolders(Integer dmsFolderId) {
        checkNotNull(dmsFolderId, "dmsFolder id entity must not be null");
//        List<Auth> listAuth = new ArrayList<Auth>();
//        listAuth = this.listAuthByDmsFolder(dmsFolderId);
        List<SubmoduleUserAuth> listSubmoduleUserAuth = new ArrayList<SubmoduleUserAuth>();
//        if(!listAuth.isEmpty()){
//            for(Auth auth : listAuth){
//                 listSubmoduleUserAuth = submoduleUserAuthDaoImpl.listByDmsFolders(dmsFolderId,auth.getId());
        listSubmoduleUserAuth = submoduleUserAuthDaoImpl.listByDmsFolders(dmsFolderId);
//            }        
//        }
        return listSubmoduleUserAuth;
    }
    
    public List<SubmoduleUserAuth> listUserByDmsFoldersAuth(Integer dmsFolderId,int auth) {
        checkNotNull(dmsFolderId, "dmsFolder id entity must not be null");
        List<SubmoduleUserAuth> listSubmoduleUserAuth = new ArrayList<SubmoduleUserAuth>();
        listSubmoduleUserAuth = submoduleUserAuthDaoImpl.listUserByDmsFoldersAuth(dmsFolderId,auth);
        return listSubmoduleUserAuth;
    }
    
    public AuthEnableDisableIdListModel getEnableDisableIdListByUserProfile(SubmoduleAuth submoduleAuth, UserProfile userProfile) {
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(userProfile, "userProfile entity must not be null");

        AuthEnableDisableIdListModel result = new AuthEnableDisableIdListModel();
        try {
            String enableList = "";
            String disableList = "";

            String checkSeparator = "฿";
            String checkList = checkSeparator;

            StructureService structureService = new StructureService();

            //Get List From UserProfile
            List<SubmoduleUserAuth> submoduleUserAuthList = submoduleUserAuthDaoImpl.listBySubmoduleAuthUserProfile(submoduleAuth, userProfile, "orderNo", "desc");
            for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                if (!checkList.contains(checkSeparator + submoduleUserAuth.getLinkId() + checkSeparator)) {
                    if (submoduleUserAuth.getAuthority().equals(enableAuthority)) {
                        enableList += "," + submoduleUserAuth.getLinkId();
                    } else {
                        disableList += "," + submoduleUserAuth.getLinkId();
                    }

                    checkList += submoduleUserAuth.getLinkId() + checkSeparator;
                }
            }

            //Get List From Structure
            if (userProfile.getStructure() != null) {
                List<Structure> structureList = structureService.listFromParentKey(userProfile.getStructure());
                submoduleUserAuthList = submoduleUserAuthDaoImpl.listBySubmoduleAuthStructure(submoduleAuth, structureList, "orderNo", "desc");
                for (SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
                    if (!checkList.contains(checkSeparator + submoduleUserAuth.getLinkId() + checkSeparator)) {
                        if (submoduleUserAuth.getAuthority().equals(enableAuthority)) {
                            enableList += "," + submoduleUserAuth.getLinkId();
                        } else {
                            disableList += "," + submoduleUserAuth.getLinkId();
                        }

                        checkList += submoduleUserAuth.getLinkId() + checkSeparator;
                    }
                }
            }

            if (!enableList.isEmpty()) {
                enableList = enableList.substring(1);
            }
            if (!disableList.isEmpty()) {
                disableList = disableList.substring(1);
            }

            result.setEnableList(enableList);
            result.setDisableList(disableList);

        } catch (Exception e) {
            LOG.info("Exception:getEnableDisableIdListByUserProfile = " + e);
        }

        return result;
    }
    
    public List<SubmoduleUserAuth> listTemplateValueByLinkId(int linkId) {
        return submoduleUserAuthDaoImpl.listTemplateValueByLinkId(linkId);
    }

}
