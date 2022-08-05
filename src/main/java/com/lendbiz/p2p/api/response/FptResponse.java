package com.lendbiz.p2p.api.response;

import java.io.Serializable;

import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Controller
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FptResponse<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	protected String ErrorCode;
	protected String Message;

	protected T data;

}
