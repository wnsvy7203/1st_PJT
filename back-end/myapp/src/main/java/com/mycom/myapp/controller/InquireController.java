package com.mycom.myapp.controller;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.service.inquire.InquireService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(
        origins = {"http://localhost:8080", "https://i8e206.p.ssafy.io"},
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
)
public class InquireController {

    private final InquireService inquireService;

    @ApiOperation(value = "문의 등록", notes = "학원 등록 문의를 등록한다. ")
    @PostMapping("/inquires")
    public ResponseEntity<CreateInquireResponse> createInquire(@RequestBody CreateInquireRequest request) {
        Long inquireId = inquireService.createInquire(request.getName(), request.getEmail(), request.getPhoneNumber(), request.getUserId(), request.getAcademyName(), request.getZipCode(), request.getFirstAddress(), request.getLastAddress(), request.getAcademyPhoneNumber());

        CreateInquireResponse response = new CreateInquireResponse(inquireId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateInquireResponse {
        private Long inquireId;
    }

    @Data
    static class CreateInquireRequest {
        private String name;
        private String email;
        private String phoneNumber;
        private String userId;
        private String academyName;
        private String zipCode;
        private String firstAddress;
        private String lastAddress;
        private String academyPhoneNumber;
    }
}
