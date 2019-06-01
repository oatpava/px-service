/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.share.service;

import com.px.share.entity.FileAttach;
import com.px.share.model.FileAttachModel;
import com.px.share.model.FileAttachModel2;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author top
 */
public class FileAttachServiceTest {
    
    
    
    @Ignore
    @BeforeClass
    public static void setUpClass() {
    }
    @Ignore
    @AfterClass
    public static void tearDownClass() {
    }
    @Ignore
    @Before
    public void setUp() {
    }
    @Ignore
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class FileAttachService.
     */
    @Ignore
    @Test
    public void testCreate() {
        System.out.println("create");
        FileAttach fileAttach = null;
        FileAttachService instance = new FileAttachService();
        FileAttach expResult = null;
        FileAttach result = instance.create(fileAttach);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getById method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testGetById() {
        System.out.println("getById");
        int id = 0;
        FileAttachService instance = new FileAttachService();
        FileAttach expResult = null;
        FileAttach result = instance.getById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getByReferenceId method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testGetByReferenceId() {
        System.out.println("getByReferenceId");
        int referenceId = 0;
        FileAttachService instance = new FileAttachService();
        FileAttach expResult = null;
        FileAttach result = instance.getByReferenceId(referenceId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testUpdate() {
        System.out.println("update");
        FileAttach fileAttach = null;
        FileAttachService instance = new FileAttachService();
        FileAttach expResult = null;
        FileAttach result = instance.update(fileAttach);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testRemove() {
        System.out.println("remove");
        int id = 0;
        int userId = 0;
        FileAttachService instance = new FileAttachService();
        FileAttach expResult = null;
        FileAttach result = instance.remove(id, userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testDelete() {
        System.out.println("delete");
        FileAttach fileAttach = null;
        FileAttachService instance = new FileAttachService();
        instance.delete(fileAttach);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAll method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testListAll() {
        System.out.println("listAll");
        String sort = "";
        String dir = "";
        FileAttachService instance = new FileAttachService();
        List<FileAttach> expResult = null;
        List<FileAttach> result = instance.listAll(sort, dir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of list method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testList() {
        System.out.println("list");
        int offset = 0;
        int limit = 0;
        String sort = "";
        String dir = "";
        FileAttachService instance = new FileAttachService();
        List<FileAttach> expResult = null;
        List<FileAttach> result = instance.list(offset, limit, sort, dir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tranformToModel method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testTranformToModel() {
        System.out.println("tranformToModel");
        FileAttach fileAttach = null;
        FileAttachService instance = new FileAttachService();
        FileAttachModel expResult = null;
        FileAttachModel result = instance.tranformToModel(fileAttach);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tranformToModel2 method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testTranformToModel2() {
        System.out.println("tranformToModel2");
        FileAttach fileAttach = null;
        FileAttachService instance = new FileAttachService();
        FileAttachModel2 expResult = null;
        FileAttachModel2 result = instance.tranformToModel2(fileAttach);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countAll method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testCountAll() {
        System.out.println("countAll");
        FileAttachService instance = new FileAttachService();
        int expResult = 0;
        int result = instance.countAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllByLinkTypeLinkId method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testListAllByLinkTypeLinkId() {
        System.out.println("listAllByLinkTypeLinkId");
        String linkType = "";
        int linkId = 0;
        String sort = "";
        String dir = "";
        FileAttachService instance = new FileAttachService();
        List<FileAttach> expResult = null;
        List<FileAttach> result = instance.listAllByLinkTypeLinkId(linkType, linkId, sort, dir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllAfterAdd method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testListAllAfterAdd() {
        System.out.println("listAllAfterAdd");
        String linkType = "";
        int linkId = 0;
        String sort = "";
        String dir = "";
        FileAttachService instance = new FileAttachService();
        List<FileAttach> expResult = null;
        List<FileAttach> result = instance.listAllAfterAdd(linkType, linkId, sort, dir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listByLinkTypeLinkId method, of class FileAttachService.
     */ @Ignore
    @Test
    public void testListByLinkTypeLinkId() {
        System.out.println("listByLinkTypeLinkId");
        String linkType = "";
        int linkId = 0;
        int offset = 0;
        int limit = 0;
        String sort = "";
        String dir = "";
        FileAttachService instance = new FileAttachService();
        List<FileAttach> expResult = null;
        List<FileAttach> result = instance.listByLinkTypeLinkId(linkType, linkId, offset, limit, sort, dir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveFile method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testSaveFile() throws Exception {
        System.out.println("saveFile");
        InputStream uploadedInputStream = null;
        String serverPathSaveFile = "";
        FileAttachService instance = new FileAttachService();
        instance.saveFile(uploadedInputStream, serverPathSaveFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveFileFromBase64 method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testSaveFileFromBase64() throws Exception {
        System.out.println("saveFileFromBase64");
        String base64data = "";
        String serverPathSaveFile = "";
        FileAttachService instance = new FileAttachService();
        instance.saveFileFromBase64(base64data, serverPathSaveFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildFilePathExt method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testBuildFilePathExt() {
        System.out.println("buildFilePathExt");
        int id = 0;
        FileAttachService instance = new FileAttachService();
        String expResult = "";
        String result = instance.buildFilePathExt(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildHtmlPathExt method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testBuildHtmlPathExt() {
        System.out.println("buildHtmlPathExt");
        int id = 0;
        FileAttachService instance = new FileAttachService();
        String expResult = "";
        String result = instance.buildHtmlPathExt(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildHtmlPathExtNoName method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testBuildHtmlPathExtNoName() {
        System.out.println("buildHtmlPathExtNoName");
        int id = 0;
        FileAttachService instance = new FileAttachService();
        String expResult = "";
        String result = instance.buildHtmlPathExtNoName(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildHtmlPathExtNoName2 method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testBuildHtmlPathExtNoName2() {
        System.out.println("buildHtmlPathExtNoName2");
        int id = 0;
        FileAttachService instance = new FileAttachService();
        String expResult = "";
        String result = instance.buildHtmlPathExtNoName2(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildThumbhunailUrl method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testBuildThumbhunailUrl() {
        System.out.println("buildThumbhunailUrl");
        String fileAttachType = "";
        String fileAttachUrl = "";
        FileAttachService instance = new FileAttachService();
        String expResult = "";
        String result = instance.buildThumbhunailUrl(fileAttachType, fileAttachUrl);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of search method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testSearch() {
        System.out.println("search");
        MultivaluedMap<String, String> queryFileAttachs = null;
        int offset = 0;
        int limit = 0;
        String sort = "";
        String dir = "";
        FileAttachService instance = new FileAttachService();
        List<FileAttach> expResult = null;
        List<FileAttach> result = instance.search(queryFileAttachs, offset, limit, sort, dir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countSearch method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testCountSearch() {
        System.out.println("countSearch");
        MultivaluedMap<String, String> queryFileAttachs = null;
        FileAttachService instance = new FileAttachService();
        int expResult = 0;
        int result = instance.countSearch(queryFileAttachs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveToRealPath method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testMoveToRealPath() {
        System.out.println("moveToRealPath");
        int fileAttachId = 0;
        FileAttachService instance = new FileAttachService();
        String expResult = "";
        String result = instance.moveToRealPath(fileAttachId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveToTempPath method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testMoveToTempPath() {
        System.out.println("moveToTempPath");
        int fileAttachId = 0;
        FileAttachService instance = new FileAttachService();
        String expResult = "";
        String result = instance.moveToTempPath(fileAttachId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRealPathFile method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testGetRealPathFile() {
        System.out.println("getRealPathFile");
        int fileAttachId = 0;
        FileAttachService instance = new FileAttachService();
        String expResult = "";
        String result = instance.getRealPathFile(fileAttachId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getByIdNotRemoved method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testGetByIdNotRemoved() {
        System.out.println("getByIdNotRemoved");
        int id = 0;
        FileAttachService instance = new FileAttachService();
        FileAttach expResult = null;
        FileAttach result = instance.getByIdNotRemoved(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildWaterMark method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testBuildWaterMark() {
        System.out.println("buildWaterMark");
        String pathSourceFile = "";
        String pathDestFile = "";
        String pathWaterMarkFile = "";
        FileAttachService instance = new FileAttachService();
        String expResult = "";
        String result = instance.buildWaterMark(pathSourceFile, pathDestFile, pathWaterMarkFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of encodeImage method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testEncodeImage() {
        System.out.println("encodeImage");
        byte[] imageByteArray = null;
        String expResult = "";
        String result = FileAttachService.encodeImage(imageByteArray);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decodeImage method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testDecodeImage() {
        System.out.println("decodeImage");
        String imageDataString = "";
        byte[] expResult = null;
        byte[] result = FileAttachService.decodeImage(imageDataString);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countAllByLinkTypeLinkId method, of class FileAttachService.
     */
     @Ignore
    @Test
    public void testCountAllByLinkTypeLinkId() {
        System.out.println("countAllByLinkTypeLinkId");
        String linkType = "";
        int linkId = 0;
        FileAttachService instance = new FileAttachService();
        int expResult = 0;
        int result = instance.countAllByLinkTypeLinkId(linkType, linkId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Ignore
    @Test
    public boolean checkHaveFile(){
        
        int attachId = 2;
        
        FileAttachService fileAttachService = new FileAttachService();
        ParamService paramService = new ParamService();
        FileAttach fileAttach = fileAttachService.getById(attachId);
        String pathDocument = paramService.getByParamName("PATH_DOCUMENT").getParamValue();
        String filePath = fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(attachId) + fileAttach.getFileAttachType();
         String pathFile = pathDocument + filePath;
         System.out.println("pathFile =  "+pathFile);
         
        
    return true;
    }
    
}
