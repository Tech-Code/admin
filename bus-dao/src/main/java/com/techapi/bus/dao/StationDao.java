package com.techapi.bus.dao;

import com.techapi.bus.entity.Station;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StationDao extends PagingAndSortingRepository<Station, String>{
}
