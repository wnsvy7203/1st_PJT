package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // 특이사항 받아오기
    @Query(value = "SELECT n.note_id, n.note_content FROM note n JOIN studentclass sc ON n.student_class_id = sc.student_class_id WHERE sc.class_id = :classId AND sc.student_id = :studentId", nativeQuery = true)
    public List<NoteInfo> findByclassIdAndStudentId(@Param("classId") Long classId, @Param("studentId") Long studentId);

    public interface NoteInfo {
        Long getNote_id();
        String getNote_content();
    }
}
