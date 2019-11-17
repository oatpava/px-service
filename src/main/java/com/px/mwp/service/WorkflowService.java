package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.UserProfileService;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import com.px.mwp.daoimpl.WorkflowDaoImpl;
import com.px.mwp.entity.Workflow;
import com.px.mwp.entity.WorkflowCc;
import com.px.mwp.entity.WorkflowTo;
import com.px.mwp.model.WorkflowModel;
import com.px.mwp.model.WorkflowModel_groupFlow;
import com.px.mwp.model.WorkflowModel_groupLinkFlow;
import com.px.mwp.model.WorkflowModel_groupNodeFlow;
import com.px.share.service.ParamService;
import com.px.wf.service.WfContentService;
import com.px.wf.service.WfFolderService;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Oat
 */
public class WorkflowService implements GenericService<Workflow, WorkflowModel> {

    private static final Logger LOG = Logger.getLogger(WorkflowService.class.getName());
    private final WorkflowDaoImpl workflowDaoImpl;

    public WorkflowService() {
        this.workflowDaoImpl = new WorkflowDaoImpl();
    }

    @Override
    public Workflow create(Workflow workflow) {
        checkNotNull(workflow, "workflow entity must not be null");
        checkNotNull(workflow.getCreatedBy(), "create by must not be null");
        checkNotNull(workflow.getLinkId(), "LinkId by must not be null");
        checkNotNull(workflow.getLinkId2(), "LinkId2 by must not be null");
        checkNotNull(workflow.getWorkflowActionId(), "WorkflowActionId by must not be null");
        checkNotNull(workflow.getWorkflowActionIdType(), "WorkflowActionIdType by must not be null");
        checkNotNull(workflow.getWorkflowActionDate(), "WorkflowActionDate by must not be null");
        return workflowDaoImpl.create(workflow);
    }

    @Override
    public Workflow getById(int id) {
        return workflowDaoImpl.getById(id);
    }

