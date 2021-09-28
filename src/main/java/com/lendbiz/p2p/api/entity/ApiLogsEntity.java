package com.lendbiz.p2p.api.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/***********************************************************************
 * 
 * @package：com.lendbiz.p2p.api.entity，@class-name：ApiLogs.java
 * 
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 6:17:24 PM
 *
 ***********************************************************************/

@Entity
@Table(name = "API_LOGS")
public class ApiLogsEntity {

	@Id
	@Column(name = "REQUEST_ID")
	private String requestId;
	@Column(name = "MESSAGE_TYPE")
	private String messageType;
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
	@Column(name = "BODY_DETAIL")
	private String bodyDetail;
	@Column(name = "HTTP_METHOD")
	private String httMethod;
	@Column(name = "SOURCE_APP_ID")
	private String sourceAppId;
	@Column(name = "SOURCE_APP_IP")
	private String sourceAppIp;
	@Column(name = "DEST_APP_ID")
	private String destAppId;
	@Column(name = "DEST_APP_PORT")
	private String destAppPort;
	@Column(name = "AUTHORIZATION")
	private String authorization;
}
