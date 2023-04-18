package com.mycom.myapp.controller;

import com.mycom.myapp.domain.*;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.repository.*;
import com.mycom.myapp.repository.user.UserRepository;
import com.mycom.myapp.service.teacher.TeacherService;
import com.mycom.myapp.service.teacher.TeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(
        origins = {"http://localhost:8080", "https://i8e206.p.ssafy.io"},
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
)
public class TeacherController {

    private final TeacherService teacherService;

    // 강사 정보 등록
    @ApiOperation(value = "강사 정보 등록", notes = "강사가 정보를 등록한다. ")
    @PostMapping("/teachers/{teacherSeq}")
    public ResponseEntity<CreateTeacherInfoResponse> createTeacherInfo(@PathVariable Long teacherSeq, @RequestBody CreateTeacherInfoRequest request) {
        Long foundSeq = teacherService.createTeacherInfo(teacherSeq, request.getIntroduce(), request.getSubjectCode());

        CreateTeacherInfoResponse response = new CreateTeacherInfoResponse(foundSeq);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateTeacherInfoResponse {
        private Long teacherSeq;
    }

    @Data
    static class CreateTeacherInfoRequest {
        private String introduce;
        private String subjectCode;
    }

    // 강사 정보 수정
    @ApiOperation(value = "강사 정보 수정", notes = "강사가 정보를 수정한다. ")
    @PutMapping("/teachers/{teacherSeq}")
    public ResponseEntity<ChangeTeacherInfoResponse> changeTeacherInfo(@PathVariable Long teacherSeq, @RequestBody ChangeTeacherInfoRequest request) {
        Long foundSeq = teacherService.changeTeacherInfo(teacherSeq, request.getIntroduce(), request.getSubjectCode());

        ChangeTeacherInfoResponse response = new ChangeTeacherInfoResponse(foundSeq);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ChangeTeacherInfoResponse {
        private Long teacherSeq;
    }

    @Data
    static class ChangeTeacherInfoRequest {
        private String introduce;
        private String subjectCode;
    }

    // 강사 정보 조회
    @ApiOperation(value = "강사 정보 조회", notes = "강사 정보를 조회한다. ")
    @GetMapping("/teachers/{teacherSeq}")
    public ResponseEntity<DetailTeacherInfoResponse> detailTeacherInfo(@PathVariable Long teacherSeq) {
        Teacher teacher = teacherService.detailTeacherInfo(teacherSeq);

        DetailTeacherInfoResponse response = new DetailTeacherInfoResponse(teacher.getSeq(), teacher.getTeacherIntroduce(), teacher.getSubjectCode());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class DetailTeacherInfoResponse {
        private Long teacherSeq;
        private String introduce;
        private String subjectCode;
    }

    // 강사 이력 등록
    @ApiOperation(value = "강사 이력 등록", notes = "강사가 본인 이력 사항을 등록한다. ")
    @PostMapping("/teachers/{teacherSeq}/histories")
    public ResponseEntity<CreateWorkHistoryResponse> createWorkHistory (@PathVariable Long teacherSeq, @RequestBody CreateWorkHistoryRequest request) {
        Long workHistoryId = teacherService.createWorkHistory(teacherSeq, request.getAcademyName(), request.getStartDateYear(), request.getStartDateMonth(), request.getEndDateYear(), request.getEndDateMonth());

        CreateWorkHistoryResponse response = new CreateWorkHistoryResponse(workHistoryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateWorkHistoryResponse {
        private Long workHistoryId;
    }

    @Data
    static class CreateWorkHistoryRequest {
        private String academyName;
        private Integer startDateYear;
        private Integer startDateMonth;
        private Integer endDateYear;
        private Integer endDateMonth;
    }

    // 강사 이력 수정
    @ApiOperation(value = "강사 이력 수정", notes = "강사가 본인 이력 사항을 수정한다. ")
    @PutMapping("/teachers/histories/{historyId}")
    public ResponseEntity<ChangeWorkHistoryResponse> changeWorkHistory(@PathVariable Long historyId, @RequestBody ChangeWorkHistoryRequest request) {
        Long workHistoryId = teacherService.changeWorkHistory(historyId, request.getAcademyName(), request.getStartDateYear(), request.getStartDateMonth(), request.getEndDateYear(), request.getEndDateMonth());

        ChangeWorkHistoryResponse response = new ChangeWorkHistoryResponse(workHistoryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ChangeWorkHistoryResponse {
        private Long workHistoryId;
    }

    @Data
    static class ChangeWorkHistoryRequest {
        private String academyName;
        private Integer startDateYear;
        private Integer startDateMonth;
        private Integer endDateYear;
        private Integer endDateMonth;
    }

    // 강사 이력 조회
    @ApiOperation(value = "강사 이력 조회", notes = "강사가 이력 사항을 조회한다. ")
    @GetMapping("/teachers/{teacherSeq}/histories")
    public ResponseEntity<ListWorkHistoryResponse> listWorkHistory(@PathVariable Long teacherSeq) {
        List<WorkHistory> list = teacherService.listWorkHistory(teacherSeq);

        ListWorkHistoryResponse response = new ListWorkHistoryResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListWorkHistoryResponse {
        List<WorkHistory> list;
    }

    // 강사 이력 삭제
    @ApiOperation(value = "강사 이력 삭제", notes = "강사가 본인 이력 사항을 삭제한다. ")
    @DeleteMapping("/teachers/histories/{historyId}")
    public ResponseEntity deleteWorkHistory(@PathVariable Long historyId) {
        teacherService.deleteWorkHistory(historyId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 강사 일정 등록
    @ApiOperation(value = "강사 일정 등록", notes = "강사가 일정을 등록한다. ")
    @PostMapping("/teachers/{teacherSeq}/schedules")
    public ResponseEntity<CreateTeacherScheduleResponse> createTeacherSchedule(@PathVariable Long teacherSeq, @RequestBody CreateTeacherScheduleRequest request) {
        Long teacherScheduleId = teacherService.createTeacherSchedule(teacherSeq, request.getScheduleCode(), request.getScheduleDate(), request.getStartTimeHour(), request.getStartTimeMinute(), request.getEndTimeHour(), request.getEndTimeMinute());

        CreateTeacherScheduleResponse response = new CreateTeacherScheduleResponse(teacherScheduleId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateTeacherScheduleResponse {
        private Long teacherScheduleId;
    }

    @Data
    static class CreateTeacherScheduleRequest {
        private String scheduleCode;
        private String scheduleDate;
        private Integer startTimeHour;
        private Integer startTimeMinute;
        private Integer endTimeHour;
        private Integer endTimeMinute;
    }

    // 강사 일정 조회
    @ApiOperation(value = "강사 일정 조회", notes = "강사가 일정을 조회한다. ")
    @GetMapping("/teachers/{teacherSeq}/schedules")
    public ResponseEntity<ListTeacherScheduleResponse> listTeacherSchedule(@PathVariable Long teacherSeq) {
        List<TeacherSchedule> list = teacherService.listTeacherSchedule(teacherSeq);

        ListTeacherScheduleResponse response = new ListTeacherScheduleResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListTeacherScheduleResponse {
        private List<TeacherSchedule> list;
    }

    // 강사 일정 수정
    @ApiOperation(value = "강사 일정 수정", notes = "강사가 일정을 수정한다. ")
    @PutMapping("/teachers/schedules/{scheduleId}")
    public ResponseEntity<ChangeTeacherScheduleResponse> changeTeacherSchedule(@PathVariable Long scheduleId, @RequestBody ChangeTeacherScheduleRequest request) {
        Long teacherScheduleId = teacherService.changeTeacherSchedule(scheduleId, request.getScheduleCode(), request.getScheduleDate(), request.getStartTimeHour(), request.getStartTimeMinute(), request.getEndTimeHour(), request.getEndTimeMinute());

        ChangeTeacherScheduleResponse response = new ChangeTeacherScheduleResponse(teacherScheduleId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ChangeTeacherScheduleResponse {
        private Long teacherScheduleId;
    }

    @Data
    static class ChangeTeacherScheduleRequest {
        private String scheduleCode;
        private String scheduleDate;
        private Integer startTimeHour;
        private Integer startTimeMinute;
        private Integer endTimeHour;
        private Integer endTimeMinute;
    }

    // 강사 일정 삭제
    @ApiOperation(value = "강사 일정 삭제", notes = "강사가 일정을 삭제한다. ")
    @DeleteMapping("/teachers/schedules/{scheduleId}")
    public ResponseEntity deleteMasterSchedule(@PathVariable Long scheduleId) {
        teacherService.deleteTeacherSchedule(scheduleId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 강사를 학원에 등록 신청
    @ApiOperation(value = "강사-학원 등록 신청", notes = "강사가 학원에 등록 신청을 한다. ")
    @GetMapping("/teachers/{teacherSeq}/academies/{academyId}")
    public ResponseEntity<TeacherAcademyApplyResponse> teacherAcademyApply(@PathVariable Long teacherSeq, @PathVariable Long academyId) {
        Map<String, Long> res = teacherService.applyTeacherToAcademy(teacherSeq, academyId);

        // 이미 강사가 학원에 등록 신청 했을 시
        if (res == null) {
            TeacherAcademyApplyResponse errorResponse = new TeacherAcademyApplyResponse(null, null, "Duplicated");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        TeacherAcademyApplyResponse response = new TeacherAcademyApplyResponse(res.get("teacherAcademyId"), res.get("alertId"), null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class TeacherAcademyApplyResponse {
        private Long teacherAcademyId;
        private Long alertId;
        private String error;
    }

    // 강사-학원 테이블 목록 조회
    @ApiOperation(value = "강사-학원 목록 조회", notes = "강사가 학원 목록을 조회한다. ")
    @GetMapping("/teacheracademy")
    public ResponseEntity<TeacherAcademyListResponse> listTeacherAcademy() {
        List<TeacherAcademy> list = teacherService.listTeacherAcademy();

        TeacherAcademyListResponse response = new TeacherAcademyListResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class TeacherAcademyListResponse {
        private List<TeacherAcademy> list;
    }

    // 강사-학원 테이블 상세 조회
    @ApiOperation(value = "강사-학원 상세 조회")
    @GetMapping("/teacheracademy/{teacherAcademyId}")
    public ResponseEntity<TeacherAcademyDetailResponse> listTeacherAcademy(@PathVariable Long teacherAcademyId) {
        TeacherAcademy teacherAcademy = teacherService.detailTeacherAcademy(teacherAcademyId);

        TeacherAcademyDetailResponse response = new TeacherAcademyDetailResponse(teacherAcademy);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class TeacherAcademyDetailResponse {
        private TeacherAcademy teacherAcademy;
    }

    // 강사-학원 삭제
    @ApiOperation(value = "강사-학원 삭제", notes = "강사가 학원을 삭제한다. ")
    @DeleteMapping("/teachers/{teacherSeq}/academies/{academyId}")
    public ResponseEntity deleteTeacherAcademy(@PathVariable Long teacherSeq, @PathVariable Long academyId) {
        teacherService.deleteTeacherAcademy(teacherSeq, academyId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 진도 등록
    @ApiOperation(value = "진도 등록", notes = "강사가 진도를 등록한다. ")
    @PostMapping("/teachers/classes/{classId}/progresses")
    public ResponseEntity<CreateProgressResponse> createProgress(@PathVariable Long classId, @RequestBody CreateProgressRequest request) {
        Long progressId = teacherService.createProgress(classId, request.getWeek(), request.getContent());

        CreateProgressResponse response = new CreateProgressResponse(progressId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateProgressResponse {
        private Long progressId;
    }

    @Data
    static class CreateProgressRequest {
        private Integer week;
        private String content;
    }

    // 진도 전체 조회
    @ApiOperation(value = "진도 전체 조회", notes = "강사가 반 진도를 전체 조회한다. ")
    @GetMapping("/teachers/classes/{classId}/progresses")
    public ResponseEntity<ListProgressResponse> listProgress (@PathVariable Long classId) {
        List<Progress> list = teacherService.listProgress(classId);

        ListProgressResponse response = new ListProgressResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListProgressResponse {
        private List<Progress> list;
    }

    // 진도 상세 조회
    @ApiOperation(value = "진도 상세 조회", notes = "반 진도를 상세 조회한다. ")
    @GetMapping("/teachers/classes/progresses/{progressId}")
    public ResponseEntity<DetailProgressResponse> detailProgress (@PathVariable Long progressId) {
        Progress progress = teacherService.detailProgress(progressId);

        DetailProgressResponse response = new DetailProgressResponse(progress);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class DetailProgressResponse {
        private Progress progress;
    }

    // 진도 수정
    @ApiOperation(value = "진도 수정", notes = "강사가 반 진도를 수정한다. ")
    @PutMapping("/teachers/classes/progresses/{progressId}")
    public ResponseEntity<ChangeProgressResponse> changeProgress(@PathVariable Long progressId, @RequestBody ChangeProgressRequest request) {
        Long retId = teacherService.changeProgress(progressId, request.getWeek(), request.getContent());

        ChangeProgressResponse response = new ChangeProgressResponse(retId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ChangeProgressResponse {
        private Long progressId;
    }

    @Data
    static class ChangeProgressRequest {
        private Integer week;
        private String content;
    }

    // 진도 삭제
    @ApiOperation(value = "진도 삭제", notes = "강사가 반 진도를 삭제한다. ")
    @DeleteMapping("/teachers/classes/progresses/{progressId}")
    public ResponseEntity<DeleteProgressResponse> deleteProgress (@PathVariable Long progressId) {
        Long retId = teacherService.deleteProgress(progressId);

        DeleteProgressResponse response = new DeleteProgressResponse(retId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class DeleteProgressResponse {
        private Long progressId;
    }

    // 강사-학원 전체 조회
    @ApiOperation(value = "강사-학원 전체 조회", notes = "강사가 학원 목록을 조회한다. ")
    @GetMapping("/teachers/{teacherSeq}/academies")
    public ResponseEntity<ListAcademyTeacherResponse> listAcademyTeacher(@PathVariable Long teacherSeq) {
        List<AcademyRepository.AcademyIdAndNameResponse> list = teacherService.listAcademy(teacherSeq);

        ListAcademyTeacherResponse response = new ListAcademyTeacherResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListAcademyTeacherResponse {
        private List<AcademyRepository.AcademyIdAndNameResponse> list;
    }

    // 강사-학원-반 전체 조회
    @ApiOperation(value = "강사-학원, 반 전체 조회", notes = "강사가 학원, 반 목록을 조회한다. ")
    @GetMapping("/teachers/{teacherSeq}/academies/{academyId}/classes")
    public ResponseEntity<ListTeacherAcademyClassResponse> listTeacherAcademyClass(@PathVariable Long teacherSeq, @PathVariable Long academyId) {
        List<ClassRepository.ClassInfoResponse> list = teacherService.listTeacherClass(teacherSeq, academyId);

        ListTeacherAcademyClassResponse response = new ListTeacherAcademyClassResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListTeacherAcademyClassResponse {
        private List<ClassRepository.ClassInfoResponse> list;
    }

    // 출석 등록
    @ApiOperation(value = "출석 등록", notes = "강사가 학생 출결을 등록한다. ")
    @PostMapping("/teachers/attendancies/classes/{classId}")
    public ResponseEntity createAttendancy(@PathVariable Long classId, @RequestBody CreateAttendancyRequest request) {
        teacherService.createAttendancy(classId, request.getAttendancyDate(), request.getIdList(), request.getCodeList());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Data
    static class CreateAttendancyRequest {
        private String attendancyDate;
        private Long[] idList;
        private String[] codeList;
    }

    // 출석 수정
    @ApiOperation(value = "출석 수정", notes = "강사가 학생 출결을 수정한다. ")
    @PutMapping("/teachers/attendancies/classes/{classId}")
    public ResponseEntity createAttendancy(@PathVariable Long classId, @RequestBody ChangeAttendancyRequest request) {
        teacherService.changeAttendancy(classId, request.getAttendancyDate(), request.getIdList(), request.getCodeList());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Data
    static class ChangeAttendancyRequest {
        private String attendancyDate;
        private Long[] idList;
        private String[] codeList;
    }

    // 출석 조회(반날짜 기준)
    @ApiOperation(value = "출석 조회(반 날짜 기준)", notes = "학생 출석을 날짜 기준으로 조회한다. ")
    @PostMapping("/teachers/attendancies/classes/{classId}/dates")
    public ResponseEntity<AttendancyResponse> listAttendancyByDate(@PathVariable Long classId, @RequestBody AttendancyRequest request) {
        List<Attendancy> list = teacherService.listAttendancyByDate(classId, request.getAttendancyDate());

        AttendancyResponse response = new AttendancyResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 출석 조회(학생 기준)
    @ApiOperation(value = "출석 조회(학생 기준)", notes = "학생을 기준으로 학생 출석을 조회한다. ")
    @GetMapping("/teachers/classes/{classId}/attendancies/students/{studentId}")
    public ResponseEntity<AttendancyResponse> listAttendancyByStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        List<Attendancy> list = teacherService.listAttendancyByStudent(classId, studentId);

        AttendancyResponse response = new AttendancyResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class AttendancyResponse {
        List<Attendancy> list;
    }

    @Data
    static class AttendancyRequest {
        private String attendancyDate;
    }

    // 강사 - 학원 - 반 목록 조회
    @ApiOperation(value = "강사-학원, 반 목록 조회", notes = "강사가 학원, 반 목록을 조회한다. ")
    @GetMapping("/teachers/{teacherSeq}/info")
    public ResponseEntity<TeacherAcademyClassInfoResponse> listAcademyClass(@PathVariable Long teacherSeq) {
        List<ClassRepository.AcademyClassInfo> list = teacherService.listAcademyClass(teacherSeq);

        TeacherAcademyClassInfoResponse response = new TeacherAcademyClassInfoResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class TeacherAcademyClassInfoResponse {
        List<ClassRepository.AcademyClassInfo> list;
    }

    // 특정 날짜 강사 일정 조회
    @ApiOperation(value = "특정 날짜 강사 일정 조회", notes = "특정 날짜로 강사의 일정을 조회한다. ")
    @GetMapping("/teachers/{teacherSeq}/schedules/{currentDate}")
    public ResponseEntity<ListTeacherScheduleResponse> listTeacherScheduleDate(@PathVariable Long teacherSeq, @PathVariable String currentDate) {
        List<TeacherSchedule> list = teacherService.listTeacherScheduleDate(teacherSeq, currentDate);

        ListTeacherScheduleResponse response = new ListTeacherScheduleResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 반에 학생 등록
    @ApiOperation(value = "반 원생 등록", notes = "강사가 반에 학생을 등록한다. ")
    @GetMapping("/teachers/classes/{classId}/students/{studentId}")
    public ResponseEntity registClassStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        teacherService.registClassStudent(classId, studentId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 반 학생 삭제
    @ApiOperation(value = "반 원생 삭제", notes = "강사가 반에서 학생을 삭제한다.")
    @DeleteMapping("/teachers/classes/{classId}/students/{studentId}/delete")
    public ResponseEntity deleteStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        teacherService.deleteStudent(classId, studentId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 반 기본정보 조회
    @ApiOperation(value = "반 기본 정보 조회", notes = "반의 기본 정보를 조회한다. ")
    @GetMapping("/teachers/classes/{classId}")
    public ResponseEntity<TeacherClassInfoResponse> detailClassInfo(@PathVariable Long classId) {
        TeacherServiceImpl.ClassUnionResponse ClassUnionResponse = teacherService.teacherClassInfo(classId);

        TeacherClassInfoResponse response = new TeacherClassInfoResponse(ClassUnionResponse.getClassInfo(), ClassUnionResponse.getTeacherInfo());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class TeacherClassInfoResponse {
        private List<ClassRepository.TeacherClassInfo> teacherClassInfo;
        private UserRepository.TeacherDeatilInfo teacherDeatilInfo;
    }

    // 반 학생 ID 목록 조회
    @ApiOperation(value = "반 학생 id 목록 조회", notes = "반의 학생 id 목록을 조회한다. ")
    @GetMapping("/teachers/classes/{classId}/students")
    public ResponseEntity<StudentIdListResponse> listClassStudentId(@PathVariable Long classId) {
        List<StudentClassRepository.StudentId> list = teacherService.listClassStudentId(classId);

        StudentIdListResponse response = new StudentIdListResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class StudentIdListResponse {
        private List<StudentClassRepository.StudentId> list;
    }

    // 시험성적 등록
    @ApiOperation(value = "시험 성적 등록", notes = "강사가 반 학생들 시험 성적을 등록한다. ")
    @PostMapping("/teachers/classes/{classId}/tests")
    public ResponseEntity<CreateTestScoreResponse> createTestScores(@PathVariable Long classId, @RequestBody CreateTestScoreRequest request) {
        Long testId = teacherService.createTestScore(classId, request.getTestDate(), request.getTestName(), request.getStudentId(), request.getStudentScore());

        CreateTestScoreResponse response = new CreateTestScoreResponse(testId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateTestScoreResponse {
        private Long testId;
    }

    @Data
    static class CreateTestScoreRequest {
        private String testDate;
        private String testName;
        private Long[] studentId;
        private Integer[] studentScore;
    }

    // 반-시험정보 목록 조회
    @ApiOperation(value = "반-시험 정보 목록 조회", notes = "반의 시험 정보 목록을 조회한다. ")
    @GetMapping("/teachers/classes/{classId}/tests/{testDate}")
    public ResponseEntity<ListClassTestInfoResponse> listClassTestInfo(@PathVariable Long classId, @PathVariable Integer testDate) {
        List<TestInfoRepository.ClassTestInfo> list = teacherService.listClassTestInfo(classId, testDate);

        ListClassTestInfoResponse response = new ListClassTestInfoResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListClassTestInfoResponse {
        private List<TestInfoRepository.ClassTestInfo> list;
    }

    // 시험ID로 학생 시험 정보 받기
    @ApiOperation(value = "시험 id로 학생 시험 정보 조회", notes = "시험 id로 학생 시험 정보를 조회한다. ")
    @GetMapping("/teachers/tests/{testInfoId}")
    public ResponseEntity<ListStudentTestInfoResponse> listStudentTestInfo(@PathVariable Long testInfoId) {
        List<StudentTestInfoRepository.StudentTestInfo> studentTestInfo = teacherService.listStudentTestInfo(testInfoId);

        ListStudentTestInfoResponse response = new ListStudentTestInfoResponse(studentTestInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListStudentTestInfoResponse {
        private List<StudentTestInfoRepository.StudentTestInfo> list;
    }

    // 시험 성적 수정
    @ApiOperation(value = "시험 성적 수정", notes = "강사가 학생들 시험 성적을 수정한다. ")
    @PutMapping("/teachers/classes/tests/{testInfoId}")
    public ResponseEntity changeStudentTestScore(@PathVariable Long testInfoId, @RequestBody ChangeTestScoreRequest request) {
        teacherService.changeStudentTestScore(testInfoId, request.getStudentId(), request.getStudentScore());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Data
    static class ChangeTestScoreRequest {
        private Long[] studentId;
        private Integer[] studentScore;
    }

    // 강사 학생 정보 상세 조회
    @ApiOperation(value = "강사-학생 정보 상세 조회", notes = "강사가 학생 상세 정보를 조회한다. ")
    @GetMapping("/teachers/students/{studentId}")
    public ResponseEntity<StudentDetailResponse> studentDetail(@PathVariable Long studentId) {
        StudentRepository.StudentInfo info = teacherService.studentDetail(studentId);

        StudentDetailResponse response = new StudentDetailResponse(info);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class StudentDetailResponse {
        StudentRepository.StudentInfo studentInfo;
    }

    // 강사 학생 시험 성적 목록 조회
    @ApiOperation(value = "강사-학생 시험 성적 목록 조회", notes = "강사가 학생 시험 성적 목록을 조회한다. ")
    @GetMapping("/teachers/scores/classes/{classId}/students/{studentId}")
    public ResponseEntity<ListStudenTestScoreResponse> listStudentTestScore(@PathVariable Long studentId, @PathVariable Long classId) {
        List<TeacherServiceImpl.StudentTestScoreList> list = teacherService.listStudentTestScore(studentId, classId);

        ListStudenTestScoreResponse response = new ListStudenTestScoreResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListStudenTestScoreResponse {
        private List<TeacherServiceImpl.StudentTestScoreList> list;
    }

    // 특이사항 등록
    @ApiOperation(value = "특이사항 등록", notes = "강사가 학생의 특이사항을 등록한다. ")
    @PostMapping("/teachers/notes/classes/{classId}/students/{studentId}")
    public ResponseEntity<CreateNoteResponse> createNote (@PathVariable Long classId, @PathVariable Long studentId, @RequestBody CreateNoteRequest request) {
        Long noteId = teacherService.createNote(classId, studentId, request.getContent());

        CreateNoteResponse response = new CreateNoteResponse(noteId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    static class CreateNoteRequest {
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class CreateNoteResponse {
        private Long noteId;
    }

    // 특이사항 삭제
    @ApiOperation(value = "특이사항 삭제", notes = "강사가 학생의 특이사항을 삭제한다. ")
    @DeleteMapping("/teachers/notes/{noteId}")
    public ResponseEntity deleteNote(@PathVariable Long noteId) {
        teacherService.deleteNote(noteId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
