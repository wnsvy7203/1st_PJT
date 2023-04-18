package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Master;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "masterschedule")
// 강사 일정
public class MasterSchedule {

    @Id @GeneratedValue
    @Column(name = "master_schedule_id")
    private Long id;

    @Column(name = "start_time_hour", nullable = false)
    private Integer startTimeHour;

    @Column(name = "start_time_minute", nullable = false)
    private Integer startTimeMinute;

    @Column(name = "end_time_hour", nullable = false)
    private Integer endTimeHour;

    @Column(name = "end_time_minute", nullable = false)
    private Integer endTimeMinute;

    @Column(name = "schedule_code", nullable = false)
    private String scheduleCode;

    @Column(name = "schedule_date", nullable = false)
    private String scheduleDate;

    // 학원장
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "master_seq")
    @JsonIgnore
    private Master master;

    // 연관관계 메서드
    public void setMaster(Master master) {
        this.master = master;
        master.getMasterSchedules().add(this);
    }

    // 생성 메서드
    public static MasterSchedule createMasterSchedule(Master master, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute) {
        MasterSchedule masterSchedule = new MasterSchedule();

        masterSchedule.setMaster(master);
        masterSchedule.setScheduleCode(scheduleCode);
        masterSchedule.setScheduleDate(scheduleDate);
        masterSchedule.setStartTimeHour(startTimeHour);
        masterSchedule.setStartTimeMinute(startTimeMinute);
        masterSchedule.setEndTimeHour(endTimeHour);
        masterSchedule.setEndTimeMinute(endTimeMinute);

        return masterSchedule;
    }
}
