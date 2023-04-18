package com.mycom.myapp.service.master;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.MasterSchedule;
import com.mycom.myapp.domain.TeacherSchedule;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MasterServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    MasterService masterService;

    @Test
    @Rollback(value = false)
    public void 특정날짜_학원장_일정조회() {

        //학원장 회원가입
        Master master = new Master();
        master.setUserId("master");
        master.setPassword("qwer1234!");
        master.setName("MASTER");
        master.setEmail("ssafy@master.com");
        master.setUserCode("UM");
        master.setPhoneNumber("01011112222");
        master.setAddress(new Address("11111", "서울특별시 강남구", "역삼동"));
        master.setProfileImageUrl("abc1lkjadfglkasdfl0");

        userService.joinMaster(master);

        // 강사 일정 등록
        masterService.createMasterSchedule(master.getSeq(), "S1", "20220101", 10, 00, 11, 20);
        masterService.createMasterSchedule(master.getSeq(), "S2", "20220101", 10, 00, 11, 20);
        masterService.createMasterSchedule(master.getSeq(), "S2", "20230103", 10, 00, 11, 20);
        masterService.createMasterSchedule(master.getSeq(), "S3", "20230103", 10, 00, 11, 20);
        masterService.createMasterSchedule(master.getSeq(), "S1", "20230202", 10, 00, 11, 20);

        List<MasterSchedule> list1 = masterService.listMasterScheduleDate(master.getSeq(), "20220101");
        List<MasterSchedule> list2 = masterService.listMasterScheduleDate(master.getSeq(), "20230103");
        List<MasterSchedule> list3 = masterService.listMasterScheduleDate(master.getSeq(), "20230202");

        assertEquals(list1.size(), 2);
        assertEquals(list2.size(), 2);
        assertEquals(list3.size(), 1);
    }

}