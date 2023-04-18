package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Teacher;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "workhistory")
// 강사 이력
public class WorkHistory {

    @Id @GeneratedValue
    @Column(name = "work_history_id")
    private Long id;

    @Column(name = "academy_name")
    private String academyName;

    @Column(name = "start_date_year")
    private Integer startDateYear;

    @Column(name = "start_date_month")
    private Integer startDateMonth;

    @Column(name = "end_date_year")
    private Integer endDateYear;

    @Column(name = "end_date_month")
    private Integer endDateMonth;

    // 강사
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "teacher_seq")
    @JsonIgnore
    private Teacher teacher;

    // 연관관계 메서드
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getWorkHistories().add(this);
    }

    // 생성 메서드
    public static WorkHistory createWorkHistory(Teacher teacher, String academyName, Integer startDateYear, Integer startDateMonth, Integer endDateYear, Integer endDateMonth) {
        WorkHistory workHistory = new WorkHistory();

        workHistory.setTeacher(teacher);
        workHistory.setAcademyName(academyName);
        workHistory.setStartDateYear(startDateYear);
        workHistory.setStartDateMonth(startDateMonth);
        workHistory.setEndDateYear(endDateYear);
        workHistory.setEndDateMonth(endDateMonth);

        return workHistory;
    }
}
