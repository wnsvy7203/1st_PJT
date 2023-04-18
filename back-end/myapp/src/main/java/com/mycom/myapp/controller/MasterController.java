package com.mycom.myapp.controller;

import com.mycom.myapp.domain.MasterSchedule;
import com.mycom.myapp.domain.TeacherSchedule;
import com.mycom.myapp.repository.AcademyRepository;
import com.mycom.myapp.repository.ClassRepository;
import com.mycom.myapp.repository.StudentRepository;
import com.mycom.myapp.repository.user.TeacherRepository;
import com.mycom.myapp.repository.user.UserRepository;
import com.mycom.myapp.service.master.MasterService;
import com.mycom.myapp.service.parent.ParentService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(
        origins = {"http://localhost:8080", "https://i8e206.p.ssafy.io"},
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
)
public class MasterController {

    private final MasterService masterService;

    // 학원장 일정 등록
    @ApiOperation(value = "학원장 일정 등록", notes = "학원장의 일정을 등록한다.")
    @PostMapping("/masters/{masterSeq}/schedules")
    public ResponseEntity<CreateMasterScheduleResponse> createMasterSchedule(@PathVariable Long masterSeq, @RequestBody CreateMasterScheduleRequest request) {
        Long masterScheduleId = masterService.createMasterSchedule(masterSeq, request.getScheduleCode(), request.getScheduleDate(), request.getStartTimeHour(), request.getStartTimeMinute(), request.getEndTimeHour(), request.getEndTimeMinute());

        CreateMasterScheduleResponse response = new CreateMasterScheduleResponse(masterScheduleId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateMasterScheduleResponse {
        private Long masterScheduleId;
    }

    @Data
    static class CreateMasterScheduleRequest {
        private String scheduleCode;
        private String scheduleDate;
        private Integer startTimeHour;
        private Integer startTimeMinute;
        private Integer endTimeHour;
        private Integer endTimeMinute;
    }

    // 학원장 일정 수정
    @ApiOperation(value = "학원장 일정 수정", notes = "학원장의 일정을 수정한다.")
    @PutMapping("/masters/schedules/{scheduleId}")
    public ResponseEntity<ChangeMasterScheduleResponse> changeMasterSchedule(@PathVariable Long scheduleId, @RequestBody ChangeMasterScheduleRequest request) {
        Long masterScheduleId = masterService.changeMasterSchedule(scheduleId, request.getScheduleCode(), request.getScheduleDate(), request.getStartTimeHour(), request.getStartTimeMinute(), request.getEndTimeHour(), request.getEndTimeMinute());

        ChangeMasterScheduleResponse response = new ChangeMasterScheduleResponse(masterScheduleId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ChangeMasterScheduleResponse {
        private Long masterScheduleId;
    }

    @Data
    static class ChangeMasterScheduleRequest {
        private String scheduleCode;
        private String scheduleDate;
        private Integer startTimeHour;
        private Integer startTimeMinute;
        private Integer endTimeHour;
        private Integer endTimeMinute;
    }

    // 학원장 일정 조회
    @ApiOperation(value = "학원장 일정 조회", notes = "학원장의 일정을 조회한다.")
    @GetMapping("/masters/{masterSeq}/schedules")
    public ResponseEntity<ListMasterScheduleResponse> listMasterSchedule(@PathVariable Long masterSeq) {
        List<MasterSchedule> list = masterService.listMasterSchedule(masterSeq);

        ListMasterScheduleResponse response = new ListMasterScheduleResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListMasterScheduleResponse {
        private List<MasterSchedule> list;
    }

    // 학원장 일정 삭제
    @ApiOperation(value = "학원장 일정 삭제", notes = "학원장의 일정을 삭제한다.")
    @DeleteMapping("/masters/schedules/{scheduleId}")
    public ResponseEntity deleteMasterSchedule(@PathVariable Long scheduleId) {
        masterService.deleteMasterSchedule(scheduleId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 학원장이 강사 등록 신청 승인
    @ApiOperation(value = "강사 등록 승인", notes = "강사가 학원에 등록 신청을 하면 학원장이 알람창에서 승인해줄 수 있다.")
    @GetMapping("/masters/approveteacher/alerts/{alertId}")
    public ResponseEntity<MasterTeacherApproveResponse> masterTeacherApprove(@PathVariable Long alertId) {
        Long teacherAcademyId = masterService.approveTeacherApply(alertId);

        MasterTeacherApproveResponse response = new MasterTeacherApproveResponse(teacherAcademyId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class MasterTeacherApproveResponse {
        private Long teacherAcademyId;
    }

    // 학원장이 강사 등록 신청 거절
    @ApiOperation(value = "강사 등록 거절", notes = "강사가 학원에 등록 신청을 하면 학원장이 알람창에서 거절할 수 있다.")
    @DeleteMapping("/masters/dismissteacher/alerts/{alertId}")
    public ResponseEntity<MasterTeacherDismissResponse> masterTeacherDismiss(@PathVariable Long alertId) {
        Long teacherAcademyId = masterService.dismissTeacherApply(alertId);

        MasterTeacherDismissResponse response = new MasterTeacherDismissResponse(teacherAcademyId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class MasterTeacherDismissResponse {
        private Long teacherAcademyId;
    }

    // 학원장이 학생 등록 신청 승인
    @ApiOperation(value = "학생 등록 승인", notes = "강사가 학원에 등록 신청을 하면 학원장이 알람창에서 승인해줄 수 있다.")
    @GetMapping("/masters/approvestudent/alerts/{alertId}")
    public ResponseEntity<MasterStudentApproveResponse> masterStudentApprove(@PathVariable Long alertId) {
        Long studentAcademyId = masterService.approveStudentApply(alertId);

        MasterStudentApproveResponse response = new MasterStudentApproveResponse(studentAcademyId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class MasterStudentApproveResponse {
        private Long studentAcademyId;
    }

    // 학원장이 학생 등록 신청 거절
    @ApiOperation(value = "학생 등록 거절", notes = "강사가 학원에 등록 신청을 하면 학원장이 알람창에서 거절할 수 있다.")
    @DeleteMapping("/masters/dismissstudent/alerts/{alertId}")
    public ResponseEntity<MasterStudentDismissResponse> masterStudentDismiss(@PathVariable Long alertId) {
        Long studentAcademyId = masterService.dismissStudentApply(alertId);

        MasterStudentDismissResponse response = new MasterStudentDismissResponse(studentAcademyId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class MasterStudentDismissResponse {
        private Long studentAcademyId;
    }

    // 학원장 Seq로 학원정보 받아오기
    @ApiOperation(value = "학원정보 받아오기", notes = "학원장의 masterSeq(userSeq)로 학원 정보를 받아온다.")
    @GetMapping("/masters/{masterSeq}/academies")
    public ResponseEntity<GetAcademyInfoResponse> getAcademyInfo(@PathVariable Long masterSeq) {
        AcademyRepository.AcademyInfoResponse academyInfo = masterService.getAcademyInfo(masterSeq);

        GetAcademyInfoResponse response = new GetAcademyInfoResponse(academyInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class GetAcademyInfoResponse {
        AcademyRepository.AcademyInfoResponse academyInfo;
    }

    // 학원ID로 반 목록 전체 받아오기
    @ApiOperation(value = "반 목록 받아오기", notes = "학원ID로 반 목록 전체를 받아온다.")
    @GetMapping("/masters/academies/{academyId}/classes")
    public ResponseEntity<ListAcademyClassResponse> listAcademyClass(@PathVariable Long academyId) {
        List<ClassRepository.ClassInfoResponse> list = masterService.getAcademyClassInfo(academyId);

        ListAcademyClassResponse response = new ListAcademyClassResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListAcademyClassResponse {
        private List<ClassRepository.ClassInfoResponse> list;
    }

    // 학원장 반 삭제
    @ApiOperation(value = "반 삭제", notes = "학원장이 반을 삭제할 수 있다.")
    @DeleteMapping("/masters/classes/{classId}")
    public ResponseEntity deleteAcademyClass(@PathVariable Long classId) {
        masterService.deleteAcademyClass(classId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 학원장 강사 삭제
    @ApiOperation(value = "강사 삭제", notes = "학원장이 강사를 삭제할 수 있다.")
    @DeleteMapping("/masters/teachers/{teacherSeq}")
    public ResponseEntity deleteTeacher(@PathVariable Long teacherSeq) {
        masterService.deleteTeacher(teacherSeq);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 학원장 학생 삭제
    @ApiOperation(value = "학생 삭제", notes = "학원장이 학생을 삭제할 수 있다.")
    @DeleteMapping("/masters/students/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
        masterService.deleteStudent(studentId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 학원장이 반 등록
    @ApiOperation(value = "반 등록", notes = "학원장이 학원의 반을 만들 수 있다.")
    @PostMapping("/masters/classes/academies/{academyId}/teachers/{teacherSeq}")
    public ResponseEntity<CreateClassResponse> createClass (@PathVariable Long academyId, @PathVariable Long teacherSeq, @RequestBody CreateClassRequest request) {
        Long classId = masterService.createClass(academyId, teacherSeq, request.getClassName(), request.getSubjectCode(), request.getGradeCode(), request.getDay(), request.getStartTimeHour(), request.getStartTimeMinute(), request.getEndTimeHour(), request.getEndTimeMinute());

        CreateClassResponse response = new CreateClassResponse(classId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateClassResponse {
        private Long classId;
    }

    @Data
    static class CreateClassRequest {
        private String className;
        private String subjectCode;
        private String gradeCode;
        private String[] day;
        private Integer[] startTimeHour;
        private Integer[] startTimeMinute;
        private Integer[] endTimeHour;
        private Integer[] endTimeMinute;
    }

    // 학원-학생 전체 조회
    @ApiOperation(value = "학생 전체 조회", notes = "학원장이 학원의 학생 전체 목록을 조회할 수 있다.")
    @GetMapping("/masters/academies/{academyId}/students")
    public ResponseEntity<ListStudentResponse> listAcademyStudent(@PathVariable Long academyId) {
        List<StudentRepository.StudentInfo> list = masterService.listAcademyStudents(academyId);

        ListStudentResponse response = new ListStudentResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListStudentResponse {
        private List<StudentRepository.StudentInfo> list;
    }

    // 학원에 소속된 강사 전체 조회
    @ApiOperation(value = "강사 전체 조회", notes = "학원장이 학원에 소속된 모든 강사 정보를 가져올 수 있다.")
    @GetMapping("/masters/academies/{academyId}/teachers")
    public ResponseEntity<ListTeacherResponse> listTeacher(@PathVariable Long academyId) {
        List<UserRepository.TeacherInfo> list_user = masterService.listTeacherInfo_user(academyId);
        List<TeacherRepository.TeacherInfo> list_teacher = masterService.listTeacherInfo_teacher(academyId);

        List<FinalTeacherInfo> list_final = new ArrayList<>();

        for (int i = 0; i < list_user.size(); i++) {
            Long teacherSeq = list_user.get(i).getUser_seq();
            String teacherName = list_user.get(i).getUser_name();
            String teacherPhoneNumber = list_user.get(i).getUser_phone_number();
            String teacherEmail = list_user.get(i).getUser_email();
            String profileImg = list_user.get(i).getUser_profile_image_url();
            String subjectCode = list_teacher.get(i).getSubject_code();

            list_final.add(new FinalTeacherInfo(teacherSeq, teacherName, teacherPhoneNumber, teacherEmail, profileImg, subjectCode));
        }

        ListTeacherResponse response = new ListTeacherResponse(list_final);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class FinalTeacherInfo {
        private Long teacherSeq;
        private String teacherName;
        private String teacherPhoneNumber;
        private String teacherEmail;
        private String profileImg;
        private String subjectCode;
    }

    @Data
    @AllArgsConstructor
    static class ListTeacherResponse {
        private List list;
    }

    // 특정 날짜 강사 일정 조회
    @ApiOperation(value = "강사 일정 조회", notes = "학원장이 특정 날짜의 강사 일정을 조회할 수 있다.")
    @GetMapping("/masters/{masterSeq}/schedules/{currentDate}")
    public ResponseEntity<ListMasterScheduleResponse> listTeacherScheduleDate(@PathVariable Long masterSeq, @PathVariable String currentDate) {
        List<MasterSchedule> list = masterService.listMasterScheduleDate(masterSeq, currentDate);

        ListMasterScheduleResponse response = new ListMasterScheduleResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 학원 정보 수정
    @ApiOperation(value = "학원 정보 수정", notes = "학원장이 학원 정보를 수정할 수 있다.")
    @PutMapping("/masters/academies/{academyId}")
    public ResponseEntity<UpdateAcademyInfoResponse> updateAcademyInfo(@PathVariable Long academyId, @RequestBody UpdateAcademyInfoRequest request) {
        Long foundAcademy = masterService.updateAcademy(academyId, request.getAcademyName(), request.getZipCode(),
                request.getFirstAddress(), request.getLastAddress(), request.getPhoneNumber(), request.getIntroduce());

        UpdateAcademyInfoResponse response = new UpdateAcademyInfoResponse(foundAcademy);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class UpdateAcademyInfoResponse {
        private Long academyId;
    }

    @Data
    static class UpdateAcademyInfoRequest {
        private String academyName;
        private String zipCode;
        private String firstAddress;
        private String lastAddress;
        private String phoneNumber;
        private String introduce;
    }

    // 학원장이 반 강사 변경
    @ApiOperation(value = "반 강사 변경", notes = "학원장이 반 강사를 변경할 수 있다.")
    @PutMapping("/masters/classes/{classId}")
    public ResponseEntity<Long> changeClassTeacher(@PathVariable Long classId, @RequestBody ChangeClassTeacherRequest request) {
        Long retClassId = masterService.changeClassTeacher(classId, request.getTeacherSeq());

        return new ResponseEntity<>(retClassId, HttpStatus.OK);
    }

    @Data
    static class ChangeClassTeacherRequest {
        private Long teacherSeq;
    }
}
