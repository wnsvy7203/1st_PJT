package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "alert")
// 알림
public class Alert {

    @Id @GeneratedValue
    @Column(name = "alert_id")
    private Long id;

    @Column(name = "from_seq", nullable = false)
    private Long fromSeq;

    @Column(name = "alert_date", nullable = false)
    private LocalDateTime alertDate;

    @Column(name = "read_yn", nullable = false)
    private boolean readYn;

    @Column(name = "alert_code", nullable = false)
    private String alertCode;

    @Column(name = "approve_yn")
    private Boolean approveYn;

    @Column(name = "from_name", nullable = false)
    private String name;

    // 유저
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_seq")
    @JsonIgnore
    private User user;

    // 연관관계 메서드
    public void setUser(User user) {
        this.user = user;
        user.getAlerts().add(this);
    }

    // 생성 메서드
    public static Alert createAlert(User user, Long fromSeq, String alertCode, String name) {
        Alert alert = new Alert();

        alert.setUser(user);
        alert.setFromSeq(fromSeq);
        alert.setAlertCode(alertCode);
        alert.setAlertDate(LocalDateTime.now());
        alert.setReadYn(false);
        alert.setName(name);

        return alert;
    }
}
