package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.AnalysisType;

public interface AnalysisTypeDao  extends PagingAndSortingRepository<AnalysisType, String>{

	@Query(value = "select a.name,a.type,sum(a.total) total from analysis_type a where a.type = :type and a.day >=:startTime and a.day<=:endTime group by a.name,a.type",nativeQuery=true)
	public List<Object[]> findByTimeAndType(@Param("type") String type,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select a.name,a.type,sum(a.total) total from analysis_type a where a.name = :name and a.day >=:startTime and a.day<=:endTime group by a.name,a.type",nativeQuery=true)
	public List<Object[]> findByTimeAndName(@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select a.name,a.type,sum(a.total) total from analysis_type  a where a.type = :type and a.name = :name and a.day >=:startTime and a.day<=:endTime group by a.name,a.type",nativeQuery=true)
	public List<Object[]> findByTimeAndNameAndType(@Param("type") String type,@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select a.name,a.type,sum(a.total) total from analysis_type a where a.day >=:startTime and a.day<=:endTime group by a.name,a.type",nativeQuery=true)
	public List<Object[]> findByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	
	@Query(value="select a.name,sum(a.total) total from analysis_type a where a.day >=:startTime and a.day<=:endTime group by a.name",nativeQuery=true)
	public List<Object[]> findByTimeForName(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select a.name,sum(a.total) total from analysis_type a where a.name = :name and a.day >=:startTime and a.day<=:endTime group by a.name",nativeQuery=true)
	public List<Object[]> findByTimeAndNameForName(@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
//	@Query("select distinct a.type from AnalysisType a")
//	public List<String> findTypeAll();
	
	@Query(value="select a.name name,sum(total) total from analysis_type a where a.name = :name and a.day >=:startTime and a.day<=:endTime group by a.name",nativeQuery=true)
	public List<Object[]> findByTimeAndTypeForTotal(@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);
//	
	
}
