// package com.lendbiz.p2p.api.configs;

// import java.io.File;
// import java.nio.charset.StandardCharsets;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.apache.commons.io.FileUtils;
// import org.springframework.boot.ApplicationArguments;
// import org.springframework.boot.ApplicationRunner;
// import org.springframework.stereotype.Component;

// import com.lendbiz.p2p.api.constants.ErrorCode;
// import com.lendbiz.p2p.api.exception.BusinessException;
// import com.lendbiz.p2p.api.model.quoctich.Converter;
// import com.lendbiz.p2p.api.model.quoctich.QuocTichList;

// @Component
// public class DataLoader implements ApplicationRunner {
//     public static String json;
//     public static Map<String, String> mapQuocTich = new HashMap<>();
//     public static List<QuocTichList> lstQT;

//     @Override
//     public void run(ApplicationArguments args) {

//         loadStaticData();

//     }

//     private void loadStaticData() {

//         try {
//             json = FileUtils.readFileToString(new File("quoctich.json"), StandardCharsets.UTF_8);
//             lstQT = Converter.fromJsonString(json);

//             for (QuocTichList quocTich : lstQT) {
//                 mapQuocTich.put(quocTich.getQuocTichCode(), quocTich.getQuocTichName());
//             }

//             // for (int i = 0; i < quocTichLists.length; i++) {
                
//             // }

//         } catch (Exception e) {
//             throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
//         }
//     }

// }
