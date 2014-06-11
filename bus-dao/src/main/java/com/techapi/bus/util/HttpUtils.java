package com.techapi.bus.util;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Http工具类
 * Created by xuefei on 6/9/14.
 */
public class HttpUtils {

    private static Log log = LogFactory.getLog(HttpUtils.class);

    /**
     * 定义编码格式 UTF-8
     */
    public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";

    /**
     * 定义编码格式 GBK
     */
    public static final String URL_PARAM_DECODECHARSET_GBK = "GBK";

    private static final String URL_PARAM_CONNECT_FLAG = "&";

    private static final String EMPTY = "";

    private static MultiThreadedHttpConnectionManager connectionManager = null;

    private static int connectionTimeOut = 100000;

    private static int socketTimeOut = 25000;

    private static int maxConnectionPerHost = 20;

    private static int maxTotalConnections = 20;

    private static HttpClient client;

    static{
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);
    }

    /**
     * POST方式提交数据
     * @param url
     * 			待请求的URL
     * @param params
     * 			要提交的数据
     * @param enc
     * 			编码
     * @return
     * 			响应结果
     * @throws IOException
     * 			IO异常
     */
    public static String URLPost(String url, Map<String, String> params, String enc){

        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            //将表单的值放入postMethod中
            Set<String> keySet = params.keySet();
            for(String key : keySet){
                String value = params.get(key);
                postMethod.addParameter(key, value);
            }
            //执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if(statusCode == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            }else{
                log.error("响应状态码 = " + postMethod.getStatusCode());
            }
        }catch(HttpException e){
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        }catch(IOException e){
            log.error("发生网络异常", e);
            e.printStackTrace();
        }finally{
            if(postMethod != null){
                postMethod.releaseConnection();
                postMethod = null;
            }
        }

        return response;
    }

    /**
     * GET方式提交数据
     * @param url
     * 			待请求的URL
     * @param params
     * 			要提交的数据
     * @param enc
     * 			编码
     * @return
     * 			响应结果
     * @throws IOException
     * 			IO异常
     */
    public static String URLGet(String url, Map<String, String> params, String enc){

        String response = EMPTY;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);

        if(strtTotalURL.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(getUrl(params, enc));
        } else {
            strtTotalURL.append(url).append("&").append(getUrl(params, enc));
        }
        log.debug("GET请求URL = \n" + strtTotalURL.toString());

        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            //执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if(statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsString();
            }else{
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        }catch(HttpException e){
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        }catch(IOException e){
            log.error("发生网络异常", e);
            e.printStackTrace();
        }finally{
            if(getMethod != null){
                getMethod.releaseConnection();
                getMethod = null;
            }
        }

        return response;
    }

    /**
     * 据Map生成URL字符串
     * @param map
     * 			Map
     * @param valueEnc
     * 			URL编码
     * @return
     * 			URL
     */
    private static String getUrl(Map<String, String> map, String valueEnc) {

        if (null == map || map.keySet().size() == 0) {
            return (EMPTY);
        }
        StringBuffer url = new StringBuffer();
        Set<String> keys = map.keySet();
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            if (map.containsKey(key)) {
                String val = map.get(key);
                String str = val != null ? val : EMPTY;
                try {
                    str = URLEncoder.encode(str, valueEnc);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
            }
        }
        String strURL = EMPTY;
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }

        return (strURL);
    }

    public static void main(String[] args) {
        Map<String,String> paraMap = new HashMap<>();

        paraMap.put("submit_form","poi_search");
        paraMap.put("query_type","RQBXY");
        paraMap.put("data_type","POI");
        paraMap.put("page_num","10");
        paraMap.put("page","1");
        paraMap.put("x","116.335432");
        paraMap.put("y","39.987598");
        paraMap.put("range","1000");
        paraMap.put("custom_and","false");
        paraMap.put("sort_rule","0");
        paraMap.put("geotype","rectangle");
        paraMap.put("display_type","1");


        String response = URLGet("http://221.180.144.45:9092/CMPOISearch2/lnm_sisserver.php?",paraMap,"UTF-8");

        Map map = XMLUtils.readPoiXMLToMap(response);
        System.out.println(response);
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            if("poilist".equals(key)) {
                List<Map<String,String>> poilist = (ArrayList)map.get("poilist");
                for (Map poiMap : poilist) {
                    Iterator poiIterator = poiMap.keySet().iterator();
                    while(poiIterator.hasNext()) {
                        String poikey = poiIterator.next().toString();
                        System.out.println("poikey: " + poikey + ", value: " + poiMap.get(poikey));
                    }
                    System.out.println("******************************************");

                }
            } else {
                System.out.println("key: " + key + ", value: " + map.get(key));
            }

        }
    }
}

