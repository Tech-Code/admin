package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.Area;

public interface AreaDao extends PagingAndSortingRepository<Area, String>{

	@Query("select c from Area c where c.areaName like :cityCodeName")
	List<Area> findByName(@Param("cityCodeName") String cityCodeName);
	
	@Query("select c from Area c where c.adCode= :cityCode")
	List<Area> findByCode(@Param("cityCode") int cityCode);
}
