package com.lendbiz.p2p.api.constants;

import java.util.HashMap;
import java.util.Map;

/***********************************************************************
 * 
 * @package：com.lendbiz.p2p.api.constans，@class-name：Constans.java
 * 
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:57:18 AM
 *
 ***********************************************************************/
public class Constants {

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

	// info call api savis
	public static final String USER_NAME_CLIENT = "lendbiz";

	// url
	public static final String GET_OTP_HEAD_VALUE_2 = "eyJ4NXQiOiJOVGRtWmpNNFpEazNOalkwWXpjNU1tWm1PRGd3TVRFM01XWXdOREU1TVdSbFpEZzROemM0WkE9PSIsImtpZCI6ImdhdGV3YXlfY2VydGlmaWNhdGVfYWxpYXMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjYXJib24uc3VwZXIiLCJhcHBsaWNhdGlvbiI6eyJvd25lciI6ImFkbWluIiwidGllclF1b3RhVHlwZSI6bnVsbCwidGllciI6IlVubGltaXRlZCIsIm5hbWUiOiJITUFDLU9UUCIsImlkIjoxMiwidXVpZCI6Ijc2ZDkyMGViLTUxZTAtNDY4ZS1iMDgwLTY2ZjIyMGU5NmE3ZiJ9LCJpc3MiOiJodHRwczpcL1wvMTcyLjE2LjEyMC4xMDA6OTQ0M1wvb2F1dGgyXC90b2tlbiIsInRpZXJJbmZvIjp7IlVubGltaXRlZCI6eyJ0aWVyUXVvdGFUeXBlIjoicmVxdWVzdENvdW50IiwiZ3JhcGhRTE1heENvbXBsZXhpdHkiOjAsImdyYXBoUUxNYXhEZXB0aCI6MCwic3RvcE9uUXVvdGFSZWFjaCI6dHJ1ZSwic3Bpa2VBcnJlc3RMaW1pdCI6MCwic3Bpa2VBcnJlc3RVbml0IjpudWxsfX0sImtleXR5cGUiOiJQUk9EVUNUSU9OIiwicGVybWl0dGVkUmVmZXJlciI6IiIsInN1YnNjcmliZWRBUElzIjpbeyJzdWJzY3JpYmVyVGVuYW50RG9tYWluIjoiY2FyYm9uLnN1cGVyIiwibmFtZSI6IkhNQUMtT1RQIiwiY29udGV4dCI6IlwvaG1hYy1vdHBcL3YxIiwicHVibGlzaGVyIjoiYWRtaW4iLCJ2ZXJzaW9uIjoidjEiLCJzdWJzY3JpcHRpb25UaWVyIjoiVW5saW1pdGVkIn1dLCJwZXJtaXR0ZWRJUCI6IiIsImlhdCI6MTY0MTQ1OTU1MCwianRpIjoiYzJhODc0M2QtMjM0OS00OWRiLWE5MmEtMDkyZGYyM2Y5ZDMyIn0=.qLuFfswSsJ5av79AzYR3L4ELtEw-oT6AcAMQiJEXEYjHbAtisTYlq8kW9mdMDhSdYB0kIrnNZtbYO6MtqdHeNIrgiCWowrgJ8frFrwLbWpvWPuiIbTjgEhI2OXAudQw5U-fCTCLUI4MmWGK48ZgcJ3q3wAFg5P3s9Bkcw-ykh_CQKt3c0SCTMsEJ_3hdoUiW_qT4qkjfr7bgKaaFuEMPsTATVhh46BUUQ3HkNJcqP45UxEa6NgyzyPMIJQjhJhp66Ca_aRCpJ0hbqlIln5LUTz6ZhTw7pOFpFhstwQg30iwqTOz5589AhrKz3elXoAT7xC6FZpMfsRgIeF_dMXkZAA==";
	public static final String VERIFY_OTP_URL = "https://api-m.digital-id.vn/hmac-otp/v1/verification";

