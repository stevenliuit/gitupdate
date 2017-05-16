package com.jcloud.b2c.platform.service.item.impl;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.item.client.vo.item.ImportItemVoForOpen;
import com.jcloud.b2c.platform.common.util.json.POIUtils;
import com.jcloud.b2c.platform.domain.vo.ImportItemFail;
import com.jcloud.b2c.platform.domain.vo.ImportItemResponse;
import com.jcloud.b2c.platform.domain.vo.ImportItemVo;
import com.jcloud.b2c.platform.service.feign.ImportItemClient;
import com.jcloud.b2c.platform.service.feign.ItemCategoryClient;
import com.jcloud.b2c.platform.service.item.ItemService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-15 16:43
 * @lastdate:
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService {
    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ImportItemClient importItemClient;
    @Autowired
    private ItemCategoryClient itemCategoryClient;

    private  static final int maxSkuLength = 10;
    @Override
    public ImportItemResponse transImportItem(MultipartFile itemTemplate,Long tenantId,Long shopId) {
        Workbook wb = null;
        InputStream inp = null;
        try {
            inp = itemTemplate.getInputStream();
            wb = WorkbookFactory.create(inp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inp != null) {
                try {
                    inp.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        ImportItemResponse response =new ImportItemResponse();
        response.setResult(true);
        //得到第一页 sheet
        //页Sheet是从0开始索引的
        Sheet sheet = wb.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum > 1006) {//加上表头判断
            response.setResult(false);
            response.setResultMsg("最多只允许上传1000行");
            return response;
        }
        //获取租户类目级数  目前先写死为2级 后续如果有支持三级的需求再修改
//        Integer platformCategoryLevel =  ControllerContext.getCategoryLevel() == null ? 3 : ControllerContext.getCategoryLevel();
        Integer platformCategoryLevel = 2;
        String[] heads = {"商品序号","商品ID", "一级类目id", "一级类目名称", "二级类目id", "二级类目名称", "三级类目id", "三级类目名称"};
        if (platformCategoryLevel == 2) {
            heads = new String[]{"商品序号","商品ID", "一级类目id", "一级类目名称", "二级类目id", "二级类目名称"};
        }
        Row rowHeads = sheet.getRow(6);
        if (rowHeads == null) {
            response.setResult(false);
            response.setResultMsg("上传文件与模板不符");
            return response;
        }
        for (int i=0; i<heads.length; i++) {
            try{
                if (! StringUtils.equals(rowHeads.getCell(i).getStringCellValue(), heads[i])) {
                    response.setResult(false);
                    response.setResultMsg("上传文件与模板不符");
                    return response;
                }

            }catch (Exception e){
                response.setResult(false);
                response.setResultMsg("上传文件与模板不符");
                return response;
            }
        }
        List<ImportItemVo> importItemVos = new ArrayList<ImportItemVo>();
        List<ImportItemFail> importItemFails = new ArrayList<ImportItemFail>();
        Set<Long> extistsSkuIds = new HashSet<Long>();
        int totalRowCount = 0;
        for (int i=7; i<=lastRowNum; i++) {
            int rowExcel = i+1;
            Row rowTemp = sheet.getRow(i);
            if (rowTemp == null) {
                continue;
            }
            totalRowCount ++;
            ImportItemVo importItemVo = new ImportItemVo();
            int colIndex = 0;
            DecimalFormat df = new DecimalFormat("0");
            Cell cellSeq = rowTemp.getCell(colIndex++);
            Cell cellSkuId = rowTemp.getCell(colIndex++);
            Cell cellCategory1 = rowTemp.getCell(colIndex++);
            Cell cellCategory1Name = rowTemp.getCell(colIndex++);
            Cell cellCategory2 = rowTemp.getCell(colIndex++);
            Cell cellCategory2Name = rowTemp.getCell(colIndex++);
            String seq = "";
            if (cellSeq != null){
                if (cellSeq.getCellType() != 0){
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(cellSeq.toString());
                    if (cellSkuId != null){
                        if (cellSkuId.getCellType() != 0){
                            importItemFail.setSkuId(cellSkuId.toString());
                        }else {
                            importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                        }
                    }else {
                        importItemFail.setSkuId("null");
                    }
                    importItemFail.setResultMsg("商品序号格式不正确");
                    importItemFails.add(importItemFail);
                    continue;
                }
                seq = df.format(cellSeq.getNumericCellValue());
                if (StringUtils.isBlank(seq)) {
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum("null");
                    if (cellSkuId != null){
                        if (cellSkuId.getCellType() != 0){
                            importItemFail.setSkuId(cellSkuId.toString());
                        }else {
                            importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                        }
                    }else {
                        importItemFail.setSkuId("null");
                    }
                    importItemFail.setResultMsg("商品序号不能为空");
                    importItemFails.add(importItemFail);
                    continue;
                }
            }else{
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum("null");
                if (cellSkuId != null){
                    if (cellSkuId.getCellType() != 0){
                        importItemFail.setSkuId(cellSkuId.toString());
                    }else {
                        importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                    }
                }else {
                    importItemFail.setSkuId("null");
                }
                importItemFail.setResultMsg("商品序号不能为空");
                importItemFails.add(importItemFail);
                continue;
            }
            String skuIdStr = "";
            if (cellSkuId != null){
                if (cellSkuId.getCellType() != 0){
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId(cellSkuId.toString());
                    importItemFail.setResultMsg("商品id格式不正确");
                    importItemFails.add(importItemFail);
                    continue;
                }
                skuIdStr = df.format(cellSkuId.getNumericCellValue());
                if (StringUtils.isBlank(skuIdStr)) {
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId("null");
                    importItemFail.setResultMsg("商品id不能为空");
                    importItemFails.add(importItemFail);
                    continue;
                }
            }else {
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                importItemFail.setSkuId("null");
                importItemFail.setResultMsg("商品id不能为空");
                importItemFails.add(importItemFail);
                continue;
            }

            String category1Str = "";
            if (cellCategory1 != null){
                if (cellCategory1.getCellType() != 0){
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                    importItemFail.setResultMsg("一级类目id格式不正确");
                    importItemFails.add(importItemFail);
                    continue;
                }
                category1Str = df.format(cellCategory1.getNumericCellValue());
                if (StringUtils.isBlank(category1Str)) {
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                    importItemFail.setResultMsg("一级类目id不能为空");
                    importItemFails.add(importItemFail);
                    continue;
                }
            }else {
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                importItemFail.setResultMsg("一级类目id不能为空");
                importItemFails.add(importItemFail);
                continue;
            }

            String category1Name = "";
            if (cellCategory1Name != null){
                category1Name = StringUtils.trimToEmpty(POIUtils.getCellValue(cellCategory1Name));
                if (StringUtils.isBlank(category1Name)) {
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                    importItemFail.setResultMsg("一级类目名称不能为空");
                    importItemFails.add(importItemFail);
                    continue;
                }
            }else{
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                importItemFail.setResultMsg("一级类目名称不能为空");
                importItemFails.add(importItemFail);
                continue;
            }

            String category2Str = "";
            if (cellCategory2 != null){
                if (cellCategory2.getCellType() != 0){
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                    importItemFail.setResultMsg("二级类目id格式不正确");
                    importItemFails.add(importItemFail);
                    continue;
                }
                category2Str = df.format(cellCategory2.getNumericCellValue());
                if (StringUtils.isBlank(category2Str)) {
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                    importItemFail.setResultMsg("二级类目id不能为空");
                    importItemFails.add(importItemFail);
                    continue;
                }
            }else {
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                importItemFail.setResultMsg("二级类目id不能为空");
                importItemFails.add(importItemFail);
                continue;
            }

            String category2Name = "";
            if (cellCategory2Name != null){
                category2Name = StringUtils.trimToEmpty(POIUtils.getCellValue(cellCategory2Name));
                if (StringUtils.isBlank(category2Name)) {
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                    importItemFail.setResultMsg("二级类目名称不能为空");
                    importItemFails.add(importItemFail);
                    continue;
                }
            }else{
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                importItemFail.setResultMsg("二级类目名称不能为空");
                importItemFails.add(importItemFail);
                continue;
            }

            Long skuId = Long.parseLong(skuIdStr);
            if (!extistsSkuIds.add(skuId)){
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                importItemFail.setResultMsg("excel中存在重复skuId");
                importItemFails.add(importItemFail);
                continue;
            }
            if(skuId.toString().length() >= maxSkuLength){
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                importItemFail.setResultMsg("skuId长度必须小于10");
                importItemFails.add(importItemFail);
                continue;
            }
            BaseResponseVo<Boolean> responseVo = importItemClient.isExistsSkuForImport(tenantId, shopId, skuId);
            if(responseVo.isSuccess()) {
                Boolean isExists = responseVo.getData();
                if (isExists == true){
                    importItemVo.setStatus(2);
                }else {
                    importItemVo.setStatus(1);
                }
                // 接口调用失败的场合
            } else {
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(cellSeq.toString());
                importItemFail.setSkuId(cellSkuId.toString());
                importItemFail.setResultMsg(responseVo.getMessage());
                importItemFails.add(importItemFail);
                continue;
            }
            Long category1 = Long.parseLong(category1Str);
            Long category2 =  Long.parseLong(category2Str);
            BaseResponseVo<Boolean> Category1response = itemCategoryClient.isExistsCategory(tenantId, category1, null);
            if(Category1response.isSuccess()) {
                Boolean isExists = Category1response.getData();
                if (isExists == false){
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                    importItemFail.setResultMsg("一级类目不存在");
                    importItemFails.add(importItemFail);
                    continue;
                }
                // 接口调用失败的场合
            } else {
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(cellSeq.toString());
                importItemFail.setSkuId(cellSkuId.toString());
                importItemFail.setResultMsg(Category1response.getMessage());
                importItemFails.add(importItemFail);
                continue;
            }
            BaseResponseVo<Boolean> Category2response = itemCategoryClient.isExistsCategory(tenantId, category2, category1);
            if(Category2response.isSuccess()) {
                Boolean isExists = Category2response.getData();
                if (isExists == false){
                    ImportItemFail importItemFail = new ImportItemFail();
                    importItemFail.setRowNum(df.format(cellSeq.getNumericCellValue()));
                    importItemFail.setSkuId(df.format(cellSkuId.getNumericCellValue()));
                    importItemFail.setResultMsg("二级类目不存在或不在一级类目下");
                    importItemFails.add(importItemFail);
                    continue;
                }
                // 接口调用失败的场合
            } else {
                ImportItemFail importItemFail = new ImportItemFail();
                importItemFail.setRowNum(cellSeq.toString());
                importItemFail.setSkuId(cellSkuId.toString());
                importItemFail.setResultMsg(Category2response.getMessage());
                importItemFails.add(importItemFail);
                continue;
            }
            importItemVo.setNum(Integer.parseInt(seq));
            importItemVo.setSkuId(skuId);
            importItemVo.setFirstCid(category1);
            importItemVo.setFirstCname(category1Name);
            importItemVo.setSecondCid(category2);
            importItemVo.setSecondCname(category2Name);
            importItemVos.add(importItemVo);
        }
        response.setImportItemVos(importItemVos);
        response.setImportItemFails(importItemFails);
        return response;
    }


}
