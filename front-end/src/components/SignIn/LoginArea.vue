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
        <div class="col-xxl-6 offset-xxl-3 col-xl-6 offset-xl-3 col-lg-8 offset-lg-2">
          <div class="sign__wrapper white-bg">
            <div class="sign__header mb-35">
              <div class="sign__in text-center">
                <!-- <a href="#" class="sign__social text-start mb-15"> -->
                  <!-- <i class="fab fa-kaggle"></i>Sign in with Kakao</a
                >
                <p>
                  <span>........</span> Or,
                  <router-link to="/login">sign in</router-link>
                  with your ID<span> ........</span> -->
                <!-- </p> -->
              </div>
            </div>
            <div class="sign__form">
              <form @submit.prevent="login">
                <div class="sign__input-wrapper mb-25">
                  <h5>아이디</h5>
                  <div class="sign__input">
                    <input type="text" placeholder="ID" v-model="id" />
                    <i class="fal fa-envelope"></i>
                  </div>
                </div>
                <div class="sign__input-wrapper mb-10">
                  <h5>비밀번호</h5>
                  <div class="sign__input">
                    <input type="password" placeholder="Password" v-model="password" />
                    <i class="fal fa-lock"></i>
                  </div>
                </div>
                <div class="sign__action d-sm-flex justify-content-between mb-30">
                  <div class="sign__forgot">
                    <router-link to='/find-id'>Forgot your ID?</router-link>&nbsp;or
                    <router-link to='/find-password'>Password?</router-link>
                  </div>
                </div>
                  <p v-if="error === 'id'" class="error">아이디를 입력해주세요</p>
                  <p v-if="error === 'password'" class="error">비밀번호를 입력해주세요</p>
                  <div v-if="error === 'validation'" class="error">
                    <p>아이디 또는 비밀번호를 잘못 입력했습니다.</p> 
                  </div>
                <button class="e-btn w-100"><span></span>로그인</button>
                <div class="sign__new text-center mt-20">
                  <br>
                  <p><router-link to="/register">회원가입</router-link></p>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { defineComponent, ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import jwt_decode from "jwt-decode";
import axios from "axios";

export default defineComponent({
  name: "LoginArea",
  setup() {
    const store = useStore();
    const id = ref("");
    const password = ref("");
    const error = ref("");
    const router = useRouter();
    const regex_id = /^[a-z|A-Z|0-9|]{5,20}$/;
    const regex_pwd = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%#?&])[A-Za-z\d$@$!*%#?&]{9,16}$/;

    // Login 기능
    const login = async () => {
      // 로그인 오류 체크
      error.value = "";
      if (!id.value) {
        error.value = "id";
        return;
      }
      if (!password.value) {
        error.value = "password";
        return;
      }
      if (!regex_id.test(id.value) || !regex_pwd.test(password.value)) {
        error.value = "validation";
        return;
      }

      // 첫번째 axios
      axios({
        method: "post",
        url: `${store.state.API_URL}/api/v1/users/login`,
        data: {
          userId: id.value,
          password: password.value,
        },
      })
        // 첫번째 axios의 then(res)
        .then((res) => {
          console.log("첫번째성공");
          // 두번째 axios 시작
          axios({
            method: "post",
            url: `${store.state.API_URL}/api/v1/users/find-seq`,
            data: {
              userId: jwt_decode(res.data.accessToken).sub,
            },
          })
            // 두번째 axios의 then(response)
            .then((response) => {
              console.log("두번째 성공");
              // 세번째 axios 시작
              axios({
                method: "get",
                url: `${store.state.API_URL}/api/v1/users/${response.data.userSeq}`,
              })
                // 세번째 axios의 대답 then(respond)
                .then((respond) => {
                  console.log(respond.data, '유저정보')
                  const payload = {
                    token: res.data.accessToken,
                    userSeq: response.data.userSeq,
                    profile: respond.data,
                  };

                  // 로그인 시 store에 token, userSeq, profile 저장
                  store.dispatch("login", payload);
                  if (store.state.profile.userCode === "UT") {
                    axios({
                      method: "get",
                      url: `${store.state.API_URL}/api/v1/teachers/${store.state.myseq}/info`,
                      headers: {
                        Authorization: `Bearer ${store.state.token}`,
                      },
                    })
                      .then((res) => {
                        store.dispatch("saveClasses", res.data.list);
                      })
                      .catch(() => {
                        error.value = "validation";
                      })
                  }
                  router.push({ name: "home" });
                })
            })
            .catch(() => {
              error.value = "validation";
            })
        })
        // 첫번째 axios의 error
        .catch(() => {
          error.value = "validation";
        })
    }
    return {
      id,
      password,
      error,
      login,
    };
  },
});
</script>
