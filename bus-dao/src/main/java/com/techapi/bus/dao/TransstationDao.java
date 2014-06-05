package com.techapi.bus.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.techapi.bus.entity.Transstation;

public interface TransstationDao extends PagingAndSortingRepository<Transstation, String>{

}
