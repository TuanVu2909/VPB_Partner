package com.lendbiz.p2p.api.response.amber;

import lombok.Data;

@Data
public class AmberResponse<T> {
    protected String EC;
    protected String EM;
    protected T DT;
}
