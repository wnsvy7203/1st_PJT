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
            <h2>아이디 찾기</h2>
            <p class="mt-20" style="font-size: 14px">
              가입 시 등록한 이메일 주소와 입력한 이메일 주소가 같아야 <br />아이디 정보를 확인할 수
              있습니다.
            </p>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xxl-6 offset-xxl-3 col-xl-6 offset-xl-3 col-lg-8 offset-lg-2">
          <div class="sign__wrapper white-bg">
            <div class="sign__form">
              <form @submit.prevent="findid">
                <div class="sign__input-wrapper mb-25">
                  <h5>이름</h5>
                  <div class="sign__input">
                    <input id="name" type="text" placeholder="이름을 입력해주세요. " v-model="name" @blur="name_validate"/>
                  </div>
                </div>
                <div class="sign__input-wrapper mb-10">
                  <h5>이메일</h5>
                  <div class="sign__input">
                    <input id="email"
                      type="text"
                      placeholder="등록한 이메일을 입력해주세요. "
                      v-model="email" @blur="email_validate"
                    />
                  </div>
                </div>
                <p v-if="error === 'name'">이름을 입력해주세요</p>
                <p v-if="error === 'email'">등록한 이메일을 입력해주세요</p>
                <div v-if="error === 'right1'">
                  <p>이름을 정확히 입력해주세요.</p>
                </div>
                <div v-if="error === 'right2'">
                  <p>이메일 주소를 정확히 입력해주세요.</p>
                </div>
                <div v-if="error === 'exist'">
                  <p>없는 이름이거나 등록되지 않은 이메일입니다.</p>
                  <p>입력하신 내용을 다시 확인해주세요.</p>
                </div>
                <button class="e-btn w-100">아이디 찾기</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { defineComponent, ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default defineComponent({
  name: 'FindidArea',
  setup() {
    const name = ref('')
    const email = ref('')
    const error = ref('')
    const router = useRouter()
    const store = useStore()
    const regex_name = /^[가-힣|a-z|A-Z|]{2,}$/
    const regex_email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/
    
    const name_validate = () => {
      error.value = ''
      if (!name.value) {
        error.value = 'name'
        return
      }

      if(!regex_name.test(name.value)) {
        error.value = 'right1'
        return
      }
    }

    const email_validate = () => {
      if (!email.value) {
        error.value = 'email'
        return
      }

      if(!regex_email.test(email.value)) {
        error.value = 'right2'
        return
      }
    }

    function validation_check() {
      name_validate();
      if (error.value === 'name' || error.value === 'right1') {
        document.getElementById("name").focus();
        return;
      }

      email_validate();
      if (error.value === 'email' || error.value === 'right2') {
        document.getElementById("email").focus();
        return;
      }
    }

    const findid = () => {
      validation_check()
      axios({
        method: 'post',
        url: `${store.state.API_URL}/api/v1/users/find-id`,
        data: {
          userName: name.value,
          userEmail: email.value,
        }
      })
      .then((res) => {
        alert(`${name.value}님의 아이디는 ${res.data} 입니다.`)
        router.push({ name : 'login' })
      })
      .catch(() => {
        error.value = 'exist'
        return
      })
    }
    return {
      name,
      email,
      error,
      findid,
      name_validate,
      email_validate
    }
  }
})
</script>

<style scoped>
.sign__input input {
  padding: 0 25px;
}
</style>
