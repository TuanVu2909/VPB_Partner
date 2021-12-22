package com.lendbiz.p2p.api.constants;

public class ErrorCode {

    public static final String UNKNOWN_ERROR = "01";
    public static final String UNKNOWN_ERROR_DESCRIPTION = "Unknown Error";

    public static final String SUCCESS = "00";
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

    // public static final String FAILED_SELFIE = "13";
    // public static final String FAILED_SELFIE_DESCRIPTION = "Anh selfie khong khop
    // voi CMND/CCCD";

    public static final String NOT_MATCHING_TYPE = "14";
    public static final String NOT_MATCHING_CMND_DESCRIPTION = "Bạn chọn sai loại giấy tờ tuỳ thân. Hãy chụp loại giấy tờ là CHỨNG MINH NHÂN DÂN!";
    public static final String NOT_MATCHING_CCCD_DESCRIPTION = "Bạn chọn sai loại giấy tờ tuỳ thân. Hãy chụp loại giấy tờ là CĂN CƯỚC CÔNG DÂN!";
    public static final String NOT_MATCHING_HC_DESCRIPTION = "Bạn chọn sai loại giấy tờ tuỳ thân. Hãy chụp loại giấy tờ là HỘ CHIẾU!";
    public static final String NOT_MATCHING_CMCAND_DESCRIPTION = "Bạn chọn sai loại giấy tờ tuỳ thân. Hãy chụp loại giấy tờ là CHỨNG MINH CÔNG AN NHÂN DÂN!";
    public static final String NOT_MATCHING_CMNDQD_DESCRIPTION = "Bạn chọn sai loại giấy tờ tuỳ thân. Hãy chụp loại giấy tờ là CHỨNG MINH NHÂN DÂN QUÂN ĐỘI!";

    public static final String NOT_MATCHING_SIDE = "15";
    public static final String NOT_MATCHING_FRONT_CMND_DESCRIPTION = "Bạn chụp sai mặt giấy tờ tuỳ thân. Hãy chụp mặt trước CHỨNG MINH NHÂN DÂN!";
    public static final String NOT_MATCHING_BACK_CMND_DESCRIPTION = "Bạn chụp sai mặt giấy tờ tuỳ thân. Hãy chụp mặt sau CHỨNG MINH NHÂN DÂN!";

    public static final String NOT_MATCHING_FRONT_CCCD_DESCRIPTION = "Bạn chụp sai mặt giấy tờ tuỳ thân. Hãy chụp mặt trước CĂN CƯỚC CÔNG DÂN!";
    public static final String NOT_MATCHING_BACK_CCCD_DESCRIPTION = "Bạn chụp sai mặt giấy tờ tuỳ thân. Hãy chụp mặt sau CĂN CƯỚC CÔNG DÂN!";

    public static final String NOT_MATCHING_SIDE_HC_DESCRIPTION = "Bạn chụp sai mặt giấy tờ tuỳ thân. Hãy chụp đúng 2 trang đầu của HỘ CHIẾU!";

    public static final String NOT_MATCHING_FRONT_CMCAND_DESCRIPTION = "Bạn chụp sai mặt giấy tờ tuỳ thân. Hãy chụp mặt trước CHỨNG MINH CÔNG AN NHÂN DÂN!";
    public static final String NOT_MATCHING_BACK_CMCAND_DESCRIPTION = "Bạn chụp sai mặt giấy tờ tuỳ thân. Hãy chụp mặt sau CHỨNG MINH CÔNG AN NHÂN DÂN!";

    public static final String NOT_MATCHING_FRONT_CMNDQD_DESCRIPTION = "Bạn chụp sai mặt giấy tờ tuỳ thân. Hãy chụp mặt trước CHỨNG MINH NHÂN DÂN QUÂN ĐỘI!";
    public static final String NOT_MATCHING_BACK_CMNDQD_DESCRIPTION = "Bạn chụp sai mặt giấy tờ tuỳ thân. Hãy chụp mặt sau CHỨNG MINH NHÂN DÂN QUÂN ĐỘI!";

    public static final String ID_FAKE = "16";
    public static final String ID_FAKE_DESCRIPTION = "Giấy tờ tuỳ thân của Quý Khách không hợp lệ. Vui lòng kiểm tra và thử lại!";
}
