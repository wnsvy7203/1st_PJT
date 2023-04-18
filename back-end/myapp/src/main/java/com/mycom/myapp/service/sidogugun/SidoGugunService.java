package com.mycom.myapp.service.sidogugun;

import com.mycom.myapp.domain.Gugun;
import com.mycom.myapp.domain.Sido;
import com.mycom.myapp.repository.AcademyRepository;
import com.mycom.myapp.repository.SidoRepository;

import java.util.List;

public interface SidoGugunService {

    // 시도 리스트 가져오기
    public List<SidoRepository.SidoInfo> listSido();

    // 시도 코드로 구군 리스트 가져오기
    public List<Gugun> listGugun(String sido);

    // 시도 구군 정보로 학원 리스트 가져오기
    public List<AcademyRepository.AcademyInfo> listAcademyByAddress(String sido, String gugun);
}
