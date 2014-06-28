package com.techapi.bus.dao;

import com.techapi.bus.entity.Transstation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransstationDao extends PagingAndSortingRepository<Transstation, String>{

    @Query("select t from Transstation t "
            + "where t.cityStation.id in :ids")
    public List<Transstation> findTransstationByCityStationId(@Param("ids") String[] cityStationIds);

    @Query("select c from Transstation c "
            + "where c.trips = :trips and c.cityStation.stationName = :stationName")
    public List<Transstation> findByTripsAndStationName(
            @Param("trips") String trips,
            @Param("stationName") String stationName);

}
