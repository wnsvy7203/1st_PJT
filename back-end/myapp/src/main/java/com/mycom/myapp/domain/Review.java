package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "review")
// 리뷰
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(name = "review_content")
    private String content;

    @Column(name = "parent_name")
    private String parentName;

    // 상담
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "meeting_id")
    @JsonIgnore
    private Meeting meeting;

    // 연관관계 메서드
    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
        meeting.getReviews().add(this);
    }

    // 생성 메서드
    public static Review createReview (Meeting meeting, String content, String parentName) {
        Review review = new Review();

        review.setMeeting(meeting);
        review.setContent(content);
        review.setParentName(parentName);

        return review;
    }
}
