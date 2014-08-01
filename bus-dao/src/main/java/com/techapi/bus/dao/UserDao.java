package com.techapi.bus.dao;

import com.techapi.bus.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by XF on 4/16/14.
 */
public interface UserDao extends PagingAndSortingRepository<User, String> {
	
	@Query("select u from User u "
			+ "where u.loginName = :loginName and u.password = :password")
	User login(
            @Param("loginName") String loginName,
            @Param("password") String password);

    @Query("select u from User u "
            + "where u.loginName = :loginName")
    User findByLoginName(@Param("loginName") String loginName);

    @Query("select u from User u "
            + "where u.id = :id")
    User findById(@Param("id") String id);

    @Query(value = "select count(*) from BUS_USER "
            + "where role = 0",nativeQuery = true)
    int findAdminUserCount();

    @Query(value = "  select * from bus_user where role=?1 and rownum=1", nativeQuery = true)
    User findOneByRole(String role);

    @Query(value = "  select id from bus_user where role=?1", nativeQuery = true)
    List<String> findAllIdsByRole(String role);
}
