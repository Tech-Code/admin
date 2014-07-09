package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.ExceptionCount;

/**
 * Created by CH on 4/16/14.
 */
public interface ExceptionCountDao extends PagingAndSortingRepository<ExceptionCount, String> {
	
	@Query("select new ExceptionCount(e.server, e.timestamp, sum(e.count)) from ExceptionCount e "
			+ "where e.timestamp between :startDate and :endDate "
			+ "group by e.server, e.timestamp "
			+ "order by e.server, e.timestamp asc")
	List<ExceptionCount> findByTimestampBetweenSumCounts(
			@Param("startDate") Long startDate, 
			@Param("endDate") Long endDate);
	
	@Query("select new ExceptionCount(e.server, e.exception, sum(e.count), e.timestamp) from ExceptionCount e "
			+ "where e.server = :server and e.timestamp between :startDate and :endDate "
			+ "group by e.exception, e.timestamp "
			+ "order by e.timestamp asc")
	List<ExceptionCount> findByServerAndTimestampBetweenSumCounts(
			@Param("server") String server, 
			@Param("startDate") Long startDate, 
			@Param("endDate") Long endDate);
	
	@Query("select e from ExceptionCount e "
			+ "where e.server = :server and e.exception = :exception and e.timestamp between :startDate and :endDate "
			+ "order by e.timestamp asc")
	List<ExceptionCount> findByServerAndExceptionAndTimestampBetween(
			@Param("server") String server, 
			@Param("exception") String exception, 
			@Param("startDate") Long startDate, 
			@Param("endDate") Long endDate);
}
