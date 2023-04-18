package com.mycom.myapp.service.academy;

import com.mycom.myapp.domain.Academy;
import com.mycom.myapp.domain.Class;
import com.mycom.myapp.repository.AcademyRepository;

import java.util.List;

public interface AcademyService {
    // 학원 목록 조회
    public List<Academy> listAcademy();

    // 학원 정보 상세 조회
    public Academy detailAcademy(Long academyId);
}
