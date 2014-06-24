package com.techapi.bus.util;

import com.techapi.bus.data.ImportUserKeyService;
import com.techapi.bus.entity.City;
import com.techapi.bus.entity.Country;
import com.techapi.bus.entity.Province;
import com.techapi.bus.entity.UserKey;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuefei on 6/9/14.
 */
public class ExcelUtils {

    protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext-bus-dao-oracle.xml");

    public ImportUserKeyService importUserKeyService = context
            .getBean(ImportUserKeyService.class);

    public static List<Province> readProvinceExcel() {
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

    public static List<UserKey> readKey() {
        List<UserKey> userKeyList = new ArrayList<>();
        try {
            Workbook workbook = Workbook.getWorkbook(new File("/Users/xuefei/Documents/MyDocument/Fun/bus/key生成策略及现有KEY/key生成策略及现有KEY/高德GIS业务现有KEY.xls"));
            Sheet sheet = workbook.getSheet(0);

            int columns = sheet.getColumns();
            int rows = sheet.getRows();

            for(int i = 1; i< rows; i++) {
                UserKey userKey = new UserKey();

                userKey.setCreateDate(sheet.getCell(0, i).getContents());
                userKey.setBusinessName(sheet.getCell(1, i).getContents());
                userKey.setBusinessSubName(sheet.getCell(2, i).getContents());
                userKey.setBusinessFlag(sheet.getCell(3, i).getContents());
                userKey.setBusinessType(sheet.getCell(4, i).getContents());
                userKey.setUsedApi(sheet.getCell(5, i).getContents());
                userKey.setProvince(sheet.getCell(6, i).getContents());
                userKey.setStatus(sheet.getCell(7, i).getContents());
                userKey.setFirm(sheet.getCell(8, i).getContents());
                userKey.setBusinessUrl(sheet.getCell(9, i).getContents());
                userKey.setKey(sheet.getCell(10, i).getContents());
                userKey.setContact(sheet.getCell(11, i).getContents());
                userKey.setBusinessResource(sheet.getCell(12, i).getContents());
                userKey.setSource(0);
                userKeyList.add(userKey);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return userKeyList;
    }

    public static void main(String[] args) {
        //ExcelUtils.readProvinceExcel();
        new ExcelUtils().importUserKeyService.importUserKey(ExcelUtils.readKey());
    }
}
