package com.mycom.myapp.repository;

import com.mycom.myapp.domain.WorkHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkhistoryRepository extends JpaRepository<WorkHistory, Long> {
    public List<WorkHistory> findByTeacherSeq(Long teacherSeq);
}
