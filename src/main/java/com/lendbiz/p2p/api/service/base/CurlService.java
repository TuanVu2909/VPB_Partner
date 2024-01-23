package com.lendbiz.p2p.api.service.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import com.lendbiz.p2p.api.response.VPBank.CurlResponse;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CurlService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public CurlResponse executeCurlCommand (
            String url, String method,
            Map<String, Object> requestHeaders,
            Map<String, Object> requestBodies,
            String dataType ) {

        String filePathCer = "\"/home/api_mobile/src/main/resources/certificate/vpbuat.pem\"";

        try {
            // Add headers
            StringBuilder headers = new StringBuilder();
            if (requestHeaders != null && !requestHeaders.isEmpty()) {
                requestHeaders.forEach((key, value) -> {
                    headers.append(" -H \"" + key + ": " + value + "\"");
                });
            }

            // Add request body
            StringBuilder body = new StringBuilder();
            if (requestBodies != null && !requestBodies.isEmpty()) {
                if("data-urlencode".equals(dataType)){
                    requestBodies.forEach((key, value) -> {
                        body.append(" --data-urlencode \"" + key + "=" + value + "\"");
                    });
                }
                else if ("data-raw".equals(dataType)){
                    body.append(" --data-raw '" + new JSONObject(requestBodies) + "'");
                }
            }

            // Build the curl command
            String curlCommand = "curl --cacert " + filePathCer +" -X " + method + " " + url + headers + body ;

            logger.info("Curl Input: \n" + curlCommand);

            // Execute the curl command using ProcessBuilder
            Process process = new ProcessBuilder("bash", "-c", curlCommand)
                    //.redirectErrorStream(true)
                    .start();

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
            int exitCode = process.waitFor(); // success = 0

            // Print the output of the command
            logger.info("Curl Output:\n" + responseAPI.toString());

            // Extract JSON data from the output
            CurlResponse curlResponse = extractDataFromResponseAPI(responseAPI.toString());

            logger.info("=======================\nExtracted Output: =======================\n" + curlResponse.getData() + "\n=======================");

            return curlResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private CurlResponse extractDataFromResponseAPI(String responseAPI) {
        int start = responseAPI.indexOf("{");
        int end = responseAPI.lastIndexOf("}");
        if (start >= 0 && end >= 0) {
            return new CurlResponse("json", responseAPI.substring(start, end + 1));
        }
        else {
            int startTag = responseAPI.indexOf("<");
            int endTag = responseAPI.lastIndexOf(">");
            if (startTag >= 0 && endTag >= 0) {
                return new CurlResponse("xml", responseAPI.substring(startTag, endTag + 1));
            }
            return new CurlResponse("unknow", "");
        }
    }
}
