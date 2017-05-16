package com.jcloud.b2c.mall.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.jcloud.b2c.common.client.CommonClient;
import com.jcloud.b2c.common.client.vo.CommonClientRequest;
import com.jcloud.b2c.common.client.vo.ProductSyncEsRequest;
import com.jcloud.b2c.common.common.dictionary.ImageSizeEnum;
import com.jcloud.b2c.common.common.dictionary.OpenApiMethodEnum;
import com.jcloud.b2c.common.common.util.ImageUtils;
import com.jcloud.b2c.common.common.util.PriceUtils;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.item.ItemBasicInfoVo;
import com.jcloud.b2c.item.client.vo.item.ItemDetailVo;
import com.jcloud.b2c.mall.service.client.enums.AdPrincipalTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.client.vo.EsItemVo;
import com.jcloud.b2c.mall.service.domain.MallCategory;
import com.jcloud.b2c.mall.service.domain.MallPrincipalItem;
import com.jcloud.b2c.mall.service.mapper.MallCategoryMapper;
import com.jcloud.b2c.mall.service.mapper.MallChannelMapper;
import com.jcloud.b2c.mall.service.mapper.MallPrincipalItemMapper;
import com.jcloud.b2c.mall.service.service.EsItemService;
import com.jcloud.b2c.mall.service.service.feign.CacheFeignClient;
import com.jcloud.b2c.mall.service.service.feign.EsClient;
import com.jcloud.b2c.mall.service.service.feign.EsIndexClient;
import com.jcloud.b2c.mall.service.service.feign.ItemClient;
import com.jd.ecc.search.client.param.SearchCondition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


/**
 * Created by issuser on 2017/3/2.
 */
