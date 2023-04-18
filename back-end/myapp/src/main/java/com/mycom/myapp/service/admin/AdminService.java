package com.mycom.myapp.service.admin;

import com.mycom.myapp.repository.user.UserRepository;

import java.util.List;

public interface AdminService {

    // 학원 등록
    public Long createAcademy(Long userSeq, String name, String zipCode, String firstAddress, String lastAddress, String phoneNumber, String introduce);

    // 유저 목록
    public List<UserRepository.UserList> findAllUser();
}
