package com.lendbiz.p2p.api.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CFMAST")
@NamedQuery(name = "CfMast.findAll", query = "SELECT c FROM CfMast c")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CfMast {	
	@Id
	private String custid;

	@Column(name = "SHORTNAME")
	private String shortName;

	@Column(name = "FULLNAME")
	private String fullName;

	@Column(name = "MNEMONIC")
	private String mnemonic;

	@Column(name = "DATEOFBIRTH")
	private Date  dateOfBirth;

	@Column(name = "IDTYPE")
	private String idType;  

	@Column(name = "IDCODE")
	private String idCode;

	@Column(name = "IDDATE")
	private Date idDate; 

	@Column(name = "IDPLACE")
	private String idPlace;

	@Column(name = "IDEXPIRED")
	private Date  idExpired;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "COUNTRY")
	private String  country;

	@Column(name = "PROVINCE")
	private String  province;

	@Column(name = "POSTCODE")
	private String postCode;

	@Column(name = "RESIDENT")
	private String  resident;

	@Column(name = "CLASS")
	private String  grClass;

	@Column(name = "GRINVESTOR")
	private String  grInvestor;

	@Column(name = "INVESTRANGE")
	private String  investRange;

	@Column(name = "TIMETOJOIN")
	private String  timeToJoin;

	@Column(name = "CUSTODYCD")
	private String custOdycd ;

	@Column(name = "STAFF")
	private String  staff;

	@Column(name = "COMPANYID")
	private String companyId;

	@Column(name = "POSITION")
	private String  position;

	@Column(name = "SEX")
	private String  sex;

	@Column(name = "SECTOR")
	private String  sector;

	@Column(name = "BUSINESSTYPE")
	private String  businessType;

	@Column(name = "INVESTTYPE")
	private String  investType;

	@Column(name = "EXPERIENCETYPE")
	private String  experienceType;

	@Column(name = "INCOMERANGE")
	private String  incomeRange;

	@Column(name = "ASSETRANGE")
	private String  assetRange;

	@Column(name = "FOCUSTYPE")
	private String  focusType;

	@Column(name = "BRID")
	private String  brid;

	@Column(name = "CAREBY")
	private String  careBy;

	@Column(name = "APPROVEID")
	private String  approveId;

	@Column(name = "LASTDATE")
	private Date  lastDate;

	@Column(name = "AUDITORID")
	private String  auditorId;

	@Column(name = "AUDITDATE")
	private Date auditDate; 

	@Column(name = "LANGUAGE")
	private String  language;

	@Column(name = "BANKACCTNO")
	private String bankacctNo;

	@Column(name = "BANKCODE")
	private String  bankCode;

	@Column(name = "VALUDADDED")
	private String  valuDadded;

	@Column(name = "ISSUERID")
	private String issuerId;

	@Column(name = "DESCRIPTION")
	private String description ;

	@Column(name = "MARRIED")
	private String married;

	@Column(name = "TAXCODE")
	private String taxCode;

	@Column(name = "INTERNATION")
	private String internation;

	@Column(name = "OCCUPATION")
	private String occupation;

	@Column(name = "EDUCATION")
	private String education;

	@Column(name = "CUSTTYPE")
	private String  custType;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "PSTATUS")
	private String pStatus ;

	@Column(name = "INVESTMENTEXPERIENCE")
	private String  investMentExperience;

	@Column(name = "PCUSTODYCD")
	private String pCustOdycd;

	@Column(name = "EXPERIENCECD")
	private String  experiencecd;

	@Column(name = "ORGINF")
	private String orginf;

	@Column(name = "TLID")
	private String  tlid;

	@Column(name = "ISBANKING")
	private String isBanking;

	@Column(name = "PIN")
	private String pin;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "MRLOANLIMIT") 
	private Float mrLoanLimit;

	@Column(name = "RISKLEVEL")
	private String  rickLevel;

	@Column(name = "TRADINGCODE")
	private String tradingCode;

	@Column(name = "TRADINGCODEDT")
	private Date tradingCodeDt;
	 
	@Column(name = "LAST_CHANGE")
	private Timestamp  lastChange;

	@Column(name = "OPNDATE")
	private Date  opnDate;

	@Column(name = "CFCLSDATE")
	private Date cfClsDate;

	//  DEFAULT 'N'
	@Column(name = "MARGINALLOW")
	private String marginAllow;

	@Column(name = "CUSTATCOM")
	private String custAtcom;

	@Column(name = "T0LOANLIMIT") 
	private float toLoanLimit;

	// DEFAULT 'N'
	@Column(name = "DMSTS") 
	private String dmsts;

	@Column(name = "ACTIVEDATE")
	private Date activeDate; 

	// DEFAULT 'N'
	@Column(name = "AFSTATUS")
	private String afStatus;

	@Column(name = "MOBILESMS")
	private String mobileSms;

	// DEFAULT 'F'
	@Column(name = "OPENVIA")
	private String openVia;

	@Column(name = "OLAUTOID")
	private Float  olAutoId;

	// DEFAULT 'Y'
	@Column(name = "VAT")
	private String vat; 

	@Column(name = "REFNAME")
	private String refName;

	//  DEFAULT 'Y'
	@Column(name = "TRADEFLOOR")
	private String tradeFloor;

	//  DEFAULT 'Y'
	@Column(name = "TRADETELEPHONE")
	private String tradeTelephone;

	//  DEFAULT 'Y'
	@Column(name = "TRADEONLINE")
	private String  tradeOnline;

	@Column(name = "COMMRATE") 
	private float commRate;

	//  DEFAULT 'Y'
	@Column(name = "CONSULTANT")
	private String  consultant;

	//  DEFAULT 'N'
	@Column(name = "ACTIVESTS")
	private String  activests;

	@Column(name = "LAST_MKID" )
	private String lastMkid;

	@Column(name = "LAST_OFID")
	private String  lastOfId;

	@Column(name = "ONLINELIMIT")
	private float onlineLimit;

	//  DEFAULT 'Y'
	@Column(name = "ISCHKONLIMIT")
	private String isChkOnLimit;

	//  DEFAULT 'A'
	@Column(name = "MANAGETYPE")
	private String manageType;

	// DEFAULT 'P'
	@Column(name = "NSDSTATUS")
	private String nsdStatus;

	@Column(name = "NSDOPNDATE")
	private Date  nsdOpnDate;

	@Column(name = "NSDCLSDATE")
	private Date  nsdClsDate; 

	@Column(name = "WEBSITE")
	private String website;

	@Column(name = "TIME2RC")
	private String time2rc;

	@Column(name = "REMAINLIMIT")
	private Float  remainLimit;

	@Column(name = "REVENUE")
	 private Float  revenue;

	@Column(name = "REFIDCODE")
	private String refIdCode;

	@Column(name = "REFIDDATE")
	private Date refIdDate;

	@Column(name = "REFIDPLACE")
	private String refIdPlace;

	@Column(name = "REFCUSTID")
	private String refCustId;

}
