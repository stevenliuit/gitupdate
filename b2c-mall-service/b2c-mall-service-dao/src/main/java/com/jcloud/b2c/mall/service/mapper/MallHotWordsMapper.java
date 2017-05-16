package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallHotWords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description: 热词
 * @author: guoyuezheng
 * @requireNo:
 * @createdate: 2017-02-17 15:06
 * @lastdate:
 */

@Mapper
public interface MallHotWordsMapper {

    MallHotWords getByPrimaryKey(MallHotWords mallHotWords);

    int insert(MallHotWords mallHotWords);

    int updateByPrimaryKeySelective(MallHotWords mallHotWords);

    int deleteByPrimaryKey(MallHotWords mallHotWords);

    List<MallHotWords> querySelective(MallHotWords mallHotWords);

    int updateByWordsType(MallHotWords mallHotWords);

    int updateBySort(@Param(value = "id") Long id,
                     @Param(value = "changeId") Long changeId,
                     @Param(value = "tenantId") Long tenantId,
                     @Param(value = "now") Date now);

    int clearToSort(MallHotWords mallHotWords);

    Integer selectMaxSort(MallHotWords mallHotWords);

    //将所修改热词排序置顶
    int updateToStick(MallHotWords mallHotWords);

    //将小于将要置顶的排序加1
    int updateLittleStick(MallHotWords mallHotWords);

    //将大于将要置尾的排序减1
   int updateBigFinally(MallHotWords mallHotWords);

   //将置尾排序改为排序最大值
   int updateToFinally(MallHotWords mallHotWords);

    int updateUp(@Param(value = "num") Integer num,
                 @Param(value = "sort") Integer sort,
                 @Param(value = "tenantId") Long tenantId,
                 @Param(value = "now") Date now);

    int updateMoveUp(MallHotWords mallHotWords);


}
