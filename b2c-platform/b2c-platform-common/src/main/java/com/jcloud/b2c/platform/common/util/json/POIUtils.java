package com.jcloud.b2c.platform.common.util.json;

import org.apache.poi.ss.usermodel.Cell;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-20 17:09
 * @lastdate:
 */
public class POIUtils {
    public POIUtils() {
    }

    public static String getCellValue(Cell cell) {
        if(cell == null) {
            return "";
        } else {
            String cellValue;
            switch(cell.getCellType()) {
                case 0:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case 1:
                    cellValue = cell.getRichStringCellValue().getString().trim();
                    break;
                case 2:
                    cellValue = cell.getCellFormula();
                    break;
                case 3:
                default:
                    cellValue = "";
                    break;
                case 4:
                    cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
            }

            return cellValue;
        }
    }
}