	public static final String GET_OTP_URl_2 = "https://api-m.digital-id.vn/hmac-otp/v1/generation";
	public static final String BASIC_AUTHEN_STRING = "Od7JOvkfEnhqswRfFW607TVBYS8a:TkIosoGyd8uPgeiB8Clh_Ec31pYa";
	public static final String GET_TOKEN_URL = "https://api-m.digital-id.vn/token";
	public static final String ESIGN_PREDICT = "https://sandbox-apim.savis.vn/ocr-prod/1.0.1/call/predict";
	public static final String ESIGN_FACE_GENERAL = "https://sandbox-apim.savis.vn/ocr-prod/1.0.1/call/face_general";
	public static final String ESIGN_REGISTER = "https://sandbox-apim.savis.vn/ekyc-prod/1.0.1/call/register_user_face";
	public static final String GET_OTP = "https://api-m.digital-id.vn/service-otp/v1/otp/generate";
	public static final String VALIDATE_OTP = "https://api-m.digital-id.vn/service-otp/v1/otp/validate";
	// public static final String SIGN_PDF =
	// "https://api-m.digital-id.vn/signing/1.0/pdf";
	public static final String SIGN_PDF = "https://api-m.digital-id.vn/lendbiz-signing/v1/pdf";
	public static final String ESIGN_API_KEY = "apikey";
	public static final String ESIGN_VALUE_HEADER = "eyJ4NXQiOiJOVGRtWmpNNFpEazNOalkwWXpjNU1tWm1PRGd3TVRFM01XWXdOREU1TVdSbFpEZzROemM0WkE9PSIsImtpZCI6ImdhdGV3YXlfY2VydGlmaWNhdGVfYWxpYXMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjYXJib24uc3VwZXIiLCJhcHBsaWNhdGlvbiI6eyJvd25lciI6ImFkbWluIiwidGllclF1b3RhVHlwZSI6bnVsbCwidGllciI6IlVubGltaXRlZCIsIm5hbWUiOiJMZW5kYml6IiwiaWQiOjExLCJ1dWlkIjoiNjA2MzU0MDYtZjY5YS00YTViLThhN2QtZjQ2ZWVjMTQ0ZmM1In0sImlzcyI6Imh0dHBzOlwvXC91YXQtYXBpcG9ydGFsLmRpZ2l0YWwtaWQudm46NDQzXC9vYXV0aDJcL3Rva2VuIiwidGllckluZm8iOnsiVW5saW1pdGVkIjp7InRpZXJRdW90YVR5cGUiOiJyZXF1ZXN0Q291bnQiLCJncmFwaFFMTWF4Q29tcGxleGl0eSI6MCwiZ3JhcGhRTE1heERlcHRoIjowLCJzdG9wT25RdW90YVJlYWNoIjp0cnVlLCJzcGlrZUFycmVzdExpbWl0IjowLCJzcGlrZUFycmVzdFVuaXQiOm51bGx9fSwia2V5dHlwZSI6IlBST0RVQ1RJT04iLCJwZXJtaXR0ZWRSZWZlcmVyIjoiIiwic3Vic2NyaWJlZEFQSXMiOlt7InN1YnNjcmliZXJUZW5hbnREb21haW4iOiJjYXJib24uc3VwZXIiLCJuYW1lIjoiZUtZQy1Qcm9kIiwiY29udGV4dCI6IlwvZWt5Yy1wcm9kXC8xLjAuMSIsInB1Ymxpc2hlciI6ImFkbWluIiwidmVyc2lvbiI6IjEuMC4xIiwic3Vic2NyaXB0aW9uVGllciI6IlVubGltaXRlZCJ9LHsic3Vic2NyaWJlclRlbmFudERvbWFpbiI6ImNhcmJvbi5zdXBlciIsIm5hbWUiOiJPQ1ItUHJvZCIsImNvbnRleHQiOiJcL29jci1wcm9kXC8xLjAuMSIsInB1Ymxpc2hlciI6ImFkbWluIiwidmVyc2lvbiI6IjEuMC4xIiwic3Vic2NyaXB0aW9uVGllciI6IlVubGltaXRlZCJ9XSwicGVybWl0dGVkSVAiOiIiLCJpYXQiOjE2MzUxNTA0OTAsImp0aSI6ImFiNmRjMWJhLTlmYTItNDJmYS1iYmE3LWRiYzU3ODMxN2NkMSJ9.f1Ylz7LI3RBEMocdKO3oUiAoM5K9NR6O6mTVVRPAumHowW4b2l9OM0985aImhBeEyAb8q47r3ZlQZ3xDdYQ24PL5geq9OsL4dilNtya9MB9VPD-_ZJGCrxKV-SxAVIulA5MPMGXenX4-CplCK_K8oIWipGSSBfOOVrw5xd8k04klEMuzxgO2hHq1bwR7APCFPWdorgcdWOKlqzPmQAJCWEdEJmchc1l6uQNxnxn_Tu4RM8WAxe7QMCAaZTO8HiWUiKKttszmcX6939cfYMTlGKsfj_WNXgYIZxgADfGW7x53cPx4nfH-JHSRlMX2Y8rYJ1jUueWRKl5Qa2xaFw1tIg==";
	public static final String LENDBIZ = "LENDBIZ";
	public static final String DES = "OPT For LENDBIZ";
	public static final String X_WSO2 = "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlHTFRDQ0JSV2dBd0lCQWdJUVZLQWg1dDltbVNOWVBaQ0w1K0l0Q1RBTkJna3Foa2lHOXcwQkFRc0ZBRENCDQpqekVMTUFrR0ExVUVCaE1DUjBJeEd6QVpCZ05WQkFnVEVrZHlaV0YwWlhJZ1RXRnVZMmhsYzNSbGNqRVFNQTRHDQpBMVVFQnhNSFUyRnNabTl5WkRFWU1CWUdBMVVFQ2hNUFUyVmpkR2xuYnlCTWFXMXBkR1ZrTVRjd05RWURWUVFEDQpFeTVUWldOMGFXZHZJRkpUUVNCRWIyMWhhVzRnVm1Gc2FXUmhkR2x2YmlCVFpXTjFjbVVnVTJWeWRtVnlJRU5CDQpNQjRYRFRJeE1EUXlNakF3TURBd01Gb1hEVEl5TURVeU16SXpOVGsxT1Zvd0Z6RVZNQk1HQTFVRUF3d01LaTVzDQpaVzVrWW1sNkxuWnVNSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDQVFFQXNCL1Y0MHA5DQpnMVoxUzc2aVJxN2llUnhZaUlxSGpTYS9namhrMDhrNWoxUDFCUmJpNkgrcVdsS2lMUnNqNkRzSUNHZ2pGTUl1DQp3eUF5YXNEZmNjU29lRTF6OWxLSUQzZlhVemZBc1VOQlJNS0ZGVE5OeExkOGo3bUdjeFliWGVPaWx3ZW0rVDhCDQpSTUdVVjJ6UTMydFNhUERzUHZtOFF6N1hRanVja2VrTjFSTk80QXhkSmZJSHpqbkpDS2E1WW03NFVEWTFHa1h1DQpMUnZGS0hReVFqSUtPdTA0OWszZjBNZ2FlTFlaZG8rN21xTGNobGpYOFh5eWY0MWw2cjJ6dERWVGUwWmRIK3RTDQpPa0R4VzRzYzdFV2Jhc1VaS3pLZGhkYVY0eTV4amgvZ0ZabFNUZ2xOdm9UR3V6SVN0RWxuQWVJbWE5NWt2eldqDQozL0ZxTjd2N2luaWN0d0lEQVFBQm80SUMrakNDQXZZd0h3WURWUjBqQkJnd0ZvQVVqWXhleEZTdGl1RjM2WnY1DQptd1hodUFHTlllRXdIUVlEVlIwT0JCWUVGQzZzVXY3WitRMjZNeld0RWhtWWRxYnB2aE5NTUE0R0ExVWREd0VCDQovd1FFQXdJRm9EQU1CZ05WSFJNQkFmOEVBakFBTUIwR0ExVWRKUVFXTUJRR0NDc0dBUVVGQndNQkJnZ3JCZ0VGDQpCUWNEQWpCSkJnTlZIU0FFUWpCQU1EUUdDeXNHQVFRQnNqRUJBZ0lITUNVd0l3WUlLd1lCQlFVSEFnRVdGMmgwDQpkSEJ6T2k4dmMyVmpkR2xuYnk1amIyMHZRMUJUTUFnR0JtZUJEQUVDQVRDQmhBWUlLd1lCQlFVSEFRRUVlREIyDQpNRThHQ0NzR0FRVUZCekFDaGtOb2RIUndPaTh2WTNKMExuTmxZM1JwWjI4dVkyOXRMMU5sWTNScFoyOVNVMEZFDQpiMjFoYVc1V1lXeHBaR0YwYVc5dVUyVmpkWEpsVTJWeWRtVnlRMEV1WTNKME1DTUdDQ3NHQVFVRkJ6QUJoaGRvDQpkSFJ3T2k4dmIyTnpjQzV6WldOMGFXZHZMbU52YlRBakJnTlZIUkVFSERBYWdnd3FMbXhsYm1SaWFYb3VkbTZDDQpDbXhsYm1SaWFYb3VkbTR3Z2dGK0Jnb3JCZ0VFQWRaNUFnUUNCSUlCYmdTQ0FXb0JhQUIxQUVhbFZldDErcEVnDQpNTFdpaVduMDgzMFJMRUYwdnYxSnVJV3I4dnh3L20xSEFBQUJlUGVHb3FjQUFBUURBRVl3UkFJZ1ZjUFpKenQzDQpkUFBsRGF0UlF4eldOb2dEUjRuRjJKb3h3b2szR1RXdkpMTUNJRndDVGhxUUFib2t2SkVyN3VwM28xOWh3MDQrDQpwYTkzbloyanlwSW10VkNSQUhjQTM2VmVxMmlDVHg5c3JlNjRYMDQrV3VyTm9oS2thbDZPT3hMQUlFUmNLbk1BDQpBQUY0OTRhaVFnQUFCQU1BU0RCR0FpRUE1WWZJSUlnTUNiaU0rZVpwRVV3bGpzU1FBYUQ3ZjVYcmhIdCtoc2tiDQp5SndDSVFEY3JVazh6dVE4czZHNVpaNmF0WEpyR2NsQVJtSGU4Szhja2xpM1hmcmdrQUIyQUZXQjFNSVdrRFlCDQpTdW9MbTFjOFUvREE1RGg0Y0NVSUZ5K2pxaDBIRTlNTUFBQUJlUGVHb2owQUFBUURBRWN3UlFJaEFMaXFHTGtwDQpnaUtFa1VtNDAyNlFiVnV2VXM5c2lyVVBuQmFST3d6cm1ra0hBaUJUNENmNVFtRnVldkpzRm90bk5GaGM5V3lhDQo4cTBIWGYvMjB1WkhrUmhxb0RBTkJna3Foa2lHOXcwQkFRc0ZBQU9DQVFFQU9NTk9zb3ZMUXlNc01xSVJDQzdxDQpCMUh2QlFvWXc5ZmU5UTRCenRxMUVHOGpVQWJFZFBhVlFnZ0dXbFF4VDNxbHp1QlNienN6Ykk4blUzM0h5SjFhDQo0NUxFeWFwQnhkL0ZEQzNhQVY4WGN2YmQzdVJjcFF6Y1NzWldDR1NTbzM4VnlzMGhZTm01T3pBUm9Zc2RkR3pmDQpFQWVkN3pXWHJlcWVCMmNNNlU4RXU1WUhEMXVlZ3RHSUJHQ29XbkYrMDFTbkdneGRXdENRcjdaSlVqbGhreHhWDQpKc05UUWVsWHp0WEREcm04dTNxVURXMU1vdEpDemdMbFRoM1ZXUngrMW9jcDBsSzZsQ3orTHAvR3ZDMGtmazlmDQpYak9HZEJFbmR5NVBZOVJzcVZNVDgzelNSR01LaUlJSUlXMk9OYU5RZ3RpcUVWZ01CSVlJa1lmcWpsZ2k2VEI5DQpWQT09DQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tDQo=";
	// info call get otp
	public static final String OTP_API_KEY = "apiKey";
	public static final String OTP_VALUE_HEADER = "eyJ4NXQiOiJNell4TW1Ga09HWXdNV0kwWldObU5EY3hOR1l3WW1NNFpUQTNNV0kyTkRBelpHUXpOR00wWkdSbE5qSmtPREZrWkRSaU9URmtNV0ZoTXpVMlpHVmxOZyIsImtpZCI6ImdhdGV3YXlfY2VydGlmaWNhdGVfYWxpYXMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjYXJib24uc3VwZXIiLCJhcHBsaWNhdGlvbiI6eyJvd25lciI6ImFkbWluIiwidGllclF1b3RhVHlwZSI6bnVsbCwidGllciI6IlVubGltaXRlZCIsIm5hbWUiOiJPVFBfU2VydmljZV9BUElfYXBwIiwiaWQiOjM2LCJ1dWlkIjoiMGEzNTU4N2QtOGM5Yi00ZDRlLTlmZGItY2I3ZmJjNzU4YTM2In0sImlzcyI6Imh0dHBzOlwvXC91YXQtYXBpcG9ydGFsLnRydXN0Y2Eudm46NDQzXC9vYXV0aDJcL3Rva2VuIiwidGllckluZm8iOnsiVW5saW1pdGVkIjp7InRpZXJRdW90YVR5cGUiOiJyZXF1ZXN0Q291bnQiLCJzdG9wT25RdW90YVJlYWNoIjp0cnVlLCJzcGlrZUFycmVzdExpbWl0IjowLCJzcGlrZUFycmVzdFVuaXQiOm51bGx9fSwia2V5dHlwZSI6IlBST0RVQ1RJT04iLCJwZXJtaXR0ZWRSZWZlcmVyIjoiIiwic3Vic2NyaWJlZEFQSXMiOlt7InN1YnNjcmliZXJUZW5hbnREb21haW4iOiJjYXJib24uc3VwZXIiLCJuYW1lIjoiT1RQX1NlcnZpY2VfQVBJIiwiY29udGV4dCI6Ilwvc2VydmljZS1vdHBcL3YxIiwicHVibGlzaGVyIjoiYWRtaW4iLCJ2ZXJzaW9uIjoidjEiLCJzdWJzY3JpcHRpb25UaWVyIjoiVW5saW1pdGVkIn1dLCJwZXJtaXR0ZWRJUCI6IiIsImlhdCI6MTYzNDA5NDI1MiwianRpIjoiNzY3MzAxMmItZmVkZC00MjMxLWE0MjAtY2M4NDlhN2YyMTcxIn0=.GoUUE-OfCsYuMmJgNnHl_dzvBgltzuVl1WQsTh9OfnkHfZR3aP5exg2mDbD6hTXZ2vgvzxvG5oTNuemkUJVTRZjP2aL74nTf9UFnGAm_ubIn3ZZpf0t4S4XCg8puV4SdAOxYUVnZgV_UiDwvyIFjlNk5jdMBHSZS3ZDaoWuBsf7D75jOl07sKTot0Z3hd8JqUhEKeyBXXGDKrU7-6BQnBqUcfyoNn5Kpe_z69b49NGQ9MMNUYbqzAPmnbF_YGvJWu1qtTg1PdiBFBUFpRJ_ccHMMIQv7tboXvdVQ1OqmAjx8FjXsY0I1iIEiA0nRrhYnOYv6avmcx5OGKmZ7-wjcTA==";

