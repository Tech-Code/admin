package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.AnalysisCity;

public interface AnalysisCityDao  extends PagingAndSortingRepository<AnalysisCity, String>{

	@Query("select a from AnalysisCity a where a.cityName = :name and a.day >=:startTime and a.day<=:endTime")
	public List<AnalysisCity> findByTimeAndType(@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);

	@Query("select a from AnalysisCity a where a.day >=:startTime and a.day<=:endTime")
	public List<AnalysisCity> findByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query("select distinct a.cityName from AnalysisCity a")
	public List<String> findTypeAll();
	
}
