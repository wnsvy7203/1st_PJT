package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository  extends JpaRepository<Review, Long> {

    // 리뷰 조회
    @Query(value = "SELECT r.review_id, r.review_content, r.parent_name, m.student_name FROM review r join meeting m ON r.meeting_id = m.meeting_id WHERE r.meeting_id = :meetingId", nativeQuery = true)
    public ReviewDetailResponse findByMeetingId(@Param("meetingId") Long meetingId);

    public interface ReviewDetailResponse {
        Long getReview_id();
        String getReview_content();
        String getParent_name();
        String getStudent_name();
    }

}
