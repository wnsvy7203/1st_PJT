package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "attendancy")
// 출석
public class Attendancy {

    @Id @GeneratedValue
    @Column(name = "attendancy_id")
    private Long id;

    @Column(name = "attendancy_date", nullable = false)
    private String attendancyDate;

    @Column(name = "attendancy_code", nullable = false)
    private String attendancyCode;

    // 학생
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    // 반
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private Class clas;

    // 연관관계 메서드
    // 출석-학생
    public void setStudent(Student student) {
        this.student = student;
        student.getAttendancies().add(this);
    }

    // 출석-반
    public void setClass(Class clas) {
        this.clas = clas;
        student.getAttendancies().add(this);
    }

    // 생성 메서드
    public static Attendancy createAttendancy(Student student, Class clas, String attendancyDate, String attendancyCode) {
        Attendancy attendancy = new Attendancy();

        attendancy.setStudent(student);
        attendancy.setClass(clas);
        attendancy.setAttendancyDate(attendancyDate);
        attendancy.setAttendancyCode(attendancyCode);

        return attendancy;
    }
}
