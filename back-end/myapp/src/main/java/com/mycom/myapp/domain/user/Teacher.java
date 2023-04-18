package com.mycom.myapp.domain.user;

import com.mycom.myapp.domain.*;
import com.mycom.myapp.domain.Class;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@DiscriminatorValue("TEACHER")
@PrimaryKeyJoinColumn(name = "teacher_seq")
@Table(name = "teacher")
@Getter @Setter
// 강사
public class Teacher extends User {

    @Column(name = "teacher_introduce")
    private String teacherIntroduce;

    @Column(name = "subject_code")
    private String subjectCode;

    // 근무 이력
    @OneToMany(mappedBy = "teacher", cascade = REMOVE)
    private List<WorkHistory> workHistories = new ArrayList<>();

    // 반
    @OneToMany(mappedBy = "teacher")
    private List<Class> classes = new ArrayList<>();

    // 강사 일정
    @OneToMany(mappedBy = "teacher", cascade = REMOVE)
    private List<TeacherSchedule> teacherSchedules = new ArrayList<>();

    // 강사-학원
    @OneToMany(mappedBy = "teacher", cascade = REMOVE)
    private List<TeacherAcademy> teacherAcademies = new ArrayList<>();
}
