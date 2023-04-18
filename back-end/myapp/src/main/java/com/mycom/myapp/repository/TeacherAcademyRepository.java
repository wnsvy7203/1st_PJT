package com.mycom.myapp.repository;

import com.mycom.myapp.domain.TeacherAcademy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherAcademyRepository extends JpaRepository<TeacherAcademy, Long> {

    // 강사-학원 삭제
    public void deleteByTeacherSeqAndAcademyId(Long teacherSeq, Long academyId);

    // 학원장이 강사 삭제
    void deleteByTeacherSeq(Long teacherSeq);

    // 강사 중복신청 체크용
    public boolean existsByTeacherSeqAndAcademyId(Long teacherSeq, Long academyId);
}
