package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.dao.UserDao;
import com.techapi.bus.entity.User;
import com.techapi.bus.util.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author Andrew.Xue
 * @date 2014-5-31
 */
@Service
public class UserService {

	@Resource
	private UserDao userDao;

    public Map<String, String> addOrUpdate(User user) throws Exception {
        Map<String, String> resultMap = new HashMap<>();
        String id = user.getId();
        User existedUser;

        String role = user.getRole();
        String[] roles = role.split(",");
        if (roles.length > 1) user.setRole(roles[1]);

        if(id == null || id.isEmpty()) { // 新增
            existedUser = userDao.findByLoginName(user.getLoginName().trim());
            if(user.getRole().isEmpty()) {
                resultMap.put("result", BusConstants.RESULT_NOTEXIST_LOGINNAME);
                resultMap.put("alertInfo", BusConstants.RESULT_NOTEXIST_LOGINNAME_STR);
                return resultMap;
            }
            if(existedUser != null) {
                resultMap.put("result", BusConstants.RESULT_REPEAT_LOGINNAME);
                resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_LOGINNAME_STR);
                return resultMap;
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            user.setTime(df.format(new Date()));
        } else { // 修改
            existedUser = userDao.findByLoginName(user.getLoginName().trim());
            if(existedUser.getRole().equals("0") && user.getRole().equals("1")) { // 从管理员-》普通用户
                int count = userDao.findAdminUserCount();
                if(count < 2) {
                    User onlyAdminUser = userDao.findOneByRole("0");
                    if(onlyAdminUser != null && onlyAdminUser.getId().equals(user.getId())) {
                        resultMap.put("result", BusConstants.RESULT_NOADMIN);
                        resultMap.put("alertInfo", BusConstants.RESULT_NOADMIN_STR);
                        return resultMap;
                    }
                }
            }
            if (existedUser != null && !existedUser.getId().equals(id)) {
                resultMap.put("result", BusConstants.RESULT_REPEAT_LOGINNAME);
                resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_LOGINNAME_STR);
                return resultMap;
            }

            user.setTime(existedUser.getTime());
        }
        userDao.save(user);
        resultMap.put("id", user.getId());
        resultMap.put("result", BusConstants.RESULT_SUCCESS);
        resultMap.put("alertInfo", BusConstants.RESULT_SUCCESS_STR);
        return resultMap;

    }

	/**
	 * @param userName
     * @param password
	 * @return
	 */
	public User login(String userName,String password) {
		User user = userDao.login(userName, password);
		return user;
		
	}

    /**
     * @return
     */
    public Map<String, Object> getAllUser() {
        List<User> list = (List<User>)userDao.findAll();
        return PageUtils.getPageMap(list);
    }

    public User getUserById(String id) {
        User user = userDao.findById(id);
        return user;
    }

    public void deleteMany(List<User> userList) {
        userDao.delete(userList);
    }

    public List<User> findByIds(List<String> ids) {
        return (List<User>) userDao.findAll(ids);
    }

    public int findAdminUserCount() {
        return userDao.findAdminUserCount();
    }

    public User findOneByRole(String role) {
        return userDao.findOneByRole(role);
    }

    public List<String> findAllIdsByRole(String role) {
        return userDao.findAllIdsByRole(role);
    }
}