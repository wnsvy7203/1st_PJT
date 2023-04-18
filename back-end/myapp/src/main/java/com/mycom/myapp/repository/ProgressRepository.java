package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    public List<Progress> findAllByClasId(Long classId);
}
