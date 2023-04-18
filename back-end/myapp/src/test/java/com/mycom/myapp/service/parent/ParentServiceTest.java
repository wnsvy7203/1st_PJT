package com.mycom.myapp.service.parent;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.Student;
import com.mycom.myapp.domain.TeacherAcademy;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.domain.user.Parent;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.repository.*;
import com.mycom.myapp.repository.user.MasterRepository;
import com.mycom.myapp.repository.user.ParentRepository;
import com.mycom.myapp.service.academy.AcademyService;
import com.mycom.myapp.service.admin.AdminService;
import com.mycom.myapp.service.master.MasterService;
import com.mycom.myapp.service.teacher.TeacherService;
import com.mycom.myapp.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ParentServiceTest {

    @Autowired
    ParentService parentService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ParentRepository parentRepository;
    @Autowired
    AcademyRepository academyRepository;
    @Autowired
    StudentAcademyRepository studentAcademyRepository;
    @Autowired
    MasterRepository masterRepository;
    @Autowired
    AlertRepository alertRepository;
    @Autowired
    UserService userService;
    @Autowired
    AcademyService academyService;
    @Autowired
    AdminService adminService;
    @Autowired
    MasterService masterService;
    @Autowired
    TeacherService teacherService;

    @Test
    public void 학부모_학생등록() {
        // given
        Parent parent = new Parent();
        parent.setUserId("parent");
        parent.setPassword("qwer1234!");
        parent.setName("PARENT");
        parent.setEmail("ssafy@parent.com");
        parent.setUserCode("UP");
        parent.setPhoneNumber("01011112222");
        parent.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        parent.setProfileImageUrl("abc1lkjadfglkasdfl0");

        // when
        userService.joinParent(parent);
        Long studentId = parentService.createStudent(parent.getSeq(), "김싸피", "싸피고", "H1", 17, "01012345678");
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("no such date"));

        // then
        assertEquals(student.getParent().getSeq(), parent.getSeq());
    }

    @Test
    public void applyStudentToAcademy() {
    }

    @Test
    public void 학부모_학생목록() {
        // given
        Parent parent = new Parent();
        parent.setUserId("parent");
        parent.setPassword("qwer1234!");
        parent.setName("PARENT");
        parent.setEmail("ssafy@parent.com");
        parent.setUserCode("UP");
        parent.setPhoneNumber("01011112222");
        parent.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        parent.setProfileImageUrl("abc1lkjadfglkasdfl0");

        // when
        userService.joinParent(parent);
        parentService.createStudent(parent.getSeq(), "김싸피", "싸피고", "H1", 17, "01012345678");
        parentService.createStudent(parent.getSeq(), "반싸피", "범어고", "H2", 18, "01012345678");
        parentService.createStudent(parent.getSeq(), "장싸피", "부산외고", "H3", 19, "01012345678");

        // given
        List<StudentRepository.StudentInfo> list = parentService.listParentStudents(parent.getSeq());

        assertEquals(list.size(), 3);
        assertEquals(list.get(0).getStudent_name(), "김싸피");
    }

    @Test
    @Rollback(value = false)
    public void 학부모_학생_상세조회() {
        // given
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

        Long studentId1 = parentService.createStudent(parent.getSeq(), "김싸피1", "싸피고", "H1", 17, "01012345678");
        Long studentId2 = parentService.createStudent(parent.getSeq(), "김싸피2", "싸피고", "H2", 18, "01012345678");

        StudentRepository.StudentInfo info1 = parentService.studentDetail(studentId1);
        StudentRepository.StudentInfo info2 = parentService.studentDetail(studentId2);

        assertEquals(info1.getStudent_name(), "김싸피1");
        assertEquals(info2.getPriority(), 2);
        assertEquals(info2.getGrade_code(), "H2");
    }

    @Test
    public void 학부모_자녀학원목록조회() {
        // given
        // 자녀 등록
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

        Long studentId = parentService.createStudent(parent.getSeq(), "김싸피", "싸피고", "H1", 17, "01012345678");
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("no such date"));

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
        Long academyId1 = adminService.createAcademy(master.getSeq(), "싸피학원", "11111", "서울특별시 강남구", "역삼동", "01000000000", "안녕하세요. ");
        Long academyId2 = adminService.createAcademy(master.getSeq(), "메가학원", "11111", "서울특별시 강남구", "역삼동", "01000000000", "안녕하세요. ");

        // 학생-학원 등록 신청
        Long alertId1 = parentService.applyStudentToAcademy(studentId, academyId1).get("alertId");
        Long alertId2 = parentService.applyStudentToAcademy(studentId, academyId2).get("alertId");

        // 학원장-학생 등록 승인
        masterService.approveStudentApply(alertId1);
        masterService.approveStudentApply(alertId2);

        // when
        List<AcademyRepository.AcademyIdAndNameResponse> list = parentService.listStudentAcademy(studentId);

        // then
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getAcademy_id(), academyId1);
    }

    @Test
    public void 학부모_자녀_학원_반_전체조회() {
        // given
        // 자녀 등록
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

        Long studentId = parentService.createStudent(parent.getSeq(), "김싸피", "싸피고", "H1", 17, "01012345678");
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("no such date"));

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
        Long classId1 = masterService.createClass(academyId1, teacher.getSeq(), "1반", "EN", "H1");
        Long classId2 = masterService.createClass(academyId1, teacher.getSeq(), "2반", "MA", "E1");

        // 학생-학원 등록 신청
        Long alertId3 = parentService.applyStudentToAcademy(studentId, academyId1).get("alertId");
        Long alertId4 = parentService.applyStudentToAcademy(studentId, academyId2).get("alertId");

        // 학원장-학생 등록 승인
        masterService.approveStudentApply(alertId3);
        masterService.approveStudentApply(alertId4);

        // 강사-학생 반 등록
        teacherService.registClassStudent(classId1, studentId);
        teacherService.registClassStudent(classId2, studentId);

        // 학부모-자녀 학원-반 목록 전체 조회
        List<ClassRepository.ClassInfoResponse> list = parentService.listStudentClass(studentId, academyId1);

        assertEquals(list.size(), 2);
    }
}