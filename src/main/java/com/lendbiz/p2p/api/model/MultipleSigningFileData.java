/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lendbiz.p2p.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Chinhdd
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MultipleSigningFileData {
    
    private byte[] signingFileData;
    private String signingFileName;
    private String mimeType;
    private String xslTemplate;
    private String xmlDocument;
    private SignCloudMetaData signCloudMetaData;
    
    //
    private String hash;
}
