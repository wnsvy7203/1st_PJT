package com.mycom.myapp.repository;

import com.mycom.myapp.domain.TeacherSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TeacherScheduleRepository extends JpaRepository<TeacherSchedule, Long> {
    public List<TeacherSchedule> findByTeacherSeq(Long teacherSeq);

    @Query(value = "SELECT * FROM teacherschedule ts WHERE ts.teacher_seq = :teacherSeq AND ts.schedule_date = :date", nativeQuery = true)
    public List<TeacherSchedule> findByteacherSeqAndDate(@Param("teacherSeq") Long teacherSeq, @Param("date") String date);

    public void deleteByTeacherSeqAndScheduleDateAndStartTimeHourAndStartTimeMinute(Long teacherSeq, String scheduleDate, Integer startTimeHour, Integer startTimeMinute);
}
