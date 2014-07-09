package com.techapi.bus.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.techapi.bus.entity.IgnoreList;

/**
 * Created by CH on 4/16/14.
 */
public interface IgnoreListDao extends PagingAndSortingRepository<IgnoreList, String> {

}