	// info call sign pdf
	public static final String SLOT_LABEL = "lendbiz_0108016492";
	public static final String USER_PIN = "6042a7a155624efd657b64694fcb4461dfcc32edb5a9a74837c3c97dae9fd9b8";
	public static final String ALIAS = "lendbiz_0108016492";

	// chu ky lbc
	public static final String LBC_SLOT_LABEL = "lendbiz_capital_0109216328";
	public static final String LBC_USER_PIN = "36da92aab4a9de33fdba6ddb6fc21ad7767b1251a3d2ee539c75a19cdbd5dd48";
	public static final String LBC_ALIAS = "lendbiz_capital_0109216328";

	public static final String IS_VISIBLE = "true";

	public static final String ERROR = "99";

	public static final String TYPE_FRONT_IDENTITY = "FRONT_IDENTITY";
	public static final String TYPE_BACK_IDENTITY = "BACK_IDENTITY";
	public static final String TYPE_SELFIE = "SELFIE";
	public static final String KEY_E_KYC = "E-KYC";

	// metadate api use register face
	public static final String METADATA = "{\"source\":\"24a00f5d-e625-4bc4-8384-c906848c03f3\"}";
	// 9pay api card
	public static final String NINE_PAY_PRODUCTS = "https://api-console.9pay.vn/service/wh/products";
	public static final String NINE_PAY_CARD = "https://api-console.9pay.vn/service/wh/order/card";
	public static final String NINE_PAY_INFO_TRANS = "https://api-console.9pay.vn/service/wh/order/check-info";
	public static final String NINE_PAY_BALANCE = "https://api-console.9pay.vn/service/account/balance";
	public static final String SIGN_IMAGE_DEFAULT = "images/condau.jpg";
	public static final String THRESHOLD = "0.75";

