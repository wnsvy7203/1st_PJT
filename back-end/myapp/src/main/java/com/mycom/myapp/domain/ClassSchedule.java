package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Teacher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "classschedule")
// 반-수업일시
public class ClassSchedule {

    @Id @GeneratedValue
    @Column(name = "class_schedule_id")
    private Long id;

    @Column(name = "day_of_the_week", nullable = false)
    private String dayOfTheWeek;

    @Column(name = "start_time_hour", nullable = false)
    private Integer startTimeHour;

    @Column(name = "start_time_minute", nullable = false)
    private Integer startTimeMinute;

    @Column(name = "end_time_hour", nullable = false)
    private Integer endTimeHour;

    @Column(name = "end_time_minute", nullable = false)
    private Integer endTimeMinute;

    // 반(class가 예약어라 clas)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private Class clas;

    // 연관관계 메서드
    public void setClass(Class clas) {
        // 반 - 학원
        this.clas = clas;
        clas.getClassSchedules().add(this);
    }

    // 생성 메서드
    public static ClassSchedule createClassSchedule (Class clas, String dayOfTheWeek, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute) {
        ClassSchedule classSchedule = new ClassSchedule();

        classSchedule.setClass(clas);
        classSchedule.setDayOfTheWeek(dayOfTheWeek);
        classSchedule.setStartTimeHour(startTimeHour);
        classSchedule.setStartTimeMinute(startTimeMinute);
        classSchedule.setEndTimeHour(endTimeHour);
        classSchedule.setEndTimeMinute(endTimeMinute);

        return classSchedule;
    }
}
