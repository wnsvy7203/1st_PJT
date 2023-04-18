package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Academy;
import com.mycom.myapp.domain.Attendancy;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AcademyRepository extends JpaRepository<Academy, Long> {
    // 학생-학원 전체 조회
    @Query(value = "SELECT a.academy_id, a.academy_name FROM academy a join studentacademy s ON a.academy_id = s.academy_id WHERE s.student_id = :studentId AND s.approve_yn = true", nativeQuery = true)
    public List<AcademyIdAndNameResponse> findByAcademyAndStudent(@Param("studentId") Long studentId);

    // 강사-학원 전체 조회
    @Query(value = "SELECT a.academy_id, a.academy_name FROM academy a join teacheracademy t ON a.academy_id = t.academy_id WHERE t.teacher_seq = :teacherSeq AND t.approve_yn = true", nativeQuery = true)
    public List<AcademyIdAndNameResponse> findByAcademyAndTeacher(@Param("teacherSeq") Long teacherSeq);

    public interface AcademyIdAndNameResponse {
        Long getAcademy_id();
        String getAcademy_name();
    }

    // 학원장 Seq로 학원정보 조회
    @Query(value = "SELECT a.academy_id, a.academy_name, a.zip_code, a.first_address, a.last_address, a.academy_phone_number, a.academy_introduce FROM academy a WHERE a.master_seq = :masterSeq", nativeQuery = true)
    public AcademyInfoResponse findBymaster(@Param("masterSeq") Long masterSeq);

    public interface AcademyInfoResponse {
        Long getAcademy_id();
        String getAcademy_name();
        String getZip_code();
        String getFirst_address();
        String getLast_address();
        String getAcademy_phone_number();
        String getAcademy_introduce();
    }

    public Academy findByMasterSeq(Long masterSeq);

    // 시도 구군 정보로 학원 리스트 가져오기
    @Query(value = "SELECT a.academy_id, a.academy_name, a.zip_code, a.first_address, a.last_address, a.academy_phone_number FROM academy a WHERE a.first_address LIKE %:address%", nativeQuery = true)
    public List<AcademyInfo> findBysidogugun(@Param("address") String address);

    public interface AcademyInfo {
        Long getAcademy_id();
        String getAcademy_name();
        String getZip_code();
        String getFirst_address();
        String getLast_address();
        String getAcademy_phone_number();
    }
}
