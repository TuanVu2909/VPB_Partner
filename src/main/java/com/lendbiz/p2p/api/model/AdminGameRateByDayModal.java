package com.lendbiz.p2p.api.model;

import java.util.List;

import com.lendbiz.p2p.api.entity.AdminGameRateByDayEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminGameRateByDayModal {

        private String date;
        private List<AdminGameRateByDayEntity> obj;

}
