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
@Table(name = "teacherschedule")
// 강사 일정
public class TeacherSchedule {

    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @Column(name = "start_time_hour")
    private Integer startTimeHour;

    @Column(name = "start_time_minute")
    private Integer startTimeMinute;

    @Column(name = "end_time_hour")
    private Integer endTimeHour;

    @Column(name = "end_time_minute")
    private Integer endTimeMinute;

    @Column(name = "schedule_code")
    private String scheduleCode;

    @Column(name = "schedule_date")
    private String scheduleDate;

    // 강사
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "teacher_seq")
    @JsonIgnore
    private Teacher teacher;

    // 연관관계 메서드
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getTeacherSchedules().add(this);
    }

    // 생성 메서드
    public static TeacherSchedule createTeacherSchedule(Teacher teacher, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute) {
        TeacherSchedule teacherSchedule = new TeacherSchedule();

        teacherSchedule.setTeacher(teacher);
        teacherSchedule.setScheduleCode(scheduleCode);
        teacherSchedule.setScheduleDate(scheduleDate);
        teacherSchedule.setStartTimeHour(startTimeHour);
        teacherSchedule.setStartTimeMinute(startTimeMinute);
        teacherSchedule.setEndTimeHour(endTimeHour);
        teacherSchedule.setEndTimeMinute(endTimeMinute);

        return teacherSchedule;
    }
}
