package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "studenttestinfo")
public class StudentTestInfo {

    @Id @GeneratedValue
    @Column(name = "student_test_info_id")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "student_name")
    private String studentName;

    // 학생
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    // 시험 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "test_info_id")
    @JsonIgnore
    private TestInfo testInfo;

    // 연관관계 메서드
    public void setStudent(Student student) {
        this.student = student;
        student.getStudentTestInfos().add(this);
    }

    public void setTestInfo(TestInfo testInfo) {
        this.testInfo = testInfo;
        testInfo.getStudentTestInfos().add(this);
    }

    // 생성 메서드
    public static StudentTestInfo createStudentTestInfo(Student student, TestInfo testInfo, Integer score, String studentName) {
        StudentTestInfo studentTestInfo = new StudentTestInfo();

        studentTestInfo.setStudent(student);
        studentTestInfo.setTestInfo(testInfo);
        studentTestInfo.setScore(score);
        studentTestInfo.setStudentName(studentName);

        return studentTestInfo;
    }
}
