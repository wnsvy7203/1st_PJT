package com.mycom.myapp.repository;

import com.mycom.myapp.domain.StudentAcademy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAcademyRepository extends JpaRepository<StudentAcademy, Long> {

    // 학부모-자녀 학원 삭제
    public void deleteByStudentIdAndAcademyId(Long studentId, Long academyId);

    // 학생 삭제
    void deleteByStudentId(Long studentId);

    // 학생 중복 신청 조회용
    public Optional<StudentAcademy> findByStudentIdAndAcademyId(Long studentId, Long academyId);
}
