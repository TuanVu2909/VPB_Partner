package com.lendbiz.p2p.api.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.response，@class-name：ApiResponse.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:55:43 AM   
 *
 ***********************************************************************/
@Data
public class ApiResponse<T> implements Serializable{
  private static final long serialVersionUID = 1L;
  
  protected String status;
  protected String message;
  @JsonInclude(Include.NON_NULL)
  protected ErrorInfo errorInfo;
  
  @JsonInclude(Include.NON_NULL)
  protected T data;
  
}
