package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.AnalysisGroup;

public interface AnalysisGroupDao extends PagingAndSortingRepository<AnalysisGroup, String>{

	@Query("select a from AnalysisGroup a where a.name = :name and a.minute >=:startTime and a.minute<=:endTime")
	public List<AnalysisGroup> findByTimeAndName(@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);

	@Query("select a from AnalysisGroup a where a.minute >=:startTime and a.minute<=:endTime")
	public List<AnalysisGroup> findByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query("select distinct a.name from AnalysisGroup a")
	public List<String> findNameAll();
}
