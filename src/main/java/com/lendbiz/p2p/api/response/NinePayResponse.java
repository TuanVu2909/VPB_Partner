package com.lendbiz.p2p.api.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NinePayResponse implements Serializable {
    private String success;
    private List<ProductResponse> data;

}
