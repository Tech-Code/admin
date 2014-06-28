package com.techapi.bus.data;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.UserKeyDao;
import com.techapi.bus.entity.UserKey;
import com.techapi.bus.util.TTL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportUserKeyService {

    @Resource
    private UserKeyDao dao;

    @Autowired
    private CacheProxy cacheProxy;

    public void importUserKey(List<UserKey> userKeyList) {
        System.out.println("Begin----------------insert redis");
        // import to redis
        int index = 2;
        for(UserKey userKey : userKeyList) {
            String key = userKey.getKey();
            String ctlcache = String.format(BusConstants.BUS_CTL_KEY, key);

            if (userKey != null) {
                //补cache
                cacheProxy.put(ctlcache, userKey,-1,index);
            } else {
                //增加null，防止击穿cache，压力数据库
                cacheProxy.put(ctlcache, new ArrayList<UserKey>(), TTL._10M.getTime(),index);
            }

        }
        System.out.println("Begin----------------insert db");
        dao.save(userKeyList);
	}
}
