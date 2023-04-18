package com.mycom.myapp.service.review;

import com.mycom.myapp.domain.Meeting;
import com.mycom.myapp.domain.Review;
import com.mycom.myapp.repository.MeetingRepository;
import com.mycom.myapp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final MeetingRepository meetingRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰 등록
    @Override
    public Long createReview(Long meetingId, String content, String parentName) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Review review = Review.createReview(meeting, content, parentName);
        Long reviewId = reviewRepository.save(review).getId();

        return reviewId;
    }

    // 리뷰 조회
    @Override
    public ReviewRepository.ReviewDetailResponse detailReview(Long meetingId) {
        return reviewRepository.findByMeetingId(meetingId);
    }

    // 리뷰 수정
    @Override
    public Long updateReview(Long reviewId, String content) {
        Review foundReview = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        foundReview.setContent(content);

        return foundReview.getId();
    }

    // 리뷰 삭제
    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
