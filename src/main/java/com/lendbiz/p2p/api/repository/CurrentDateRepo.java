package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lendbiz.p2p.api.entity.HolidayDateEntity;

public interface CurrentDateRepo extends JpaRepository<HolidayDateEntity, String> {

        @Query(value = "select TO_CHAR(dateval, 'dd-MM-yyyy') as dateval, grpid from gmholiday where grpid = (select max(grpid) from gmholiday)", nativeQuery = true)
        List<HolidayDateEntity> getHolidayDate();

}
