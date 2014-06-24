package com.techapi.bus.service;

import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.UserKeyDao;
import com.techapi.bus.entity.UserKey;
import com.techapi.bus.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-8
 */
@Service
public class UserKeyService {

	@Resource
	private UserKeyDao userKeyDao;

    @Autowired
    private CacheProxy cacheProxy;

    public void addOrUpdate(UserKey userKey) {
        userKeyDao.save(userKey);
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {

		List<UserKey>userKeyList = (List<UserKey>) userKeyDao.findAll();
        return PageUtils.getPageMap(userKeyList);
	}

    public Map<String,Object> findSection(int page,int rows) {
        Pageable pager = new PageRequest(page-1, rows);
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
        userKeyDao.delete(userKeyList);
    }


}