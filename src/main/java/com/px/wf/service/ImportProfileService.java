package com.px.wf.service;

import com.px.admin.daoimpl.UserProfileDaoImpl;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.StructureService;
import com.px.admin.service.TitleService;
import com.px.admin.service.UserService;
import com.px.wf.model.ImportProfileModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Oat
 */
public class ImportProfileService {

    private final UserProfileDaoImpl userProfileDaoImpl;
    private final List<UserProfile> listUserProfile;

    public ImportProfileService() {
        this.userProfileDaoImpl = new UserProfileDaoImpl();
        this.listUserProfile = new ArrayList<>();
    }

    public ImportProfileModel createProfile(ImportProfileModel importProfileModel) {
        String key;
        while (true) {
            key = genRandomKey(15);

            UserProfile tmp = userProfileDaoImpl.getByCodeGetRemoved(key);
            if (tmp == null) {
                break;
            }
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setCreatedBy(importProfileModel.getCreatedBy());
        userProfile.setStructure(new StructureService().getById(-1));
        userProfile.setTitle(new TitleService().getById(1));
        userProfile.setUser(new UserService().getById(-1));
        userProfile.setUserProfileFullName(importProfileModel.getName());
        userProfile.setUserProfileFullNameEng("THIS_IS_IMPORT_PROFILE");
        userProfile.setUserProfileVersion(-1);
        userProfile.setUserProfileCode(key);
        userProfile = userProfileDaoImpl.create(userProfile);

        return new ImportProfileModel(userProfile, importProfileModel.getCreatorFullName(), null);
    }

    private String genRandomKey(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String key = "";
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) Math.floor(Math.random() * chars.length());
            key += chars.charAt(randomIndex);
        }

        return key;
    }

    public ImportProfileModel updateProfile(ImportProfileModel importProfileModel) {
        UserProfile userProfile = userProfileDaoImpl.getByIdNotRemoved(importProfileModel.getId());
        if (userProfile == null) {
            return null;
        }

        userProfile.setUpdatedBy(importProfileModel.getUpdatedBy());
        userProfile.setUpdatedDate(LocalDateTime.now());
        userProfile.setUserProfileFullName(importProfileModel.getName());
        userProfile = userProfileDaoImpl.update(userProfile);

        return new ImportProfileModel(userProfile, importProfileModel.getCreatorFullName(), importProfileModel.getUpdaterFullName());
    }

    public List<ImportProfileModel> listProfile(String sort, String dir) {
        List<ImportProfileModel> listImportProfileModel = new ArrayList<>();

        List<UserProfile> listUserProfile = userProfileDaoImpl.listByUserId(-1, sort, dir);
        for (UserProfile userProfile : listUserProfile) {
            ImportProfileModel importProfileModel = new ImportProfileModel(userProfile, null, null);

            UserProfile creator = findUserProfile(userProfile.getCreatedBy());
            importProfileModel.setCreatorFullName(creator != null ? creator.getUserProfileFullName() : "-");

            if (userProfile.getUpdatedBy() != null && userProfile.getUpdatedBy() != 0) {
                UserProfile updater = findUserProfile(userProfile.getUpdatedBy());
                importProfileModel.setUpdaterFullName(updater != null ? updater.getUserProfileFullName() : "-");
            }

            listImportProfileModel.add(importProfileModel);
        }

        return listImportProfileModel;
    }

    private UserProfile findUserProfile(int id) {
        for (UserProfile userProfile : listUserProfile) {
            if (Objects.equals(userProfile.getId(), id)) {
                listUserProfile.add(userProfile);
                return userProfile;
            }
        }

        UserProfile userProfile = userProfileDaoImpl.getById(id);
        if (userProfile != null) {
            listUserProfile.add(userProfile);
            return userProfile;
        }

        return null;
    }

    public ImportProfileModel removeProfile(int id, int removedBy) {
        UserProfile userProfile = userProfileDaoImpl.getByIdNotRemoved(id);
        if (userProfile == null) {
            return null;
        }

        userProfile.setRemovedBy(removedBy);
        userProfile.setRemovedDate(LocalDateTime.now());
        userProfile = userProfileDaoImpl.update(userProfile);

        return new ImportProfileModel(userProfile, "", "");
    }

}
