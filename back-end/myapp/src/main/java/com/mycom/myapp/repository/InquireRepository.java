package com.mycom.myapp.repository;

import com.mycom.myapp.domain.Inquire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquireRepository extends JpaRepository<Inquire, Long> {
}
