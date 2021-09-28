package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.entity，@class-name：PartnerAccount.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:57:04 AM   
 *
 ***********************************************************************/
@Entity
@Table(name = "API_PARTNER_ACCOUNT")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PartnerAccountEntity {

	@Id
    @Column(name = "ID")
    private int id;
    @Column(name = "PARTNER_ACCOUNT")
    private String partnaerAccount;
    @Column(name = "PARTNER_PASSWORD")
    private String partnerPassword;
    @Column(name = "PARTNER_NAME")
    private String partnerName;
    @Column(name = "PARTNER_ROLE")
    private String partnerRole;

}
