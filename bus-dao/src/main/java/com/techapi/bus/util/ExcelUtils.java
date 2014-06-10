package com.techapi.bus.util;

import com.techapi.bus.entity.City;
import com.techapi.bus.entity.Country;
import com.techapi.bus.entity.Province;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuefei on 6/9/14.
 */
public class ExcelUtils {
    public static List<Province> readExcel() {
        List<Province> provinceList = new ArrayList<>();
        try {
            Workbook workbook = Workbook.getWorkbook(new File("/Users/xuefei/Documents/MyDocument/Fun/bus/1 县级行政区划Ad_code列表_13Q4.xls"));
            Sheet sheet = workbook.getSheet(0);
            Range[] mergedCells = sheet.getMergedCells();


            Province province;
            List<City> cityList = null;
            for(Range mergedCell : mergedCells) {
                Cell topLeft = mergedCell.getTopLeft();
                Cell bottomRight = mergedCell.getBottomRight();
                int column = topLeft.getColumn();

                if(column == 1) {
                    province = new Province();
                    province.setProvinceName(topLeft.getContents());
                    cityList = new ArrayList<>();
                    province.setCityList(cityList);
                    provinceList.add(province);
                }


                if(column == 2) {
                    City city = new City();
                    city.setCityName(topLeft.getContents());
                    List<Country> countryList = new ArrayList<>();
                    for(int i = topLeft.getRow(); i <= bottomRight.getRow();i++) {
                        Cell countryIdCell = sheet.getCell(4,i);
                        Cell countryNameCell = sheet.getCell(3,i);
                        Country country = new Country();
                        country.setCountryId(countryIdCell.getContents());
                        country.setCountryName(countryNameCell.getContents());
                        countryList.add(country);
                    }
                    city.setCountryList(countryList);
                    cityList.add(city);
                }
            }
            System.out.println("provinceList.size():" + provinceList.size());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return provinceList;
    }

    public static void main(String[] args) {
        ExcelUtils.readExcel();

    }
}
