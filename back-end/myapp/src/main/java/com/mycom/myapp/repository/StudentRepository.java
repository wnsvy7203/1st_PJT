package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // 학부모-학생 전체 조회
    @Query(value = "SELECT s.student_id, s.student_name, s.student_age, s.student_phone_number, s.student_school, s.priority, s.grade_code FROM student s WHERE s.parent_seq = :parentSeq", nativeQuery = true)
    public List<StudentRepository.StudentInfo> findByParentSeq(@Param("parentSeq") Long parentSeq);

    // 학원-학생 전체 조회
    @Query(value = "SELECT s.student_id, s.student_name, s.student_age, s.student_phone_number, s.student_school, s.priority, s.grade_code, s.student_profile_image_url FROM student s join studentacademy sa ON s.student_id = sa.student_id WHERE sa.academy_id = :academyId AND sa.approve_yn = true", nativeQuery = true)
    public List<StudentRepository.StudentInfo> findByacademyId(@Param("academyId") Long academyId);

    public interface StudentInfo {
        Long getStudent_id();
        String getStudent_name();
        Integer getStudent_age();
        String getStudent_phone_number();
        String getStudent_school();
        Integer getPriority();
        String getGrade_code();
        String getStudent_profile_image_url();
    }

    // 학부모-학생 상세 조회
    @Query(value = "SELECT s.student_id, s.student_name, s.student_age, s.student_phone_number, s.student_school, s.priority, s.grade_code FROM student s WHERE s.student_id = :studentId", nativeQuery = true)
    public StudentInfo findBystudentId(@Param("studentId") Long studentId);
}