@Service("esItemService")
public class EsItemServiceImpl implements EsItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsItemServiceImpl.class);

    private static String ES_TYPE_NAME = "type-search-item";
    private static String ES_INDEX_CONFIG = "/es-config/item_index.json";
    private static String ES_MAPPING_CONFIG = "/es-config/item_mapping.json";
    private static String CHARSET = "UTF-8";
    private static int ADD_UPDATE_OP = 1;
    private static int DELETE_OP = 2;

    @Value("${default.areaId: 1-72-2799-0}")
    private String defaultArea;

    @Resource
    private EsIndexClient esIndexClient;

    @Resource
    private EsClient esClient;

    @Resource
    private CacheFeignClient cacheFeignClient;

    @Autowired
    private MallPrincipalItemMapper mallPrincipalItemMapper;

    @Autowired
    private MallCategoryMapper mallCategoryMapper;

    @Autowired
    private MallChannelMapper mallChannelMapper;

    @Autowired
    private ItemClient itemClient;

    @Resource
    private CommonClient commonClient;

    @Override
    public void putIntoEs(Long itemId, Long tenantId) {
        Map<Long, EsItemVo> map = new HashMap();
        StringBuffer categoryString = new StringBuffer();
        StringBuffer channelCategories = new StringBuffer();
        StringBuffer appCatString = new StringBuffer();
        EsItemVo esItemVo = new EsItemVo();
        MallPrincipalItem mallPrincipalItem = new MallPrincipalItem();
        //用于查出频道二级类目
//        MallCategory mallChannelCategories = new MallCategory();
//        mallChannelCategories.setTenantId(tenantId);
        //用于查出频道一级类目
        MallCategory mallChannelOne = new MallCategory();
        mallChannelOne.setTenantId(tenantId);
        //用于查三级类目
        MallCategory mallCategory = new MallCategory();
        mallCategory.setTenantId(tenantId);
        //用于查询二级一级类目
        MallCategory categoryTwo = new MallCategory();
        categoryTwo.setTenantId(tenantId);
        //用于查出一级类目
        MallCategory categoryOne = new MallCategory();
        categoryOne.setTenantId(tenantId);
        mallPrincipalItem.setTenantId(tenantId);
        mallPrincipalItem.setItemId(itemId);
        mallPrincipalItem.setState(StateEnum.ON_SHELF.getValue());
        mallPrincipalItem.setPrincipalType(AdPrincipalTypeEnum.CATEGORY.getValue());
        //根据一个商品找到类目集合
        List<MallPrincipalItem> list = mallPrincipalItemMapper.querySelective(mallPrincipalItem);
        if (CollectionUtils.isEmpty(list)) {
            esItemVo.setItemId(itemId);
            this.putIntoEs(tenantId, esItemVo, DELETE_OP);
            return;
        }
        //遍历集合
        for (MallPrincipalItem principalItem1 : list) {
            if (map.get(itemId) != null) {
                esItemVo = map.get(itemId);
            } else {
                map.put(itemId, esItemVo);
            }
            Long PrincipalId = principalItem1.getPrincipalId();
            mallCategory.setId(PrincipalId);
            MallCategory category = mallCategoryMapper.getByPrimaryKey(mallCategory);
            //等于0时说明是首页类目，首页类目一共三级
            if (category.getChannelId() == 0) {
                //ClientType当等于3时，说明是APP类目
                if (category.getClientType() == 3) {
                    esItemVo.setAppCat2Id(category.getId());
                    esItemVo.setAppCat2Name(category.getName());
                    appCatString.append(category.getId()).append(":").append(category.getName()).append(";");
                    //查出一级类目
                    if (category.getParentId() != 0) {
                        categoryOne.setId(category.getParentId());
                        categoryOne = mallCategoryMapper.getByPrimaryKey(categoryOne);
                        esItemVo.setAppCat1Id(categoryOne.getId());
                        esItemVo.setAppCat1Name(categoryOne.getName());
                        appCatString.append(categoryOne.getId()).append(":").append(categoryOne.getName()).append(";");
                    }
                    esItemVo.setAppCatString(appCatString.toString());
                    continue;
                }
                esItemVo.setCat3Id(category.getId());
                esItemVo.setCat3Name(category.getName());
                categoryString.append(category.getId()).append(":").append(category.getName()).append(";");
                //查出二级类目
                if (category.getParentId() != 0) {
                    categoryTwo.setId(category.getParentId());
                    categoryTwo = mallCategoryMapper.getByPrimaryKey(categoryTwo);
                    esItemVo.setCat2Id(categoryTwo.getId());
                    esItemVo.setCat2Name(categoryTwo.getName());
                    categoryString.append(categoryTwo.getId()).append(":").append(categoryTwo.getName()).append(";");
                    //查出一级类目
                    if (categoryTwo.getParentId() != 0) {
                        categoryOne.setId(categoryTwo.getParentId());
                        categoryOne = mallCategoryMapper.getByPrimaryKey(categoryOne);
                        esItemVo.setCat1Id(categoryOne.getId());
                        esItemVo.setCat1Name(categoryOne.getName());
                        categoryString.append(categoryOne.getId()).append(":").append(categoryOne.getName()).append(";");
                    }
                }
                esItemVo.setCategoryString(categoryString.toString());
            }//当不等于0时说明是频道类目，频道类目有两级
            else {
                //查出频道二级类目信息
                channelCategories.append(category.getId()).append(":").append(category.getName()).append(";");
                //查出频道一级类目信息
                if (category.getParentId() != 0) {
                    mallChannelOne.setId(category.getParentId());
                    mallChannelOne = mallCategoryMapper.getByPrimaryKey(mallChannelOne);
                    channelCategories.append(mallChannelOne.getId()).append(":").append(mallChannelOne.getName()).append(";");
                } else {
                    continue;
                }
                esItemVo.setChannelCategories(channelCategories.toString());
            }
        }
        BaseResponseVo<ItemBasicInfoVo> baseResponseVo = itemClient.getItemBasicInfo(tenantId, tenantId, itemId);
        if (baseResponseVo.getData() != null) {
            esItemVo.setItemId(baseResponseVo.getData().getSkuId());
            String itemName = baseResponseVo.getData().getSkuName();
            if (StringUtils.isBlank(itemName)) {
                LOGGER.warn("item name is null, ItemDetailVo={}", baseResponseVo.getData());
                return;
            }
            esItemVo.setItemName(baseResponseVo.getData().getSkuName());
            String mainImg = baseResponseVo.getData().getImgUrl();
            esItemVo.setItemImgSrc(mainImg);
            esItemVo.setBrandName(baseResponseVo.getData().getBrandName());
            esItemVo.setBrandId(baseResponseVo.getData().getBrandId());

            try {
                String state = itemClient.findItemStock(tenantId, tenantId, itemId, defaultArea.replaceAll("-", ",")).getData();
                int stateInt = "1".equals(state) ? 1 : 0;
                esItemVo.setState(stateInt);
                LOGGER.info("调用可售状态接口 state=" + state + ";stateInt=" + stateInt);
            } catch (Exception e) {
                LOGGER.error("调用可售状态接口异常itemId=" + itemId, e);
                esItemVo.setState(0);
            }
//            esItemVo.setState(new Integer(34).equals(baseResponseVo.getData().getStockState()) ? 0 : 1);
            esItemVo.setPrice(PriceUtils.priceFormat(baseResponseVo.getData().getPrice()));
            esItemVo.setMarketPrice(PriceUtils.priceFormat(baseResponseVo.getData().getMarketprice()));
        }
        this.putIntoEs(tenantId, esItemVo, ADD_UPDATE_OP);
    }

    @Override
    public void putIntoEsTask(Long tenantId, List<Long> itemIds) {
        LOGGER.info("begin put into es task tenantId={}, itemIds={}", tenantId, itemIds);

        ProductSyncEsRequest request = new ProductSyncEsRequest();
        List<String> itemIdStrs = new ArrayList();
        for (Long itemId : itemIds) {
            itemIdStrs.add(String.valueOf(itemId));
        }
        request.setTenantId(tenantId);
        request.setSkuList(itemIdStrs);

        CommonClientRequest commonClientRequest = new CommonClientRequest();
        commonClientRequest.setMethod(OpenApiMethodEnum.PRODUCT_SYNC_ES.getCode());
        commonClientRequest.setRequestParam(request);

        String result = commonClient.api(JSON.toJSONString(commonClientRequest));
        LOGGER.info("put into es task result:{}", result);
    }

    @Override
    public void putJsonMapping(Long tenantId) {
        org.springframework.core.io.Resource resource = new ClassPathResource(ES_MAPPING_CONFIG);
        String mappingJson;
        try {
            mappingJson = StreamUtils.copyToString(resource.getInputStream(), Charset.forName(CHARSET));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean flag = esIndexClient.putJsonMapping(tenantId, ES_TYPE_NAME, mappingJson);
        LOGGER.info("putJsonMapping, tenantId={}, result={}", tenantId, flag);
        if (!flag) {
            throw new RuntimeException("create mapping fail");
        }
    }

    @Override
    public void deleteIndex(Long tenantId) {
        esIndexClient.deleteIndex(tenantId);
    }

    /**
     * 同步数据到ES
     *
     * @param tenantId
     * @param esItemVo
     * @param type     1、新增或更新   2、删除
     * @throws IOException
     */
    public void putIntoEs(Long tenantId, EsItemVo esItemVo, int type) {
        LOGGER.info("begin put into es, tenantId={}, esItemVo={}, type={}", tenantId, esItemVo, type);

        String lockKey = "b2c_put_into_es_lock_key_" + tenantId + "_" + esItemVo.getItemId();
        try {
            if (!acquireLock(tenantId, lockKey, 1000 * 20)) {
                LOGGER.info("the thread don't get the lock, tenantId={}, esItemVo={}, type={}", tenantId, esItemVo, type);
                return;
            }
        } catch (Exception e) {
            LOGGER.error("put into es lock error, " + e.getMessage(), e);
        }

        try {
            boolean isIndexExists = esIndexClient.isIndexExists(tenantId);
            LOGGER.info("isIndexExists={}, tenantId={}", isIndexExists, tenantId);
            if (!isIndexExists) {//如果不存在索引
                org.springframework.core.io.Resource resource = new ClassPathResource(ES_INDEX_CONFIG);
                String indexJson;
                try {
                    indexJson = StreamUtils.copyToString(resource.getInputStream(), Charset.forName(CHARSET));
                } catch (IOException e) {
                    LOGGER.error("tenantId=" + tenantId, e);
                    throw new RuntimeException(e);
                }
                boolean flag = esIndexClient.createSettingsIndexByJson(tenantId, indexJson);
                LOGGER.info("createSettingsIndexByJson tenantId={}, result = {}", tenantId, flag);
                if (!flag) {
                    throw new RuntimeException("create index fail");
                }
            }
            boolean isTypeExists = esIndexClient.isTypeExists(tenantId, ES_TYPE_NAME);
            LOGGER.info("isTypeExists tenantId={}, result = {}", tenantId, isTypeExists);
            if (!isTypeExists) {//如果不存在type
                this.putJsonMapping(tenantId);
            }
            SearchCondition searchCondition = SearchCondition.getInstance().page(1).size(1).eq("itemId", esItemVo.getItemId());
            List<Map<String, Object>> result = esClient.searchByCondition(tenantId, ES_TYPE_NAME, searchCondition);
            if (type == ADD_UPDATE_OP) {//如果是新增或更新
                if (CollectionUtils.isEmpty(result)) {//es中不存在则新增
                    esItemVo.setCreated(new Date());
                    esItemVo.setModified(esItemVo.getCreated());
                    esClient.indexDocForJson(tenantId, ES_TYPE_NAME, esItemVo.toJsonString());
                } else {//es中存在则更新
                    String id = String.valueOf(result.get(0).get("id"));
                    if (result.get(0).get("created") != null) {
                        long createdL = Long.parseLong(String.valueOf(result.get(0).get("created")));
                        Date createdD = new Date(createdL);
                        esItemVo.setCreated(createdD);
                    } else {
                        esItemVo.setCreated(new Date());
                    }
                    esItemVo.setModified(new Date());
                    esClient.updateDocByJson(tenantId, ES_TYPE_NAME, id, esItemVo.toJsonString());
                }
            } else if (type == DELETE_OP) {//如果是删除
                if(CollectionUtils.isNotEmpty(result)) {
                    String id = String.valueOf(result.get(0).get("id"));
                    esClient.deleteDocById(tenantId, ES_TYPE_NAME, id);
                }

            }
            LOGGER.info("put into es success, tenantId={}", tenantId);
        } catch (Exception e) {
            LOGGER.error("put into es error, " + e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            releaseLock(tenantId, lockKey);
        }
    }

    private boolean acquireLock(Long tenantId, String lockKey, long expired) {
        //通过SETNX试图获取一个lock
        boolean success = false;
        long value = System.currentTimeMillis() + expired;
        boolean flag = cacheFeignClient.setnx(tenantId, lockKey, String.valueOf(value));
        //SETNX成功，则成功获取一个锁
        if (flag) {
            success = true;
        } else {//SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
            long oldValue = Long.valueOf(cacheFeignClient.get(tenantId, lockKey));
            //超时
            if (oldValue < System.currentTimeMillis()) {
                String getValue = cacheFeignClient.getSet(tenantId, lockKey, String.valueOf(value));
                // 获取锁成功
                if (Long.valueOf(getValue) == oldValue) {
                    success = true;
                } else {// 已被其他进程捷足先登了
                    success = false;
                }
            } else {//未超时，则直接返回失败
                success = false;
            }
        }
        return success;
    }

    private void releaseLock(Long tenantId, String lockKey) {
        long current = System.currentTimeMillis();
        // 避免删除非自己获取得到的锁
        if (current < Long.valueOf(cacheFeignClient.get(tenantId, lockKey))) {
            cacheFeignClient.del(tenantId, lockKey);
        }
    }
}
