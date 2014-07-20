package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.Speed;

public interface SpeedDao extends PagingAndSortingRepository<Speed, String> {

	
	/***
	 * 根据交通工具明细和城市查询换乘
	 * @param transportation
	 * @return
	 */
	@Query("select c from Speed c "
			+ "where c.tranSportation = :transportation")
	public List<Speed> findByTransportation(
			@Param("transportation") String transportation);
}
