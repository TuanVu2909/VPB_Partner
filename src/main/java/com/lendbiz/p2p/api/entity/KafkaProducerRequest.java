package com.lendbiz.p2p.api.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaProducerRequest<T> {
    private String method;
    private T object;
}
