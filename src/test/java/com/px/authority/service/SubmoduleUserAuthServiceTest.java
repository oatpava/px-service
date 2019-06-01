package com.px.authority.service;

import com.px.admin.entity.UserProfile;
import com.px.admin.service.UserProfileService;
import com.px.share.util.HibernateUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Peach
 */
public class SubmoduleUserAuthServiceTest {

    public SubmoduleUserAuthServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        HibernateUtil.shutdown();
    }
    
    @Ignore
    @Test
    public void testSetup() {
        System.out.println("start...");
        UserProfileService userProfileService = new UserProfileService();
        UserProfile userProfile = userProfileService.getById(1);
        System.out.println("userProfile = "+userProfile.getUserProfileFullName());
        System.out.println("end...");
    }
}
