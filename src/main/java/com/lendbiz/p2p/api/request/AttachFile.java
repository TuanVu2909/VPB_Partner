package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachFile {
    private String attachmentID;
    private String content;
    private String fileType;
    private String filename;
}
