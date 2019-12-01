/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.mwp.entity.PrivateGroup;
import com.px.mwp.entity.PrivateGroupUser;
import com.px.mwp.model.PrivateGroupModel;
import com.px.mwp.model.PrivateGroupUserModel;
import com.px.mwp.model.UserStructureModel;
import com.px.mwp.service.PrivateGroupService;
import com.px.mwp.service.PrivateGroupUserService;
import com.px.share.model.ListOptionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 *
 * @author Oat
 */
@Api(value = "privateGroup กลุ่มส่วนตัว")
@Path("v1/privateGroup")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PrivateGroupResource {

    private static final Logger LOG = Logger.getLogger(PrivateGroupResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create PrivateGroup",
            notes = "สร้างกลุ่มส่วนตัว",
            response = PrivateGroupModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "PrivateGroup created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            PrivateGroupModel privateGroupModel
    ) {
        LOG.info("create privateGroup...");
        Gson gs = new GsonBuilder()
                .setVersion(privateGroupModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
//        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            PrivateGroup privateGroup = new PrivateGroup();
            privateGroup.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            //privateGroup.setCreatedBy(userID);
            privateGroup.setOwnerId(privateGroupModel.getOwnerId());
            privateGroup.setGroupName(privateGroupModel.getGroupName());
            privateGroup.setGroupType(privateGroupModel.getGroupType());
            PrivateGroupService privateGroupService = new PrivateGroupService();
            privateGroup = privateGroupService.create(privateGroup);
            privateGroup.setOrderNo(privateGroup.getId());
            privateGroup = privateGroupService.update(privateGroup);

//            UserStructureModel[] listUser = privateGroupModel.getListUser();
//            if (listUser != null) {
//                PrivateGroupUserService pguService = new PrivateGroupUserService();
//                for (int i = 0; i < listUser.length; i++) {
//                    PrivateGroupUser pgu = new PrivateGroupUser();
//                    pgu.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//                    pgu.setPrivateGroupId(privateGroupModel.getId());
//                    pgu.setUserId(listUser[i].getId());
//                    pgu.setUserName(listUser[i].getName());
//                    pgu.setUserType(listUser[i].getType());
//
//                    pgu = pguService.create(pgu);
//                    pgu.setOrderNo(pgu.getId());
//                    pgu = pguService.update(pgu);
//                }
//
//            }
            responseData.put("data", privateGroupService.tranformToModel(privateGroup));
            responseData.put("message", "PrivateGroup created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create PrivateGroupUsers",
            notes = "สร้างผู้ใช้ของกลุ่มส่วนตัว",
            response = PrivateGroupModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "PrivateGroupUser created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/groupUser")
    public Response createGroupUsers(
            List<PrivateGroupUserModel> listPguModel
    ) {
        LOG.info("create privateGroupUser...");
        Gson gs = new GsonBuilder()
                .setVersion(listPguModel.get(0).getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            PrivateGroupUserService pguService = new PrivateGroupUserService();
            List<PrivateGroupUserModel> tmp = new ArrayList();
            for (PrivateGroupUserModel pguModel : listPguModel) {
                PrivateGroupUser pgu = new PrivateGroupUser();
                pgu.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                pgu.setPrivateGroupId(pguModel.getPrivateGroupId());
                pgu.setUserId(pguModel.getUserId());
                pgu.setUserName(pguModel.getUserName());
                pgu.setUserType(pguModel.getType());
                if (pguModel.getType() == 2) {
                    pgu.setEmail(pguModel.getEmail());
                }

                pgu = pguService.create(pgu);
                pgu.setOrderNo(pgu.getId());
                pgu = pguService.update(pgu);
                tmp.add(pguService.tranformToModel(pgu));
            }
            responseData.put("data", tmp);
            responseData.put("message", "PrivateGroupUser created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ลบ privateGroup",
            notes = "ลบ privateGroup",
            response = PrivateGroupModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "privateGroup deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "privateGroup by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response _remove(
            @ApiParam(name = "id", value = "รหัส privateGroup", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("remove...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "privateGroup by id not found in the database.");
        try {
            PrivateGroupService privateGroupService = new PrivateGroupService();
            PrivateGroupUserService pguService = new PrivateGroupUserService();
            List<PrivateGroupUser> listPgu = pguService.listByPrivateGroupId(id, "orderNo", "asc");
            for (PrivateGroupUser pgu : listPgu) {
                pguService.remove(pgu.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
            }
            PrivateGroup privateGroup = privateGroupService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (privateGroup != null) {
                status = Response.Status.OK;
                responseData.put("data", true);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ลบ privateGroupUser",
            notes = "ลบ privateGroupUser",
            response = PrivateGroupUserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "privateGroupUser deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "privateGroupUser by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/groupUser/{id}")
    public Response _removeGroupUser(
            @ApiParam(name = "id", value = "รหัส privateGroupUser", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("remove...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "privateGroupUser by id not found in the database.");
        try {
            PrivateGroupUserService pguService = new PrivateGroupUserService();
            PrivateGroupUser pgu = pguService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (pgu != null) {
                status = Response.Status.OK;
                responseData.put("data", true);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

//    @ApiOperation(
//            value = "ลบ privateGroupUser",
//            notes = "ลบ privateGroupUser",
//            response = PrivateGroupUserModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "privateGroupUser deleted by id success.")
//        ,
//        @ApiResponse(code = 404, message = "privateGroupUser by id not found in the database.")
//        ,
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @PUT
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/remove")
//    public Response remove2(
//            int[] id
//    ) {
//        LOG.info("remove2...");
//        Gson gs = new GsonBuilder()
//                .setVersion(1.0)
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "privateGroupUser by id not found in the database.");
//        try {
//            PrivateGroupUserService pguService = new PrivateGroupUserService();
//            for (int i = 0; i < id.length; i++) {
//                pguService.remove(id[i], Integer.parseInt(httpHeaders.getHeaderString("userID")));
//            }
//            status = Response.Status.OK;
//            responseData.put("data", null);
//            responseData.put("message", "");
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
    @ApiOperation(
            value = "ลบถาวร privateGroup",
            notes = "ลบถาวร privateGroup",
            response = PrivateGroupModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "privateGroup deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "privateGroup by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/delete/{id}")
    public Response delete(
            @ApiParam(name = "id", value = "รหัส privateGroup", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("delete...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "privateGroup by id not found in the database.");
        try {
            PrivateGroupService privateGroupService = new PrivateGroupService();
            PrivateGroup privateGroup = privateGroupService.getById(id);
            if (privateGroup != null) {
                PrivateGroupUserService pguService = new PrivateGroupUserService();
                List<PrivateGroupUser> listPgu = pguService.listByPrivateGroupId(id, "orderNo", "asc");
                for (PrivateGroupUser pgu : listPgu) {
                    pguService.delete(pgu);
                }
                privateGroupService.delete(privateGroup);
                status = Response.Status.OK;
                responseData.put("data", true);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ลบถาวร privateGroupUser",
            notes = "ลบถาวร privateGroupUser",
            response = PrivateGroupUserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "privateGroupUser deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "privateGroupUser by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/groupUser/delete/{id}")
    public Response deleteGroupUser(
            @ApiParam(name = "id", value = "รหัส privateGroupUser", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("delete...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "privateGroupUser by id not found in the database.");
        try {
            PrivateGroupUserService pguService = new PrivateGroupUserService();
            PrivateGroupUser pgu = pguService.getById(id);
            if (pgu != null) {
                pguService.delete(pgu);
                status = Response.Status.OK;
                responseData.put("data", true);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "list privateGroup by ownerID",
            notes = "list privateGroup by ownerID",
            responseContainer = "List",
            response = PrivateGroupModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "PrivateGroup list success.")
        ,
        @ApiResponse(code = 404, message = "PrivateGroup list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/list/{groupType}")
    public Response listAll( //@BeanParam ListOptionModel listOptionModel
            @ApiParam(name = "groupType", value = "ประเถท (0=privategroup, 1=favourite)", required = true)
            @PathParam("groupType") int groupType
    ) {
        LOG.info("listByOwnerId...");
        Gson gs = new GsonBuilder()
                //                .setVersion(listOptionModel.getVersion())
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "privateGroup by id not found in the database.");
        try {
            PrivateGroupService privateGroupService = new PrivateGroupService();
//            List<PrivateGroup> listPrivateGroup = privateGroupService.listByOwnerId(userID, listOptionModel.getSort(), listOptionModel.getDir());
            List<PrivateGroup> listPrivateGroup = privateGroupService.listByOwnerIdAndType(Integer.parseInt(httpHeaders.getHeaderString("userID")), groupType);
            ArrayList<PrivateGroupModel> listPrivateGroupModel = new ArrayList();
            if (!listPrivateGroup.isEmpty()) {
                PrivateGroupUserService pguService = new PrivateGroupUserService();
                listPrivateGroup.forEach(privategroup -> {
//                    List<PrivateGroupUser> listPgu = pguService.listByPrivateGroupId(privategroup.getId(), listOptionModel.getSort(), listOptionModel.getDir());
                    List<PrivateGroupUser> listPgu = pguService.listByPrivateGroupId(privategroup.getId(), "orderNo", "desc");

                    if (!listPgu.isEmpty()) {
                        ArrayList<PrivateGroupUserModel> listPguModel = new ArrayList();
                        listPgu.forEach(pgu -> {
                            listPguModel.add(pguService.tranformToModel(pgu));
                        });
                        listPrivateGroupModel.add(privateGroupService.tranformToModel(privategroup, listPguModel));
                    } else {
                        listPrivateGroupModel.add(privateGroupService.tranformToModel(privategroup));
                    }
                });

                listPrivateGroupModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listPrivateGroupModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            //ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "get privateGroup by id",
            notes = "get privateGroup by id",
            response = PrivateGroupModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "PrivateGroup success.")
        ,
        @ApiResponse(code = 404, message = "PrivateGroup not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @ApiParam(name = "id", value = "รหัส privateGroup", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("getById...");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "privateGroup by id not found in the database.");
        try {
            PrivateGroupService privateGroupService = new PrivateGroupService();
            PrivateGroup privateGroup = privateGroupService.getByIdNotRemoved(id);
            if (privateGroup != null) {
                status = Response.Status.OK;
                responseData.put("data", privateGroupService.tranformToModel(privateGroup));
                responseData.put("message", "");
            }
            responseData.put("success", true);

        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "update privateGroup",
            notes = "update privateGroup",
            response = PrivateGroupUserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "PrivateGroup success.")
        ,
        @ApiResponse(code = 404, message = "PrivateGroup not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(
            PrivateGroupModel privateGroupModel
    ) {
        LOG.info("update...");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "privateGroup by id not found in the database.");
        try {
            PrivateGroupService privateGroupervice = new PrivateGroupService();
            PrivateGroup privateGroup = privateGroupervice.getByIdNotRemoved(privateGroupModel.getId());
            if (privateGroup != null) {
                privateGroup.setGroupName(privateGroupModel.getGroupName());

                privateGroup = privateGroupervice.update(privateGroup);
                status = Response.Status.OK;
                responseData.put("data", privateGroupervice.tranformToModel(privateGroup));
                responseData.put("message", "");
            }
            responseData.put("success", true);

        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "get privateGroupUser by id",
            notes = "get privateGroupUser by id",
            response = PrivateGroupUserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "PrivateGroupUser success.")
        ,
        @ApiResponse(code = 404, message = "PrivateGroupUser not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/groupUser/{id}")
    public Response getGroupUserById(
            @ApiParam(name = "id", value = "รหัส privateGroupUser", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("getUserById...");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "privateGroupUser by id not found in the database.");
        try {
            PrivateGroupUserService pguService = new PrivateGroupUserService();
            PrivateGroupUser pgu = pguService.getByIdNotRemoved(id);
            if (pgu != null) {
                status = Response.Status.OK;
                responseData.put("data", pguService.tranformToModel(pgu));
                responseData.put("message", "");
            }
            responseData.put("success", true);

        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "update privateGroupUser",
            notes = "update privateGroupUser",
            response = PrivateGroupUserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "PrivateGroupUser success.")
        ,
        @ApiResponse(code = 404, message = "PrivateGroupUser not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/groupUser")
    public Response updateGroupUser(
            PrivateGroupUserModel pguModel
    ) {
        LOG.info("updateGroupUser...");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "privateGroupUser by id not found in the database.");
        try {
            PrivateGroupUserService pguService = new PrivateGroupUserService();
            PrivateGroupUser pgu = pguService.getByIdNotRemoved(pguModel.getId());
            if (pgu != null) {
                pgu.setUserName(pguModel.getUserName());
                pgu.setEmail(pguModel.getEmail());

                pgu = pguService.update(pgu);
                status = Response.Status.OK;
                responseData.put("data", pguService.tranformToModel(pgu));
                responseData.put("message", "");
            }
            responseData.put("success", true);

        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

}
