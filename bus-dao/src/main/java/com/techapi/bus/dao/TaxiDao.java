package com.techapi.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.techapi.bus.entity.Taxi;
@Component("taxiDao")
public interface TaxiDao extends PagingAndSortingRepository<Taxi, String>{

	@Query("select c from Taxi c "
			+ "where c.cityName = :cityname")
	public List<Taxi> findBycityName(@Param("cityname") String cityname);
}
