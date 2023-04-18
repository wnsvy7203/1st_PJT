package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Teacher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Table(name = "teacheracademy")
// 강사-학원
public class TeacherAcademy {

    @Id @GeneratedValue
    @Column(name = "teacher_academy_id")
    private Long id;

    @Column(name = "approve_yn")
    private boolean approveYn;

    @Column(name = "apply_date")
    private LocalDateTime applyDate;

    @Column(name = "approve_date")
    private LocalDateTime approveDate;

    // 강사
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "teacher_seq")
    @JsonIgnore
    private Teacher teacher;

    // 학원
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academy_id")
    @JsonIgnore
    private Academy academy;

    // 연관관계 메서드
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getTeacherAcademies().add(this);
    }

    public void setAcademy(Academy academy) {
        this.academy = academy;
        academy.getTeacherAcademies().add(this);
    }

    // 생성 메서드
    public static TeacherAcademy createTeacherAcademy(Teacher teacher, Academy academy) {
        TeacherAcademy teacherAcademy = new TeacherAcademy();

        teacherAcademy.setTeacher(teacher);
        teacherAcademy.setAcademy(academy);
        teacherAcademy.setApplyDate(LocalDateTime.now());
        teacherAcademy.setApproveYn(false);

        return teacherAcademy;
    }
}
