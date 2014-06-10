package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.techapi.bus.entity.Speed;
@Component("speedDao")
public interface SpeedDao extends PagingAndSortingRepository<Speed, String> {

	/***
	 * 根据城市名称和交通工具查询时速
	 * @param transportation
	 * @param cityname
	 * @return
	 */
	@Query("select c from Speed c "
			+ "where c.tranSportation = :transportation and c.cityName= :cityname")
	public List<Speed> findByTransportationAndCityName(
			@Param("transportation") String transportation,
			@Param("cityname") String cityname);
	
	/***
	 * 根据交通工具明细和城市查询换乘
	 * @param transportation
	 * @param cityname
	 * @param transportdes
	 * @return
	 */
	@Query("select c from Speed c "
			+ "where c.tranSportation = :transportation and c.cityName= :cityname and c.tranSportDes= :transportdes")
	public List<Speed> findByTransportationAndDetailAndCityName(
			@Param("transportation") String transportation,
			@Param("cityname") String cityname,@Param("transportdes") String transportdes);
}
