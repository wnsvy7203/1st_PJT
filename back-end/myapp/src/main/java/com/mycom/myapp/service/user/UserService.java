package com.mycom.myapp.service.user;

import com.mycom.myapp.domain.user.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    // 학원장 등록
    public Long joinMaster(Master master);

    // 강사 등록
    public Long joinTeacher(Teacher teacher);

    // 학부모 등록
    public Long joinParent(Parent parent);

    // 회원 조회
    public User refer(Long userSeq);

    // 회원 수정
    public Long update(Long userSeq, User user);

    // 회원 삭제
    public Long delete(Long userSeq);

    // 로그인
    public TokenInfo login(String userId, String password);

    // 회원 아이디 찾기
    public String findUserId(User user);

    // 아이디 중복 체크
    public boolean checkId(String userId);

    // 비밀번호 찾기를 위한 정보 확인
    public boolean findPassword(String name, String email, String id);

    // 비밀번호 초기화
    public Long resetPassword(Long userSeq, String newPassword);

    // 유저 ID로 유저 Seq, 유저 code 받기
    public Map<String, String> findUserSeq(String userId);

    // 비밀번호 초기화 email 보내기
    public void sendMail(Long userSeq, String address);
}
