package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Gugun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GugunRepository extends JpaRepository<Gugun, String> {

    // 시도 코드로 구군 리스트 가져오기
    @Query(value = "SELECT * FROM gugun_code g WHERE g.sido_code = :sido", nativeQuery = true)
    public List<Gugun> findBysidoCode(String sido);
}
