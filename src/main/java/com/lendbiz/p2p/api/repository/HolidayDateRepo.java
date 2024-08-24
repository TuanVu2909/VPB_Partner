package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.HolidayDateEntity;
import com.lendbiz.p2p.api.entity.bank.VpbHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HolidayDateRepo extends JpaRepository<VpbHoliday, String> {
    @Query(value = "select TO_CHAR(dateval, 'dd-MM-yyyy') as dateval, grpid from vpb_holiday", nativeQuery = true)
    List<VpbHoliday> getVpbHolidayDate();
}
