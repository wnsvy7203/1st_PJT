<template>
 <section class="contact__area pt-115 pb-120">
   <div class="container">
      <div class="row">
         <div class="col-xxl-7 col-xl-7 col-lg-6">
            <div class="contact__wrapper">
               <div class="section__title-wrapper mb-40">
                  <h2 class="section__title">학원등록<span class="yellow-bg yellow-bg-big"> 문의<img src="../../assets/img/shape/yellow-bg.png" alt=""></span></h2>
               </div>
               <div class="contact__form">
                  <form @submit.prevent="sendInquiry">
                     <div class="row">
                        <div class="col-xxl-6 col-xl-6 col-md-6">
                           <div class="contact__form-input">
                              <input id="name" v-model="inquiryInfo.name" type="text" placeholder="이름" name="name" @blur="name_validate">
                              <p v-if="error.name === 1" class="err-msg">필수 입력사항입니다.</p>
                              <p v-if="error.name === 2" class="err-msg">이름을 정확히 입력해주세요.</p>
                           </div>
                        </div>
                        <div class="col-xxl-6 col-xl-6 col-md-6">
                           <div class="contact__form-input">
                              <input id="email" v-model="inquiryInfo.email" type="email" placeholder="이메일" name="email" @blur="email_validate">
                              <p v-if="error.email === 1" class="err-msg">필수 입력사항입니다.</p>
                              <p v-if="error.email === 2" class="err-msg">이메일 주소를 정확히 입력해주세요.</p>
                           </div>
                        </div>
                        <div class="col-xxl-6 col-xl-6 col-md-6">
                           <div class="contact__form-input">
                              <input id="id" v-model="inquiryInfo.userId" type="text" placeholder="원하는 아이디를 입력해주세요" name="ID" @blur="id_validate">
                              <p v-if="error.id === 1" class="err-msg">필수 입력사항입니다.</p>
                              <p v-if="error.id === 2" class="err-msg">5~20 자의 영문 소문자, 숫자만 사용가능합니다.</p>
                              <p v-if="error.id === 3" class="err-msg">이미 사용 중인 아이디입니다.</p>
                           </div>
                        </div>
                        <div class="col-xxl-6 col-xl-6 col-md-6">
                           <div class="contact__form-input">
                              <input id="phoneNumber" v-model="inquiryInfo.phoneNumber" type="text" placeholder="연락처 (-)는 제외하고 입력해주세요" name="academyName" maxlength="11" @blur="phoneNumber_validate">
                              <p v-if="error.phoneNumber === 1" class="err-msg">필수 입력사항입니다.</p>
                              <p v-if="error.phoneNumber === 2" class="err-msg">숫자만 입력 가능합니다.</p>
                           </div>
                        </div>
                        <div class="col-xxl-6 col-xl-6 col-md-6">
                           <div class="contact__form-input">
                              <input v-model="inquiryInfo.academyName" type="text" placeholder="학원 이름을 입력해주세요" name="academyName">
                           </div>
                        </div>
                        <div class="col-xxl-6 col-xl-6 col-md-6">
                           <div class="contact__form-input">
                              <input @click="findAddress" id="zonecode" v-model.number="inquiryInfo.zipCode" type="number" placeholder="우편번호" name="zipCode" readonly>
                           </div>
                        </div>
                        <div class="col-xxl-12 col-xl-12 col-md-6">
                           <div class="contact__form-input">
                              <input @click="findAddress" v-model="inquiryInfo.firstAddress" type="text" placeholder="기본주소" name="firstAddress" readonly>
                           </div>
                        </div>
                        <div class="col-xxl-12 col-xl-12 col-md-6">
                           <div class="contact__form-input">
                              <input id="detailAddress" v-model="inquiryInfo.lastAddress" type="text" placeholder="상세주소" name="lastAddress" @blur="address_validate">
                              <p v-if="error.address === 1" class="err-msg">필수 입력사항입니다.</p>
                              <p v-if="error.address === 2" class="err-msg">상세주소를 입력해주세요.</p>
                           </div>
                        </div>
                        <div class="col-xxl-6 col-xl-6 col-md-6">
                           <div class="contact__form-input">
                              <input id="academyNumber" v-model.number="inquiryInfo.academyPhoneNumber" type="text" placeholder="학원 연락처 (-)는 제외하고 입력해주세요" name="academyNumber" maxlength="11" @blur="phoneNumber_validate">
                              <p v-if="error.academyPhoneNumber === 1" class="err-msg">필수 입력사항입니다.</p>
                              <p v-if="error.academyPhoneNumber === 2" class="err-msg">숫자만 입력 가능합니다.</p>
                           </div>
                        </div>
                        <div class="col-xxl-6 col-xl-6 col-md-6">
                           <div class="contact__btn">
                              <button type="submit" class="e-btn">문의 보내기</button>
                           </div>
                        </div>
                     </div>
                  </form>
               </div>
            </div>
         </div>
         <div class="col-xxl-4 offset-xxl-1 col-xl-4 offset-xl-1 col-lg-5 offset-lg-1">
            <div class="contact__info white-bg p-relative z-index-1">
               <div class="contact__shape">
                  <img class="contact-shape-1" src="../../assets/img/contact/contact-shape-1.png" alt="">
                  <img class="contact-shape-2" src="../../assets/img/contact/contact-shape-2.png" alt="">
                  <img class="contact-shape-3" src="../../assets/img/contact/contact-shape-3.png" alt="">
               </div>
               <div class="contact__info-inner white-bg">
                  <ul>
                     <li>
                        <div class="contact__info-item d-flex align-items-start mb-35">
                           <div class="contact__info-icon mr-15">
                              <svg class="map" viewBox="0 0 24 24">
                                 <path class="st0" d="M21,10c0,7-9,13-9,13s-9-6-9-13c0-5,4-9,9-9S21,5,21,10z"/>
                                 <circle class="st0" cx="12" cy="10" r="3"/>
                              </svg>
                           </div>
                           <div class="contact__info-text">
                              <h4>SSAFY</h4>
                              <p>부산 강서구 녹산산업중로 333</p>

                           </div>
                        </div>
                     </li>
                     <li>
                        <div class="contact__info-item d-flex align-items-start mb-35">
                           <div class="contact__info-icon mr-15">
                              <svg class="mail" viewBox="0 0 24 24">
                                 <path class="st0" d="M4,4h16c1.1,0,2,0.9,2,2v12c0,1.1-0.9,2-2,2H4c-1.1,0-2-0.9-2-2V6C2,4.9,2.9,4,4,4z"/>
                                 <polyline class="st0" points="22,6 12,13 2,6 "/>
                              </svg>
                           </div>
                           <div class="contact__info-text">
                              <h4>Email us directly</h4>
                              <p>support@educal.com</p>
                              <p>info@educal.com</p>
                           </div>
                        </div>
                     </li>
                     <li>
                        <div class="contact__info-item d-flex align-items-start mb-35">
                           <div class="contact__info-icon mr-15">
                              <svg class="call" viewBox="0 0 24 24">
                                 <path class="st0" d="M22,16.9v3c0,1.1-0.9,2-2,2c-0.1,0-0.1,0-0.2,0c-3.1-0.3-6-1.4-8.6-3.1c-2.4-1.5-4.5-3.6-6-6  c-1.7-2.6-2.7-5.6-3.1-8.7C2,3.1,2.8,2.1,3.9,2C4,2,4.1,2,4.1,2h3c1,0,1.9,0.7,2,1.7c0.1,1,0.4,1.9,0.7,2.8c0.3,0.7,0.1,1.6-0.4,2.1  L8.1,9.9c1.4,2.5,3.5,4.6,6,6l1.3-1.3c0.6-0.5,1.4-0.7,2.1-0.4c0.9,0.3,1.8,0.6,2.8,0.7C21.3,15,22,15.9,22,16.9z"/>
                                 </svg>
                           </div>
                           <div class="contact__info-text">
                              <h4>Phone</h4>
                              <p>+(051) 123 4567</p>
                              <p>+(010) 0000 0000</p>
                           </div>
                        </div>
                     </li>
                  </ul>
                  <div class="contact__social pl-30">
                     <h4>Follow Us</h4>
                     <ul>
                        <li><a href="#" class="fb" ><i class="social_facebook"></i></a></li>
                        <li><a href="#" class="tw" ><i class="social_twitter"></i></a></li>
                        <li><a href="#" class="pin" ><i class="social_pinterest"></i></a></li>
                     </ul>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
 </section>
