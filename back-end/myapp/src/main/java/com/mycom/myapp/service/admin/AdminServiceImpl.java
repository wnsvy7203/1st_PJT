package com.mycom.myapp.service.admin;

import com.mycom.myapp.domain.Academy;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.repository.AcademyRepository;
import com.mycom.myapp.repository.user.MasterRepository;
import com.mycom.myapp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AcademyRepository academyRepository;
    private final MasterRepository masterRepository;
    private final UserRepository userRepository;

    // 학원 등록
    @Override
    @CacheEvict(value = "academy", allEntries = true)
    public Long createAcademy(Long userSeq, String name, String zipCode, String firstAddress, String lastAddress, String phoneNumber, String introduce) {
        Master master = masterRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Academy academy = Academy.createAcademy(master, name, zipCode, firstAddress, lastAddress, phoneNumber, introduce);

        return academyRepository.save(academy).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRepository.UserList> findAllUser() {
        return userRepository.findAllusers();
    }
}
