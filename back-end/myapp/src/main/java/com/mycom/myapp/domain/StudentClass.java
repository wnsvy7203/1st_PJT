package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "studentclass")
// 학생-반
public class StudentClass {

    @Id @GeneratedValue
    @Column(name = "student_class_id")
    private Long id;

    // 학생
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    // 반(class가 예약어라 clas로)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private Class clas;

    // 특이사항
    @OneToMany(mappedBy = "studentClass", cascade = REMOVE)
    private List<Note> notes = new ArrayList<>();

    // 연관관계 메서드
    public void setStudent(Student student) {
        // 학생반 - 학생
        this.student = student;
        student.getStudentClasses().add(this);
    }

    public void setClass(Class clas) {
        // 학생반 - 반
        this.clas = clas;
        clas.getStudentClasses().add(this);
    }

    // 생성 메서드
    public static StudentClass createStudentClass(Student student, Class clas) {
        StudentClass studentClass = new StudentClass();

        studentClass.setStudent(student);
        studentClass.setClass(clas);

        return studentClass;
    }
}
