package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Parent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "meeting")
// 상담
public class Meeting {

    @Id @GeneratedValue
    @Column(name = "meeting_id")
    private Long id;

    @Column(name = "meeting_code", nullable = false)
    private String meetingCode;

    @Column(name = "counselor_seq", nullable = false)
    private Long counselorSeq;

    @Column(name = "academy_name", nullable = false)
    private String academyName;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "meeting_start_hour", nullable = false)
    private Integer meetingStartHour;

    @Column(name = "meeting_start_minute", nullable = false)
    private Integer meetingStartMinute;

    @Column(name = "meeting_start_date", nullable = false)
    private String meetingStartDate;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "class_id", nullable = false)
    private Long classId;

    // 학부모
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_seq")
    @JsonIgnore
    private Parent parent;

    // 리뷰
    @OneToMany(mappedBy = "meeting")
    private List<Review> reviews = new ArrayList<>();

    // 연관관계 메서드
    public void setParent(Parent parent) {
        this.parent = parent;
        parent.getMeetings().add(this);
    }

    // 생성 메서드
    public static Meeting createMeeting (Parent parent, Long counselorSeq, String meetingCode, String academyName, String className, String studentName, Integer meetingStartHour, Integer meetingStartMinute, String meetingStartDate, Long studentId, Long classId) {
        Meeting meeting = new Meeting();

        meeting.setParent(parent);
        meeting.setCounselorSeq(counselorSeq);
        meeting.setMeetingCode(meetingCode);
        meeting.setAcademyName(academyName);
        meeting.setClassName(className);
        meeting.setStudentName(studentName);
        meeting.setMeetingStartHour(meetingStartHour);
        meeting.setMeetingStartMinute(meetingStartMinute);
        meeting.setMeetingStartDate(meetingStartDate);
        meeting.setStudentId(studentId);
        meeting.setClassId(classId);

        return meeting;
    }

    public void setParentToNull() {
        this.parent = null;
    }
}
