package com.lendbiz.p2p.api.entity;
import lombok.*;

import javax.naming.ldap.PagedResultsControl;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRACKING_OTP")
public class Otp implements Serializable {
    @Id
    private String id;
    @Column(name = "OTP")
    private String otp;
}
