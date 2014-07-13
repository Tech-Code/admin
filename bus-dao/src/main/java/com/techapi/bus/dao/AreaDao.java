package com.techapi.bus.dao;

import com.techapi.bus.entity.Area;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AreaDao extends PagingAndSortingRepository<Area, String>{

	@Query("select c from Area c where c.areaName like :cityCodeName")
	List<Area> findByName(@Param("cityCodeName") String cityCodeName);
	
	@Query("select c from Area c where c.areaCode= :cityCode and areaType=1")
	List<Area> findByCode(@Param("cityCode") int cityCode);

    @Query("select c from Area c "
            + "where c.areaName = :area_name and c.areaType = 1")
    public Area findByAreaName(@Param("area_name") String area_name);
	
	/***
	 * 查询地级城市
	 * @return
	 */
	@Query("select c from Area c where areaType=1")
	List<Area> findAllFilterType();

    @Query("select c from Area c "
            + "where c.areaType = :area_type and c.areaCode != null and c.deleteFlag ='0'")
    public List<Area> findByType(@Param("area_type") int area_type);
}
