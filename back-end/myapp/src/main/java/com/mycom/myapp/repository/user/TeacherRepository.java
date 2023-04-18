package com.mycom.myapp.repository.user;

import com.mycom.myapp.domain.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query(value = "SELECT t.subject_code FROM teacher t join teacheracademy ta on t.teacher_seq = ta.teacher_seq WHERE ta.academy_id = :academyId AND ta.approve_yn = true", nativeQuery = true)
    public List<TeacherInfo> findByacademyId(@Param("academyId") Long academyId);

    public interface TeacherInfo {
        String getSubject_code();
    }
}
