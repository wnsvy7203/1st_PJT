package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Meeting;
import com.mycom.myapp.domain.user.Parent;
import com.mycom.myapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    // 학부모-상담 목록 조회
    @Query(value = "SELECT m.meeting_id, m.parent_seq, m.counselor_seq, m.meeting_code, u.user_name, m.academy_name, m.class_name, m.meeting_start_date, m.meeting_start_hour, m.meeting_start_minute, m.student_name, m.student_id, m.class_id FROM meeting m join user u ON m.counselor_seq = u.user_seq WHERE m.parent_seq = :parentSeq", nativeQuery = true)
    public List<ParentMeetingInfoResponse> findByparentSeq(@Param("parentSeq") Long parentSeq);

    public interface ParentMeetingInfoResponse {
        Long getMeeting_id();
        Long getParent_seq();
        Long getCounselor_seq();
        String getMeeting_code();
        String getUser_name();
        String getAcademy_name();
        String getClass_name();
        String getMeeting_start_date();
        Integer getMeeting_start_hour();
        Integer getMeeting_start_minute();
        String getStudent_name();
        Long getStudent_id();
        Long getClass_id();
    }

    // 강사, 학원장-상담 목록 조회
    @Query(value = "SELECT m.meeting_id, m.parent_seq, m.counselor_seq, m.meeting_code, m.academy_name, m.class_name, m.meeting_start_date, m.meeting_start_hour, m.meeting_start_minute, m.student_name, m.student_id, m.class_id FROM meeting m WHERE m.counselor_seq = :userSeq", nativeQuery = true)
    public List<TeacherMasterMeetingInfoResponse> findByTeacherMasterSeq(@Param("userSeq") Long userSeq);

    public interface TeacherMasterMeetingInfoResponse {
        Long getMeeting_id();
        Long getParent_seq();
        Long getCounselor_seq();
        String getMeeting_code();
        String getAcademy_name();
        String getClass_name();
        String getMeeting_start_date();
        Integer getMeeting_start_hour();
        Integer getMeeting_start_minute();
        String getStudent_name();
        Long getStudent_id();
        Long getClass_id();
    }

    // 학부모 삭제 때 사용
    public List<Meeting> findByParentSeq(Long parentSeq);
}
