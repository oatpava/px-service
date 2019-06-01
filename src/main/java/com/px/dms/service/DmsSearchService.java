/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.gson.Gson;
import com.px.admin.entity.UserProfile;
import com.px.admin.entity.WordBriefDetail;
import com.px.admin.service.UserProfileService;
import com.px.admin.service.WordBriefDetailService;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.service.SubmoduleAuthService;
import com.px.authority.service.SubmoduleUserAuthService;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.DmsFolder;
import com.px.dms.model.DmsSearchFormModel;
import com.px.share.util.Common;
import com.px.dms.model.DmsSearchModel;
import com.px.dms.model.DmsSearchResultModel;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import com.px.share.util.Elasticsearch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

/**
 *
 * @author top
 */
public class DmsSearchService {

    private static final Logger LOG = Logger.getLogger(DmsSearchService.class.getName());

    final private String IndexName = "praxticol8";
    final private String IndexType = "dmsSearchD1";
    final private String searchField = "searchContent";

    public DmsSearchService() {
    }

    public boolean setup() {
        boolean result = false;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            elasticsearch.createIndex(this.IndexName);
            elasticsearch.setTextFieldThaiAnalyzer(this.IndexName, this.IndexType, this.searchField);

            result = true;

            elasticsearch.closeTransportClient();
        }

