package com.techapi.bus.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.UserKey;

public interface UserKeyDao extends PagingAndSortingRepository<UserKey, String> {

	@Query("select c from UserKey c " + "where c.key = :key")
	public UserKey findOneByKey(@Param("key") String key);
}