	// MBBANK
	public static final String HMAC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgcaKR79S/g+e+kCvGBAa957NCgAnuS4IEIUcAgoDfOSfTRaXeMHYnCD6b8apuoxAF7rA0/oLXJM0flhztNSpzW8oHVBksO5wZlFnT0U5eftfWixxoZHRJHPIQlcoXHAPt6oUiKqIpAnMdYP269gPZDiW75k9D6FfxosymXqVWO9F6yUbYU/REbQl8sn5H04q9IKeIW5MOtmMFGrGxZKecjW+a/Km3FIPfl4INM01rOJRWTCCfq0GimaYdy9JYcaNxM1QBqmy1UdyxAE9MKN4QsQqL3xLZDxt1ykcsLgF1BaF3OT7s4btZp88y0AuJ7LObVxS1wHevatvJl5f5DLq0QIDAQAB";
	public static final String MB_TRANSFER = "https://api-public.mbbank.com.vn/ms/funds-partner/transfer-fund/v1.0/make-transfer-partner";
	public static final String MB_GET_TOKEN_URL = "https://api-public.mbbank.com.vn/oauth2/v1/token";
	public static final String MB_GET_NAME_URL = "https://api-public.mbbank.com.vn/ms/onbehalfpayment/v1.0/onbehalfpayment/v1.0/account/credit/name";
	public static final String MB_CONVERT_ID_CARD = "https://mbcard.mbbank.com.vn:8446/mbcardgw/internet/cardinfo/v1_0/generatetoken";
	public static final String MBBANK_BASIC_AUTHEN_STRING = "CNyagnuEbAZuUiI6qNFmFDYGPK14cNdt:iWpKiQT787zW2TMq";

