package com.lendbiz.p2p.api.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.Input9Pay;
import com.lendbiz.p2p.api.entity.NinePayResult;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.repository.ProductGMRepository;
import com.lendbiz.p2p.api.request.Create9PayRequest;
import com.lendbiz.p2p.api.service.NinePayService;

import com.lendbiz.p2p.api.service.UserService;
import com.lendbiz.p2p.api.service.impl.Card9PayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/lendbiz")
@Log4j2
public class NinePayController {

    @Autowired
    NinePayService ninepayService;
    @Autowired
    Card9PayServiceImpl card9PayService;
    @Autowired
    UserService userService;
    @Autowired
    PackageFilterRepository filterRepository;

    @PostMapping("/9pay/create")
    public ResponseEntity<?> createNinePayUrl(HttpServletRequest httpServletRequest,
                                              @RequestHeader("requestId") String requestId,
                                              @RequestBody Create9PayRequest request) throws UnsupportedEncodingException {
        log.info("[" + requestId + "] << createNinePayUrl >>");

        return ninepayService.create9Payment(request);
    }

    @PostMapping("/9pay/decode")
    public ResponseEntity<?> decodeNinePayResult(HttpServletRequest httpServletRequest,
                                                 @RequestHeader("requestId") String requestId, @RequestBody String encodeString)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << decodeNinePayResult >>");
        return ninepayService.decode9Payment(encodeString);

    }

    @GetMapping("/9pay/test")
    public ResponseEntity<?> test(HttpServletRequest httpServletRequest,
                                  @RequestHeader("requestId") String requestId, @RequestParam("sId") String sId,
                                  @RequestParam("qua") String qua)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << test >>");
        Input9Pay input9Pay = new Input9Pay();
        input9Pay.setQuantity(qua);
        input9Pay.setProductId(sId);
        return ninepayService.findTransaction(sId);

    }
//
//    @GetMapping("/9pay/test2")
//    public Object test2()
//            throws BusinessException, UnsupportedEncodingException {
//
////        card9PayService.create();
//        return filterRepository.changeCoin();
//
//    }

    @GetMapping("/9pay/trans")
    public ResponseEntity<?> trans(HttpServletRequest httpServletRequest,
                                   @RequestHeader("requestId") String requestId, @RequestParam("cid") String cId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-trans-info-buy-card >>");

        return ninepayService.findTransaction(cId);

    }

    @PostMapping("/9pay/update-ref")
    public ResponseEntity<?> updateReferenceId(HttpServletRequest httpServletRequest,
                                               @RequestHeader("requestId") String requestId, @RequestBody AccountInput accountInput)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-updateReferenceId >>");
        return userService.updateReferenceId(accountInput);

    }

    @GetMapping("/9pay/get-coin")
    public ResponseEntity<?> getCoin(HttpServletRequest httpServletRequest,
                                     @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-get-coin >>");

        return userService.getCoin(cId);

    }

    @PostMapping("/9pay/change-coin")
    public ResponseEntity<?> changeCoin(HttpServletRequest httpServletRequest,
                                        @RequestHeader("requestId") String requestId, @RequestBody AccountInput input)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-change-coin>>");

        return userService.changeCoin(input);

    }

    @GetMapping("/9pay/get-trans-by-custid")
    public ResponseEntity<?> transBycustId(HttpServletRequest httpServletRequest,
                                           @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-trans-info-by-cif >>");
        return card9PayService.getAllByCustId(cId);

    }

    @PostMapping("/9pay/pay-card")
    public ResponseEntity<?> payCard(HttpServletRequest httpServletRequest, @RequestHeader("session") String session,
                                     @RequestHeader("requestId") String requestId, @RequestBody Input9Pay input9Pay)
            throws BusinessException, UnsupportedEncodingException {

        log.info("[" + requestId + "] << payCard >>");
//        String custId = userService.checkSession(session);
        input9Pay.setCif("000028");
        return ninepayService.buyCard(input9Pay);

    }

    @GetMapping("/9pay/trans-by-date")
    @Transactional(readOnly = true)
    public ResponseEntity<?> findTrans(HttpServletRequest httpServletRequest, @RequestHeader("session") String session,
                                       @RequestHeader("requestId") String requestId, @RequestParam("edate") String eDate, @RequestParam("sdate") String sDate)
            throws BusinessException, UnsupportedEncodingException {

        log.info("[" + requestId + "] << find-trans >>");
//        String custId = userService.checkSession(session);
        return card9PayService.findByDate(sDate, eDate, "000028");

    }

    @GetMapping("/9pay/products")
    public ResponseEntity<?> products(HttpServletRequest httpServletRequest,
                                      @RequestHeader("requestId") String requestId, @RequestParam("serviceId") String sId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << get-9pay-products >>");

        return ninepayService.getCardProducts(sId);

    }

    @GetMapping("/9pay/balance")
    public ResponseEntity<?> balance(HttpServletRequest httpServletRequest,
                                     @RequestHeader("requestId") String requestId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << get-9pay-balance >>");

        return ninepayService.balance();

    }

    @RequestMapping("/9pay/success")
    public ModelAndView thanhtoan(@RequestParam("result") String result,
                                  @RequestParam("checksum") String checksum) throws JsonMappingException, JsonProcessingException {
        // System.out.println(result + "_______" + checksum);

        String byteArray = result;

        byte[] decodedBytes = Base64.getDecoder().decode(byteArray);
        String decodedString = new String(decodedBytes);

        ObjectMapper mapper = new ObjectMapper();
        // Map<String, Object> map = mapper.readValue(decodedString, Map.class);

        // JsonNode rootNode = mapper.readTree(decodedString);

        NinePayResult res = mapper.readValue(decodedString, NinePayResult.class);

        System.out.println(res.toString());

        ModelAndView modelAndView = new ModelAndView("success-signature");
        return modelAndView;
    }

    @GetMapping("/9pay/get-cc")
    public ResponseEntity<?> getTransTest(HttpServletRequest httpServletRequest,
                                          @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-trans-info-by-cif >>");
        return card9PayService.getTranTest(cId);

    }

    @Autowired
    ProductGMRepository repository;

    @GetMapping("/9pay/get-tt")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getTransTest2(HttpServletRequest httpServletRequest,
                                           @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-trans-info-by-cif >>");
        return card9PayService.getTransHistory(cId);

    }

    @GetMapping("/9pay/get-tt2")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getTransTest2(HttpServletRequest httpServletRequest,
                                       @RequestParam("cif") String cId)
            throws BusinessException, UnsupportedEncodingException {
        return card9PayService.getP();

    }

}
