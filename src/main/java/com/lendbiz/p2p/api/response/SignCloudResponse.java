package com.lendbiz.p2p.api.response;

import java.util.Date;
import java.util.List;

import com.lendbiz.p2p.api.model.MultipleSignedFileData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignCloudResponse {

	private int responseCode;
    private String responseMessage;
    private String billCode;
    private Date timestamp;
    private int logInstance;
    private String notificationMessage;
    private int remainingCounter;
    private byte[] signedFileData;
    private String signedFileName;
    private String authorizeCredential;
    private String signedFileUUID;
    private String mimeType;
    private String certificateDN;
    private String certificateSerialNumber;
    private String certificateThumbprint;
    private Date validFrom;
    private Date validTo;
    private String issuerDN;
    private String uploadedFileUUID;
    private String downloadedFileUUID;
    private byte[] downloadedFileData;
    private String signatureValue;
    private int authorizeMethod;
    private String notificationSubject;
    private String dmsMetaData;
    private String csr;
    private String certificate;
    private int certificateStateID;
    private List<MultipleSignedFileData> multipleSignedFileData;
}