        return result;
    }

    public boolean isIndexExist() {
        boolean result = false;
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            result = elasticsearch.isIndexExist(this.IndexName);
            elasticsearch.closeTransportClient();
        }

        return result;
    }

    public DmsSearchModel addData(DmsSearchModel dmsSeaechResoreModel) {
        checkNotNull(dmsSeaechResoreModel, "DmsSeaechResoreModel must not be null");
        DmsFolderService dmsFolderService = new DmsFolderService();
        String folderP = dmsFolderService.getById(dmsSeaechResoreModel.getDocumentFolderId()).getDmsFolderParentKey();
        String[] parts = folderP.split("฿");
        List<String> temp = new ArrayList<String>();
        for (int i = 1; i < parts.length; i++) {
            temp.add(parts[i]);

        }
        dmsSeaechResoreModel.setParentKey(temp);

        DmsSearchModel result = null;
        IndexResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            try {

                boolean setCreatedDate = true;
                String elasticsearchData = generateElasticsearchData(dmsSeaechResoreModel, setCreatedDate);
                String id = null;
                response = elasticsearch.addData(this.IndexName, this.IndexType, id, elasticsearchData);
                id = response.getId();
                GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);

                if (getReponse.getSourceAsString() != null) {
                    result = new Gson().fromJson(getReponse.getSourceAsString(), DmsSearchModel.class);
                    result.setDmsSearchId(id);
                }

            } catch (Exception e) {
            }
            elasticsearch.closeTransportClient();
        }

        return result;
    }

    public DmsSearchModel addDataFolder(DmsSearchModel dmsSeaechResoreModel, String folderParentKey) {
        checkNotNull(dmsSeaechResoreModel, "DmsSeaechResoreModel must not be null");
//        DmsFolderService dmsFolderService = new DmsFolderService();
//        String folderP = dmsFolderService.getById(dmsSeaechResoreModel.getDocumentFolderId()).getDmsFolderParentKey();
//        dmsSeaechResoreModel.setParentKey(folderP);

        String[] parts = folderParentKey.split("฿");
        List<String> temp = new ArrayList<String>();
        for (int i = 1; i < parts.length; i++) {
            temp.add(parts[i]);

        }
        dmsSeaechResoreModel.setParentKey(temp);

        DmsSearchModel result = null;
        IndexResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            try {

                boolean setCreatedDate = true;
                String elasticsearchData = generateElasticsearchData(dmsSeaechResoreModel, setCreatedDate);
                String id = null;
                response = elasticsearch.addData(this.IndexName, this.IndexType, id, elasticsearchData);
                id = response.getId();
                GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);

                if (getReponse.getSourceAsString() != null) {
                    result = new Gson().fromJson(getReponse.getSourceAsString(), DmsSearchModel.class);
                    result.setDmsSearchId(id);
                }

            } catch (Exception e) {
            }

            elasticsearch.closeTransportClient();
        }

        return result;
    }

    private String generateElasticsearchData(DmsSearchModel dmsSeaechResoreModel, boolean setCreatedDate) {
        String result = "";
        UserProfileService userProfileService = new UserProfileService();
        UserProfile userProfile = new UserProfile();
        try {
            String searchContent = "";
            XContentBuilder builder = jsonBuilder();
            builder.startObject();

            builder.field("removeBy", dmsSeaechResoreModel.getRemovedBy());

            builder.field("id", dmsSeaechResoreModel.getId());

            builder.field("type", dmsSeaechResoreModel.getType());//C-> ตู�? D-> ลิ�?�?�?ั�? F-> �?�?�?ม DOC-> เอ�?สาร

            builder.field("folderName", dmsSeaechResoreModel.getFolderName());
            searchContent += " " + dmsSeaechResoreModel.getFolderName();

            builder.field("folderDescription", dmsSeaechResoreModel.getFolderDescription());
            searchContent += " " + dmsSeaechResoreModel.getFolderDescription();

            if (setCreatedDate) {
                builder.field("createdDate", new Date());
            }
//             userProfile = userProfileService.getById(dmsSeaechResoreModel.getCreatedBy());
            if (dmsSeaechResoreModel.getCreatedBy() > 0) {
                userProfile = userProfileService.getById(dmsSeaechResoreModel.getCreatedBy());
                builder.field("createName", userProfile.getUserProfileFullName());
                searchContent += " " + userProfile.getUserProfileFullName();
            } else {
                builder.field("createName", "");
            }

            builder.field("updatedDate", new Date());

            builder.field("documentFolderId", dmsSeaechResoreModel.getDocumentFolderId());
//            searchContent += " " + dmsSeaechResoreModel.getDocumentFolderId();

            if (dmsSeaechResoreModel.getUpdatedBy() > 0) {
                userProfile = userProfileService.getById(dmsSeaechResoreModel.getUpdatedBy());
                builder.field("updateName", userProfile.getUserProfileFullName());
                searchContent += " " + userProfile.getUserProfileFullName();
            } else {
                builder.field("updateName", "");
            }
            builder.field("removeBy", dmsSeaechResoreModel.getRemovedBy());
            builder.field("documentPublicDate", dateThaiToLocalDateTime(dmsSeaechResoreModel.getDocumentPublicDate()));
            searchContent += " " + dmsSeaechResoreModel.getDocumentPublicDate();
            builder.field("documentExpireDate", dateThaiToLocalDateTime(dmsSeaechResoreModel.getDocumentExpireDate()));
            searchContent += " " + dmsSeaechResoreModel.getDocumentExpireDate();
            builder.field("documentDate01", dateThaiToLocalDateTime(dmsSeaechResoreModel.getDocumentDate01()));
            searchContent += " " + dmsSeaechResoreModel.getDocumentDate01();
            builder.field("documentDate02", dateThaiToLocalDateTime(dmsSeaechResoreModel.getDocumentDate02()));
            searchContent += " " + dmsSeaechResoreModel.getDocumentDate02();
            builder.field("documentDate03", dateThaiToLocalDateTime(dmsSeaechResoreModel.getDocumentDate03()));
            searchContent += " " + dmsSeaechResoreModel.getDocumentDate03();
            builder.field("documentDate04", dateThaiToLocalDateTime(dmsSeaechResoreModel.getDocumentDate04()));
            searchContent += " " + dmsSeaechResoreModel.getDocumentDate04();
            builder.field("documentName", dmsSeaechResoreModel.getDocumentName());
            searchContent += " " + dmsSeaechResoreModel.getDocumentName();

            builder.field("documentPublicStatus", dmsSeaechResoreModel.getDocumentPublicStatus());

            DmsFolderService dmsFolderService = new DmsFolderService();
            String fullPath = dmsFolderService.getFullPathName(dmsSeaechResoreModel.getDocumentFolderId());
            builder.field("fullPathName", fullPath);

            builder.field("documentFloat01", dmsSeaechResoreModel.getDocumentFloat01());
            searchContent += " " + dmsSeaechResoreModel.getDocumentFloat01();

            builder.field("documentFloat02", dmsSeaechResoreModel.getDocumentFloat02());
            searchContent += " " + dmsSeaechResoreModel.getDocumentFloat02();

            builder.field("documentVarchar01", dmsSeaechResoreModel.getDocumentVarchar01());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar01();

            builder.field("documentVarchar02", dmsSeaechResoreModel.getDocumentVarchar02());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar02();

            builder.field("documentVarchar03", dmsSeaechResoreModel.getDocumentVarchar03());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar03();

            builder.field("documentVarchar04", dmsSeaechResoreModel.getDocumentVarchar04());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar04();

            builder.field("documentVarchar05", dmsSeaechResoreModel.getDocumentVarchar05());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar05();

            builder.field("documentVarchar06", dmsSeaechResoreModel.getDocumentVarchar06());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar06();

            builder.field("documentVarchar07", dmsSeaechResoreModel.getDocumentVarchar07());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar07();

            builder.field("documentVarchar08", dmsSeaechResoreModel.getDocumentVarchar08());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar08();

            builder.field("documentVarchar09", dmsSeaechResoreModel.getDocumentVarchar09());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar09();

            builder.field("documentVarchar10", dmsSeaechResoreModel.getDocumentVarchar10());
            searchContent += " " + dmsSeaechResoreModel.getDocumentVarchar10();

            builder.field("documentText01", dmsSeaechResoreModel.getDocumentText01());
            searchContent += " " + dmsSeaechResoreModel.getDocumentText01();

            builder.field("documentText02", dmsSeaechResoreModel.getDocumentText02());
            searchContent += " " + dmsSeaechResoreModel.getDocumentText02();

            builder.field("documentText03", dmsSeaechResoreModel.getDocumentText03());
            searchContent += " " + dmsSeaechResoreModel.getDocumentText03();

            builder.field("documentText04", dmsSeaechResoreModel.getDocumentText04());
            searchContent += " " + dmsSeaechResoreModel.getDocumentText04();

            builder.field("documentText05", dmsSeaechResoreModel.getDocumentText05());
            searchContent += " " + dmsSeaechResoreModel.getDocumentText05();

            builder.field("documentInt01", dmsSeaechResoreModel.getDocumentInt01());
            searchContent += " " + dmsSeaechResoreModel.getDocumentInt01();

            builder.field("documentInt02", dmsSeaechResoreModel.getDocumentInt02());
            searchContent += " " + dmsSeaechResoreModel.getDocumentInt02();

            builder.field("documentInt03", dmsSeaechResoreModel.getDocumentInt03());
            searchContent += " " + dmsSeaechResoreModel.getDocumentInt03();

            builder.field("documentInt04", dmsSeaechResoreModel.getDocumentInt04());
            searchContent += " " + dmsSeaechResoreModel.getDocumentInt04();

            builder.field("dmsDocumentIntComma", dmsSeaechResoreModel.getDmsDocumentIntComma());
            searchContent += " " + dmsSeaechResoreModel.getDmsDocumentIntComma();

            builder.field("dmsDocumentSec", dmsSeaechResoreModel.getDmsDocumentSec());

            builder.field("wfTypeId", dmsSeaechResoreModel.getWfTypeId());

            builder.field("flowId", dmsSeaechResoreModel.getFlowId());

            builder.field("documentTypeId", dmsSeaechResoreModel.getDocumentTypeId());

//             parentKey
//            builder.field("parentKey", dmsSeaechResoreModel.getParentKey());

            builder.startArray("parentKey");
            if (dmsSeaechResoreModel.getParentKey() != null) {
                if (dmsSeaechResoreModel.getParentKey().size() > 0) {
                    for (String tmp : dmsSeaechResoreModel.getParentKey()) {
                        builder.value(tmp);
//                        searchContent += " " + tmp;
                    }
                }
            }
            builder.endArray();

//            builder.field("documentFolderId", dmsSeaechResoreModel.getDocumentFolderId());
            builder.startArray("fileAttachName");
            if (dmsSeaechResoreModel.getFileAttachName() != null) {
                if (dmsSeaechResoreModel.getFileAttachName().size() > 0) {
                    for (String tmp : dmsSeaechResoreModel.getFileAttachName()) {
                        builder.value(tmp);
                        searchContent += " " + tmp;
                    }
                }
            }
            builder.endArray();
            builder.startArray("fullText");
            if (dmsSeaechResoreModel.getFullText() != null) {
                if (dmsSeaechResoreModel.getFullText().size() > 0) {
                    for (String tmp : dmsSeaechResoreModel.getFullText()) {
                        builder.value(tmp);
                        searchContent += " " + tmp;
                    }
                }
            }
            builder.endArray();
            builder.field(this.searchField, searchContent);

            builder.endObject();

            result = builder.string();

        } catch (IOException e) {
        }
        return result;
    }

    public DmsSearchModel updateData(String id, DmsSearchModel dmsSeaechResoreModel) {
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0, "id must not be empty");
        checkNotNull(dmsSeaechResoreModel, "dmsSeaechResoreModel must not be null");
        DmsFolderService dmsFolderService = new DmsFolderService();
           String folderP = dmsFolderService.getById(dmsSeaechResoreModel.getDocumentFolderId()).getDmsFolderParentKey();
        String[] parts = folderP.split("฿");
        List<String> temp = new ArrayList<String>();
        for (int i = 1; i < parts.length; i++) {
            temp.add(parts[i]);

        }
        dmsSeaechResoreModel.setParentKey(temp);

        DmsSearchModel result = null;
        UpdateResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            try {
                boolean setCreatedDate = false;
                String elasticsearchData = generateElasticsearchData(dmsSeaechResoreModel, setCreatedDate);
                response = elasticsearch.updateData(this.IndexName, this.IndexType, id, elasticsearchData);

                GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);
                if (getReponse.getSourceAsString() != null) {
                    result = new Gson().fromJson(getReponse.getSourceAsString(), DmsSearchModel.class);
                    result.setDmsSearchId(id);
                }
            } catch (Exception e) {
            }

            elasticsearch.closeTransportClient();
        }

        return result;
    }

    public DmsSearchModel updateDataFolder(String id, DmsSearchModel dmsSeaechResoreModel, String folderParentKey) {
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0, "id must not be empty");
        checkNotNull(dmsSeaechResoreModel, "dmsSeaechResoreModel must not be null");

        String[] parts = folderParentKey.split("฿");
        List<String> temp = new ArrayList<String>();
        for (int i = 1; i < parts.length; i++) {
            temp.add(parts[i]);

        }
        dmsSeaechResoreModel.setParentKey(temp);
        DmsSearchModel result = null;
        UpdateResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            try {
                boolean setCreatedDate = false;
                String elasticsearchData = generateElasticsearchData(dmsSeaechResoreModel, setCreatedDate);
                response = elasticsearch.updateData(this.IndexName, this.IndexType, id, elasticsearchData);

                GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);
                if (getReponse.getSourceAsString() != null) {
                    result = new Gson().fromJson(getReponse.getSourceAsString(), DmsSearchModel.class);
                    result.setDmsSearchId(id);
                }
            } catch (Exception e) {
            }

            elasticsearch.closeTransportClient();
        }

        return result;
    }

    public DmsSearchModel deleteData(String id) {
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0, "id must not be empty");

        DmsSearchModel result = null;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);
            if (getReponse.getSourceAsString() != null) {
                result = new Gson().fromJson(getReponse.getSourceAsString(), DmsSearchModel.class);
                result.setDmsSearchId(id);

                DeleteResponse response = elasticsearch.deleteData(this.IndexName, this.IndexType, id);
            }

            elasticsearch.closeTransportClient();
        }

        return result;
    }

    public DmsSearchModel getData(String id) {
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0, "id must not be empty");

        DmsSearchModel result = null;
        GetResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();

        if (elasticsearch.getClient() != null) {
            response = elasticsearch.getData(this.IndexName, this.IndexType, id);
            elasticsearch.closeTransportClient();

            if (response.getSourceAsString() != null) {
                result = new Gson().fromJson(response.getSourceAsString(), DmsSearchModel.class);
                result.setDmsSearchId(response.getId());
            }
        }

        return result;

    }
