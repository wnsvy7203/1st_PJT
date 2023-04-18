package com.mycom.myapp.service.teacher;

import com.mycom.myapp.domain.*;
import com.mycom.myapp.domain.Class;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.domain.user.User;
import com.mycom.myapp.repository.*;
import com.mycom.myapp.repository.user.MasterRepository;
import com.mycom.myapp.repository.user.TeacherRepository;
import com.mycom.myapp.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final WorkhistoryRepository workhistoryRepository;
    private final TeacherScheduleRepository teacherScheduleRepository;
    private final AcademyRepository academyRepository;
    private final TeacherAcademyRepository teacherAcademyRepository;
    private final MasterRepository masterRepository;
    private final AlertRepository alertRepository;
    private final ClassRepository classRepository;
    private final ProgressRepository progressRepository;
    private final StudentRepository studentRepository;
    private final AttendancyRepository attendancyRepository;
    private final StudentClassRepository studentClassRepository;
    private final TestInfoRepository testInfoRepository;
    private final StudentTestInfoRepository studentTestInfoRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    // 강사 정보 등록
    @Override
    public Long createTeacherInfo(Long teacherSeq, String introduce, String subjectCode) {
        Teacher teacher = teacherRepository.findById(teacherSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        teacher.setTeacherIntroduce(introduce);
        teacher.setSubjectCode(subjectCode);

        return teacher.getSeq();
    }

    // 강사 정보 수정
    @Override
    public Long changeTeacherInfo(Long teacherSeq, String introduce, String subjectCode) {
        Teacher teacher = teacherRepository.findById(teacherSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        teacher.setTeacherIntroduce(introduce);
        teacher.setSubjectCode(subjectCode);

        return teacher.getSeq();
    }

    @Override
    public Teacher detailTeacherInfo(Long teacherSeq) {
        return teacherRepository.findById(teacherSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    // 강사 근무 이력 등록
    @Override
    public Long createWorkHistory(Long teacherSeq, String academyName, Integer startDateYear, Integer startDateMonth, Integer endDateYear, Integer endDateMonth) {
        Teacher teacher = teacherRepository.findById(teacherSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        WorkHistory workHistory = WorkHistory.createWorkHistory(teacher, academyName, startDateYear, startDateMonth, endDateYear, endDateMonth);

        return workhistoryRepository.save(workHistory).getId();
    }

    // 강사 근무 이력 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<WorkHistory> listWorkHistory(Long teacherSeq) {
        return workhistoryRepository.findByTeacherSeq(teacherSeq);
    }

    // 강사 근무 이력 수정
    @Override
    public Long changeWorkHistory(Long historyId, String academyName, Integer startDateYear, Integer startDateMonth, Integer endDateYear, Integer endDateMonth) {
        WorkHistory workHistory = workhistoryRepository.findById(historyId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        workHistory.setAcademyName(academyName);
        workHistory.setStartDateYear(startDateYear);
        workHistory.setStartDateMonth(startDateMonth);
        workHistory.setEndDateYear(endDateYear);
        workHistory.setEndDateMonth(endDateMonth);

        return workHistory.getId();
    }

    // 강사 근무 이력 삭제
    @Override
    public void deleteWorkHistory(Long historyId) {
        workhistoryRepository.deleteById(historyId);
    }

    // 강사 일정 등록
    @Override
    public Long createTeacherSchedule(Long teacherSeq, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute) {
        Teacher teacher = teacherRepository.findById(teacherSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        TeacherSchedule teacherSchedule = TeacherSchedule.createTeacherSchedule(teacher, scheduleCode, scheduleDate, startTimeHour, startTimeMinute, endTimeHour, endTimeMinute);

        return teacherScheduleRepository.save(teacherSchedule).getId();
    }

    // 강사 일정 조회
    @Override
    @Transactional(readOnly = true)
    public List<TeacherSchedule> listTeacherSchedule(Long teacherSeq) {
        return teacherScheduleRepository.findByTeacherSeq(teacherSeq);
    }

    // 강사 일정 수정
    @Override
    public Long changeTeacherSchedule(Long scheduleId, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute) {
        TeacherSchedule teacherSchedule = teacherScheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        teacherSchedule.setScheduleCode(scheduleCode);
        teacherSchedule.setScheduleDate(scheduleDate);
        teacherSchedule.setStartTimeHour(startTimeHour);
        teacherSchedule.setStartTimeMinute(startTimeMinute);
        teacherSchedule.setEndTimeHour(endTimeHour);
        teacherSchedule.setEndTimeMinute(endTimeMinute);

        return teacherSchedule.getId();
    }

    // 강사 일정 삭제
    @Override
    public void deleteTeacherSchedule(Long scheduleId) {
        teacherScheduleRepository.deleteById(scheduleId);
    }

    // 강사를 학원에 등록 신청
    @Override
    public Map<String, Long> applyTeacherToAcademy(Long teacherSeq, Long academyId) {
        Teacher teacher = teacherRepository.findById(teacherSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Academy academy = academyRepository.findById(academyId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        // teacherAcademy가 존재하면 신청했던것이므로, 재신청 불가
        if(teacherAcademyRepository.existsByTeacherSeqAndAcademyId(teacher.getSeq(), academy.getId())) {
            return null;
        }

        TeacherAcademy teacherAcademy = TeacherAcademy.createTeacherAcademy(teacher, academy);

        Long teacherAcademyId = teacherAcademyRepository.save(teacherAcademy).getId();

        Long masterSeq = academy.getMaster().getSeq();
        Master master = masterRepository.findById(masterSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        User teacher1 = userRepository.findByuserseq(teacherSeq);

        Alert alert = Alert.createAlert(master, teacherAcademyId, "A1", teacher1.getName());

        Long alertId = alertRepository.save(alert).getId();

        Map<String, Long> ret = new HashMap<>();
        ret.put("teacherAcademyId", teacherAcademyId);
        ret.put("alertId", alertId);

        return ret;
    }

    // 반에서 학생 삭제
    @Override
    public void deleteStudent(Long classId, Long studentId) {
        StudentClass studentClass = studentClassRepository.findByclassIdAndStudentId(classId, studentId);
        studentClassRepository.delete(studentClass);
    }

    @Override
    public List<TeacherAcademy> listTeacherAcademy() {
        return teacherAcademyRepository.findAll();
    }

    @Override
    public TeacherAcademy detailTeacherAcademy(Long teacherAcademyId) {
        return teacherAcademyRepository.findById(teacherAcademyId).orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    @Override
    public void deleteTeacherAcademy(Long teacherSeq, Long academyId) {
        teacherAcademyRepository.deleteByTeacherSeqAndAcademyId(teacherSeq, academyId);
    }

    // 진도 등록
    @Override
    public Long createProgress(Long classId, Integer week, String content) {
        Class clas = classRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Progress progress = Progress.createProgress(clas, week, content);
        return progressRepository.save(progress).getId();
    }

    // 진도 전체 조회
    @Override
    public List<Progress> listProgress(Long classId) {
        return progressRepository.findAllByClasId(classId);
    }

    // 진도 상세 조회
    @Override
    public Progress detailProgress(Long progressId) {
        return progressRepository.findById(progressId).orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    // 진도 수정
    @Override
    public Long changeProgress(Long progressId, Integer week, String content) {
        Progress progress = progressRepository.findById(progressId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        progress.setWeek(week);
        progress.setContent(content);

        return progressId;
    }

    // 진도 삭제
    @Override
    public Long deleteProgress(Long progressId) {
        progressRepository.deleteById(progressId);

        return progressId;
    }

    // 강사-학원 전체 조회
    @Override
    public List<AcademyRepository.AcademyIdAndNameResponse> listAcademy(Long teacherSeq) {
        return academyRepository.findByAcademyAndTeacher(teacherSeq);
    }

    // 강사-학원-반 전체 조회
    @Override
    public List<ClassRepository.ClassInfoResponse> listTeacherClass(Long teacherSeq, Long academyId) {
        return classRepository.findByTeacherSeqAndAcademyid(teacherSeq, academyId);
    }

    // 출석 등록
    @Override
    public void createAttendancy(Long classId, String attendancyDate, Long[] idList, String[] codeList) {
        List<Attendancy> attendancies = new ArrayList<>();

        Class clas = classRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        if (idList.length != codeList.length) throw new IllegalStateException("정보 잘못 입력");
        for (int i = 0; i < idList.length; i++) {
            Student student = studentRepository.findById(idList[i]).orElseThrow(() -> new IllegalArgumentException("no such data"));
            Attendancy attendancy = Attendancy.createAttendancy(student, clas, attendancyDate, codeList[i]);

            attendancies.add(attendancy);
        }

        attendancyRepository.saveAll(attendancies);
    }

    // 출석 수정
    @Override
    public void changeAttendancy(Long classId, String attendancyDate, Long[] idList, String[] codeList) {
        List<Attendancy> attendancies = new ArrayList<>();

        Class clas = classRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        if (idList.length != codeList.length) throw new IllegalStateException("정보 잘못 입력");
        for (int i = 0; i < idList.length; i++) {
            Attendancy attendancy = attendancyRepository.findByclassIdAndStudentIdAndAttendancyDate(classId, idList[i], attendancyDate);

            attendancy.setAttendancyCode(codeList[i]);
        }
    }

    // 출석 조회(반-날짜별)
    @Override
    public List<Attendancy> listAttendancyByDate(Long classId, String attendancyDate) {
        return attendancyRepository.findByclassIdAndAttendancyDate(classId, attendancyDate);
    }

    // 출석 조회(반-학생별)
    @Override
    public List<Attendancy> listAttendancyByStudent(Long classId, Long studentId) {
        return attendancyRepository.findByclassIdAndStudentId(classId, studentId);
    }

    // 강사 - 학원 - 반정보 한번에 조회
    @Override
    public List<ClassRepository.AcademyClassInfo> listAcademyClass(Long teacherSeq) {
        return classRepository.findAcademyAndClassByTeacherSeq(teacherSeq);
    }

    // 특정 날짜로 강사 일정 받아오기
    @Override
    public List<TeacherSchedule> listTeacherScheduleDate(Long teacherSeq, String date) {
        return teacherScheduleRepository.findByteacherSeqAndDate(teacherSeq, date);
    }

    // 반에 학생 등록
    @Override
    public void registClassStudent(Long classId, Long studentId) {
        Class clas = classRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("no such data"));

//        if(studentClassRepository.findByclassIdAndStudentId(classId, studentId) == null) {
//            throw new IllegalStateException("이미 등록된 학생입니다.");
//        }

        StudentClass studentClass = StudentClass.createStudentClass(student, clas);

        studentClassRepository.save(studentClass);
    }

    // 반 기본 정보 조회
    @Override
    public ClassUnionResponse teacherClassInfo(Long classId) {
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

    @Override
    public List<StudentClassRepository.StudentId> listClassStudentId(Long classId) {
        return studentClassRepository.findByClassId(classId);
    }

    // 시험 점수 저장
    @Override
    public Long createTestScore(Long classId, String testDate, String testName, Long[] studentId, Integer[] studentScore) {
        // 시험 정보 먼저 저장
        Class clas = classRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        TestInfo testInfo = TestInfo.createTestInfo(clas, testName, testDate);
        testInfoRepository.save(testInfo);

        // 시험 점수 입력
        for (int i = 0; i < studentId.length; i++) {
            System.out.println(studentId[i]);
            Student student = studentRepository.findById(studentId[i]).orElseThrow(() -> new IllegalArgumentException("no such data"));

            StudentTestInfo studentTestInfo = StudentTestInfo.createStudentTestInfo(student, testInfo, studentScore[i], student.getName());
            studentTestInfoRepository.save(studentTestInfo);
        }

        return testInfo.getId();
    }

    @Override
    public List<TestInfoRepository.ClassTestInfo> listClassTestInfo(Long classId, Integer testDate) {
        return testInfoRepository.findByclassId(classId, testDate);
    }

    // 시험 ID로 학생시험정보 받기
    @Override
    public List<StudentTestInfoRepository.StudentTestInfo> listStudentTestInfo(Long testInfoId) {
        return studentTestInfoRepository.findBytestInfoId(testInfoId);
    }

    @Override
    public void changeStudentTestScore(Long testInfoId, Long[] studentId, Integer[] studentScore) {
        List<StudentTestInfo> studentTestInfos = studentTestInfoRepository.findByTestInfoId(testInfoId);

        for (StudentTestInfo info: studentTestInfos) {
            for (int i = 0; i < studentId.length; i++) {
                if (info.getStudent().getId().equals(studentId[i])) {
                    info.setScore(studentScore[i]);
                    break;
                }
            }
        }
    }

    @Override
    public StudentRepository.StudentInfo studentDetail(Long studentId) {
        return studentRepository.findBystudentId(studentId);
    }

    // 강사 학생 시험 성적 목록 조회
    @Override
    public List<StudentTestScoreList> listStudentTestScore(Long studentId, Long classId) {
        List<StudentTestInfoRepository.TestScoreByClassId> testScoreInfoList = studentTestInfoRepository.findTestInfoByclassId(classId);

        Integer rank = 0;
        Integer studentScore = 0;
        List<StudentTestScoreList> list = new ArrayList<>();
        for (int i = 0; i < testScoreInfoList.size(); i++) {
            Long testInfoId = testScoreInfoList.get(i).getTest_info_id();
            List<StudentTestInfoRepository.TestScoreRank> testScoreRankList = studentTestInfoRepository.findTestScoreRank(testInfoId);

            for (int j = 0; j < testScoreRankList.size(); j++) {
                if(testScoreRankList.get(j).getStudent_id().equals(studentId)) {
                    rank = j + 1;
                    studentScore = testScoreRankList.get(j).getScore();

                    StudentTestScoreList studentTestScoreList = new StudentTestScoreList(testScoreInfoList.get(i).getTest_info_name(), studentScore, testScoreInfoList.get(i).getTest_date(),
                            testScoreInfoList.get(i).getTotal_cnt(), testScoreInfoList.get(i).getAvg_score(), rank);

                    list.add(studentTestScoreList);
                }
            }
        }
        
        return list;
    }

    // 특이사항 등록
    @Override
    public Long createNote(Long classId, Long studentId, String content) {
        StudentClass studentClass = studentClassRepository.findByclassIdAndStudentId(classId, studentId);
        Note note = Note.createNote(studentClass, content);

        return noteRepository.save(note).getId();
    }

    // 특이사항 삭제
    @Override
    public void deleteNote(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    @Data
    @AllArgsConstructor
    public class StudentTestScoreList {
        String testInfoName;
        Integer score;
        String testDate;
        Integer totalCnt;
        Double avgScore;
        Integer rank;
    }
}
