package com.lendbiz.p2p.api.constans;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.constans，@class-name：Constans.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:57:18 AM   
 *
 ***********************************************************************/
public class Constans {
	
	public static final String SUCCESS = "00";
	public static final String FAIL = "01";
	public static final String MESSAGE_SUCCESS = "SUCCESSFUL!";
	public static final String MESSAGE_FAIL = "HAS ERROR!";

	public static final String TLPROFILE_TLID = "TLID";
	public static final String LENDBIZ_HO_ID = "0001";
	public static final String LENDBIZ_DN_ID = "0002";

	public static final String SPACES = " ";

	public static interface authorizationMode {

		String GC_AUTHORIZATION_MODE_LDAP = "LDAP";
		String GC_AUTHORIZATION_MODE_DB = "DB";
		String GC_AUTHORIZATION_MODE_NONE = "NONE";
	}
	
	public static interface lbResponseErrorCode {

		String ERROR_NOT_FOUND = "05";
		String ERROR_CODE_AUTHENTICATE_FAIL = "101";
		
	}
	
	public static interface lbResponseMessages {

		String ERROR_NOT_FOUND = "Không tìm thấy kết quả!";
		String RES_MSG_AUTHENTICATE_FAIL = "AUTHENTICATE FAIL!";
		
	}

	public static interface userDetails {

		String USER = "USER";
		
	}

}
