package com.techapi.bus.dao;

import com.techapi.bus.entity.CityStation;
import com.techapi.bus.entity.Speed;
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
    /***
     * 原生sql，解析时需要按顺序
     * @return
     */
    @Query(value="select t.TRANSTYPE, t.TRIPS, t.TRANSDETAIL,t.CITYCODE, t.STATION, t.STATIONORDER, t.COORDINATE, t.ARRIVETIME, t.DEPARTTIME, t.MILES, t.PRICE, c.cityName from BUS_TRANSSTATION t inner join BUS_CITYSTATION c on T.STATION = C.stationName order by t.STATIONORDER",nativeQuery=true)
    public List<Object[]> findCityAndTransstation();


    @Query("select c from CityStation c "
            + "where c.stationName = :stationName")
    public List<CityStation> findByStationName(
            @Param("stationName") String stationName);

}
