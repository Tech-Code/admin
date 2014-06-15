package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.Poi;

public interface PoiDao extends PagingAndSortingRepository<Poi, String>{

	@Query("select c from Poi c "
			+ "where c.stationId = :stationid order by walkDistance asc")
	public List<Poi> findBystationID(@Param("stationid") String stationid);
}
