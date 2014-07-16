package com.techapi.bus.dao;

import com.techapi.bus.entity.CityStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.UserKey;

import java.util.List;

public interface UserKeyDao extends PagingAndSortingRepository<UserKey, String> {

	@Query("select c from UserKey c " + "where c.key = :key")
	public UserKey findOneByKey(@Param("key") String key);

    @Query("select c from UserKey c "
            + "where c.businessName like :businessName "
            + "and c.businessFlag like :businessFlag "
            + "and c.businessType like :businessType "
            + "and c.province like :province "
            + "and c.businessUrl like :businessUrl "
            + "and c.key like :key ")
    public List<UserKey> findBySearch(
            @Param("businessName") String businessName,
            @Param("businessFlag") String businessFlag,
            @Param("businessType") String businessType,
            @Param("province") String province,
            @Param("businessUrl") String businessUrl,
            @Param("key") String key);
}
