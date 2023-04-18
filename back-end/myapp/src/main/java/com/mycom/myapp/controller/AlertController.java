package com.mycom.myapp.controller;

import com.mycom.myapp.domain.Alert;
import com.mycom.myapp.service.alert.AlertService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
public class AlertController {

    private final AlertService alertService;

    // 알림 등록
    @ApiOperation(value = "알림 등록", notes = "알림을 생성한다. ")
    @PostMapping("/alerts/users/{userSeq}")
    public ResponseEntity<CreateAlertResponse> createAlert(@PathVariable Long userSeq, @RequestBody CreateAlertRequest request) {
        Long alertId = alertService.createAlert(userSeq, request.getFromSeq(), request.getAlertCode(), request.getFromName());

        CreateAlertResponse response = new CreateAlertResponse(alertId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class CreateAlertResponse {
        private Long alertId;
    }

    @Data
    static class CreateAlertRequest {
        private Long fromSeq;
        private String alertCode;
        private String fromName;
    }

    // 알림 조회
    @ApiOperation(value = "알림 조회", notes = "알림 목록을 조회한다. ")
    @GetMapping("/alerts/users/{userSeq}")
    public ResponseEntity<ListAlertResponse> listAlert(@PathVariable Long userSeq) {
        List<Alert> list = alertService.listAlert(userSeq);

        ListAlertResponse response = new ListAlertResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListAlertResponse {
        private List<Alert> alertList;
    }

    // 알림 상세 조회
    @ApiOperation(value = "알림 상세 조회", notes = "알림을 조회한다. ")
    @GetMapping("/alerts/{alertId}")
    public ResponseEntity<DetailAlertResponse> detailAlert(@PathVariable Long alertId) {
        Alert alert = alertService.detailAlert(alertId);

        DetailAlertResponse response = new DetailAlertResponse(alert.getFromSeq(), alert.getAlertDate(), alert.isReadYn(), alert.getAlertCode());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class DetailAlertResponse {
        private Long fromSeq;
        private LocalDateTime alertDate;
        private boolean readYn;
        private String alertCode;
    }

    // 알림 삭제
    @ApiOperation(value = "알림 삭제", notes = "알림을 삭제한다. ")
    @DeleteMapping("/alerts/{alertId}")
    public ResponseEntity deleteAlert(@PathVariable Long alertId) {
        alertService.deleteAlert(alertId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 알림 읽음
    @ApiOperation(value = "알림 읽음", notes = "알림 읽음 상태를 변경한다. ")
    @PutMapping("/alerts/{alertId}")
    public ResponseEntity changeReadYn(@PathVariable Long alertId) {
        alertService.changeReadYn(alertId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
