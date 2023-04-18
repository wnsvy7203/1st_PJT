package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "progress")
// 진도
public class Progress {

    @Id @GeneratedValue
    @Column(name = "progress_id")
    private Long id;

    @Column(name = "week")
    private Integer week;

    @Column(name = "content")
    private String content;

    // 반(class가 예약어라 clas)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private Class clas;

    // 연관관계 메서드
    public void setClass(Class clas) {
        this.clas = clas;
        clas.getProgresses().add(this);
    }

    // 생성 메서드
    public static Progress createProgress(Class clas, Integer week, String content) {
        Progress progress = new Progress();

        progress.setClass(clas);
        progress.setWeek(week);
        progress.setContent(content);

        return progress;
    }
}
