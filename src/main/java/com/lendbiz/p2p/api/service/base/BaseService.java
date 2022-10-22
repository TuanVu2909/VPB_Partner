/***************************************************************************
 * Copyright 2018 by VIETIS - All rights reserved.                *    
 **************************************************************************/
package com.lendbiz.p2p.api.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class BaseService {

	public BaseService(Environment env) {
	}

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
