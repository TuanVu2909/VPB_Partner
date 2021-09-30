package com.lendbiz.p2p.api.exception;

import com.lendbiz.p2p.api.constants.ErrorCode;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.exception，@class-name：InputInvalidExeption.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:56:50 AM   
 *
 ***********************************************************************/
public class InputInvalidExeption extends BusinessException {

    private static final long serialVersionUID = 7641702942202000228L;

    public InputInvalidExeption() {
        super(ErrorCode.INVALID_DATA_REQUEST, ErrorCode.INVALID_DATA_REQUEST_DESCRIPTION);
    }

    public InputInvalidExeption(String message) {
        super(ErrorCode.INVALID_DATA_REQUEST, message);
    }

    public InputInvalidExeption(String objectName, String message) {
        super(ErrorCode.INVALID_DATA_REQUEST, objectName + ":" + message);
    }

}