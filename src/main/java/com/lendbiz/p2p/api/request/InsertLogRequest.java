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
   private String messageType;
   private int status;
   private String bodyDetail;
   private String httpMethod;
   private String sourceAppId;
   private String sourceAppIp;
   private String destAppId;
   private String destAppPort;
   private String authorization;
   private String path;

}
