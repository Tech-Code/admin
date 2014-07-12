package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.AnalysisGroup;
public interface AnalysisGroupDao extends PagingAndSortingRepository<AnalysisGroup, String>{

	@Query(value="select name,substr(minute,0,7)||'-01 00:00:00',sum(total) total from analysis_group a where  a.minute >=:startTime and a.minute<=:endTime group by name,substr(minute,0,7) order by name asc,substr(minute,0,7) desc",nativeQuery=true)
	public List<Object[]> findByPosition7(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select name,substr(minute,0,10)||' 00:00:00',sum(total) total from analysis_group a where  a.minute >=:startTime and a.minute<=:endTime group by name,substr(minute,0,10) order by name asc,substr(minute,0,10) desc",nativeQuery=true)
	public List<Object[]> findByPosition10(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select name,substr(minute,0,13)||':00:00',sum(total) total from analysis_group a where  a.minute >=:startTime and a.minute<=:endTime group by name,substr(minute,0,13) order by name asc,substr(minute,0,13) desc",nativeQuery=true)
	public List<Object[]> findByPosition13(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select name,substr(minute,0,16) ||':00',sum(total) total from analysis_group a where  a.minute >=:startTime and a.minute<=:endTime group by name,substr(minute,0,16) order by name asc,substr(minute,0,16) desc",nativeQuery=true)
	public List<Object[]> findByPosition16(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	
	@Query(value="select name,substr(minute,0,7)||'-01 00:00:00',sum(total) total from analysis_group a where  a.minute >=:startTime and a.minute<=:endTime and a.name=:name group by name,substr(minute,0,7) order by name asc,substr(minute,0,7) desc",nativeQuery=true)
	public List<Object[]> findByPositionAndName7(@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select name,substr(minute,0,10)||' 00:00:00',sum(total) total from analysis_group a where  a.minute >=:startTime and a.minute<=:endTime and a.name=:name group by name,substr(minute,0,10) order by name asc,substr(minute,0,10) desc",nativeQuery=true)
	public List<Object[]> findByPositionAndName10(@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select name,substr(minute,0,13)||':00:00',sum(total) total from analysis_group a where  a.minute >=:startTime and a.minute<=:endTime and a.name=:name group by name,substr(minute,0,13) order by name asc,substr(minute,0,13) desc",nativeQuery=true)
	public List<Object[]> findByPositionAndName13(@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Query(value="select name,substr(minute,0,16) ||':00',sum(total) total from analysis_group a where  a.minute >=:startTime and a.minute<=:endTime and a.name=:name group by name,substr(minute,0,16) order by name asc,substr(minute,0,16) desc",nativeQuery=true)
	public List<Object[]> findByPositionAndName16(@Param("name") String name,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
}
