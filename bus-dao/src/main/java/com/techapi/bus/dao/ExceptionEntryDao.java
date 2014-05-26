package com.techapi.bus.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.EntryId;
import com.techapi.bus.entity.ExceptionEntry;

/**
 * Created by CH on 4/16/14.
 */
public interface ExceptionEntryDao extends PagingAndSortingRepository<ExceptionEntry, EntryId> {

    @Query("select e from ExceptionEntry e where e.id.server = :server and e.exception = :exception")
    Page<ExceptionEntry> findByServerAndException(@Param("server") String server, @Param("exception") String exception, Pageable pageable);
    
    @Query("select e from ExceptionEntry e where e.id.server = :server and e.exception = :exception and e.id.timestamp between :startDate and :endDate")
    Page<ExceptionEntry> findByServerAndException(
    		@Param("server") String server, 
    		@Param("exception") String exception, 
    		@Param("startDate") Long startDate, 
    		@Param("endDate") Long endDate, 
    		Pageable pageable);
    
    @Query("select e from ExceptionEntry e where e.id.server = :server and e.exception = :exception and e.location = :location and e.id.timestamp between :startDate and :endDate")
    Page<ExceptionEntry> findByServerAndExceptionAndLocation(
    		@Param("server") String server, 
    		@Param("exception") String exception, 
    		@Param("location") String location, 
    		@Param("startDate") Long startDate, 
    		@Param("endDate") Long endDate, 
    		Pageable pageable);
    
    @Query("select new org.apache.commons.lang3.tuple.ImmutablePair(e.location, count(e.location) as counts) from ExceptionEntry e "
    		+ "where e.id.server = :server and e.exception = :exception and e.id.timestamp between :startDate and :endDate "
    		+ "group by e.location "
    		+ "order by counts desc")
    List<ImmutablePair<String, Long>> countLocationByServerAndException(
    		@Param("server") String server, 
    		@Param("exception") String exception, 
    		@Param("startDate") Long startDate, 
    		@Param("endDate") Long endDate);

    @Query("select e from ExceptionEntry e where e.id.server = :server")
    List<ExceptionEntry> findByServer(@Param("server") String server, Pageable pageable);

    @Query("select count(e) from ExceptionEntry e where e.id.server = :server and e.exception = :exception and e.id.timestamp > :timestamp")
    Long getExceptionCountSince(@Param("server") String server, @Param("exception") String exception, @Param("timestamp") Date timestamp);
}