    @Override
    public Workflow getByIdNotRemoved(int id) {
        checkNotNull(id, "Workflow id entity must not be null");
        return workflowDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public Workflow update(Workflow workflow) {
        checkNotNull(workflow, "workflow entity must not be null");
        checkNotNull(workflow.getUpdatedBy(), "update by must not be null");
        checkNotNull(workflow.getLinkId(), "LinkId by must not be null");
        checkNotNull(workflow.getLinkId2(), "LinkId2 by must not be null");
        checkNotNull(workflow.getWorkflowActionId(), "WorkflowActionId by must not be null");
        checkNotNull(workflow.getWorkflowActionIdType(), "WorkflowActionIdType by must not be null");
        checkNotNull(workflow.getWorkflowActionDate(), "WorkflowActionDate by must not be null");
        workflow.setUpdatedDate(LocalDateTime.now());
        return workflowDaoImpl.update(workflow);
    }

    @Override
    public Workflow remove(int id, int userId) {
        checkNotNull(id, "workflow id must not be null");
        Workflow workflow = getById(id);
        checkNotNull(workflow, "workflow entity not found in database.");
        workflow.setRemovedBy(userId);
        workflow.setRemovedDate(LocalDateTime.now());
        return workflowDaoImpl.update(workflow);
    }

    @Override
    public List<Workflow> list(int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Workflow> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Workflow> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WorkflowModel tranformToModel(Workflow workflow) {
        WorkflowModel workflowModel = null;
        if (workflow != null) {
            workflowModel = new WorkflowModel();
            workflowModel.setId(workflow.getId());
            workflowModel.setCreatedBy(workflow.getCreatedBy());
            workflowModel.setCreatedDate(Common.localDateTimeToString(workflow.getCreatedDate()));
            workflowModel.setOrderNo((float) workflow.getOrderNo());
            workflowModel.setUpdatedBy(workflow.getUpdatedBy());
            workflowModel.setUpdatedDate(Common.localDateTimeToString(workflow.getUpdatedDate()));
            workflowModel.setRemovedBy(workflow.getRemovedBy());
            workflowModel.setRemovedDate(Common.localDateTimeToString(workflow.getRemovedDate()));
            workflowModel.setLinkId(workflow.getLinkId());
            workflowModel.setLinkId2(workflow.getLinkId2());
            workflowModel.setLinkId3(workflow.getLinkId3());
            workflowModel.setWorkflowActionId(workflow.getWorkflowActionId());
            workflowModel.setWorkflowActionIdType(workflow.getWorkflowActionIdType());
            workflowModel.setWorkflowActionName(workflow.getWorkflowActionName());
            workflowModel.setWorkflowActionPosition(workflow.getWorkflowActionPosition());
            workflowModel.setWorkflowActionType(workflow.getWorkflowActionType());
            workflowModel.setWorkflowActionDate(Common.localDateTimeToString(workflow.getWorkflowActionDate()));
            workflowModel.setWorkflowDescription(workflow.getWorkflowDescription());
            workflowModel.setWorkflowNote(workflow.getWorkflowNote());
            workflowModel.setWorkflowTitle(workflow.getWorkflowTitle());
            workflowModel.setWorkflowStr01(workflow.getWorkflowStr01());
            workflowModel.setWorkflowStr02(workflow.getWorkflowStr02());
            workflowModel.setWorkflowStr03(workflow.getWorkflowStr03());
            workflowModel.setWorkflowStr04(workflow.getWorkflowStr04());
            workflowModel.setWorkflowDate01(Common.localDateTimeToString(workflow.getWorkflowDate01()));
            workflowModel.setWorkflowDate02(Common.localDateTimeToString(workflow.getWorkflowDate02()));
            workflowModel.setWorkflowDetail(null);
        }
        return workflowModel;
    }

    public WorkflowModel tranformToModelWithDetail(Workflow workflow) {
        WorkflowModel workflowModel = null;
        if (workflow != null) {
            workflowModel = new WorkflowModel();
            workflowModel.setId(workflow.getId());
            workflowModel.setCreatedBy(workflow.getCreatedBy());
            workflowModel.setCreatedDate(Common.localDateTimeToString(workflow.getCreatedDate()));
            workflowModel.setOrderNo((float) workflow.getOrderNo());
            workflowModel.setUpdatedBy(workflow.getUpdatedBy());
            workflowModel.setUpdatedDate(Common.localDateTimeToString(workflow.getUpdatedDate()));
            workflowModel.setRemovedBy(workflow.getRemovedBy());
            workflowModel.setRemovedDate(Common.localDateTimeToString(workflow.getRemovedDate()));
            workflowModel.setLinkId(workflow.getLinkId());
            workflowModel.setLinkId2(workflow.getLinkId2());
            workflowModel.setLinkId3(workflow.getLinkId3());
            workflowModel.setWorkflowActionId(workflow.getWorkflowActionId());
            workflowModel.setWorkflowActionIdType(workflow.getWorkflowActionIdType());
            String name = workflow.getWorkflowActionName();
            UserProfile userProfile = new UserProfileService().getById(workflow.getWorkflowActionId());
            if (userProfile != null) {
                name = name + " (" + userProfile.getStructure().getStructureShortName() + ")";
            }
            workflowModel.setWorkflowActionName(name);
            workflowModel.setWorkflowActionPosition(workflow.getWorkflowActionPosition());
            workflowModel.setWorkflowActionType(getAction(workflow));
            workflowModel.setWorkflowActionDate(Common.localDateTimeToString(workflow.getWorkflowActionDate()));
            workflowModel.setWorkflowDescription(workflow.getWorkflowDescription());
            if (workflow.getWorkflowActionType().equals("N") || workflow.getWorkflowActionType().equals("R")) {
                workflowModel.setWorkflowDescription((workflow.getWorkflowStr02() == null) ? "รอเอกสารตัวจริง" : "ได้รับเอกสารตัวจริงแล้ว\r\n" + workflow.getWorkflowStr02());
            }
            workflowModel.setWorkflowNote(workflow.getWorkflowNote());
            workflowModel.setWorkflowTitle(workflow.getWorkflowTitle());
            workflowModel.setWorkflowStr01(workflow.getWorkflowStr01());
            workflowModel.setWorkflowStr02(workflow.getWorkflowStr02());
            workflowModel.setWorkflowStr03(workflow.getWorkflowStr03());
            workflowModel.setWorkflowStr04(workflow.getWorkflowStr04());
            workflowModel.setWorkflowDate01(Common.localDateTimeToString(workflow.getWorkflowDate01()));
            workflowModel.setWorkflowDate02(Common.localDateTimeToString(workflow.getWorkflowDate02()));
            workflowModel.setWorkflowDetail(genDetail(workflow));
        }
        return workflowModel;
    }

    private String genDetail(Workflow workflow) {
        String detail = "";
        int contentFormat = Integer.parseInt(new ParamService().getByParamName("CONTENTFORMAT").getParamValue());
        char actionType = workflow.getWorkflowActionType().charAt(0);
        String name = "-";
        String contentNoOrder = "-";
        String bookNo = "-";
        if (workflow.getWorkflowActionName() != null) {
            name = workflow.getWorkflowActionName();
            UserProfile userProfile = new UserProfileService().getById(workflow.getWorkflowActionId());
            String shortStructure = "-";
            if (userProfile != null) {
                shortStructure = userProfile.getStructure().getStructureShortName();
            }
            name = name + " (" + shortStructure + ")";
        }
        if (workflow.getWorkflowStr03() != null) {
            contentNoOrder = workflow.getWorkflowStr03().substring(workflow.getWorkflowStr03().length() - (contentFormat + 5));
        }
        if (workflow.getWorkflowStr04() != null) {
            bookNo = workflow.getWorkflowStr04();
        }
        switch (actionType) {
            case ('N'): {
                detail = name + " [สร้างหนังสือ] ลำดับเลขทะเบียน " + contentNoOrder + " เลขที่หนังสือ " + bookNo;
            }
            break;
            case ('R'): {
                int folderId = new WfContentService().getById(workflow.getLinkId2()).getWfContentFolderId();
                String folderType = new WfFolderService().getById(folderId).getWfContentType().getContentTypeName();
                String str01 = workflow.getWorkflowStr01();
                String registerAgain = (str01 != null && str01.equals("1")) ? "(รับเรื่องอีกครั้ง)" : "";
                detail = name + " [ลง" + folderType + registerAgain + "] ลำดับเลขทะเบียน " + contentNoOrder + " เลขที่หนังสือ " + bookNo;
            }
            break;
            case ('S'): {
                WorkflowToService workflowToService = new WorkflowToService();
                List<WorkflowTo> listWorkflowTo = workflowToService.listByWorkflowId(workflow.getId());
                String sendTo = "-";
                if (listWorkflowTo.size() > 0) {
                    sendTo = listWorkflowTo.get(0).getUserProfileFullName();
                    if (listWorkflowTo.size() > 1) {
                        for (int i = 1; i < listWorkflowTo.size(); i++) {
                            sendTo += ", " + listWorkflowTo.get(i).getUserProfileFullName();
                        }
                    }
                }
                WorkflowCcService workflowCcService = new WorkflowCcService();
                List<WorkflowCc> listWorkflowCc = workflowCcService.listByWorkflowId(workflow.getId());
                String sendCc = "";
                if (listWorkflowCc.size() > 0) {
                    sendCc = ", [สำเนา] " + listWorkflowCc.get(0).getUserProfileFullName();
                    if (listWorkflowCc.size() > 1) {
                        for (int i = 1; i < listWorkflowCc.size(); i++) {
                            sendCc += ", " + listWorkflowCc.get(i).getUserProfileFullName();
                        }
                    }
                }
                detail = name + " [ส่งหนังสือให้] " + sendTo + " ลำดับเลขทะเบียน " + contentNoOrder + " เลขที่หนังสือ " + bookNo + sendCc;
            }
            break;
            case ('A'): {
                WorkflowToService workflowToService = new WorkflowToService();
                List<WorkflowTo> listWorkflowTo = workflowToService.listByWorkflowId(workflow.getId());
                String sendTo = "-";
                if (listWorkflowTo.size() > 0) {
                    sendTo = listWorkflowTo.get(0).getUserProfileFullName();
                    if (listWorkflowTo.size() > 1) {
                        for (int i = 1; i < listWorkflowTo.size(); i++) {
                            sendTo += ", " + listWorkflowTo.get(i).getUserProfileFullName();
                        }
                    }
                }
                WorkflowCcService workflowCcService = new WorkflowCcService();
                List<WorkflowCc> listWorkflowCc = workflowCcService.listByWorkflowId(workflow.getId());
                String sendCc = "";
                if (listWorkflowCc.size() > 0) {
                    sendCc = ", [สำเนา] " + listWorkflowCc.get(0).getUserProfileFullName();
                    if (listWorkflowCc.size() > 1) {
                        for (int i = 1; i < listWorkflowCc.size(); i++) {
                            sendCc += ", " + listWorkflowCc.get(i).getUserProfileFullName();
                        }
                    }
                }
                detail = name + " [ส่งหนังสือ (ตอบกลับ) ให้] " + sendTo + " ลำดับเลขทะเบียน " + contentNoOrder + " เลขที่หนังสือ " + bookNo + sendCc;
            }
            break;
            case ('F'): {
                String action = (workflow.getLinkId3() == 0) ? "ทำเรื่องเสร็จ" : "ทำเรื่องเสร็จจัดเก็บ";
                detail = name + " [" + action + "] ลำดับเลขทะเบียน " + contentNoOrder + " เลขที่หนังสือ " + bookNo;
            }
            break;
            case ('C'): {
                detail = name + " [ยกเลิกหนังสือ] ลำดับเลขทะเบียน " + contentNoOrder + " เลขที่หนังสือ " + bookNo;
            }
            break;
            case ('D'): {
                String users = "";
                if (workflow.getWorkflowStr01() != null) {
                    users = workflow.getWorkflowStr01();
                }
                detail = name + " [ยกเลิกการส่ง] " + users + " ลำดับเลขทะเบียน " + contentNoOrder + " เลขที่หนังสือ " + bookNo;
            }
            break;
            case ('E'): {
                detail = name + " [ยกเลิกเรื่องเสร็จ] ลำดับเลขทะเบียน " + contentNoOrder + " เลขที่หนังสือ " + bookNo;
            }
            break;
            case ('B'): {
                detail = name + " [แก้ไขการยกเลิกหนังสือ] ลำดับเลขทะเบียน " + contentNoOrder + " เลขที่หนังสือ " + bookNo;
            }
            break;
            default:
                break;
        }
        return detail;
    }

    public List<Workflow> listByLinkIdGetRemoved(int linkId, String sort, String dir) {
        checkNotNull(linkId, "linkId by must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return workflowDaoImpl.listByLinkIdGetRemoved(linkId, sort, dir);
    }

    public Workflow getByLinkIdAndLinkId2(int linkId, int linkId2, String type) {//for "F" and "C" only (getOneByCriteria)
        return workflowDaoImpl.getByLinkIdAndLinkId2(linkId, linkId2, type);
    }

    public Workflow getByLinkIdAndLinkId3(int linkId, int linkId3, String type) {//for "F" and "C" only (getOneByCriteria)
        return workflowDaoImpl.getByLinkIdAndLinkId3(linkId, linkId3, type);
    }

    public List<Workflow> listByLinkIdAndLinkId3(int linkId, int linkId3, String type) {
        return workflowDaoImpl.listByLinkIdAndLinkId3(linkId, linkId3, type);
    }

    private String checkLengthData(String str, int length) {
        String result = null;
        if (str.length() > length) {
            result = str.substring(0, length);
        } else {
            result = str;
        }
        return result;
    }

    public WorkflowModel checkLengthField(WorkflowModel workflowPostModel) {
        workflowPostModel.setWorkflowActionType(checkLengthData(workflowPostModel.getWorkflowActionType(), 10));
        if (!workflowPostModel.getWorkflowActionName().equals("")) {
            workflowPostModel.setWorkflowActionName(checkLengthData(workflowPostModel.getWorkflowActionName(), 1000));
        }
        if (!workflowPostModel.getWorkflowActionPosition().equals("")) {
            workflowPostModel.setWorkflowActionPosition(checkLengthData(workflowPostModel.getWorkflowActionPosition(), 1000));
        }
        if (!workflowPostModel.getWorkflowDescription().equals("")) {
            workflowPostModel.setWorkflowDescription(checkLengthData(workflowPostModel.getWorkflowDescription(), 1000));
        }
        if (!workflowPostModel.getWorkflowNote().equals("")) {
            workflowPostModel.setWorkflowNote(checkLengthData(workflowPostModel.getWorkflowNote(), 1000));
        }
        if (!workflowPostModel.getWorkflowTitle().equals("")) {
            workflowPostModel.setWorkflowTitle(checkLengthData(workflowPostModel.getWorkflowTitle(), 1000));
        }
        if (!workflowPostModel.getWorkflowStr01().equals("")) {
            workflowPostModel.setWorkflowStr01(checkLengthData(workflowPostModel.getWorkflowStr01(), 1000));
        }
        if (!workflowPostModel.getWorkflowStr02().equals("")) {
            workflowPostModel.setWorkflowStr02(checkLengthData(workflowPostModel.getWorkflowStr02(), 1000));
        }
        if (!workflowPostModel.getWorkflowStr03().equals("")) {
            workflowPostModel.setWorkflowStr03(checkLengthData(workflowPostModel.getWorkflowStr03(), 1000));
        }
        if (!workflowPostModel.getWorkflowStr04().equals("")) {
            workflowPostModel.setWorkflowStr04(checkLengthData(workflowPostModel.getWorkflowStr04(), 1000));
        }
        return workflowPostModel;
    }

    public WorkflowModel_groupFlow getWorkflowImage(List<Workflow> listWorkflow) {
        WorkflowModel_groupFlow workflowModel_groupFlow = new WorkflowModel_groupFlow();
        List<WorkflowModel_groupNodeFlow> listNode = new ArrayList<WorkflowModel_groupNodeFlow>();
        List<WorkflowModel_groupLinkFlow> listLink = new ArrayList<WorkflowModel_groupLinkFlow>();
        WorkflowModel_groupNodeFlow node = new WorkflowModel_groupNodeFlow();
        WorkflowModel_groupLinkFlow link = new WorkflowModel_groupLinkFlow();

        String keyAddedNode = "";

        HashMap hashAddedNode = new HashMap();

        WorkflowToService workflowToService = new WorkflowToService();
        WorkflowCcService workflowCcService = new WorkflowCcService();

        HashMap hashImg = new HashMap();
        hashImg.put("0", "assets/icons/flow_user.png");
        hashImg.put("1", "assets/icons/flow_user_check.png");
        hashImg.put("N", "assets/icons/flow_n.png");
        hashImg.put("R", "assets/icons/flow_r.png");
        hashImg.put("S", "assets/icons/flow_s.png");
        hashImg.put("A", "assets/icons/flow_a.png");
        hashImg.put("F", "assets/icons/flow_f.png");
        hashImg.put("C", "assets/icons/flow_c.png");
        hashImg.put("D", "assets/icons/flow_d.png");
        hashImg.put("E", "assets/icons/flow_e.png");
        hashImg.put("B", "assets/icons/flow_b.png");

        String br = "\r\n";

        if (!listWorkflow.isEmpty()) {
            List<Workflow> listAdded = new ArrayList();
            int num = 0;
            for (Workflow workflow : listWorkflow) {
                listAdded.add(workflow);
                num++;

                String actionType = workflow.getWorkflowActionType();
                String date = Common.localDateTimeToString4(workflow.getWorkflowDate01());
                String createdDate = Common.localDateTimeToString2(workflow.getCreatedDate());
                String dateTime = createdDate.replace(" ", br);
                boolean hardCopy;
                String hardCopy_str;
                if (workflow.getWorkflowStr02() == null) {
                    hardCopy = false;
                    hardCopy_str = "รอรับเอกสารตัวจริง";
                } else {
                    hardCopy = true;
                    hardCopy_str = "ได้รับเอกสารตัวจริงแล้ว " + workflow.getWorkflowStr02();
                }

                String caption = "[" + createdDate + "]" + br
                        + "เลขทะเบียน: " + workflow.getWorkflowStr03() + br
                        + "เลขที่หนังสือ: " + workflow.getWorkflowStr04() + br
                        + "ลงวันที่: " + date + br
                        + "รายละเอียด: " + workflow.getWorkflowNote() + br
                        + hardCopy_str;

                if (actionType.equals("N") || actionType.equals("R")
                        || actionType.equals("F") || actionType.equals("C")
                        || actionType.equals("D") || actionType.equals("E") || actionType.equals("B")) {

                    String key = String.valueOf(workflow.getLinkId2()) + "+" + String.valueOf(workflow.getWorkflowActionId()) + ",0";
                    keyAddedNode = (String) hashAddedNode.get(key);
                    if (keyAddedNode == null) {
                        String structure = new UserProfileService().getById(workflow.getWorkflowActionId()).getStructure().getStructureName();
                        node = new WorkflowModel_groupNodeFlow();
                        node.setCategory("");
                        node.setKey(key);
                        node.setName(workflow.getWorkflowActionName() + br + "(" + structure + ")");
                        node.setText(hardCopy_str);
                        node.setSource((hardCopy) ? (String) hashImg.get("1") : (String) hashImg.get("0"));
                        listNode.add(node);
                        hashAddedNode.put(key, "0");
                    }

                    String key_comment = num + actionType;
                    node = new WorkflowModel_groupNodeFlow();
                    node.setCategory("Comment");
                    node.setKey(key_comment);
//                    node.setName(Integer.toString(num) + ". " + getAction(actionType.charAt(0)));
                    node.setName(num + ". " + getAction(workflow));
                    node.setText(caption);
                    node.setSource((String) hashImg.get(actionType));
                    listNode.add(node);

                    link = new WorkflowModel_groupLinkFlow();
                    link.setCategory("Comment");
                    link.setFrom(key_comment);
                    link.setTo(key);
                    link.setText(dateTime);
                    link.setCaption("");
                    listLink.add(link);

                } else if (actionType.equals("S") || actionType.equals("A")) {
                    String action = (actionType.equals("S")) ? "ส่งหนังสือ" : "ตอบกลับ";
                    //String tmpKey2 = "";//*******
                    List<String> listCanceledName = new ArrayList();
                    List<Workflow> listWorkFlowD = listByLinkIdAndLinkId3(workflow.getLinkId(), workflow.getId(), "D");
                    if (!listWorkFlowD.isEmpty()) {
                        for (Workflow workflowD : listWorkFlowD) {
                            String[] tmp = workflowD.getWorkflowStr01().split(", ");
                            for (int i = 0; i < tmp.length; i++) {
                                listCanceledName.add(tmp[i]);
                            }
                        }
                    }

                    List<WorkflowTo> listWorkflowTo = workflowToService.listByWorkflowId(workflow.getId());
                    if (!listWorkflowTo.isEmpty()) {
                        int sendCount = 0;
                        for (WorkflowTo workflowTo : listWorkflowTo) {
                            sendCount++;
                            String key = String.valueOf(workflow.getLinkId2()) + "+" + String.valueOf(workflowTo.getUserProfileId()) + "," + String.valueOf(workflowTo.getStructureId());
                            int linkId2 = workflowTo.getWorkflow().getLinkId2();
                            int workflowId = workflowTo.getWorkflow().getId();
                            String key_from = String.valueOf(linkId2) + "+" + String.valueOf(findActionId(listAdded, workflowId)) + ",0";
                            keyAddedNode = (String) hashAddedNode.get(key);
                            boolean isCanceled = false;
                            if (!listCanceledName.isEmpty()) {
                                for (String canceledName : listCanceledName) {
                                    if (canceledName.equalsIgnoreCase(workflowTo.getUserProfileFullName())) {
                                        isCanceled = true;
                                    }
                                }

                            }
                            if (keyAddedNode == null) {
                                node = new WorkflowModel_groupNodeFlow();
                                node.setCategory("");
                                node.setKey(key);
                                node.setName(workflowTo.getUserProfileFullName());
                                node.setText("");
                                node.setSource((String) hashImg.get("0"));
                                node.setIsStrikethrough(isCanceled);
                                listNode.add(node);
                                hashAddedNode.put(key, "0");
                            }

                            link = new WorkflowModel_groupLinkFlow();
                            link.setCategory("");
                            link.setFrom(key_from);
                            link.setTo(key);
                            link.setText(num + "." + sendCount + " " + action + br + date);
                            link.setCaption("");
                            listLink.add(link);
                        }

                        List<WorkflowCc> listWorkflowCc = workflowCcService.listByWorkflowId(workflow.getId());
                        if (!listWorkflowCc.isEmpty()) {
                            for (WorkflowCc workflowCc : listWorkflowCc) {
                                String key = String.valueOf(workflow.getLinkId2()) + "+" + String.valueOf(workflowCc.getUserProfileId()) + "," + String.valueOf(workflowCc.getStructureId());
                                int linkId2 = workflowCc.getWorkflow().getLinkId2();
                                int workflowId = workflowCc.getWorkflow().getId();
                                String key_from = String.valueOf(linkId2) + "+" + String.valueOf(findActionId(listAdded, workflowId)) + ",0";
                                keyAddedNode = (String) hashAddedNode.get(key);
                                if (keyAddedNode == null) {
                                    node = new WorkflowModel_groupNodeFlow();
                                    node.setCategory("");
                                    node.setKey(key);
                                    node.setName(workflowCc.getUserProfileFullName());
                                    node.setText("");
                                    node.setSource((String) hashImg.get("0"));
                                    listNode.add(node);
                                    hashAddedNode.put(key, "0");
                                }

                                link = new WorkflowModel_groupLinkFlow();
                                link.setCategory("");
                                link.setFrom(key_from);
                                link.setTo(key);
                                link.setText(dateTime);
                                link.setCaption("");
                                listLink.add(link);
                            }
                        }
                    }
                }
            }
        }

        workflowModel_groupFlow.setTopic("");
        workflowModel_groupFlow.setWorkflowModel_groupNodeFlow(listNode);
        workflowModel_groupFlow.setWorkflowModel_groupLinkFlow(listLink);
        return workflowModel_groupFlow;
    }

    private String getAction(Workflow workflow) {
        char actiontype = workflow.getWorkflowActionType().charAt(0);
        String action;
        switch (actiontype) {
            case 'N':
                action = "สร้างหนังสือ";
                break;
            case 'R':
                String str01 = workflow.getWorkflowStr01();
                action = (str01 != null && str01.equals("1")) ? "ลงทะเบียน" + "\r\n" + "(รับเรื่องอีกครั้ง)" : "ลงทะเบียน";
                break;
            case 'S':
                action = "ส่งหนังสือให้";
                break;
            case 'A':
                action = "ตอบกลับหนังสือให้";
                break;
            case 'F':
//                action = (workflow.getLinkId3() == 0) ? "ทำเรื่องเสร็จ" : "ทำเรื่องเสร็จจัดเก็บ";
                action = "ทำเรื่องเสร็จ";
                break;
            case 'C':
                action = "ยกเลิกหนังสือ";
                break;
            case 'D':
                action = "ยกเลิกการส่ง";
                break;
            case 'E':
                action = "ยกเลิกเรื่องเสร็จ";
                break;
            case 'B':
                action = "แก้ไขการยกเลิกหนังสือ";
                break;
            default:
                action = "-";
                break;
        }
        return action;
    }

    private String findActionId(List<Workflow> list, int id) {
        String userId = "";
        for (Workflow wf : list) {
            if (wf.getId() == id) {
                userId = String.valueOf(wf.getWorkflowActionId());
                break;
            }
        }
        return userId;
    }

}
