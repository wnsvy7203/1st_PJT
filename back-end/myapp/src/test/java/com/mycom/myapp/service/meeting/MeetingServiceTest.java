package com.mycom.myapp.service.meeting;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.Alert;
import com.mycom.myapp.domain.Meeting;
import com.mycom.myapp.domain.user.Parent;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.repository.AlertRepository;
import com.mycom.myapp.repository.MeetingRepository;
import com.mycom.myapp.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MeetingServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    MeetingService meetingService;
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    AlertRepository alertRepository;

    @Test
    @Rollback(value = false)
    public void 상담등록() {
        // given
        // 학부모 등록
        Parent parent = new Parent();
        parent.setUserId("parent");
        parent.setPassword("qwer1234!");
        parent.setName("PARENT");
        parent.setEmail("ssafy@parent.com");
        parent.setUserCode("UP");
        parent.setPhoneNumber("01011112222");
        parent.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        parent.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long parentSeq = userService.joinParent(parent);

        // 강사 등록
        Teacher teacher = new Teacher();
        teacher.setUserId("teacher");
        teacher.setPassword("qwer1234!");
        teacher.setName("TEACHER");
        teacher.setEmail("ssafy@teacher.com");
        teacher.setUserCode("UT");
        teacher.setPhoneNumber("01011112222");
        teacher.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        teacher.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long teacherSeq = userService.joinTeacher(teacher);

        // when
        Map<String, Long> res = meetingService.createMeeting(parentSeq, teacherSeq, "MW", "싸피학원", "1반", "2022-01-12 13:02:03", "이자녀");
        Long meetingId = res.get("meetingId");
        Long alertId = res.get("alertId");

        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        // then
        assertEquals(meeting.getParent().getName(), "PARENT");
        assertEquals(alert.getAlertCode(), "A3");
    }

    @Test
    public void 학부모_상담목록조회() {
        // given
        // 학부모 등록
        Parent parent = new Parent();
        parent.setUserId("parent");
        parent.setPassword("qwer1234!");
        parent.setName("PARENT");
        parent.setEmail("ssafy@parent.com");
        parent.setUserCode("UP");
        parent.setPhoneNumber("01011112222");
        parent.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        parent.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long parentSeq = userService.joinParent(parent);

        // 강사 등록
        Teacher teacher = new Teacher();
        teacher.setUserId("teacher");
        teacher.setPassword("qwer1234!");
        teacher.setName("TEACHER");
        teacher.setEmail("ssafy@teacher.com");
        teacher.setUserCode("UT");
        teacher.setPhoneNumber("01011112222");
        teacher.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        teacher.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long teacherSeq = userService.joinTeacher(teacher);

        // 상담 등록
        meetingService.createMeeting(parentSeq, teacherSeq, "MC", "싸피학원", "1반", "2022-01-12 13:02:03", "이자녀");
        meetingService.createMeeting(parentSeq, teacherSeq, "MW", "메가학원", "1반", "2022-03-16 23:02:03", "김자녀");

        // when
        List<MeetingRepository.ParentMeetingInfoResponse> list = meetingService.listPrentMeeting(parentSeq);

        // then
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getMeeting_code(), "MC");
    }

    @Test
    public void 강사학원장_상담목록조회() {
        // given
        // 학부모 등록
        Parent parent = new Parent();
        parent.setUserId("parent");
        parent.setPassword("qwer1234!");
        parent.setName("PARENT");
        parent.setEmail("ssafy@parent.com");
        parent.setUserCode("UP");
        parent.setPhoneNumber("01011112222");
        parent.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        parent.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long parentSeq = userService.joinParent(parent);

        // 강사 등록
        Teacher teacher = new Teacher();
        teacher.setUserId("teacher");
        teacher.setPassword("qwer1234!");
        teacher.setName("TEACHER");
        teacher.setEmail("ssafy@teacher.com");
        teacher.setUserCode("UT");
        teacher.setPhoneNumber("01011112222");
        teacher.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        teacher.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long teacherSeq = userService.joinTeacher(teacher);

        // 상담 등록
        meetingService.createMeeting(parentSeq, teacherSeq, "MC", "싸피학원", "1반", "2022-01-12 13:02:03", "이자녀");
        meetingService.createMeeting(parentSeq, teacherSeq, "MW", "메가학원", "1반", "2022-03-16 23:02:03", "김자녀");

        // when
        List<MeetingRepository.TeacherMasterMeetingInfoResponse> list = meetingService.listTeacherMasterMeeting(teacherSeq);

        // then
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getMeeting_code(), "MC");
    }
}