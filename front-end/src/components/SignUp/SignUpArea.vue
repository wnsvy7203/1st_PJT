<template>
  <section class="signup__area po-rel-z1 pt-100 pb-145">
    <div class="sign__shape">
      <img class="man-1" src="../../assets/img/icon/sign/man-3.png" alt="" />
      <img class="man-2 man-22" src="../../assets/img/icon/sign/man-2.png" alt="" />
      <img class="circle" src="../../assets/img/icon/sign/circle.png" alt="" />
      <img class="zigzag" src="../../assets/img/icon/sign/zigzag.png" alt="" />
      <img class="dot" src="../../assets/img/icon/sign/dot.png" alt="" />
      <img class="bg" src="../../assets/img/icon/sign/sign-up.png" alt="" />
      <img class="flower" src="../../assets/img/icon/sign/flower.png" alt="" />
    </div>
    <div class="container">
      <div class="row">
        <div class="col-xxl-8 offset-xxl-2 col-xl-8 offset-xl-2">
          <div class="section__title-wrapper text-center mb-55">
            <h2 class="section__title">회원가입</h2>
            <p>Create Account For educal</p>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xxl-6 offset-xxl-3 col-xl-6 offset-xl-3 col-lg-8 offset-lg-2">
          <div class="sign__wrapper white-bg">
            <div class="sign__form signup">
              <div class="btn-group" role="group" aria-label="Basic radio toggle button group" style="width: 100%; margin-bottom: 20px">
                <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked @click="parentStatus" style="height: 100%; width: 50%">
                <label class="btn btn-outline-secondary" for="btnradio1">학부모</label>
                
                <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off" @click="teacherStatus" style="height: 100%; width: 50%">
                <label class="btn btn-outline-secondary" for="btnradio2">강사</label>
              </div>

              <ParentSignupArea v-if="state === 'UP'"/>
              <TeacherSignupArea v-else-if="state === 'UT'"/>
            </div>
            <div class="sign__new text-center mt-20">
              <div>이미 Educal의 회원이신가요?</div>
              <p><router-link to="/login"> 로그인하러 가기</router-link></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import ParentSignupArea from '@/components/SignUp/ParentSignup.vue'
import TeacherSignupArea from '@/components/SignUp/TeacherSignup.vue'

import { ref } from 'vue'

export default {
  name: "SignUpArea",
  components: {
    ParentSignupArea,
    TeacherSignupArea
  },
  setup() {
    const state = ref('UP')

    function parentStatus() {
      state.value = 'UP'
    }

    function teacherStatus() {
      state.value = 'UT'
    }

    return {
      state,

      parentStatus,
      teacherStatus
    }
  },
};
</script>

<style scoped>
#selectForm {
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>
