package com.mycom.myapp.service.inquire;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.Inquire;
import com.mycom.myapp.repository.InquireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InquireServiceImpl implements InquireService {

    private final InquireRepository inquireRepository;

    // 문의사항 등록
    @Override
    public Long createInquire(String name, String email, String phoneNumber, String userId, String academyName, String zipCode, String firstAddress, String lastAddress, String academyPhoneNumber) {
        Inquire inquire = new Inquire();

        inquire.setName(name);
        inquire.setEmail(email);
        inquire.setPhone_number(phoneNumber);
        inquire.setUserId(userId);
        inquire.setAcademyName(academyName);
        inquire.setAddress(new Address(zipCode, firstAddress, lastAddress));
        inquire.setAcademyPhoneNumber(academyPhoneNumber);

        return inquireRepository.save(inquire).getId();
    }

    @Override
    public List<Inquire> listInquires() {
        return inquireRepository.findAll();
    }

    @Override
    public Inquire detailInquire(Long inquireId) {
        return inquireRepository.findById(inquireId).orElseThrow(() -> new IllegalArgumentException("no such data"));
    }
}
