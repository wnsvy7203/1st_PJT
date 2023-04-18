package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    public List<Class> findByTeacherSeqAndAcademyId(Long teacherSeq, Long academyId);

    // 강사-학원-반 전체 조회
    @Query(value = "SELECT c.clas_id, c.grade_code, c.class_name FROM class c WHERE c.teacher_seq = :teacherSeq AND c.academy_id = :academyId", nativeQuery = true)
    public List<ClassInfoResponse> findByTeacherSeqAndAcademyid(@Param("teacherSeq") Long teacherSeq, @Param("academyId") Long academyId);

    // 학원-반 전체 조회
    @Query(value = "SELECT c.clas_id, c.grade_code, c.class_name FROM class c WHERE c.academy_id = :academyId", nativeQuery = true)
    public List<ClassInfoResponse> findByacademyId(@Param("academyId") Long academyId);

    public interface ClassInfoResponse {
        Long getClas_id();
        String getGrade_code();
        String getClass_name();
    }

    // 강사 - 학원 - 반정보 한번에 조회
    @Query(value = "SELECT a.academy_id, a.academy_name, c.clas_id, c.class_name FROM class c join academy a on c.academy_id = a.academy_id WHERE c.teacher_seq = :teacherSeq", nativeQuery = true)
    public List<AcademyClassInfo> findAcademyAndClassByTeacherSeq(@Param("teacherSeq") Long teacherSeq);

    public interface AcademyClassInfo {
        Long getAcademy_id();
        String getAcademy_name();
        Long getClas_id();
        String getClass_name();
    }

    // 강사가 반 기본정보 조회
    @Query(value = "SELECT c.clas_id, c.class_name, c.subject_code, c.grade_code, c.teacher_seq, cs.day_of_the_week, cs.start_time_hour, cs.start_time_minute, cs.end_time_hour, cs.end_time_minute FROM class c join classschedule cs ON c.clas_id = cs.class_id WHERE c.clas_id = :classId", nativeQuery = true)
    public List<TeacherClassInfo> findByclassId(@Param("classId") Long classId);

    public interface TeacherClassInfo {
        Long getClas_id();
        String getClass_name();
        String getSubject_code();
        String getGrade_code();
        Long getTeacher_seq();
        String getDay_of_the_week();
        Integer getStart_time_hour();
        Integer getStart_time_minute();
        Integer getEnd_time_hour();
        Integer getEnd_time_minute();
    }

    // 학부모-자녀 학원-반 전체 조회
    @Query(value = "SELECT c.clas_id, c.class_name, a.academy_name FROM studentclass sc join class c on c.clas_id = sc.class_id join academy a on a.academy_id = c.academy_id WHERE sc.student_id = :studentId", nativeQuery = true)
    public List<StudentClassResponse> findByStudentIdAndAcademyid(@Param("studentId") Long studentId);

    public interface StudentClassResponse {
        Long getClas_id();
        String getClass_name();
        String getAcademy_name();
    }

    public List<Class> findByTeacherSeq(Long teacherSeq);

    // 반ID로 학원장Seq, 강사Seq, 학원이름, 반이름 받기
    @Query(value = "SELECT a.master_seq, c.teacher_seq, a.academy_name, c.class_name FROM class c JOIN academy a ON c.academy_id = a.academy_id WHERE c.clas_id = :classId", nativeQuery = true)
    public MeetingInfo findByclassid(@Param("classId") Long classId);

    public interface MeetingInfo {
        Long getMaster_seq();
        Long getTeacher_seq();
        String getAcademy_name();
        String getClass_name();
    }

    // 강사 Seq로 강사의 모든 반 시간 가져오기
    @Query(value = "SELECT cs.day_of_the_week, cs.start_time_hour, cs.start_time_minute, cs.end_time_hour, cs.end_time_minute FROM class c JOIN classschedule cs ON c.clas_id = cs.class_id WHERE c.teacher_seq = :teacherSeq", nativeQuery = true)
    public List<ClassScheduleInfo> findAllScheduleByTeacherSeq(@Param("teacherSeq") Long teacherSeq);

    public interface ClassScheduleInfo {
        String getDay_of_the_week();
        Integer getStart_time_hour();
        Integer getStart_time_minute();
        Integer getEnd_time_hour();
        Integer getEnd_time_minute();
    }
}
