package com.mycom.myapp.domain.user;

import com.mycom.myapp.domain.Meeting;
import com.mycom.myapp.domain.ParentAcademy;
import com.mycom.myapp.domain.Student;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

@Entity
@DiscriminatorValue("PARENT")
@PrimaryKeyJoinColumn(name = "parent_seq")
@Table(name = "parent")
@Getter @Setter
// 학부모
public class Parent extends User {

    // 학부모-학원
    @OneToMany(mappedBy = "parent")
    private List<ParentAcademy> parentAcademies = new ArrayList<>();

    // 학생
    @OneToMany(mappedBy = "parent", cascade = REMOVE)
    private List<Student> students = new ArrayList<>();

    // 상담
    @OneToMany(mappedBy = "parent")
    private List<Meeting> meetings = new ArrayList<>();
}
