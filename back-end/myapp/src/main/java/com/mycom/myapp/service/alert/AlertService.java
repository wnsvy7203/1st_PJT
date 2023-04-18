package com.mycom.myapp.service.alert;
import com.mycom.myapp.domain.Alert;

import java.util.List;

public interface AlertService {
    // 알림 생성
    public Long createAlert(Long userSeq, Long fromSeq, String alertCode, String fromName);

    // 알림 조회
    public List<Alert> listAlert(Long userSeq);

    // 알림 상세 조회
    public Alert detailAlert(Long alertId);

    // 알림 삭제
    public void deleteAlert(Long alertId);

    // 알림 읽음
    public void changeReadYn(Long alertId);
}
