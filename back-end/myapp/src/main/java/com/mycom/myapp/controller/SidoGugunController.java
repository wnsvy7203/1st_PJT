package com.mycom.myapp.controller;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.Gugun;
import com.mycom.myapp.domain.Sido;
import com.mycom.myapp.repository.AcademyRepository;
import com.mycom.myapp.repository.SidoRepository;
import com.mycom.myapp.service.sidogugun.SidoGugunService;
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
public class SidoGugunController {

    private final SidoGugunService sidoGugunService;

    // 시도 리스트 가져오기
    @ApiOperation(value = "시도 리스트", notes = "시도 리스트를 가져온다. ")
    @GetMapping("/sido")
    public ResponseEntity<SidoListResponse> sidoList() {
        List<SidoRepository.SidoInfo> list = sidoGugunService.listSido();

        SidoListResponse response = new SidoListResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class SidoListResponse {
        private List<SidoRepository.SidoInfo> list;
    }

    // 시도에 따른 구군 리스트 가져오기
    @ApiOperation(value = "구군 리스트", notes = "시도에 따른 구군 리스트를 가져온다. ")
    @GetMapping("/gugun/{sido}")
    public ResponseEntity<GugunListResponse> gugunList(@PathVariable String sido) {
        List<Gugun> list = sidoGugunService.listGugun(sido);

        GugunListResponse response = new GugunListResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class GugunListResponse {
        List<Gugun> list;
    }

    // 시도 구군 정보로 학원 리스트 가져오기
    @ApiOperation(value = "지역별 학원 목록", notes = "시도 구군 정보로 학원 리스트를 가져온다. ")
    @PostMapping("/academies")
    public ResponseEntity<ListAcademySidoGugunResponse> listAcademy(@RequestBody ListAcademySidoGugunRequest request) {
        List<AcademyRepository.AcademyInfo> list = sidoGugunService.listAcademyByAddress(request.getSido(), request.getGugun());

        ListAcademySidoGugunResponse response = new ListAcademySidoGugunResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListAcademySidoGugunResponse {
        private List<AcademyRepository.AcademyInfo> list;
    }

    @Data
    static class ListAcademySidoGugunRequest {
        private String sido;
        private String gugun;
    }
}
