package com.mycom.myapp.service.academy;

import com.mycom.myapp.domain.Academy;
import com.mycom.myapp.repository.AcademyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AcademyServiceImpl implements AcademyService {

    private final AcademyRepository academyRepository;

    // 학원전체 조회
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "academy")
    public List<Academy> listAcademy() {
        return academyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Academy detailAcademy(Long academyId) {
        return academyRepository.findById(academyId).orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    // 학원-반 전체 조회
//    public
}
