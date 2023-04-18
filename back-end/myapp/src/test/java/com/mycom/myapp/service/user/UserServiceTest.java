package com.mycom.myapp.service.user;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.domain.user.Parent;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.domain.user.User;
import com.mycom.myapp.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입() {
        // given
        Master user1 = new Master();
        user1.setUserId("master");
        user1.setPassword("qwer1234!");
        user1.setName("MASTER");
        user1.setEmail("ssafy@master.com");
        user1.setUserCode("UM");
        user1.setPhoneNumber("01011112222");
        user1.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        user1.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Teacher user2 = new Teacher();
        user2.setUserId("teacher");
        user2.setPassword("qwer1234!");
        user2.setName("TEACHER");
        user2.setEmail("ssafy@teacher.com");
        user2.setUserCode("UT");
        user2.setPhoneNumber("01011112222");
        user2.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        user2.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Parent user3 = new Parent();
        user3.setUserId("parent");
        user3.setPassword("qwer1234!");
        user3.setName("PARENT");
        user3.setEmail("ssafy@parent.com");
        user3.setUserCode("UP");
        user3.setPhoneNumber("01011112222");
        user3.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        user3.setProfileImageUrl("abc1lkjadfglkasdfl0");

        // when
        Long masterSeq = userService.joinMaster(user1);
        Long teacherSeq = userService.joinTeacher(user2);
        Long parentSeq = userService.joinParent(user3);

        User foundMaster = userRepository.findById(masterSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
        User foundTeacher = userRepository.findById(teacherSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
        User foundParent = userRepository.findById(parentSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        //then
        assertEquals(foundMaster.getUserId(), user1.getUserId());
        assertEquals(foundTeacher.getUserId(), user2.getUserId());
        assertEquals(foundParent.getUserId(), user3.getUserId());
    }

    @Test
    public void 회원정보조회() {
        // given
        Master user = new Master();
        user.setUserId("master");
        user.setPassword("qwer1234!");
        user.setName("MASTER");
        user.setEmail("ssafy@master.com");
        user.setUserCode("UM");
        user.setPhoneNumber("01011112222");
        user.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        user.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long userSeq = userService.joinMaster(user);

        // when
        User foundUser = userService.refer(userSeq);

        // then
        assertEquals(foundUser.getName(), user.getName());
    }

    @Test
    void 회원정보수정() {
        // given
        Master user = new Master();
        user.setUserId("master");
        user.setPassword("qwer1234!");
        user.setName("MASTER");
        user.setEmail("ssafy@master.com");
        user.setUserCode("UM");
        user.setPhoneNumber("01011112222");
        user.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        user.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long userSeq = userService.joinMaster(user);

        User updateUser = new User();
        updateUser.setUserId("master");
        updateUser.setPassword("qwer1234!");
        updateUser.setName("학원장");
        updateUser.setEmail("master@master.com");
        updateUser.setUserCode("UM");
        updateUser.setPhoneNumber("01011112222");
        updateUser.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        updateUser.setProfileImageUrl("abc1lkjadfglkasdfl0");

        // when
        Long updateUserSeq = userService.update(userSeq, updateUser);

        // then
        User updateFoundUser = userRepository.findById(updateUserSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
        assertNotEquals(updateFoundUser.getName(), "MASTER");
    }

    @Test
    void 회원정보삭제() {
        // given
        Master user1 = new Master();
        user1.setUserId("master");
        user1.setPassword("qwer1234!");
        user1.setName("MASTER");
        user1.setEmail("ssafy@master.com");
        user1.setUserCode("UM");
        user1.setPhoneNumber("01011112222");
        user1.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        user1.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Teacher user2 = new Teacher();
        user2.setUserId("teacher");
        user2.setPassword("qwer1234!");
        user2.setName("TEACHER");
        user2.setEmail("ssafy@teacher.com");
        user2.setUserCode("UT");
        user2.setPhoneNumber("01011112222");
        user2.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        user2.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Parent user3 = new Parent();
        user3.setUserId("parent");
        user3.setPassword("qwer1234!");
        user3.setName("PARENT");
        user3.setEmail("ssafy@parent.com");
        user3.setUserCode("UP");
        user3.setPhoneNumber("01011112222");
        user3.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        user3.setProfileImageUrl("abc1lkjadfglkasdfl0");

        Long masterSeq = userService.joinMaster(user1);
        Long teacherSeq = userService.joinTeacher(user2);
        Long parentSeq = userService.joinParent(user3);

        // when
        userService.delete(masterSeq);

        // then

    }

    @Test
    void login() {
    }

    @Test
    void 아이디찾기() {

    }

    @Test
    void checkId() {
    }

    @Test
    void findPassword() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void findUserSeq() {
    }
}