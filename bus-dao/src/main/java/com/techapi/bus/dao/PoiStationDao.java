package com.techapi.bus.dao;

import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PoiStationDao extends PagingAndSortingRepository<PoiStation, String>{
    @Query("select c from PoiStation c "
            + "where c.poiStationPK.adCode = :adCode")
    public List<PoiStation> findByAdCode(@Param("adCode") String adCode);

    @Query(value = "SELECT * FROM(SELECT A.*, rownum r FROM(SELECT * FROM bus_poistation) A WHERE rownum <= ?2) B WHERE r > ?1", nativeQuery = true)
    public List<PoiStation> findAllByPage(int pageStart, int pageEnd);

    @Query(value = "SELECT * FROM(SELECT A.*, rownum r FROM(SELECT * FROM bus_poistation WHERE ADCODE = ?3) A WHERE rownum <= ?2) B WHERE r > ?1", nativeQuery = true)
    public List<PoiStation> findByAdCodeByPage(int pageStart, int pageEnd, String adCode);


    @Query("select count(c) from PoiStation c"
            + " where c.poiStationPK.adCode = :adCode")
    public int findCountByAdCode(@Param("adCode") String adCode);

    @Query("select count(c) from PoiStation c")
    public int findAllCount();
}
