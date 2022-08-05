package com.lendbiz.p2p.api.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.exception，@class-name：BusinessException.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:56:57 AM   
 *
 ***********************************************************************/
@Setter
@Getter
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected String        status;

    protected  Object      data;

    public BusinessException(String status) {
        super();
        this.status = status;
    }
    
    public BusinessException(String status, String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(String status, String data, String message) {
        super(data + ": " + message);
        this.status = status;
    }
}
