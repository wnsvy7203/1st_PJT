package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "studentacademy")
// 학생-학원
public class StudentAcademy {

    @Id @GeneratedValue
    @Column(name = "student_academy_id")
    private Long id;

    @Column(name = "approve_yn")
    private boolean approveYn;

    @Column(name = "apply_date")
    private LocalDateTime applyDate;

    @Column(name = "approve_date")
    private LocalDateTime approveDate;

    @Column(name = "class_approve_yn")
    private boolean classApproveYn;

    // 학생
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    // 학원
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academy_id")
    @JsonIgnore
    private Academy academy;

    // 연관관계 메서드
    public void setStudent(Student student) {
        this.student = student;
        student.getStudentAcademies().add(this);
    }

    public void setAcademy(Academy academy) {
        this.academy = academy;
        academy.getStudentAcademies().add(this);
    }

    // 생성 메서드
    public static StudentAcademy createStudentAcademy(Student student, Academy academy) {
        StudentAcademy studentAcademy = new StudentAcademy();

        studentAcademy.setStudent(student);
        studentAcademy.setAcademy(academy);
        studentAcademy.setApplyDate(LocalDateTime.now());
        studentAcademy.setApproveYn(false);

        return studentAcademy;
    }
}
