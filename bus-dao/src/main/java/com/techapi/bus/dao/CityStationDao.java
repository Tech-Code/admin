package com.techapi.bus.dao;

import com.techapi.bus.entity.CityStation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by XF on 4/16/14.
 */
@Transactional(readOnly = true)
public interface CityStationDao extends PagingAndSortingRepository<CityStation, String> {
	
	@Query("select c from CityStation c "
			+ "where c.cityCode = :cityCode ")
	List<CityStation> findByCityCode(
			@Param("cityCode") String cityCode);

    @Modifying
    @Transactional
    @Query("update CityStation c set c.cityName = :cityName where c.cityCode = :cityCode")
    public int updateCityStation(@Param("cityCode") String cityCode,
                             @Param("cityName") String cityName);

	
	
}
