package com.lendbiz.p2p.api.exception;

import com.lendbiz.p2p.api.constans.ErrorCode;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.exception，@class-name：InvalidFileException.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:56:45 AM   
 *
 ***********************************************************************/
public class InvalidFileException extends BusinessException {

    private static final long serialVersionUID = 7641702942202000228L;
    
    public InvalidFileException() {
        super(ErrorCode.INVALID_FILE, ErrorCode.INVALID_FILE_DESCRIPTION);
    }
    
    public InvalidFileException(String message) {
        super(ErrorCode.INVALID_FILE, message);
    }
}
