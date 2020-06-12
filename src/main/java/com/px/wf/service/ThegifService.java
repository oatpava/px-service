//package com.px.wf.service;
//
//import static com.google.common.base.Preconditions.checkNotNull;
//import com.px.wf.daoimpl.ThegifDaoImpl;
//import com.px.share.entity.FileAttach;
//import com.px.share.entity.Param;
//import com.px.wf.entity.Thegif;
//import com.px.wf.entity.ThegifDepartment;
//import com.px.wf.entity.ThegifDocFile;
//import com.px.wf.entity.WfContent;
//import com.px.share.model.FileAttachModel;
//import com.px.share.service.GenericService;
//import com.px.wf.model.get.ThegifModel;
//import com.px.wf.model.get.ThegifModel_ECMSMimeCode;
//import com.px.wf.model.get.ThegifModel_ECMSMinistry;
//import com.px.wf.model.get.ThegifModel_ECMSOfficer;
//import com.px.wf.model.get.ThegifModel_ECMSOrganization;
//import com.px.wf.model.get.ThegifModel_ECMSResult;
//import com.px.wf.model.get.ThegifModel_ECMSSecret;
//import com.px.wf.model.get.ThegifModel_ECMSSpeed;
//import com.px.wf.model.get.ThegifModel_ECMSStatus;
//import com.px.wf.model.get.ThegifModel_ECMSTime;
//import com.px.wf.model.get.ThegifModel_groupShowList;
//import com.px.wf.model.get.ThegifModel_groupShowList2;
//import com.px.admin.service.SubmoduleService;
//import com.px.share.service.FileAttachService;
//import com.px.share.util.Common;
//import com.px.share.service.ParamService;
//import com.px.dms.entity.DmsDocument;
//import com.px.dms.service.DmsDocumentService;
////import com.px.documentfile.entity.DocumentFile;
////import com.px.documentfile.service.DocumentFileService;
//import java.io.File;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import javax.ws.rs.core.MultivaluedMap;
//import com.px.wf.ecms.Attachment;
//import com.px.wf.ecms.Letter;
//import com.px.wf.ecms.MimeCode;
//import com.px.wf.ecms.Ministry;
//import com.px.wf.ecms.Organization;
//import com.px.wf.ecms.Reference;
//import com.px.wf.ecms.Secret;
//import com.px.wf.ecms.Speed;
//import com.px.wf.ecms.WsecmsService;
//import com.px.wf.ecms.WsecmsService_Service;
//import com.px.wf.model.post.ThegifStatusSendModel;
//
///**
// *
// * @author 
// */
//
//public class ThegifService implements GenericService<Thegif, ThegifModel> {
//
//    private final ThegifDaoImpl thegifDaoImpl;
//
//    public ThegifService() {
//        this.thegifDaoImpl = new ThegifDaoImpl();
//    }
//
//    @Override
//    public Thegif create(Thegif thegif) {
//        checkNotNull(thegif, "thegif entity must not be null");
//        checkNotNull(thegif.getCreatedBy(), "thegif by must not be null");
//        return thegifDaoImpl.create(thegif);
//    }
//
//    @Override
//    public Thegif getById(int id) {
//        return thegifDaoImpl.getById(id);
//    }
//
//    @Override
//    public Thegif getByIdNotRemoved(int id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Thegif update(Thegif thegif) {
//        checkNotNull(thegif, "thegif entity must not be null");
//        checkNotNull(thegif.getUpdatedBy(), "update by must not be null");
//        thegif.setUpdatedDate(LocalDateTime.now());
//        return thegifDaoImpl.update(thegif);
//    }
//
//    @Override
//    public Thegif remove(int id, int userId) {
//        checkNotNull(id, "thegif id must not be null");
//        Thegif thegif = getById(id);
//        checkNotNull(thegif, "thegif entity not found in database.");
//        thegif.setRemovedBy(userId);
//        thegif.setRemovedDate(LocalDateTime.now());
//        return thegifDaoImpl.update(thegif);
//    }
//
//    @Override
//    public List<Thegif> list(int offset, int limit, String sort, String dir) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<Thegif> listAll(String sort, String dir) {
//        checkNotNull(sort, "sort must not be null");
//        checkNotNull(dir, "dir must not be null");
//        return thegifDaoImpl.listAll(sort, dir);
//    }
//
//    public List<Thegif> listByElementType(String elementType, int offset, int limit, String sort, String dir) {
//        checkNotNull(offset, "offset must not be null");
//        checkNotNull(limit, "limit must not be null");
//        checkNotNull(sort, "sort must not be null");
//        checkNotNull(dir, "dir must not be null");
//        return thegifDaoImpl.listByElementType(elementType, offset, limit, sort, dir);
//    }
//
//    public List<Thegif> listByBookNo(String bookNo, String elementType, String depCode, int offset, int limit, String sort, String dir) {
//        checkNotNull(offset, "offset must not be null");
//        checkNotNull(limit, "limit must not be null");
//        checkNotNull(sort, "sort must not be null");
//        checkNotNull(dir, "dir must not be null");
//        return thegifDaoImpl.listByBookNo(bookNo, elementType, depCode, offset, limit, sort, dir);
//    }     
//    
//    public List<Thegif> listByElementType(String elementType) {
//        return thegifDaoImpl.listByElementType(elementType);
//    }
//
//    @Override
//    public int countAll() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<Thegif> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public int countSearch(MultivaluedMap<String, String> queryParams) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ThegifModel tranformToModel(Thegif thegif) {
//        ThegifModel thegifModel = null;
//        if (thegif != null) {
//            thegifModel = new ThegifModel();
//            thegifModel.setId(thegif.getId());
//            thegifModel.setCreatedBy(thegif.getCreatedBy());
//            thegifModel.setCreatedDate(Common.localDateTimeToString(thegif.getCreatedDate()));
//            thegifModel.setOrderNo((float) thegif.getOrderNo());
//            thegifModel.setUpdatedBy(thegif.getUpdatedBy());
//            thegifModel.setUpdatedDate(Common.localDateTimeToString(thegif.getUpdatedDate()));
//            thegifModel.setRemovedBy(thegif.getRemovedBy());
//            thegifModel.setRemovedDate(Common.localDateTimeToString(thegif.getRemovedDate()));
//            thegifModel.setThegifElementType(thegif.getThegifElementType());
//            thegifModel.setThegifProcessId(thegif.getThegifProcessId());
//            thegifModel.setThegifSendDate(thegif.getThegifSendDate());
//            thegifModel.setThegifBookNo(thegif.getThegifBookNo());
//            thegifModel.setThegifBookDate(thegif.getThegifBookDate());
//            thegifModel.setThegifSpeed(thegif.getThegifSpeed());
//            thegifModel.setThegifSecret(thegif.getThegifSecret());
//            thegifModel.setThegifSenderDepartmentCode(thegif.getThegifSenderDepartmentCode());
//            thegifModel.setThegifReceiverDepartmentCode(thegif.getThegifReceiverDepartmentCode());
//            thegifModel.setThegifAcceptDate(thegif.getThegifAcceptDate());
//            thegifModel.setThegifAcceptId(thegif.getThegifAcceptId());
//            thegifModel.setThegifAcceptDepartmentCode(thegif.getThegifAcceptDepartmentCode());
//            thegifModel.setThegifLetterStatus(thegif.getThegifLetterStatus());
//            thegifModel.setThegifSubject(thegif.getThegifSubject());
//            thegifModel.setThegifDescription(thegif.getThegifDescription());
//            thegifModel.setThegifAttachment(thegif.getThegifAttachment());
//            thegifModel.setThegifReference(thegif.getThegifReference());
//            thegifModel.setThegifFilePath(thegif.getThegifFilePath());
//            thegifModel.setThegifFrom(thegif.getThegifFrom());
//            thegifModel.setThegifTo(thegif.getThegifTo());            
//        }
//        return thegifModel;
//    }
//
//    public ThegifStatusSendModel tranformToThegifStatusSendModel(Thegif thegif) {
//        ThegifStatusSendModel thegifModel = null;
//        if (thegif != null) {
//            thegifModel = new ThegifStatusSendModel();
//            thegifModel.setId(thegif.getId());
//            thegifModel.setCreatedBy(thegif.getCreatedBy());
//            thegifModel.setCreatedDate(Common.localDateTimeToString(thegif.getCreatedDate()));
//            thegifModel.setOrderNo((float) thegif.getOrderNo());
//            thegifModel.setUpdatedBy(thegif.getUpdatedBy());
//            thegifModel.setUpdatedDate(Common.localDateTimeToString(thegif.getUpdatedDate()));
//            thegifModel.setRemovedBy(thegif.getRemovedBy());
//            thegifModel.setRemovedDate(Common.localDateTimeToString(thegif.getRemovedDate()));
//            thegifModel.setThegifElementType(thegif.getThegifElementType());
//            thegifModel.setThegifProcessId(thegif.getThegifProcessId());
//            thegifModel.setThegifSendDate(thegif.getThegifSendDate());
//            thegifModel.setThegifBookNo(thegif.getThegifBookNo());
//            thegifModel.setThegifBookDate(thegif.getThegifBookDate());
//            thegifModel.setThegifSpeed(thegif.getThegifSpeed());
//            thegifModel.setThegifSecret(thegif.getThegifSecret());
//            thegifModel.setThegifSenderDepartmentCode(thegif.getThegifSenderDepartmentCode());
//            thegifModel.setThegifReceiverDepartmentCode(thegif.getThegifReceiverDepartmentCode());
//            thegifModel.setThegifAcceptDate(thegif.getThegifAcceptDate());
//            thegifModel.setThegifAcceptId(thegif.getThegifAcceptId());
//            thegifModel.setThegifAcceptDepartmentCode(thegif.getThegifAcceptDepartmentCode());
//            thegifModel.setThegifLetterStatus(thegif.getThegifLetterStatus());
//            thegifModel.setThegifSubject(thegif.getThegifSubject());
//            thegifModel.setThegifDescription(thegif.getThegifDescription());
//            thegifModel.setThegifAttachment(thegif.getThegifAttachment());
//            thegifModel.setThegifReference(thegif.getThegifReference());
//            thegifModel.setThegifFilePath(thegif.getThegifFilePath());
//            thegifModel.setThegifFrom(thegif.getThegifFrom());
//            thegifModel.setThegifTo(thegif.getThegifTo());
//
//            String thegifStatusSend = "";
//            String thegifAcceptNo = "";
//            String thegifDepName = "";
//            String thegifStatusAcceptDate = "";            
//
//            String elementType = "";
////            String elementType = "ReceiveLetterNotifier,AcceptLetterNotifier";
//            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
//            ThegifService thegifService = new ThegifService();
//            List<Thegif> listthegif = thegifService.listByBookNo(thegif.getThegifBookNo(), elementType, thegif.getThegifReceiverDepartmentCode(),0, 10, "createdDate", "desc"); //bookno,elementype,depcode
//            ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(thegif.getThegifReceiverDepartmentCode());
//                if(thegifDepartment != null){
//                    thegifDepName = thegifDepartment.getThegifDepartmentName();
//                }else {
//                    thegifDepName = thegif.getThegifReceiverDepartmentCode();
//                }
//            
//                if (!listthegif.isEmpty()) {
//                    for(Thegif tmpthegif : listthegif){
//                        thegifStatusSend = tmpthegif.getThegifElementType();
//                        thegifAcceptNo = tmpthegif.getThegifAcceptId();
//                        String acceptDate = thegifService.changeDateToFormat(tmpthegif.getThegifAcceptDate(), 1);
//                        thegifStatusAcceptDate = acceptDate; //.replace("T", " ");
//                        if(thegifAcceptNo == null || thegifAcceptNo.equals("")) thegifAcceptNo = "";
//                        thegifStatusSend = getDataLetterStatus(thegifStatusSend);
//                        break;
//                    }
//                }else {
//                    thegifStatusSend = "ส่งหนังสือ";
//                    String sendDate = thegifService.changeDateToFormat(thegif.getThegifSendDate(), 1);
//                    thegifStatusAcceptDate = sendDate;
//                }       
//                
//            thegifModel.setThegifStatusSend(thegifStatusSend);
//            thegifModel.setThegifAcceptNo(thegifAcceptNo);
//            thegifModel.setThegifDepName(thegifDepName);
//            thegifModel.setThegifStatusAcceptDate(thegifStatusAcceptDate);
//        }
//        return thegifModel;
//    }    
//    
//    public ThegifModel_groupShowList tranformToModelGroupShowList(String fieldName, String data, int thegifId, String fieldDescription, String fieldType, int listWidth, int fieldLength) {
//        ThegifModel_groupShowList thegifModel_groupShowList = null;
//        if (fieldName != null) {
//            thegifModel_groupShowList = new ThegifModel_groupShowList();
//            thegifModel_groupShowList.setFieldName(fieldName);
//            thegifModel_groupShowList.setData(data);
//            thegifModel_groupShowList.setThegifId(thegifId);
//            thegifModel_groupShowList.setFieldDescription(fieldDescription);
//            thegifModel_groupShowList.setFieldType(fieldType);
//            thegifModel_groupShowList.setListWidth(listWidth);
//            thegifModel_groupShowList.setFieldLength(fieldLength);
//        }
//        return thegifModel_groupShowList;
//    }
//
//    public ThegifModel_groupShowList2 tranformToModelGroupShowList2(Thegif thegif, List<ThegifModel_groupShowList> listThegif) {
//
//        ThegifModel_groupShowList2 thegifModel_groupShowList2 = null;
//        if (thegif != null) {
//            thegifModel_groupShowList2 = new ThegifModel_groupShowList2();
//            thegifModel_groupShowList2.setId(thegif.getId());
//            thegifModel_groupShowList2.setCreatedBy(thegif.getCreatedBy());
//            thegifModel_groupShowList2.setCreatedDate(Common.localDateTimeToString(thegif.getCreatedDate()));
//            thegifModel_groupShowList2.setOrderNo((float) thegif.getOrderNo());
//            thegifModel_groupShowList2.setUpdatedBy(thegif.getUpdatedBy());
//            thegifModel_groupShowList2.setUpdatedDate(Common.localDateTimeToString(thegif.getUpdatedDate()));
//            thegifModel_groupShowList2.setRemovedBy(thegif.getRemovedBy());
//            thegifModel_groupShowList2.setRemovedDate(Common.localDateTimeToString(thegif.getRemovedDate()));
//            thegifModel_groupShowList2.setThegifElementType(thegif.getThegifElementType());
//            thegifModel_groupShowList2.setThegifProcessId(thegif.getThegifProcessId());
//            thegifModel_groupShowList2.setThegifSendDate(thegif.getThegifSendDate());
//            thegifModel_groupShowList2.setThegifBookNo(thegif.getThegifBookNo());
//            thegifModel_groupShowList2.setThegifBookDate(thegif.getThegifBookDate());
//            thegifModel_groupShowList2.setThegifSpeed(thegif.getThegifSpeed());
//            thegifModel_groupShowList2.setThegifSecret(thegif.getThegifSecret());
//            thegifModel_groupShowList2.setThegifSenderDepartmentCode(thegif.getThegifSenderDepartmentCode());
//            thegifModel_groupShowList2.setThegifReceiverDepartmentCode(thegif.getThegifReceiverDepartmentCode());
//            thegifModel_groupShowList2.setThegifAcceptDate(thegif.getThegifAcceptDate());
//            thegifModel_groupShowList2.setThegifAcceptId(thegif.getThegifAcceptId());
//            thegifModel_groupShowList2.setThegifAcceptDepartmentCode(thegif.getThegifAcceptDepartmentCode());
//            thegifModel_groupShowList2.setThegifLetterStatus(thegif.getThegifLetterStatus());
//            thegifModel_groupShowList2.setThegifSubject(thegif.getThegifSubject());
//            thegifModel_groupShowList2.setThegifDescription(thegif.getThegifDescription());
//            thegifModel_groupShowList2.setThegifAttachment(thegif.getThegifAttachment());
//            thegifModel_groupShowList2.setThegifReference(thegif.getThegifReference());
//            thegifModel_groupShowList2.setThegifFilePath(thegif.getThegifFilePath());
//            thegifModel_groupShowList2.setTd(listThegif); 
//            List<FileAttachModel> listFileAttach = new ArrayList();//*
//            thegifModel_groupShowList2.setWfFileAttach(listFileAttach);
//            thegifModel_groupShowList2.setThegifFrom(thegif.getThegifFrom());
//            thegifModel_groupShowList2.setThegifTo(thegif.getThegifTo());
//            thegifModel_groupShowList2.setNumFileAttach(new FileAttachService().countAllByLinkTypeLinkId("thegif", thegif.getId()));
//        }
//        return thegifModel_groupShowList2;
//    }
//
//    public ThegifModel tranformToModelChangeData(Thegif thegif) {
//        ThegifService thegifService = new ThegifService();
//        ThegifModel thegifModel = null;
//
//        if (thegif != null) {
//            thegifModel = new ThegifModel();
//            thegifModel.setId(thegif.getId());
//            thegifModel.setCreatedBy(thegif.getCreatedBy());
//            thegifModel.setCreatedDate(Common.localDateTimeToString(thegif.getCreatedDate()));
//            thegifModel.setOrderNo((float) thegif.getOrderNo());
//            thegifModel.setUpdatedBy(thegif.getUpdatedBy());
//            thegifModel.setUpdatedDate(Common.localDateTimeToString(thegif.getUpdatedDate()));
//            thegifModel.setRemovedBy(thegif.getRemovedBy());
//            thegifModel.setRemovedDate(Common.localDateTimeToString(thegif.getRemovedDate()));
//            String elementType = thegifService.getDataLetterStatus(thegif.getThegifElementType());
//            thegifModel.setThegifElementType(elementType);
//            thegifModel.setThegifProcessId(thegif.getThegifProcessId());
//            String sendDate = thegifService.changeDateToFormat(thegif.getThegifSendDate(), 1);
//            thegifModel.setThegifSendDate(sendDate);
//            thegifModel.setThegifBookNo(thegif.getThegifBookNo());
//            String bookDate = thegifService.changeDateToFormat(thegif.getThegifBookDate().substring(0, 10), 0);
//            thegifModel.setThegifBookDate(bookDate);
//            String speed = thegifService.getDataSpeed(thegif.getThegifSpeed());
//            thegifModel.setThegifSpeed(speed);
//            String secert = thegifService.getDataSecret(thegif.getThegifSecret());
//            thegifModel.setThegifSecret(secert);
//            String department = thegifService.getDataDepartment(thegif.getThegifSenderDepartmentCode());
//            thegifModel.setThegifSenderDepartmentCode(department);
//            String department2 = thegifService.getDataDepartment(thegif.getThegifReceiverDepartmentCode());
//            thegifModel.setThegifReceiverDepartmentCode(department2);
//            String acceptDate = thegifService.changeDateToFormat(thegif.getThegifAcceptDate(), 1);
//            thegifModel.setThegifAcceptDate(acceptDate);
//            thegifModel.setThegifAcceptId(thegif.getThegifAcceptId());
//            String department3 = thegifService.getDataDepartment(thegif.getThegifAcceptDepartmentCode());
//            thegifModel.setThegifAcceptDepartmentCode(department3);
//            String letterStatus = thegifService.getDataLetterStatus(thegif.getThegifLetterStatus());
//            thegifModel.setThegifLetterStatus(letterStatus);
//            thegifModel.setThegifSubject(thegif.getThegifSubject());
//            thegifModel.setThegifDescription(thegif.getThegifDescription());
//            thegifModel.setThegifAttachment(thegif.getThegifAttachment());
//            thegifModel.setThegifReference(thegif.getThegifReference());
//            thegifModel.setThegifFilePath(thegif.getThegifFilePath());
//            thegifModel.setThegifFrom(thegif.getThegifFrom());
//            thegifModel.setThegifTo(thegif.getThegifTo()); 
//        }
//        return thegifModel;
//    }
//
//    public List<ThegifModel_groupShowList> listbyFieldName(Thegif thegif, String elementType) {
//        ThegifService thegifService = new ThegifService();
//        ThegifDocFileService thegifDocFileService = new ThegifDocFileService();
//        ThegifModel_groupShowList2 thegifModel_groupShowList2 = new ThegifModel_groupShowList2();
//        List<ThegifModel_groupShowList> listThegifModel_groupShowList = new ArrayList();
//        int thegifId = thegif.getId();
//
//        //รับหนังสือ
//        if (elementType.equals("CorrespondenceLetter")) {
//            String department = thegifService.getDataDepartment(thegif.getThegifSenderDepartmentCode());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifSenderDepartmentCode", department, thegifId, "จาก", "TEXT", 200, 255));
//            //========================
//            String department2 = thegifService.getDataDepartment(thegif.getThegifReceiverDepartmentCode());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifReceiverDepartmentCode", department2, thegifId, "ถึง", "TEXT", 200, 1000));
//            //========================
//            String bookNo = thegifService.checkStringNull(thegif.getThegifBookNo());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifBookNo", bookNo, thegifId, "เลขที่หนังสือ", "TEXT", 100, 255));
//            //========================
//            String bookDate = thegifService.changeDateToFormat(thegif.getThegifBookDate().substring(0, 10), 0);
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifBookDate", bookDate, thegifId, "วันที่", "DATETIME", 100, 0));
//            //========================
//            String subject = thegifService.checkStringNull(thegif.getThegifSubject());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifSubject", subject, thegifId, "เรื่อง", "TEXT", 200, 4000));
//            //========================
//            String secert = thegifService.getDataSecret(thegif.getThegifSecret());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifSecret", secert, thegifId, "ลับ", "TEXT", 50, 50));
//            //========================
//            String speed = thegifService.getDataSpeed(thegif.getThegifSpeed());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifSpeed", speed, thegifId, "ด่วน", "TEXT", 50, 50));
//            //========================
//            String sendDate = thegifService.changeDateToFormat(thegif.getThegifSendDate(), 1);
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifSendDate", sendDate, thegifId, "วันเวลาที่ส่ง", "DATETIME", 100, 0));
//            //========================
//            String letterStatus = thegifService.getDataLetterStatus2(thegif.getThegifLetterStatus());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifLetterStatus", letterStatus, thegifId, "สถานะหนังสือ", "TEXT", 100, 255));
//            //======================== 
//            List<ThegifDocFile> listThegifDocFile = thegifDocFileService.listByThegifId(thegif.getId(), "createdBy", "asc");
//            if (!listThegifDocFile.isEmpty()) {
//                listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("hasFile", "true", thegifId, "", "BOOLEAN", 50, 0));
//            } else {
//                listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("hasFile", "false", thegifId, "", "BOOLEAN", 50, 0));
//            }
//            //========================
//            String from = thegifService.checkStringNull(thegif.getThegifFrom());
//            //System.out.println("1from="+from);
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifFrom", from, thegifId, "จาก", "TEXT", 100, 1000));
//            //========================
//            String to = thegifService.checkStringNull(thegif.getThegifTo());
//            //System.out.println("1to="+to);
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifTo", to, thegifId, "ถึง/เรียน", "TEXT", 100, 1000));
//            
//        } else {
//            String elementType2 = thegifService.getDataLetterStatus(thegif.getThegifElementType());//elementType
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifElementType", elementType2, thegifId, "สถานะ", "TEXT", 100, 50));
//            String bookNo = thegifService.checkStringNull(thegif.getThegifBookNo());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifBookNo", bookNo, thegifId, "เลขที่หนังสือ", "TEXT", 100, 255));
//            String bookDate = thegifService.changeDateToFormat(thegif.getThegifBookDate().substring(0, 10), 0);
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifBookDate", bookDate, thegifId, "วันที่", "DATETIME", 100, 0));
//            String subject = thegifService.checkStringNull(thegif.getThegifSubject());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifSubject", subject, thegifId, "เรื่อง", "TEXT", 200, 4000));
//            String acceptDate = thegifService.changeDateToFormat(thegif.getThegifAcceptDate(), 1);
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifAcceptDate", acceptDate, thegifId, "วันที่รับ", "DATETIME", 100, 0));
//            String acceptId = thegifService.checkStringNull(thegif.getThegifAcceptId());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifAcceptId", acceptId, thegifId, "เลขที่รับ", "TEXT", 100, 255));
//            String department2 = thegifService.getDataDepartment(thegif.getThegifAcceptDepartmentCode());
////            System.out.println("department2="+department2);
////            System.out.println("thegif.getThegifLetterStatus()="+thegif.getThegifLetterStatus());
////            System.out.println("thegif.getThegifElementType()="+thegif.getThegifElementType());
//            if(thegif.getThegifElementType() != null && thegif.getThegifElementType().equals("SendLetter")){
//                department2 = thegifService.getDataDepartment(thegif.getThegifReceiverDepartmentCode());
////                System.out.println("department2department2="+department2);
//            }            
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifAcceptDepartmentCode", department2, thegifId, "หน่วยงานที่รับ", "TEXT", 200, 255));
//            String letterStatus = thegifService.getDataLetterStatus3(thegif.getThegifLetterStatus(), thegif.getThegifElementType());
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifLetterStatus", letterStatus, thegifId, "สถานะหนังสือ", "TEXT", 100, 255));
//            String SendDate = thegifService.changeDateToFormat(thegif.getThegifSendDate(), 1);
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifLetterStatus", SendDate, thegifId, "วันเวลาที่ส่ง", "DATETIME", 100, 0));
//            String from = thegifService.checkStringNull(thegif.getThegifFrom());
//            //System.out.println("2from="+from);
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifFrom", from, thegifId, "จาก", "TEXT", 100, 1000));
//            //========================
//            String to = thegifService.checkStringNull(thegif.getThegifTo());
//            //System.out.println("2to="+to);
//            listThegifModel_groupShowList.add(thegifService.tranformToModelGroupShowList("thegifTo", to, thegifId, "ถึง/เรียน", "TEXT", 100, 1000));
//            //========================            
//        }
//
//        return listThegifModel_groupShowList;
//    }
//
//    public String getDataDepartment(String departmentCode) {
//        try {
//            String department = "";
//            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
//            if (departmentCode != null && !departmentCode.equals("")) {
//                ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(departmentCode);
//                if(thegifDepartment != null){
//                department = thegifDepartment.getThegifDepartmentName();
//                } else {
//                    department = "";
//                }
//            } else {
//                department = "";
//            }
//            return department;
//        } catch (Exception e) {
////            log.error("Exception : " + e);
//        }
//        return "";
//    }
//
//    public String getDataSpeed(String speed) {
//        if (speed != null && !speed.equals("")) {
//            if (numbericOnly(speed)) {
//                int intSpeed = Integer.parseInt(speed);
//                if (intSpeed == 1) {
//                    speed = "ปกติ";
//                } else if (intSpeed == 2) {
//                    speed = "ด่วน";
//                } else if (intSpeed == 3) {
//                    speed = "ด่วนมาก";
//                } else if (intSpeed == 4) {
//                    speed = "ด่วนที่สุด";
//                }
//            } else {
//                speed = "ปกติ";
//            }
//        } else {
//            speed = "";
//        }
//        return speed;
//    }
//
//    public String getDataSecret(String secret) {
//        if (secret != null && !secret.equals("")) {
//            if (numbericOnly(secret)) {
//                int intSecret = Integer.parseInt(secret);
//                if (intSecret == 1) {
//                    secret = "ปกติ";
//                } else if (intSecret == 2) {
//                    secret = "ลับ";
//                } else if (intSecret == 3) {
//                    secret = "ลับมาก";
//                } else if (intSecret == 4) {
//                    secret = "ลับที่สุด";
//                } else if (intSecret == 5) {
//                    secret = "ลับเฉพาะบุคคล";
//                }
//            } else {
//                secret = "ปกติ";
//            }
//        } else {
//            secret = "";
//        }
//        return secret;
//    }
//
//    public boolean numbericOnly(String tempStr) {
//        boolean result = true;
//        String number = "0123456789";
//
//        for (int i = 0; i < tempStr.length(); i++) {
//            String tempChar = String.valueOf(tempStr.charAt(i));
//            if (number.indexOf(tempChar) == -1) {
//                result = false;
//                break;
//            }
//        }
//        return result;
//    }
//
//    public String getDataLetterStatus(String letterStatus) {
//        if (letterStatus != null) {
//            if (letterStatus.equals("CorrespondenceLetter")) {
//                letterStatus = "แจ้งรับหนังสือ";
//            } else if (letterStatus.equals("SendLetter")) {
//                letterStatus = "ส่งหนังสือ";
//            } else if (letterStatus.equals("ReceiveLetterNotifier")) {
//                letterStatus = "ปลายทางรับหนังสือ";
//            } else if (letterStatus.equals("AcceptLetterNotifier")) {
//                letterStatus = "ปลายทางลงเลขรับหนังสือ";
//            } else if (letterStatus.equals("RejectLetterNotifier")) {
//                letterStatus = "ปลายทางปฏิเสธหนังสือ";
//            } else if (letterStatus.equals("InvalidLetterNotifier")) {
//                letterStatus = "ปลายทางแจ้งหนังสือผิด/ส่งผิด";
//            } else if (letterStatus.equals("InvalidAcceptIDNotifier")) {
//                letterStatus = "ปลายทางแจ้งเลขรับผิด";
//            } else if (letterStatus.equals("CorrespondenceDeleteGovernmentDocumentRequest")) {
//                letterStatus = "ขอลบหนังสือภายนอกเพื่อส่งใหม่";
//            } else {
//                letterStatus = "";
//            }
//        } else {
//            letterStatus = "";
//        }
//
//        return letterStatus;
//    }
//
//    public String getDataLetterStatus2(String letterStatus) {
//        if (letterStatus != null) {
//            if (letterStatus.equals("CorrespondenceLetter")) {
//                letterStatus = "แจ้งรับหนังสือ";
//            } else if (letterStatus.equals("SendLetter")) {
//                letterStatus = "ส่งหนังสือ";
//            } else if (letterStatus.equals("ReceiveLetterNotifier")) {
//                letterStatus = "รับหนังสือ";
//            } else if (letterStatus.equals("AcceptLetterNotifier")) {
//                letterStatus = "ลงเลขรับหนังสือ";
//            } else if (letterStatus.equals("RejectLetterNotifier")) {
//                letterStatus = "แจ้งปฏิเสธหนังสือ";
//            } else if (letterStatus.equals("InvalidLetterNotifier")) {
//                letterStatus = "แจ้งหนังสือผิด/ส่งผิด";
//            } else if (letterStatus.equals("InvalidAcceptIDNotifier")) {
//                letterStatus = "แจ้งเลขรับผิด";
//            } else if (letterStatus.equals("CorrespondenceDeleteGovernmentDocumentRequest")) {
//                letterStatus = "ขอลบหนังสือภายนอกเพื่อส่งใหม่";
//            } else {
//                letterStatus = "";
//            }
//        } else {
//            letterStatus = "";
//        }
//
//        return letterStatus;
//    }
//
//    public String getDataLetterStatus3(String letterStatus, String elementType) {
//        if (letterStatus != null) {
//            if (letterStatus.equals("CorrespondenceLetter")) {
//                letterStatus = "แจ้งรับหนังสือ";
//            } else if (letterStatus.equals("SendLetter")) {
//                letterStatus = "ส่งหนังสือ";
//            } else if (letterStatus.equals("ReceiveLetterNotifier")) {
//                letterStatus = "ปลายทางรับหนังสือ";
//            } else if (letterStatus.equals("AcceptLetterNotifier")) {
//                letterStatus = "ปลายทางลงเลขรับหนังสือ";
//            } else if (letterStatus.equals("RejectLetterNotifier")) {
//                letterStatus = "ปลายทางปฏิเสธหนังสือ";
//            } else if (letterStatus.equals("InvalidLetterNotifier")) {
//                letterStatus = "ปลายทางแจ้งหนังสือผิด/ส่งผิด";
//            } else if (letterStatus.equals("InvalidAcceptIDNotifier")) {
//                if (elementType.equals("InvalidAcceptIDNotifier")) {
//                    letterStatus = "ปลายทางแจ้งเลขรับผิด";
//                } else {
//                    letterStatus = "แจ้งเลขรับผิด";
//                }
//            } else if (letterStatus.equals("CorrespondenceDeleteGovernmentDocumentRequest")) {
//                letterStatus = "ขอลบหนังสือภายนอกเพื่อส่งใหม่";
//            } else {
//                letterStatus = "";
//            }
//        } else {
//            letterStatus = "";
//        }
//
//        return letterStatus;
//    }
//
//    public String textToLetterStatus(String letterStatus) {
//        if (!letterStatus.equals("")) {
//            if ("ปฏิเสธหนังสือ".indexOf(letterStatus) > -1) {
//                letterStatus = "RejectLetterNotifier";
//            } else if ("แจ้งหนังสือผิด/ส่งผิด".indexOf(letterStatus) > -1) {
//                letterStatus = "InvalidLetterNotifier";
//            } else if ("แจ้งเลขรับผิด".indexOf(letterStatus) > -1) {
//                letterStatus = "InvalidAcceptIDNotifier";
//            } else if ("ขอลบหนังสือภายนอกเพื่อส่งใหม่".indexOf(letterStatus) > -1) {
//                letterStatus = "CorrespondenceDeleteGovernmentDocumentRequest";
//            } else {
//                letterStatus = "";
//            }
//        } else {
//            letterStatus = "";
//        }
//
//        return letterStatus;
//    }
//
//    public String changeDateToFormat(String strDate, int type) {
//        Date tmpDate = new Date();
//        Calendar calendar = Calendar.getInstance();
//        String tempData2 = "";
//        if (strDate != null && !strDate.equals("")) {
//            if (strDate.length() > 10) {
//                if (strDate.indexOf(".") > -1) {
//                    String[] temp1 = strDate.split("\\.");
//                    strDate = temp1[0];
//                }
//                String tempData1 = strDate.substring(0, 10);
//                tempData2 = strDate.substring(11);
//                String[] tempData = tempData1.split("-");//yyyy-MM-dd
//                String[] tempDataTime = tempData2.split(":");//hh:mm:ss
//
//                calendar.set(Integer.parseInt(tempData[0]) + 543, Integer.parseInt(tempData[1]) - 1, Integer.parseInt(tempData[2]), Integer.parseInt(tempDataTime[0]), Integer.parseInt(tempDataTime[1]), Integer.parseInt(tempDataTime[2]));
//                tmpDate = calendar.getTime();
//            } else {//no Time
//                String[] tempData = strDate.split("-");//yyyy-MM-dd
//
//                calendar.set(Integer.parseInt(tempData[0]) + 543, Integer.parseInt(tempData[1]) - 1, Integer.parseInt(tempData[2]));
//                tmpDate = calendar.getTime();
//            }
//
//            if (type == 0) {
//                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//                strDate = format1.format(calendar.getTime());
//            } else {
//                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//                strDate = format1.format(calendar.getTime()) + " " + tempData2;
//            }
//
//        } else {
//            strDate = "";
//        }
//
//        return strDate;
//    }
//
//    public Integer countAllListByElementType(String elementType) {
//        checkNotNull(elementType, "elementType must not be null");
//        return thegifDaoImpl.countAllListByElementType(elementType);
//    }
//
//    public String checkStringNull(String strData) {
//        if (strData == null) {
//            strData = "";
//        }
//        return strData;
//    }
//
//    public List<Thegif> searchThegifReceive(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
//        checkNotNull(queryParams, "queryParams entity must not be null");
//        checkNotNull(offset, "offset must not be null");
//        checkNotNull(limit, "limit must not be null");
//        checkNotNull(sort, "sort must not be null");
//        checkNotNull(dir, "dir must not be null");
//        return thegifDaoImpl.searchThegifReceive(queryParams, offset, limit, sort, dir);
//    }
//
//    public int countSearchThegifReceive(MultivaluedMap<String, String> queryParams) {
//        checkNotNull(queryParams, "queryParams entity must not be null");
//        return thegifDaoImpl.countSearchThegifReceive(queryParams);
//    }
//
//    public List<Thegif> searchThegifStatus(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
//        checkNotNull(queryParams, "queryParams entity must not be null");
//        checkNotNull(offset, "offset must not be null");
//        checkNotNull(limit, "limit must not be null");
//        checkNotNull(sort, "sort must not be null");
//        checkNotNull(dir, "dir must not be null");
//        return thegifDaoImpl.searchThegifStatus(queryParams, offset, limit, sort, dir);
//    }
//
//    public int countSearchThegifStatus(MultivaluedMap<String, String> queryParams) {
//        checkNotNull(queryParams, "queryParams entity must not be null");
//        return thegifDaoImpl.countSearchThegifStatus(queryParams);
//    }
//
//    //--ecms
//    public List<Ministry> getECMSMinistry(String fromDepCodeP,String fromWsurlP, String toWsurlP) {
//
//        List<Ministry> list_ministry = null;
//        // TODO initialize WS operation arguments here
//        String fromDepCode = fromDepCodeP;
//        String fromWsurl = fromWsurlP;
//        String toWsurl = toWsurlP;
//        String tagLetter1 = "GetMinistryOrganizationList";
//        String tagLetter2 = "";
//        //System.out.println("fromDepCode = " + fromDepCode);
//        //System.out.println("fromWsurl = " + fromWsurl);
//        //System.out.println("toWsurl = " + toWsurl);
//        try { // Call Web Service Operation
//            com.px.wf.ecms.LetterOther letterother = null;
//            // TODO process result here
////            WsecmsService_Service service = new WsecmsService_Service();
////            WsecmsService port = service.getWsecmsServicePort();
//            
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            
//            String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            System.out.println("getECMSMinistry Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("getECMSMinistry Result Replace = " + result);
//            list_ministry = port.getMinistryService(result);	
//            
////            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
////            System.out.println("Result = " + result);
//
////            listMinistry = port.getMinistryService(result);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return list_ministry;
//        }
//    }
//
//    public List<Organization> getECMSOrganization(String fromDepCodeP,
//            String fromWsurlP, String toWsurlP) {
//
//        List<Organization> listOrganization = null;
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "GetOrganizationList";
//            java.lang.String tagLetter2 = "";
//
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//
//            listOrganization = port.getOrganizationService(result);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listOrganization;
//        }
//    }
//
//    public List<Speed> getECMSSpeed(String fromDepCodeP,
//            String fromWsurlP, String toWsurlP) {
//
//        List<Speed> listSpeed = null;
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "GetSpeedCodes";
//            java.lang.String tagLetter2 = "";
//
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//            
//            listSpeed = port.getSpeedService(result);
//            //System.out.println("listSpeed="+listSpeed);
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listSpeed;
//        }
//    }
//
//    public List<Secret> getECMSSecret(String fromDepCodeP,
//            String fromWsurlP, String toWsurlP) {
//
//        List<Secret> listSecret = null;
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "GetSecretCodes";
//            java.lang.String tagLetter2 = "";
//
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//
//            listSecret = port.getSecretService(result);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listSecret;
//        }
//    }
//
//    public List<MimeCode> getECMSMimeCode(String fromDepCodeP,
//            String fromWsurlP, String toWsurlP) {
//
//        List<MimeCode> listMimeCode = null;
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "GetMimeCodes";
//            java.lang.String tagLetter2 = "";
//
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//
//            listMimeCode = port.getMimeCodeService(result);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listMimeCode;
//        }
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> getECMSStatus(String fromDepCodeP,
//            String fromWsurlP, String toWsurlP) {
//
//        String status = "";
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//        
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "OutboundStatusRequest";
//            java.lang.String tagLetter2 = "";
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//
//            status = port.getValinTagNameService2(result, "OutboundStatusResponse");
//            if (status.equals("")) {
//                status = "ECMS Active";
//            }
//            String errorcode = port.getError(result);
//            listThegifModel_ECMSResult = setDataECMSResult(status, errorcode,"", -1);
//
////            status = port.getValinTagNameService2(result, "OutboundStatusResponse");
////            if (status.equals("Error")) {
////                status = port.getValinTagNameService2(result, "ErrorCode");
////            } else if (status.equals("")) {
////                status = "ECMS Active";
////            }
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listThegifModel_ECMSResult;
//        }
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> getECMSTimeCheck(String fromDepCodeP,
//            String fromWsurlP, String toWsurlP) {
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//        String status = "";
//        //System.out.println("--------------getECMSTimeCheck--------------");
//        try { // Call Web Service Operation
//            //System.out.println("Call Web Service Operation");
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            //System.out.println("service="+service);
//            WsecmsService port = service.getWsecmsServicePort();
//            //System.out.println("port="+port);
//            // TODO initialize WS operation arguments here
//            //System.out.println("fromDepCodeP="+fromDepCodeP);
//            //System.out.println("fromWsurlP="+fromWsurlP);
//            //System.out.println("toWsurlP="+toWsurlP);
//            //System.out.println("----------------");
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "TimeCheckRequest";
//            java.lang.String tagLetter2 = "";
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();    
//            //System.out.println("fromDepCode="+fromDepCode);
//            //System.out.println("fromWsurl="+fromWsurl);
//            //System.out.println("toWsurl="+toWsurl);
//            //System.out.println("tagLetter1="+tagLetter1);
//            //System.out.println("tagLetter2="+tagLetter2);
//            //System.out.println("letterother="+letterother);            
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//
//            //System.out.println("Result First = " + result);
////            result = result.replaceAll("/", "\\");
////            System.out.println("Result Replace / Slash = " + result);
//
//            status = port.getValinTagNameService2(result, "ECMSTime");
//            if (status.equals("Error")) {
//                status = port.getValinTagNameService2(result, "ErrorCode");
//            }
//            //System.out.println("status="+status);
//            String errorcode = port.getError(result);
//            //System.out.println("errorcode="+errorcode);
//            
//            listThegifModel_ECMSResult = setDataECMSResult(status, errorcode,"",-1);
//            //System.out.println("listThegifModel_ECMSResultlistThegifModel_ECMSResult="+listThegifModel_ECMSResult);
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//            //System.out.println("Exception = " + ex.getMessage());
//        } finally {
//            return listThegifModel_ECMSResult;
//        }
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> getECMSRejectLetterNotifier(String fromDepCodeP, String fromWsurlP, String toWsurlP, Thegif thegif) {
//        String status = "";
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "CorrespondenceLetterOutboundRequest";
//            java.lang.String tagLetter2 = "RejectLetterNotifier";
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            letterother.setLetterId(thegif.getThegifBookNo());
//            letterother.setCorrespondenceDate(thegif.getThegifBookDate());
//            letterother.setSubject(thegif.getThegifSubject());
//
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//
////            status = port.getValinTagNameService2(result, "RejectLetterNotifier");
////            if (status.equals("Error")) {
////                status = port.getValinTagNameService2(result, "ErrorCode");
////            }
//            String errorcode = port.getError(result);
//            listThegifModel_ECMSResult = setDataECMSResult(result, errorcode,"",-1);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listThegifModel_ECMSResult;
//        }
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> getECMSInvalidLetterNotifier(String fromDepCodeP, String fromWsurlP, String toWsurlP, Thegif thegif) {
//        String status = "";
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "CorrespondenceLetterOutboundRequest";
//            java.lang.String tagLetter2 = "InvalidLetterNotifier";
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            letterother.setLetterId(thegif.getThegifBookNo());
//            letterother.setCorrespondenceDate(thegif.getThegifBookDate());
//            letterother.setSubject(thegif.getThegifSubject());
//
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//
////            status = port.getValinTagNameService2(result, "InvalidLetterNotifier");
////            if (status.equals("Error")) {
////                status = port.getValinTagNameService2(result, "ErrorCode");
////            }
//            String errorcode = port.getError(result);
//            listThegifModel_ECMSResult = setDataECMSResult(result, errorcode,"",-1);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listThegifModel_ECMSResult;
//        }
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> getECMSInvalidAcceptIDNotifier(String fromDepCodeP, String fromWsurlP, String toWsurlP, Thegif thegif) {
//        String status = "";
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "CorrespondenceLetterOutboundRequest";
//            java.lang.String tagLetter2 = "InvalidAcceptIDNotifier";
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            letterother.setLetterId(thegif.getThegifBookNo());
//            letterother.setCorrespondenceDate(thegif.getThegifBookDate());
//            letterother.setSubject(thegif.getThegifSubject());
//            letterother.setAcceptId(thegif.getThegifAcceptId());
//
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//
////            status = port.getValinTagNameService2(result, "InvalidAcceptIDNotifier");
////            if (status.equals("Error")) {
////                status = port.getValinTagNameService2(result, "ErrorCode");
////            }
//            String errorcode = port.getError(result);
//            listThegifModel_ECMSResult = setDataECMSResult(result, errorcode,"",-1);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listThegifModel_ECMSResult;
//        }
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> getECMSDeleteGovernmentDocumentRequest(String fromDepCodeP, String fromWsurlP, String toWsurlP, Thegif thegif) {
//        String status = "";
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "CorrespondenceDeleteGovernmentDocumentRequest";
//            java.lang.String tagLetter2 = "";
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            letterother.setLetterId(thegif.getThegifBookNo());
//            String bookDate = thegif.getThegifBookDate().substring(0, 10);
//            letterother.setCorrespondenceDate(bookDate);
//            letterother.setSenderDeptCode(fromDepCodeP);
//            letterother.setAcceptDeptCode(thegif.getThegifReceiverDepartmentCode());
//
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
//            //System.out.println("Result Replace = " + result);
//
////            status = port.getValinTagNameService2(result, "ECMSDeleteGovernmentDocumentRequest");
////            if (status.equals("Error")) {
////                status = port.getValinTagNameService2(result, "ErrorCode");
////            }
//            String errorcode = port.getError(result);
//            listThegifModel_ECMSResult = setDataECMSResult(result, errorcode,"",-1);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listThegifModel_ECMSResult;
//        }
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> getECMSDeleteRequest(String fromDepCodeP, String fromWsurlP, String toWsurlP, String thegifProcessId) {
//        String status = "";
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "CorrespondenceLetterDeleteRequest";
//            java.lang.String tagLetter2 = "";
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            letterother.setProcessId(thegifProcessId);
//
//            // TODO process result here
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//
//            status = port.getValinTagNameService2(result, "ECMSDeleteRequest");
////            if (status.equals("Error")) {
////                status = port.getValinTagNameService2(result, "ErrorCode");
////            }
//            String errorcode = port.getError(result);
//            listThegifModel_ECMSResult = setDataECMSResult(result, errorcode,"",-1);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listThegifModel_ECMSResult;
//        }
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> getECMSAcceptLetterNotifier(String fromDepCodeP, String fromWsurlP, String toWsurlP, Thegif thegif, String wfContentContentNo) {
//        String status = "";
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "CorrespondenceLetterOutboundRequest";
//            java.lang.String tagLetter2 = "AcceptLetterNotifier";
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            letterother.setLetterId(thegif.getThegifBookNo());
//            letterother.setCorrespondenceDate(thegif.getThegifBookDate());
//            letterother.setSubject(thegif.getThegifSubject());
//            letterother.setAcceptId(wfContentContentNo);
//
//            // TODO process result here 
//            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            //System.out.println("Result First = " + result);
////            result = result.replaceFirst("C:", "Z:");
////            System.out.println("Result Replace = " + result);
//
////            status = port.getValinTagNameService2(result, "AcceptLetterNotifier");
////            if (status.equals("Error")) {
////                status = port.getValinTagNameService2(result, "ErrorCode");
////            }
//            String errorcode = port.getError(result);
//            listThegifModel_ECMSResult = setDataECMSResult(result, errorcode,"",-1);
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return listThegifModel_ECMSResult;
//        }
//    }
//
//    public ThegifModel_ECMSTime tranformToModelECMSTime(String ECMSTime) {
//        ThegifModel_ECMSTime thegifModel_ECMSTime = null;
//        thegifModel_ECMSTime = new ThegifModel_ECMSTime();
//        thegifModel_ECMSTime.setECMSTimeCheck(ECMSTime);
//
//        return thegifModel_ECMSTime;
//    }
//
//    public ThegifModel_ECMSStatus tranformToModelECMSStatus(String ECMSStatus) {
//        ThegifModel_ECMSStatus thegifModel_ECMSStatus = null;
//        thegifModel_ECMSStatus = new ThegifModel_ECMSStatus();
//        thegifModel_ECMSStatus.setECMSStatus(ECMSStatus);
//
//        return thegifModel_ECMSStatus;
//    }
//
//    public ThegifModel_ECMSSpeed tranformToModelECMSSpeed(Speed speed) {
//        ThegifModel_ECMSSpeed thegifModel_ECMSSpeed = null;
//        thegifModel_ECMSSpeed = new ThegifModel_ECMSSpeed();
//        thegifModel_ECMSSpeed.setSpeedValue(speed.getSpeedValue());
//        thegifModel_ECMSSpeed.setSpeedDescription(speed.getSpeedDescription());
//
//        return thegifModel_ECMSSpeed;
//    }
//
//    public ThegifModel_ECMSSecret tranformToModelECMSSecret(Secret secret) {
//        ThegifModel_ECMSSecret thegifModel_ECMSSecret = null;
//        thegifModel_ECMSSecret = new ThegifModel_ECMSSecret();
//        thegifModel_ECMSSecret.setSecretValue(secret.getSecretValue());
//        thegifModel_ECMSSecret.setSecretDescription(secret.getSecretDescription());
//
//        return thegifModel_ECMSSecret;
//    }
//
//    public ThegifModel_ECMSMimeCode tranformToModelECMSMimeCode(MimeCode mimeCode) {
//        ThegifModel_ECMSMimeCode thegifModel_ECMSMimeCode = null;
//        thegifModel_ECMSMimeCode = new ThegifModel_ECMSMimeCode();
//        thegifModel_ECMSMimeCode.setContentType(mimeCode.getContentType());
//        thegifModel_ECMSMimeCode.setFileExtension(mimeCode.getFileExtension());
//
//        return thegifModel_ECMSMimeCode;
//    }
//
//    public ThegifModel_ECMSMinistry tranformToModelECMSMinistry(Ministry ministry) {
//        ThegifModel_ECMSMinistry thegifModel_ECMSMinistry = null;
//        thegifModel_ECMSMinistry = new ThegifModel_ECMSMinistry();
//        thegifModel_ECMSMinistry.setMinistryID(ministry.getMinistryID());
//        thegifModel_ECMSMinistry.setMinistryTHName(ministry.getMinistryTHName());
//
//        return thegifModel_ECMSMinistry;
//    }
//
//    public ThegifModel_ECMSOrganization tranformToModelECMSOrganization(Organization organization) {
//        ThegifModel_ECMSOrganization thegifModel_ECMSOrganization = null;
//        thegifModel_ECMSOrganization = new ThegifModel_ECMSOrganization();
//        thegifModel_ECMSOrganization.setECMSURL(organization.getECMSURL());
//        thegifModel_ECMSOrganization.setOrganizationENName(organization.getOrganizationENName());
//        thegifModel_ECMSOrganization.setOrganizationID(organization.getOrganizationID());
//        thegifModel_ECMSOrganization.setOrganizationTHName(organization.getOrganizationTHName());
//
//        return thegifModel_ECMSOrganization;
//    }
//
//    public ThegifModel_ECMSResult tranformToModelECMSResult(String result, String errorCode, String errorDescription,String depId,int wfContentId) {
//        ThegifModel_ECMSResult thegifModel_ECMSResult = null;
//        thegifModel_ECMSResult = new ThegifModel_ECMSResult();
//        thegifModel_ECMSResult.setResult(result);
//        thegifModel_ECMSResult.setErrorCode(errorCode);
//        thegifModel_ECMSResult.setErrorDescription(errorDescription);
//        thegifModel_ECMSResult.setDepCode(depId);
//        thegifModel_ECMSResult.setWfContentId(wfContentId);
//
//        return thegifModel_ECMSResult;
//    }
//
//    public String checkECMSLetter(String fromDepCodeP,
//            String fromWsurlP, String toWsurlP,
//            String ECMSDELETEREQUEST, int userID) {
//        String result2 = "";
//
//        if (ECMSDELETEREQUEST.equals("")) {
//            ECMSDELETEREQUEST = "Y";
//        }
//
//        String lettertype2 = "";
//
//        String processid = "";
//        String processtime = "";
//        String letterid = "";
//        String corrdate = "";
//        String subject = "";
//        String acceptid = "";
//        String acceptdate = "";
//        String acceptdeptcode = "";
//
//        String senddate = "";
//        String secret = "";
//        String speed = "";
//        String sender_departmentid = "";
//        String receiver_departmentid = "";
//        String description = "";
//        String attachmenttext = "";
//        String referencetext = "";
//        String referenceid = "";
//        String referencecorrdate = "";
//        String referencesubject = "";
//
//        String attachbase64 = "";
//        String attachmime = "";
//        String attachfiletype = "";
//        int attachtype = 0;
//        String from = "";
//        String to = "";
//        
//        try { // Call Web Service Operation
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String fromWsurl = fromWsurlP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String tagLetter1 = "CorrespondenceLetterInboundRequest";
//            java.lang.String tagLetter2 = "";
//            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//            // TODO process result here
//            java.lang.String pathfile = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            
//            //System.out.println("Pathfile First = " + pathfile);
////            pathfile = pathfile.replaceFirst("C:", "Z:");
////            System.out.println("Pathfile Replace= " + pathfile);
//            
//            Reference reference = new Reference();
//            Attachment attach = new Attachment();
//            Letter letter = port.receiveLetterService(pathfile);
//            lettertype2 = letter.getTypeLetter2();
//
//            if (lettertype2 == null) {
//                lettertype2 = "CorrespondenceLetterInboundResponse";
//            }
//            if (!lettertype2.equals("CorrespondenceLetterInboundResponse")) {
//                processid = noNull(letter.getProcessId());
//                processtime = noNull(letter.getProcessTime());
//                letterid = letter.getLetterNumber();
//                corrdate = letter.getCorrespondenceDate();
//                subject = letter.getSubject();
//                acceptid = noNull(letter.getAcceptId());
//                acceptdate = noNull(letter.getAcceptDate());
//                acceptdeptcode = noNull(letter.getAcceptDeptCode());
//                senddate = letter.getSendDate();
//                secret = letter.getSecretCode();
//                speed = letter.getSpeedCode();
//                sender_departmentid = letter.getSenderDeptId();
//                receiver_departmentid = letter.getReceiverDeptId();
//                description = letter.getDescription();
//                attachmenttext = letter.getAttachmentText();
//
//                letterid = checkEscChar("2", letterid);
//                subject = checkEscChar("2", subject);
//                acceptid = checkEscChar("2", acceptid);
//                description = checkEscChar("2", description);
//                attachmenttext = checkEscChar("2", attachmenttext);
//                if(letter.getSenderName() != null && !letter.getSenderName().equals("")){
//                    from += letter.getSenderName();
//                        if(letter.getSenderLname() != null && !letter.getSenderLname().equals("")){
//                            from += " "+letter.getSenderLname();
//                        }
//                }
//
//                if(letter.getSenderTitle()!= null && !letter.getSenderTitle().equals("")){
//                    from = letter.getSenderTitle();
//                }
//                
//                
//                if(letter.getReceiverName() != null && !letter.getReceiverName().equals("")){
//                    to += letter.getReceiverName();
//                    if(letter.getReceiverLname() != null && !letter.getReceiverLname().equals("")){
//                        to += " "+letter.getReceiverLname();
//                    }
//                }
//                if(letter.getReceiverTitle() != null && !letter.getReceiverTitle().equals("")){
//                    //to = letter.getReceiverTitle();
//                    to += " "+letter.getReceiverTitle();
//                }                                     
//                
//                for (int ref = 0; ref < letter.getReference().size(); ref++) {
//                    reference = letter.getReference().get(ref);
//                    referenceid = reference.getLetterNumber();
//                    referencecorrdate = reference.getCorrespondenceDate();
//                    referencesubject = reference.getSubject();
//                    referenceid = checkEscChar("2", referenceid);
//                    referencesubject = checkEscChar("2", referencesubject);
//                    if (!referencecorrdate.equals("")) {
//                        referencecorrdate = "," + referencecorrdate;
//                    }
//                    if (!referencesubject.equals("")) {
//                        referencesubject = "," + referencesubject;
//                    }
//                    if (referencetext.equals("")) {
//                        referencetext = referenceid + referencecorrdate + referencesubject;
//                    } else {
//                        referencetext += " | " + referenceid + referencecorrdate + referencesubject;
//                    }
//                }
//            }
//
//            ThegifService thegifService = new ThegifService();
//            Thegif thegif = new Thegif();
//            if (lettertype2.equals("CorrespondenceLetterInboundResponse")) {
//
//                //do nothing
//            } else if (lettertype2.equals("CorrespondenceLetter")) {
//                //--todox insert into pc_thegif
//                thegif.setCreatedBy(userID);
//                thegif.setThegifElementType(lettertype2);
//                thegif.setThegifProcessId(processid);
//                thegif.setThegifSendDate(senddate);
//                thegif.setThegifBookNo(letterid);
//                thegif.setThegifBookDate(corrdate);
//                thegif.setThegifSpeed(speed);
//                thegif.setThegifSecret(secret);
//                thegif.setThegifSenderDepartmentCode(sender_departmentid);
//                thegif.setThegifReceiverDepartmentCode(receiver_departmentid);
//                thegif.setThegifAcceptDate(acceptdate);
//                thegif.setThegifAcceptId(acceptid);
//                thegif.setThegifAcceptDepartmentCode(acceptdeptcode);
//                thegif.setThegifLetterStatus(lettertype2);
//                thegif.setThegifSubject(subject);
//                thegif.setThegifDescription(description);
//                thegif.setThegifAttachment(attachmenttext);
//                referencetext = changeDateInReferenceThegif(referencetext);
//                thegif.setThegifReference(referencetext);
//                thegif.setThegifFilePath(pathfile);
//                thegif.setThegifFrom(from); //tum add
//                thegif.setThegifTo(to); //tum add                
//                thegif = thegifService.create(thegif);
//
//                thegif.setUpdatedBy(userID);
//                thegif.setOrderNo(thegif.getId());
//                thegif = thegifService.update(thegif);
//
//                String filepath = "";
//                ThegifDocFileService thegifDocFileService = new ThegifDocFileService();
//
//                String documentName = subject;
//                int thegifId = thegif.getId();
//
//                DmsDocument document = new DmsDocument();
//                SubmoduleService submoduleService = new SubmoduleService();
//                int submoduleId = submoduleService.getBySubmoduleCode("thegif").getId();
//                DmsDocumentService dmsDocumentService = new DmsDocumentService();
////                DocumentFile documentFile = new DocumentFile();
////                DocumentFileService documentFileService = new DocumentFileService();
//                ParamService paramService = new ParamService();
//                FileAttach fileAttach = new FileAttach();
//                FileAttachService fileAttachService = new FileAttachService();
//                String paramPath = paramService.getByParamName("THEGIF_PAHT").getParamValue();
//
//                document = dmsDocumentService.createForWf(userID, documentName);
//                int docId = document.getId();
//                //--todox insert into pc_thegif_docfile
//                for (int att = 0; att < letter.getAttachment().size(); att++) {
//                    attach = letter.getAttachment().get(att);
//                    attachtype = attach.getType();
//                    attachbase64 = attach.getBase64();
//                    attachmime = attach.getMime();
//                    attachfiletype = ExtBytype(attachmime);
//                    attachtype = att + 1;
//
//                    ThegifDocFile thegifDocFile = new ThegifDocFile();
//                    thegifDocFile.setCreatedBy(userID);
//                    thegifDocFile.setThegifId(thegif.getId());
//                    thegifDocFile.setThegifDocFileFileId(attachtype);
//                    thegifDocFile.setThegifDocFileLetter("เอกสารแนบ " + attachtype);
//                    thegifDocFile.setThegifDocFileLetterType(attachfiletype);
//                    thegifDocFile.setDmsDocumentId(0);
//                    thegifDocFile = thegifDocFileService.create(thegifDocFile);
//
//                    thegifDocFile.setOrderNo(thegifDocFile.getId());
//                    thegifDocFile.setUpdatedBy(userID);
//                    thegifDocFileService.update(thegifDocFile);
//
//                    //===use rest dmsDocument createDocThegifForWf top===
//                    //set file
//                    //add base64
//                    //move2realpath              
//                    int thegifIdInt = 0;
//                    String thegifIdStr = "";
//                    thegifIdInt = thegifDocFile.getId();
//
//                    if (thegifIdInt < 1000) {
//                        if (thegifIdInt < 10) {
//                            thegifIdStr = "0" + "0" + "0" + Integer.toString(thegifIdInt);
//                        }
//                        if (thegifIdInt < 100 && thegifIdInt >= 10) {
//                            thegifIdStr = "0" + "0" + Integer.toString(thegifIdInt);
//                        }
//                        if (thegifIdInt >= 100) {
//                            thegifIdStr = "0" + Integer.toString(thegifIdInt);
//                        }
//                    } else {
//                        thegifIdStr = Integer.toString(thegifIdInt);
//                    }
//
//                    String thegifType = thegifDocFile.getThegifDocFileLetterType();
//                    String tempPath = paramPath + Integer.toString(thegifId) + "_" + thegifIdStr + thegifType;
//                    String docFileName = thegifDocFile.getThegifDocFileLetter();
//
////                    documentFile.setDocumentFileName(docFileName);
////                    documentFile.setModuleId(submoduleId);
////                    documentFile.setLinkId(docId);
////                    documentFile.setLinkId2(3);
////                    documentFile.setCreatedBy(userID);
////                    documentFile.setDocumentFileType(thegifType);
//                    ThegifDocFile thegifDocFile1 = thegifDocFileService.getById(thegifDocFile.getId());
//                    thegifDocFile1.setDmsDocumentId(docId);
//                    thegifDocFileService.update(thegifDocFile1);
////                    int documentFileId = documentFileService.createDocumentFile(documentFile);
//
//                    fileAttach.setCreatedBy(userID);
//                    fileAttach.setFileAttachName(docFileName);
//                    fileAttach.setFileAttachType(thegifType);
//                    fileAttach.setLinkType("thegif");
//                    fileAttach.setLinkId(thegifId);
//                    fileAttach.setSecrets(1);
//                    fileAttach = fileAttachService.create(fileAttach);
//
//                    if (fileAttach != null) {
//                        String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
//                        String fileSave = pathDocumentTemp + fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
//
//                        try {
//                            fileAttachService.saveFileFromBase64(attachbase64, fileSave);
//
//                        } catch (IOException ex) {
//                            java.util.logging.Logger.getLogger(ThegifService.class
//                                    .getName()).log(Level.SEVERE, null, ex);
//                        }
//
//                        fileSave = fileAttachService.moveToRealPath(fileAttach.getId());
//                        File file = new File(fileSave);
//                        fileAttach.setFileAttachSize(file.length());
//                        fileAttach = fileAttachService.update(fileAttach);
//
//                    }
////                        }
////                    }
//                    //===use rest dmsDocument createDocThegifForWf top===
//                }
//
//                if (ECMSDELETEREQUEST.equals("Y")) {
//                    try {
//                        fromDepCode = fromDepCodeP;
//                        fromWsurl = fromWsurlP;
//                        toWsurl = toWsurlP;
//                        tagLetter1 = "CorrespondenceLetterDeleteRequest";
//                        letterother = new com.px.wf.ecms.LetterOther();
//                        letterother.setProcessId(processid);
//                        pathfile = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
////                        pathfile = pathfile.replaceFirst("C:", "Z:");
//                    } catch (Exception ex) {
//                        // TODO handle custom exceptions here
//                    }
//                }
//
//                //ส่งหนังสือตอบรับ
//                try {
//                    tagLetter1 = "CorrespondenceLetterOutboundRequest";
//                    tagLetter2 = "ReceiveLetterNotifier";
//                    fromDepCode = fromDepCodeP;
//                    fromWsurl = fromWsurlP;
//
//                    //todox url of sender so tell ble coding
//                    ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
//                    ThegifDepartment thegifDepartment = new ThegifDepartment();
//                    thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(sender_departmentid);
//                    toWsurl = thegifDepartment.getThegifDepartmentServiceName();
//
//                    letterother = new com.px.wf.ecms.LetterOther();
//                    letterother.setLetterId(letterid);
//                    letterother.setCorrespondenceDate(corrdate);
//                    letterother.setSubject(subject);
//                    pathfile = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//                } catch (Exception ex) {
//                    // TODO handle custom exceptions here
//                }
//
//            } else if (lettertype2.equals("ReceiveLetterNotifier")
//                    || lettertype2.equals("AcceptLetterNotifier")
//                    || lettertype2.equals("RejectLetterNotifier")
//                    || lettertype2.equals("InvalidLetterNotifier")
//                    || lettertype2.equals("InvalidAcceptIDNotifier")) {
//
//                //--todox insert into pc_thegif
//                thegif.setCreatedBy(userID);
//                thegif.setThegifElementType(lettertype2);
//                thegif.setThegifProcessId(processid);
//                thegif.setThegifSendDate(senddate);
//                thegif.setThegifBookNo(letterid);
//                thegif.setThegifBookDate(corrdate);
//                thegif.setThegifSpeed(speed);
//                thegif.setThegifSecret(secret);
//                thegif.setThegifSenderDepartmentCode(sender_departmentid);
//                thegif.setThegifReceiverDepartmentCode(receiver_departmentid);
//                thegif.setThegifAcceptDate(acceptdate);
//                thegif.setThegifAcceptId(acceptid);
//                thegif.setThegifAcceptDepartmentCode(acceptdeptcode);
//                thegif.setThegifLetterStatus(lettertype2);
//                thegif.setThegifSubject(subject);
//                thegif.setThegifDescription(description);
//                thegif.setThegifAttachment(attachmenttext);
//                thegif.setThegifReference(referencetext);
//                thegif.setThegifFilePath(pathfile);
//                thegif.setThegifFrom(from); //tum add
//                thegif.setThegifTo(to); //tum add                  
//                thegif = thegifService.create(thegif);
//
//                thegif.setUpdatedBy(userID);
//                thegif.setOrderNo(thegif.getId());
//                thegifService.update(thegif);
//
//                if (ECMSDELETEREQUEST.equals("Y")) {
//                    try {
//                        fromDepCode = fromDepCodeP;
//                        fromWsurl = fromWsurlP;
//                        toWsurl = toWsurlP;
//                        tagLetter1 = "CorrespondenceLetterDeleteRequest";
//                        letterother = new com.px.wf.ecms.LetterOther();
//                        letterother.setProcessId(processid);
//                        pathfile = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//                    } catch (Exception ex) {
//                        // TODO handle custom exceptions here
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } finally {
//            return result2;
//        }
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> sendECMSLetter(String fromDepCodeP,
//            WfContent wfContent, List<FileAttach> listFileAttach,
//            ThegifModel_ECMSOfficer thegifModel_ECMSOfficerSender, ThegifModel_ECMSOfficer thegifModel_ECMSOfficerReceiver,String depId,int wfContentId) {
//
//        String result2 = "";
//
//        //--todox get from List<Officer>
//        String fromWsurlP = thegifModel_ECMSOfficerSender.getWSURL();
//        String toWsurlP = thegifModel_ECMSOfficerReceiver.getWSURL();
//        
//        String sender_givenname = thegifModel_ECMSOfficerSender.getName();
//        String sender_familyname = thegifModel_ECMSOfficerSender.getlName();
//        String sender_jobtitle = thegifModel_ECMSOfficerSender.getTitle();
//        String sender_ministryid = thegifModel_ECMSOfficerSender.getMinistryId();
//        String sender_departmentid = thegifModel_ECMSOfficerSender.getDeptId();
//
//        String receiver_givenname = thegifModel_ECMSOfficerReceiver.getName();
//        String receiver_familyname = thegifModel_ECMSOfficerReceiver.getlName();
//        String receiver_jobtitle = thegifModel_ECMSOfficerReceiver.getTitle();
//        String receiver_ministryid = thegifModel_ECMSOfficerReceiver.getMinistryId();
//        String receiver_departmentid = thegifModel_ECMSOfficerReceiver.getDeptId();
//        //-- get from List<Officer>
//
//        String reference = "";
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//
//        try { // Call Web Service Operation
////            com.px.wf.ecms.WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
////            com.px.wf.ecms.WsecmsService port = service.getWsecmsServicePort();
//            WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fromDepCode = fromDepCodeP;
//            java.lang.String toWsurl = toWsurlP;
//            java.lang.String fromWsurl = fromWsurlP;
//            com.px.wf.ecms.Letter letter = new com.px.wf.ecms.Letter();
//            // TODO process result here
////            System.out.println("wfContent.getWfContentBookNo()="+wfContent.getWfContentBookNo());
////            System.out.println("wfContent.getWfContentBookDate().toString()="+Common.localDateTimeToString(wfContent.getWfContentBookDate()));
////            System.out.println("wfContent.getWfContentTitle()="+wfContent.getWfContentTitle());
////            System.out.println("wfContent.getWfContentSecret()="+wfContent.getWfContentSecret());
////            System.out.println("wfContent.getWfContentSpeed()="+wfContent.getWfContentSpeed());
////            System.out.println("sender_givenname="+sender_givenname);
////            System.out.println("sender_familyname="+sender_familyname);
////            System.out.println("sender_jobtitle="+sender_jobtitle);
////            System.out.println("sender_ministryid="+sender_ministryid);
////            System.out.println("sender_departmentid="+sender_departmentid);
////            System.out.println("receiver_givenname="+receiver_givenname);
////            System.out.println("receiver_familyname="+receiver_familyname);
////            System.out.println("receiver_jobtitle="+receiver_jobtitle);
////            System.out.println("receiver_ministryid"+receiver_ministryid);
////            System.out.println("receiver_departmentid="+receiver_departmentid);
////            System.out.println("wfContent.getWfContentAttachment()="+wfContent.getWfContentAttachment());
////            System.out.println("wfContent.getWfContentDescription()="+wfContent.getWfContentDescription());
////            System.out.println("wfContent.getWfContentReference()="+wfContent.getWfContentReference());
//            letter.setLetterNumber(checkEscChar("1", wfContent.getWfContentBookNo()));
//            letter.setCorrespondenceDate(ECMSFormatDate(Common.localDateTimeToString(wfContent.getWfContentBookDate()), false)); 
//            letter.setSubject(checkEscChar("1", wfContent.getWfContentTitle()));
//            letter.setSecretCode(ECMSFormatCode(wfContent.getWfContentSecret()));
//            letter.setSpeedCode(ECMSFormatCode(wfContent.getWfContentSpeed()));
//            letter.setSenderName(checkEscChar("1", sender_givenname));
//            letter.setSenderLname(checkEscChar("1", sender_familyname));
//            letter.setSenderTitle(checkEscChar("1", sender_jobtitle)); //ตำแหน่ง,ชื่อหน่วยงาน
//            letter.setSenderMinistryId(sender_ministryid);
//            letter.setSenderDeptId(sender_departmentid);
//            letter.setReceiverName(checkEscChar("1", receiver_givenname));
//            letter.setReceiverLname(checkEscChar("1", receiver_familyname));
//            letter.setReceiverTitle(checkEscChar("1", receiver_jobtitle)); //ตำแหน่ง,ชื่อหน่วยงาน
//            letter.setReceiverMinistryId(receiver_ministryid);
//            letter.setReceiverDeptId(receiver_departmentid);
//            letter.setAttachmentText(checkEscChar("1", wfContent.getWfContentAttachment()));
//            letter.setSendDate(ECMSNowDate());
//            letter.setDescription(checkEscChar("1", wfContent.getWfContentDescription())); 
////            letter.setDescription(checkEscChar("1", wfContent.getWfContentText01()));
//
//            reference = wfContent.getWfContentReference();
//            if (reference != null && !reference.equals("")) {
//                Reference obj_reference = new Reference();
//                String referenceid = "";
//                String referencedate = "";
//                String referencesubject = "";
//                String[] valreference = new String[3];
//                String[] valreferenceGroup = getTokenStrings(reference, "|", false);
//                for (int re = 0; re < valreferenceGroup.length; re++) {
//                    valreferenceGroup[re] = valreferenceGroup[re].trim();
//                    valreference = getTokenStrings(valreferenceGroup[re], ",", false);
//                    if (valreference.length > 2) {
//                        referenceid = valreference[0];
//                        referencedate = valreference[1];
//                        referencesubject = valreference[2];
//                        if (!referencedate.equals("")) {
//                            Date tmpreferencedate = Common.dateThaiToEng(referencedate);
//                            referencedate = ECMSFormatDate(tmpreferencedate, false);
//                        }
//                        referenceid = checkEscChar("1", referenceid);
//                        referencesubject = checkEscChar("1", referencesubject);
////                        System.out.println("referenceid="+referenceid);
////                        System.out.println("referencesubject="+referencesubject);
////                        System.out.println("referencedate="+referencedate);
//                        obj_reference = new Reference();
//                        obj_reference.setLetterNumber(referenceid);
//                        obj_reference.setCorrespondenceDate(referencedate);
//                        obj_reference.setSubject(referencesubject);
//                        letter.getReference().add(obj_reference);
//                    }
//                }
//            } 
//  
//            //--todox get from listFileAttach
//            //case 1 have docfile
//            //case 2 not have docfile so add empty text
//            int count = 0;
//            int att = 1;
//            String filetype = "";
//            String mimetype = "";
//            String filepath = "";
//            Attachment obj_attach = new Attachment();
//            FileAttachService fileAttachService = new FileAttachService();
//            if (listFileAttach.size() > 0) {
//                for (FileAttach fileAttach : listFileAttach) {
//                    filetype = fileAttach.getFileAttachType().replaceAll(".", "");
//                    mimetype = fileAttach.getFileAttachType();
//                    mimetype = typeByExt(mimetype);
//                    filepath = fileAttachService.moveToTempPath(fileAttach.getId());
//                    filepath = fileToBase64(filepath);
//                    count++;
//                    if (count > 1) {
//                        att = 2;
//                    }
//                    obj_attach = new Attachment();
//                    obj_attach.setType(att);
//                    obj_attach.setBase64(filepath);
//                    obj_attach.setMime(mimetype);
//                    obj_attach.setFiletype(filetype);
//                    letter.getAttachment().add(obj_attach);
//                }
//            } else {
//                ParamService paramService = new ParamService();
//                Param param = paramService.getByParamName("PATH_DOCUMENT_TEMP");
//                filepath = param.getParamValue() + "empty.txt";
//                obj_attach = new Attachment();
//                obj_attach.setType(1);
//                filepath = fileToBase64(filepath);
//                obj_attach.setBase64(filepath);
//                obj_attach.setMime("text/plain");
//                obj_attach.setFiletype("TXT");
//                letter.getAttachment().add(obj_attach);
//            }
//
//            result2 = port.sendLetterService(fromDepCode, toWsurl, fromWsurl, letter);
//
//            String errorcode = port.getError(result2);
//            listThegifModel_ECMSResult = setDataECMSResult(result2, errorcode , depId , wfContentId);
//
////            String errorcode = "";
////            String errordescription = "";
////            errorcode = port.getValinTagNameService2(result2, "ErrorCode");
////            if (errorcode == null) {
////                errorcode = "";
////            }
////            if (!errorcode.equals("")) {
////                errordescription = port.getValinTagNameService2(result2, "ErrorDescription");
////            }
////            thegifModel_ECMSResult = tranformToModelECMSResult(result2, errorcode, errordescription);
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
////            log.error("===ex=== " + ex);
//        }
//
//        return listThegifModel_ECMSResult;
//    }
//
////    public String ReplyForRegister(String fromDepCodeP, String fromWsurlP, String toWsurlP,Thegif Thegif) {
//////        String tagLetter1 = "CorrespondenceLetterOutboundRequest";
//////        String tagLetter2 = "ReceiveLetterNotifier";
//////        String fromDepCode = fromDepCodeP;
//////        String fromWsurl = fromWsurlP;
//////        String toWsurl = toWsurlP;
//////        
//////        com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
//////        letterother = new com.px.wf.ecms.LetterOther();
//////        letterother.setLetterId(Thegif.getThegifAcceptId());
//////        letterother.setCorrespondenceDate(Thegif.);
//////        letterother.setSubject(subject);
//////        pathfile = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
////        
////        String status = "";
////
////        try { // Call Web Service Operation
////            com.px.wf.ecms.WsecmsService_Service service = new com.px.wf.ecms.WsecmsService_Service();
////            com.px.wf.ecms.WsecmsService port = service.getWsecmsServicePort();
////            // TODO initialize WS operation arguments here
////            java.lang.String fromDepCode = fromDepCodeP;
////            java.lang.String fromWsurl = fromWsurlP;
////            java.lang.String toWsurl = toWsurlP;
////            java.lang.String tagLetter1 = "CorrespondenceLetterDeleteRequest";
////            java.lang.String tagLetter2 = "";
////            com.px.wf.ecms.LetterOther letterother = new com.px.wf.ecms.LetterOther();
////            letterother.setProcessId(thegif.getThegifProcessId());
////
////            // TODO process result here
////            java.lang.String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
////            System.out.println("Result = " + result);
////
////            status = port.getValinTagNameService2(result, "ECMSDeleteRequest");
////            if (status.equals("Error")) {
////                status = port.getValinTagNameService2(result, "ErrorCode");
////            }
////
////        } catch (Exception ex) {
////            // TODO handle custom exceptions here
////        } finally {
////            return status;
////        }
////    }
//    public String ECMSNowDate() {
//        String result = "";
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        result = df.format(c.getTime());
//        result = result.replaceAll(" ", "T");
//        return result;
//    }
//
//    public String changeDateThai2Eng(String datein, boolean hasTime) {
//        String result = "";
//        String[] valueDateIn = getTokenStrings(datein, "/ ", false);
//        if (valueDateIn.length > 2) {
//            int y = Integer.parseInt(valueDateIn[2]);
//            if (y > 2443) {
//                y = y - 543;
//            }
//            result = valueDateIn[0] + "/" + valueDateIn[1] + "/" + Integer.toString(y);
//            if (hasTime && valueDateIn.length > 3) {
//                result += " " + valueDateIn[3];
//            }
//        }
//        return result;
//    }
//
//    public String ECMSFormatDate(String datein, boolean hasTime) throws ParseException {
//        String format = "dd/MM/yyyy HH:mm:ss";
//        if (hasTime == false) {
//            format = "dd/MM/yyyy";
//        }
//        datein = changeDateThai2Eng(datein, hasTime);
//        java.text.DateFormat df = new SimpleDateFormat(format);
//        Date dateout = df.parse(datein);
//        return ECMSFormatDate(dateout, hasTime);
//    }
//  
//    public String ECMSFormatDate(Date datein, boolean hasTime) {
//        String result = "";
//        String format = "yyyy-MM-dd HH:mm:ss";
//        if (hasTime == false) {
//            format = "yyyy-MM-dd";
//        }
//        SimpleDateFormat df = new SimpleDateFormat(format);
//        result = df.format(datein);
//        if (hasTime) {
//            result = result.replaceAll(" ", "T");
//        }
//        return result;
//    }
//
//    public String ECMSFormatCode(int intcode) {
//        String result = "";
//        java.text.DecimalFormat formatcode = new java.text.DecimalFormat("000");
//        result = formatcode.format(intcode);
//        return result;
//    }
//
//    public static String checkEscChar(String type, String input) {
//        //TODO write your implementation code here:
//
//        String result = input;
//        try {
//            if (!result.equals("")) {
//                if (type.equals("1")) {
////                    result = result.replaceAll("&", "&amp;");
//////                    result = result.replaceAll("'", "&apos;");
//////                    result = result.replaceAll("\"", "&quot;");
////                    result = result.replaceAll("<", "&lt;");
////                    result = result.replaceAll(">", "&gt;");
//                } else if (type.equals("2")) {
////                    result = result.replaceAll("&apos;", "'");
////                    result = result.replaceAll("&quot;", "\"");
////                    result = result.replaceAll("&gt;", ">");
////                    result = result.replaceAll("&lt;", "<");
////                    result = result.replaceAll("&amp;", "&");
//
//                }
//            }
//        } catch (Exception e) {
//            result = input;
//        } finally {
//            return result;
//        }
//    }
//
//    public static String noNull(String data) {
//        if (data != null) {
//            return data;
//        } else {
//            return "";
//        }
//    }
//
//    public static String[] getTokenStrings(String str, String delim, boolean returnTokens
//    ) {
//        str = noNull(str);
//        java.util.StringTokenizer st = null;
//        if (delim == null) {
//            st = new java.util.StringTokenizer(str);
//        } else {
//            st = new java.util.StringTokenizer(str, delim, returnTokens);
//        }
//
//        String[] theArray = new String[st.countTokens()];
//
//        for (int i = 0; st.hasMoreTokens(); i++) {
//            theArray[i] = st.nextToken();
//        }
//        return theArray;
//    }
//
//    public static String[] getTokenStrings(String str, String delim) {
//        java.util.StringTokenizer st = null;
//        if (delim == null) {
//            st = new java.util.StringTokenizer(str);
//        } else {
//            st = new java.util.StringTokenizer(str, delim);
//        }
//
//        String[] theArray = new String[st.countTokens()];
//
//        for (int i = 0; st.hasMoreTokens(); i++) {
//            theArray[i] = st.nextToken();
//        }
//        return theArray;
//    }
//
//    public static String fileToBase64(String filename) throws Exception {
//        //int l;
//        java.io.FileInputStream fis = new java.io.FileInputStream(filename);
//        byte[] data = new byte[fis.available()];
//        fis.read(data);
//        String sd = new String(encode(data));
//        return sd;
//    }
//
//    // Mapping table from 6-bit nibbles to Base64 characters.
//    private static char[] map1 = new char[64];
//
//    static {
//        int i = 0;
//        for (char c = 'A'; c <= 'Z'; c++) {
//            map1[i++] = c;
//        }
//        for (char c = 'a'; c <= 'z'; c++) {
//            map1[i++] = c;
//        }
//        for (char c = '0'; c <= '9'; c++) {
//            map1[i++] = c;
//        }
//        map1[i++] = '+';
//        map1[i++] = '/';
//    }
//
//    // Mapping table from Base64 characters to 6-bit nibbles.
//    private static byte[] map2 = new byte[4096];
//
//    static {
//        for (int i = 0; i < map2.length; i++) {
//            map2[i] = -1;
//        }
//        for (int i = 0; i < 64; i++) {
//            map2[map1[i]] = (byte) i;
//        }
//    }
//
//    /**
//     * Encodes a string into Base64 format. No blanks or line breaks are
//     * inserted.
//     *
//     * @param s a String to be encoded.
//     * @return A String with the Base64 encoded data.
//     */
//    public static String encodeString(String s) {
//        return new String(encode(s.getBytes()));
//    }
//
//    /**
//     * Encodes a byte array into Base64 format. No blanks or line breaks are
//     * inserted.
//     *
//     * @param in an array containing the data bytes to be encoded.
//     * @return A character array with the Base64 encoded data.
//     */
//    public static char[] encode(byte[] in) {
//        return encode(in, in.length);
//    }
//
//    /**
//     * Encodes a byte array into Base64 format. No blanks or line breaks are
//     * inserted.
//     *
//     * @param in an array containing the data bytes to be encoded.
//     * @param iLen number of bytes to process in <code>in</code>.
//     * @return A character array with the Base64 encoded data.
//     */
//    public static char[] encode(byte[] in, int iLen) {
//        int oDataLen = (iLen * 4 + 2) / 3;       // output length without padding
//        int oLen = ((iLen + 2) / 3) * 4;         // output length including padding
//        char[] out = new char[oLen];
//        int ip = 0;
//        int op = 0;
//        while (ip < iLen) {
//            int i0 = in[ip++] & 0xff;
//            int i1 = ip < iLen ? in[ip++] & 0xff : 0;
//            int i2 = ip < iLen ? in[ip++] & 0xff : 0;
//            int o0 = i0 >>> 2;
//            int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
//            int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
//            int o3 = i2 & 0x3F;
//            out[op++] = map1[o0];
//            out[op++] = map1[o1];
//            out[op] = op < oDataLen ? map1[o2] : '=';
//            op++;
//            out[op] = op < oDataLen ? map1[o3] : '=';
//            op++;
//        }
//        return out;
//    }
//
//    /**
//     * Decodes a string from Base64 format.
//     *
//     * @param s a Base64 String to be decoded.
//     * @return A String containing the decoded data.
//     * @throws IllegalArgumentException if the input is not valid Base64 encoded
//     * data.
//     */
//    public static String decodeString(String s) {
//        return new String(decode(s));
//    }
//
//    /**
//     * Decodes a byte array from Base64 format.
//     *
//     * @param s a Base64 String to be decoded.
//     * @return An array containing the decoded data bytes.
//     * @throws IllegalArgumentException if the input is not valid Base64 encoded
//     * data.
//     */
//    public static byte[] decode(String s) {
//        return decode(s.toCharArray());
//    }
//
//    /**
//     * Decodes a byte array from Base64 format. No blanks or line breaks are
//     * allowed within the Base64 encoded data.
//     *
//     * @param in a character array containing the Base64 encoded data.
//     * @return An array containing the decoded data bytes.
//     * @throws IllegalArgumentException if the input is not valid Base64 encoded
//     * data.
//     */
//    public static byte[] decode(char[] in) {
//        int iLen = in.length;
//        if (iLen % 4 != 0) {
//            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
//        }
//        while (iLen > 0 && in[iLen - 1] == '=') {
//            iLen--;
//        }
//        int oLen = (iLen * 3) / 4;
//        byte[] out = new byte[oLen];
//        int ip = 0;
//        int op = 0;
//        while (ip < iLen) {
//            int i0 = in[ip++];
//            int i1 = in[ip++];
//            int i2 = ip < iLen ? in[ip++] : 'A';
//            int i3 = ip < iLen ? in[ip++] : 'A';
//            if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) {
//                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
//            }
//            int b0 = map2[i0];
//            int b1 = map2[i1];
//            int b2 = map2[i2];
//            int b3 = map2[i3];
//            if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) {
//                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
//            }
//            int o0 = (b0 << 2) | (b1 >>> 4);
//            int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
//            int o2 = ((b2 & 3) << 6) | b3;
//            out[op++] = (byte) o0;
//            if (op < oLen) {
//                out[op++] = (byte) o1;
//            }
//            if (op < oLen) {
//                out[op++] = (byte) o2;
//            }
//        }
//        return out;
//    }
//
//    public static String typeByExt(String fname) {
//
//        if (fname == null) {
//            return "text/plain";
//        }
//        if (fname.endsWith("rtf") || fname.endsWith("RTF")) {
//            return "application/rtf";
//        } else if (fname.endsWith("doc") || fname.endsWith("DOC")) {
//            return "application/msword";
//        } else if (fname.endsWith("docx") || fname.endsWith("DOCX")) {
//            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
//        } else if (fname.endsWith("xls") || fname.endsWith("XLS")) {
//            return "application/vnd.ms-excel";
//        } else if (fname.endsWith("xlsx") || fname.endsWith("XLSX")) {
//            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//        } else if (fname.endsWith("ppt") || fname.endsWith("PPT")) {
//            return "application/vnd.ms-powerpoint";
//        } else if (fname.endsWith("pptx") || fname.endsWith("PPTX")) {
//            return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
//        } else if (fname.endsWith("mdb") || fname.endsWith("MDB")) {
//            return "application/x-msaccess";
//        } else if (fname.endsWith("gz") || fname.endsWith("GZ")) {
//            return "application/x-gzip";
//        } else if (fname.endsWith("zip") || fname.endsWith("ZIP")) {
//            return "application/zip";
//        } else if (fname.endsWith("jar") || fname.endsWith("JAR")) {
//            return "application/java-archive";
//        } else if (fname.endsWith("java") || fname.endsWith("JAVA")) {
//            return "text/plain";
//        } else if (fname.endsWith("js") || fname.endsWith("JS")) {
//            return "text/javascript";
//        } else if (fname.endsWith("pdf") || fname.endsWith("PDF")) {
//            return "application/pdf";
//        } else if (fname.endsWith("avi") || fname.endsWith("AVI")) {
//            return "video/x-msvideo";
//        } else if (fname.endsWith("wmv") || fname.endsWith("WMV")) {
//            return "video/x-ms-wmv";
//        } else if (fname.endsWith("jpg") || fname.endsWith("JPG")) {
//            return "image/jpeg";
//        } else if (fname.endsWith("jpeg") || fname.endsWith("JPEG")) {
//            return "image/jpeg";
//        } else if (fname.endsWith("bmp") || fname.endsWith("BMP")) {
//            return "image/bmp";
//        } else if (fname.endsWith("gif") || fname.endsWith("GIF")) {
//            return "image/gif";
//        } else if (fname.endsWith("png") || fname.endsWith("PNG")) {
//            return "image/png";
//        } else if (fname.endsWith("tif") || fname.endsWith("TIF")) {
//            return "image/tiff";
//        } else if (fname.endsWith("tiff") || fname.endsWith("TIFF")) {
//            return "image/tiff";
//        } else if (fname.endsWith("rar") || fname.endsWith("RAR")) {
//            return "application/x-rar";
//        } else {
//            return "text/plain";
//        }
//
//    }
//
//    public static String ExtBytype(String fname) {
//
//        if (fname == null) {
//            return ".RTF";
//        }
//        if (fname.equalsIgnoreCase("application/rtf")) {
//            return ".RTF";
//        }
//        if (fname.equalsIgnoreCase("application/msword") || fname.equalsIgnoreCase("application/doc")) {
//            return ".DOC";
//        }
//        if (fname.equalsIgnoreCase("application/vnd.ms-excel") || fname.equalsIgnoreCase("application/vndms-excel") || fname.equalsIgnoreCase("application/xls")) {
//            return ".XLS";
//        }
//        if (fname.equalsIgnoreCase("application/vnd.ms-powerpoint") || fname.equalsIgnoreCase("application/vndms-powerpoint")) {
//            return ".PPT";
//        }
//        if (fname.equalsIgnoreCase("application/x-msaccess")) {
//            return ".MDB";
//        }
//        if (fname.equalsIgnoreCase("application/x-gzip")) {
//            return ".GZ";
//        }
//        if (fname.equalsIgnoreCase("application/x-zip") || fname.equalsIgnoreCase("application/zip")) {
//            return ".ZIP";
//        }
//        if (fname.equalsIgnoreCase("application/java-archive")) {
//            return ".JAR";
//        }
//        if (fname.equalsIgnoreCase("text/javascript") || fname.equalsIgnoreCase("text/x-javascript")) {
//            return ".JS";
//        }
//        if (fname.equalsIgnoreCase("application/pdf")) {
//            return ".PDF";
//        }
//        if (fname.equalsIgnoreCase("video/x-msvideo")) {
//            return ".AVI";
//        }
//        if (fname.equalsIgnoreCase("video/x-ms-wmv")) {
//            return ".WMV";
//        }
//        if (fname.equalsIgnoreCase("image/jpeg") || fname.equalsIgnoreCase("image/jpg")) {
//            return ".JPG";
//        }
//        if (fname.equalsIgnoreCase("image/bmp")) {
//            return ".BMP";
//        }
//        if (fname.equalsIgnoreCase("image/gif")) {
//            return ".GIF";
//        }
//        if (fname.equalsIgnoreCase("image/png")) {
//            return ".PNG";
//        }
//        if (fname.equalsIgnoreCase("image/tiff")) {
////            return ".TIF";
//            return ".TIFF";
//        }
//        if (fname.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
//            return ".PPTX";
//        }
//        if (fname.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
//            return ".XLSX";
//        }
//        if (fname.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
//            return ".DOCX";
//        }
//        if (fname.equalsIgnoreCase("application/x-rar-compressed")) {
//            return ".RAR";
//        } else {
//            return ".TXT";
//        }
//
//    }
//
//    public List<ThegifDepartment> createNewECMSMinistry(String fromDepCodeP, String fromWsurlP, String toWsurlP, int userID) {
//        ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
//        List<ThegifDepartment> listNewMinistry = new ArrayList();
//
//        //list กระทรวงจาก ecms
//        System.out.println("NewECMSMinistryfromDepCodeP="+fromDepCodeP);
//        System.out.println("NewECMSMinistryfromWsurlP="+fromWsurlP);
//        System.out.println("NewECMSMinistrytoWsurlP="+toWsurlP);
//        List<Ministry> listECMSMinistry = this.getECMSMinistry(fromDepCodeP, fromWsurlP, toWsurlP);
//        System.out.println("listECMSMinistrySize="+listECMSMinistry.size());
//        for (Ministry ministry : listECMSMinistry) {
//            ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(ministry.getMinistryID());
//            
//            if (thegifDepartment == null) {
//                System.out.println("thegifDepartmentname="+thegifDepartment.getThegifDepartmentName());
//                System.out.println("thegifDepartmentcode="+thegifDepartment.getThegifDepartmentCode());
//                System.out.println("thegifDepartmentservice="+thegifDepartment.getThegifDepartmentServiceName());
//                ThegifDepartment thegifDepartmentNew = new ThegifDepartment();
//                thegifDepartmentNew.setCreatedBy(userID);
//                if (thegifDepartmentNew != null) {
//                    thegifDepartmentNew.setThegifDepartmentName(ministry.getMinistryTHName());
//                    thegifDepartmentNew.setThegifDepartmentCode(ministry.getMinistryID());
//                    thegifDepartmentNew.setThegifDepartmentServiceName(null);
//
//                    ThegifDepartment parentThegifDepartment = new ThegifDepartment();
//                    thegifDepartmentNew.setParentId(1);
//
//                    //nodelevel and parentKey
//                    parentThegifDepartment = thegifDepartmentService.getById(1);
//
//                    thegifDepartmentNew.setNodeLevel(parentThegifDepartment.getNodeLevel() + 1);
//                    thegifDepartmentNew = thegifDepartmentService.create(thegifDepartmentNew);
//
//                    thegifDepartmentNew.setParentKey(parentThegifDepartment.getParentKey() + thegifDepartmentNew.getId() + "฿");
//                    thegifDepartmentNew.setOrderNo(thegifDepartmentNew.getId());
//                    thegifDepartmentNew.setUpdatedBy(userID);
//                    thegifDepartmentService.update(thegifDepartmentNew);
//                    listNewMinistry.add(thegifDepartmentNew);
//                }
//            } else if (!thegifDepartment.getThegifDepartmentName().equals(ministry.getMinistryTHName())) {
//                thegifDepartment.setThegifDepartmentName(ministry.getMinistryTHName());
//
//                thegifDepartment.setUpdatedBy(userID);
//                thegifDepartmentService.update(thegifDepartment);
//                listNewMinistry.add(thegifDepartment);
//            }
//        }
//        System.out.println("listNewMinistry="+listNewMinistry);
//        return listNewMinistry;
//    }
//
//    public List<ThegifDepartment> createNewECMSOrganization(String fromDepCodeP, String fromWsurlP, String toWsurlP, int userID) {
//        ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
//        List<ThegifDepartment> listNewOrg = new ArrayList();
//
//        //list หน่วยงานจาก ecms
//        System.out.println("NewECMSOrganizationfromDepCodeP="+fromDepCodeP);
//        System.out.println("NewECMSOrganizationfromWsurlP="+fromWsurlP);
//        System.out.println("NewECMSOrganizationWsurlP="+toWsurlP);        
//        List<Organization> listECMSOrganization = this.getECMSOrganization(fromDepCodeP, fromWsurlP, toWsurlP);
//        System.out.println("listECMSOrganizationSize="+listECMSOrganization.size());
//        for (Organization organization : listECMSOrganization) {
//            ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(organization.getOrganizationID());
//            if (thegifDepartment == null) {
//
//                ThegifDepartment thegifDepartmentNew = new ThegifDepartment();
//                thegifDepartmentNew.setCreatedBy(userID);
//                if (thegifDepartmentNew != null) {
//                    thegifDepartmentNew.setThegifDepartmentName(organization.getOrganizationTHName());
//                    thegifDepartmentNew.setThegifDepartmentCode(organization.getOrganizationID());
//                    thegifDepartmentNew.setThegifDepartmentServiceName(organization.getECMSURL());
//
//                    ThegifDepartment parentThegifDepartment = new ThegifDepartment();
//
//                    String orgCode = organization.getOrganizationID().substring(0, 2);
//                    parentThegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(orgCode);
//
//                    thegifDepartmentNew.setNodeLevel(parentThegifDepartment.getNodeLevel() + 1);
//                    thegifDepartmentNew.setParentId(parentThegifDepartment.getId());
//                    thegifDepartmentNew = thegifDepartmentService.create(thegifDepartmentNew);
//
//                    thegifDepartmentNew.setParentKey(parentThegifDepartment.getParentKey() + thegifDepartmentNew.getId() + "฿");
//                    thegifDepartmentNew.setOrderNo(thegifDepartmentNew.getId());
//                    thegifDepartmentNew.setUpdatedBy(userID);
//                    thegifDepartmentService.update(thegifDepartmentNew);
//                    listNewOrg.add(thegifDepartmentNew);
//                }
//            } else if (!thegifDepartment.getThegifDepartmentName().equals(organization.getOrganizationTHName())) {
//                thegifDepartment.setThegifDepartmentName(organization.getOrganizationTHName());
//
//                thegifDepartment.setUpdatedBy(userID);
//                thegifDepartmentService.update(thegifDepartment);
//                listNewOrg.add(thegifDepartment);
//            }
//        }
//        System.out.println("listNewOrg="+listNewOrg);
//        return listNewOrg;
//    }
//
//    public String changeDateInReferenceThegif(String reference) {
//        String result = "";
//        checkNotNull(reference, "");
//        if (!reference.equals("")) {
//            if (reference.indexOf("|") > -1) {
//                String[] tempReference = reference.split("\\|");
//                for (int i = 0; i < tempReference.length; i++) {
//                    String[] tempRef = tempReference[i].split(",");
//                    String tempRef2 = changeDateToFormat(tempRef[1], 0);
//                    result += tempRef[0] + "," + tempRef2 + "," + tempRef[2] + "|";
//                }
//                result = result.substring(0, result.length() - 1);
//            } else {
//                String[] tempRef = reference.split(",");
//                String tempRef2 = changeDateToFormat(tempRef[1], 0);
//                result = tempRef[0] + "," + tempRef2 + "," + tempRef[2];
//            }
//        }
//        return result;
//    }
//
//    public ArrayList<ThegifModel_ECMSResult> setDataECMSResult(String result, String errorText , String depId , int wfContentId) {
//        ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = new ArrayList();
//        ThegifModel_ECMSResult thegifModel_ECMSResult = new ThegifModel_ECMSResult();
//
//        if (errorText.equals("")) {
//            thegifModel_ECMSResult = tranformToModelECMSResult(result, "", "" ,depId , wfContentId);
//            listThegifModel_ECMSResult.add(thegifModel_ECMSResult);
//        } else {
//            String[] tempErrorText = errorText.split("\\|");
//            for (int i = 0; i < tempErrorText.length; i++) {
//                String[] tempErrorText2 = tempErrorText[i].split("=");
//                thegifModel_ECMSResult = tranformToModelECMSResult(result, tempErrorText2[0], tempErrorText2[1],depId ,wfContentId);
//                listThegifModel_ECMSResult.add(thegifModel_ECMSResult);
//            }
//        }
//        return listThegifModel_ECMSResult;
//    }
//}
//
