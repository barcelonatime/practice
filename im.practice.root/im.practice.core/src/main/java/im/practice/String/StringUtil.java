package im.practice.String;

import org.apache.commons.lang3.StringUtils;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.String$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class StringUtil {
    public static void main(String[] args){
        String sourceUrl="http://slb-shy.bestvcdn.com/gslb/program/FDN/P100020202020202020181226001_1/1300/stream.m3u8?_mdCode=110020202020202020181226001&=BTV&_type=1&_rCode=TerOut_18445&_userId=020341000019228&_categoryPath=SMG_LG,SMG_LG_DY,&_adPositionId=01001000&_adCategorySource=0&token_mod=&_client=103&taskID=ysh_ps_002-ott_1395024705346_020341000019228&_cms=ctv&_flag=.m3u8";
        String a=sourceUrl.replaceAll("((.*)(://)(.*)@([^/]*)/)|((.*)(://)([^/]*)/)|(^/)","");
        System.out.println(a);
        
        sourceUrl=StringUtils.substringBefore(sourceUrl.replaceAll("((.*)(://)(.*)@([^/]*)/)|((.*)(://)([^/]*)/)|(^/)", ""), "?");
        System.out.println(sourceUrl);
    }
}
