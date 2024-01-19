package com.lendbiz.p2p.api.service.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.lendbiz.p2p.api.response.VPBank.VPBResAPI;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class CurlService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    public VPBResAPI executeCurlCommand (
            String url, String method,
            Map<String, Object> requestHeaders,
            Map<String, Object> requestBodies,
            String dataType ) {

        VPBResAPI response = new VPBResAPI(-1, "Unknown");
        try {
            // Build the curl command
//            StringBuilder curlCommand = new StringBuilder("curl --cacert "+ filePathCer +" \\--location --request " + method + " " + "'" + url + "' \\");
//            StringBuilder curlCommand = new StringBuilder(" \\--location --request " + method + " " + "'" + url + "' \\");
//
//            // Add headers
//            if (requestHeaders != null && !requestHeaders.isEmpty()) {
//                AtomicInteger size = new AtomicInteger(requestHeaders.size());
//                requestHeaders.forEach((key, value) -> {
//                    if (size.decrementAndGet() == 0 && requestBodies == null && requestBodies.isEmpty()){
//                        curlCommand.append("--header '" + key +": " + value + "'");
//                    }
//                    else {
//                        curlCommand.append("--header '" + key +": " + value + "' \\");
//                    }
//                });
//            }
//
//            // Add request body
//            if (requestBodies != null && !requestBodies.isEmpty()) {
//                if("data-urlencode".equals(dataType)){
//                    AtomicInteger size = new AtomicInteger(requestBodies.size());
//                    requestBodies.forEach((key, value) -> {
//                        if (size.decrementAndGet() == 0){
//                            curlCommand.append("--data-urlencode '" + key +"=" + value + "'");
//                        }
//                        else {
//                            curlCommand.append("--data-urlencode '" + key +"=" + value + "' \\");
//                        }
//                    });
//                }
//                else if ("data-raw".equals(dataType)){
//                    curlCommand.append("--data-raw '" + new JSONObject(requestBodies) + "'");
//                }
//            }
//
//            System.out.println("Curl Input: "+curlCommand.toString());

//            // Run the curl command using cmd.exe on Windows
//            Process process = new ProcessBuilder().command("cmd.exe", "/c", curlCommand.toString()).start();
//
//            // Read the output of the command
//            StringBuilder output = new StringBuilder();
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    output.append(line).append("\n");
//                }
//            }

            // Run the curl command using bash on Linux
            //Process process = new ProcessBuilder().command("bash", "-c", curlCommand.toString()).start();
//            String filePathCer = "/home/api_mobile/src/main/resources/certificate/vpbuat.pem";
//            String url1 = "https://uat-ob-gatewaylb-int.vpbank.com.vn/token";
//            String data1 = "scope=make_internal_transfer init_payments_data_read make_external_fund_transfer own_trasfer_history_read&grant_type=client_credentials";
//            String resolve = "uat-ob-gatewaylb-int.vpbank.com.vn:443:10.37.25.102";
//
//            ProcessBuilder processBuilder = new ProcessBuilder(
//                    "curl",
//                    "--cacert", filePathCer,
//                    "-X", "POST",
//                    "-H", "Content-Type: application/x-www-form-urlencoded",
//                    "-H", "Authorization: Basic " + "eEhabWFJV0tmWkNrU2ZWV0huaGNRV0VINm84YTpNcWVtaHQyaDM0V3RhOVdiSEFYUmF2OGxmWTBh",
//                    "-d", data1,
//                    "--resolve", resolve,
//                    url1
//            );

            StringBuilder curlCommand = new StringBuilder("curl");
            String urlTest = "https://postman-rest-api-learner.glitch.me/info";
            String requestMethod = "GET"; // or "POST", "PUT", etc.
            //String header = "Content-Type: application/json";
            //String data = "{\"key\": \"value\"}";

            // Append components to the curl command
            curlCommand.append(" -X ").append(requestMethod);
            //curlCommand.append(" -v"); // verbose output
            //curlCommand.append(" -H '").append(header).append("'");
            //curlCommand.append(" -d '").append(data).append("'");
            curlCommand.append(" ").append(urlTest);

            logger.info("Curl Input: \n" + curlCommand.toString());

            // Build the command as a list of strings
            ProcessBuilder processBuilder = new ProcessBuilder(
                    curlCommand.toString().split("\\s+")
            );

            // Redirect error stream to output stream
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Get input stream to read the response
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read and print the response
            String line;
            StringBuilder responseAPI = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                responseAPI.append(line).append("\n");
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();

            // Print the output of the command
            logger.info("Curl Output:\n" + responseAPI.toString());

            // Extract JSON data from the output
            String jsonStringResult = extractJsonFromCurlOutput(responseAPI.toString());
            System.out.println("=======================\nExtracted Output: " + jsonStringResult + "\n=======================");


            response.setCode(exitCode);
            response.setData(jsonStringResult);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String extractJsonFromCurlOutput(String output) {
        // Assuming the JSON is enclosed in curly braces {}
        int start = output.indexOf("{");
        int end = output.lastIndexOf("}");
        if (start >= 0 && end >= 0) {
            return output.substring(start, end + 1);
        } else {
            return "";
        }
    }
}
