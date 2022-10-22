package com.lendbiz.p2p.api.request;

import java.util.List;

import com.lendbiz.p2p.api.model.FileInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UploadFilesRequest {
	private String custId;
	private List<FileInfo> files;

}
