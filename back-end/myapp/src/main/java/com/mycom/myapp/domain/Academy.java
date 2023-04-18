package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Master;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "academy")
// 학원
public class Academy {

    @Id @GeneratedValue
    @Column(name = "academy_id")
    private Long id;

    @Column(name = "academy_name", nullable = false)
    private String name;

    @Embedded
    private Address address;

    @Column(name = "academy_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "academy_introduce")
    private String introduce;

    // 학원장
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "master_seq")
    @JsonIgnore
    private Master master;

    // 강사-학원
    @OneToMany(mappedBy = "academy", cascade = REMOVE)
    private List<TeacherAcademy> teacherAcademies = new ArrayList<>();

    // 반
    @OneToMany(mappedBy = "academy", cascade = REMOVE)
    private List<Class> classes = new ArrayList<>();

    // 학생-학원
    @OneToMany(mappedBy = "academy", cascade = REMOVE)
    private List<StudentAcademy> studentAcademies = new ArrayList<>();

    // 연관관계 메서드
    public void setMaster(Master master) {
        this.master = master;
        master.setAcademy(this);
    }

    // 생성 메서드
    public static Academy createAcademy(Master master, String academyName, String zipCode, String firstAddress, String lastAddress, String phoneNumber, String introduce) {
        Academy academy = new Academy();

        academy.setMaster(master);
        academy.setName(academyName);
        academy.setAddress(new Address(zipCode, firstAddress, lastAddress));
        academy.setPhoneNumber(phoneNumber);
        academy.setIntroduce(introduce);

        return academy;
    }
}
