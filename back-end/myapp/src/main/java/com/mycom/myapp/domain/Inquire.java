package com.mycom.myapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "inquire")
// 학원등록문의
public class Inquire {

    @Id @GeneratedValue
    @Column(name = "inquire_id")
    private Long id;

    @Column(name = "inquire_name")
    private String name;

    @Column(name = "inquire_email")
    private String email;

    @Column(name = "inquire_phone_number")
    private String phone_number;

    @Column(name = "inquire_user_id")
    private String userId;

    @Column(name = "inquire_academy_name")
    private String academyName;

    @Embedded
    private Address address;

    @Column(name = "inquire_academy_phone_number")
    private String academyPhoneNumber;
}
