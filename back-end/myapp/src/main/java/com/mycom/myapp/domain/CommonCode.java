package com.mycom.myapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "commoncode")
// 공통 코드
public class CommonCode {

    @Id @GeneratedValue
    @Column(name = "common_code_id")
    private String id;

    @Column(name = "common_code_name")
    private String name;

    @Column(name = "code_value")
    private String value;
}
