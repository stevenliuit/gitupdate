/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallCategoryServiceImpl.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.common.common.util.JacksonUtil;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallCategory;
import com.jcloud.b2c.mall.service.mapper.MallCategoryMapper;
import com.jcloud.b2c.mall.service.service.MallCategoryService;
import com.jcloud.b2c.mall.service.service.feign.CacheFeignClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description: 类目IMPL
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 14:48
 * @lastdate:
 */

@Service("mallCategoryService")
public class MallCategoryServiceImpl implements MallCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MallCategoryServiceImpl.class);

    private static String CACHE_CATEGORY_KEY = "CACHE_CATEGORY_KEY_";
    private static String ENCODING= "UTF-8";
    private static int EXPIRE = 0;

    @Autowired
    private MallCategoryMapper mallCategoryMapper;

    @Autowired
    private CacheFeignClient cacheFeignClient;

    @Override
    public MallCategory get(Long id, Long tenantId){
        MallCategory mallCategory = new MallCategory();
        mallCategory.setId(id);
        mallCategory.setTenantId(tenantId);
        MallCategory category = mallCategoryMapper.getByPrimaryKey(mallCategory);
        if (category == null) {
            return null;
        }
        try {
            String json = JacksonUtil.convert(mallCategory);
            cacheFeignClient.saveBytes2Cache(tenantId, this.getCacheCategoryKey(tenantId, category.getId()), json.getBytes(ENCODING), EXPIRE);
        } catch (Exception e) {
            LOGGER.error("缓存类目失败", e);
        }
        return category;
    }

    @Override
    public boolean isUniqueName(MallCategory mallCategory) {
        MallCategory cate =mallCategoryMapper.get(mallCategory);
        if(null==cate){
            return true;
        }
        Long id0=mallCategory.getId();
        Long id1=cate.getId();
        if(id0!=null&&id0.equals(id1)){
            return true;
        }
        return false;
    }

    @Override
    public BaseResponseVo<Void> moveCate(MallCategory current, String action) {
        current = mallCategoryMapper.getByPrimaryKey(current);
        List<MallCategory> list = mallCategoryMapper.queryByParentId(current);
        BaseResponseVo baseResponseVo=new BaseResponseVo();
        Collections.sort(list);
        Long current_id=current.getId();
        Integer current_sort=current.getSortNum();
        MallCategory target=null;
        if("top".equals(action)){
            target=list.get(0);
            if (target.getId()==current_id){
                baseResponseVo.setIsSuccess(false);
                baseResponseVo.setMessage("已经是第一个了");
                return baseResponseVo;
            }
        }else if("bottom".equals(action)){
            target=list.get(list.size()-1);
            if (target.getId()==current_id){
                baseResponseVo.setIsSuccess(false);
                baseResponseVo.setMessage("已经是最后一个了");
                return baseResponseVo;
            }
        }else{
            for(int index=0;index<list.size();index++){
                MallCategory cate=list.get(index);
                if(cate.getId().equals(current_id)){
                    current=cate;
                    if("up".equals(action)){
                        if(index==0){
                            baseResponseVo.setIsSuccess(false);
                            baseResponseVo.setMessage("已经是第一个了");
                            return baseResponseVo;
                        }
                        target=list.get(index-1);
                    }else {
                        if(index==list.size()-1){
                            baseResponseVo.setIsSuccess(false);
                            baseResponseVo.setMessage("已经是最后一个了");
                            return baseResponseVo;
                        }
                        target=list.get(index+1);
                    }
                    break;
                }
            }
        }
        list.clear();list=null;
        if(target!=null){
            Integer target_sort_num=target.getSortNum();
            if("top".equals(action)){
                current.setSortNum(target_sort_num);
            }else if("bottom".equals(action)){
                current.setSortNum(target_sort_num+1);
            }else{
                target.setSortNum(current_sort);
                current.setSortNum(target_sort_num);
            }
            //保存交换过sortnumde实体
            Calendar calendar=Calendar.getInstance();
            current.setModified(calendar.getTime());
            target.setModified(calendar.getTime());
            //如果序号相等，通过修改modified进行控制

                if("up".equals(action)||"top".equals(action)){
                    calendar.add(Calendar.SECOND,1);//续一秒
                    current.setModified(calendar.getTime());
                }else  if("down".equals(action)||"bottom".equals(action)){
                    calendar.add(Calendar.SECOND,-1);//减一秒
                    current.setModified(calendar.getTime());
                }


            mallCategoryMapper.updateSort(current);
            mallCategoryMapper.updateSort(target);
        }
        target=null;
        return baseResponseVo;
    }

    public BaseResponseVo<Void> updateMoveUp(MallCategory current, String action) {
        BaseResponseVo baseResponseVo = new BaseResponseVo();
        baseResponseVo.setIsSuccess(false);
        baseResponseVo.setMessage("移动失败，请稍后再试");

        //根据ID获取要操作数据
        MallCategory mallCategory = mallCategoryMapper.getById(current);
        //上移
        if ("up".equals(action)) {
            //获取操作数据的上一个不相等序号
            MallCategory lessSortCategory = mallCategoryMapper.getLessSort(mallCategory);
            if (lessSortCategory == null) {
                //获取形同排序序号的上一条数据
                MallCategory purposeCategory = mallCategoryMapper.getMinTime(mallCategory);
                if (purposeCategory == null) {
                    baseResponseVo.setMessage("已经是第一个了");
                } else {
                    //基准时间
                    Date date = purposeCategory.getModified();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.SECOND, -1);
                    purposeCategory.setModified(calendar.getTime());
                    //将目标数据的时间赋给操作数据，实现目标数据时间小于操作数据时间
                    mallCategory.setModified(date);
                    Integer num = mallCategoryMapper.updateByPrimaryKeySelective(mallCategory);
                    Integer nums = mallCategoryMapper.updateByPrimaryKeySelective(purposeCategory);
                    if (num == 1 && nums == 1) {
                        baseResponseVo.setMessage("上移成功");
                    }
                }
            } else {
                //基准时间
                Date date = lessSortCategory.getModified();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.SECOND, -1);
                lessSortCategory.setModified(calendar.getTime());
                //将目标数据的时间赋给操作数据，实现目标数据时间小于操作数据时间
                mallCategory.setSortNum(lessSortCategory.getSortNum());
                mallCategory.setModified(date);
                Integer num = mallCategoryMapper.updateByPrimaryKeySelective(mallCategory);
                Integer nums = mallCategoryMapper.updateByPrimaryKeySelective(lessSortCategory);
                if (num == 1 && nums == 1) {
                    baseResponseVo.setMessage("上移成功");
                }
            }
        }
        //置顶
        if ("top".equals(action)) {
            current.setSortNum(1);
            current.setModified(new Date());
            Integer upd = mallCategoryMapper.updateByPrimaryKeySelective(current);
            if (upd == 1) {
                baseResponseVo.setMessage("置顶成功");
            }
        }
        //下移
        if ("down".equals(action)) {
            //获取形同排序序号的下一条数据
            MallCategory purposeCategory = mallCategoryMapper.getMaxTime(mallCategory);
            if (purposeCategory == null) {
                //获取操作数据的下一个不相等序号
                MallCategory downSortCategory = mallCategoryMapper.getDownSort(mallCategory);
                if (downSortCategory == null) {
                    baseResponseVo.setMessage("已经是最后一个了");
                } else {
                    //基准时间
                    Date date = downSortCategory.getModified();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.SECOND, 1);
                    downSortCategory.setModified(calendar.getTime());
                    //将目标数据的时间赋给操作数据，实现目标数据时间小于操作数据时间
                    mallCategory.setSortNum(downSortCategory.getSortNum());
                    mallCategory.setModified(date);
                    Integer num = mallCategoryMapper.updateByPrimaryKeySelective(mallCategory);
                    Integer nums = mallCategoryMapper.updateByPrimaryKeySelective(downSortCategory);
                    if (num == 1 && nums == 1) {
                        baseResponseVo.setMessage("下移成功");
                    }
                }
            } else {
                //基准时间，
                Date date = purposeCategory.getModified();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.SECOND, 1);
                purposeCategory.setModified(calendar.getTime());
                //将目标数据的时间赋给操作数据，实现目标数据时间小于操作数据时间
                mallCategory.setModified(date);
                Integer num = mallCategoryMapper.updateByPrimaryKeySelective(mallCategory);
                Integer nums = mallCategoryMapper.updateByPrimaryKeySelective(purposeCategory);
                if (num == 1 && nums == 1) {
                    baseResponseVo.setMessage("下移成功");
                }
            }
        }
        //置底
        if ("bottom".equals(action)) {
            //获取最大序号数据
            Integer maxSort = mallCategoryMapper.getMaxSortCategory(mallCategory).getSortNum();
            current.setSortNum(maxSort + 1);
            Integer upd = mallCategoryMapper.updateByPrimaryKeySelective(current);
            if (upd == 1) {
                baseResponseVo.setMessage("置底成功");
            }
        }
        return baseResponseVo;
    }

    @Override
    public boolean add(MallCategory mallCategory){
        String url = mallCategory.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallCategory.setCreated(now);
        mallCategory.setModified(now);
        mallCategoryMapper.updateSortToMoveDown(mallCategory, null);
        mallCategoryMapper.insert(mallCategory);

        try {
            MallCategory temp = this.get(mallCategory.getId(), mallCategory.getTenantId());
            String json = JacksonUtil.convert(temp);
            cacheFeignClient.saveBytes2Cache(mallCategory.getTenantId(), this.getCacheCategoryKey(mallCategory.getTenantId(), mallCategory.getId()), json.getBytes(ENCODING), EXPIRE);
        } catch (Exception e) {
            LOGGER.error("缓存类目失败", e);
        }
        return true;
    }

    @Override
    public boolean update(MallCategory mallCategory, Integer beforeSort){
        String url = mallCategory.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallCategory.setModified(now);
        if (mallCategory.getSortNum()<beforeSort) {
            mallCategoryMapper.updateSortToMoveDown(mallCategory, beforeSort);
        } else if (mallCategory.getSortNum()>beforeSort) {
            Integer afterSort = mallCategory.getSortNum();
            mallCategory.setSortNum(beforeSort);
            mallCategoryMapper.updateSortToMoveUp(mallCategory, afterSort);
            mallCategory.setSortNum(afterSort);
        }
        mallCategoryMapper.updateByPrimaryKeySelective(mallCategory);

        try {
            MallCategory temp = this.get(mallCategory.getId(), mallCategory.getTenantId());
            if (temp != null) {
                String json = JacksonUtil.convert(temp);
                cacheFeignClient.saveBytes2Cache(mallCategory.getTenantId(), this.getCacheCategoryKey(mallCategory.getTenantId(), mallCategory.getId()), json.getBytes(ENCODING), EXPIRE);
            }
        } catch (Exception e) {
            LOGGER.error("缓存类目失败", e);
        }
        return true;
    }

    @Override
    public boolean delete(MallCategory mallCategory){
        Date now = new Date();
        mallCategory.setModified(now);
        mallCategory.setState(StateEnum.DELETED.getValue());
//        mallCategoryMapper.updateSortToMoveUp(mallCategory, null);
        mallCategoryMapper.deleteByPrimaryKey(mallCategory);
        try {
            cacheFeignClient.deleteKey(mallCategory.getTenantId(), this.getCacheCategoryKey(mallCategory.getTenantId(), mallCategory.getId()));
        } catch (Exception e) {
            LOGGER.error("缓存类目失败", e);
        }
        return true;
    }

    @Override
    public List<MallCategory> query(MallCategory mallCategory){
        return mallCategoryMapper.querySelective(mallCategory);
    }

    @Override
    public List<MallCategory> queryByParentId(MallCategory mallCategory) {
        mallCategory.setState(StateEnum.ON_SHELF.getValue());
        return mallCategoryMapper.queryByParentId(mallCategory);
    }

    @Override
    public MallCategory getByIdFromCache(Long tenantId, Long id) {
        LOGGER.info("从缓存中查询运营类目tenantId={}, id={}", tenantId, id);
        String json = cacheFeignClient.get(tenantId, this.getCacheCategoryKey(tenantId, id));
        LOGGER.info("从缓存中查询运营类目tenantId={}, id={}, json={}", tenantId, id, json);
        MallCategory category;
        if (StringUtils.isNotBlank(json)) {
            try {
                category = JacksonUtil.parse(json, MallCategory.class);
                if (StringUtils.isBlank(category.getName()) || category.getState() == null) {
                    category = this.get(id, tenantId);
                }
                LOGGER.info("从缓存中查询运营类目category={}", category);
            } catch (Exception e) {
                category = this.get(id, tenantId);
                LOGGER.warn("从缓存获取商品异常tenantId =" + tenantId + ",id=" + id, e);
            }
        } else {
            category = this.get(id, tenantId);
            LOGGER.info("从缓存中查询运营类目json is null, category={}", category);
        }
        return category;
    }

    @Override
    public boolean verifySortNum(MallCategory mallCategory) {
        List<MallCategory> categoryList =mallCategoryMapper.getBySortNum(mallCategory);
//        MallCategory cate =mallCategoryMapper.getBySortNum(mallCategory);
        if(categoryList.size()==0){
            return true;
        }
        Long id0=mallCategory.getId();
        for (MallCategory cate:categoryList){
        Long id1=cate.getId();
        if(id0!=null&&id0.equals(id1)){
            return true;
        }
        }
        return false;
    }

    private String getCacheCategoryKey(Long tenantId, Long categoryId) {
        return CACHE_CATEGORY_KEY + tenantId + "_" + categoryId;
    }
}
