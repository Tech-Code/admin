package com.techapi.bus.dao;

import com.techapi.bus.entity.TArea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TAreaDao extends PagingAndSortingRepository<TArea, String>{

    @Query("select c from TArea c "
            + "where c.AREA_TYPE = :area_type and AREA_CODE !=null and DELETE_FLAG ='0'")
    public List<TArea> findByType(@Param("area_type") String area_type);

    @Query("select c from TArea c "
            + "where c.AREA_NAME = :area_name and c.AREA_TYPE = 1")
    public TArea findByAreaName(@Param("area_name") String area_name);
}
