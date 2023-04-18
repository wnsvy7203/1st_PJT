package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.domain.user.Teacher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "class")
// 반
public class Class {

    @Id @GeneratedValue
    @Column(name = "clas_id")
    private Long id;

    @Column(name = "class_name", nullable = false)
    private String name;

    @Column(name = "subject_code", nullable = false)
    private String subjectCode;

    @Column(name = "grade_code", nullable = false)
    private String gradeCode;

    // 강사
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "teacher_seq")
    @JsonIgnore
    private Teacher teacher;

    // 학생-반(class가 예약어라 clas로)
    @OneToMany(mappedBy = "clas", cascade = REMOVE)
    private List<StudentClass> studentClasses = new ArrayList<>();

    // 학원
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academy_id")
    @JsonIgnore
    private Academy academy;

    // 진도(class가 예약어라 clas)
    @OneToMany(mappedBy = "clas", cascade = REMOVE)
    private List<Progress> progresses = new ArrayList<>();

    // 시험정보(class가 예약어라 clas)
    @OneToMany(mappedBy = "clas", cascade = REMOVE)
    private List<TestInfo> testInfos = new ArrayList<>();

    // 반-수업일시(class가 예약어라 clas)
    @OneToMany(mappedBy = "clas", cascade = REMOVE)
    private List<ClassSchedule> classSchedules = new ArrayList<>();

    // 출석
    @OneToMany(mappedBy = "clas", cascade = REMOVE)
    private List<Attendancy> attendancies = new ArrayList<>();

    // 연관관계 메서드
    public void setAcademy(Academy academy) {
        // 반 - 학원
        this.academy = academy;
        academy.getClasses().add(this);
    }

    public void setTeacher(Teacher teacher) {
        // 반 - 강사
        this.teacher = teacher;
        teacher.getClasses().add(this);
    }

    // 생성 메서드
    public static Class createClass (Academy academy, Teacher teacher, String className, String subjectCode, String gradeCode) {
        Class clas = new Class();

        clas.setAcademy(academy);
        clas.setTeacher(teacher);
        clas.setName(className);
        clas.setSubjectCode(subjectCode);
        clas.setGradeCode(gradeCode);

        return clas;
    }

    public void setTeacherToNull() {
        this.teacher = null;
    }
}