	public interface KAFKA {
		public static final String TOPIC_LOGS_MB = "topic_mb_logs";
		public static final String GROUP_MB_ID = "group_mb_id";
		public static final String TOPIC_LOGS_3GANG = "topic_3gang_logs";
		public static final String GROUP_3GANG_ID = "group_3gang_id";
		public static final String TOPIC_CASH_OUT_3GANG = "topic_cash_out_new";
		public static final String GROUP_CASH_OUT_3GANG_ID = "group_cash_out_id_new";
		public static final String TOPIC_SAVE_ID_CARD = "topic_save_id_card";
		public static final String GROUP_SAVE_ID_CARD = "group_save_id_card";
		public static final String TOPIC_3GANG_PARTITIONS = "3gang-partitions-log";
		public static final String GROUP_3GANG_PARTITIONS = "group_3gang_partitions";
	}

	// accesstrade
	public static final String ACCESS_TRADE_URI = "https://api.accesstrade.vn/v1/postbacks/conversions";
	public static final String ACCESS_TRADE_UPDATE_URI = "https://api.accesstrade.vn/v1/postbacks/conversions";

	// hyperlead
	public static final String HYPER_LEAD_URI = "https://s2s.riofintech.net";
	public static final String HYPER_LEAD_API_KEY = "k97Ocp5U6VoAMgGW";
	public static final String HYPER_LEAD_POSTBACK_TYPE = "cpl_standard_postback";

