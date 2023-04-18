package com.mycom.myapp.service.parent;

import com.mycom.myapp.domain.Attendancy;
import com.mycom.myapp.domain.Progress;
import com.mycom.myapp.domain.StudentTestInfo;
import com.mycom.myapp.repository.*;

import java.util.List;
import java.util.Map;

public interface ParentService {
    // 학생 등록
    public Long createStudent(Long parentSeq, String name, String school, String gradeCode, Integer age, String phoneNumber);

    // 학생을 학원에 등록 신청
    public Map<String, Long> applyStudentToAcademy(Long studentId, Long academyId);

    // 학부모-자녀 목록 조회
    public List<StudentRepository.StudentInfo> listParentStudents(Long parentSeq);

    // 학부모-자녀 상세 조회
    public StudentRepository.StudentInfo studentDetail(Long studentId);

    // 학부모-자녀 정보 수정
    public Long updateStudentInfo(Long studentId, String name, String school, String gradeCode, Integer age, String phoneNumber, String studentProfileImageUrl);

    // 학부모-자녀 삭제
    public void deleteStudent(Long studentId);

    // 학부모-자녀 학원 목록 조회
    public List<AcademyRepository.AcademyIdAndNameResponse> listStudentAcademy(Long studentId);

    // 학부모-자녀 학원 삭제
    public void deleteStudentAcademy(Long studentId, Long academyId);

    // 학부모-자녀 학원-반 전체 조회
    public List<ClassRepository.StudentClassResponse> listStudentClass(Long studentId);

    // 학부모-자녀 반 기본 정보 조회
    public ParentServiceImpl.ClassUnionResponse studentClassInfo(Long classId);

    // 학부모-자녀 출결 조회
    public List<Attendancy> listStudentAttendancy(Long classId, Long studentId);

    // 학부모-자녀 진도 전체 조회
    public List<Progress> listProgress(Long classId);

    // 학부모-자녀 시험 목록 조회
    public List<TestInfoRepository.ClassTestInfo> listClassTestInfo(Long classId, Integer testDate);

    // 학부모-자녀 시험 성적 조회
    public ParentServiceImpl.StudentTestScoreResponse listStudentTestInfo(Long studentId, Long testInfoId);

    // 반ID와 학생ID로 시험 정보 조회
    public List<TestInfoRepository.TestInfoClassIdAndStudentId> listTestInfo(Long classId, Long studentId);

    // test_info_id로 시험 정보 상세조회 및 성적 추이 가져오기
    public ParentServiceImpl.RetInfo listTestInfoAndTrends(Long classId, Long studentId, Long testInfoId);

    // 강사 Seq로 강사의 모든 반 시간 가져오기
    public List<ClassRepository.ClassScheduleInfo> listAllSchedule(Long teacherSeq);
}
