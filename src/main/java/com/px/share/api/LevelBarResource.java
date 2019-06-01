package com.px.share.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.LevelBarModel;
import com.px.share.model.LevelModel;
import com.px.share.model.VersionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
@Api(value = "LevelBar ลำดับการเข้าถึงข้อมูล")
@Path("v1/levelBars")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class LevelBarResource {

    private static final Logger LOG = Logger.getLogger(LevelBarResource.class.getName());

    @ApiOperation(
            value = "Method for update LevelBar.",
            notes = "แก้ไขลำดับการเข้าถึงข้อมูล",
            response = LevelBarModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "LevelBar updeted by id success.")
        ,@ApiResponse(code = 404, message = "LevelBar by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(
            @BeanParam VersionModel versionModel,
            LevelBarModel levelBarModel
    ) {
        LOG.debug("update...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("success", true);
        LevelBarModel newlevelBarModel = new LevelBarModel();
        ArrayList<LevelModel> listLevel = new ArrayList();
        ArrayList<String> listLevelName = new ArrayList();
        LevelModel levelModel = new LevelModel();
        levelModel = levelBarModel.getNewLevel();
        if (levelModel.getShortSize() > 0) {
            if (levelModel.getLevelName().length() > levelModel.getShortSize()) {
                try {
                    levelModel.setShortName(levelModel.getLevelName().substring(0, levelModel.getShortSize() - 3) + "...");
                } catch (Exception eShort) {
                    LOG.error("Exception eShort= " + eShort.getMessage());
                }
            } else {
                levelModel.setShortName(levelModel.getLevelName());
            }
        }
        try {
            if (levelBarModel.getListLevelName() == null) {
                levelBarModel.setListLevelName(listLevelName);
            }
            if (levelBarModel.getListLevelName().isEmpty()) {
                listLevelName.add(levelModel.getLevelName());
                listLevel.add(levelModel);
                newlevelBarModel.setListLevel(listLevel);
                newlevelBarModel.setListLevelName(listLevelName);
            } else {
                int nameIndex = levelBarModel.getListLevelName().indexOf(levelModel.getLevelName());
                if (nameIndex != -1) {
                    for (int i = 0; i <= nameIndex; i++) {
                        try {
                            listLevelName.add(levelBarModel.getListLevelName().get(i));
                            listLevel.add(levelBarModel.getListLevel().get(i));
                        } catch (Exception eee) {
                            LOG.error("Exception eee= " + eee.getMessage());
                        }
                    }
                } else {
                    listLevelName = levelBarModel.getListLevelName();
                    listLevel = levelBarModel.getListLevel();
                    listLevelName.add(levelModel.getLevelName());
                    listLevel.add(levelModel);
                }
                newlevelBarModel.setListLevel(listLevel);
                newlevelBarModel.setListLevelName(listLevelName);
            }
            newlevelBarModel.setNewLevel(levelModel);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
        }
        responseData.put("data", newlevelBarModel);
        responseData.put("message", "");
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