//    /////////////////////////////////////////////////////////////

    public DmsSearchResultModel search(List<DmsSearchFormModel> searchFormList, String order, String[] fetchSource, int from, int size, String defaultSymbolForSpace, String symbolAnd, String symbolOr, String symbolNot, String symbolWith, String preHighlightTag, String postHighlightTag, int userID) {
        DmsSearchResultModel result = new DmsSearchResultModel();

        long searchTime = new Date().getTime();
        //get WordBrief List
        WordBriefDetailService wordBriefDetailService = new WordBriefDetailService();
        List<WordBriefDetail> wordBriefDetailList = wordBriefDetailService.listAll("", "");
        String[] wordBriefList = new String[wordBriefDetailList.size()];
        int i = 0;
        for (WordBriefDetail wordBriefDetail : wordBriefDetailList) {
            wordBriefList[i] = wordBriefDetail.getWordBriefDetailName();
            i++;
        }
        /*String[] wordBriefList = new String[1]; //for test
        wordBriefList[0] = "฿�?ิม�?ิ฿kimchy฿";*/

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            //QueryBuilder queryBuilder = elasticsearch.advanceSearchFullTextQuery(this.searchField,searchText,defaultSymbolForSpace,symbolAnd,symbolOr,symbolNot,symbolWith,wordBriefList);
            BoolQueryBuilder booleanQueryBuilder = boolQuery();

            booleanQueryBuilder.must(matchQuery("removeBy", 0));

            DmsFolderService dmsFolderService = new DmsFolderService();
//            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();

            ArrayList<SortBuilder> arrSortBuilder = new ArrayList();
            HighlightBuilder highlightBuilder = null;

            SearchResponse searchResponse;

            if (searchFormList == null || searchFormList.size() < 2) {
                searchResponse = elasticsearch.searchData(this.IndexName, this.IndexType, booleanQueryBuilder, arrSortBuilder, fetchSource, highlightBuilder, 0, 1);
                //result.setSearchTime(searchResponse.getTookInMillis());
                result.setSearchAll(searchResponse.getHits().getTotalHits());
            }

            String searchText;

            if (searchFormList != null) {
                SubmoduleAuthService SubmoduleAuthService = new SubmoduleAuthService();
                SubmoduleAuth submoduleAuth = SubmoduleAuthService.getBySubmoduleAuthCode("DMS_OF");
                UserProfileService UserProfileService = new UserProfileService();
                UserProfile userProfile = UserProfileService.getById(userID);

                for (DmsSearchFormModel searchForm : searchFormList) {

                    booleanQueryBuilder.must(matchQuery("parentKey", searchForm.getDocumentFolderId()));
                    DmsFolder folderTemp = dmsFolderService.getById(searchForm.getDocumentFolderId());
                    List<DmsFolder> listDms = dmsFolderService.getListForSearchNotInParentKey(folderTemp, submoduleAuth, userProfile);
                    if (listDms.size() > 0) {
                        BoolQueryBuilder tmpBooleanQueryBuilder2 = boolQuery();
                        for (DmsFolder folder : listDms) {
                            tmpBooleanQueryBuilder2.should(matchQuery("parentKey", folder.getId()));
                        }
                        booleanQueryBuilder.mustNot(tmpBooleanQueryBuilder2);

                    }
                    if (searchForm.getAllField() != null && !searchForm.getAllField().trim().equals("")) {
                        //search field all + fullText 
                        searchText = searchForm.getAllField().trim();
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery(this.searchField, searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));

                    }

                    if (searchForm.getCreateName() != null && !searchForm.getCreateName().trim().equals("")) {
                        searchText = searchForm.getCreateName().trim();
//                        booleanQueryBuilder.must(matchQuery("createName", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("createName", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getUpdateName() != null && !searchForm.getUpdateName().trim().equals("")) {
                        searchText = searchForm.getUpdateName().trim();
//                        booleanQueryBuilder.must(matchQuery("updateName", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("updateName", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentName() != null && !searchForm.getDocumentName().trim().equals("")) {
                        searchText = searchForm.getDocumentName().trim();
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentName", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentPublicStatus() != null && !searchForm.getDocumentPublicStatus().trim().equals("")) {
                        searchText = searchForm.getDocumentPublicStatus().trim();
                        booleanQueryBuilder.must(matchQuery("documentPublicStatus", searchText));
                    }

                    if (searchForm.getDocumentFloat01() != -1) {
                        Float searchTFloat = searchForm.getDocumentFloat01();
                        booleanQueryBuilder.must(matchQuery("documentFloat01", searchTFloat));
                    }

                    if (searchForm.getDocumentFloat02() != -1) {
                        Float searchTFloat = searchForm.getDocumentFloat02();
                        booleanQueryBuilder.must(matchQuery("documentFloat02", searchTFloat));
                    }

                    if (searchForm.getDocumentVarchar01() != null && !searchForm.getDocumentVarchar01().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar01().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar01", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar01", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentVarchar02() != null && !searchForm.getDocumentVarchar02().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar02().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar02", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar02", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentVarchar03() != null && !searchForm.getDocumentVarchar03().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar03().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar03", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar03", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentVarchar04() != null && !searchForm.getDocumentVarchar04().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar04().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar04", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar04", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentVarchar05() != null && !searchForm.getDocumentVarchar05().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar05().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar05", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar05", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentVarchar06() != null && !searchForm.getDocumentVarchar06().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar06().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar06", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar06", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentVarchar07() != null && !searchForm.getDocumentVarchar07().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar07().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar07", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar07", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentVarchar08() != null && !searchForm.getDocumentVarchar08().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar08().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar08", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar08", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentVarchar09() != null && !searchForm.getDocumentVarchar09().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar09().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar09", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar09", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentVarchar10() != null && !searchForm.getDocumentVarchar10().trim().equals("")) {
                        searchText = searchForm.getDocumentVarchar10().trim();
//                        booleanQueryBuilder.must(matchQuery("documentVarchar10", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentVarchar10", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentText01() != null && !searchForm.getDocumentText01().trim().equals("")) {
                        searchText = searchForm.getDocumentText01().trim();
//                        booleanQueryBuilder.must(matchQuery("documentText01", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentText01", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentText02() != null && !searchForm.getDocumentText02().trim().equals("")) {
                        searchText = searchForm.getDocumentText02().trim();
//                        booleanQueryBuilder.must(matchQuery("documentText02", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentText02", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentText03() != null && !searchForm.getDocumentText03().trim().equals("")) {
                        searchText = searchForm.getDocumentText03().trim();
//                        booleanQueryBuilder.must(matchQuery("documentText03", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentText03", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentText04() != null && !searchForm.getDocumentText04().trim().equals("")) {
                        searchText = searchForm.getDocumentText04().trim();
//                        booleanQueryBuilder.must(matchQuery("documentText04", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentText04", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentText05() != null && !searchForm.getDocumentText05().trim().equals("")) {
                        searchText = searchForm.getDocumentText05().trim();
//                        booleanQueryBuilder.must(matchQuery("documentText05", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("documentText05", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentInt01() != -1) {
                        int searchInt = searchForm.getDocumentInt01();
                        booleanQueryBuilder.must(matchQuery("documentInt01", searchInt));
                    }

                    if (searchForm.getDocumentInt02() != -1) {
                        int searchInt = searchForm.getDocumentInt02();
                        booleanQueryBuilder.must(matchQuery("documentInt02", searchInt));
                    }

                    if (searchForm.getDocumentInt03() != -1) {
                        int searchInt = searchForm.getDocumentInt03();
                        booleanQueryBuilder.must(matchQuery("documentInt03", searchInt));
                    }
                    if (searchForm.getDocumentInt04() != -1) {
                        int searchInt = searchForm.getDocumentInt04();
                        booleanQueryBuilder.must(matchQuery("documentInt04", searchInt));
                    }
                    if (searchForm.getDmsDocumentIntComma() != -1) {
                        int searchInt = searchForm.getDmsDocumentIntComma();
                        booleanQueryBuilder.must(matchQuery("dmsDocumentIntComma", searchInt));
                    }

                    if (searchForm.getWfTypeId() != -1) {
                        int searchInt = searchForm.getWfTypeId();
                        booleanQueryBuilder.must(matchQuery("wfTypeId", searchInt));
                    }

                    if (searchForm.getFlowId() != -1) {
                        int searchInt = searchForm.getFlowId();
                        booleanQueryBuilder.must(matchQuery("flowId", searchInt));
                    }

                    if (searchForm.getFileAttachName() != null && !searchForm.getFileAttachName().trim().equals("")) {
                        searchText = searchForm.getFileAttachName().trim();
//                        booleanQueryBuilder.must(matchQuery("fileAttachName", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("fileAttachName", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getFullText() != null && !searchForm.getFullText().trim().equals("")) {
                        searchText = searchForm.getFullText().trim();
//                        booleanQueryBuilder.must(matchQuery("fileAttachName", searchText));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("fullText", searchText, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList));
                    }

                    if (searchForm.getDocumentPublicDate() != null && !searchForm.getDocumentPublicDate().trim().equals("")) {
                        searchText = searchForm.getDocumentPublicDate().trim();
//                         booleanQueryBuilder.must(elasticsearch.advanceSearchSubTextQuery("fileAttachName",searchText,defaultSymbolForSpace,symbolAnd,symbolOr,symbolNot,symbolWith,wordBriefList));
//                        booleanQueryBuilder.must(rangeQuery("age").gt(5).lte(22));
                        booleanQueryBuilder.must(matchQuery("documentPublicDate", searchText));
                    }

                    if (searchForm.getDocumentExpireDateForm() != null && !searchForm.getDocumentExpireDateForm().trim().equals("")) {
                        searchText = searchForm.getDocumentExpireDateForm().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentExpireDate").gt(dateThaiToLocalDateTime(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentExpireDate", searchText, ""));
                    }

                    if (searchForm.getDocumentExpireDateTo() != null && !searchForm.getDocumentExpireDateTo().trim().equals("")) {
                        searchText = searchForm.getDocumentExpireDateTo().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentExpireDate").lte(Common.dateThaiToEng(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentExpireDate", "", searchText));
                    }

                    if (searchForm.getDocumentDate01Form() != null && !searchForm.getDocumentDate01Form().trim().equals("")) {
                        searchText = searchForm.getDocumentDate01Form().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentDate01").gt(dateThaiToLocalDateTime(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentDate01", searchText, ""));
                    }

                    if (searchForm.getDocumentDate01To() != null && !searchForm.getDocumentDate01To().trim().equals("")) {
                        searchText = searchForm.getDocumentPublicDate().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentDate01").lte(dateThaiToLocalDateTime(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentDate01", "", searchText));
                    }

                    if (searchForm.getDocumentDate02Form() != null && !searchForm.getDocumentDate02Form().trim().equals("")) {
                        searchText = searchForm.getDocumentDate02Form().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentDate02").gt(dateThaiToLocalDateTime(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentDate02", searchText, ""));
                    }

                    if (searchForm.getDocumentDate02To() != null && !searchForm.getDocumentDate02To().trim().equals("")) {
                        searchText = searchForm.getDocumentDate02To().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentDate02").lte(dateThaiToLocalDateTime(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentDate02", "", searchText));
                    }

                    if (searchForm.getDocumentDate03Form() != null && !searchForm.getDocumentDate03Form().trim().equals("")) {
                        searchText = searchForm.getDocumentDate03Form().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentDate03").gt(dateThaiToLocalDateTime(searchText)));

                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentDate03", searchText, ""));
                    }

                    if (searchForm.getDocumentDate03To() != null && !searchForm.getDocumentDate03To().trim().equals("")) {
                        searchText = searchForm.getDocumentDate03To().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentDate03").lte(dateThaiToLocalDateTime(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentDate03", "", searchText));
                    }

                    if (searchForm.getDocumentDate04Form() != null && !searchForm.getDocumentDate04Form().trim().equals("")) {
                        searchText = searchForm.getDocumentDate04Form().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentDate04").gt(dateThaiToLocalDateTime(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentDate04", searchText, ""));

                    }

                    if (searchForm.getDocumentDate04To() != null && !searchForm.getDocumentDate04To().trim().equals("")) {
                        searchText = searchForm.getDocumentDate04To().trim();
//                        booleanQueryBuilder.must(rangeQuery("documentDate04").lte(dateThaiToLocalDateTime(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("documentDate04", "", searchText));
                    }

                    if (searchForm.getCreatedDateForm() != null && !searchForm.getCreatedDateForm().trim().equals("")) {

                        searchText = searchForm.getCreatedDateForm().trim();
//                        Elasticsearch elasticsearch = new Elasticsearch();
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("createdDate", searchText, ""));

                    }

                    if (searchForm.getCreatedDateTo() != null && !searchForm.getCreatedDateTo().trim().equals("")) {
                        searchText = searchForm.getCreatedDateTo().trim();
//                        booleanQueryBuilder.must(rangeQuery("createdDate").lte(Common.dateThaiToEng(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("createdDate", "", searchText));

                    }

                    if (searchForm.getUpdatedDateForm() != null && !searchForm.getUpdatedDateForm().trim().equals("")) {
                        searchText = searchForm.getUpdatedDateForm().trim();
//                        booleanQueryBuilder.must(rangeQuery("updatedDate").gt(Common.dateThaiToEng(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("updatedDate", searchText, ""));

                    }

                    if (searchForm.getUpdatedDateTo() != null && !searchForm.getUpdatedDateTo().trim().equals("")) {
                        searchText = searchForm.getUpdatedDateTo().trim();
//                        booleanQueryBuilder.must(rangeQuery("updatedDate").lte(dateThaiToLocalDateTime(searchText)));
                        booleanQueryBuilder.must(elasticsearch.advanceSearchDateQuery("updatedDate", "", searchText));

                    }

                }
            }

            if (order.equalsIgnoreCase("DESC")) {
                arrSortBuilder.add(SortBuilders.fieldSort("createdDate").order(SortOrder.DESC));
//                arrSortBuilder.add(SortBuilders.fieldSort("contentNo.startNumber").order(SortOrder.DESC));
            } else {
                arrSortBuilder.add(SortBuilders.fieldSort("createdDate").order(SortOrder.ASC));
//                arrSortBuilder.add(SortBuilders.fieldSort("contentNo.startNumber").order(SortOrder.ASC));
            }

            highlightBuilder = new HighlightBuilder()
                    .preTags(preHighlightTag)
                    .postTags(postHighlightTag);

            if (fetchSource != null && fetchSource.length > 0) {
                for (String fetch : fetchSource) {

                    highlightBuilder.field(fetch);

                }
            }
            searchResponse = elasticsearch.searchData(this.IndexName, this.IndexType, booleanQueryBuilder, arrSortBuilder, fetchSource, highlightBuilder, from, size);
            elasticsearch.closeTransportClient();

            result.setSearchTime(new Date().getTime() - searchTime);
            result.setSearchHit(searchResponse.getHits().getTotalHits());

            List<DmsSearchModel> searchResult = new ArrayList();
            if (searchResponse.getHits().getHits() != null) {
                for (SearchHit hit : searchResponse.getHits().getHits()) {

                    DmsSearchModel tmpObj = new Gson().fromJson(hit.getSourceAsString(), DmsSearchModel.class);
                    tmpObj.setDmsSearchId(hit.getId());
                    searchResult.add(tmpObj);
                }
            }

            result.setSearchResult(searchResult);
        }

        return result;

    }

    /////////////////////////////////////////////////
    public QueryBuilder advanceSearchNo(String field, String toField, String yearField, String value, String defaultSymbolForSpace, String symbolAnd, String symbolOr, String symbolNot, String symbolWith) {
        QueryBuilder result;
        Elasticsearch elasticsearch = new Elasticsearch();

        if (symbolAnd == null || symbolAnd.isEmpty()) {
            symbolAnd = "&";
        }
        if (symbolOr == null || symbolOr.isEmpty()) {
            symbolOr = "|";
        }
        if (symbolNot == null || symbolNot.isEmpty()) {
            symbolNot = "!";
        }
        if (symbolWith == null || symbolWith.isEmpty()) {
            symbolWith = "^";
        }
        if (defaultSymbolForSpace == null || defaultSymbolForSpace.isEmpty()) {
            defaultSymbolForSpace = "|";
        } else {
            if (defaultSymbolForSpace.equals(symbolAnd)) {
                defaultSymbolForSpace = "&";
            } else if (defaultSymbolForSpace.equals(symbolOr)) {
                defaultSymbolForSpace = "|";
            }
        }

        String tmp = elasticsearch.replaceAllStr(value, symbolNot, "!");
        tmp = elasticsearch.replaceAllStr(tmp, symbolWith, "^");
        tmp = elasticsearch.replaceAllStr(tmp, symbolAnd, "&");
        tmp = elasticsearch.replaceAllStr(tmp, symbolOr, "|");

        //-----------Insert Default Symbol for space------------
        String spaceReplace = "@@@@@";
        tmp = elasticsearch.replaceSpaceInsideQuote(tmp, spaceReplace);
        tmp = elasticsearch.trimBetweenSymbol(tmp, "&|!^");
        String[] arrToken = Common.getTokenStrings(tmp, " ", false);
        tmp = "";
        for (int i = 0; i < arrToken.length; i++) {
            if (i == arrToken.length - 1) {
                tmp += arrToken[i];
            } else {
                tmp += arrToken[i] + defaultSymbolForSpace;
            }
        }

        String[][] replaceValue = new String[5][2];
        replaceValue[0][0] = spaceReplace;
        replaceValue[0][1] = " ";
        replaceValue[1][0] = "|";
        replaceValue[1][1] = symbolOr;
        replaceValue[2][0] = "&";
        replaceValue[2][1] = symbolAnd;
        replaceValue[3][0] = "^";
        replaceValue[3][1] = symbolWith;
        replaceValue[4][0] = "!";
        replaceValue[4][1] = symbolNot;

        tmp = elasticsearch.replaceStrInsideSymbol(tmp, "\"", "\"", replaceValue, ""); //Repair symbol in quote that was replaced before
        tmp = tmp.replaceAll("\"", "");//remove quote

        //Repair false syntax (have symbol and,or,with at first character)
        String[] valuetempspace1 = Common.getTokenStrings(tmp, "&|^", true);
        if (valuetempspace1.length > 0) {
            if ((valuetempspace1[0].equals("&")) || (valuetempspace1[0].equals("|")) || (valuetempspace1[0].equals("^"))) {
                tmp = "";
                for (int VTS1 = 1; VTS1 < valuetempspace1.length; VTS1++) {
                    tmp += valuetempspace1[VTS1];
                }
            }
        }

        arrToken = Common.getTokenStrings(tmp, "&|!", true);
        arrToken = elasticsearch.manageOperatorFormat(arrToken);
        tmp = "";
        for (String token : arrToken) {
            tmp += token;
        }

        //-----------------create Query-----------------------
        BoolQueryBuilder queryOr = boolQuery();
        String[] arrOr = Common.getTokenStrings(tmp, "\\|", false);
        for (String tokenOr : arrOr) {
            String[] arrAnd = Common.getTokenStrings(tokenOr, "&", false);
            BoolQueryBuilder queryAnd = boolQuery();
            for (String tokenAnd : arrAnd) {
                if (tokenAnd.indexOf("!") == 0) {
                    queryAnd.mustNot(advanceSearchNoQuery(field, toField, yearField, tokenAnd.substring(1)));
                } else {
                    queryAnd.must(advanceSearchNoQuery(field, toField, yearField, tokenAnd));
                }
            }
            queryOr.should(queryAnd);
        }

        result = queryOr;

        return result;
    }

    public QueryBuilder advanceSearchNoQuery(String field, String toField, String yearField, String value) {
        QueryBuilder result;

        BoolQueryBuilder query = boolQuery();
        value = changeNumThaiToEng(value);
        String tmpNo;
        String tmpToNo;
        String tmpYear;
        String yearPlus = "25";

        if ((value.indexOf("/") > 0) && (value.indexOf("/") < value.length() - 1)) {
            String[] tmpToken = Common.getTokenStrings(value, "/", false);
            tmpNo = tmpToken[0];
            tmpYear = tmpToken[1];

            if (isNumber(tmpYear)) {
                if (Integer.parseInt(tmpYear) < 100) {
                    tmpYear = yearPlus + tmpYear;
                }
            }

            if ((tmpNo.indexOf("-") > 0) && (tmpNo.indexOf("-") < tmpNo.length() - 1)) {
                String[] tmpNoToken = Common.getTokenStrings(tmpNo, "-", false);
                tmpNo = tmpNoToken[0];
                tmpToNo = tmpNoToken[1];

                if (isNumber(tmpNo) && isNumber(tmpToNo) && isNumber(tmpYear)) {
                    if (toField.equals("")) {
                        query.must(matchQuery(yearField, Integer.parseInt(tmpYear)));
                        query.must(rangeQuery(field).gte(Integer.parseInt(tmpNo)).lte(Integer.parseInt(tmpToNo)));
                    } else {
                        query.must(matchQuery(yearField, Integer.parseInt(tmpYear)));
                        query.must(boolQuery()
                                .should(rangeQuery(field).gte(Integer.parseInt(tmpNo)).lte(Integer.parseInt(tmpToNo)))
                                .should(rangeQuery(toField).gte(Integer.parseInt(tmpNo)).lte(Integer.parseInt(tmpToNo)))
                                .should(boolQuery()
                                        .must(rangeQuery(field).lte(Integer.parseInt(tmpNo)))
                                        .must(rangeQuery(toField).gte(Integer.parseInt(tmpNo))))
                                .should(boolQuery()
                                        .must(rangeQuery(field).lte(Integer.parseInt(tmpToNo)))
                                        .must(rangeQuery(toField).gte(Integer.parseInt(tmpToNo))))
                        );
                    }
                } else {
                    query.must(matchQuery(yearField, -1));//return false query
                }
            } else {
                if (isNumber(tmpNo) && isNumber(tmpYear)) {
                    if (toField.equals("")) {
                        query.must(matchQuery(yearField, Integer.parseInt(tmpYear)));
                        query.must(matchQuery(field, Integer.parseInt(tmpNo)));
                    } else {
                        query.must(matchQuery(yearField, Integer.parseInt(tmpYear)));
                        query.must(rangeQuery(field).lte(Integer.parseInt(tmpNo)));
                        query.must(rangeQuery(toField).gte(Integer.parseInt(tmpNo)));
                    }
                } else {
                    query.must(matchQuery(yearField, -1));//return false query
                }
            }
        } else {
            value = value.replaceAll("/", "");
            tmpNo = value;

            String tmpToYear;

            if ((tmpNo.indexOf("-") > 0) && (tmpNo.indexOf("-") < tmpNo.length() - 1)) {
                String[] tmpNoToken = Common.getTokenStrings(value, "-", false);
                tmpNo = tmpNoToken[0];
                tmpToNo = tmpNoToken[1];

                tmpYear = tmpNo;
                tmpToYear = tmpToNo;

                if (isNumber(tmpYear)) {
                    if (Integer.parseInt(tmpYear) < 100) {
                        tmpYear = yearPlus + tmpYear;
                    }
                }

                if (isNumber(tmpToYear)) {
                    if (Integer.parseInt(tmpToYear) < 100) {
                        tmpToYear = yearPlus + tmpToYear;
                    }
                }

                if (isNumber(tmpNo) && isNumber(tmpToNo)) {
                    if (toField.equals("")) {
                        query.must(boolQuery()
                                .should(rangeQuery(yearField).gte(Integer.parseInt(tmpYear)).lte(Integer.parseInt(tmpToYear)))
                                .should(rangeQuery(field).gte(Integer.parseInt(tmpNo)).lte(Integer.parseInt(tmpToNo))));
                    } else {
                        query.must(boolQuery()
                                .should(rangeQuery(yearField).gte(Integer.parseInt(tmpYear)).lte(Integer.parseInt(tmpToYear)))
                                .should(rangeQuery(field).gte(Integer.parseInt(tmpNo)).lte(Integer.parseInt(tmpToNo)))
                                .should(rangeQuery(toField).gte(Integer.parseInt(tmpNo)).lte(Integer.parseInt(tmpToNo)))
                                .should(boolQuery()
                                        .must(rangeQuery(field).lte(Integer.parseInt(tmpNo)))
                                        .must(rangeQuery(toField).gte(Integer.parseInt(tmpNo))))
                                .should(boolQuery()
                                        .must(rangeQuery(field).lte(Integer.parseInt(tmpToNo)))
                                        .must(rangeQuery(toField).gte(Integer.parseInt(tmpToNo))))
                        );
                    }
                } else {
                    query.must(matchQuery(yearField, -1));//return false query
                }
            } else {
                if (isNumber(tmpNo)) {
                    tmpYear = tmpNo;

                    if (isNumber(tmpYear)) {
                        if (Integer.parseInt(tmpYear) < 100) {
                            tmpYear = yearPlus + tmpYear;
                        }
                    }

                    if (toField.equals("")) {
                        query.must(boolQuery()
                                .should(matchQuery(field, Integer.parseInt(tmpNo)))
                                .should(matchQuery(yearField, Integer.parseInt(tmpYear))));
                    } else {
                        query.must(boolQuery()
                                .should(matchQuery(yearField, Integer.parseInt(tmpYear)))
                                .should(boolQuery()
                                        .must(rangeQuery(field).lte(Integer.parseInt(tmpNo)))
                                        .must(rangeQuery(toField).gte(Integer.parseInt(tmpNo))))
                        );
                    }
                } else {
                    query.must(matchQuery(yearField, -1));//return false query
                }
            }
        }

        result = query;

        return result;
    }

    private String changeNumThaiToEng(String numberValue) {
        String result = "";
        if (!numberValue.equals("")) {
            for (int i = 0; i < numberValue.length(); i++) {
                char ch = numberValue.charAt(i);

                switch (ch) {
                    case '๑':
                        result += "1";
                        break;
                    case '๒':
                        result += "2";
                        break;
                    case '๓':
                        result += "3";
                        break;
                    case '๔':
                        result += "4";
                        break;
                    case '๕':
                        result += "5";
                        break;
                    case '๖':
                        result += "6";
                        break;
                    case '๗':
                        result += "7";
                        break;
                    case '๘':
                        result += "8";
                        break;
                    case '๙':
                        result += "9";
                        break;
                    case '๐':
                        result += "0";
                        break;
                    default:
                        result += ch;
                        break;
                }
            }
        }

        return result;
    }

    private boolean isNumber(String chkNumber) {
        String checkOK = "0123456789";
        boolean allValid = true;

        int i = 0;
        int j = 0;
        if (!chkNumber.equals("")) {
            for (i = 0; i < chkNumber.length(); i++) {
                char ch = chkNumber.charAt(i);
                for (j = 0; j < checkOK.length(); j++) {
                    if (ch == checkOK.charAt(j)) {
                        break;
                    }
                }

                if (j == checkOK.length()) {
                    allValid = false;
                    break;
                }
            }
        } else {
            allValid = false;
        }

        if (allValid) {
            return (true);
        } else {
            return (false);
        }
    }

    public DmsSearchModel changDocumntToSearch(DmsDocument document) {

//        DmsDocumentService dmsDocumentService = new DmsDocumentService();
        DmsSearchModel dmsSearchModel = new DmsSearchModel();
        dmsSearchModel.setCreatedBy(document.getCreatedBy());
        dmsSearchModel.setUpdatedBy(document.getUpdatedBy());
        dmsSearchModel.setId(document.getId());
        dmsSearchModel.setDocumentPublicDate(Common.localDateTimeToString(document.getDmsDocumentPublicDate()));
        dmsSearchModel.setDocumentExpireDate(Common.localDateTimeToString(document.getDmsDocumentExpireDate()));
        dmsSearchModel.setDocumentDate01(Common.localDateTimeToString(document.getDmsDocumentDatetime01()));
        dmsSearchModel.setDocumentDate02(Common.localDateTimeToString(document.getDmsDocumentDatetime02()));
        dmsSearchModel.setDocumentDate03(Common.localDateTimeToString(document.getDmsDocumentDatetime03()));
        dmsSearchModel.setDocumentDate04(Common.localDateTimeToString(document.getDmsDocumentDatetime04()));

        dmsSearchModel.setDocumentTypeId(document.getDocumentTypeId());
        dmsSearchModel.setDocumentName(document.getDmsDocumentName());
        dmsSearchModel.setDocumentPublicStatus(document.getDmsDocumentPublicStatus());
        dmsSearchModel.setDocumentFolderId(document.getDmsFolderId());
        dmsSearchModel.setDocumentFloat01(document.getDmsDocumentFloat01());
        dmsSearchModel.setDocumentFloat02(document.getDmsDocumentFloat02());
        dmsSearchModel.setDocumentVarchar01(document.getDmsDocumentVarchar01());
        dmsSearchModel.setDocumentVarchar02(document.getDmsDocumentVarchar02());
        dmsSearchModel.setDocumentVarchar03(document.getDmsDocumentVarchar03());
        dmsSearchModel.setDocumentVarchar04(document.getDmsDocumentVarchar04());
        dmsSearchModel.setDocumentVarchar05(document.getDmsDocumentVarchar05());
        dmsSearchModel.setDocumentVarchar06(document.getDmsDocumentVarchar06());
        dmsSearchModel.setDocumentVarchar07(document.getDmsDocumentVarchar07());
        dmsSearchModel.setDocumentVarchar08(document.getDmsDocumentVarchar08());
        dmsSearchModel.setDocumentVarchar09(document.getDmsDocumentVarchar09());
        dmsSearchModel.setDocumentVarchar10(document.getDmsDocumentVarchar10());

        dmsSearchModel.setDocumentText01(document.getDmsDocumentText01());
        dmsSearchModel.setDocumentText02(document.getDmsDocumentText02());
        dmsSearchModel.setDocumentText03(document.getDmsDocumentText03());
        dmsSearchModel.setDocumentText04(document.getDmsDocumentText04());
        dmsSearchModel.setDocumentText05(document.getDmsDocumentText05());

        dmsSearchModel.setDocumentInt01(document.getDmsDocumentInt01());
        dmsSearchModel.setDocumentInt02(document.getDmsDocumentInt02());
        dmsSearchModel.setDocumentInt03(document.getDmsDocumentInt03());
        dmsSearchModel.setDocumentInt04(document.getDmsDocumentInt04());
        dmsSearchModel.setDmsDocumentIntComma(document.getDmsDocumentIntComma());
        dmsSearchModel.setDmsDocumentSec(document.getDmsDocumentSec());
//        dmsSearchModel.setExpType(document.get);
//        dmsSearchModel.setExpNumber(0);
//        dmsSearchModel.setIsExp(IndexType);
        dmsSearchModel.setWfTypeId(document.getWfTypeId());
        dmsSearchModel.setFlowId(document.getFlowId());
        dmsSearchModel.setDmsSearchId(document.getDmsSearchId());

        dmsSearchModel.setRemovedBy(document.getRemovedBy());

//        dmsSearchModel.setFullText(null);
//        dmsSearchModel.setFileAttachName(null);
        return (dmsSearchModel);
    }

    public DmsSearchModel changFolderToSearch(DmsFolder folder) {

//        DmsDocumentService dmsDocumentService = new DmsDocumentService();
        DmsSearchModel dmsSearchModel = new DmsSearchModel();
        dmsSearchModel.setCreatedBy(folder.getCreatedBy());
        dmsSearchModel.setUpdatedBy(folder.getUpdatedBy());
        dmsSearchModel.setId(folder.getId());

        dmsSearchModel.setDmsSearchId(folder.getDmsSearchId());

        dmsSearchModel.setType(folder.getDmsFolderType());
        dmsSearchModel.setFolderName(folder.getDmsFolderName());
        dmsSearchModel.setFolderDescription(folder.getDmsFolderDescription());
        dmsSearchModel.setDocumentFolderId(folder.getId());

        dmsSearchModel.setRemovedBy(folder.getRemovedBy());
        return (dmsSearchModel);
    }

    public DmsSearchModel updateDataMove(String id, int folderId) {
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0, "id must not be empty");
        checkNotNull(folderId, "folderId must not be null");

        DmsSearchModel result = null;
        UpdateResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            try {

                String elasticsearchData = generateElasticsearchDataMove(folderId);
                response = elasticsearch.updateData(this.IndexName, this.IndexType, id, elasticsearchData);

                GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);
                if (getReponse.getSourceAsString() != null) {
                    result = new Gson().fromJson(getReponse.getSourceAsString(), DmsSearchModel.class);
                    result.setDmsSearchId(id);
                }
            } catch (Exception e) {
            }

            elasticsearch.closeTransportClient();
        }

        return result;
    }

    private String generateElasticsearchDataMove(int folderId) {
        String result = "";
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();

            String searchContent = "";
            XContentBuilder builder = jsonBuilder();
            builder.startObject();
            if (folderId > 0) {
                String fullPath = dmsFolderService.getFullPathName(folderId);
                builder.field("documentFolderId", folderId);
                builder.field("fullPathName", fullPath);
            } else {
                String fullPath = "";
                builder.field("documentFolderId", folderId);
                builder.field("fullPathName", fullPath);
            }
            builder.endObject();

            result = builder.string();

        } catch (IOException e) {
        }
        return result;
    }

    public DmsSearchModel updateDatareStore(String id) {
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0, "id must not be empty");

        DmsSearchModel result = null;
        UpdateResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            try {

                String elasticsearchData = generateElasticsearchDataStore();
                response = elasticsearch.updateData(this.IndexName, this.IndexType, id, elasticsearchData);

                GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);
                if (getReponse.getSourceAsString() != null) {
                    result = new Gson().fromJson(getReponse.getSourceAsString(), DmsSearchModel.class);
                    result.setDmsSearchId(id);
                }
            } catch (Exception e) {
            }

            elasticsearch.closeTransportClient();
        }

        return result;
    }

    private String generateElasticsearchDataStore() {
        String result = "";
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();

            String searchContent = "";
            XContentBuilder builder = jsonBuilder();
            builder.startObject();

            builder.field("removeBy", 0);

            builder.endObject();

            result = builder.string();

        } catch (IOException e) {
        }
        return result;
    }

}
