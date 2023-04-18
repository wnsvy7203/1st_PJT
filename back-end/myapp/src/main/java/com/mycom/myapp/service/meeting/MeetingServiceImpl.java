package com.mycom.myapp.service.meeting;

import com.mycom.myapp.domain.Alert;
import com.mycom.myapp.domain.MasterSchedule;
import com.mycom.myapp.domain.Meeting;
import com.mycom.myapp.domain.TeacherSchedule;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.domain.user.Parent;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.domain.user.User;
import com.mycom.myapp.repository.*;
import com.mycom.myapp.repository.user.MasterRepository;
import com.mycom.myapp.repository.user.ParentRepository;
import com.mycom.myapp.repository.user.TeacherRepository;
import com.mycom.myapp.repository.user.UserRepository;
import com.mycom.myapp.service.teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final AlertRepository alertRepository;
    private final MasterRepository masterRepository;
    private final TeacherRepository teacherRepository;
    private final MasterScheduleRepository masterScheduleRepository;
    private final TeacherScheduleRepository teacherScheduleRepository;
    private final ClassRepository classRepository;

    // 상담 등록
    @Override
    public Map<String, Long> createMeeting(Long parentSeq, Long counselorSeq, String meetingCode, String academyName, String className, String studentName, Integer startTimeHour, Integer startTimeMinute, String meetingStartDate, Long studentId, Long classId) {
        Parent parent = parentRepository.findById(parentSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Meeting meeting = Meeting.createMeeting(parent, counselorSeq, meetingCode, academyName, className, studentName, startTimeHour, startTimeMinute, meetingStartDate, studentId, classId);

        Long meetingId = meetingRepository.save(meeting).getId();
        User user = userRepository.findById(counselorSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Alert alert = Alert.createAlert(user, parentSeq, "A3", user.getName());

        Long alertId = alertRepository.save(alert).getId();

        String userCode = userRepository.findById(counselorSeq).get().getUserCode();

        Integer endTimeHour = startTimeHour;
        Integer endTimeMinute = startTimeMinute + 60;

        if (endTimeMinute >= 60) {
            endTimeHour++;
            endTimeMinute -= 60;
        }

        // 학원장인 경우
        if (userCode.equals("UM")) {
            Master master = masterRepository.findById(counselorSeq).get();
            MasterSchedule masterSchedule = MasterSchedule.createMasterSchedule(master, "SM", meeting.getMeetingStartDate(), startTimeHour, startTimeMinute, endTimeHour, endTimeMinute);
            masterScheduleRepository.save(masterSchedule);
        } else if (userCode.equals("UT")){
            // 강사인 경우
            Teacher teacher = teacherRepository.findById(counselorSeq).get();
            TeacherSchedule teacherSchedule = TeacherSchedule.createTeacherSchedule(teacher, "SM", meeting.getMeetingStartDate(), startTimeHour, startTimeMinute, endTimeHour, endTimeMinute);
            teacherScheduleRepository.save(teacherSchedule);
        }

        Map<String, Long> ret = new HashMap<>();
        ret.put("meetingId", meetingId);
        ret.put("alertId", alertId);

        return ret;
    }

    // 학부모 상담 목록 조회
    @Override
    public List<MeetingRepository.ParentMeetingInfoResponse> listPrentMeeting(Long parentSeq) {
        return meetingRepository.findByparentSeq(parentSeq);
    }

    // 강사, 학원장 상담 목록 조회
    @Override
    public List<MeetingRepository.TeacherMasterMeetingInfoResponse> listTeacherMasterMeeting(Long userSeq) {
        return meetingRepository.findByTeacherMasterSeq(userSeq);
    }

    // 상담 삭제
    @Override
    public void deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).get();

        Long counselorSeq = meeting.getCounselorSeq();
        String meetingDate = meeting.getMeetingStartDate();
        Integer meetingStartHour = meeting.getMeetingStartHour();
        Integer meetingStartMinute = meeting.getMeetingStartMinute();

        User user = userRepository.findById(counselorSeq).get();

        // 학원장
        if(user.getUserCode().equals("UM")) {
            masterScheduleRepository.deleteByMasterSeqAndScheduleDateAndStartTimeHourAndStartTimeMinute(counselorSeq, meetingDate, meetingStartHour, meetingStartMinute);
        } else if (user.getUserCode().equals("UT")) {
            // 강사
            teacherScheduleRepository.deleteByTeacherSeqAndScheduleDateAndStartTimeHourAndStartTimeMinute(counselorSeq, meetingDate, meetingStartHour, meetingStartMinute);
        }

        meetingRepository.deleteById(meetingId);
    }

    @Override
    public ClassRepository.MeetingInfo detailMeetingInfo(Long classId) {
        return classRepository.findByclassid(classId);
    }

    @Override
    public Long changeMeetingCode(Long meetingId, String meetingCode) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        meeting.setMeetingCode(meetingCode);

        return meetingId;
    }
}
