package com.techapi.bus.data;

import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.UserKeyDao;
import com.techapi.bus.entity.UserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ImportUserKeyService {

    @Resource
    private UserKeyDao dao;

    @Autowired
    private CacheProxy cacheProxy;

    public void importUserKey(List<UserKey> userKeyList) {
        dao.save(userKeyList);
	}
}
