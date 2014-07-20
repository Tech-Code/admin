package com.techapi.bus.dao;

import com.techapi.bus.entity.Poi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PoiDao extends PagingAndSortingRepository<Poi, String>, JpaSpecificationExecutor<Poi> {

	@Query("select c from Poi c "
			+ "where c.poiPK.stationId = :stationid order by walkDistance asc")
	public List<Poi> findBystationID(@Param("stationid") String stationid);

    @Query("select c from Poi c "
            + "where c.id = :id")
    public Poi findOneById(@Param("id") String id);

    @Query("select c from Poi c "
            + "where c.id in :ids")
    public List<Poi> findByids(@Param("ids") String[] ids);

    @Query("select c from Poi c "
            + "where c.poiPK.stationId = :stationId and c.poiPK.poiId = :poiId")
    public Poi findBystationIDAndPoiId(@Param("stationId") String stationId,
                                             @Param("poiId") String poiId);

    @Query("select count(c) from Poi c"
            + " where c.poiPK.stationId like :cityCode ")
    public int findCountByCityCode(@Param("cityCode") String cityCode);

    @Query(value = "SELECT * FROM(SELECT A.*, rownum r FROM(SELECT * FROM bus_poi) A WHERE rownum <= ?2) B WHERE r > ?1", nativeQuery = true)
    public List<Poi> findByCityCode(int pageStart,int pageEnd);

    //@Query(value = "SELECT B.ID,B.CITYCODE,B.STATIONID,B.POIID,B.POINAME,B.POITYPE1,B.POITYPE2,B.POITYPE3,B.POICOORDINATE,B.WALKDISTANCE,B.ORIENTATION,B.ADDRESS,B.TEL,B.AREA_NAME as CITYNAME FROM (SELECT A.*,rownum r FROM (SELECT C.*,D.area_name FROM bus_poi C INNER JOIN bus_area D ON C.citycode = D.ad_code) A WHERE rownum <= ?2) B WHERE r > ?1", nativeQuery = true)
    //public List<Object[]> findAllByPage(int pageStart, int pageEnd);

    //@Query(value = "SELECT * from bus_poi a " +
    //        " inner join bus_station b on a.stationid = b.STATIONID " +
    //        " inner join BUS_CITYSTATION c on a.citycode = c.citycode " +
    //        " where c.cityname like ?3 and a.cityCode like ?1 and a.poiName like ?2 and b.stationName like ?4", nativeQuery = true)
    //public List<Poi> findBySearch(
    //        String cityCode,
    //        String poiName,
    //        String cityName,
    //        String stationName);

    @Query(value = "SELECT count(*) FROM bus_poi C INNER JOIN bus_area D ON C.citycode = D.ad_code where c.citycode like ?1 and d.area_name like ?2 and c.stationid like ?3 and c.poiname like ?4",nativeQuery = true)
    public int findAllCount(String cityCode,
                            String cityName,
                            String poiName,
                            String stationId);

    @Query(value = "SELECT B.ID,B.CITYCODE,B.STATIONID,B.POIID,B.POINAME,B.POITYPE1,B.POITYPE2,B.POITYPE3,B.POICOORDINATE,B.WALKDISTANCE,B.ORIENTATION,B.ADDRESS,B.TEL,B.AREA_NAME as CITYNAME FROM (SELECT A.*,rownum r FROM (SELECT C.*,D.area_name FROM bus_poi C INNER JOIN bus_area D ON C.citycode = D.ad_code where c.citycode like ?3 and d.area_name like ?4 and c.stationid like ?5 and c.poiname like ?6) A WHERE rownum <= ?2) B WHERE r > ?1", nativeQuery = true)
    public List<Object[]> findBySearch(int pageStart, int pageEnd,
            String cityCode,
            String cityName,
            String poiName,
            String stationId);


}
