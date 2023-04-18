package com.mycom.myapp.service.parent;

import com.mycom.myapp.domain.*;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.domain.user.Parent;
import com.mycom.myapp.domain.user.User;
import com.mycom.myapp.repository.*;
import com.mycom.myapp.repository.user.MasterRepository;
import com.mycom.myapp.repository.user.ParentRepository;
import com.mycom.myapp.repository.user.UserRepository;
import com.mycom.myapp.service.teacher.TeacherServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final AcademyRepository academyRepository;
    private final StudentAcademyRepository studentAcademyRepository;
    private final MasterRepository masterRepository;
    private final AlertRepository alertRepository;
    private final ClassRepository classRepository;
    private final AttendancyRepository attendancyRepository;
    private final ProgressRepository progressRepository;
    private final TestInfoRepository testInfoRepository;
    private final StudentTestInfoRepository studentTestInfoRepository;
    private final UserRepository userRepository;

    // 학생 등록
    @Override
    public Long createStudent(Long parentSeq, String name, String school, String gradeCode, Integer age, String phoneNumber) {
        Parent parent = parentRepository.findById(parentSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Student student = Student.createStudent(parent, name, school, gradeCode, age, phoneNumber, parent.getStudents().size() + 1);
        Long studentId = studentRepository.save(student).getId();

        return studentId;
    }

    // 학생을 학원에 등록 신청
    @Override
    public Map<String, Long> applyStudentToAcademy(Long studentId, Long academyId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new IllegalArgumentException("no such data"));
        Academy academy = academyRepository.findById(academyId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        if(studentAcademyRepository.findByStudentIdAndAcademyId(studentId, academyId).isPresent()) {
            return null;
        }

        StudentAcademy studentAcademy = StudentAcademy.createStudentAcademy(student, academy);

        Long studentAcademyId = studentAcademyRepository.save(studentAcademy).getId();

        Long masterSeq = academy.getMaster().getSeq();
        Master master = masterRepository.findById(masterSeq).orElseThrow(() -> new IllegalArgumentException("no such date"));

        String userName = userRepository.findUsernameBystudentId(studentId);

        Alert alert = Alert.createAlert(master, studentAcademyId, "A2", userName);

        Long alertId = alertRepository.save(alert).getId();

        Map<String, Long> ret = new HashMap<>();
        ret.put("studentAcademyId", studentAcademyId);
        ret.put("alertId", alertId);

        return ret;
    }

    // 학부모-자녀 전체 조회
    @Override
    public List<StudentRepository.StudentInfo> listParentStudents(Long parentSeq) {
        return studentRepository.findByParentSeq(parentSeq);
    }

    // 학부모-자녀 상세 조회
    @Override
    public StudentRepository.StudentInfo studentDetail(Long studentId) {
        return studentRepository.findBystudentId(studentId);
    }

    // 학부모-자녀 정보 수정
    @Override
    public Long updateStudentInfo(Long studentId, String name, String school, String gradeCode, Integer age, String phoneNumber, String studentProfileImageUrl) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        student.setName(name);
        student.setSchool(school);
        student.setGradeCode(gradeCode);
        student.setAge(age);
        student.setPhoneNumber(phoneNumber);
        student.setStudentProfileImageUrl(studentProfileImageUrl);

        return studentId;
    }

    // 학부모-자녀 삭제
    @Override
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    // 학부모-자녀 학원 조회
    @Override
    public List<AcademyRepository.AcademyIdAndNameResponse> listStudentAcademy(Long studentId) {
        return academyRepository.findByAcademyAndStudent(studentId);
    }

    // 학부모-자녀 학원 삭제
    @Override
    public void deleteStudentAcademy(Long studentId, Long academyId) {
        studentAcademyRepository.deleteByStudentIdAndAcademyId(studentId, academyId);
    }

    // 학부모-자녀 학원-반 전체 조회
    @Override
    public List<ClassRepository.StudentClassResponse> listStudentClass(Long studentId) {
        return classRepository.findByStudentIdAndAcademyid(studentId);
    }

    // 학부모-자녀 반 기본 정보 조회
    @Override
    public ClassUnionResponse studentClassInfo(Long classId) {
        List<ClassRepository.TeacherClassInfo> teacherClassInfo = classRepository.findByclassId(classId);

        UserRepository.TeacherDeatilInfo teacherDeatilInfo = userRepository.findByteacherSeq(teacherClassInfo.get(0).getTeacher_seq());

        return new ClassUnionResponse(teacherClassInfo, teacherDeatilInfo);
    }

    @Data
    @AllArgsConstructor
    public class ClassUnionResponse {
        private List<ClassRepository.TeacherClassInfo> classInfo;
        private UserRepository.TeacherDeatilInfo teacherInfo;
    }

    // 학부모-자녀 출결 조회
    @Override
    public List<Attendancy> listStudentAttendancy(Long classId, Long studentId) {
        return attendancyRepository.findByclassIdAndStudentId(classId, studentId);
    }

    // 학부모-자녀 진도 전체 조회
    @Override
    public List<Progress> listProgress(Long classId) {
        return progressRepository.findAllByClasId(classId);
    }

    // 학부모-자녀 시험 목록 조회
    @Override
    public List<TestInfoRepository.ClassTestInfo> listClassTestInfo(Long classId, Integer testDate) {
        return testInfoRepository.findByclassId(classId, testDate);
    }

    // 학부모-자녀 시험 성적 조회 (시험 ID로 학생시험정보 받기)
    @Override
    public StudentTestScoreResponse listStudentTestInfo(Long studentId, Long testInfoId) {
        List<StudentTestInfoRepository.StudentTestInfo> list = studentTestInfoRepository.findBytestInfoId(testInfoId);

        list.sort((l1, l2) -> (l2.getScore() - l1.getScore()));

        Integer sum = 0;
        Integer score = 0;
        Integer rank = 0;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getStudent_id().equals(studentId)) {
                score = list.get(i).getScore();
                rank = i + 1;
            }

            sum += list.get(i).getScore();
        }

        Integer totalCnt = list.size();
        String avg = String.format("%.1f", (double) sum / totalCnt);

        StudentTestScoreResponse response = new StudentTestScoreResponse(score, avg, rank, totalCnt);

        return response;
    }

    @Override
    public List<TestInfoRepository.TestInfoClassIdAndStudentId> listTestInfo(Long classId, Long studentId) {
        return testInfoRepository.findbyclassIdAndstudentId(classId, studentId);
    }

    @Override
    public RetInfo listTestInfoAndTrends(Long classId, Long studentId, Long testInfoId) {
        // 1.
        Double myAvg = testInfoRepository.findaveragescore(testInfoId);
        Integer myRank = 0;
        Integer myScore = 0;

        List<StudentTestInfoRepository.TestScoreRank> ranks = studentTestInfoRepository.findTestScoreRank(testInfoId);

        // 순위 구하기
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i).getStudent_id().equals(studentId)) {
                myRank = i + 1;
                myScore = ranks.get(i).getScore();
                break;
            }
        }

        // 전체 응시 인원 가져오기
        Integer totalCnt = studentTestInfoRepository.findtotalcnt(testInfoId);

        MyTestInfo myTestInfo = new MyTestInfo();

        myTestInfo.score = myScore;
        myTestInfo.average = myAvg;
        myTestInfo.rank = myRank;
        myTestInfo.totalCnt = totalCnt;

        // 2.
        List<TestInfoRepository.GetId> testInfoIds = testInfoRepository.findallTestInfo(classId, studentId);

        // 3.
        List<MyTestTrend> myTestTrends = new ArrayList<>();

        for (TestInfoRepository.GetId getId : testInfoIds) {
            Double average = testInfoRepository.findaveragescore(getId.getTest_info_id());
            Integer rank = 0;
            Integer score = 0;

            List<StudentTestInfoRepository.TestScoreRank> foundRanks = studentTestInfoRepository.findTestScoreRank(getId.getTest_info_id());

            for (int i = 0; i < foundRanks.size(); i++) {
                if (foundRanks.get(i).getStudent_id().equals(studentId)) {
                    rank = i + 1;
                    score = ranks.get(i).getScore();
                    break;
                }
            }

            Integer tCnt = studentTestInfoRepository.findtotalcnt(testInfoId);

            MyTestTrend myTestTrend = new MyTestTrend();

            myTestTrend.score = score;
            myTestTrend.average = average;
            myTestTrend.rank = rank;
            myTestTrend.testDate = getId.getTest_date();
            myTestTrend.totalCnt = tCnt;
            myTestTrend.testName = getId.getTest_info_name();

            myTestTrends.add(myTestTrend);
        }

        RetInfo retInfo = new RetInfo();

        retInfo.myTestInfo = myTestInfo;
        retInfo.myTestTrend = myTestTrends;

        return retInfo;
    }

    @Override
    public List<ClassRepository.ClassScheduleInfo> listAllSchedule(Long teacherSeq) {
        return classRepository.findAllScheduleByTeacherSeq(teacherSeq);
    }

    @Data
    public class MyTestInfo {
        private Integer score;
        private Double average;
        private Integer rank;
        private Integer totalCnt;
    }

    @Data
    public class MyTestTrend {
        private String testDate;
        private Integer score;
        private Double average;
        private Integer rank;
        private Integer totalCnt;
        private String testName;
    }

    @Data
    public class RetInfo {
        private MyTestInfo myTestInfo;
        private List<MyTestTrend> myTestTrend;
    }

    @Data
    @AllArgsConstructor
    public class StudentTestScoreResponse {
        private Integer score;
        private String avg;
        private Integer rank;
        private Integer totalCnt;
    }
}
