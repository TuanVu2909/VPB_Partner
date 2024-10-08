/*
 * Apache License, Version 2.0
 *
 * Copyright (c) 2021 TU HOANG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lendbiz.p2p.api.service.impl;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.producer.ProducerMessage;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.request.InsertLogRequest;
import com.lendbiz.p2p.api.response.ErrorInfo;
import com.lendbiz.p2p.api.service.LoggingService;
import com.lendbiz.p2p.api.utils.StringUtil;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class LoggingServiceImpl implements LoggingService {

    @Autowired
    PackageFilterRepository pkgFilterRepo;

    @Autowired
    private ProducerMessage producerMessage;

    String requestId;
    String sessionId;

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        if (!StringUtil.isEmty(httpServletRequest.getHeader("RequestId"))) {
            requestId = httpServletRequest.getHeader("RequestId");
        } else
            requestId = "Unknown";

        if (!StringUtil.isEmty(httpServletRequest.getHeader("session"))) {
            sessionId = httpServletRequest.getHeader("session");
        } else
            sessionId = "Unknown";

        stringBuilder.append("[" + requestId + "]");
        stringBuilder.append("REQUEST ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("]");

        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            stringBuilder.append("body=[" + body + "]");
        }

        try {
            InsertLogRequest insertLogRequest = new InsertLogRequest(requestId, sessionId,
                    httpServletRequest.getRequestURI(),
                    0, httpServletRequest.getLocalAddr(), 0, null, body.toString(), null, null);

            JSONObject jsonObjectLogs = new JSONObject(insertLogRequest);

            producerMessage.sendLogs3Gang(jsonObjectLogs.toString());

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // pkgFilterRepo.insertLogs(insertLogRequest);

        log.info(stringBuilder.toString());
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object body) {

        StringBuilder stringBuilder = new StringBuilder();

        String errorInfo = "";

        try {
            JSONObject bodyJson = new JSONObject(body);

            if (!StringUtil.isEmty(httpServletRequest.getHeader("RequestId"))) {
                requestId = httpServletRequest.getHeader("RequestId");
            } else
                requestId = "Unknown";

            stringBuilder.append("[" + requestId + "]");
            stringBuilder.append("RESPONSE ");
            stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
            stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
            stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("]");
            stringBuilder.append("responseBody=[").append(bodyJson.toString()).append("] ");

            InsertLogRequest insertLogRequest;
            String errorCode = "";

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;

        
            root = mapper.readTree(bodyJson.toString());
            errorCode = root.get("status").asText();

            insertLogRequest = new InsertLogRequest(requestId,
                    sessionId, httpServletRequest.getRequestURI(),
                    0, httpServletRequest.getLocalAddr(), 1,
                    String.valueOf(httpServletResponse.getStatus()), null,
                    bodyJson.toString(), errorCode);
            JSONObject jsonObjectLogs = new JSONObject(insertLogRequest);
            producerMessage.sendLogs3Gang(jsonObjectLogs.toString());
        } catch (Exception e) {
            log.error(e.getMessage());

        }

        // pkgFilterRepo.insertLogs(insertLogRequest);

        log.info(stringBuilder.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }
}
