package com.techapi.bus.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.techapi.bus.entity.AnalysisManage;
public interface AnalysisManageDao extends PagingAndSortingRepository<AnalysisManage, String>{

	 /***
	  * 根据开始时间结束时间业务账号查询
	  */
	 @Query(value = "SELECT a FROM AnalysisManage a where a.evertime>=?1 and a.evertime<=?2")
	 public Page<AnalysisManage> findByTimePage(String startTime,String endTime,Pageable pageable);
	 
	 
	 @Query(value = "SELECT a FROM AnalysisManage a where a.evertime>=?1 and a.evertime<=?2 and a.url like ?3")
	 public Page<AnalysisManage> findByTimeAndSearchKey(String startTime,String endTime,String searchKey,Pageable pageable);
	 
	 @Query(value = "SELECT a FROM AnalysisManage a where a.evertime>=?1 and a.evertime<=?2 and a.name=?3")
	 public Page<AnalysisManage> findByTimeAndName(String startTime,String endTime,String name,Pageable pageable);
	 
	 @Query(value = "SELECT a FROM AnalysisManage a where a.evertime>=?1 and a.evertime<=?2 and a.name=?3 and a.url like ?4")
	 public Page<AnalysisManage> findByTimeAndNameAndSearchKey(String startTime,String endTime,String name,String searchKey,Pageable pageable);
	 
}
