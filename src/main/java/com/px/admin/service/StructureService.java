package com.px.admin.service;

import com.px.share.service.LogDataService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.StructureDaoImpl;
import com.px.admin.daoimpl.VStructureDaoImpl;
import com.px.share.entity.LogData;
import com.px.admin.entity.Structure;
import com.px.admin.entity.VStructure;
import com.px.admin.model.StructureConvertModel;
import com.px.admin.model.StructureModel;
import com.px.admin.model.VStructureModel;
import com.px.share.entity.TempTable;
import com.px.share.service.FileAttachService;
import com.px.share.service.GenericTreeService;
import com.px.share.util.Common;
import com.px.share.util.TreeUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author OPAS
 */
public class StructureService implements GenericTreeService<Structure, StructureModel> {

    private static final Logger LOG = Logger.getLogger(StructureService.class.getName());
    private final StructureDaoImpl structureDaoImpl;

    public StructureService() {
        this.structureDaoImpl = new StructureDaoImpl();
    }

    @Override
    public String generateParentKey(Structure structure) {
        String result = "";
        try {
            result = TreeUtil.generateParentKey(structure);
        } catch (Exception e) {
            LOG.error("Exception : " + e);
        }

        return result;
    }

    @Override
    public Structure getParent(Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(structure.getParentId(), "structure parent id must not be null");
        return structureDaoImpl.findParent(structure.getParentId());
    }

    @Override
    public List<Structure> listChild(Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(structure.getId(), "structure id must not be null");
        return structureDaoImpl.findChild(structure.getId());
    }

