package com.mycom.myapp.controller;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.user.*;
import com.mycom.myapp.service.user.UserService;
import com.mycom.myapp.service.user.UserServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(
        origins = {"http://localhost:8080", "https://i8e206.p.ssafy.io"},
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
)
public class UserController {

    private final UserServiceImpl userService;

    @ApiOperation(value = "회원 가입", notes = "학원장, 강사, 학부모를 각각 나누어서 회원가입 진행")
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> saveUser(@RequestBody CreateUserRequest request) {
        // request로부터 user 생성

        // 학원장 회원가입
        String discrimination = request.getUserCode();
        if (discrimination.equals("UM")) {
            Master master = new Master();
            master.setUserId(request.getUserId());
            master.setPassword(request.getUserPassword());
            master.setName(request.getUserName());
            master.setUserCode(request.getUserCode());
            master.setPhoneNumber(request.getUserPhoneNumber());
            master.setAddress(new Address(request.getZipCode(), request.getFirstAddress(), request.getLastAddress()));
            master.setEmail(request.getUserEmail());
            master.setProfileImageUrl(request.getUserProfileImageUrl());
            master.getRoles().add("MASTER");

            // DB에 저장 후 userSeq 반환
            Long userSeq = userService.joinMaster(master);

            CreateUserResponse response = new CreateUserResponse(userSeq);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // 강사 회원가입
        else if(discrimination.equals("UT")) {
            Teacher teacher = new Teacher();
            teacher.setUserId(request.getUserId());
            teacher.setPassword(request.getUserPassword());
            teacher.setName(request.getUserName());
            teacher.setUserCode(request.getUserCode());
            teacher.setPhoneNumber(request.getUserPhoneNumber());
            teacher.setAddress(new Address(request.getZipCode(), request.getFirstAddress(), request.getLastAddress()));
            teacher.setEmail(request.getUserEmail());
            teacher.setProfileImageUrl(request.getUserProfileImageUrl());
            teacher.getRoles().add("TEACHER");

            // DB에 저장 후 userSeq 반환
            Long userSeq = userService.joinTeacher(teacher);

            CreateUserResponse response = new CreateUserResponse(userSeq);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // 학부모 회원가입
        else if(discrimination.equals("UP")) {
            Parent parent = new Parent();
            parent.setUserId(request.getUserId());
            parent.setPassword(request.getUserPassword());
            parent.setName(request.getUserName());
            parent.setUserCode(request.getUserCode());
            parent.setPhoneNumber(request.getUserPhoneNumber());
            parent.setAddress(new Address(request.getZipCode(), request.getFirstAddress(), request.getLastAddress()));
            parent.setEmail(request.getUserEmail());
            parent.setProfileImageUrl(request.getUserProfileImageUrl());
            parent.getRoles().add("PARENT");

            // DB에 저장 후 userSeq 반환
            Long userSeq = userService.joinParent(parent);

            CreateUserResponse response = new CreateUserResponse(userSeq);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    static class CreateUserResponse {
        @ApiModelProperty(value = "회원가입 후 저장된 userSeq", dataType = "Long")
        private Long userSeq;
    }

    @Data
    static class CreateUserRequest {
        private String userId;
        private String userPassword;
        private String userName;
        private String userCode;
        private String userPhoneNumber;
        private String zipCode;
        private String firstAddress;
        private String lastAddress;
        private String userEmail;
        private String userProfileImageUrl;
    }

    // 유저 정보 조회
    @GetMapping("/users/{userSeq}")
    @ApiOperation(value = "유저 정보 조회", notes = "유저의 기본 정보를 조회한다.")
    public ResponseEntity<ReferUserResponse> referUser (@PathVariable Long userSeq) {
        // userSeq로 user 상세정보 조회
        User user = userService.refer(userSeq);

        // 반환할 객체(비밀번호 빠짐)
        ReferUserResponse response = new ReferUserResponse();
        response.setUserSeq(user.getSeq());
        response.setUserId(user.getUserId());
        response.setUserName(user.getName());
        response.setUserCode(user.getUserCode());
        response.setUserPhoneNumber(user.getPhoneNumber());
        response.setZipCode(user.getAddress().getZipCode());
        response.setFirstAddress(user.getAddress().getFirstAddress());
        response.setLastAddress(user.getAddress().getLastAddress());
        response.setUserEmail(user.getEmail());
        response.setUserProfileImageUrl(user.getProfileImageUrl());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    static class ReferUserResponse {
        private Long userSeq;
        private String userId;
        private String userName;
        private String userCode;
        private String userPhoneNumber;
        private String zipCode;
        private String firstAddress;
        private String lastAddress;
        private String userEmail;
        private String userProfileImageUrl;
    }

    // 유저 정보 수정
    @PutMapping("/users/{userSeq}")
    @ApiOperation(value = "유저 정보 수정", notes = "유저의 기본 정보를 수정한다.")
    public ResponseEntity<Long> updateUser(@PathVariable Long userSeq, @RequestBody  UpdateUserRequest request) {
        // request로부터 받은 값으로 user 세팅
        User user = new User();
        user.setName(request.getUserName());
        user.setPhoneNumber(request.getUserPhoneNumber());
        user.setAddress(new Address(request.getZipCode(), request.getFirstAddress(), request.getLastAddress()));
        user.setEmail(request.getUserEmail());
        user.setProfileImageUrl(request.getUserProfileImageUrl());

        // user 업데이트
        Long updatedUserSeq = userService.update(userSeq, user);

        return new ResponseEntity<>(updatedUserSeq, HttpStatus.OK);
    }

    @Data
    static class UpdateUserRequest {
        private String userName;
        private String userPhoneNumber;
        private String zipCode;
        private String firstAddress;
        private String lastAddress;
        private String userEmail;
        private String userProfileImageUrl;
    }

    // 유저 정보 삭제
    @DeleteMapping("/users/{userSeq}")
    @ApiOperation(value = "유저정보 삭제", notes = "유저를 삭제한다. 관련된 테이블을 모두 삭제한다. 강사를 삭제할 경우, 강사가 소속된 반의 강사 부분은 null로 변경된다.")
    public ResponseEntity<Long> deleteUser (@PathVariable Long userSeq) {
        Long retSeq = userService.delete(userSeq);

        return new ResponseEntity<>(retSeq, HttpStatus.NO_CONTENT);
    }

    // 아이디 찾기
    @PostMapping("/users/find-id")
    @ApiOperation(value = "ID 찾기", notes = "유저 이름과 이메일을 통해 아이디를 찾을 수 있다.")
    public ResponseEntity FindUserId (@RequestBody FindUserIdRequest request) {
        User user = new User();
        user.setName(request.getUserName());
        user.setEmail(request.getUserEmail());

        String foundId = userService.findUserId(user);
        return new ResponseEntity<>(foundId, HttpStatus.OK);
    }

    @Data
    static class FindUserIdRequest {
        private String userName;
        private String userEmail;
    }

    // 로그인
    @PostMapping("/users/login")
    @ApiOperation(value = "로그인", notes = "유저 아이디와 비밀번호를 통해 로그인을 할 수 있다. 로그인을 할 경우 access token을 반환한다.")
    public TokenInfo login(@RequestBody UserLoginRequest request) {
        String userId = request.getUserId();
        String password = request.getPassword();
        TokenInfo tokenInfo = userService.login(userId, password);
        return tokenInfo;
    }

    @Data
    static class UserLoginRequest {
        private String userId;
        private String password;
    }

    // 아이디 중복 체크
    @PostMapping("/users/check-id")
    @ApiOperation(value = "회원가입시 아이디 중복체크", notes = "회원가입시 아이디를 중복체크 할 수 있도록 하는 api")
    public ResponseEntity<CheckIdResponse> checkId (@RequestBody CheckIdRequest request) {
        boolean duplicated = userService.checkId(request.getId());

        CheckIdResponse response = new CheckIdResponse(duplicated);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    static class CheckIdRequest {
        private String id;
    }

    @Data
    @AllArgsConstructor
    static class CheckIdResponse {
        private boolean duplicated;
    }

    // 비밀번호 찾기를 위한 정보확인
    @PostMapping("/users/find-password")
    @ApiOperation(value = "비밀번호 찾기를 위한 정보 확인", notes = "유저 이름, 아이디, 이메일을 통해 진짜 유저인지 인증절차를 진행한다.")
    public ResponseEntity<FindPasswordResponse> findPassword (@RequestBody FindPasswordRequest request) {
        boolean isCorrectPassword = userService.findPassword(request.getName(), request.getEmail(), request.getId());

        FindPasswordResponse response = new FindPasswordResponse(isCorrectPassword);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class FindPasswordResponse {
        private boolean isCorrectPassword;
    }

    @Data
    static class FindPasswordRequest {
        private String name;
        private String email;
        private String id;
    }

    // 비밀번호 초기화 링크 이메일로 보내기
    @PostMapping("/users/send-email/{userSeq}")
    @ApiOperation(value = "비밀번호 초기화 링크 이메일로 보내기", notes = "인증된 유저에게 비밀번호를 초기화 할 수 있는 페이지 링크를 이메일로 보낸다.")
    public ResponseEntity sendEmail(@PathVariable Long userSeq, @RequestBody SendEmailRequest request) {
        userService.sendMail(userSeq, request.getAddress());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Data
    static class SendEmailRequest {
        private String address;
    }

    // 비밀번호 초기화
    @PostMapping("/users/reset-password")
    @ApiOperation(value = "비밀번호 초기화", notes = "인증된 유저가 새 비밀번호를 입력하면 DB에 해당 유저의 비밀번호가 바뀐다.")
    public ResponseEntity<ResetPasswordResponse> resetPassword (@RequestBody ResetPasswordRequest request) {
        Long userSeq = userService.resetPassword(request.getUserSeq(), request.getNewPassword());

        ResetPasswordResponse response = new ResetPasswordResponse(userSeq);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ResetPasswordResponse {
        private Long userSeq;
    }

    @Data
    static class ResetPasswordRequest {
        private Long userSeq;
        private String newPassword;
    }

    // 유저 ID로 유저 Seq, 유저 code 받기
    @PostMapping("/users/find-seq")
    @ApiOperation(value = "유저 ID로 userSeq와 userCode 받기", notes = "로그인시 userSeq와 userCode를 받아 유저 유형에 따른 화면 보임이 가능하게 한다.")
    public ResponseEntity<FindSeqResponse> findSeq(@RequestBody FindSeqRequest request) {
        Map<String, String> map = userService.findUserSeq(request.getUserId());

        FindSeqResponse response = new FindSeqResponse(Long.parseLong(map.get("userSeq")), map.get("userCode"));

        return new ResponseEntity<FindSeqResponse>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class FindSeqResponse {
        private Long userSeq;
        private String userCode;
    }

    @Data
    static class FindSeqRequest {
        private String userId;
    }
}
