<template>
  <form @submit.prevent="sendInfo">
    <div class="sign__input-wrapper mb-25">
      <h5>아이디</h5>
      <div class="sign__input">
        <input
          v-model="userInfo.id"
          type="text"
          placeholder="ID"
          id="id"
          @blur="id_validate"
        />
        <i class="fal fa-user"></i>
      </div>
      <p v-if="error.id === 1" class="err-msg">필수 입력사항입니다.</p>
      <p v-if="error.id === 2" class="err-msg">
        5~20 자의 영문 소문자, 숫자만 사용가능합니다.
      </p>
      <p v-if="error.id === 3" class="err-msg">이미 사용 중인 아이디입니다.</p>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>이메일</h5>
      <div class="sign__input">
        <input
          id="email"
          type="text"
          placeholder="e-mail address"
          v-model="userInfo.email"
          @blur="email_validate"
        />
        <i class="fal fa-envelope"></i>
      </div>
      <p v-if="error.email === 1" class="err-msg">필수 입력사항입니다.</p>
      <p v-if="error.email === 2" class="err-msg">이메일 주소를 정확히 입력해주세요.</p>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>비밀번호</h5>
      <div class="sign__input">
        <input
          id="pwd"
          type="password"
          placeholder="Password"
          v-model="userInfo.pwd"
          maxlength="16"
          @blur="pwd_validate"
        />
        <i class="fal fa-lock"></i>
      </div>
      <p v-if="error.pwd === 1" class="err-msg">필수 입력사항입니다.</p>
      <p v-if="error.pwd === 2" class="err-msg">
        9~16자의 영문, 숫자, 특수문자를 사용하세요.
      </p>
    </div>
    <div class="sign__input-wrapper mb-10">
      <h5>비밀번호 확인</h5>
      <div class="sign__input">
        <input
          id="pwd2"
          type="password"
          placeholder="Re-Password"
          v-model="pwd2"
          maxlength="16"
          @blur="pwd2_validate"
        />
        <i class="fal fa-lock"></i>
      </div>
      <p v-if="error.pwd2 === 1" class="err-msg">필수 입력사항입니다.</p>
      <p v-if="error.pwd2 === 2" class="err-msg">비밀번호가 일치하지 않습니다.</p>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>이름</h5>
      <div class="sign__input">
        <input
          id="name"
          type="text"
          placeholder="Name"
          v-model="userInfo.name"
          @blur="name_validate"
        />
        <i class="fal fa-user"></i>
      </div>
      <p v-if="error.name === 1" class="err-msg">필수 입력사항입니다.</p>
      <p v-if="error.name === 2" class="err-msg">이름을 정확히 입력해주세요.</p>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>연락처</h5>
      <div class="sign__input">
        <input
          id="phoneNumber"
          type="text"
          placeholder="'-'를 제외하고 입력해주세요"
          v-model="userInfo.phoneNumber"
          maxlength="11"
          @blur="phoneNumber_validate"
        />
        <i class="fal fa-phone"></i>
      </div>
      <p v-if="error.phoneNumber === 1" class="err-msg">필수 입력사항입니다.</p>
      <p v-if="error.phoneNumber === 2" class="err-msg">숫자만 입력 가능합니다.</p>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>과목</h5>
      <div class="sign__input">
        <select
          id="subject"
          v-model="userInfo.subject"
          class="form-select hero__select mt-5"
          aria-label="Default select example"
          @blur="subject_validate"
          required
        >
          <option value="" disabled selected style="display: none">---선택---</option>
          <option value="KO">국어</option>
          <option value="EN">영어</option>
          <option value="MA">수학</option>
          <option value="SI">과학</option>
          <option value="SO">사회</option>
          <option value="CS">컴퓨터</option>
          <option value="AL">알고리즘</option>
        </select>
        <i class="fal fa-book"></i>
      </div>
      <p v-if="error.subject === 1" class="err-msg">필수 입력사항입니다.</p>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>우편번호</h5>
      <div class="sign__input">
        <input
          id="zonecode"
          type="number"
          @click="findAddress"
          v-model.number="userInfo.zonecode"
          readonly
          placeholder="우편번호"
        />
        <i class="fal fa-envelope"></i>
      </div>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>기본주소</h5>
      <div class="sign__input">
        <input
          type="text"
          @click="findAddress"
          placeholder="기본 주소"
          v-model="userInfo.address"
          readonly
        />
        <i class="fa-sharp far fa-house"></i>
      </div>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>상세주소</h5>
      <div class="sign__input">
        <input
          type="text"
          id="detailAddress"
          v-model="userInfo.detailAddress"
          placeholder="상세 주소"
          @blur="address_validate"
        />
        <i class="fal fa-asterisk"></i>
      </div>
      <p v-if="error.address === 1" class="err-msg">필수 입력사항입니다.</p>
      <p v-if="error.address === 2" class="err-msg">상세주소를 입력해주세요.</p>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>강사 소개</h5>
      <div class="sign__input">
        <input
          type="text"
          id="introduce"
          v-model="userInfo.introduce"
        />
        <i class="fal fa-asterisk"></i>
      </div>
    </div>
    <div class="sign__input-wrapper mb-25">
      <h5>프로필 이미지</h5>
      <div class="mb-30" style="width: 100%">
        <div class="sign__input sign__agree d-flex align-items-center profileInput">
          <input id="file-name" class="upload-name" placeholder="첨부파일" readonly />
          <label for="profile-img">선택</label>
          <input
            @change="imgUpdated"
            type="file"
            id="profile-img"
            accept="image/*"
          />
        </div>
      </div>
    </div>
    <button class="e-btn w-100"><span></span>가입하기</button>
  </form>
