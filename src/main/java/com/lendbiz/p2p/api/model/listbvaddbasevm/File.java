package com.lendbiz.p2p.api.model.listbvaddbasevm;

import com.fasterxml.jackson.annotation.*;

public class File {
    private String content;
    private String fileType;
    private String filename;

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }

    @JsonProperty("fileType")
    public String getFileType() { return fileType; }
    @JsonProperty("fileType")
    public void setFileType(String value) { this.fileType = value; }

    @JsonProperty("filename")
    public String getFilename() { return filename; }
    @JsonProperty("filename")
    public void setFilename(String value) { this.filename = value; }
}
