package com.jcloud.b2c.platform.service.item;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.platform.domain.vo.ImportItemResponse;
import com.jcloud.b2c.platform.domain.vo.ImportItemVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description: 商品相关接口类
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-15 16:43
 * @lastdate:
 */
public interface ItemService {

    /**
     * 将excel导入的sku映射为对应的实体类
     *
     * @param itemTemplate
     * @return
     */
    public ImportItemResponse transImportItem(MultipartFile itemTemplate,Long tenantId,Long shopId);

}
