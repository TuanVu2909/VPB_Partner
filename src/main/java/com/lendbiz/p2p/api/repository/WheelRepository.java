package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.WheelEntity;

public interface WheelRepository extends JpaRepository<WheelEntity, String> {

        @Procedure("WheelEntity.updateWheelConfig")
        WheelEntity updateWheelConfig(@Param("pv_id") int id,
                        @Param("pv_rate") int rate,
                        @Param("pv_giftid") int giftId,
                        @Param("pv_groupid") int groupId,
                        @Param("pv_status") int status);

        @Procedure("WheelEntity.updateWheelGroupTime")
        WheelEntity updateWheelGroupTime(@Param("pv_groupid") int groupId,
                        @Param("pv_fromtime") String fromTime,
                        @Param("pv_totime") String toTime,
                        @Param("pv_fromdate") String fromDate,
                        @Param("pv_todate") String toDate,
                        @Param("pv_status") int status,
                        @Param("pv_maxprize") int maxPrize);

        @Procedure("WheelEntity.updateWheelPrize")
        WheelEntity updateWheelPrize(@Param("pv_id") int id,
                        @Param("pv_name") String name,
                        @Param("pv_status") int status,
                        @Param("pv_ramount") int rAmount);

        @Procedure("WheelEntity.insertWheelHistory")
        WheelEntity insertWheelHistory(@Param("pv_custid") String custId,
                        @Param("pv_status") int status,
                        @Param("pv_giftid") int giftId);

}
