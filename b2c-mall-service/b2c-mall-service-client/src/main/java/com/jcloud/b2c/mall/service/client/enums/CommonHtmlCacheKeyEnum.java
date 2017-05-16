package com.jcloud.b2c.mall.service.client.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *MallIndexPageServiceImpl 类中 各种html片段缓存是的key
 * Created by shiyusen on 2017/4/7.
 */
public enum CommonHtmlCacheKeyEnum implements Serializable {



    COMMON_HEADER_KEY("b2c_common_header_key_",""),
    COMMON_FOOTER_KEY("b2c_common_footer_key_",""),
    COMMON_CHANNEL_KEY("b2c_common_channel_key_",""),
    COMMON_HEADER_FRAGMENT_KEY("b2c_common_header_fragment_key_",""),
    COMMON_PAY_HEADER_KEY("b2c_pay_header_key_",""),
    SIMPLE_HEADER_KEY("b2c_simple_header_key_",""),
    B2C_INDEX_PAGE_CACHE_KEY("b2c_index_page_cache_key_",""),
    B2C_H5_INDEX_PAGE_CACHE_KEY("b2c_h5_index_page_cache_key_","")
    ;

    private String key;
    private String name;

    CommonHtmlCacheKeyEnum() {

    }
    CommonHtmlCacheKeyEnum(String key, String name) {
        this.key = key;
        this.name=name;
    }

    public static List<String> getKeys(){
        return getKeys(null);
    }
    public static List<String> getKeys(Long tenantId){
        List<String> keys=new ArrayList<String>();
        CommonHtmlCacheKeyEnum[] arr=CommonHtmlCacheKeyEnum.values();
        if(tenantId==null){
            for(int i=0;i<arr.length;i++){
                keys.add(arr[i].getKey());
            }
        }else{
            for(int i=0;i<arr.length;i++){
                keys.add(arr[i].getKey()+tenantId);
            }
        }

        return keys;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
