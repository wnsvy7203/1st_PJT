package com.mycom.myapp.service.meeting;

import com.mycom.myapp.domain.Meeting;
import com.mycom.myapp.repository.ClassRepository;
import com.mycom.myapp.repository.MeetingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MeetingService {

    // 상담 등록
    public Map<String, Long> createMeeting(Long parentSeq, Long counselorSeq, String meetingCode, String academyName, String className, String studentName, Integer startTimeHour, Integer startTimeMinute, String meetingStartDate, Long studentId, Long classId);

    // 학부모 상담 목록 조회
    public List<MeetingRepository.ParentMeetingInfoResponse> listPrentMeeting(Long parentSeq);

    // 강사, 학원장 상담 목록 조회
    public List<MeetingRepository.TeacherMasterMeetingInfoResponse> listTeacherMasterMeeting(Long userSeq);

    // 상담 삭제
    public void deleteMeeting(Long meetingId);

    // 반ID로 학원장Seq, 강사Seq, 학원이름, 반이름 받기
    public ClassRepository.MeetingInfo detailMeetingInfo(Long classId);

    // 상담 상태코드 변경
    public Long changeMeetingCode(Long meetingId, String meetingCode);
}
