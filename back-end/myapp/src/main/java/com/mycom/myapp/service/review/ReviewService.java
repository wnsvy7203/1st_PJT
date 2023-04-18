package com.mycom.myapp.service.review;

import com.mycom.myapp.domain.Review;
import com.mycom.myapp.repository.ReviewRepository;

public interface ReviewService {

    // 리뷰 등록
    public Long createReview(Long meetingId, String content, String parentName);

    // 리뷰 조회
    public ReviewRepository.ReviewDetailResponse detailReview(Long reviewId);

    // 리뷰 수정
    public Long updateReview(Long reviewId, String content);

    // 리뷰 삭제
    public void deleteReview(Long reviewId);
}
