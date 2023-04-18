package com.mycom.myapp.repository.user;

import com.mycom.myapp.domain.user.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
}