	// VNPT
	public static final String VNPT_DOMAIN = "https://api.idg.vnpt.vn";
	// Account trial
	// public static final String VNPT_ID = "ee3036c3-36f5-7efa-e053-63199f0aee9d";
	// public static final String VNPT_KEY =
	// "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALISKEQNtCXxqd71DupX9N7cdG3w4mdJOuNqtMXBwDKK8uR7LM38B8uZN5EzttpccxIv9/+5tHMJ//KZMpcBLiMCAwEAAQ==";
	// public static final String VNPT_TOKEN = "bearer
	// eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlZTMwMzIxYS0wZDczLTA5OGMtZTA1My02MjE5OWYwYTUwMDQiLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoibGVzb24xNzEwQGdtYWlsLmNvbSIsInNjb3BlIjpbInJlYWQiXSwiaXNzIjoiaHR0cHM6Ly9sb2NhbGhvc3QiLCJuYW1lIjoibGVzb24xNzEwQGdtYWlsLmNvbSIsInV1aWRfYWNjb3VudCI6ImVlMzAzMjFhLTBkNzMtMDk4Yy1lMDUzLTYyMTk5ZjBhNTAwNCIsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiOGQ3ZDQ5NjQtZDBiYS00ODY4LTk0N2ItN2FmNDM5NzJhMzhjIiwiY2xpZW50X2lkIjoiYWRtaW5hcHAifQ.DygdcdCwUthwSueTmf9wI39jkDchnndIzcliMOjW8fM5vuQxPJ3BDB5ZpmaRm2WG--7i0UVWo_Yj4I69kSqsP_zLEUF-p_riEbQL1FSYiOROJlMTsxHId6CUAIELWT7ugW8btW7Msqv6p7hSjSNipl3cNUW3Z2JkOikz-wdln1nqxMjiwo2UUaxkxQrUgwAHDBG7rgjJLge2H49VABRAfoqWBfrHZd8nTLdiPTLMqZcfg3nKXk7CHkjQ_Ft5P5sH-mRG7SUKab4Z4ympv8AJsiHMcxAgcwvhXfZ3HtU6c5FQ_9i05vvxRzJgl7sOptQw0-FGVfdnd_L8fJZzywrKZA";
	// Account live
	public static final String VNPT_ID = "edf6fbf8-6468-3377-e053-63199f0aea8b";
	public static final String VNPT_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ4DZir0axuFUoNmqUfzteiLjcx29ly/pDcFiMG/pFk5I/9z8eKvzw6WcfCG0VFY38FjBEA0sdtU2uhWfmxMBbsCAwEAAQ==";
	public static final String VNPT_TOKEN = "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlZGY2ZjlhOC1mYzcxLTY2ZDQtZTA1My02MjE5OWYwYWNlNTgiLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoibmd1eWVubGhuZ3V5ZW5AZ21haWwuY29tIiwic2NvcGUiOlsicmVhZCJdLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdCIsIm5hbWUiOiJuZ3V5ZW5saG5ndXllbkBnbWFpbC5jb20iLCJ1dWlkX2FjY291bnQiOiJlZGY2ZjlhOC1mYzcxLTY2ZDQtZTA1My02MjE5OWYwYWNlNTgiLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6ImVkYjg1NzUzLTIxMWEtNGZhMC1iYTJmLTI1M2I3ZDkyYjI2ZCIsImNsaWVudF9pZCI6ImFkbWluYXBwIn0.Ug0_c8cU8unP_J1E81sxHWBHcjISS1U-yVasD9q13gF6AgnYtsn3YMH4tbMIpQ-urQ1SuRtNWP4CTA3ywlPU_Y7tx0dkJ6FRnZjarfpsviGun1zafy5iccXOJGP47Hvjai34yk-hEQ_BZgjFy9s9kl56KqAyEXStwhNU5umyr-6Qj11c9BEXCnmOUc8tWC8VJ_uy9wc4zV8gw468XiZHAsZ9LzjwyMkf050DkK2EGoA4TMHlhuFaLo6xB5uR4X1wHiYhNyoDTp_8MmwwfpkoUbHo9ZIP3OTJ9DQ5zEIvtzQAbo8IepVuwPmb_lucO5v8AflXLOJBKLlottgutjsRdQ";

