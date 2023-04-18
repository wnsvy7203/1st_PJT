package com.mycom.myapp.service.alert;

import com.mycom.myapp.domain.Alert;
import com.mycom.myapp.domain.user.User;
import com.mycom.myapp.repository.AlertRepository;
import com.mycom.myapp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    // 알림 생성
    @Override
    public Long createAlert(Long userSeq, Long fromSeq, String alertCode, String fromName) {
        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Alert alert = Alert.createAlert(user, fromSeq, alertCode, fromName);

        Long alertId = alertRepository.save(alert).getId();

        return alertId;
    }

    // 알림 조회
    @Override
    @Transactional(readOnly = true)
    public List<Alert> listAlert(Long userSeq) {
        return alertRepository.findByUserSeq(userSeq);
    }

    // 알림 상세 조회
    @Override
    @Transactional(readOnly = true)
    public Alert detailAlert(Long alertId) {
        return alertRepository.findById(alertId).orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    // 알림 삭제
    @Override
    public void deleteAlert(Long alertId) {
        alertRepository.deleteById(alertId);
    }

    // 알림 읽음
    @Override
    public void changeReadYn(Long alertId) {
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        alert.setReadYn(true);
    }
}
