package com.lendbiz.p2p.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.Card9PayDetails;
import com.lendbiz.p2p.api.entity.Card9PayEntity;
import com.lendbiz.p2p.api.entity.Card9PayEntity_v2;
import com.lendbiz.p2p.api.entity.TransactionBuyCard;

import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.*;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.response.BaseResponse;

import com.lendbiz.p2p.api.utils.Utils;
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
    NotifyRepo notifyRepo;
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
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String strSDate = formatter.format(sDateF);
        String strEDate = formatter.format(eDateF);

        ArrayList<Card9PayEntity_v2> list = (ArrayList<Card9PayEntity_v2>) dynamicRepository.findViaProcedure(cif, strSDate, strEDate);
        ArrayList<TransactionBuyCard> arrayList = new ArrayList<>();
        if (list.size() == 0){
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);

    }

        list.forEach((n) -> {
            Card9PayDetails[] card9PayDetailsList;
            try {
                byte[] dc = Base64.getDecoder().decode(n.getCard_code());
                String data = new String(dc, "UTF-8");
                card9PayDetailsList = mapper.readValue(data, Card9PayDetails[].class);
                for (int i = 0; i < card9PayDetailsList.length; i++) {
                    Card9PayDetails card9PayDetails = card9PayDetailsList[i];
                    String codeDe = Utils.decrypt(card9PayDetails.getCard_code());
                    card9PayDetailsList[i].setCard_code(codeDe);

                }
                TransactionBuyCard tran = new TransactionBuyCard();
                tran.setId(n.getId());
                tran.setCif(n.getCif());
                tran.setPayDate(n.getPayDate());
                tran.setCard9PayDetailsList(card9PayDetailsList);
                tran.setPrice(n.getPrice());
                tran.setService_name(n.getService_name());
                tran.setTransactionId(n.getTransId());
                tran.setProduct_des(n.getProduct_des());
                tran.setProduct_name(n.getProduct_name());
                arrayList.add(tran);
            } catch (JsonProcessingException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });
        return response(toResult(arrayList));
    }

    public void create(Card9PayEntity card9PayEntity) {
        System.out.println("-----");
        try {
            notifyRepo.insert_trans9pay(card9PayEntity.getCustid()
                    , card9PayEntity.getTrans_Id()
                    , card9PayEntity.getProduct_id()
                    , card9PayEntity.getPrice()
                    , card9PayEntity.getPay_status()
                    , card9PayEntity.getSeri_code()
                    , card9PayEntity.getCard_code(),
                    card9PayEntity.getAmount()
            );
        } catch (Exception e) {
            throw new BusinessException("01", e.getMessage());
        }
    }

    public ResponseEntity<?> getTranTest(String cif) {
        return response(toResult(card9PayRepository.findByCustId(cif)));
    }

    @Autowired
    RateRepo rateRepo;

    public ResponseEntity<?> getP() {
        try {
            System.out.println("2323");
            return response(toResult(rateRepo.getRatePro("12", "6.5", "10000000")));
        } catch (Exception e) {
            throw new BusinessException("11", e.getMessage());
        }

    }

    public ResponseEntity<?> getTransHistory(String cif) {
        List<Card9PayEntity_v2> lstCard9Pay;
        try {
            lstCard9Pay = dynamicRepository.findViaProcedure(cif, "10-JAN-2021", "10-FEB-2022");
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.ERROR_500, ErrorCode.ERROR_500_DESCRIPTION);
        }

        if (lstCard9Pay.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        return response(toResult(lstCard9Pay));
    }
}