	// FUND AMBER
	public static final String AMBER_URL = "https://online.ambercapital.vn"; // link golive
	public static final String GRANT_TYPE = "password";
	public static final String CLIENT_ID = "FUNDAGENT";
	public static final String CLIENT_SECRET = "FUNDAGENT2022";
	public static final String USERNAME = "LBC";
	public static final String PASSWORD = "F]tT5pM{xs5UC%n";

	public static final String BANKCODE = "VCB";
	public static final String BANKNAME = "Ngân hàng TMCP Ngoại Thương Việt Nam";
	public static final String BANKACC = "1090000004";
	public static final String BANKRECEIVENAME = "Quỹ Đầu tư Trái Phiếu An toàn Amber";
	public static final String BANKBRANCH = "Hội Sở";

	public static final Map<String, Object> AFM_INFO_STATUS = new HashMap<>();
	static {
		AFM_INFO_STATUS.put("P", "Chờ duyệt");
		AFM_INFO_STATUS.put("R", "Chờ duyệt hủy");
		AFM_INFO_STATUS.put("A", "Hoạt động");
		AFM_INFO_STATUS.put("J", "Chờ duyệt sửa");
		AFM_INFO_STATUS.put("N", "Chờ đóng");
		AFM_INFO_STATUS.put("B", "Phong tỏa");
	}

