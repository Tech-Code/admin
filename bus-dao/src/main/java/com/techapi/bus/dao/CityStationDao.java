package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.CityStation;

/**
 * Created by XF on 4/16/14.
 */
public interface CityStationDao extends PagingAndSortingRepository<CityStation, String> {
	
	@Query("select c from CityStation c "
			+ "where c.cityCode = :cityCode ")
	List<CityStation> findByCityCode(
			@Param("cityCode") String cityCode);
	
	
}
