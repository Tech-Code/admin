package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.UserKeyDao;
import com.techapi.bus.entity.UserKey;
import com.techapi.bus.util.*;
import com.techapi.bus.vo.SpringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fei.xue
 * @Description:
 * @date 2014-3-8
 */
@Service
public class UserKeyService {

    @Resource
    private UserKeyDao userKeyDao;

    @Autowired
    private CacheProxy cacheProxy;

    private static Set<String> keysSet = new HashSet<>();

    public UserKey addOrUpdate(UserKey userKey) {

        if (keysSet.size() == 0) {
            Iterable<UserKey> userKeyList = userKeyDao.findAll();
            Iterator<UserKey> userKeyIterator = userKeyList.iterator();
            while (userKeyIterator.hasNext()) {
                UserKey userKey1 = userKeyIterator.next();
                keysSet.add(userKey1.getKey());
            }
        }

        // 生成key
        String key = userKey.getKey();
        if (key == null || key.isEmpty()) {
            do {
                key = Common.getRandStr(80, false, true);
            } while (!keysSet.add(key));

            userKey.setKey(key);
            userKey.setSource(1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String strDate = sdf.format(new Date());
            userKey.setCreateDate(strDate);
        }

        String ctlcache = String.format(BusConstants.BUS_CTL_KEY, key);

        if (userKey != null) {
            //补cache
            cacheProxy.put(ctlcache, userKey, -1, BusConstants.REDIS_INDEX_KEY);
        } else {
            //增加null，防止击穿cache，压力数据库
            cacheProxy.put(ctlcache, new ArrayList<UserKey>(), TTL._10M.getTime(), BusConstants.REDIS_INDEX_KEY);
        }
        return userKeyDao.save(userKey);
    }

    /**
     * @return
     */
    public Map<String, Object> findAll() {

        List<UserKey> userKeyList = (List<UserKey>) userKeyDao.findAll();
        return PageUtils.getPageMap(userKeyList);
    }

    public Map<String, Object> findSection(int page, int rows) {
        Pageable pager = new PageRequest(page - 1, rows);
        Page<UserKey> userKeyList = userKeyDao.findAll(pager);
        return PageUtils.getPageMap(userKeyList);
    }

    /**
     * @return
     */
    public UserKey findById(String id) {
        UserKey userKey = userKeyDao.findOne(id);
        return userKey;
    }


    public List<UserKey> findByIds(List<String> ids) {
        return (List<UserKey>) userKeyDao.findAll(ids);
    }


    public void deleteOne(String id) {
        userKeyDao.delete(id);
    }

    public int deleteMany(List<UserKey> userKeyList) {
        int deleteCount = 0;
        for (UserKey userKey : userKeyList) {
            if (userKey.getSource() == 1) {
                if (userKey != null) {
                    String poicache = String.format(BusConstants.BUS_CTL_KEY, userKey.getKey());
                    cacheProxy.delete(poicache, BusConstants.REDIS_INDEX_KEY);
                }
                userKeyDao.delete(userKey);
                deleteCount++;
            }
        }
        if(deleteCount == 0) return BusConstants.DELETE_NONE_USERKEY_STATUS;
        if(deleteCount == userKeyList.size()) return BusConstants.DELETE_ALL_USERKEY_STATUS;
        if (deleteCount < userKeyList.size()) return BusConstants.DELETE_PART_USERKEY_STATUS;

        return BusConstants.DELETE_NONE_USERKEY_STATUS;
    }


    public List<SpringMap> findAllBusinessTypes(int notAll) {
        Map<String, String> businessTypeMap = PropertyMapUtils.getBusinessTypeMap();
        Iterator<String> iterator = businessTypeMap.keySet().iterator();
        List<SpringMap> springMapList = new ArrayList<>();
        while (iterator.hasNext()) {
            String businessTypeName = iterator.next();
            SpringMap springMap = new SpringMap();
            springMap.setId(businessTypeName);
            springMap.setText(businessTypeName);
            springMapList.add(springMap);
        }
        if(notAll == 0) {
            SpringMap totalSpringMap = new SpringMap();
            totalSpringMap.setId("all");
            totalSpringMap.setText("全部");
            springMapList.add(totalSpringMap);
        }

        return springMapList;
    }

    public Map<String, Object> findBySearchBySection(int page, int rows, String businessName, String businessFlag, String selectBusinessType, String province, String businessUrl, String key) {
        Pageable pager = new PageRequest(page - 1, rows);

        List<UserKey> searchResult = userKeyDao.findBySearch("%" + businessName + "%", "%" + businessFlag + "%", "%" + selectBusinessType + "%", "%" + province + "%", "%" + businessUrl + "%", "%" + key + "%");

        return PageUtils.getPageMap(searchResult, pager);
    }

    public String findBySearchToExcel(HttpServletResponse response, String businessName, String businessFlag, String selectBusinessType, String province, String businessUrl, String key) {
        List<UserKey> searchResult = userKeyDao.findBySearch("%" + businessName + "%", "%" + businessFlag + "%", "%" + selectBusinessType + "%", "%" + province + "%", "%" + businessUrl + "%", "%" + key + "%");
        String[] title = {"时间", "业务名称", "业务子名称", "业务标识", "业务分类", "使用API", "省份", "状态", "厂商" , "业务地址", "key", "联系人", "资源", "来源"};
        String result = ExportExcel.exportExcel(response, "userkey.xls", title, searchResult);
        return result;

    }


}