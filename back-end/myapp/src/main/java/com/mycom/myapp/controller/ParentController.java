package com.mycom.myapp.controller;

import com.mycom.myapp.domain.Attendancy;
import com.mycom.myapp.domain.Progress;
import com.mycom.myapp.domain.Review;
import com.mycom.myapp.domain.StudentAcademy;
import com.mycom.myapp.repository.*;
import com.mycom.myapp.repository.user.UserRepository;
import com.mycom.myapp.service.parent.ParentService;
import com.mycom.myapp.service.parent.ParentServiceImpl;
import com.mycom.myapp.service.review.ReviewService;
import com.mycom.myapp.service.teacher.TeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
public class ParentController {

    private final ParentService parentService;
    private final ReviewService reviewService;

    // 학부모-학생 등록
    @ApiOperation(value = "학부모-학생 등록", notes = "학부모가 학생(자녀)를 등록한다. ")
    @PostMapping("/parents/{parentSeq}/students")
    public ResponseEntity<CreateStudentResponse> createStudent(@PathVariable Long parentSeq, @RequestBody CreateStudentRequest request) {
        Long studentId = parentService.createStudent(parentSeq, request.getName(), request.getSchool(), request.getGradeCode(), request.getAge(), request.getPhoneNumber());

        CreateStudentResponse response = new CreateStudentResponse(studentId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateStudentResponse {
        private Long studentId;
    }

    @Data
    static class CreateStudentRequest {
        private String name;
        private String school;
        private String gradeCode;
        private Integer age;
        private String phoneNumber;
    }

    // 학생을 학원에 등록 신청
    @ApiOperation(value = "학생을 학원에 등록 신청", notes = "학생을 학원에 등록 신청한다. ")
    @GetMapping("/parents/students/{studentId}/academies/{academyId}")
    public ResponseEntity<StudentAcademyApplyResponse> studentAcademyApply(@PathVariable Long studentId, @PathVariable Long academyId) {
        Map<String, Long> res = parentService.applyStudentToAcademy(studentId, academyId);

        if (res == null) {
            StudentAcademyApplyResponse response = new StudentAcademyApplyResponse(null, null, "Duplicated");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        StudentAcademyApplyResponse response = new StudentAcademyApplyResponse(res.get("studentAcademyId"), res.get("alertId"), null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class StudentAcademyApplyResponse {
        private Long studentAcademyId;
        private Long alertId;
        private String error;
    }

    // 학부모-학생 전체 조회
    @ApiOperation(value = "학부모-학생 전체 조회", notes = "학부모가 학생(본인 자녀) 목록을 조회한다. ")
    @GetMapping("/parents/{parentSeq}/students")
    public ResponseEntity<ListStudentResponse> listParentStudent(@PathVariable Long parentSeq) {
        List<StudentRepository.StudentInfo> list = parentService.listParentStudents(parentSeq);

        ListStudentResponse response = new ListStudentResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListStudentResponse {
        private List<StudentRepository.StudentInfo> list;
    }

    // 학부모-자녀 상세조회
    @ApiOperation(value = "학부모-학생 상세조회", notes = "학부모가 학생(자녀) 정보를 상세 조회한다. ")
    @GetMapping("/parents/students/{studentId}")
    public ResponseEntity<StudentDetailResponse> studentDetail(@PathVariable Long studentId) {
        StudentRepository.StudentInfo info = parentService.studentDetail(studentId);

        StudentDetailResponse response = new StudentDetailResponse(info);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class StudentDetailResponse {
        StudentRepository.StudentInfo studentInfo;
    }

    // 학부모-자녀 정보 수정
    @ApiOperation(value = "학부모-학생 정보 수정", notes = "학부모가 학생(자녀) 정보를 수정한다. ")
    @PutMapping("/parents/students/{studentId}")
    public ResponseEntity<UpdateStudentInfoResponse> updateStudentInfo(@PathVariable Long studentId, @RequestBody UpdateStudentInfoRequest request) {
        Long foundId = parentService.updateStudentInfo(studentId, request.getName(), request.getSchool(), request.getGradeCode(), request.getAge(), request.getPhoneNumber(), request.getStudentProfileImageUrl());

        UpdateStudentInfoResponse response = new UpdateStudentInfoResponse(foundId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class UpdateStudentInfoResponse {
        private Long studentId;
    }

    @Data
    static class UpdateStudentInfoRequest {
        private String name;
        private String school;
        private String gradeCode;
        private Integer age;
        private String phoneNumber;
        private String studentProfileImageUrl;
    }

    // 학부모-자녀 삭제
    @ApiOperation(value = "학부모-학생 삭제", notes = "학부모가 학생(자녀)를 삭제한다. ")
    @DeleteMapping("/parents/students/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
        parentService.deleteStudent(studentId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 리뷰 등록
    @ApiOperation(value = "리뷰 등록", notes = "학부모가 상담 리뷰를 등록한다. ")
    @PostMapping("/parents/reviews/meetings/{meetingId}")
    public ResponseEntity<CreateReviewResponse> createReview(@PathVariable Long meetingId, @RequestBody CreateReviewRequest request) {
        Long reviewId = reviewService.createReview(meetingId, request.getContent(), request.getParentName());

        CreateReviewResponse response = new CreateReviewResponse(reviewId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateReviewResponse {
        private Long reviewId;
    }

    @Data
    static class CreateReviewRequest {
        private Long reviewId;
        private Long meetingId;
        private String content;
        private String parentName;
    }

    // 리뷰 조회
    @ApiOperation(value = "리뷰 조회", notes = "학부모가 상담 리뷰를 조회한다. ")
    @GetMapping("/parents/reviews/meetings/{meetingId}")
    public ResponseEntity<DetailReviewResponse> detailReview (@PathVariable Long meetingId) {
        ReviewRepository.ReviewDetailResponse review = reviewService.detailReview(meetingId);

        DetailReviewResponse response = new DetailReviewResponse(review.getReview_id(), review.getReview_content(), review.getParent_name(), review.getStudent_name());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class DetailReviewResponse {
        private Long reviewId;
        private String content;
        private String parentName;
        private String studentName;
    }

    // 리뷰 수정
    @ApiOperation(value = "리뷰 수정", notes = "학부모가 상담 리뷰를 수정한다. ")
    @PutMapping("/parents/reviews/{reviewId}")
    public ResponseEntity<Long> updateReview(@PathVariable Long reviewId, @RequestBody UpdateReviewRequest request) {
        reviewService.updateReview(reviewId, request.getContent());

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Data
    static class UpdateReviewRequest {
        private String content;
    }

    // 리뷰 삭제
    @ApiOperation(value = "리뷰 삭제", notes = "학부모가 상담 리뷰를 삭제한다. ")
    @DeleteMapping("/parents/reviews/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    // 학부모-자녀 학원 목록 조회
    @ApiOperation(value = "학부모-자녀 학원 목록 조회", notes = "학부모가 자녀의 학원 목록을 조회한다. ")
    @GetMapping("/parents/{parentSeq}/students/{studentId}/academies")
    public ResponseEntity<ListAcademyStudentResponse> listAcademyStudent(@PathVariable Long studentId) {
        List<AcademyRepository.AcademyIdAndNameResponse> list = parentService.listStudentAcademy(studentId);

        ListAcademyStudentResponse response = new ListAcademyStudentResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListAcademyStudentResponse {
        private List<AcademyRepository.AcademyIdAndNameResponse> list;
    }

    // 학부모-자녀 학원 삭제
    @ApiOperation(value = "학부모-자녀 학원 삭제", notes = "학부모가 자녀의 학원을 삭제한다. ")
    @DeleteMapping("/parents/students/{studentId}/academies/{academyId}")
    public ResponseEntity deleteStudentAcademy(@PathVariable Long studentId, @PathVariable Long academyId) {
        parentService.deleteStudentAcademy(studentId, academyId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 학부모-자녀 학원-반 전체 조회
    @ApiOperation(value = "학부모-자녀 학원, 반 전체 조회", notes = "학부모가 자녀의 학원, 반 목록을 조회한다. ")
    @GetMapping("/parents/students/{studentId}/classes")
    public ResponseEntity<ListStudentAcademyClassResponse> listStudentAcademyClass(@PathVariable Long studentId) {
        List<ClassRepository.StudentClassResponse> list = parentService.listStudentClass(studentId);

        ListStudentAcademyClassResponse response = new ListStudentAcademyClassResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListStudentAcademyClassResponse {
        private List<ClassRepository.StudentClassResponse> list;
    }

    // 학부모-자녀 반 기본 정보 조회
    @ApiOperation(value = "학부모-자녀 반 기본 정보 조회", notes = "학부모가 자녀의 반 상세 정보를 조회한다. ")
    @GetMapping("/parents/classes/{classId}")
    public ResponseEntity<StudentClassInfoResponse> detailClassInfo(@PathVariable Long classId) {
        ParentServiceImpl.ClassUnionResponse ClassUnionResponse = parentService.studentClassInfo(classId);

        StudentClassInfoResponse response = new StudentClassInfoResponse(ClassUnionResponse.getClassInfo(), ClassUnionResponse.getTeacherInfo());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class StudentClassInfoResponse {
        private List<ClassRepository.TeacherClassInfo> teacherClassInfo;
        private UserRepository.TeacherDeatilInfo teacherDeatilInfo;
    }

    // 학부모-자녀 출결 조회
    @ApiOperation(value = "학부모-자녀 출결 조회", notes = "학부모가 자녀의 출결을 조회한다. ")
    @GetMapping("/parents/classes/{classId}/attendancies/students/{studentId}")
    public ResponseEntity<AttendancyResponse> listStudentAttendancy(@PathVariable Long classId, @PathVariable Long studentId) {
        List<Attendancy> list = parentService.listStudentAttendancy(classId, studentId);

        AttendancyResponse response = new AttendancyResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class AttendancyResponse {
        List<Attendancy> list;
    }

    // 학부모-자녀 진도 전체 조회
    @ApiOperation(value = "학부모-자녀 진도 전체 조회", notes = "학부모가 자녀의 반 진도를 조회한다. ")
    @GetMapping("/parents/classes/{classId}/progresses")
    public ResponseEntity<ListProgressResponse> listProgress (@PathVariable Long classId) {
        List<Progress> list = parentService.listProgress(classId);

        ListProgressResponse response = new ListProgressResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListProgressResponse {
        private List<Progress> list;
    }

    // 학부모-자녀 시험 목록 조회
    @ApiOperation(value = "학부모-자녀 시험 목록 조회", notes = "학부모가 자녀의 반 시험 목록을 조회한다. ")
    @GetMapping("/parents/classes/{classId}/tests/{testDate}")
    public ResponseEntity<ListClassTestInfoResponse> listClassTestInfo(@PathVariable Long classId, @PathVariable Integer testDate) {
        List<TestInfoRepository.ClassTestInfo> list = parentService.listClassTestInfo(classId, testDate);

        ListClassTestInfoResponse response = new ListClassTestInfoResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    static class ListClassTestInfoRequest {
        private String testDate;
    }

    @Data
    @AllArgsConstructor
    static class ListClassTestInfoResponse {
        private List<TestInfoRepository.ClassTestInfo> list;
    }

    // 학부모-자녀 시험 성적 조회 (성적, 평균, 석차, 전체 학생수)
    @ApiOperation(value = "학부모-자녀 시험 성적 조회", notes = "학부모가 자녀의 시험 성적을 조회한다. (성적, 평균, 석차, 전체 학생수)")
    @GetMapping("/parents/students/{studentId}/tests/{testInfoId}")
    public ResponseEntity<ParentServiceImpl.StudentTestScoreResponse> listStudentTestInfo(@PathVariable Long studentId, @PathVariable Long testInfoId) {
        ParentServiceImpl.StudentTestScoreResponse response = parentService.listStudentTestInfo(studentId, testInfoId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 반ID와 학생ID로 시험 정보 조회
    @ApiOperation(value = "반 id와 학생 id로 시험 정보 조회", notes = "학부모가 시험 목록과 그에 대한 학생 점수를 조회한다. ")
    @GetMapping("/parents/classes/{classId}/students/{studentId}")
    public ResponseEntity<TestListInfoResponse> listTestInfo(@PathVariable Long classId, @PathVariable Long studentId) {
        List<TestInfoRepository.TestInfoClassIdAndStudentId> list = parentService.listTestInfo(classId, studentId);

        TestListInfoResponse response = new TestListInfoResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class TestListInfoResponse {
        private List<TestInfoRepository.TestInfoClassIdAndStudentId> list;
    }

    // test_info_id로 시험 정보 상세조회 및 성적 추이 가져오기
    @ApiOperation(value = "test_info_id로 시험 정보 상세조회 및 성적 추이 조회", notes = "test_info_id를 이용해 시험 정보를 상세 조회 및 성적 추이를 조회한다. ")
    @GetMapping("/parents/classes/{classId}/students/{studentId}/testInfos/{testInfoId}")
    public ResponseEntity<TestInfoAndTestTrendResponse> detailTestAndTrend(@PathVariable Long classId, @PathVariable Long studentId, @PathVariable Long testInfoId) {
        ParentServiceImpl.RetInfo retInfo = parentService.listTestInfoAndTrends(classId, studentId, testInfoId);

        TestInfoAndTestTrendResponse response = new TestInfoAndTestTrendResponse(retInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class TestInfoAndTestTrendResponse {
        private ParentServiceImpl.RetInfo retInfo;
    }

    // 강사 Seq로 강사의 모든 반 시간 가져오기
    @GetMapping("/parents/schedules/teachers/{teacherSeq}")
    public ResponseEntity<ListAllClassScheduleResponse> listAllClassSchedule(@PathVariable Long teacherSeq) {
        List<ClassRepository.ClassScheduleInfo> list = parentService.listAllSchedule(teacherSeq);

        ListAllClassScheduleResponse response = new ListAllClassScheduleResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListAllClassScheduleResponse {
        List<ClassRepository.ClassScheduleInfo> list;
    }
}