    public List<Structure> listChild(Structure structure, String sort, String dir) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(structure.getId(), "structure id must not be null");
        return structureDaoImpl.findChild(structure.getId(), sort, dir);
    }

    @Override
    public List<Structure> listFromParentKey(Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(structure.getParentId(), "structure parent id must not be null");
        ArrayList<Structure> result = new ArrayList();
        try {
            List parentList = TreeUtil.getListFromParentKey(structure);
            for (Object tmp : parentList) {
                result.add((Structure) tmp);
            }
        } catch (Exception e) {
            LOG.error("Exception : " + e);
        }

        return result;
    }

    @Override
    public Structure create(Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(structure.getStructureName(), "structure name must not be null");
        checkNotNull(structure.getCreatedBy(), "create by must not be null");
        structure = structureDaoImpl.create(structure);
        structure.setParentKey(generateParentKey(structure));
        structure.setNodeLevel(TreeUtil.generateNodeLevel(structure));
        if (structure.getOrderNo() == 0) {
            structure.setOrderNo(structure.getId());
        }
        structure = update(structure);
        return structure;
    }

    @Override
    public Structure getById(int id) {
        checkNotNull(id, "structure id entity must not be null");
        return structureDaoImpl.getById(id);
    }

    @Override
    public Structure update(Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(structure.getStructureName(), "structure name must not be null");
        checkNotNull(structure.getUpdatedBy(), "update by must not be null");
        structure.setUpdatedDate(LocalDateTime.now());
        return structureDaoImpl.update(structure);
    }

    //ย้ายโครงสร้าง Update ParentId กับ ParentKey
    public Structure updateByParentId(Structure structure) {
        checkNotNull(structure, "structure entity must not be null");
        checkNotNull(structure.getUpdatedBy(), "update by must not be null");
        structure.setParentKey(generateParentKey(structure));
        structure.setNodeLevel(TreeUtil.generateNodeLevel(structure));
        structure.setUpdatedDate(LocalDateTime.now());
        return structureDaoImpl.update(structure);
    }

    @Override
    public Structure remove(int id, int userId) {
        checkNotNull(id, "structure id must not be null");
        Structure structure = getById(id);
        checkNotNull(structure, "structure entity not found in database.");
        structure.setRemovedBy(userId);
        structure.setRemovedDate(LocalDateTime.now());
        return structureDaoImpl.update(structure);
    }

    @Override
    public List<Structure> listAll(String sort, String dir) {
        return structureDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<Structure> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return structureDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public StructureModel tranformToModel(Structure structure) {
        StructureModel structureModel = null;
        if (structure != null) {
            structureModel = new StructureModel();
            structureModel.setId(structure.getId());
            structureModel.setDetail(structure.getStructureDetail());
            structureModel.setName(structure.getStructureName());
            structureModel.setShortName(structure.getStructureShortName());
            structureModel.setNodeLevel(structure.getNodeLevel());
            structureModel.setParentId(structure.getParentId());
            structureModel.setParentKey(structure.getParentKey());
            structureModel.setCode(structure.getStructureCode());
        }
        return structureModel;
    }

    public StructureConvertModel tranformToConvertModel(int stat, StructureModel structure, VStructureModel vStructure) {
        StructureConvertModel structureConvertModel = null;

        if (stat != 0) {
            structureConvertModel = new StructureConvertModel();
            structureConvertModel.setStatus(stat);
            structureConvertModel.setStructure(structure);
            structureConvertModel.setVStructure(vStructure);
        }
        return structureConvertModel;
    }

    @Override
    public int countAll() {
        return structureDaoImpl.countAll();
    }

    @Override
    public List<Structure> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        return structureDaoImpl.search(queryParams, offset, limit, sort, dir);
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryStructures) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Structure getByIdNotRemoved(int id) {
        checkNotNull(id, "Structure id entity must not be null");
        return structureDaoImpl.getByIdNotRemoved(id);
    }

    public List<Structure> leaveReportStatStructure(int userId, String sort, String dir) {
        checkNotNull(userId, "userId must not be null");
        return structureDaoImpl.leaveReportStatStructure(userId, sort, dir);
    }

    public List<Structure> searchByNameOrCode(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        return structureDaoImpl.searchByNameOrCode(queryParams, offset, limit, sort, dir);
    }

    public double findOrderNo(int id) {
        checkNotNull(id, "userProfile id must not be null");
        Structure struc2 = this.getByIdNotRemoved(id);
        Structure struc = structureDaoImpl.getPrevOrderBy(id);
        double orderNoNum = 0;
        if (struc != null && struc.getOrderNo() == (struc2.getOrderNo() - 1)) {
            orderNoNum = (struc.getOrderNo() + struc2.getOrderNo()) / 2;
        } else {
            orderNoNum = struc2.getOrderNo() - 1;
        }
        return orderNoNum;
    }

    public Integer findParentId(String code) {
        Structure structure = new Structure();
        String codeD = "";
        Integer parId = null;
        if (code != null && !"".equalsIgnoreCase(code)) {
//            String sub = code.substring(0,2);
            if (code.substring(2).equalsIgnoreCase("0000")) {
                parId = 1;
            } else if (!code.substring(3, 4).equalsIgnoreCase("0") && code.substring(4).equalsIgnoreCase("00")) {
                codeD = code.substring(0, 2).concat("0000");
                structure = this.getByCode(codeD);
                if (structure != null) {
                    parId = structure.getId();
                }
            } else if (!code.substring(4).equalsIgnoreCase("00")) {
                codeD = code.substring(0, 4).concat("00");
                structure = this.getByCode(codeD);
                if (structure != null) {
                    parId = structure.getId();
                }
//            }else{
//                parId = null;
            }
        }
        return parId;
    }

    public Structure saveLogForCreate(Structure structure, String clientIp) {
        //For Create Log when create Structure
        String logDescription = this.generateLogForCreateEntity(structure);
        LogData logData = new LogData();
        logData.setCreatedBy(structure.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(structure.getClass().getName());
        logData.setLinkId(structure.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_ADMIN);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return structure;
    }

    public Structure saveLogForUpdate(Structure structureOld, Structure structureNew, String clientIp) {
        //For Create Log when update Structure
        String logDescription = this.generateLogForUpdateEntity(structureOld, structureNew);
        LogData logData = new LogData();
        logData.setCreatedBy(structureNew.getUpdatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(structureNew.getClass().getName());
        logData.setLinkId(structureNew.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return structureNew;
    }

    public Structure saveLogForRemove(Structure structure, String clientIp) {
        //For Create Log when remove Structure
        String logDescription = this.generateLogForRemoveEntity(structure);
        LogData logData = new LogData();
        logData.setCreatedBy(structure.getRemovedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(structure.getClass().getName());
        logData.setLinkId(structure.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return structure;
    }

    private String generateLogForCreateEntity(Structure structure) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ชื่อโครงสร้าง : ");
        descriptionLog.append(Common.noNull(structure.getStructureName(), ""));
        return descriptionLog.toString();
    }

    private String generateLogForUpdateEntity(Structure structureOld, Structure structureNew) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ชื่อโครงสร้าง : ");
        descriptionLog.append(structureOld.getStructureName());
        descriptionLog.append("เป็น");
        descriptionLog.append(Common.noNull(structureNew.getStructureName(), ""));

        return descriptionLog.toString();
    }

    private String generateLogForRemoveEntity(Structure structure) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ชื่อโครงสร้าง : ");
        descriptionLog.append(structure.getStructureName());
        return descriptionLog.toString();
    }

    public List<Structure> listStructureByType(int type, String sort, String dir) {
        checkNotNull(type, "type must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return structureDaoImpl.listStructureByType(type, sort, dir);
    }

    public Structure getByCode(String code) {
        checkNotNull(code, "vStructure id entity must not be null");
        return structureDaoImpl.getByCode(code);
    }

    public boolean checkDup(String code, String name) {
        boolean result = false;
        int countOfStucture = structureDaoImpl.countDup(code, name);
        if (countOfStucture != 0) {
            result = true;
        }
        return result;
    }

    public int readFileStrucExcel(int fileId, String clientIp) {
        int result = 0;
        BufferedReader br = null;
        try {
            FileAttachService fileAttachService = new FileAttachService();
            String dataPathFile = fileAttachService.moveToTempPath(fileId);
            FileInputStream targetFile = new FileInputStream(new File(dataPathFile));
            LOG.info("dataPathFile = " + dataPathFile);
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(targetFile);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int countRow = 0;

            VStructureDaoImpl vStructure1DaoImpl = new VStructureDaoImpl();
            VStructureService vStructureService = new VStructureService();
            List<VStructure> listVStructure = vStructureService.listAllNotRemove("id", "asc");
            if (!listVStructure.isEmpty()) {
                for (VStructure vStructure1 : listVStructure) {
                    vStructure1DaoImpl.delete(vStructure1);
                }
            }

            while (rowIterator.hasNext()) {
                countRow++;
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                LOG.info("----------------------------------------------");
                int countCell = 0;
//                String codeChk = codeChk;//รับจาก interface
                String code = "";
                String shortName = "";
                String name = "";
                String detail = "";

                if (countRow > 1) {
                    for (int i = 0; i < 4; i++) {        //51 = last cell
                        countCell++;
                        Cell cell = row.getCell(countCell - 1, Row.CREATE_NULL_AS_BLANK);
                        //Check the cell type and format accordingly
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC: // numeric value in Excel  
                                LOG.info(countRow + " " + countCell + "N)" + cell.getNumericCellValue());
                                if (countCell == 1) {
                                    code = String.valueOf((int) cell.getNumericCellValue());
                                    LOG.info(code);
                                }
                                break;
                            case Cell.CELL_TYPE_FORMULA: // precomputed value based on formula
                                LOG.info(countRow + " " + countCell + "F)" + cell.getCellFormula());
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                LOG.info(countRow + " " + countCell + "S)" + "");
                            case Cell.CELL_TYPE_BOOLEAN: //boolean value 
                                LOG.info(countRow + " " + countCell + "B)" + cell.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_STRING: // String Value in Excel 
                                LOG.info(countRow + " " + countCell + "T)" + cell.getStringCellValue());
                                if (countCell == 2) {
                                    shortName = cell.getStringCellValue().trim();
                                }
                                if (countCell == 3) {
                                    name = cell.getStringCellValue().trim();
                                }
                                if (countCell == 4) {
                                    detail = cell.getStringCellValue().trim();
                                }
                                break;
                            case Cell.CELL_TYPE_ERROR:
                            default:
                                throw new RuntimeException("There is no support for this type of cell");
//                        break;
                        }
                    }//while (cellIterator.hasNext()) {  //// OR ///// for(int i=0; i<20; i++){  
                    VStructure vStructure = new VStructure();

                    vStructure.setCreatedBy(2);//test
                    vStructure.setStructureCode(code);
                    vStructure.setStructureName(name);
                    vStructure.setStructureShortName(shortName);
                    vStructure.setStructureDetail(detail);
                    try {
//                        vStructure1 = vStructureService.getById(vStructure.getId());
//                        if (vStructure != null) {
//                            if (vStructure1 != null && vStructure.getId() == vStructure1.getId()) {
//                                vStructure.setOrderNo(vStructure.getId());
//                                vStructure = vStructureService.update(vStructure);
//                            } else {
//                                vStructure = vStructureService.create(vStructure);
//                            }
//                        }
//                        } else {
                        vStructure = vStructureService.create(vStructure);
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOG.error("Exception create = " + e.getMessage());
                    }

                    if (vStructure != null) {
                        vStructure.setOrderNo(vStructure.getId());
                        vStructure = vStructureService.update(vStructure);
                    }
//                    vStructureService.saveLogForCreate(insureTax, clientIp);
                    result++;
                }
            }
            targetFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Exception = " + e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                LOG.error("Exception Close BufferedReader = " + ex.getMessage());
            }
        }
        return result;
    }

    public List<Structure> listByName(String structureName) {
        return structureDaoImpl.listByName(structureName);
    }

}
