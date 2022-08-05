package com.lendbiz.p2p.api.model;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignCloudMetaData {
    private HashMap<String, String> singletonSigning;
    private HashMap<String, String> counterSigning;
    
}