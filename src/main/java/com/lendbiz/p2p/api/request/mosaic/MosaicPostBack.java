package com.lendbiz.p2p.api.request.mosaic;

import lombok.Data;

@Data
public class MosaicPostBack {
    private String click_id;
    private String comment;
    private int status_code;
    private String note;
}
