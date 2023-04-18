package com.mycom.myapp.controller;

import com.mycom.myapp.domain.Inquire;
import com.mycom.myapp.repository.user.UserRepository;
import com.mycom.myapp.service.admin.AdminService;
import com.mycom.myapp.service.inquire.InquireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public class AdminController {

    private final AdminService adminService;
    private final InquireService inquireService;

    // 학원 생성
    @ApiOperation(value = "학원 생성", notes = "학원을 생성한다. ")
    @PostMapping("/admin/academies/users/{masterSeq}")
    public ResponseEntity<CreateAcademyResponse> createAcademy(@PathVariable Long masterSeq, @RequestBody CreateAcademyRequest request) {
        Long academyId = adminService.createAcademy(masterSeq, request.academyName, request.zipCode, request.firstAddress, request.lastAddress, request.phoneNumber, request.introduce);

        CreateAcademyResponse createAcademyResponse = new CreateAcademyResponse(masterSeq, academyId);

        return new ResponseEntity<>(createAcademyResponse, HttpStatus.OK);
    }

    @Data
    static class CreateAcademyRequest {
        private String academyName;
        private String zipCode;
        private String firstAddress;
        private String lastAddress;
        private String phoneNumber;
        private String introduce;
    }

    @Data
    @AllArgsConstructor
    static class CreateAcademyResponse {
        private Long userSeq;
        private Long academyId;
    }

    // 문의 목록 조회
    @ApiOperation(value = "문의 목록 조회", notes = "문의 게시판 목록을 조회한다. ")
    @GetMapping("/admins/inquires")
    public ResponseEntity<InquireListResponse> listInquire() {
        List<Inquire>  list = inquireService.listInquires();

        InquireListResponse response = new InquireListResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class InquireListResponse {
        List<Inquire> list;
    }

    // 문의 상세 조회
    @ApiOperation(value = "문의 상세 조회", notes = "문의 글을 상세 조회한다. ")
    @GetMapping("/admins/inquires/{inquireId}")
    public ResponseEntity<DetailInquireResponse> detailInquire(@PathVariable Long inquireId) {
        Inquire inquire = inquireService.detailInquire(inquireId);

        DetailInquireResponse response = new DetailInquireResponse(inquire);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class DetailInquireResponse {
        private Inquire inquire;
    }

    // 전체 회원 조회
    @ApiOperation(value = "전체 회원 조회", notes = "전체 회원을 조회한다. ")
    @GetMapping("/admins/users")
    public ResponseEntity<ListUserResponse> listUser() {
        List<UserRepository.UserList> list = adminService.findAllUser();

        ListUserResponse response = new ListUserResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListUserResponse {
        List<UserRepository.UserList> list;
    }
}
