package com.mycom.myapp.controller;

import com.mycom.myapp.domain.Meeting;
import com.mycom.myapp.repository.ClassRepository;
import com.mycom.myapp.repository.MeetingRepository;
import com.mycom.myapp.service.meeting.MeetingService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
public class MeetingController {

    private final MeetingService meetingService;

    // 상담 등록
    @ApiOperation(value = "상담 등록", notes = "상담을 등록한다. ")
    @PostMapping("/meetings/parents/{parentSeq}")
    public ResponseEntity<CreateMeetingResponse> createMeeting(@PathVariable Long parentSeq, @RequestBody CreateMeetingRequest request) {
        Map<String, Long> res = meetingService.createMeeting(parentSeq, request.getCounselorSeq(), request.getMeetingCode(), request.getAcademyName(), request.getClassName(), request.getStudentName(), request.getStartTimeHour(), request.getStartTimeMinute(), request.getStartDate(), request.getStudentId(), request.getClassId());

        CreateMeetingResponse response = new CreateMeetingResponse(res.get("meetingId"), res.get("alertId"));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateMeetingResponse {
        private Long meetingId;
        private Long alertId;
    }

    @Data
    static class CreateMeetingRequest {
        private Long parentSeq;
        private Long counselorSeq;
        private String meetingCode;
        private String academyName;
        private String className;
        private String studentName;
        private Integer startTimeHour;
        private Integer startTimeMinute;
        private String startDate;
        private Long studentId;
        private Long classId;
    }

    // 학부모 상담 목록 조회
    @ApiOperation(value = "학부모 상담 목록 조회", notes = "학부모가 상담 목록을 조회한다. ")
    @GetMapping("/meetings/parents/{parentSeq}")
    public ResponseEntity<ListParentMeetingResponse> listParentMeeting(@PathVariable Long parentSeq) {
        List<MeetingRepository.ParentMeetingInfoResponse> list = meetingService.listPrentMeeting(parentSeq);

        ListParentMeetingResponse response = new ListParentMeetingResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListParentMeetingResponse {
        private List<MeetingRepository.ParentMeetingInfoResponse> parentMeetingList;
    }

    // 강사, 학원장 상담 목록 조회
    @ApiOperation(value = "강사, 학원장 상담 목록 조회", notes = "강사, 학원장이 상담 목록을 조회한다. ")
    @GetMapping("/meetings/counselor/{userSeq}")
    public ResponseEntity<ListTeacherMasterMeetingResponse> listTeacherMasterMeeting(@PathVariable Long userSeq) {
        List<MeetingRepository.TeacherMasterMeetingInfoResponse> list = meetingService.listTeacherMasterMeeting(userSeq);

        ListTeacherMasterMeetingResponse response = new ListTeacherMasterMeetingResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListTeacherMasterMeetingResponse {
        private List<MeetingRepository.TeacherMasterMeetingInfoResponse> teacherMasterMeetingList;
    }

    // 상담 삭제
    @ApiOperation(value = "상담 삭제", notes = "상담 내역을 삭제한다. ")
    @DeleteMapping("/meetings/{meetingId}")
    public ResponseEntity deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 반ID로 학원장Seq, 강사Seq, 학원이름, 반이름 받기
    @ApiOperation(value = "반 id로 학원장 seq, 강사 seq, 학원이름, 반이름 받기", notes = "반 id로 학원장 seq, 강사 seq, 학원이름, 반이름 받기")
    @GetMapping("/meetings/classes/{classId}")
    public ResponseEntity<MeetingInfoResponse> detailInfo(@PathVariable Long classId) {
        ClassRepository.MeetingInfo meetingInfo = meetingService.detailMeetingInfo(classId);

        MeetingInfoResponse response = new MeetingInfoResponse(meetingInfo.getMaster_seq(), meetingInfo.getTeacher_seq(), meetingInfo.getAcademy_name(), meetingInfo.getClass_name());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class MeetingInfoResponse {
        private Long masterSeq;
        private Long teacherSeq;
        private String academyName;
        private String className;
    }

    // 상담 상태코드 변경
    @ApiOperation(value = "상담 상태코드 변경", notes = "상담 상태코드를 변경한다. ")
    @PutMapping("/meetings/{meetingId}")
    public ResponseEntity<ChangeMeetingCodeResponse> changeMeetingCode(@PathVariable Long meetingId, @RequestBody ChangeMeetingCodeRequest request) {
        Long changedMeetingId = meetingService.changeMeetingCode(meetingId, request.getMeetingCode());

        ChangeMeetingCodeResponse response = new ChangeMeetingCodeResponse(changedMeetingId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ChangeMeetingCodeResponse {
        private Long meetingId;
    }

    @Data
    static class ChangeMeetingCodeRequest {
        private String meetingCode;
    }
}
