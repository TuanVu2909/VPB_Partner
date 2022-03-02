package com.lendbiz.p2p.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.Card9PayEntity;
import com.lendbiz.p2p.api.entity.Card9PayEntity_v2;
import com.lendbiz.p2p.api.entity.InvestAssets;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.*;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.response.BaseResponse;

import com.lendbiz.p2p.api.response.InvestAssetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Card9PayServiceImpl extends BaseResponse<NinePayServiceImpl> {
    @Autowired
    Card9PayRepository card9PayRepository;
    @Autowired
    PackageFilterRepository filterRepository;
    @Autowired
    ProductGMRepository productGMRepository;
    @Autowired
    PackageFilterRepository filter;

    @Autowired
    DynamicRepository dynamicRepository;

    @Autowired
    RoleRepository roleRepository;

    public List<Card9PayEntity> getAll() {
        return card9PayRepository.findAll();
    }

    public ResponseEntity<?> getAllByCustId(String cif) {
        return response(toResult(card9PayRepository.findByCustId(cif)));
    }

    public ResponseEntity<?> findByDate(String sDate, String eDate, String cif) {
        Date sDateF = null;
        Date eDateF = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sDateF = sdf.parse(sDate);
            eDateF = sdf.parse(eDate);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String strSDate = formatter.format(sDateF);
        String strEDate = formatter.format(eDateF);
        return response(toResult(dynamicRepository.findViaProcedure(cif,strSDate,strEDate)));
    }

    public void create(Card9PayEntity card9PayEntity) {
        try {
            card9PayRepository.save(card9PayEntity);
        } catch (Exception e) {
            throw new BusinessException("01", e.getMessage());
        }
    }

    public ResponseEntity<?> getTranTest(String cif) {
        return response(toResult(card9PayRepository.findByCustId(cif)));
    }
@Autowired
TermRepo termRepo;
    public ResponseEntity<?> getP( ) {
        try {
            System.out.println("2323");
            return response(toResult(termRepo.getTerm("14")));
        }catch (Exception e){
            throw new BusinessException("11",e.getMessage());
        }

    }

    public ResponseEntity<?> getTransHistory(String cif) {
        System.out.println("cii");
        List<Card9PayEntity_v2> lstCard9Pay;
        try {
            lstCard9Pay = dynamicRepository.findViaProcedure(cif,"10-JAN-2021","10-FEB-2022");
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.ERROR_500, ErrorCode.ERROR_500_DESCRIPTION);
        }

        if (lstCard9Pay.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }

        return response(toResult(lstCard9Pay));
    }
}
