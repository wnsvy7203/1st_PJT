package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Parent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "student")
// 학생
public class Student {

    @Id @GeneratedValue
    @Column(name = "student_id")
    private Long id;

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_school")
    private String school;

    @Column(name = "student_age")
    private Integer age;

    @Column(name = "student_phone_number")
    private String phoneNumber;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "grade_code")
    private String gradeCode;

    @Column(name = "student_profile_image_url")
    private String studentProfileImageUrl;

    // 학부모
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_seq")
    @JsonIgnore
    private Parent parent;

    // 학생-반
    @OneToMany(mappedBy = "student", cascade = REMOVE)
    private List<StudentClass> studentClasses = new ArrayList<>();

    // 학생-학원
    @OneToMany(mappedBy = "student", cascade = REMOVE)
    private  List<StudentAcademy> studentAcademies = new ArrayList<>();

    // 학생-시험정보
    @OneToMany(mappedBy = "student", cascade = REMOVE)
    private List<StudentTestInfo> studentTestInfos = new ArrayList<>();

    // 출석
    @OneToMany(mappedBy = "student", cascade = REMOVE)
    private List<Attendancy> attendancies = new ArrayList<>();

    // 연관관계 메서드
    public void setParent(Parent parent) {
        this.parent = parent;
        parent.getStudents().add(this);
    }

    // 생성 메서드
    public static Student createStudent(Parent parent, String name, String school, String gradeCode, Integer age, String phoneNumber, Integer priority) {
        Student student = new Student();

        student.setParent(parent);
        student.setName(name);
        student.setSchool(school);
        student.setGradeCode(gradeCode);
        student.setAge(age);
        student.setPhoneNumber(phoneNumber);
        student.setPriority(priority);

        return student;
    }
}
