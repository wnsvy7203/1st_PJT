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
            <h2>비밀번호 찾기</h2>
            <p class="mt-20" style="font-size: 14px">
              가입 시 등록한 정보와 입력한 정보가 같아야 초기화 메일을 받을 수 있습니다.
            </p>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xxl-6 offset-xxl-3 col-xl-6 offset-xl-3 col-lg-8 offset-lg-2">
          <div class="sign__wrapper white-bg">
            <div class="sign__form">
              <form @submit.prevent="findpw">
                <div class="sign__input-wrapper mb-25">
                  <h5>아이디</h5>
                  <div class="sign__input">
                    <input type="text" placeholder="아이디를 입력해주세요. " id="id" v-model="id" @blur="id_validate"/>
                  </div>
                </div>
                <div class="sign__input-wrapper mb-25">
                  <h5>이름</h5>
                  <div class="sign__input">
                    <input type="text" placeholder="이름을 입력해주세요. " id="name" v-model="name" @blur="name_validate"/>
                  </div>
                </div>
                <div class="sign__input-wrapper mb-30">
                  <h5>이메일</h5>
                  <div class="sign__input">
                    <input
                      type="text"
                      placeholder="등록한 이메일을 입력해주세요. "
                      v-model="email" id="email" @blur="email_validate"
                    />
                  </div>
                </div>
                <p v-if="error === 'id'">아이디를 입력해주세요</p>
                <p v-if="error === 'name'">이름을 입력해주세요</p>
                <p v-if="error === 'email'">등록한 이메일을 입력해주세요</p>
                <p v-if="error === 'right1'">아이디를 정확히 입력해주세요.</p>
                <p v-if="error === 'right2'">이름을 정확히 입력해주세요.</p>
                <p v-if="error === 'right3'">이메일을 정확히 입력해주세요.</p>
                <p v-if="error === 'exist'">입력하신 내용을 다시 확인해주세요.</p>
                <button class="e-btn w-100">비밀번호 초기화 메일 받기 </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- <div
    class="modal fade"
    id="exampleModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
    v-if="isOk"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-body_">
          메일이 전송되었습니다. <br />
          메일함을 확인해 주세요.
        </div>
        <div class="modal-footer_">
          <button type="button" class="btn closeBtn" data-bs-dismiss="modal">확인</button>
        </div>
      </div>
    </div>
  </div> -->
 
</template>

<script>
import { defineComponent, ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default defineComponent({
  name: 'FindpwPage',
  setup() {
    const id = ref('')
    const name = ref('')
    const email = ref('')
    const error = ref('')
    const router = useRouter()
    const store = useStore()
    const regex_id = /^[a-z|A-Z|0-9|]{5,20}$/
    const regex_name = /^[가-힣|a-z|A-Z|]{2,}$/
    const regex_email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/

    const id_validate = () => {
      error.value = ''
      if (!id.value) {
        error.value = 'id'
        return
      }

      if(!regex_id.test(id.value)) {
        error.value = 'right1'
        return
      }
    }

    const name_validate = () => {
      error.value = ''
      if (!name.value) {
        error.value = 'name'
        return
      }

      if(!regex_name.test(name.value)) {
        error.value = 'right2'
        return
      }
    }

    const email_validate = () => {
      if (!email.value) {
        error.value = 'email'
        return
      }

      if(!regex_email.test(email.value)) {
        error.value = 'right3'
        return
      }
    }

    function validation_check() {
      id_validate()
      if (error.value === 'id' || error.value === 'right1') {
        document.getElementById("id").focus();
        return;
      }

      name_validate()
      if (error.value === 'name' || error.value === 'right2') {
        document.getElementById("name").focus();
        return;
      }

      email_validate()
      if (error.value === 'email' || error.value === 'right3') {
        document.getElementById("email").focus();
        return;
      }
    }

    const findpw = () => {
      validation_check()
      axios({
        method: 'post',
        url: `${store.state.API_URL}/api/v1/users/find-password`,
        data: {
          userName: name.value,
          userEmail: email.value,
          userId: id.value,
        }
      })
      .then(() => {
        alert(`이메일 발송 성공`)
        router.push({ name : 'login' })
      })
      .catch(() => {
        error.value = 'exist'
        return
      })
    }
    return {
      id,
      name,
      email,
      error,
      findpw,
      id_validate,
      name_validate,
      email_validate,
      
    }
  }
})
</script>

<style scoped>
.sign__input input {
  padding: 0 25px;
}

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
