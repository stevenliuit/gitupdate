package com.jcloud.b2c.platform.domain.vo;

import java.util.List;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-15 18:13
 * @lastdate:
 */

public class ImportItemResponse {
    private List<ImportItemVo> importItemVos;

    private List<ImportItemFail> importItemFails;

    private Boolean result;

    private String resultMsg;

    public List<ImportItemVo> getImportItemVos() {
        return importItemVos;
    }

    public void setImportItemVos(List<ImportItemVo> importItemVos) {
        this.importItemVos = importItemVos;
    }

    public List<ImportItemFail> getImportItemFails() {
        return importItemFails;
    }

    public void setImportItemFails(List<ImportItemFail> importItemFails) {
        this.importItemFails = importItemFails;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public String toString() {
        return "ImportItemResponse{" +
                "importItemVos=" + importItemVos +
                ", importItemFails=" + importItemFails +
                ", result=" + result +
                ", resultMsg='" + resultMsg + '\'' +
                '}';
    }
}
