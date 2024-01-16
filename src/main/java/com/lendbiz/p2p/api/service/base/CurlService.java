package com.lendbiz.p2p.api.service.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.lendbiz.p2p.api.response.VPBank.VPBResAPI;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CurlService {
    public VPBResAPI executeCurlCommand(String url, String method, Map<String, String> requestHeaders, String requestBody) {
        VPBResAPI response = new VPBResAPI(-1, "Unknown");
        try {
            // Build the curl command
            StringBuilder curlCommand = new StringBuilder("curl -k --location --request " + method + " " + "'" + url + "'");

            // Add headers
            if (requestHeaders != null && !requestHeaders.isEmpty()) {
                requestHeaders.forEach((key, value) -> {
                    curlCommand.append(" --header '" + key +": " + value + "'");
                });
            }

            // Add request body
            if (requestBody != null && !requestBody.isEmpty()) {
                curlCommand.append(" --data-raw '" + requestBody + "'");
            }

            System.out.println("Curl Input: "+curlCommand.toString());

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
            Process process = new ProcessBuilder().command("bash", "-c", curlCommand.toString()).start();

            // Read the output of the command
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            System.out.println("Curl Output: "+ output.toString());

            int exitCode = process.waitFor();

            response.setCode(exitCode);
            response.setData(output.toString());

            return response;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
