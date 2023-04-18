package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Teacher;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "testinfo")
// 시험정보
public class TestInfo {

    @Id @GeneratedValue
    @Column(name = "test_info_id")
    private Long id;

    @Column(name = "test_info_name")
    private String testName;

    @Column(name = "test_date")
    private String testDate;

    // 반
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private Class clas;

    // 학생-시험 정보
    @OneToMany(mappedBy = "testInfo", cascade = REMOVE)
    private List<StudentTestInfo> studentTestInfos = new ArrayList<>();

    // 연관관계 메서드
    public void setClass(Class clas) {
        this.clas = clas;
        clas.getTestInfos().add(this);
    }

    // 생성 메서드
    public static TestInfo createTestInfo(Class clas, String testName, String testDate) {
        TestInfo testInfo = new TestInfo();

        testInfo.setClass(clas);
        testInfo.setTestName(testName);
        testInfo.setTestDate(testDate);

        return testInfo;
    }
}
