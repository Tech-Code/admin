package com.techapi.bus.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.techapi.bus.entity.Taxi;

public interface TaxiDao extends PagingAndSortingRepository<Taxi, String>{

}
