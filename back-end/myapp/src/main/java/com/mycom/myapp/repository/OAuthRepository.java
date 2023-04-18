package com.mycom.myapp.repository;

import com.mycom.myapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT count(*) FROM user u WHERE u.user_email = :userEmail", nativeQuery = true)
    public int isRegisteredUser(@Param("userEmail") String userEmail);
}
