/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.daoimpl;

import com.px.share.util.Elasticsearch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import org.elasticsearch.search.sort.SortBuilder;
import com.px.dms.dao.ElasticSearchDao;
import com.px.dms.entity.ElasticSearch;
import com.px.share.daoimpl.GenericDaoImpl;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.hibernate.criterion.DetachedCriteria;

/**
 *
 * @author TOP
 */
public class ElasticSearchDaoImpl extends GenericDaoImpl<ElasticSearch, Integer> implements ElasticSearchDao {

    public ElasticSearchDaoImpl(Class<ElasticSearch> entityClass) {
        super(ElasticSearch.class);
    }

    public ElasticSearchDaoImpl() {
        super(ElasticSearch.class);
    }

    @Override
    public IndexResponse addData(String indexName, String indexType, String id, List<ElasticSearch> data) {
//        return null;
        //moduleID indexName
        //submoduleID indexType
        //linkID id

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        IndexResponse response = null;

        try {
            XContentBuilder builder = jsonBuilder();
            builder.startObject();
            for (int i = 0; i < data.size(); i++) {
                builder.field(data.get(i).getFieldName(), data.get(i).getFieldData());

            }
            builder.endObject();
            response = elasticsearch.addData(indexName, indexType, id, builder.string());

        } catch (IOException ex) {
            Logger.getLogger(ElasticSearchDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    ;
  
       
      
    

    @Override
    public UpdateResponse updateData(String moduleId, String subModuleId, String linkId, List<ElasticSearch> data) {

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        UpdateResponse response = null;

        try {
            XContentBuilder builder = jsonBuilder();
            builder.startObject();
            for (int i = 0; i < data.size(); i++) {
                builder.field(data.get(i).getFieldName(), data.get(i).getFieldData());

            }
            builder.endObject();
            response = elasticsearch.updateData(moduleId, subModuleId, linkId, builder.string());

        } catch (IOException ex) {
            Logger.getLogger(ElasticSearchDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ElasticSearchDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;

    }

    @Override
    public DeleteResponse deleteData(String moduleId, String subModuleId, String linkId) {
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        DeleteResponse response = elasticsearch.deleteData(moduleId, subModuleId, linkId);
        return response;
    }

    @Override
    public SearchResponse searchdata(String indexName, String indexType, String isFullText, List<ElasticSearch> data, int from, int size, String sortByField, String orderBy) {
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        QueryBuilder booleanQueryBuilder;
        if (isFullText.equalsIgnoreCase("Y")) {
            booleanQueryBuilder = advanceSearchFullTextQuery(indexName, indexType, data);
        } else {
            booleanQueryBuilder = advanceSearchSubTextQuery(indexName, indexType, data);
        }
        boolean useScroll = false;

        ArrayList<SortBuilder> arrSortBuilder = new ArrayList();//oreder

        if (!sortByField.equals("") || sortByField == null) {
            if (!orderBy.equals("") || orderBy == null) {
                if (orderBy.equalsIgnoreCase("DESC") || orderBy.equalsIgnoreCase("desc")) {
                    arrSortBuilder.add(SortBuilders.fieldSort(sortByField).order(SortOrder.DESC));
                } else {
                    arrSortBuilder.add(SortBuilders.fieldSort(sortByField).order(SortOrder.ASC));
                }
            } else {
                arrSortBuilder.add(SortBuilders.fieldSort(sortByField).order(SortOrder.DESC));
            }
        }

        SearchResponse response = new SearchResponse();
        
        return response;
    }

    @Override
    public QueryBuilder advanceSearchSubTextQuery(String indexName, String indexType, List<ElasticSearch> data) {
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();

        BoolQueryBuilder booleanQueryBuilder = boolQuery();

        for (ElasticSearch data1 : data) {
            booleanQueryBuilder.must(wildcardQuery(data1.getFieldName(), "*" + data1.getFieldData() + "*"));
        }
        return booleanQueryBuilder;
    }

    @Override
    public BoolQueryBuilder advanceSearchFullTextQuery(String indexName, String indexType, List<ElasticSearch> data) {
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        QueryBuilder queryBuilder;
        BoolQueryBuilder booleanQueryBuilder = boolQuery();

        String defaultSymbolForSpace = "|";
        String symbolAnd = "AND";
        String symbolOr = "OR";
        String symbolNot = "NOT";
        String symbolWith = "WITH";

        String[] wordBriefList = new String[1];//คำเหมือน
        wordBriefList[0] = "";
        for (ElasticSearch data1 : data) {
            queryBuilder = elasticsearch.advanceSearchFullTextQuery(data1.getFieldName(), data1.getFieldData(), defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith, wordBriefList);
            booleanQueryBuilder.must(queryBuilder);
        }
        return booleanQueryBuilder;
    }

    @Override
    public ElasticSearch findParent(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ElasticSearch> findChild(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ElasticSearch> findChild(Integer id, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ElasticSearch create(ElasticSearch t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ElasticSearch getById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ElasticSearch getByIdNotRemoved(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ElasticSearch getOneByCriteria(DetachedCriteria criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ElasticSearch update(ElasticSearch entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(ElasticSearch entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ElasticSearch> listByCriteria(DetachedCriteria criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ElasticSearch> listByCriteria(DetachedCriteria criteria, int firstResult, int maxResult) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer countAll(DetachedCriteria criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean putMapping(String indexName, String indexType, String fieldName) {
        try {
            Elasticsearch elasticsearch = new Elasticsearch();
            fieldName ="\""+ fieldName+"\"";
            elasticsearch.createTransportClient();
            String mappingData = "{\n"
                    + "\"properties\": {\n"
                    + fieldName +": {\n"
                    + "\"type\": \"string\",\n"
                    + "\"fielddata\": true\n"
                    + //For set sort text column
                    "}\n"
                    + "}\n"
                    + "}";
            PutMappingResponse response = elasticsearch.putMapping(indexName, indexType, mappingData);

        } catch (Exception e) {
        }

        return true;
    }

}
