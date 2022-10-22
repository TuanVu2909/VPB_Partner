/***************************************************************************
 * Copyright 2018 by VIETIS - All rights reserved.                *    
 **************************************************************************/
package com.lendbiz.p2p.api.response;

import java.io.Serializable;

import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  Author : Chinh Duong </br>
 *  Email: chinh.duong@vietis.com.vn</br>
 *  Sep 20, 2018
 */
@Setter
@Getter
@Controller
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LbResponse<T> implements Serializable{
  private static final long serialVersionUID = 1L;
  
  protected String status;
  protected String message;
  
  protected T data;
  
}
