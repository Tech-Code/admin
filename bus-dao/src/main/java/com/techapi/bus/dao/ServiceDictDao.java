package com.techapi.bus.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.techapi.bus.entity.ServiceDict;

public interface ServiceDictDao extends PagingAndSortingRepository<ServiceDict, String>{

}
