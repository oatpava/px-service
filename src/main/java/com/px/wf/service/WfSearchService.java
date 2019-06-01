/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.wf.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.gson.Gson;
import com.px.share.model.FileAttachWfSearchModel;
import com.px.share.util.Elasticsearch;
import com.px.wf.model.WfContentESModel;
import java.io.IOException;
import java.util.ArrayList;
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
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

/**
 *
 * @author Oat
 */
public class WfSearchService {

    private static final Logger LOG = Logger.getLogger(WfSearchService.class.getName());

    final private String IndexName = "pxwfsearch";
    final private String IndexType = "wfsearch";
    final private String searchField = "searchField";//field of search all, must add(+=) all field to gen this field

    public WfSearchService() {
    }

    public boolean setup() {
        boolean result = false;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            if (!elasticsearch.isIndexExist(this.IndexName)) {
                elasticsearch.createIndex(this.IndexName);
            }
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

    public WfContentESModel addData(WfContentESModel searchModel) {
        checkNotNull(searchModel, "searchModel must not be null");

        WfContentESModel result = null;
        IndexResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            try {
                String elasticsearchData = generateElasticsearchData(searchModel);
                String id = null;
                response = elasticsearch.addData(this.IndexName, this.IndexType, id, elasticsearchData);
                id = response.getId();
                GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);
                if (getReponse.getSourceAsString() != null) {
                    result = new Gson().fromJson(getReponse.getSourceAsString(), WfContentESModel.class);
                    result.setSearchId(id);
                }
            } catch (Exception e) {
                System.out.println("Exception:SearchCCService-addData:" + e);
            }
            elasticsearch.closeTransportClient();
        }
        return result;
    }

    public WfContentESModel updateData(String id, WfContentESModel searchModel) {
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0, "id must not be empty");
        checkNotNull(searchModel, "searchModel must not be null");

        WfContentESModel result = null;
        UpdateResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            try {
                String elasticsearchData = generateElasticsearchData(searchModel);
                response = elasticsearch.updateData(this.IndexName, this.IndexType, id, elasticsearchData);

                GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);
                if (getReponse.getSourceAsString() != null) {
                    result = new Gson().fromJson(getReponse.getSourceAsString(), WfContentESModel.class);
                    result.setSearchId(id);
                }
            } catch (Exception e) {
                System.out.println("Exception:SearchCCService-updateData:" + e);
            }
            elasticsearch.closeTransportClient();
        }
        return result;
    }

    public WfContentESModel deleteData(String id) {
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0, "id must not be empty");

        WfContentESModel result = null;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            GetResponse getReponse = elasticsearch.getData(this.IndexName, this.IndexType, id);
            if (getReponse.getSourceAsString() != null) {
                result = new Gson().fromJson(getReponse.getSourceAsString(), WfContentESModel.class);
                result.setSearchId(id);

                DeleteResponse response = elasticsearch.deleteData(this.IndexName, this.IndexType, id);
            }
            elasticsearch.closeTransportClient();
        }
        return result;
    }

    public WfContentESModel getData(String id) {
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0, "id must not be empty");

        WfContentESModel result = null;
        GetResponse response;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            response = elasticsearch.getData(this.IndexName, this.IndexType, id);
            elasticsearch.closeTransportClient();

            if (response.getSourceAsString() != null) {
                result = new Gson().fromJson(response.getSourceAsString(), WfContentESModel.class);
                result.setSearchId(response.getId());
            }
        }
        return result;
    }

    private String generateElasticsearchData(WfContentESModel searchModel) {
        String result = "";
        try {
            //String searchContent = "";
            XContentBuilder builder = jsonBuilder();
            builder.startObject();
//            if (searchModel.getLinkId() == null) {
//                searchModel.setLinkId("");
//            }
            builder.field("linkId", searchModel.getLinkId());

            builder.field("folderId", searchModel.getFolderId());

            builder.startArray("fileAttachs");
            if (searchModel.getFileAttachs() != null) {
                if (searchModel.getFileAttachs().size() > 0) {
                    for (FileAttachWfSearchModel tmp : searchModel.getFileAttachs()) {
                        if (tmp != null) {
                            builder.startObject()
                                    .field("fullText", tmp.getFulltext())
                                    .endObject();
                        }
                    }
                }
            }
            builder.endArray();

            builder.endObject();
            result = builder.string();
        } catch (IOException e) {
            System.out.println("Exception:SearchCCService-generateElasticsearchData:" + e);
        }
        return result;
    }

    public List<WfContentESModel> search(int folderId, String searchText) {
        List<WfContentESModel> result = new ArrayList();

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if (elasticsearch.getClient() != null) {
            BoolQueryBuilder booleanQueryBuilder = boolQuery();

            booleanQueryBuilder.must(matchQuery("folderId", folderId));
            booleanQueryBuilder.must(wildcardQuery("fileAttachs.fullText", "*" + searchText + "*"));

            ArrayList<SortBuilder> arrSortBuilder = new ArrayList();
            arrSortBuilder.add(SortBuilders.fieldSort("linkId").order(SortOrder.DESC));
            String[] fetchSource = null;
            HighlightBuilder highlightBuilder = null;
            int from = 0;
            int size = 0;

            SearchResponse searchResponse = elasticsearch.searchData(this.IndexName, this.IndexType, booleanQueryBuilder, arrSortBuilder, fetchSource, highlightBuilder, from, size);
            elasticsearch.closeTransportClient();

            if (searchResponse.getHits().getHits() != null) {
                for (SearchHit hit : searchResponse.getHits().getHits()) {
                    WfContentESModel tmpObj = new Gson().fromJson(hit.getSourceAsString(), WfContentESModel.class);
                    tmpObj.setSearchId(hit.getId());
                    result.add(tmpObj);
                }
            }
        }

        return result;
    }
}
