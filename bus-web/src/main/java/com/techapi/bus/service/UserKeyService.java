package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.UserKeyDao;
import com.techapi.bus.entity.UserKey;
import com.techapi.bus.util.Common;
import com.techapi.bus.util.PageUtils;
import com.techapi.bus.util.TTL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public void addOrUpdate(UserKey userKey) {

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
        userKeyDao.save(userKey);
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

    public void deleteMany(List<UserKey> userKeyList) {

        for (UserKey userKey : userKeyList) {
            if (userKey.getSource() == 1) {
                if (userKey != null) {
                    String poicache = String.format(BusConstants.BUS_CTL_KEY, userKey.getKey());
                    cacheProxy.delete(poicache, BusConstants.REDIS_INDEX_KEY);
                }
                userKeyDao.delete(userKey);
            }
        }

    }


}