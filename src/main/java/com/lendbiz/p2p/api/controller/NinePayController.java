//package com.lendbiz.p2p.api.controller;
//
//import java.io.UnsupportedEncodingException;
//import java.time.LocalDate;
//import java.util.Base64;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.lendbiz.p2p.api.entity.AccountInput;
//import com.lendbiz.p2p.api.entity.Input9Pay;
//import com.lendbiz.p2p.api.entity.MarkUsedRequest;
//import com.lendbiz.p2p.api.entity.NinePayResult;
//import com.lendbiz.p2p.api.exception.BusinessException;
//import com.lendbiz.p2p.api.repository.PackageFilterRepository;
//import com.lendbiz.p2p.api.repository.ProductGMRepository;
//import com.lendbiz.p2p.api.request.Create9PayRequest;
//import com.lendbiz.p2p.api.request.IpnRequest;
//import com.lendbiz.p2p.api.service.LoggingService;
//import com.lendbiz.p2p.api.service.NinePayService;
//
//import com.lendbiz.p2p.api.service.UserService;
//import com.lendbiz.p2p.api.service.impl.Card9PayServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import lombok.extern.log4j.Log4j2;
//
//@RestController
//@RequestMapping("/lendbiz")
//@Log4j2
//public class NinePayController {
//
//    @Autowired
//    NinePayService ninepayService;
//    @Autowired
//    Card9PayServiceImpl card9PayService;
//    @Autowired
//    UserService userService;
//    @Autowired
//    PackageFilterRepository filterRepository;
//
//    @PostMapping("/9pay/create")
//    public ResponseEntity<?> createNinePayUrl(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId,
//            @RequestBody Create9PayRequest request) throws UnsupportedEncodingException {
//        log.info("[" + requestId + "] << createNinePayUrl >>");
//
//        return ninepayService.create9Payment(request);
//    }
//
//    @PostMapping("/9pay/decode")
//    public ResponseEntity<?> decodeNinePayResult(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId, @RequestBody String encodeString)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + requestId + "] << decodeNinePayResult >>");
//        return ninepayService.decode9Payment(encodeString);
//
//    }
//
//    @GetMapping("/9pay/test")
//    public ResponseEntity<?> test(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId, @RequestParam("sId") String sId,
//            @RequestParam("qua") String qua)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + requestId + "] << test >>");
//        Input9Pay input9Pay = new Input9Pay();
//        input9Pay.setQuantity(qua);
//        input9Pay.setProductId(sId);
//        return ninepayService.findTransaction(sId);
//
//    }
//    //
//    // @GetMapping("/9pay/test2")
//    // public Object test2()
//    // throws BusinessException, UnsupportedEncodingException {
//    //
//    //// card9PayService.create();
//    // return filterRepository.changeCoin();
//    //
//    // }
//
//    @GetMapping("/9pay/trans")
//    public ResponseEntity<?> trans(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId, @RequestParam("cid") String cId)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + requestId + "] << check-trans-info-buy-card >>");
//
//        return ninepayService.findTransaction(cId);
//
//    }
//
//    @GetMapping("/9pay/get-trans-by-custid")
//    @Transactional(readOnly = true)
//    public ResponseEntity<?> transBycustId(HttpServletRequest httpServletRequest,
//            @RequestHeader("session") String session,
//            @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + session + "] << check-trans-info-by-cif >>");
//        log.info("[" + requestId + "] << check-trans-info-by-cif >>");
//        return card9PayService.findByDate("01-01-2000", "01-01-3000", session);
//
//    }
//
//    @PostMapping("/9pay/mark-used")
//    @Transactional(readOnly = true)
//    public ResponseEntity<?> markUsed(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId, @RequestBody MarkUsedRequest request)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + requestId + "] << mark >>");
//        return card9PayService.markUsed(request.getId(), request.getStatus());
//    }
//
//    @PostMapping("/9pay/pay-card")
//    @Transactional(readOnly = true)
//    public ResponseEntity<?> payCard(HttpServletRequest httpServletRequest, @RequestHeader("session") String session,
//            @RequestHeader("requestId") String requestId, @RequestBody Input9Pay input9Pay)
//            throws BusinessException, UnsupportedEncodingException {
//
//        log.info("[" + requestId + "] << payCard >>");
//        // String custId = userService.checkSession(session);
//        return ninepayService.buyCard(input9Pay);
//
//    }
//
//    @GetMapping("/9pay/trans-by-date")
//    @Transactional(readOnly = true)
//    public ResponseEntity<?> findTrans(HttpServletRequest httpServletRequest, @RequestHeader("session") String session,
//            @RequestHeader("requestId") String requestId, @RequestParam("edate") String eDate,
//            @RequestParam("sdate") String sDate, @RequestParam("cif") String cif)
//            throws BusinessException, UnsupportedEncodingException {
//
//        log.info("[" + requestId + "] << find-trans >>");
//        // String custId = userService.checkSession(session);
//        return card9PayService.findByDate(sDate, eDate, cif);
//
//    }
//
//    @GetMapping("/9pay/products")
//    public ResponseEntity<?> products(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId, @RequestParam("serviceId") String sId)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + requestId + "] << get-9pay-products >>");
//
//        return ninepayService.getCardProducts(sId);
//
//    }
//
//    @GetMapping("/9pay/get-prod-from-lb")
//    @Transactional(readOnly = true)
//    public ResponseEntity<?> getProducts(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId, @RequestParam("sid") String sId)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + requestId + "] << get-9pay-products-f-lendbiz >>");
//
//        return card9PayService.getProductCard9pay(sId);
//
//    }
//
//    @GetMapping("/9pay/balance")
//    public ResponseEntity<?> balance(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + requestId + "] << get-9pay-balance >>");
//
//        return ninepayService.balance();
//
//    }
//
//    @RequestMapping("/9pay/success")
//    public ModelAndView thanhtoan(@RequestParam("result") String result,
//            @RequestParam("checksum") String checksum) throws JsonMappingException, JsonProcessingException {
//        // System.out.println(result + "_______" + checksum);
//
//        String byteArray = result;
//
//        byte[] decodedBytes = Base64.getDecoder().decode(byteArray);
//        String decodedString = new String(decodedBytes);
//
//        ObjectMapper mapper = new ObjectMapper();
//        // Map<String, Object> map = mapper.readValue(decodedString, Map.class);
//
//        // JsonNode rootNode = mapper.readTree(decodedString);
//
//        NinePayResult res = mapper.readValue(decodedString, NinePayResult.class);
//
//        System.out.println(res.toString());
//
//        ModelAndView modelAndView = new ModelAndView("success-signature");
//        return modelAndView;
//    }
//
//    @GetMapping("/9pay/get-cc")
//    public ResponseEntity<?> getTransTest(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + requestId + "] << check-trans-info-by-cif >>");
//        return card9PayService.getTranTest(cId);
//
//    }
//
//    @Autowired
//    ProductGMRepository repository;
//
//    @GetMapping("/9pay/get-tt")
//    @Transactional(readOnly = true)
//    public ResponseEntity<?> getTransTest2(HttpServletRequest httpServletRequest,
//            @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
//            throws BusinessException, UnsupportedEncodingException {
//        log.info("[" + requestId + "] << check-trans-info-by-cif >>");
//        return card9PayService.getTransHistory(cId);
//
//    }
//
//    @GetMapping("/9pay/get-tt2")
//    @Transactional(readOnly = true)
//    public Object getTransTest2(HttpServletRequest httpServletRequest)
//            throws BusinessException, UnsupportedEncodingException {
//        return card9PayService.getP();
//
//    }
//
//    @Autowired
//    private LoggingService loggingGetRequest;
//
//    @PostMapping(path = "/9pay/ipn", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
//    public ResponseEntity<?> ipn(HttpServletRequest httpServletRequest, IpnRequest request) {
//
//        log.info(request.getResult());
//        log.info(request.getChecksum());
//
//        loggingGetRequest.logRequest(httpServletRequest, "result: " + request.getResult());
//
//        return ninepayService.ipn(request);
//    }
//
//}