</template>

<script>
import { ref, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
   name:'ContactArea',
   setup() {
    const store = useStore()
    const router = useRouter()
    const inquiryInfo = reactive({
      name : "",
      email : "",
      phoneNumber : "",
      userId : "",
      academyName : "",
      zipCode : "",
      firstAddress : "",
      lastAddress : "",
      academyPhoneNumber : ""
    })
    const error = reactive({
      name: 0,
      id: false,
      email: 0,
      phoneNumber: 0,
      academyPhoneNumber: 0,
      address: 0,
    })

    const regex_id = /^[a-z|A-Z|0-9|]{5,20}$/
    const regex_name = /^[가-힣|a-z|A-Z|]{2,}$/
    const regex_email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
    const regex_phoneNumber = /^[0-9]+$/

    function name_validate() {
      if (!inquiryInfo.name) {
        error.name = 1
      } else if (!regex_name.test(inquiryInfo.name)) {
        error.name = 2
      } else {
        error.name = 0
      }
    }

    function id_validate() {
      if (!inquiryInfo.userId) {
        error.id = 1
      } else if (!regex_id.test(inquiryInfo.userId)) {
        error.id = 2
      } else {
        axios ({
          method: 'post',
          url: `${store.state.API_URL}/api/v1/users/check-id`,
          data: {
            "id": inquiryInfo.userId
          }
        })
          .then((res) => {
            if (res.data["duplicated"]) {
              error.id = 3
            }
            else {
              error.id = 0
            }
          })
          .catch(() => {
            console.log('error')
          })
        }
      }

    function email_validate() {
      if (!inquiryInfo.email) {
        error.email = 1
      } else if (!regex_email.test(inquiryInfo.email)) {
        error.email = 2
      } else {
        error.email = 0
      }
    }

    function phoneNumber_validate() {
      if (!inquiryInfo.phoneNumber) {
        error.phoneNumber = 1
      } else if (!regex_phoneNumber.test(inquiryInfo.phoneNumber)) {
        error.phoneNumber = 2
      } else {
        error.phoneNumber = 0
      }
    }

    function academyPhoneNumber_validate() {
      if (!inquiryInfo.academyPhoneNumber) {
        error.academyPhoneNumber = 1
      } else if (!regex_phoneNumber.test(inquiryInfo.academyPhoneNumber)) {
        error.academyPhoneNumber = 2
      } else {
        error.academyPhoneNumber = 0
      }
    }

    function address_validate() {
      if (inquiryInfo.zipCode && !inquiryInfo.lastAddress) {
        error.address = 2
      } else {
        error.address = 0
      }
    }

    function validation_check() {
      name_validate()
      if (error.name) {
        document.getElementById("name").focus()
        return
      }

      email_validate()
      if (error.email) {
        document.getElementById("email").focus()
        return
      }

      address_validate()
      if (error.address) {
        document.getElementById("address").focus()
      }

      phoneNumber_validate()
      if (error.phoneNumber) {
        document.getElementById("phoneNumber").focus()
        return
      }
    }

    const sendInquiry = () => {
      validation_check()
      if(Object.values(error).every(v => v === 0)) {
        axios({
          method: 'post',
          url: `${store.state.API_URL}/api/v1/inquires`,
          data: {
            name : inquiryInfo.name,
            email : inquiryInfo.email,
            phoneNumber : inquiryInfo.phoneNumber,
            userId : inquiryInfo.userId,
            academyName : inquiryInfo.academyName,
            zipCode : inquiryInfo.zipCode,
            firstAddress : inquiryInfo.firstAddress,
            lastAddress : inquiryInfo.lastAddress,
            academyPhoneNumber : inquiryInfo.academyPhoneNumber
          }
        })
          .then(() => {
            alert('문의 등록 완료\n3일 내에 안내 메일이 발송됩니다.')
            router.push({ name: 'home' })
          })
      }
    }

    function findAddress() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          const addr = ref('')

          addr.value = data.roadAddress

          inquiryInfo.zipCode = data.zonecode
          inquiryInfo.firstAddress = addr.value

          document.getElementById("detailAddress").focus()
        }
      }).open()
    }

    function toHome() {
      router.push({ name: 'home' })
    }

    function toSignup() {
      router.push({ name: 'signup' })
    }

    function toLogin() {
      router.push({ name: 'login' })
    }

    return {
      inquiryInfo,
      error,
      findAddress,

      toHome,
      toSignup,
      toLogin,

      id_validate,
      name_validate,
      email_validate,
      address_validate,
      phoneNumber_validate,
      academyPhoneNumber_validate,
      sendInquiry,
    }
  }
}
</script>
