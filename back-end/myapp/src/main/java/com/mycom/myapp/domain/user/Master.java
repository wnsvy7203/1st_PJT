package com.mycom.myapp.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.Academy;
import com.mycom.myapp.domain.MasterSchedule;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@DiscriminatorValue("MASTER")
@PrimaryKeyJoinColumn(name = "master_seq")
@Table(name = "master")
@Getter @Setter
// 학원장
public class Master extends User {

    // 학원
    @OneToOne(mappedBy = "master", cascade = REMOVE)
    @JsonIgnore
    private Academy academy;

    // 학원장 일정
    @OneToMany(mappedBy = "master", cascade = REMOVE)
    private List<MasterSchedule> masterSchedules = new ArrayList<>();
}
