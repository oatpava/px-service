package com.px.share.util;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author peach
 */
public class AdvanceSearch {
    private static final Logger LOG = Logger.getLogger(AdvanceSearch.class.getName());

    public AdvanceSearch() {
    }
    
    public Criterion advanceSearchTextQuery(String field,String value,String defaultSymbolForSpace,String symbolAnd,String symbolOr,String symbolNot,String symbolWith,String[] wordBriefList){
        Criterion result;
        
        if(symbolAnd==null || symbolAnd.isEmpty()) { symbolAnd = "&"; }
        if(symbolOr==null || symbolOr.isEmpty()) { symbolOr = "|"; }
        if(symbolNot==null || symbolNot.isEmpty()) { symbolNot = "!"; }
        if(symbolWith==null || symbolWith.isEmpty()) { symbolWith = "^"; }
        if(defaultSymbolForSpace==null || defaultSymbolForSpace.isEmpty()) { 
            defaultSymbolForSpace = "|"; 
        } else {
            if(defaultSymbolForSpace.equals(symbolAnd)){ defaultSymbolForSpace = "&"; }
            else if(defaultSymbolForSpace.equals(symbolOr)){ defaultSymbolForSpace = "|"; }
        }
        
        String tmp = replaceAllStr(value,symbolNot,"!");
        tmp = replaceAllStr(tmp,symbolWith,"^");
        tmp = replaceAllStr(tmp,symbolAnd,"&");
        tmp = replaceAllStr(tmp,symbolOr,"|");
        
        //-----------Insert Default Symbol for space------------
        String spaceReplace =  "@@@@@";
        tmp = replaceSpaceInsideQuote(tmp,spaceReplace);
        tmp = trimBetweenSymbol(tmp,"&|!^");
        String[] arrToken = Common.getTokenStrings(tmp," ",false);
        tmp = "";
        for(int i=0;i<arrToken.length;i++) {	
            if(i==arrToken.length-1){tmp+=arrToken[i];}
            else{tmp+=arrToken[i]+defaultSymbolForSpace;}
        }
        
        String[][] replaceValue = new String[5][2];
        replaceValue[0][0] = spaceReplace;	replaceValue[0][1] = " ";
        replaceValue[1][0] = "|";               replaceValue[1][1] = symbolOr;
        replaceValue[2][0] = "&";		replaceValue[2][1] = symbolAnd;
        replaceValue[3][0] = "^";		replaceValue[3][1] = symbolWith;
        replaceValue[4][0] = "!";		replaceValue[4][1] = symbolNot;
        
        tmp = replaceStrInsideSymbol(tmp,"\"","\"",replaceValue,""); //Repair symbol in quote that was replaced before
        tmp = tmp.replaceAll("\"","");//remove quote
        
        //tmp = tmp.replaceAll("\\*","%"); //Replace * with wildcard
        //tmp = replaceAllStr(tmp,"%","*"); //Replace % with wildcard
        //tmp = replaceAllStr(tmp,"^","*"); //Replace ^ with wildcard
        
        //Repair false syntax (have symbol and,or,with at first character)
        String[] valuetempspace1 = Common.getTokenStrings(tmp,"&|^",true);
        if(valuetempspace1.length>0) {
            if((valuetempspace1[0].equals("&"))||(valuetempspace1[0].equals("|"))||(valuetempspace1[0].equals("^"))) {	
                tmp="";
                for(int VTS1=1;VTS1<valuetempspace1.length;VTS1++) {	
                    tmp+=valuetempspace1[VTS1];	 
                }		
            }
        }
        
        arrToken = Common.getTokenStrings(tmp,"&|!",true);
        arrToken = manageOperatorFormat(arrToken);
        tmp = "";
        for (String token : arrToken) {
            tmp += token;	 
        }

        //-----------------create Query-----------------------
        Disjunction queryOr = Restrictions.disjunction();
        String[] arrOr = Common.getTokenStrings(tmp,"\\|",false);
        for (String tokenOr : arrOr) {
            String[] arrAnd = Common.getTokenStrings(tokenOr, "&", false);
            Conjunction queryAnd = Restrictions.conjunction();
            for (String tokenAnd : arrAnd) {
                if (tokenAnd.indexOf("!") == 0) {
                    queryAnd.add(Restrictions.not(advanceSearchWordListQuery(field,tokenAnd.substring(1),wordBriefList)));
                } else {
                    queryAnd.add(advanceSearchWordListQuery(field,tokenAnd,wordBriefList));
                }
            }
            queryOr.add(queryAnd);
        }
        
        result = queryOr;

        return result;
    }
    
    public Criterion advanceSearchWordListQuery(String field,String value,String[] wordBriefList){
        Criterion result;
        
        Disjunction query = Restrictions.disjunction();
        String separator = "฿";//PxInit.Separator;
        boolean hasWordList = false;
        if(wordBriefList!=null){
            for(String wordBrief : wordBriefList){
                if(wordBrief!=null){
                    if(wordBrief.contains(separator+value+separator)){
                        hasWordList = true;

                        String[] arrWord = Common.getTokenStrings(wordBrief,separator,false);
                        for(String token : arrWord){
                            if(!token.isEmpty()) {
                                query.add(Restrictions.like(field, token, MatchMode.ANYWHERE));
                            }
                        }
                    }
                }
            }
        }
        
        if(!hasWordList){
            query.add(Restrictions.like(field, value, MatchMode.ANYWHERE));
        }
        
        result = query;
        
        return result;
    }
    
