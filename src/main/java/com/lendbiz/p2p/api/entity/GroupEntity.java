/*
 * Apache License, Version 2.0
 *
 * Copyright (c) 2021 TU HOANG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "TLGROUPS")
@Getter
@Setter
public class GroupEntity {

    @Id
    @Column(name = "GRPID")
    String grpId;
    @Column(name = "GRPNAME")
    String grpName;
    @Column(name = "GRPTYPE")
    String grpType;
    @Column(name = "GRPRIGHT")
    String grpRight;
    @Column(name = "ACTIVE")
    String active;
    @Column(name = "DESCRIPTIon")
    String description;
    @Column(name = "PRGRPID")
    String prgrpId;

}
