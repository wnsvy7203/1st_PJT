package com.mycom.myapp.repository.user;

import com.mycom.myapp.domain.user.Master;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
}
