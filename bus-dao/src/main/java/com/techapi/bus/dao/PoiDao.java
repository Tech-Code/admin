package com.techapi.bus.dao;

import com.techapi.bus.entity.Poi;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PoiDao extends PagingAndSortingRepository<Poi, String>, JpaSpecificationExecutor<Poi> {

	@Query("select c from Poi c ")
	public List<Poi> findBystationID();

    @Query("select c from Poi c "
            + "where c.poiId in :poiIds")
    public List<Poi> findByids(@Param("poiIds") String[] poiIds);

    @Query("select c from Poi c "
            + "where c.poiId = :poiId")
    public Poi findByPoiId(@Param("poiId") String poiId);

    @Query("select c from Poi c "
            + "where c.poiName = :poiName and c.gridId = :gridId")
    public Poi findByPoiNameAndGridId(@Param("poiName") String poiName,
                                      @Param("gridId") String gridId);

    @Query("select count(c) from Poi c"
            + " where c.cityCode like :cityCode ")
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


    @Query(value = "SELECT COUNT(*)\n" +
            "  FROM BUS_POI\n" +
            " WHERE CITYCODE LIKE ?1\n" +
            "   AND CITYNAME LIKE ?2\n" +
            "   AND POINAME LIKE ?3\n", nativeQuery = true)
    public int findAllCount(String cityCode,
                            String cityName,
                            String poiName);

    //@Query(value = "SELECT count(*) FROM bus_poi C INNER JOIN (select * from bus_area E where e.area_type = '1') D ON substr(C.citycode,0,DECODE(d.area_name,'北京市',3,'天津市',3,'上海市',3,'重庆市',3,4)) = substr(D.ad_code, 0, DECODE(d.area_name,'北京市',3,'天津市',3,'上海市',3,'重庆市',3,4))  where c.citycode like ?1 and d.area_name like ?2 and c.poiname like ?3", nativeQuery = true)
    //public int findAllCount(String cityCode,
    //                        String cityName,
    //                        String poiName);


    @Query(value = "  SELECT *\n" +
            "          FROM (SELECT A.*, ROWNUM R\n" +
            "            FROM (SELECT *\n" +
            "                    FROM BUS_POI\n" +
            "                   WHERE CITYCODE LIKE ?3\n" +
            "                     AND CITYNAME LIKE ?4\n" +
            "                     AND POINAME  LIKE ?5\n)A" +
            "           WHERE ROWNUM <= ?2) B\n" +
            "         WHERE R > ?1", nativeQuery = true)
    public List<Poi> findBySearch(int pageStart, int pageEnd,
                                       String cityCode,
                                       String cityName,
                                       String poiName);

    //@Query(value = "SELECT B.CITYCODE,B.POIID,B.POINAME,B.POITYPE1,B.POITYPE2,B.POITYPE3,B.POICOORDINATE,B.ADDRESS,B.TEL,B.GRIDID,B.AREA_NAME as CITYNAME FROM (SELECT A.*,rownum r FROM (SELECT C.*,D.area_name FROM bus_poi C INNER JOIN (select * from bus_area E where e.area_type = '1') D ON substr(C.citycode,0,DECODE(d.area_name,'北京市',3,'天津市',3,'上海市',3,'重庆市',3,4)) = substr(D.ad_code, 0, DECODE(d.area_name,'北京市',3,'天津市',3,'上海市',3,'重庆市',3,4)) where c.citycode like ?3 and d.area_name like ?4 and c.poiname like ?5) A WHERE rownum <= ?2) B WHERE r > ?1", nativeQuery = true)
    //public List<Object[]> findBySearch(int pageStart, int pageEnd,
    //                                   String cityCode,
    //                                   String cityName,
    //                                   String poiName);


    @Query(value = "SELECT COUNT(*)\n" +
            "  FROM BUS_POI\n" +
            " WHERE CITYCODE LIKE ?1\n" +
            "   AND CITYNAME LIKE ?2\n" +
            "   AND POINAME LIKE ?3\n" +
            "   AND GRIDID IN (?4)", nativeQuery = true)
    public int findAllCountByGridIds(String cityCode,
                            String cityName,
                            String poiName,
                            String[] gridIds);


    // @Query(value = "SELECT count(*) FROM bus_poi C INNER JOIN (select * from bus_area E where e.area_type = '1') D ON substr(C.citycode,0,DECODE(d.area_name,'北京市',3,'天津市',3,'上海市',3,'重庆市',3,4)) = substr(D.ad_code, 0, DECODE(d.area_name,'北京市',3,'天津市',3,'上海市',3,'重庆市',3,4))  where c.citycode like ?1 and d.area_name like ?2 and c.poiname like ?3 and c.gridid in (?4)", nativeQuery = true)
    //public int findAllCountByGridIds(String cityCode,
    //                        String cityName,
    //                        String poiName,
    //                        String[] poiIds);

    @Query(value = "  SELECT *\n" +
            "          FROM (SELECT A.*, ROWNUM R\n" +
            "            FROM (SELECT *\n" +
            "                    FROM BUS_POI\n" +
            "                   WHERE CITYCODE LIKE ?3\n" +
            "                     AND CITYNAME LIKE ?4\n" +
            "                     AND POINAME  LIKE ?5\n" +
            "                     AND GRIDID IN (?6))A"    +
            "           WHERE ROWNUM <= ?2) B\n" +
            "         WHERE R > ?1", nativeQuery = true)
    public List<Poi> findBySearchAndGridIds(int pageStart, int pageEnd,
                                       String cityCode,
                                       String cityName,
                                       String poiName,
                                       String[] gridIds);




    @Query(value = "SELECT *\n" +
            "  FROM (SELECT A.*, ROWNUM R\n" +
            "          FROM (SELECT *\n" +
            "                  FROM BUS_POI\n" +
            "                 WHERE POIID IN\n" +
            "                       (SELECT POIID\n" +
            "                          FROM (SELECT T1.POIID,\n" +
            "                                       FN_GET_DISTANCE(?8, ?9, T1.X, T1.Y) AS DISTANCE,\n" +
            "                                       ROW_NUMBER() OVER(ORDER BY FN_GET_DISTANCE(?8, ?9, T1.X, T1.Y)) AS RN\n" +
            "                                  FROM (SELECT T.POIID,\n" +
            "                                               SUBSTR(T.POICOORDINATE,\n" +
            "                                                      1,\n" +
            "                                                      INSTR(T.POICOORDINATE,\n" +
            "                                                            ',',\n" +
            "                                                            1,\n" +
            "                                                            1) - 1) AS X,\n" +
            "                                               SUBSTR(T.POICOORDINATE,\n" +
            "                                                      INSTR(T.POICOORDINATE,\n" +
            "                                                            ',',\n" +
            "                                                            1,\n" +
            "                                                            1) + 1,\n" +
            "                                                      12) AS Y\n" +
            "                                          FROM BUS_POI T\n" +
            "                                         WHERE CITYCODE LIKE ?3\n" +
            "                                           AND CITYNAME LIKE ?4\n" +
            "                                           AND POINAME LIKE ?5\n" +
            "                                           AND GRIDID IN (?6)) T1) T2\n" +
            "                         WHERE T2.DISTANCE < ?7)) A WHERE ROWNUM <= ?2) B WHERE R > ?1", nativeQuery = true)
    public List<Poi> findBySearchAndGridIdsAndRange(int pageStart, int pageEnd,
                                                    String cityCode,
                                                    String cityName,
                                                    String poiName,
                                                    String[] gridIds,
                                                    int range,
                                                    float clon,
                                                    float clat);


    @Query(value = "SELECT count(*)\n" +
            "                  FROM BUS_POI\n" +
            "                 WHERE POIID IN\n" +
            "                       (SELECT POIID\n" +
            "                          FROM (SELECT T1.POIID,\n" +
            "                                       FN_GET_DISTANCE(?6, ?7, T1.X, T1.Y) AS DISTANCE,\n" +
            "                                       ROW_NUMBER() OVER(ORDER BY FN_GET_DISTANCE(?6, ?7, T1.X, T1.Y)) AS RN\n" +
            "                                  FROM (SELECT T.POIID,\n" +
            "                                               SUBSTR(T.POICOORDINATE,\n" +
            "                                                      1,\n" +
            "                                                      INSTR(T.POICOORDINATE,\n" +
            "                                                            ',',\n" +
            "                                                            1,\n" +
            "                                                            1) - 1) AS X,\n" +
            "                                               SUBSTR(T.POICOORDINATE,\n" +
            "                                                      INSTR(T.POICOORDINATE,\n" +
            "                                                            ',',\n" +
            "                                                            1,\n" +
            "                                                            1) + 1,\n" +
            "                                                      12) AS Y\n" +
            "                                          FROM BUS_POI T\n" +
            "                                         WHERE CITYCODE LIKE ?1\n" +
            "                                           AND CITYNAME LIKE ?2\n" +
            "                                           AND POINAME LIKE ?3\n" +
            "                                           AND GRIDID IN (?4)) T1) T2\n" +
            "                         WHERE T2.DISTANCE < ?5)", nativeQuery = true)
    public int findBySearchAndGridIdsAndRangeCount(String cityCode,
                                                    String cityName,
                                                    String poiName,
                                                    String[] gridIds,
                                                    int range,
                                                    float clon,
                                                    float clat);


    //@Query(value = "SELECT B.CITYCODE,B.POIID,B.POINAME,B.POITYPE1,B.POITYPE2,B.POITYPE3,B.POICOORDINATE,B.ADDRESS,B.TEL,B.GRIDID,B.AREA_NAME as CITYNAME FROM (SELECT A.*,rownum r FROM (SELECT C.*,D.area_name FROM bus_poi C INNER JOIN (select * from bus_area E where e.area_type = '1') D ON substr(C.citycode,0,DECODE(d.area_name,'北京市',3,'天津市',3,'上海市',3,'重庆市',3,4)) = substr(D.ad_code, 0, DECODE(d.area_name,'北京市',3,'天津市',3,'上海市',3,'重庆市',3,4)) where c.citycode like ?3 and d.area_name like ?4 and c.poiname like ?5 and c.gridid in (?6)) A WHERE rownum <= ?2) B WHERE r > ?1", nativeQuery = true)
    //public List<Object[]> findBySearchAndGridIds(int pageStart, int pageEnd,
    //                                             String cityCode,
    //                                             String cityName,
    //                                             String poiName,
    //                                             String[] poiIds);


}
