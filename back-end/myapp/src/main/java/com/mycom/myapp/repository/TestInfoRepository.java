package com.mycom.myapp.repository;

import com.mycom.myapp.domain.TestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestInfoRepository extends JpaRepository<TestInfo, Long> {

    // 반ID, 시험 날짜로 시험정보 가져오기
    @Query(value = "SELECT ti.test_info_id, ti.test_info_name FROM testinfo ti WHERE ti.class_id = :classId AND ti.test_date LIKE :testDate%", nativeQuery = true)
    public List<ClassTestInfo> findByclassId(@Param("classId") Long classId, @Param("testDate") Integer testDate);

    public interface ClassTestInfo {
        Long getTest_info_id();
        String getTest_info_name();
    }

    // 반ID와 학생ID로 시험 정보 조회
    @Query(value = "SELECT ti.test_info_id, ti.test_info_name, ti.test_date, sti.score FROM testinfo ti JOIN studenttestinfo sti ON ti.test_info_id = sti.test_info_id WHERE ti.class_id = :classId AND sti.student_id = :studentId", nativeQuery = true)
    public List<TestInfoClassIdAndStudentId> findbyclassIdAndstudentId(@Param("classId") Long classId, @Param("studentId") Long studentId);

    public interface TestInfoClassIdAndStudentId {
        Long getTest_info_id();
        String getTest_info_name();
        String getTest_date();
        Integer getScore();
    }

    // test_info_id로 시험 평균 가져오기
    @Query(value = "SELECT round(avg(sti.score), 1) as 'avg' FROM testinfo ti JOIN studenttestinfo sti ON ti.test_info_id = sti.test_info_id WHERE ti.test_info_id = :testInfoId GROUP BY ti.test_info_id", nativeQuery = true)
    public Double findaveragescore(@Param("testInfoId") Long testInfoId);

    // classId와 studentId로 학생이 해당 반에서 친 모든 시험 가져오기
    @Query(value = "SELECT ti.test_info_id, ti.test_date, ti.test_info_name FROM testinfo ti JOIN studenttestinfo sti ON ti.test_info_id = sti.test_info_id WHERE ti.class_id = :classId AND sti.student_id = :studentId ORDER BY ti.test_date DESC", nativeQuery = true)
    public List<GetId> findallTestInfo(@Param("classId") Long classId, @Param("studentId") Long studentId);

    public interface GetId{
        Long getTest_info_id();
        String getTest_date();
        String getTest_info_name();
    }
}
