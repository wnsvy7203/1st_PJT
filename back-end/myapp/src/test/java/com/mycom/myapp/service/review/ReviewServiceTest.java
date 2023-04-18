package com.mycom.myapp.service.review;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.Meeting;
import com.mycom.myapp.domain.Review;
import com.mycom.myapp.domain.user.Parent;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.repository.MeetingRepository;
import com.mycom.myapp.repository.ReviewRepository;
import com.mycom.myapp.service.meeting.MeetingService;
import com.mycom.myapp.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    MeetingService meetingService;
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void 리뷰등록() {
        // given
        // 상담 등록
        // 학부모 등록
        Parent parent = new Parent();
        parent.setUserId("parent");
        parent.setPassword("qwer1234!");
        parent.setName("PARENT");
        parent.setEmail("ssafy@parent.com");
        parent.setUserCode("UP");
        parent.setPhoneNumber("01011112222");
        parent.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        parent.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long parentSeq = userService.joinParent(parent);

        // 강사 등록
        Teacher teacher = new Teacher();
        teacher.setUserId("teacher");
        teacher.setPassword("qwer1234!");
        teacher.setName("TEACHER");
        teacher.setEmail("ssafy@teacher.com");
        teacher.setUserCode("UT");
        teacher.setPhoneNumber("01011112222");
        teacher.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        teacher.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long teacherSeq = userService.joinTeacher(teacher);

        Map<String, Long> res = meetingService.createMeeting(parentSeq, teacherSeq, "MW", "싸피학원", "1반", "2022-01-12 13:02:03", "이자녀");

        Long meetingId = res.get("meetingId");

        // 리뷰 등록
        // when
        Long reviewId = reviewService.createReview(meetingId, "완전 좋습니다. ", parent.getName());
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        // then
        assertEquals(review.getMeeting().getId(), meetingId);
        assertEquals(review.getParentName(), "PARENT");

        // 리뷰 조회
        // when
        ReviewRepository.ReviewDetailResponse reviewDetail = reviewService.detailReview(meetingId);

        // then
        assertEquals(reviewDetail.getParent_name(), "PARENT");
        assertEquals(reviewDetail.getStudent_name(), "이자녀");

        // 리뷰 수정
        // when
        reviewService.updateReview(reviewId, "수정한 내용");
        Review updateReview = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        // then
        assertEquals(updateReview.getParentName(), "PARENT");
        assertEquals(updateReview.getContent(), "수정한 내용");
    }
}