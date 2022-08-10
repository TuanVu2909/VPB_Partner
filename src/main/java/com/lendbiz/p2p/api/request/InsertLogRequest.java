package com.lendbiz.p2p.api.request;

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
public class InsertLogRequest {

   private String requestId;
   private String userId;
   private String functionName;
   private int functionId;
   private String fromIp;
   private int requestType;
   private String responseStatus;
   private String requestBody;
   private String responseBody;
   private String businessError;

}
