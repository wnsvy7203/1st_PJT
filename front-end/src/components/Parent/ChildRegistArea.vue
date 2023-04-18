<template>
    <section class="signup__area po-rel-z1 pt-100 pb-145">
      <div class="sign__shape">
        <img class="man-1" src="@/assets/img/icon/sign/man-3.png" alt="" />
        <img class="man-2 man-22" src="@/assets/img/icon/sign/man-2.png" alt="" />
        <img class="circle" src="@/assets/img/icon/sign/circle.png" alt="" />
        <img class="zigzag" src="@/assets/img/icon/sign/zigzag.png" alt="" />
        <img class="dot" src="@/assets/img/icon/sign/dot.png" alt="" />
        <img class="bg" src="@/assets/img/icon/sign/sign-up.png" alt="" />
        <img class="flower" src="@/assets/img/icon/sign/flower.png" alt="" />
      </div>
      <div class="container">
        <div class="row">
          <div class="col-xxl-8 offset-xxl-2 col-xl-8 offset-xl-2">
            <div class="section__title-wrapper text-center mb-25">
              <h2 class="section__title">자녀 등록</h2>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-xxl-6 offset-xxl-3 col-xl-6 offset-xl-3 col-lg-8 offset-lg-2">
            <div class="sign__wrapper white-bg">
              <div class="sign__form">
                <div class="sign__input-wrapper mb-25">
                  <!-- 회원정보 -->
                </div>
                <form @submit.prevent="sendinfo">
                  <div class="sign__input-wrapper mb-25">
                    <h5>이름</h5>
                    <div class="sign__input">
                      <input type="text" id="name" v-model="childinfo.student_name" placeholder="이름을 입력해주세요">
                    </div>
                  </div>
                  <div class="sign__input-wrapper mb-25">
                    <h5>전화번호</h5>
                    <div class="sign__input">
                      <input type="text" id="phonenumber" maxlength="11" v-model="childinfo.student_phone_number" placeholder="(-)를 제외하고 입력해주세요">
                    </div>
                  </div>
                  <div class="sign__input-wrapper mb-25">
                    <h5>나이</h5>
                    <div class="sign__input">
                      <input type="number" id="age" min="0" max="100" v-model="childinfo.student_age" placeholder="나이를 입력해주세요">
                    </div>
                  </div>
                  <div class="sign__input-wrapper mb-25">
                    <h5>학교</h5>
                    <div class="sign__input">
                      <input type="text" id="name" v-model="childinfo.student_school" placeholder="학교명을 입력해주세요">
                    </div>
                  </div>
                  <div class="sign__input-wrapper mb-25">
                    <h5>학년</h5>
                    <div class="sign__input">
                      <select
                        v-model="childinfo.grade_code"
                        class="form-select hero__select mt-5"
                        aria-label="Default select example"
                        required
                      >
                        <option value="" selected hidden>학년을 선택해주세요</option>
                        <option value="E1">초1</option>
                        <option value="E2">초2</option>
                        <option value="E3">초3</option>
                        <option value="E4">초4</option>
                        <option value="E5">초5</option>
                        <option value="E6">초6</option>
                        <option value="M1">중1</option>
                        <option value="M2">중2</option>
                        <option value="M3">중3</option>
                        <option value="H1">고1</option>
                        <option value="H2">고2</option>
                        <option value="H3">고3</option>
                      </select>
                    </div>
                  </div>
                  <button type="submit" class="e-btn w-4 a_btn">
                    등록
                  </button>
                  <button type="button" class="btn btn-secondary w-40">
                    <router-link :to="{name: 'home'}">뒤로가기</router-link>
                  </button> 
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </template>
  
  <script>
  import axios from 'axios'
  import { useStore } from 'vuex'
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  
  export default {
    name: 'ChildRegist',
    setup() {
      const store = useStore()
      const router = useRouter()
      const childinfo = ref({
        student_name: '',
        student_school: '',
        grade_code: '',
        student_age: '',
        student_phone_number: ''
      })
  
      function sendinfo() {
        axios({
          method:'post',
          url: `${store.state.API_URL}/api/v1/parents/${store.state.myseq}/students`,
          data: {
              name: childinfo.value.student_name,
              phoneNumber: childinfo.value.student_phone_number,
              age : childinfo.value.student_age,
              school : childinfo.value.student_school,
              gradeCode: childinfo.value.grade_code
  
          },
        })
        .then(() => {
          alert('자녀 등록 완료')
          router.push({ name: 'home'})
        })
        .catch((err) => {
          console.log(err)
        })
      }
  
      return {
        childinfo,
        sendinfo
      }
    }
  }
  </script>
  
  <style scoped>
  .a_btn{
    margin-left: 60px;
  }
  .btn {
    margin-left: 40px;
    padding: 12px 10px;
  }
  
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
  </style>