package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Attendancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendancyRepository extends JpaRepository<Attendancy, Long> {

    @Query(value = "SELECT * FROM attendancy a WHERE a.class_id = :classId AND a.student_id = :studentId AND a.attendancy_date = :attendancyDate", nativeQuery = true)
    public Attendancy findByclassIdAndStudentIdAndAttendancyDate(@Param("classId") Long classId, @Param("studentId") Long studentId, @Param("attendancyDate") String attendancyDate);

    @Query(value = "SELECT * FROM attendancy a WHERE a.attendancy_date = :attendancyDate", nativeQuery = true)
    public List<Attendancy> findByattendancyDate(String attendancyDate);

    @Query(value = "SELECT * FROM attendancy a WHERE a.class_id = :classId AND a.attendancy_date = :attendancyDate", nativeQuery = true)
    public List<Attendancy> findByclassIdAndAttendancyDate(@Param("classId") Long classId, @Param("attendancyDate") String attendancyDate);

    @Query(value = "SELECT * FROM attendancy a WHERE a.class_id = :classId AND a.student_id = :studentId", nativeQuery = true)
    public List<Attendancy> findByclassIdAndStudentId(@Param("classId") Long classId, @Param("studentId") Long studentId);
}
