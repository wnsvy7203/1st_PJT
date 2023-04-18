package com.mycom.myapp.controller;

import com.mycom.myapp.domain.Academy;
import com.mycom.myapp.repository.AcademyRepository;
import com.mycom.myapp.service.academy.AcademyService;
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
public class AcademyController {

    private final AcademyService academyService;

    // 학원 목록 조회
    @ApiOperation(value = "학원 목록 조회", notes = "학원 목록을 조회한다. ")
    @GetMapping("/academies")
    public ResponseEntity<ListAcademyResponse> listAcademy () {
        List<Academy> list = academyService.listAcademy();

        ListAcademyResponse response = new ListAcademyResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListAcademyResponse {
        private List<Academy> list;
    }

    // 학원 정보 상세 조회
    @ApiOperation(value = "학원 정보 상세 조회", notes = "학원 상세 정보를 조회한다. ")
    @GetMapping("/academies/{academyId}")
    public ResponseEntity<DetailAcademyResponse> detailAcademy (@PathVariable Long academyId) {
        Academy academy = academyService.detailAcademy(academyId);

        DetailAcademyResponse response = new DetailAcademyResponse(academy.getId(), academy.getMaster().getSeq(), academy.getName(), academy.getAddress().getZipCode(), academy.getAddress().getFirstAddress(), academy.getAddress().getLastAddress(), academy.getPhoneNumber(), academy.getIntroduce());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class DetailAcademyResponse {
        private Long academyId;
        private Long masterSeq;
        private String academyName;
        private String zipCode;
        private String firstAddress;
        private String lastAddress;
        private String academyPhoneNumber;
        private String academyIntroduce;
    }
}