	public static final Map<String, Object> AFM_STATUS_VSD = new HashMap<>();
	static {
		AFM_STATUS_VSD.put("T", "Chờ xác nhận sửa thông tin tài khoản");
		AFM_STATUS_VSD.put("P", "Chờ xác nhận mở tài khoản");
		AFM_STATUS_VSD.put("C", "Đã được VSD xác nhận mở");
		AFM_STATUS_VSD.put("VSDR", "VSD từ chối");
	}

	public static final Map<String, Object> AFM_DEAL_STATUS = new HashMap<>();
	static {
		AFM_DEAL_STATUS.put("2", "Lệnh đặt thành công");
		AFM_DEAL_STATUS.put("3", "Đang xử lý");
		AFM_DEAL_STATUS.put("4", "Khớp lệnh một phần");
		AFM_DEAL_STATUS.put("5", "Chờ đối chiếu");
		AFM_DEAL_STATUS.put("6", "Đang sửa");
		AFM_DEAL_STATUS.put("7", "Đang hủy");
		AFM_DEAL_STATUS.put("8", "Đã sửa (lệnh gốc)");
		AFM_DEAL_STATUS.put("9", "Đã hủy (lệnh gốc)");
		AFM_DEAL_STATUS.put("14", "Khớp lệnh thành công");
		AFM_DEAL_STATUS.put("20", "Khớp tiền thành công");
		AFM_DEAL_STATUS.put("21", "Khớp tiền không thành công");
	}
}
