package com.lendbiz.p2p.api.constants;

public class ErrorCode {
    
    public static final String UNKNOWN_ERROR = "00"; 
    public static final String UNKNOWN_ERROR_DESCRIPTION = "Unknown Error";
    
    public static final String SUCCESS = "01";
    public static final String SUCCESS_DESCRIPTION = "Successful!";
    
    public static final String INVALID_DATA_REQUEST = "02";
    public static final String INVALID_DATA_REQUEST_DESCRIPTION = "Invalid Input's request!";

    public static final String NOT_FOUND_ENTITY = "03";
    public static final String NOT_FOUND_ENTITY_DESCRIPTION = "Not found entity!";
    
    public static final String INVALID_FILE = "04";
    public static final String INVALID_FILE_DESCRIPTION = "Upload false!";
    
    public static final String NOT_MATCH_DATA = "05";
    public static final String NOT_MATCH_DATA_DESCRIPTION = "Not match data!";
    
    public static final String UNAUTHORIZED = "06";
    public static final String UNAUTHORIZED_DESCRIPTION = "No permission to do!";
    
    public static final String INVALID_SESSION = "07";
    public static final String INVALID_SESSION_DESCRIPTION = "Invalid Session!";
    
    public static final String FAILED_TO_EXECUTE = "08";
    public static final String FAILED_TO_EXECUTE_DESCRIPTION = "Co loi xay ra khi ket noi toi may chu fpt";
    
    public static final String FAILED_TO_JSON = "09";
    public static final String FAILED_TO_JSON_DESCRIPTION = "Can not convert from Json to Object";
    
    public static final String FAILED_TO_FILE = "10";
    public static final String FAILED_TO_FILE_DESCRIPTION = "Can not convert file !";
    
    public static final String FAILED_SAVE_FILE = "11";
    public static final String FAILED_SAVE_FILE_DESCRIPTION = "Can not save file to server";

    public static final String FAILED_IDENTITY = "12";
    public static final String FAILED_IDENTITY_DESCRIPTION = "FAILED_IDENTITY_DESCRIPTION";

    public static final String FAILED_SELFIE = "13";
    public static final String FAILED_SELFIE_DESCRIPTION = "Anh selfie khong khop voi CMND/CCCD";
}
