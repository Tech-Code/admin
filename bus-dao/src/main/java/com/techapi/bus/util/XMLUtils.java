package com.techapi.bus.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;


/**
 * Created by xuefei on 6/9/14.
 */
public class XMLUtils {
    /**
     * @param xml
     * @return Map
     * @description 将xml字符串转换成map
     */
    public static Map readPoiXMLToMap(String xml) {
        Map result = new HashMap();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点

            Element searchResultElement = (Element)doc.selectSingleNode("/LbsResult/KeywordRes/list/searchresult");

            result.put("count",searchResultElement.elementTextTrim("bounds"));

            Element listElement = searchResultElement.element("list");

            Iterator iter = listElement.elementIterator("poi");

            // 遍历head节点
            List<Map<String,String>> poilist = new ArrayList<>();
            while (iter.hasNext()) {
                Map map = new HashMap();
                Element poiElement = (Element) iter.next();
                String poiid = poiElement.elementTextTrim("POIID");
                String name = poiElement.elementTextTrim("NAME");
                String otype = poiElement.elementTextTrim("OTYPE");
                String stype = poiElement.elementTextTrim("STYPE");
                String atype = poiElement.elementTextTrim("ATYPE");
                String x = poiElement.elementTextTrim("X");
                String y = poiElement.elementTextTrim("Y");
                String adminCode = poiElement.elementTextTrim("ADMIN_CODE");
                String provinceName = poiElement.elementTextTrim("PROVINCE_NAME");
                String cityName = poiElement.elementTextTrim("CITY_NAME");
                String cityCode = poiElement.elementTextTrim("CITY_CODE");
                String adminName = poiElement.elementTextTrim("ADMIN_NAME");
                String distance = poiElement.elementTextTrim("distance");
                String address = poiElement.elementTextTrim("ADDRESS");
                String telephone = poiElement.elementTextTrim("TELEPHONE");


                map.put("poiid", poiid);
                map.put("name", name);
                map.put("otype", otype);
                map.put("stype", stype);
                map.put("atype", atype);
                map.put("x", x);
                map.put("y", y);
                map.put("adminCode", adminCode);
                map.put("provinceName", provinceName);
                map.put("cityName", cityName);
                map.put("cityCode", cityCode);
                map.put("adminName", adminName);
                map.put("distance", distance);
                map.put("address", address);
                map.put("telephone", telephone);

                poilist.add(map);
            }
            result.put("poilist",poilist);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}