    public String replaceAllStr(String strValue,String subValue,String replaceValue) {
        int fromindex=0;
        String tmpUpper=strValue.toUpperCase();
        subValue = subValue.toUpperCase();

        String result = "";
        String tempSub;
        boolean moreSub=true;
        int hasSub;
        while(moreSub) {
            hasSub=tmpUpper.indexOf(subValue,fromindex);
            if(hasSub==-1) {	
                moreSub=false;
                hasSub=tmpUpper.length();
                tempSub=strValue.substring(fromindex,hasSub);
                result=result+tempSub;
            } else {	
                tempSub=strValue.substring(fromindex,hasSub);	 
                result=result+tempSub+replaceValue;
            }

            fromindex=hasSub+subValue.length();
        }

        return result;
    }
    
    public String replaceStrInsideSymbol(String strValue,String frontSym,String backSym,String[][] replaceValue,String tempSymbol) {
        String result = strValue;
        if(tempSymbol.equals("")) {tempSymbol = "@&#_#&@";}

        while(result.indexOf(frontSym)>=0 
                && result.indexOf(backSym,result.indexOf(frontSym)+frontSym.length())>=0  
                && !frontSym.equals("") 
                && !backSym.equals("")) {

            int startIndex = result.indexOf(frontSym);
            int endIndex = result.indexOf(backSym,startIndex+frontSym.length());
            String beforeSym = result.substring(0,startIndex);
            String inSym = result.substring(startIndex+frontSym.length(),endIndex);
            String afterSym = result.substring(endIndex+backSym.length());

            for(int TNT=0;TNT<replaceValue.length;TNT++) {
                inSym = replaceAllStr(inSym,replaceValue[TNT][0],replaceValue[TNT][1]);
            }

            result = beforeSym+tempSymbol+inSym+tempSymbol+afterSym;

        }

        boolean isFront = true;
        while(result.indexOf(tempSymbol)>=0) {
            if (isFront) {
                result = result.replaceFirst(tempSymbol,frontSym);
                isFront = false;
            } else {
                result = result.replaceFirst(tempSymbol,backSym);
                isFront = true;
            }
        }

        return result;
    }
    
    public String replaceSpaceInsideQuote(String strValue,String spaceReplace) {
        String result = "";
        boolean haveFix = false;
        char[] tempChar = strValue.toCharArray();
        for(int TNT=0;TNT<tempChar.length;TNT++) {
            switch (tempChar[TNT]) {
                case '\"':
                    if (haveFix) {
                        result += "\"";
                        haveFix = false;
                    } else {
                        result += "\"";
                        haveFix = true;
                    }   
                    break;
                case ' ':
                    if (haveFix) { result += spaceReplace; }
                    else { result += " "; }
                    break;
                default:
                    result += tempChar[TNT];
                    break;
            }

        }
        if(haveFix) {result+="\"";}

        return result;
    }
    
    public String trimBetweenSymbol(String strValue,String symBol) {
        String result = "";
        String[] valuetemp = Common.getTokenStrings(strValue,symBol,true);
        for(int i=0;i<valuetemp.length;i++) {	
            result += valuetemp[i].trim();
        }

        return result;
    }
    
    public String[] insertStringArray (String[] strArray,String strValue,int indexValue) {
        String[] resultArray = new String[strArray.length+1];
        for (int i =0;i<indexValue;i++) {
            resultArray[i] = strArray[i];
        }

        resultArray[indexValue] = strValue;

        for (int i =indexValue;i<strArray.length;i++) {
            resultArray[i+1] = strArray[i];
        }

        return resultArray;
    }
    
    public String[] manageOperatorFormat (String[] strArray) {
        if (strArray.length>0) {
            if(strArray[0].equals("&") || strArray[0].equals("|")) {
                strArray = insertStringArray (strArray,"฿฿฿฿฿฿",0);
            } 


            if (strArray.length>1) {
                if(!(strArray[0].equals("&") || strArray[0].equals("|")) && strArray[1].equals("!")) {
                    strArray = insertStringArray (strArray,"&",1);
                }
            }

            if(strArray[strArray.length-1].equals("&") || strArray[strArray.length-1].equals("|") || strArray[strArray.length-1].equals("!")) {
                strArray = insertStringArray (strArray,"฿฿฿฿฿฿",strArray.length);
            }

            for (int i=1;i<strArray.length-1;i++) {
                if((strArray[i].equals("&") || strArray[i].equals("|")) 
                    && (strArray[i-1].equals("&") || strArray[i-1].equals("|"))) {
                        strArray = insertStringArray (strArray,"฿฿฿฿฿฿",i);
                } else if (strArray[i].equals("!")
                            &&(strArray[i+1].equals("&") || strArray[i+1].equals("|"))) {
                        strArray = insertStringArray (strArray,"฿฿฿฿฿฿",i+1);
                } else if(!(strArray[i].equals("&") || strArray[i].equals("|")) && strArray[i+1].equals("!")) {
                        strArray = insertStringArray (strArray,"&",i+1);
                } /*else if (strArray[i].equals("!")
                                                && !(strArray[i-1].equals("&") || strArray[i-1].equals("|"))) {
                                strArray = insertStringArray (strArray,"&",i);
                }*/
            }
        }

        return strArray;
    }
}
