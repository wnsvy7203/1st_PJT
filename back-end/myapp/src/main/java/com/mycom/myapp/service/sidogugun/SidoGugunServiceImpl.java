package com.mycom.myapp.service.sidogugun;

import com.mycom.myapp.domain.Gugun;
import com.mycom.myapp.repository.AcademyRepository;
import com.mycom.myapp.repository.GugunRepository;
import com.mycom.myapp.repository.SidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SidoGugunServiceImpl implements SidoGugunService {

    private final SidoRepository sidoRepository;
    private final GugunRepository gugunRepository;
    private final AcademyRepository academyRepository;

    @Override
    public List<SidoRepository.SidoInfo> listSido() {
        return sidoRepository.findAllSido();
    }

    @Override
    public List<Gugun> listGugun(String sido) {
        return gugunRepository.findBysidoCode(sido);
    }

    @Override
    public List<AcademyRepository.AcademyInfo> listAcademyByAddress(String sido, String gugun) {
        String location = sido + " " + gugun;

        return academyRepository.findBysidogugun(location);
    }
}
