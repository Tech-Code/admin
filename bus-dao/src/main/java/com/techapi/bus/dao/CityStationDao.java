package com.techapi.bus.dao;

import com.techapi.bus.entity.CityStation;
import com.techapi.bus.entity.PoiStation;
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
//    @Query(value="select t.TRANSTYPE, t.TRIPS, t.TRANSDETAIL,t.CITYCODE, t.STATION, t.STATIONORDER, t.COORDINATE, t.ARRIVETIME, t.DEPARTTIME, t.MILES, t.PRICE, c.cityName from BUS_TRANSSTATION t inner join BUS_CITYSTATION c on T.STATION = C.stationName order by t.STATIONORDER",nativeQuery=true)
    @Query(value="select c.TRANSTYPE, t.TRIPS, c.TRANSDETAIL,c.CITYCODE, c.stationname as station, t.STATIONORDER, c.COORDINATE, t.ARRIVETIME, t.DEPARTTIME, t.MILES, t.PRICE, c.cityName from BUS_TRANSSTATION t inner join BUS_CITYSTATION c on T.citystationid = C.id order by t.STATIONORDER",nativeQuery=true)
    public List<Object[]> findCityAndTransstation();


    @Query("select c from CityStation c "
            + "where c.cityStationName = :stationName")
    public List<CityStation> findByStationName(
            @Param("stationName") String stationName);

    @Query("select c from CityStation c "
            + "where c.cityStationName like :stationName "
            + "and c.cityCode like :cityCode "
            + "and c.cityName like :cityName "
            + "and c.transType like :transType ")
    public List<CityStation> findBySearch(
            @Param("cityCode") String cityCode,
            @Param("cityName") String cityName,
            @Param("transType") String transType,
            @Param("stationName") String stationName);

    @Query("select count(c) from CityStation c"
            + " where c.cityCode = :cityCode")
    public int findCountByCityCode(@Param("cityCode") String cityCode);

    @Query("select count(c) from CityStation c")
    public int findAllCount();

    @Query(value = "SELECT * FROM(SELECT A.*, rownum r FROM(SELECT * FROM bus_citystation) A WHERE rownum <= ?2) B WHERE r > ?1", nativeQuery = true)
    public List<CityStation> findAllByPage(int pageStart, int pageEnd);

    @Query(value = "SELECT * FROM(SELECT A.*, rownum r FROM(SELECT * FROM bus_citystation WHERE ADCODE = ?3) A WHERE rownum <= ?2) B WHERE r > ?1", nativeQuery = true)
    public List<CityStation> findByCityCodeByPage(int pageStart, int pageEnd, String cityCode);


}
