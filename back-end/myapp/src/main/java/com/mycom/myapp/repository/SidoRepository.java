package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SidoRepository extends JpaRepository<Sido, String> {

    // 시도 리스트 가져오기
    @Query(value = "SELECT s.code, s.name FROM sido_code s", nativeQuery = true)
    public List<SidoInfo> findAllSido();

    public interface SidoInfo {
        String getCode();
        String getName();
    }
}
