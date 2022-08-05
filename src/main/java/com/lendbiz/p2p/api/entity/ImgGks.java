package com.lendbiz.p2p.api.entity;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImgGks {
    private String attachmentId;
    private String content;
    private String fileType;
    private String filename;

}
