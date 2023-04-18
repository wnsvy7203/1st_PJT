package com.mycom.myapp.service.user;

import com.mycom.myapp.domain.Class;
import com.mycom.myapp.domain.Meeting;
import com.mycom.myapp.domain.user.*;
import com.mycom.myapp.jwt.JwtTokenProvider;
import com.mycom.myapp.repository.ClassRepository;
import com.mycom.myapp.repository.MeetingRepository;
import com.mycom.myapp.repository.user.MasterRepository;
import com.mycom.myapp.repository.user.ParentRepository;
import com.mycom.myapp.repository.user.TeacherRepository;
import com.mycom.myapp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MasterRepository masterRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    private final ClassRepository classRepository;
    private final MeetingRepository meetingRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final JavaMailSender javaMailSender;

    // 학원장 등록
    @Override
    public Long joinMaster(Master master) {
        return masterRepository.save(master).getSeq();
    }

    // 강사 등록
    @Override
    public Long joinTeacher(Teacher teacher) {
        return teacherRepository.save(teacher).getSeq();
    }

    // 학부모 등록
    @Override
    public Long joinParent(Parent parent) {
        return parentRepository.save(parent).getSeq();
    }

    // 회원 정보 조회
    @Override
    @Transactional(readOnly = true)
    public User refer(Long userSeq) {
        return userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    // 회원 정보 변경
    @Override
    public Long update(Long userSeq, User user) {
        User foundUser = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        foundUser.setName(user.getName());
        foundUser.setPhoneNumber(user.getPhoneNumber());
        foundUser.setAddress(user.getAddress());
        foundUser.setEmail(user.getEmail());
        foundUser.setProfileImageUrl(user.getProfileImageUrl());

        return foundUser.getSeq();
    }

    // 회원 삭제
    @Override
    public Long delete(Long userSeq) {
        User user = userRepository.findById(userSeq).get();

        String userCode = user.getUserCode();

        // 강사 삭제 (반에서 강사정보 null로 바꿈)
        if (userCode.equals("UT")) {
            List<Class> classes = classRepository.findByTeacherSeq(user.getSeq());

            for(Class c : classes) {
                c.setTeacherToNull();
            }
        }

        // 학부모 삭제 (상담에서 학부모정보 null로 바꿈)
        else if (userCode.equals("UP")) {
            List<Meeting> meetings = meetingRepository.findByParentSeq(user.getSeq());

            for (Meeting m : meetings) {
                m.setParentToNull();
            }
        }

        userRepository.deleteById(userSeq);

        return userSeq;
    }

    // 로그인
    @Override
    public TokenInfo login(String userId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    // 회원 아이디 찾기
    @Override
    public String findUserId(User user) {
        String userName = user.getName();
        String userEmail = user.getEmail();

        List<User> users = userRepository.findByName(userName);
        if (users.isEmpty()) {
            // 입력한 이름이 DB에 없는 경우
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        } else {
            // 입력한 이름이 DB에 있는 경우, 해당 유저의 email과 사용자가 입력한 email이 일치하는지 확인한다.
            if (userEmail.equals(users.get(0).getEmail())) {
                // 이메일이 일치하는 경우
                return users.get(0).getUserId();
            } else {
                // 이메일이 일치하지 않는 경우
                throw new IllegalStateException("이메일이 일치하지 않습니다.");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean findPassword(String name, String email, String id) {
        return userRepository.existsByNameAndEmailAndUserId(name, email, id);
    }

    @Override
    public Long resetPassword(Long userSeq, String newPassword) {
        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        user.setPassword(newPassword);

        return user.getSeq();
    }

    @Override
    public Map<String, String> findUserSeq(String userId) {
        User user = userRepository.findByUserId(userId).get();

        Long userSeq = user.getSeq();
        String userCode = user.getUserCode();

        Map<String, String> map = new HashMap<>();

        map.put("userSeq", Long.toString(userSeq));
        map.put("userCode", userCode);

        return map;
    }

    @Override
    public void sendMail(Long userSeq, String address) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try{
            // 1. 메일 수신자 설정
            String[] receiveList = {address};

            simpleMailMessage.setTo(receiveList);

            // 2. 메일 제목 설정
            simpleMailMessage.setSubject("비밀번호 초기화 메일");

            // 3. 메일 내용 설정
            simpleMailMessage.setText("https://i8e206.p.ssafy.io/reset-pw" + "?userSeq=" + userSeq);

            // 4. 메일 전송
            javaMailSender.send(simpleMailMessage);
            } catch(Exception e){
                        System.out.println(e.toString());
                    }
            }
}
