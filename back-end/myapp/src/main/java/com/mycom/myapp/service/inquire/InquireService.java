package com.mycom.myapp.service.inquire;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.Inquire;

import java.util.List;

public interface InquireService {

    // 문의사항 등록
    public Long createInquire(String name, String email, String phoneNumber, String userId, String academyName, String zipCode, String firstAddress, String lastAddress, String academyPhoneNumber);

    // 문의사항 전체 조회
    public List<Inquire> listInquires();

    // 문의 상세 조회
    public Inquire detailInquire(Long inquireId);
}
