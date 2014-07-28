package com.techapi.bus.dao;

import com.techapi.bus.entity.UserKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserKeyDao extends PagingAndSortingRepository<UserKey, String> {

	@Query("select c from UserKey c " + "where c.generateKey = :generateKey")
	public UserKey findOneByKey(@Param("generateKey") String generateKey);

    @Query("select c from UserKey c "
            + "where coalesce(c.businessName,'0') like :businessName "
            + "and coalesce(c.businessFlag,'0') like :businessFlag "
            + "and coalesce(c.businessType,'0') like :businessType "
            + "and coalesce(c.province,'0') like :province "
            + "and coalesce(c.businessUrl,'0') like :businessUrl "
            + "and c.generateKey like :generateKey ")
        public List<UserKey> findBySearch(
            @Param("businessName") String businessName,
            @Param("businessFlag") String businessFlag,
            @Param("businessType") String businessType,
            @Param("province") String province,
            @Param("businessUrl") String businessUrl,
            @Param("generateKey") String generateKey);


}
