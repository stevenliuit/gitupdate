package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallPrincipalItem;
import com.jcloud.b2c.mall.service.mapper.MallPrincipalItemMapper;
import com.jcloud.b2c.mall.service.service.MallPrincipalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 主体与商品ServiceImpl
 * Created by issuser on 2017/3/1.
 * liuhao
 */
@Service("mallPrincipalItemService")
public class MallPrincipalItemServiceImpl implements MallPrincipalItemService {

    @Autowired
    private MallPrincipalItemMapper mallPrincipalItemMapper;

    @Override
    public MallPrincipalItem get(Long id, Long tenantId) {
        MallPrincipalItem mallPrincipalItem = new MallPrincipalItem();
        mallPrincipalItem.setId(id);
        mallPrincipalItem.setTenantId(tenantId);
        return mallPrincipalItemMapper.getByPrimaryKey(mallPrincipalItem);
    }

    @Override
    public List<MallPrincipalItem> query(MallPrincipalItem mallPrincipalItem) {
        int pageIndex = mallPrincipalItem.getPageIndex() == null ? 0 : mallPrincipalItem.getPageIndex();
        int pageSize = mallPrincipalItem.getPageSize() == null ? 10 : mallPrincipalItem.getPageSize();
        pageIndex = pageIndex * pageSize;
        mallPrincipalItem.setPageIndex(pageIndex);
        return mallPrincipalItemMapper.querySelective(mallPrincipalItem);
    }

    @Override
    public Integer queryCount(MallPrincipalItem mallPrincipalItem) {
        return mallPrincipalItemMapper.queryCountSelective(mallPrincipalItem);
    }

    @Override
    public boolean update(MallPrincipalItem mallPrincipalItem) {
        Date time = new Date();
        mallPrincipalItem.setModified(time);
        return mallPrincipalItemMapper.updateByPrimaryKeySelective(mallPrincipalItem) == 1;
    }

    @Override
    public boolean add(MallPrincipalItem mallPrincipalItem) {
        Long itemId = mallPrincipalItem.getItemId();
        Long tenantId = mallPrincipalItem.getTenantId();
        MallPrincipalItem countPara = new MallPrincipalItem();
        countPara.setItemId(itemId);
        countPara.setTenantId(tenantId);
        countPara.setClientType(mallPrincipalItem.getClientType());
        countPara.setPrincipalId(mallPrincipalItem.getPrincipalId());
        countPara.setPrincipalType(mallPrincipalItem.getPrincipalType());
        countPara.setState(StateEnum.ON_SHELF.getValue());
        int count = this.queryCount(countPara);
        if (count > 0) {
            return true;
        }
        Date time = new Date();
        mallPrincipalItem.setCreated(time);
        mallPrincipalItem.setModified(time);
        return mallPrincipalItemMapper.insert(mallPrincipalItem) == 1;
    }

    @Override
    public boolean delete(MallPrincipalItem mallPrincipalItem) {
        Date time = new Date();
        mallPrincipalItem.setModified(time);
        mallPrincipalItem.setState(StateEnum.DELETED.getValue());
        return mallPrincipalItemMapper.deleteByPrimaryKey(mallPrincipalItem) == 1;
    }

    @Override
    public boolean addBatch(List<MallPrincipalItem> mallPrincipalItemList) {
        Date time = new Date();
        for (MallPrincipalItem mallPrincipalItem : mallPrincipalItemList) {
            mallPrincipalItem.setCreated(time);
            mallPrincipalItem.setModified(time);
        }
        return mallPrincipalItemMapper.insertBatch(mallPrincipalItemList) > 1;
    }

    @Override
    public Map<Long,Boolean> isBindItems(Long tenantId, Long shopId, List<Long> skuIds){
        Map<Long,Boolean> map = new HashMap<Long,Boolean>();
        List<MallPrincipalItem> mpiList = mallPrincipalItemMapper.queryBySukIds(tenantId, shopId, StateEnum.ON_SHELF.getValue(), skuIds);
        List<String> idList = new ArrayList<String>();
        if(mpiList!=null && mpiList.size()>0){
            for(MallPrincipalItem mpi : mpiList){
                idList.add(String.valueOf(mpi.getItemId()));
            }
        }
        for(Long skuId : skuIds){
            map.put(skuId, idList.contains(String.valueOf(skuId)));
        }
        return map;
    }

    @Override
    public int batchDelete(Long tenantId, List<Long> skuIds) {
        return this.mallPrincipalItemMapper.batchDelete(tenantId, skuIds);
    }

    @Override
    public List<Long> queryDistinctItemIds(Long tenantId, int pageIndex, int pageSize) {
        return mallPrincipalItemMapper.queryDistinctItemIds(tenantId, pageIndex, pageSize);
    }

    @Override
    public Long countDistinctItemId(Long tenantId) {
        return mallPrincipalItemMapper.countDistinctItemId(tenantId);
    }
}
