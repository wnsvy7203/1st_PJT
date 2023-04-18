package com.mycom.myapp.service.teacher;

import com.mycom.myapp.controller.TeacherController;
import com.mycom.myapp.domain.*;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.domain.user.Parent;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.repository.AcademyRepository;
import com.mycom.myapp.repository.AttendancyRepository;
import com.mycom.myapp.repository.ClassRepository;
import com.mycom.myapp.repository.user.TeacherRepository;
import com.mycom.myapp.repository.user.UserRepository;
import com.mycom.myapp.service.academy.AcademyService;
import com.mycom.myapp.service.admin.AdminService;
import com.mycom.myapp.service.master.MasterService;
import com.mycom.myapp.service.parent.ParentService;
import com.mycom.myapp.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeacherServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    MasterService masterService;
    @Autowired
    AdminService adminService;
    @Autowired
    ParentService parentService;
    @Autowired
    AttendancyRepository attendancyRepository;

    @Test
    public void 강사_정보등록() {
        // given
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
        Long seq = teacherService.createTeacherInfo(teacherSeq, "안녕하세요. 김강사입니다. ", "KO");

        // then
        assertEquals(seq, teacher.getSeq());
    }

    @Test
    public void 강사_학원목록조회() {
        // given
        Master master = new Master();
        master.setUserId("master");
        master.setPassword("qwer1234!");
        master.setName("MASTER");
        master.setEmail("ssafy@master.com");
        master.setUserCode("UM");
        master.setPhoneNumber("01011112222");
        master.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        master.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Teacher teacher = new Teacher();
        teacher.setUserId("teacher");
        teacher.setPassword("qwer1234!");
        teacher.setName("TEACHER");
        teacher.setEmail("ssafy@teacher.com");
        teacher.setUserCode("UT");
        teacher.setPhoneNumber("01011112222");
        teacher.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        teacher.setProfileImageUrl("abc1lkjadfglkasdfl0");

        userService.joinMaster(master);
        Long teacherSeq = userService.joinTeacher(teacher);
        Long academyId1 = adminService.createAcademy(master.getSeq(), "싸피학원", "11111", "서울특별시 강남구", "역삼동", "01000000000", "안녕하세요. ");
        Long academyId2 = adminService.createAcademy(master.getSeq(), "메가학원", "11111", "서울특별시 강남구", "역삼동", "01000000000", "안녕하세요. ");

        // 강사-학원 등록 신청
        Long alertId1 = teacherService.applyTeacherToAcademy(teacherSeq, academyId1).get("alertId");
        Long alertId2 = teacherService.applyTeacherToAcademy(teacherSeq, academyId2).get("alertId");

        // 학원장-강사 등록 승인
        masterService.approveTeacherApply(alertId1);
        masterService.approveTeacherApply(alertId2);

        // when 
        List<TeacherAcademy> list = teacherService.listTeacherAcademy();

        // then
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getAcademy().getId(), academyId1);
    }

    @Test
    public void 학원_강사전체조회() {
        // given
        Teacher teacher1 = new Teacher();
        teacher1.setUserId("teacher1");
        teacher1.setPassword("qwer1234!");
        teacher1.setName("teacher1");
        teacher1.setEmail("ssafy@teacher1.com");
        teacher1.setUserCode("UT");
        teacher1.setPhoneNumber("01011112222");
        teacher1.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        teacher1.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long teacherSeq1 = userService.joinTeacher(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setUserId("teacher2");
        teacher2.setPassword("qwer1234!");
        teacher2.setName("teacher2");
        teacher2.setEmail("ssafy@teacher2.com");
        teacher2.setUserCode("UT");
        teacher2.setPhoneNumber("01011112222");
        teacher2.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        teacher2.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long teacherSeq2 = userService.joinTeacher(teacher2);

        Master master = new Master();
        master.setUserId("master");
        master.setPassword("qwer1234!");
        master.setName("MASTER");
        master.setEmail("ssafy@master.com");
        master.setUserCode("UM");
        master.setPhoneNumber("01011112222");
        master.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        master.setProfileImageUrl("abc1lkjadfglkasdfl0");

        userService.joinMaster(master);

        // 강사 정보 등록
        teacherService.createTeacherInfo(teacherSeq1, "안녕하세요. 김강사입니다. ", "KO");
        teacherService.createTeacherInfo(teacherSeq2, "하이루. 반강사입니다. ", "MA");

        // 학원장-학원 등록
        Long academyId = adminService.createAcademy(master.getSeq(), "싸피학원", "11111", "서울특별시 강남구", "역삼동", "01000000000", "안녕하세요. ");
        // 강사-학원 등록 신청
        Long alertId1 = teacherService.applyTeacherToAcademy(teacherSeq1, academyId).get("alertId");
        Long alertId2 = teacherService.applyTeacherToAcademy(teacherSeq2, academyId).get("alertId");
        // 학원장-강사 등록 승인
        masterService.approveTeacherApply(alertId1);
        masterService.approveTeacherApply(alertId2);

        // when
        List<UserRepository.TeacherInfo> list = masterService.listTeacherInfo_user(academyId);
        List<TeacherRepository.TeacherInfo> list1 = masterService.listTeacherInfo_teacher(academyId);

        List<FinalTeacherInfo> list2 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Long teacherSeq = list.get(i).getUser_seq();
            String teacherName = list.get(i).getUser_name();
            String teacherPhoneNumber = list.get(i).getUser_phone_number();
            String subjectCode = list1.get(i).getSubject_code();

            list2.add(new FinalTeacherInfo(teacherSeq, teacherName, teacherPhoneNumber, subjectCode));
        }

        // then
        assertEquals(list2.size(), 2);
        assertEquals(list2.get(0).teacherName, teacher1.getName());
    }

    static class FinalTeacherInfo {
        private Long teacherSeq;
        private String teacherName;
        private String teacherPhoneNumber;
        private String subjectCode;

        public FinalTeacherInfo(Long teacherSeq, String teacherName, String teacherPhoneNumber, String subjectCode) {
            this.teacherSeq = teacherSeq;
            this.teacherName = teacherName;
            this.teacherPhoneNumber = teacherPhoneNumber;
            this.subjectCode = subjectCode;
        }
    }

    @Test
    public void 강사_학원_반_전체조회() {
        // given
        Master master = new Master();
        master.setUserId("master");
        master.setPassword("qwer1234!");
        master.setName("MASTER");
        master.setEmail("ssafy@master.com");
        master.setUserCode("UM");
        master.setPhoneNumber("01011112222");
        master.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        master.setProfileImageUrl("abc1lkjadfglkasdfl0");

        userService.joinMaster(master);

        Teacher teacher = new Teacher();
        teacher.setUserId("teacher");
        teacher.setPassword("qwer1234!");
        teacher.setName("TEACHER");
        teacher.setEmail("ssafy@teacher.com");
        teacher.setUserCode("UT");
        teacher.setPhoneNumber("01011112222");
        teacher.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        teacher.setProfileImageUrl("abc1lkjadfglkasdfl0");

        userService.joinTeacher(teacher);

        // 강사 정보 등록
        teacherService.createTeacherInfo(teacher.getSeq(), "안녕하세요. 김강사입니다. ", "KO");

        // 학원장-학원 등록
        Long academyId1 = adminService.createAcademy(master.getSeq(), "싸피학원", "11111", "서울특별시 강남구", "역삼동", "01000000000", "안녕하세요. ");
        Long academyId2 = adminService.createAcademy(master.getSeq(), "싸피학원2222", "12345", "부산광역시 강서구", "송정동", "01000000000", "안녕하세요. ");

        // 강사-학원 등록 신청
        Long alertId1 = teacherService.applyTeacherToAcademy(teacher.getSeq(), academyId1).get("alertId");
        Long alertId2 = teacherService.applyTeacherToAcademy(teacher.getSeq(), academyId2).get("alertId");
        // 학원장-강사 등록 승인
        masterService.approveTeacherApply(alertId1);
        masterService.approveTeacherApply(alertId2);

        // 학원장 반 생성
        masterService.createClass(academyId1, teacher.getSeq(), "1반", "EN", "H1");
        masterService.createClass(academyId1, teacher.getSeq(), "2반", "MA", "E1");

        masterService.createClass(academyId2, teacher.getSeq(), "1반", "KO", "M1");
        masterService.createClass(academyId2, teacher.getSeq(), "3반", "SC", "M2");

        // 강사-학원-반 목록 전체 조회
        List<ClassRepository.AcademyClassInfo> list = teacherService.listAcademyClass(teacher.getSeq());

        assertEquals(list.size(), 4);
        assertEquals(list.get(0).getAcademy_id(), academyId1);
        assertEquals(list.get(0).getAcademy_name(), "싸피학원");
    }

    @Test
    public void 특정날짜_강사_일정조회() {

        //강사 회원가입
        Teacher teacher = new Teacher();
        teacher.setUserId("teacher");
        teacher.setPassword("qwer1234!");
        teacher.setName("TEACHER");
        teacher.setEmail("ssafy@teacher.com");
        teacher.setUserCode("UT");
        teacher.setPhoneNumber("01011112222");
        teacher.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        teacher.setProfileImageUrl("abc1lkjadfglkasdfl0");

        userService.joinTeacher(teacher);

        // 강사 일정 등록
        teacherService.createTeacherSchedule(teacher.getSeq(), "S1", "20220101", 10, 00, 11, 20);
        teacherService.createTeacherSchedule(teacher.getSeq(), "S2", "20220101", 10, 00, 11, 20);
        teacherService.createTeacherSchedule(teacher.getSeq(), "S2", "20230103", 10, 00, 11, 20);
        teacherService.createTeacherSchedule(teacher.getSeq(), "S3", "20230103", 10, 00, 11, 20);
        teacherService.createTeacherSchedule(teacher.getSeq(), "S1", "20230202", 10, 00, 11, 20);

        List<TeacherSchedule> list1 = teacherService.listTeacherScheduleDate(teacher.getSeq(), "20220101");
        List<TeacherSchedule> list2 = teacherService.listTeacherScheduleDate(teacher.getSeq(), "20230103");
        List<TeacherSchedule> list3 = teacherService.listTeacherScheduleDate(teacher.getSeq(), "20230202");

        assertEquals(list1.size(), 2);
        assertEquals(list2.size(), 2);
        assertEquals(list3.size(), 1);
    }

    @Test
    @Rollback(value = false)
    public void 출석() {
        // given
        // 학원장 등록
        Master master = new Master();
        master.setUserId("master");
        master.setPassword("qwer1234!");
        master.setName("MASTER");
        master.setEmail("ssafy@master.com");
        master.setUserCode("UM");
        master.setPhoneNumber("01011112222");
        master.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        master.setProfileImageUrl("abc1lkjadfglkasdfl0");

        userService.joinMaster(master);

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

        userService.joinTeacher(teacher);

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

        userService.joinParent(parent);

        // 학부모 자녀 등록
        Long studentId1 = parentService.createStudent(parent.getSeq(), "김싸피", "싸피고", "H1", 17, "01012345678");
        Long studentId2 = parentService.createStudent(parent.getSeq(), "반싸피", "범어고", "H2", 18, "01012345678");
        Long studentId3 = parentService.createStudent(parent.getSeq(), "장싸피", "부산외고", "H3", 19, "01012345678");

        // 강사 정보 등록
        teacherService.createTeacherInfo(teacher.getSeq(), "안녕하세요. 김강사입니다. ", "KO");

        // 학원장-학원 등록
        Long academyId = adminService.createAcademy(master.getSeq(), "싸피학원", "11111", "서울특별시 강남구", "역삼동", "01000000000", "안녕하세요. ");

        // 강사-학원 등록 신청
        Long alertId = teacherService.applyTeacherToAcademy(teacher.getSeq(), academyId).get("alertId");

        // 학원장-강사 등록 승인
        masterService.approveTeacherApply(alertId);

        // 학원장 반 생성
        Long classId = masterService.createClass(academyId, teacher.getSeq(), "1반", "EN", "H1");

        // 학생을 학원에 등록 신청
        Long alertId4 = parentService.applyStudentToAcademy(studentId1, academyId).get("alertId");
        Long alertId5 = parentService.applyStudentToAcademy(studentId2, academyId).get("alertId");
        Long alertId6 = parentService.applyStudentToAcademy(studentId3, academyId).get("alertId");

        // 등록 승인
        masterService.approveStudentApply(alertId4);
        masterService.approveStudentApply(alertId5);
        masterService.approveStudentApply(alertId6);

        // 반에 학생을 넣음
        teacherService.registClassStudent(classId, studentId1);
        teacherService.registClassStudent(classId, studentId2);
        teacherService.registClassStudent(classId, studentId3);

        // 출석 등록
        Long[] idList = {studentId1, studentId2, studentId3};
        String[] codeList = {"AA", "AA", "AB"};
        teacherService.createAttendancy(classId, "20220101", idList, codeList);

        List<Attendancy> attendancies = attendancyRepository.findByattendancyDate("20220101");

        assertEquals(attendancies.size(), 3);
        assertEquals(attendancies.get(0).getAttendancyDate(), "20220101");

        // 출석 변경
        String[] codeChangedList = {"AA", "AA", "AA"};
        teacherService.changeAttendancy(classId, "20220101", idList, codeChangedList);

        List<Attendancy> attendancies1 = attendancyRepository.findByattendancyDate("20220101");

        assertEquals(attendancies1.get(0).getAttendancyCode(), "AA");
        assertEquals(attendancies1.get(1).getAttendancyCode(), "AA");
        assertEquals(attendancies1.get(2).getAttendancyCode(), "AA");

        // 출석 조회(반-날짜별)
        List<Attendancy> list = teacherService.listAttendancyByDate(classId, "20220101");

        assertEquals(list.size(), 3);
        assertEquals(list.get(0).getAttendancyDate(), "20220101");
    }
}