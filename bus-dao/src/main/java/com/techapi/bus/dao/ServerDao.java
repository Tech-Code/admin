package com.techapi.bus.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.techapi.bus.entity.Server;

/**
 * Created by CH on 4/16/14.
 */
public interface ServerDao extends PagingAndSortingRepository<Server, String> {

}
