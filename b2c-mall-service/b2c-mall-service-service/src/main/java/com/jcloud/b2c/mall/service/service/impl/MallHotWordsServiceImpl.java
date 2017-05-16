package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.client.enums.HotWordsTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallHotWords;
import com.jcloud.b2c.mall.service.mapper.MallHotWordsMapper;
import com.jcloud.b2c.mall.service.service.MallHotWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description: 热词SERVICEIMPL
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/17 17:08
 * @lasdate
 */
@Service("mallHotWordsService")
public class MallHotWordsServiceImpl implements MallHotWordsService {

    @Autowired
    private MallHotWordsMapper mallHotWordsMapper;

    @Override
    public MallHotWords get(Long id, Long tenantId) {
        MallHotWords mallHotWords = new MallHotWords();
        mallHotWords.setId(id);
        mallHotWords.setTenantId(tenantId);
        return mallHotWordsMapper.getByPrimaryKey(mallHotWords);
    }

    @Override
    public boolean add(MallHotWords mallHotWords) {
        Date now = new Date();
        mallHotWords.setCreated(now);
        mallHotWords.setModified(now);
        if (mallHotWords.getWordsType()==HotWordsTypeEnum.DEFAULT_SEARCH.getValue())
           mallHotWordsMapper.updateByWordsType(mallHotWords);
        return mallHotWordsMapper.insert(mallHotWords) == 1 ? true : false;
    }

    @Override
    public boolean update(MallHotWords mallHotWords) {
        Date now = new Date();
        mallHotWords.setModified(now);
        if (mallHotWords.getWordsType()==HotWordsTypeEnum.DEFAULT_SEARCH.getValue())
          mallHotWordsMapper.updateByWordsType(mallHotWords);
        return mallHotWordsMapper.updateByPrimaryKeySelective(mallHotWords) == 1 ? true : false;
    }

    @Override
    public boolean delete(MallHotWords mallHotWords) {
        Date now = new Date();
        mallHotWords.setModified(now);
        mallHotWords.setState(StateEnum.DELETED.getValue());
        mallHotWordsMapper.clearToSort(mallHotWords);
        return mallHotWordsMapper.deleteByPrimaryKey(mallHotWords) == 1 ? true : false;
    }

    @Override
    public List<MallHotWords> query(MallHotWords mallHotWords) {
        return mallHotWordsMapper.querySelective(mallHotWords);
    }

    @Override
    public boolean updateSort(Long id, Long changeId, Long tenantId) {
        Date now = new Date();
        return mallHotWordsMapper.updateBySort(id, changeId, tenantId, now) ==1 ? true : false;
    }

    @Override
    public Integer selectMaxSort(MallHotWords mallHotWords){
        return mallHotWordsMapper.selectMaxSort(mallHotWords);
    }

    @Override
    public boolean updateToStick(MallHotWords mallHotWords) {
        mallHotWordsMapper.updateLittleStick(mallHotWords);
        return mallHotWordsMapper.updateToStick(mallHotWords) == 1 ? true : false;
    }

    @Override
    public boolean updateToFinally(MallHotWords mallHotWords) {
        Integer maxSort= mallHotWordsMapper.selectMaxSort(mallHotWords);
        mallHotWordsMapper.updateBigFinally(mallHotWords);
        mallHotWords.setSortNum(maxSort);
        return mallHotWordsMapper.updateToFinally(mallHotWords) == 1 ? true : false;
    }


    @Override
    public  boolean updateMoveUp(MallHotWords mallHotWords){
        Date now=mallHotWords.getModified();
        Long  tenantId = mallHotWords.getTenantId();
        //用于赋值
        Integer sort =mallHotWords.getSortNum();
        //用于查找下一个
        Integer num= mallHotWords.getSortNum();
        if (num!=1){
            num=num-1;
            mallHotWordsMapper.updateUp(num,sort,tenantId,now);
        }
        mallHotWords.setSortNum(num);
        return mallHotWordsMapper.updateToFinally(mallHotWords) == 1 ? true : false;

    }

    @Override
    public boolean updateMoveDown(MallHotWords mallHotWords) {
        Integer maxSort= mallHotWordsMapper.selectMaxSort(mallHotWords);
        Date now=mallHotWords.getModified();
        Long  tenantId = mallHotWords.getTenantId();
        //用于赋值
        Integer sort =mallHotWords.getSortNum();
        //用于查找
        Integer num= mallHotWords.getSortNum();
        if (num!=maxSort){
            num=num+1;
            mallHotWordsMapper.updateUp(num,sort,tenantId,now);
        }
        mallHotWords.setSortNum(num);
        return mallHotWordsMapper.updateToFinally(mallHotWords) == 1 ? true : false;
    }

}