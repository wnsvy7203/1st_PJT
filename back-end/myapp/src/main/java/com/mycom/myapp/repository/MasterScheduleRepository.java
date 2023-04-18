package com.mycom.myapp.repository;

import com.mycom.myapp.domain.MasterSchedule;
import com.mycom.myapp.domain.TeacherSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MasterScheduleRepository extends JpaRepository<MasterSchedule, Long> {
    public List<MasterSchedule> findByMasterSeq(Long masterSeq);

    @Query(value = "SELECT * FROM masterschedule ms WHERE ms.master_seq = :masterSeq AND ms.schedule_date = :date", nativeQuery = true)
    public List<MasterSchedule> findBymasterSeqAndDate(@Param("masterSeq") Long masterSeq, @Param("date") String date);

    public void deleteByMasterSeqAndScheduleDateAndStartTimeHourAndStartTimeMinute(Long masterSeq, String scheduleDate, Integer startTimeHour, Integer startTimeMinute);

}
