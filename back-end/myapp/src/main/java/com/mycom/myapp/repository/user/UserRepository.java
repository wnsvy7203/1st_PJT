package com.mycom.myapp.repository.user;

import com.mycom.myapp.domain.user.Parent;
import com.mycom.myapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String userName);
    Optional<User> findByUserId(String username);

    boolean existsByUserId(String userId);
    boolean existsByNameAndEmailAndUserId(String name, String email, String userId);

    @Query(value = "SELECT u.user_seq, u.user_name, u.user_phone_number, u.user_email, u.user_profile_image_url FROM user u join teacheracademy ta on u.user_seq = ta.teacher_seq WHERE ta.academy_id = :academyId AND ta.approve_yn = true", nativeQuery = true)
    public List<TeacherInfo> findByacademyId(@Param("academyId") Long academyId);

    public interface TeacherInfo {
        Long getUser_seq();
        String getUser_name();
        String getUser_phone_number();
        String getUser_email();
        String getUser_profile_image_url();
    }

    @Query(value = "SELECT u.user_name, u.user_phone_number, u.user_email, u.user_profile_image_url FROM user u WHERE u.user_seq = :teacherSeq", nativeQuery = true)
    public TeacherDeatilInfo findByteacherSeq(@Param("teacherSeq") Long taecherSeq);

    public interface TeacherDeatilInfo {
        String getUser_name();
        String getUser_phone_number();
        String getUser_email();
        String getUser_profile_image_url();
    }

    @Query(value = "SELECT u.user_seq, u.user_name, u.user_id, u.user_code, u.user_register_date FROM user u", nativeQuery = true)
    public List<UserList> findAllusers();

    public interface UserList {
        String getUser_seq();
        String getUser_name();
        String getUser_id();
        String getUser_code();
        String getUser_register_date();
    }

    @Query(value = "SELECT u.user_name FROM user u JOIN student s ON u.user_seq = s.parent_seq WHERE s.student_id = :studentId", nativeQuery = true)
    public String findUsernameBystudentId(@Param("studentId") Long studentId);

    @Query(value = "SELECT * FROM user u WHERE u.user_seq = :userSeq", nativeQuery = true)
    public User findByuserseq(@Param("userSeq") Long userSeq);
}
