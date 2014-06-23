package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.AnalysisType;

public interface AnalysisTypeDao  extends PagingAndSortingRepository<AnalysisType, String>{

	@Query("select a from AnalysisType a where a.type = :type and a.day >=:startTime and a.day<=:endTime")
	public List<AnalysisType> findByTimeAndType(@Param("type") String type,@Param("startTime") String startTime,@Param("endTime") String endTime);

	@Query("select a from AnalysisType a where a.day >=:startTime and a.day<=:endTime")
	public List<AnalysisType> findByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query("select distinct a.type from AnalysisType a")
	public List<String> findTypeAll();
}
