package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.techapi.bus.entity.Poi;

@Component("poiDao")
public interface PoiDao extends PagingAndSortingRepository<Poi, String>{

	@Query("select c from Poi c "
			+ "where c.stationId = :stationid ")
	public List<Poi> findBystationID(@Param("stationid") String stationid);
}
