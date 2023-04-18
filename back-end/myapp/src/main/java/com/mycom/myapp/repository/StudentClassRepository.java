package com.mycom.myapp.repository;

import com.mycom.myapp.domain.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {

    // 반 학생 ID 모두 조회
    @Query(value = "SELECT sc.student_id FROM studentclass sc WHERE sc.class_id = :classId", nativeQuery = true)
    public List<StudentId> findByClassId(@Param("classId") Long classId);

    public interface StudentId {
        Long getStudent_id();
    }

    // 특이사항 등록
    @Query(value = "SELECT * FROM studentclass sc WHERE sc.class_id = :classId AND sc.student_id = :studentId", nativeQuery = true)
    public StudentClass findByclassIdAndStudentId(@Param("classId") Long classId, @Param("studentId") Long studentId);

    // 반에서 학생 삭제
    public void delete(StudentClass studentClass);
}
