package com.mycom.myapp.repository;

import com.mycom.myapp.domain.StudentTestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentTestInfoRepository extends JpaRepository<StudentTestInfo, Long> {

    // 시험ID로 학생 시험정보 목록 받기
    @Query(value = "SELECT sti.student_id, sti.student_name, sti.score FROM studenttestinfo sti WHERE sti.test_info_id = :testInfoId", nativeQuery = true)
    public List<StudentTestInfo> findBytestInfoId(@Param("testInfoId") Long testInfoId);

    public interface StudentTestInfo {
        Long getStudent_id();
        String getStudent_name();
        Integer getScore();
    }

    public List<com.mycom.myapp.domain.StudentTestInfo> findByTestInfoId(Long testInfoId);

    // 반ID로 시험정보 가져오기
    @Query(value = "SELECT ti.test_info_id, ti.test_info_name, ti.test_date, count(sti.student_id) as total_cnt, round(avg(sti.score), 1) as avg_score FROM testinfo ti join studenttestinfo sti on ti.test_info_id = sti.test_info_id WHERE ti.class_id = :classId GROUP BY ti.test_info_id;", nativeQuery = true)
    public List<TestScoreByClassId> findTestInfoByclassId(@Param("classId") Long classId);

    public interface TestScoreByClassId {
        Long getTest_info_id();
        String getTest_info_name();
        String getTest_date();
        Integer getScore();
        Integer getTotal_cnt();
        Double getAvg_score();
    }

    // 석차 리스트 가져오기
    @Query(value = "SELECT sti.score, sti.student_id FROM testinfo ti join studenttestinfo sti on ti.test_info_id = sti.test_info_id WHERE ti.test_info_id = :testInfoId ORDER BY score DESC", nativeQuery = true)
    public List<TestScoreRank> findTestScoreRank(@Param("testInfoId") Long testInfoId);

    public interface TestScoreRank {
        Integer getScore();
        Long getStudent_id();
    }

    // 시험 친 총 인원 수 가져오기
    @Query(value = "SELECT count(*) as 'totalCnt' FROM studenttestinfo sti WHERE sti.test_info_id = :testInfoId", nativeQuery = true)
    public Integer findtotalcnt(@Param("testInfoId") Long testInfoId);
}