</template>

<script>
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import { ref, reactive } from "vue"
import axios from "axios"
import AWS from 'aws-sdk'

export default {
  setup() {
    const store = useStore();
    const router = useRouter();

    // userInfo 값 입력
    const userInfo = reactive({
      id: "",
      pwd: "",
      name: "",
      email: "",
      phoneNumber: "",
      subject: null,
      zonecode: "",
      address: "",
      detailAddress: "",
      introduce: "안녕하세요 *** 강사입니다.", 
      profileImg: '',
    });

    const file = ref(null)

    function imgUpdated(event) {
      file.value = event.target.files[0]
      document.getElementById('file-name').value = file.value.name
    }

    // 비밀번호 확인은 pwd와 값만 같으면 valid check 통과
    const pwd2 = ref("");

    // 에러 코드 작성
    const error = reactive({
      id: false,
      pwd: 0,
      pwd2: 0,
      name: 0,
      email: 0,
      subject: 0,
      phoneNumber: 0,
      zonecode: 0,
      address: 0,
      detailAddress: 0,
    });

    // 정규 표현식
    const regex_id = /^[a-z|A-Z|0-9|]{5,20}$/;
    const regex_pwd = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{9,16}$/;
    const regex_name = /^[가-힣|a-z|A-Z|]{2,}$/;
    const regex_email =
      /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const regex_phoneNumber = /^[0-9]+$/;

    // 회원가입 시 validation check 하는 부분
    // 에러 코드 1이면 필수 입력 값 없음
    // template v-if p태그 참조

    function id_validate() {
      if (!userInfo.id) {
        error.id = 1;
      } else if (!regex_id.test(userInfo.id)) {
        error.id = 2;
      } else {
        axios({
          method: "post",
          url: `${store.state.API_URL}/api/v1/users/check-id`,
          data: {
            id: userInfo.id,
          },
        }).then((res) => {
          if (res.data["duplicated"]) {
            error.id = 3;
          } else {
            error.id = 0;
          }
        });
      }
    }

    function pwd_validate() {
      if (!userInfo.pwd) {
        error.pwd = 1;
      } else if (!regex_pwd.test(userInfo.pwd)) {
        error.pwd = 2;
      } else {
        error.pwd = 0;
      }
    }

    function pwd2_validate() {
      if (!pwd2.value) {
        error.pwd2 = 1;
      } else if (pwd2.value !== userInfo.pwd) {
        error.pwd2 = 2;
      } else {
        error.pwd2 = 0;
      }
    }

    function name_validate() {
      if (!userInfo.name) {
        error.name = 1;
      } else if (!regex_name.test(userInfo.name)) {
        error.name = 2;
      } else {
        error.name = 0;
      }
    }

    function email_validate() {
      if (!userInfo.email) {
        error.email = 1;
      } else if (!regex_email.test(userInfo.email)) {
        error.email = 2;
      } else {
        error.email = 0;
      }
    }

    function phoneNumber_validate() {
      if (!userInfo.phoneNumber) {
        error.phoneNumber = 1;
      } else if (!regex_phoneNumber.test(userInfo.phoneNumber)) {
        error.phoneNumber = 2;
      } else {
        error.phoneNumber = 0;
      }
    }
    
    function subject_validate() {
      if (!userInfo.subject) {
        error.subject = 1
      } else {
        error.subject = 0
      }
    }

    function address_validate() {
      if (!userInfo.zonecode) {
        error.address = 1;
      } else if (userInfo.zonecode && !userInfo.detailAddress) {
        error.address = 2;
      } else {
        error.address = 0;
      }
    }

    // validation check
    function validation_check() {
      id_validate();
      if (error.id) {
        document.getElementById("id").focus();
        return;
      }

      pwd_validate();
      if (error.pwd) {
        document.getElementById("pwd").focus();
        return;
      }

      pwd2_validate();
      if (error.pwd2) {
        document.getElementById("pwd2").focus();
        return;
      }

      name_validate();
      if (error.name) {
        document.getElementById("name").focus();
        return;
      }

      email_validate();
      if (error.email) {
        document.getElementById("email").focus();
        return;
      }

      subject_validate()
      if (error.subject) {
        document.getElementById('subject').focus()
        return
      }

      phoneNumber_validate();
      if (error.phoneNumber) {
        document.getElementById("phoneNumber").focus();
        return;
      }

      address_validate();
      if (error.zonecode) {
        document.getElementById("zonecode").focus();
        return;
      } else if (error.detailAddress) {
        document.getElementById("detailAddress").focus();
        return;
      }
    }

    // 정보 보내기 전에 validation check
    const sendInfo = async () => {
      validation_check()

      if (file.value) {
        userInfo.profileImg = await imgUrl()
      }

      if (Object.values(error).every((v) => v === 0)) {
        axios({
          method: "post",
          url: `${store.state.API_URL}/api/v1/users`,
          data: {
            userId: userInfo.id,
            userPassword: userInfo.pwd,
            userName: userInfo.name,
            userPhoneNumber: userInfo.phoneNumber,
            userCode: 'UT',
            zipCode: userInfo.zonecode,
            firstAddress: userInfo.address,
            lastAddress: userInfo.detailAddress,
            userEmail: userInfo.email,
            userProfileImageUrl: userInfo.profileImg,
          },
        })
          .then((res) => {
            console.log(res)
            axios({
              method: 'post',
              url: `${store.state.API_URL}/api/v1/teachers/${res.data.userSeq}`,
              data: {
                subjectCode: userInfo.subject,
                introduce: userInfo.introduce
              },
            })
              .then(() => {
                alert("회원가입 완료");
                router.push({ name: "login" });
              })
          })
      }
    };

    // 다음 주소 찾기
    function findAddress() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          const addr = ref("");

          addr.value = data.roadAddress;

          userInfo.zonecode = data.zonecode;
          userInfo.address = addr.value;

          document.getElementById("detailAddress").focus();
        },
      }).open();
    }

    return {
      userInfo,
      pwd2,
      error,
      findAddress,

      id_validate,
      pwd_validate,
      pwd2_validate,
      name_validate,
      email_validate,
      phoneNumber_validate,
      subject_validate,
      address_validate,

      sendInfo,
      imgUpdated,
      file
    };
  }
}
</script>

<style scoped>
form {
  padding: 1px;
}
.form-select {
  margin-right: 5px;
  display: inline-block;
}
#selectForm {
  width: 100%;
  display: flex;
  justify-content: center;
}
.form-select {
  border-radius: 6px;
  height: 60px;
  border: 2px solid transparent;
  background-color: #f6f6f7;
  padding: 0;
  font-size: 16px;
  color: #0e1133;
  padding: 0 20px;
}
.sign__input-wrapper p {
  margin: 5px 15px;
  font-size: 14px;
}
.profileInput label {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 5px 10px;
  color: rgb(64, 64, 64);
  background-color: #f6f6f7;
  border: 1px solid rgb(171, 171, 171);
  height: 45px;
  margin-left: 10px;
  border-radius: 6px;
  width: 20%;
  font-size: 15px;
}
.profileInput .upload-name {
  display: inline-block;
  height: 45px;
  padding: 0 10px;
  vertical-align: middle;
  border: 1px solid #dddddd;
  width: 80%;
  color: #999999;
  border-radius: 6px;
}
.profileInput input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}
.form-select {
  text-indent: 30px;
}
</style>