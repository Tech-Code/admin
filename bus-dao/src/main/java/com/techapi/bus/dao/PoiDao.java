package com.techapi.bus.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.techapi.bus.entity.Poi;

public interface PoiDao extends PagingAndSortingRepository<Poi, String>{

}
