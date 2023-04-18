<template>
  <section class="signup__area po-rel-z1 pt-100 pb-145">
    <div class="sign__shape">
      <img class="man-1" src="../../assets/img/icon/sign/man-1.png" alt="" />
      <img class="man-2" src="../../assets/img/icon/sign/man-2.png" alt="" />
      <img class="circle" src="../../assets/img/icon/sign/circle.png" alt="" />
      <img class="zigzag" src="../../assets/img/icon/sign/zigzag.png" alt="" />
      <img class="dot" src="../../assets/img/icon/sign/dot.png" alt="" />
      <img class="bg" src="../../assets/img/icon/sign/sign-up.png" alt="" />
    </div>
    <div class="container">
      <div class="row">
        <div class="col-xxl-8 offset-xxl-2 col-xl-8 offset-xl-2">
          <div class="section__title-wrapper text-center mb-35">
            <h2>비밀번호 재설정</h2>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xxl-6 offset-xxl-3 col-xl-6 offset-xl-3 col-lg-8 offset-lg-2">
          <div class="sign__wrapper white-bg">
            <div class="sign__form">
              <form>
                <div class="sign__input-wrapper mb-25">
                  <h5>비밀번호</h5>
                  <div class="sign__input mb-10">
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
                <div class="sign__input-wrapper mb-30">
                  <h5>비밀번호 확인</h5>
                  <div class="sign__input mb-10">
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

                <button
                  class="e-btn w-100"
                  type="button"
                  data-bs-toggle="modal"
                  data-bs-target="#exampleModal"
                >
                  등록
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- Modal -->
  <div
    class="modal fade"
    id="exampleModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-body_">비밀번호가 재설정되었습니다.</div>
        <div class="modal-footer_">
          <button type="button" class="btn closeBtn" data-bs-dismiss="modal">확인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive } from "vue";

export default {
  name: "FindPasswordArea",
  setup() {
    // userInfo 값 입력
    const userInfo = reactive({
      type: null,
      id: "",
      pwd: "",
      name: "",
      email: "",
      phoneNumber: "",
      zonecode: "",
      address: "",
      detailAddress: "",
      profileImg: null,
    });

    // 비밀번호 확인은 pwd와 값만 같으면 valid check 통과
    const pwd2 = ref("");

    // 에러 코드 작성
    const error = reactive({
      type: 0,
      id: false,
      pwd: 0,
      pwd2: 0,
      name: 0,
      email: 0,
      phoneNumber: 0,
      zonecode: 0,
      address: 0,
      detailAddress: 0,
    });

    // 정규 표현식
    const regex_pwd = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{9,16}$/;

    // validation check 하는 부분
    // 에러 코드 1이면 필수 입력 값 없음
    // template v-if p태그 참조

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

    // validation check
    // function validation_check() {
    //   pwd_validate();
    //   if (error.pwd) {
    //     document.getElementById("pwd").focus();
    //     return;
    //   }

    //   pwd2_validate();
    //   if (error.pwd2) {
    //     document.getElementById("pwd2").focus();
    //     return;
    //   }
    // }

    return {
      userInfo,
      pwd2,
      error,
      pwd_validate,
      pwd2_validate,
    };
  },
};
</script>

<style scoped>
.modal-dialog {
  max-width: 370px;
  margin: 1.75rem auto;
}

.modal-body_ {
  position: relative;
  flex: 1 1 auto;
  padding: 1rem;
  color: black;
  text-align: center;
}

.modal-footer_ {
  display: flex;
  flex-wrap: wrap;
  flex-shrink: 0;
  align-items: center;
  justify-content: flex-end;
  padding: 0.75rem;
  border-bottom-right-radius: calc(0.3rem - 1px);
  border-bottom-left-radius: calc(0.3rem - 1px);
}

.closeBtn {
  margin: auto;
  background-color: #ff8f21;
  color: white;
  width: 100%;
}
</style>
