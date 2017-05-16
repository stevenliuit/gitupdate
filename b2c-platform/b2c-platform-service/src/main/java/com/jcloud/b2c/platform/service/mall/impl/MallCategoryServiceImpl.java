/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallCategoryServiceImpl.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/25
 */

package com.jcloud.b2c.platform.service.mall.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.vo.MallCategoryVo;
import com.jcloud.b2c.platform.domain.PageTypeEnum;
import com.jcloud.b2c.platform.service.feign.MallCategoryClient;
import com.jcloud.b2c.platform.service.mall.MallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @description: MALL CATEGORY SERVICE IMPL 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-25 14:13
 * @lastdate:
 */

@Service("mallCategoryService")
public class MallCategoryServiceImpl implements MallCategoryService{
    @Autowired
    MallCategoryClient mallCategoryClient;
    private static Integer MAX_LEVEL = 2;

    public MallCategoryVo getCategory(Long id,Long tenantId){
        return mallCategoryClient.get(id,tenantId).getData();
    }

    public Integer getCategoryMaxLevel(ClientTypeEnum ct, PageTypeEnum pageType){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put(ClientTypeEnum.PC.getName()+PageTypeEnum.INDEX.getName(), 3);
        //map.put(ClientTypeEnum.PC.getName()+PageTypeEnum.CHANNEL.getName(), 2);
        Integer maxLevel = map.get(ct.getName() + pageType.getName());
        return maxLevel==null?MAX_LEVEL:maxLevel;
    }

    //id-level,category
    public Map<String, MallCategoryVo> getCategoryMap(MallCategoryVo mallCategoryVo){
        List<MallCategoryVo> list = mallCategoryClient.query(mallCategoryVo).getData();
        if(list == null || list.size() == 0){
            return null;
        }

        PageTypeEnum pageType = PageTypeEnum.INDEX;
        if(mallCategoryVo.getChannelId()>0L){
            pageType = PageTypeEnum.CHANNEL;
        }
        return this.getCategoryMap(list, this.getCategoryMaxLevel(ClientTypeEnum.getClientTypeByValue(mallCategoryVo.getClientType()),pageType));
    }

    private static Map<String, MallCategoryVo> getCategoryMap(List<MallCategoryVo> list, Integer maxLevel){
        Map<String, MallCategoryVo> mallCategoryMap = new LinkedHashMap<String, MallCategoryVo>();
        List<Long> parentIdList = new ArrayList<Long>();
        Integer level = 1;
        while (list.size()>0){
            if(level == 1){
                parentIdList.add(0L);
            }
            putLevelCategory(mallCategoryMap, level, list, parentIdList);
            level ++;
            //大于设定的最大层级
            if(level > maxLevel){
                break;
            }
        }
        return mallCategoryMap;
    }

    //根据父ID查找当前层级的元素
    private static void putLevelCategory(Map<String, MallCategoryVo> mallCategoryMap,Integer level, List<MallCategoryVo> mallCategoryList, List<Long> parentIdList){
        List<Long> newParentIdList = new ArrayList<Long>();
        Iterator<MallCategoryVo> iterator = mallCategoryList.iterator();
        while (iterator.hasNext()){
            MallCategoryVo mc = iterator.next();
            if(parentIdList.contains(mc.getParentId())){
                mallCategoryMap.put(mc.getId() + "-" + level, mc);
                newParentIdList.add(mc.getId());
                iterator.remove();
            }
        }
        parentIdList.clear();
        for(Long parentId : newParentIdList){
            parentIdList.add(parentId);
        }
    }

    public static void main(String[] args){
       /**  List<MallCategoryVo> list = new ArrayList<MallCategoryVo>();
        putMallCategoryVo(list, 10L, 0L, "1级1");
        putMallCategoryVo(list, 11L, 0L, "1级2");
        putMallCategoryVo(list, 12L, 10L, "2级1-1");
        putMallCategoryVo(list, 13L, 10L, "2级1-2");
        putMallCategoryVo(list, 14L, 11L, "2级2-1");
        putMallCategoryVo(list, 15L, 11L, "2级2-2");
        putMallCategoryVo(list, 16L, 12L, "3级1-1-1");
        putMallCategoryVo(list, 17L, 12L, "3级1-2-2");
        putMallCategoryVo(list, 18L, 13L, "3级2-1-1");
        try {
            Map<String, MallCategoryVo> map = getCategoryMap(list);
            System.out.println(map.size());
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String,MallCategoryVo> map = JSON.parseObject("{\"1-0\":{\"clientType\":1,\"created\":1487582046000,\"heighlight\":0,\"id\":1,\"modified\":1487582048000,\"name\":\"一级-1\",\"parentId\":0,\"sortNum\":1,\"state\":1,\"tenantId\":1},\"2-0\":{\"clientType\":1,\"created\":1487582057000,\"heighlight\":0,\"id\":2,\"modified\":1487582059000,\"name\":\"一级-2\",\"parentId\":0,\"sortNum\":1,\"state\":1,\"tenantId\":1},\"3-1\":{\"clientType\":1,\"created\":1487583278000,\"heighlight\":0,\"id\":3,\"modified\":1487583279000,\"name\":\"二级-1-1\",\"parentId\":1,\"sortNum\":1,\"state\":1,\"tenantId\":1},\"4-1\":{\"clientType\":1,\"created\":1487584655000,\"heighlight\":0,\"id\":4,\"modified\":1487584657000,\"name\":\"二级-1-2\",\"parentId\":1,\"sortNum\":1,\"state\":1,\"tenantId\":1},\"5-2\":{\"clientType\":1,\"created\":1487584682000,\"heighlight\":1,\"id\":5,\"modified\":1487584683000,\"name\":\"三级-1-1-1\",\"parentId\":3,\"sortNum\":1,\"state\":1,\"tenantId\":1},\"6-2\":{\"clientType\":1,\"created\":1487591184000,\"heighlight\":0,\"id\":6,\"modified\":1487591186000,\"name\":\"三级-1-2-1\",\"parentId\":4,\"sortNum\":1,\"state\":1,\"tenantId\":1}}",
                new TypeReference<Map<String, MallCategoryVo>>() {});

        System.out.println(map.size()); */
    }

    private static void putMallCategoryVo(List<MallCategoryVo> list, Long id, Long parentId, String name){
        MallCategoryVo c1 = new MallCategoryVo();
        c1.setParentId(parentId);
        c1.setId(id);
        c1.setName(name);
        list.add(c1);
    }

    public static class MallCategoryMap extends HashMap<String, MallCategoryVo> {
    }
}

