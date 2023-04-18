package com.mycom.myapp.service.teacher;

import com.mycom.myapp.domain.*;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.repository.*;
import com.mycom.myapp.repository.user.TeacherRepository;
import com.mycom.myapp.repository.user.UserRepository;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    // 강사 정보 등록
    public Long createTeacherInfo(Long teacherSeq, String introduce, String subjectCode);

    // 강사 정보 수정
    public Long changeTeacherInfo(Long teacherSeq, String introduce, String subjectCode);

    // 강사 정보 조회
    public Teacher detailTeacherInfo(Long teacherSeq);

    // 강사 근무 이력 등록
    public Long createWorkHistory(Long teacherSeq, String academyName, Integer startDateYear, Integer startDateMonth, Integer endDateYear, Integer endDateMonth);

    // 강사 근무 이력 조회
    public List<WorkHistory> listWorkHistory(Long teacherSeq);

    // 강사 근무 이력 수정
    public Long changeWorkHistory(Long historyId, String academyName, Integer startDateYear, Integer startDateMonth, Integer endDateYear, Integer endDateMonth);

    // 강사 근무 이력 삭제
    public void deleteWorkHistory(Long historyId);

    // 강사 일정 등록
    public Long createTeacherSchedule(Long teacherSeq, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute);

    // 강사 일정 조회
    public List<TeacherSchedule> listTeacherSchedule(Long teacherSeq);

    // 강사 일정 수정
    public Long changeTeacherSchedule(Long scheduleId, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute);

    // 강사 일정 삭제
    public void deleteTeacherSchedule(Long scheduleId);

    // 강사를 학원에 등록 신청
    public Map<String, Long> applyTeacherToAcademy(Long teacherSeq, Long academyId);

    // 강사-학원 테이블 리스트 조회
    public List<TeacherAcademy> listTeacherAcademy();

    // 강사-학원 테이블 상세 조회
    public TeacherAcademy detailTeacherAcademy(Long teacherAcademyId);

    // 강사-학원 삭제
    public void deleteTeacherAcademy(Long teacherSeq, Long academyId);

    // 진도 등록
    public Long createProgress(Long classId, Integer week, String content);

    // 진도 조회
    public List<Progress> listProgress(Long classId);

    // 진도 상세 조회
    public Progress detailProgress(Long progressId);

    // 진도 수정
    public Long changeProgress(Long progressId, Integer week, String contents);

    // 진도 삭제
    public Long deleteProgress(Long progressId);

    // 강사-학원 전체 조회
    public List<AcademyRepository.AcademyIdAndNameResponse> listAcademy(Long teacherSeq);

    // 강사-학원-반 전체 조회
    public List<ClassRepository.ClassInfoResponse> listTeacherClass(Long teacherSeq, Long academyId);

    // 출석 등록
    public void createAttendancy(Long classId, String attendancyDate, Long[] idList, String[] codeList);

    // 출석 수정
    public void changeAttendancy(Long classId, String attendancyDate, Long[] idList, String[] codeList);

    // 출석 조회(반-날짜별)
    public List<Attendancy> listAttendancyByDate(Long classId, String attendancyDate);

    // 출석 조회(반-학생별)
    public List<Attendancy> listAttendancyByStudent(Long classId, Long studentId);

    // 강사 - 학원 - 반정보 한번에 조회
    public List<ClassRepository.AcademyClassInfo> listAcademyClass(Long teacherSeq);

    // 특정 날짜 강사 일정 조회
    public List<TeacherSchedule> listTeacherScheduleDate(Long teacherSeq, String date);

    // 반에 학생 등록
    public void registClassStudent(Long classId, Long studentId);

    // 반에서 학생 삭제
    public void deleteStudent(Long classId, Long studentId);

    // 강사가 반 기본정보 조회
    public TeacherServiceImpl.ClassUnionResponse teacherClassInfo(Long classId);

    // 반 학생 ID 조회
    public List<StudentClassRepository.StudentId> listClassStudentId(Long classId);

    // 시험 성적 등록
    public Long createTestScore(Long classId, String testDate, String testName, Long[] studentId, Integer[] studentScore);

    // 반 시험 목록 조회
    public List<TestInfoRepository.ClassTestInfo> listClassTestInfo(Long classId, Integer testDate);

    // 시험ID로 학생 시험 정보 목록 받기
    public List<StudentTestInfoRepository.StudentTestInfo> listStudentTestInfo(Long testInfoId);

    // 시험 성적 수정
    public void changeStudentTestScore(Long testInfoId, Long[] studentId, Integer[] studentScore);

    // 강사 학생 정보 상세 조회
    public StudentRepository.StudentInfo studentDetail(Long studentId);

    // 강사 학생 시험 성적 목록 조회
    public List<TeacherServiceImpl.StudentTestScoreList> listStudentTestScore(Long studentId, Long classId);

    // 특이사항 등록
    public Long createNote(Long classId, Long studentId, String content);

    // 특이사항 삭제
    public void deleteNote(Long noteId);
}
