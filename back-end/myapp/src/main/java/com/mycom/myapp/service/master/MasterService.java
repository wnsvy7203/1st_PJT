package com.mycom.myapp.service.master;

import com.mycom.myapp.domain.Academy;
import com.mycom.myapp.domain.MasterSchedule;
import com.mycom.myapp.domain.TeacherSchedule;
import com.mycom.myapp.repository.AcademyRepository;
import com.mycom.myapp.repository.ClassRepository;
import com.mycom.myapp.repository.StudentRepository;
import com.mycom.myapp.repository.user.TeacherRepository;
import com.mycom.myapp.repository.user.UserRepository;

import java.util.List;

public interface MasterService {
    // 학원장 일정 등록
    public Long createMasterSchedule(Long masterSeq, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute);

    // 학원장 일정 조회
    public List<MasterSchedule> listMasterSchedule(Long masterSeq);

    // 학원장 일정 수정
    public Long changeMasterSchedule(Long scheduleId, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute);

    // 학원장 일정 삭제
    public void deleteMasterSchedule(Long scheduleId);

    // 학원장이 강사 등록 승인
    public Long approveTeacherApply(Long alertId);

    // 학원장이 강사 등록 거절
    public Long dismissTeacherApply(Long alertId);

    // 학원장이 학생 등록 승인
    public Long approveStudentApply(Long alertId);

    // 학원장이 학생 등록 거절
    public Long dismissStudentApply(Long alertId);

    // 학원장 Seq로 학원정보 받아오기
    public AcademyRepository.AcademyInfoResponse getAcademyInfo(Long masterSeq);

    // 학원ID로 반 목록 전체 받아오기
    public List<ClassRepository.ClassInfoResponse> getAcademyClassInfo(Long academyId);

    // 학원장 반 삭제
    public void deleteAcademyClass(Long classId);

    // 학원장 강사 삭제
    public void deleteTeacher(Long teacherSeq);

    // 학원장 학생 삭제
    public void deleteStudent(Long studentId);

    // 반 등록
    public Long createClass(Long academyId, Long teacherId, String className, String subjectCode, String gradeCode, String[] day, Integer[] startTimeHour, Integer[] startTimeMinute, Integer[] endTimeHour, Integer[] endTimeMinute);

    // 학원-학생 목록 조회
    public List<StudentRepository.StudentInfo> listAcademyStudents(Long academyId);

    // 학원-강사 목록(유저)
    public List<UserRepository.TeacherInfo> listTeacherInfo_user(Long academyId);

    // 학원-강사 목록(강사)
    public List<TeacherRepository.TeacherInfo> listTeacherInfo_teacher(Long academyId);

    // 특정 날짜 학원장 일정 조회
    public List<MasterSchedule> listMasterScheduleDate(Long masterSeq, String date);

    // 학원 정보 수정
    public Long updateAcademy(Long academyId, String academyName, String zipCode, String firstAddress, String lastAddress, String phoneNumber, String introduce);

    // 학원장이 반 강사 변경
    public Long changeClassTeacher(Long classId, Long teacherSeq);
}
