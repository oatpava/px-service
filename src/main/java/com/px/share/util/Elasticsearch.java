//package com.px.share.util;
//
//import static com.google.common.base.Preconditions.checkArgument;
//import static com.google.common.base.Preconditions.checkNotNull;
//import com.px.share.entity.Param;
//import com.px.share.service.ParamService;
//import java.net.InetAddress;
//import java.util.ArrayList;
//import org.apache.log4j.Logger;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
//import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
//import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
//import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
//import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.sort.SortBuilder;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//
///**
// *
// * @author Peach
// */
//public class Elasticsearch {
//    private static final Logger LOG = Logger.getLogger(Elasticsearch.class.getName());
//    private String clusterName = "elasticsearch";
//    private String hostName = "127.0.0.1";
//    private int hostPort = 9300;
//    
//    private TransportClient client = null;
//    private boolean useScroll = false;
//    private TimeValue scrollTime = new TimeValue(30000);
//    private boolean showScoreWhenSort = false;
//
//    public Elasticsearch() {
//        ParamService paramService = new ParamService();
//        Param param = paramService.getByParamName("ELASTICSEARCH_SERVER_NAME");
//        if(param!=null && !param.getParamValue().isEmpty()){
//            hostName = param.getParamValue();
//        }
//        
//        param = paramService.getByParamName("ELASTICSEARCH_SERVER_PORT");
//        if(param!=null && !param.getParamValue().isEmpty()){
//            hostPort = Integer.parseInt(param.getParamValue());
//        }
//        
//        param = paramService.getByParamName("ELASTICSEARCH_CLUSTER_NAME");
//        if(param!=null && !param.getParamValue().isEmpty()){
//            clusterName = param.getParamValue();
//        }
//    }
//
//    public String getClusterName() {
//        return clusterName;
//    }
//
//    public void setClusterName(String clusterName) {
//        this.clusterName = clusterName;
//    }
//
//    public String getHostName() {
//        return hostName;
//    }
//
//    public void setHostName(String hostName) {
//        this.hostName = hostName;
//    }
//
//    public int getHostPort() {
//        return hostPort;
//    }
//
//    public void setHostPort(int hostPort) {
//        this.hostPort = hostPort;
//    }
//
//    public TransportClient getClient() {
//        return client;
//    }
//
//    public void setClient(TransportClient client) {
//        this.client = client;
//    }
//
//    public boolean isShowScoreWhenSort() {
//        return showScoreWhenSort;
//    }
//
//    public void setShowScoreWhenSort(boolean showScoreWhenSort) {
//        this.showScoreWhenSort = showScoreWhenSort;
//    }
//
//    public boolean isUseScroll() {
//        return useScroll;
//    }
//
//    public void setUseScroll(boolean useScroll) {
//        this.useScroll = useScroll;
//    }
//
//    public TimeValue getScrollTime() {
//        return scrollTime;
//    }
//
//    public void setScrollTime(TimeValue scrollTime) {
//        this.scrollTime = scrollTime;
//    }
//    
//    public void createTransportClient(){
//        Settings settings = Settings.builder().put("cluster.name", clusterName)
//                                            //.put("client.transport.sniff", true)
//                                            //.put("shield.user", elasticUserName+":"+elasticPassword)
////                                            .put("indices.cluster.send_refresh_mapping", false)
//                                            .build();
//        try{
//            this.client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), hostPort));
//        } catch(Exception e){
//            this.client = null;
//            LOG.info("Exception: Elasticsearch.createTransportClient()="+e);
//        }
//    }
//    
//    public void closeTransportClient(){
//        if(this.client!=null) {
//            this.client.close();
//        }
//    }
//    
//    public void createIndex(String indexName){
//        checkNotNull(this.client,"Elasticsearch Client must not be null");
//        checkNotNull(indexName,"index name must not be null");
//        checkArgument(indexName.length() > 0,"index name must not be empty");
//        
//        this.client.admin().indices().prepareCreate(indexName).get();
//    }
//    
//    public void deleteIndex(String indexName){
//        checkNotNull(this.client,"Elasticsearch Client must not be null");
//        checkNotNull(indexName,"index name must not be null");
//        checkArgument(indexName.length() > 0,"index name must not be empty");
//        
//        DeleteIndexResponse delete = this.client.admin().indices().delete(new DeleteIndexRequest(indexName)).actionGet();
//    }
//    
//    public boolean isIndexExist(String indexName){
//        return this.client.admin().indices().prepareExists(indexName).execute().actionGet().isExists();
//    }
//    
//    public PutMappingResponse setTextFieldSortEnable(String indexName,String indexType,String fieldName){
//        String mappingData = "{\n" +                              
//                                "\"properties\": {\n" +
//                                    "\""+fieldName+"\": {\n" +
//                                        "\"type\": \"text\",\n" +
//                                        "\"fielddata\": true\n" + //For set sort text column
//                                    "}\n" +
//                                "}\n" +
//                            "}";
//        PutMappingResponse response = putMapping(indexName, indexType, mappingData);
//        return response;
//    }
//    
//    public PutMappingResponse setTextFieldThaiAnalyzer(String indexName,String indexType,String fieldName){
//        String mappingData = "{\n" +                              
//                                "\"properties\": {\n" +
//                                    "\""+fieldName+"\": {\n" +
//                                        "\"type\": \"text\",\n" +
//                                        "\"analyzer\": \"thai\"\n" +
//                                        /*"\"analysis\": {\n" +
//                                            "\"filter\": {\n" +
//                                                "\"thai_stop\": {\n" +
//                                                    "\"type\": \"stop\",\n" +
//                                                    "\"stopwords\": \"_thai_\"\n" +
//                                                "}\n" +
//                                            "}\n" +
//                                        "},\n" +*/
//                                        /*"\"analyzer\": {\n" +
//                                            "\"thai\": {\n" +
//                                                "\"tokenizer\": \"thai\",\n" +
//                                                "\"filter\": [\n" +
//                                                    "\"lowercase\",\n" +
//                                                    "\"thai_stop\"\n" +
//                                                "]\n" +
//                                            "}\n" +
//                                        "}\n" +*/
//                                    "}\n" +
//                                "}\n" +
//                            "}";
//        PutMappingResponse response = putMapping(indexName, indexType, mappingData);
//        return response;
//    }
//    
//    public PutMappingResponse putMapping(String indexName,String indexType,String mappingData){
//        checkNotNull(this.client,"Elasticsearch Client must not be null");
//        checkNotNull(indexName,"index name must not be null");
//        checkArgument(indexName.length() > 0,"index name must not be empty");
//        checkNotNull(indexType,"index type must not be null");
//        checkArgument(indexType.length() > 0,"index type must not be empty");
//        checkNotNull(mappingData,"mapping data must not be null");
//        checkArgument(mappingData.length() > 0,"mapping data must not be empty");
//        
//        PutMappingResponse response = this.client.admin().indices().preparePutMapping(indexName)
//                                        .setType(indexType)
//                                        .setSource(mappingData)
//                                        .get();
//        
//        return response;
//    }
//    
//    public IndexResponse addData(String indexName,String indexType,String id,String data){
//        checkNotNull(this.client,"Elasticsearch Client must not be null");
//        checkNotNull(indexName,"index name must not be null");
//        checkArgument(indexName.length() > 0,"index name must not be empty");
//        checkNotNull(indexType,"index type must not be null");
//        checkArgument(indexType.length() > 0,"index type must not be empty");
//        checkNotNull(data,"data must not be null");
//        checkArgument(data.length() > 0,"data must not be empty");
//        
//        IndexResponse response;
//
//        if(id==null || id.isEmpty()){
//            response = this.client.prepareIndex(indexName, indexType).setSource(data).get();
//        } else {
//            response = this.client.prepareIndex(indexName, indexType, id).setSource(data).get();
//        }
//        
//        return response;
//    }
//    
//    public GetResponse getData(String indexName,String indexType,String id){
//        checkNotNull(this.client,"Elasticsearch Client must not be null");
//        checkNotNull(indexName,"index name must not be null");
//        checkArgument(indexName.length() > 0,"index name must not be empty");
//        checkNotNull(indexType,"index type must not be null");
//        checkArgument(indexType.length() > 0,"index type must not be empty");
//        checkNotNull(id,"id must not be null");
//        checkArgument(id.length() > 0,"id must not be empty");
//        
//        GetResponse response = this.client.prepareGet(indexName, indexType, id).get();
//        
//        return response;
//    }
//    
//    public UpdateResponse updateData(String indexName,String indexType,String id,String data) throws Exception{
//        checkNotNull(this.client,"Elasticsearch Client must not be null");
//        checkNotNull(indexName,"index name must not be null");
//        checkArgument(indexName.length() > 0,"index name must not be empty");
//        checkNotNull(indexType,"index type must not be null");
//        checkArgument(indexType.length() > 0,"index type must not be empty");
//        checkNotNull(id,"id must not be null");
//        checkArgument(id.length() > 0,"id must not be empty");
//        checkNotNull(data,"data must not be null");
//        checkArgument(data.length() > 0,"data must not be empty");
//        
//        UpdateRequest updateRequest = new UpdateRequest(indexName, indexType, id).doc(data);
//        UpdateResponse response = this.client.update(updateRequest).get();
//        
//        return response;
//    }
//    
//    public DeleteResponse deleteData(String indexName,String indexType,String id){
//        checkNotNull(this.client,"Elasticsearch Client must not be null");
//        checkNotNull(indexName,"index name must not be null");
//        checkArgument(indexName.length() > 0,"index name must not be empty");
//        checkNotNull(indexType,"index type must not be null");
//        checkArgument(indexType.length() > 0,"index type must not be empty");
//        checkNotNull(id,"id must not be null");
//        checkArgument(id.length() > 0,"id must not be empty");
//        
//        DeleteResponse response = this.client.prepareDelete(indexName, indexType, id).get();
//        
//        return response;
//    }
//    
//    public SearchResponse searchData(String indexName,String indexType,QueryBuilder queryBuilder,ArrayList<SortBuilder> arrSortBuilder,String[] fetchSource,HighlightBuilder highlightBuilder,int from,int size){
//        checkNotNull(this.client,"Elasticsearch Client must not be null");
//        checkNotNull(indexName,"index name must not be null");
//        checkArgument(indexName.length() > 0,"index name must not be empty");
//        checkNotNull(indexType,"index type must not be null");
//        checkArgument(indexType.length() > 0,"index type must not be empty");
//        checkNotNull(queryBuilder,"queryBuilder must not be null");
//        checkNotNull(arrSortBuilder,"sortBuilder Array must not be null");
//        
//        SearchRequestBuilder builder = this.client.prepareSearch(indexName);
//        builder.setTypes(indexType);
//        builder.setQuery(queryBuilder);
//        if(fetchSource!=null && fetchSource.length>0){
//            builder.setFetchSource(fetchSource, null);
//        }
//        if(!arrSortBuilder.isEmpty()){
//            for(SortBuilder sortBuilder:arrSortBuilder){
//                builder.addSort(sortBuilder);
//            }
//            
//            builder.setTrackScores(this.showScoreWhenSort);
//        }
//        if(from<0){
//            from = 0;
//        }
//        builder.setFrom(from);
//        if(size>0){
//            builder.setSize(size);
//        }
//        if(this.useScroll){
//            builder.setScroll(scrollTime);
//        }
//
//        if(highlightBuilder!=null){
//            builder.highlighter(highlightBuilder);
//        }
//
//        SearchResponse response = builder.get();
//        
//        return response;
//    }
//    
//    public SearchResponse searchNextDataFromScroll(String scrollId){
//        checkNotNull(this.client,"Elasticsearch Client must not be null");
//        checkNotNull(scrollId,"index name must not be null");
//        checkArgument(scrollId.length() > 0,"index name must not be empty");
//        
//        SearchResponse response = this.client.prepareSearchScroll(scrollId)
//                                                .setScroll(scrollTime)
//                                                .execute()
//                                                .actionGet();
//        
//        return response;
//    }
//    
//    public QueryBuilder advanceSearchDateQuery(String field,String startDate,String endDate){
//        QueryBuilder result = null;
//        
//        if(startDate==null){
//            startDate = "";
//        } else {
//            startDate = dateThaiToEng(startDate);
//        }
//        
//        if(endDate==null){
//            endDate = "";
//        } else {
//            endDate = dateThaiToEng(endDate);
//        }
//        
//        if(!startDate.trim().isEmpty() && !endDate.trim().isEmpty()){
//            result = rangeQuery(field).gte(startDate).lte(endDate).format("dd/MM/yyyy");
//        } else if(!startDate.trim().isEmpty()){
//            result = rangeQuery(field).gte(startDate).format("dd/MM/yyyy");
//        } else if(!endDate.trim().isEmpty()){
//            result = rangeQuery(field).lte(endDate).format("dd/MM/yyyy");
//        }
//        
//        return result;
//    }
//    
//    public QueryBuilder advanceSearchSubTextQuery(String field,String value,String defaultSymbolForSpace,String symbolAnd,String symbolOr,String symbolNot,String symbolWith,String[] wordBriefList){
//        return advanceSearchTextQuery("",field,value,defaultSymbolForSpace,symbolAnd,symbolOr,symbolNot,symbolWith,wordBriefList);
//    }
//    
//    public QueryBuilder advanceSearchFullTextQuery(String field,String value,String defaultSymbolForSpace,String symbolAnd,String symbolOr,String symbolNot,String symbolWith,String[] wordBriefList){
//        return advanceSearchTextQuery("fullText",field,value,defaultSymbolForSpace,symbolAnd,symbolOr,symbolNot,symbolWith,wordBriefList);
//    }
//    
//    public QueryBuilder advanceSearchTextQuery(String searchType,String field,String value,String defaultSymbolForSpace,String symbolAnd,String symbolOr,String symbolNot,String symbolWith,String[] wordBriefList){
//        QueryBuilder result;
//        
//        if(symbolAnd==null || symbolAnd.isEmpty()) { symbolAnd = "&"; }
//        if(symbolOr==null || symbolOr.isEmpty()) { symbolOr = "|"; }
//        if(symbolNot==null || symbolNot.isEmpty()) { symbolNot = "!"; }
//        if(symbolWith==null || symbolWith.isEmpty()) { symbolWith = "^"; }
//        if(defaultSymbolForSpace==null || defaultSymbolForSpace.isEmpty()) { 
//            defaultSymbolForSpace = "|"; 
//        } else {
//            if(defaultSymbolForSpace.equals(symbolAnd)){ defaultSymbolForSpace = "&"; }
//            else if(defaultSymbolForSpace.equals(symbolOr)){ defaultSymbolForSpace = "|"; }
//        }
//        
//        String tmp = replaceAllStr(value,symbolNot,"!");
//        tmp = replaceAllStr(tmp,symbolWith,"^");
//        tmp = replaceAllStr(tmp,symbolAnd,"&");
//        tmp = replaceAllStr(tmp,symbolOr,"|");
//        
//        //-----------Insert Default Symbol for space------------
//        String spaceReplace =  "@@@@@";
//        tmp = replaceSpaceInsideQuote(tmp,spaceReplace);
//        tmp = trimBetweenSymbol(tmp,"&|!^");
//        String[] arrToken = Common.getTokenStrings(tmp," ",false);
//        tmp = "";
//        for(int i=0;i<arrToken.length;i++) {	
//            if(i==arrToken.length-1){tmp+=arrToken[i];}
//            else{tmp+=arrToken[i]+defaultSymbolForSpace;}
//        }
//        
//        String[][] replaceValue = new String[5][2];
//        replaceValue[0][0] = spaceReplace;	replaceValue[0][1] = " ";
//        replaceValue[1][0] = "|";               replaceValue[1][1] = symbolOr;
//        replaceValue[2][0] = "&";		replaceValue[2][1] = symbolAnd;
//        replaceValue[3][0] = "^";		replaceValue[3][1] = symbolWith;
//        replaceValue[4][0] = "!";		replaceValue[4][1] = symbolNot;
//        
//        tmp = replaceStrInsideSymbol(tmp,"\"","\"",replaceValue,""); //Repair symbol in quote that was replaced before
//        tmp = tmp.replaceAll("\"","");//remove quote
//        if (!searchType.equals("fullText")) {
//            //tmp = tmp.replaceAll("\\*","%"); //Replace * with wildcard
//            tmp = replaceAllStr(tmp,"%","*"); //Replace % with wildcard
//            tmp = replaceAllStr(tmp,"^","*"); //Replace ^ with wildcard
//        }
//        
//        //Repair false syntax (have symbol and,or,with at first character)
//        String[] valuetempspace1 = Common.getTokenStrings(tmp,"&|^",true);
//        if(valuetempspace1.length>0) {
//            if((valuetempspace1[0].equals("&"))||(valuetempspace1[0].equals("|"))||(valuetempspace1[0].equals("^"))) {	
//                tmp="";
//                for(int VTS1=1;VTS1<valuetempspace1.length;VTS1++) {	
//                    tmp+=valuetempspace1[VTS1];	 
//                }		
//            }
//        }
//        
//        arrToken = Common.getTokenStrings(tmp,"&|!",true);
//        arrToken = manageOperatorFormat(arrToken);
//        tmp = "";
//        for (String token : arrToken) {
//            tmp += token;	 
//        }
//
//        //-----------------create Query-----------------------
//        BoolQueryBuilder queryOr = boolQuery();
//        String[] arrOr = Common.getTokenStrings(tmp,"\\|",false);
//        for (String tokenOr : arrOr) {
//            String[] arrAnd = Common.getTokenStrings(tokenOr, "&", false);
//            BoolQueryBuilder queryAnd = boolQuery();
//            for (String tokenAnd : arrAnd) {
//                if (tokenAnd.indexOf("!") == 0) {
//                    queryAnd.mustNot(advanceSearchWordListQuery(searchType,field,tokenAnd.substring(1),wordBriefList));
//                } else {
//                    queryAnd.must(advanceSearchWordListQuery(searchType,field,tokenAnd,wordBriefList));
//                }
//            }
//            queryOr.should(queryAnd);
//        }
//        
//        result = queryOr;
//
//        return result;
//    }
//    
//    public QueryBuilder advanceSearchWordListQuery(String searchType,String field,String value,String[] wordBriefList){
//        QueryBuilder result;
//        
//        BoolQueryBuilder query = boolQuery();
//        String separator = "฿";//PxInit.Separator;
//        boolean hasWordList = false;
//        if(wordBriefList!=null){
//            for(String wordBrief : wordBriefList){
//                if(wordBrief!=null){
//                    if(wordBrief.contains(separator+value+separator)){
//                        hasWordList = true;
//
//                        String[] arrWord = Common.getTokenStrings(wordBrief,separator,false);
//                        for(String token : arrWord){
//                            if(!token.isEmpty()) {
//                                if (searchType.equals("fullText")) {
//                                    query.should(fullTextQuery(field, token));
//                                } else {
//                                    query.should(subTextQuery(field, token));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        
//        if(hasWordList){
//            result = query;
//        } else {
//            if (searchType.equals("fullText")) {
//                result = fullTextQuery(field, value);
//            } else {
//                result = subTextQuery(field,value);
//            }
//        }
//        
//        return result;
//    }
//    
//    public QueryBuilder fullTextQuery(String field,String value){
//        return matchQuery(field, value);
//    }
//    
//    public QueryBuilder subTextQuery(String field,String value){
//        return wildcardQuery(field, "*" + value + "*");
//    }
//    
//    public String replaceAllStr(String strValue,String subValue,String replaceValue) {
//        int fromindex=0;
//        String tmpUpper=strValue.toUpperCase();
//        subValue = subValue.toUpperCase();
//
//        String result = "";
//        String tempSub;
//        boolean moreSub=true;
//        int hasSub;
//        while(moreSub) {
//            hasSub=tmpUpper.indexOf(subValue,fromindex);
//            if(hasSub==-1) {	
//                moreSub=false;
//                hasSub=tmpUpper.length();
//                tempSub=strValue.substring(fromindex,hasSub);
//                result=result+tempSub;
//            } else {	
//                tempSub=strValue.substring(fromindex,hasSub);	 
//                result=result+tempSub+replaceValue;
//            }
//
//            fromindex=hasSub+subValue.length();
//        }
//
//        return result;
//    }
//    
//    public String replaceStrInsideSymbol(String strValue,String frontSym,String backSym,String[][] replaceValue,String tempSymbol) {
//        String result = strValue;
//        if(tempSymbol.equals("")) {tempSymbol = "@&#_#&@";}
//
//        while(result.indexOf(frontSym)>=0 
//                && result.indexOf(backSym,result.indexOf(frontSym)+frontSym.length())>=0  
//                && !frontSym.equals("") 
//                && !backSym.equals("")) {
//
//            int startIndex = result.indexOf(frontSym);
//            int endIndex = result.indexOf(backSym,startIndex+frontSym.length());
//            String beforeSym = result.substring(0,startIndex);
//            String inSym = result.substring(startIndex+frontSym.length(),endIndex);
//            String afterSym = result.substring(endIndex+backSym.length());
//
//            for(int TNT=0;TNT<replaceValue.length;TNT++) {
//                inSym = replaceAllStr(inSym,replaceValue[TNT][0],replaceValue[TNT][1]);
//            }
//
//            result = beforeSym+tempSymbol+inSym+tempSymbol+afterSym;
//
//        }
//
//        boolean isFront = true;
//        while(result.indexOf(tempSymbol)>=0) {
//            if (isFront) {
//                result = result.replaceFirst(tempSymbol,frontSym);
//                isFront = false;
//            } else {
//                result = result.replaceFirst(tempSymbol,backSym);
//                isFront = true;
//            }
//        }
//
//        return result;
//    }
//    
//    public String replaceSpaceInsideQuote(String strValue,String spaceReplace) {
//        String result = "";
//        boolean haveFix = false;
//        char[] tempChar = strValue.toCharArray();
//        for(int TNT=0;TNT<tempChar.length;TNT++) {
//            switch (tempChar[TNT]) {
//                case '\"':
//                    if (haveFix) {
//                        result += "\"";
//                        haveFix = false;
//                    } else {
//                        result += "\"";
//                        haveFix = true;
//                    }   
//                    break;
//                case ' ':
//                    if (haveFix) { result += spaceReplace; }
//                    else { result += " "; }
//                    break;
//                default:
//                    result += tempChar[TNT];
//                    break;
//            }
//
//        }
//        if(haveFix) {result+="\"";}
//
//        return result;
//    }
//    
//    public String trimBetweenSymbol(String strValue,String symBol) {
//        String result = "";
//        String[] valuetemp = Common.getTokenStrings(strValue,symBol,true);
//        for(int i=0;i<valuetemp.length;i++) {	
//            result += valuetemp[i].trim();
//        }
//
//        return result;
//    }
//    
//    public String[] insertStringArray (String[] strArray,String strValue,int indexValue) {
//        String[] resultArray = new String[strArray.length+1];
//        for (int i =0;i<indexValue;i++) {
//            resultArray[i] = strArray[i];
//        }
//
//        resultArray[indexValue] = strValue;
//
//        for (int i =indexValue;i<strArray.length;i++) {
//            resultArray[i+1] = strArray[i];
//        }
//
//        return resultArray;
//    }
//    
//    public String[] manageOperatorFormat (String[] strArray) {
//        if (strArray.length>0) {
//            if(strArray[0].equals("&") || strArray[0].equals("|")) {
//                strArray = insertStringArray (strArray,"฿฿฿฿฿฿",0);
//            } 
//
//
//            if (strArray.length>1) {
//                if(!(strArray[0].equals("&") || strArray[0].equals("|")) && strArray[1].equals("!")) {
//                    strArray = insertStringArray (strArray,"&",1);
//                }
//            }
//
//            if(strArray[strArray.length-1].equals("&") || strArray[strArray.length-1].equals("|") || strArray[strArray.length-1].equals("!")) {
//                strArray = insertStringArray (strArray,"฿฿฿฿฿฿",strArray.length);
//            }
//
//            for (int i=1;i<strArray.length-1;i++) {
//                if((strArray[i].equals("&") || strArray[i].equals("|")) 
//                    && (strArray[i-1].equals("&") || strArray[i-1].equals("|"))) {
//                        strArray = insertStringArray (strArray,"฿฿฿฿฿฿",i);
//                } else if (strArray[i].equals("!")
//                            &&(strArray[i+1].equals("&") || strArray[i+1].equals("|"))) {
//                        strArray = insertStringArray (strArray,"฿฿฿฿฿฿",i+1);
//                } else if(!(strArray[i].equals("&") || strArray[i].equals("|")) && strArray[i+1].equals("!")) {
//                        strArray = insertStringArray (strArray,"&",i+1);
//                } /*else if (strArray[i].equals("!")
//                                                && !(strArray[i-1].equals("&") || strArray[i-1].equals("|"))) {
//                                strArray = insertStringArray (strArray,"&",i);
//                }*/
//            }
//        }
//
//        return strArray;
//    }
//    
//    public String dateThaiToEng(String inputDate){
//        String result = "";
//        if(inputDate!=null){
//            String[] tmpList = inputDate.split("/");
//            if(tmpList.length>=3){
//                int year = Integer.parseInt(tmpList[2]);
//                result = tmpList[0]+"/"+tmpList[1]+"/"+(year-543);
//            }
//        }
//        return result;
//    }
//    
//}
