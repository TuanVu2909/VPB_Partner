package com.lendbiz.p2p.api.exception;

import com.lendbiz.p2p.api.constants.ErrorCode;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.exception，@class-name：NotFoundException.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:56:53 AM   
 *
 ***********************************************************************/
public class NotFoundException extends BusinessException {

    private static final long serialVersionUID = 7641702942202000228L;
    
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND_ENTITY, ErrorCode.NOT_FOUND_ENTITY_DESCRIPTION);
    }
    
    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND_ENTITY, message);
    }
    
}
