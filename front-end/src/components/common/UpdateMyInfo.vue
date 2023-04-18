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
          <div class="section__title-wrapper text-center mb-25">
            <h2 class="section__title">내 정보 수정</h2>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xxl-6 offset-xxl-3 col-xl-6 offset-xl-3 col-lg-8 offset-lg-2">
          <div class="sign__wrapper white-bg">
            <div class="sign__form">
              <div class="sign__input-wrapper mb-25">
                <!-- 프로필 이미지 -->   
                <div class="profileImg mt-20 mb-">             
                  <label for="file-input">
                    <img v-if="userInfo.profileImg" :src="userInfo.profileImg" alt="" style="cursor: pointer;">
                    <img v-else src="../../assets/img/noProfile.png" style="cursor: pointer;">
                  </label>
                  <input 
                    @change="imgUpdated"
                    id="file-input"
                    type="file"
                    style="display: none">
                </div>
                <div v-if=userInfo.profileImg class="sign__input sign__agree d-flex align-items-center profileInput mb-30 mt-20">
                  <label for="profile-img">사진 삭제</label>
                  <button
                    @click="imgDeleted"
                    id="profile-img"
                  ></button>
                </div>
                <div v-else class="sign__input sign__agree d-flex align-items-center profileInput mb-30 mt-20">
                  <label for="file-input">사진 등록</label>
                  <input
                    @change="imgUpdated"
                    type="file"
                    id="file-input"
                    accept="image/*"
                  >
                </div>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>회원유형</h5>
                <div class="myInfo">{{ makeType(userInfo.type) }}</div>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>이름</h5>
                <div class="sign__input">
                  <div class="myInfo">{{ userInfo.name }}</div>
                </div>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>연락처</h5>
                <div class="sign__input">
                  <input
                    id="phoneNumber"
                    type="text"
                    placeholder="010-1234-5678"
                    v-model="userInfo.phoneNumber"
                    maxlength="11"
                    @blur="phoneNumber_validate"
                  />
                </div>
                <p v-if="error.phoneNumber === 1" class="err-msg">필수 입력사항입니다.</p>
                <p v-if="error.phoneNumber === 2" class="err-msg">숫자만 입력 가능합니다.</p>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>아이디</h5>
                <div class="sign__input">
                  <div class="myInfo">{{ userInfo.id }}</div>
                </div>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>이메일</h5>
                <div class="sign__input">
                  <input
                    id="email"
                    type="text"
                    placeholder="ssafy123@gmail.com"
                    v-model="userInfo.email"
                    @blur="email_validate"
                  />
                </div>
                <p v-if="error.email === 1" class="err-msg">필수 입력사항입니다.</p>
                <p v-if="error.email === 2" class="err-msg">이메일 주소를 정확히 입력해주세요.</p>
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
                    placeholder="50612"
                  />
                </div>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>기본주소</h5>
                <div class="sign__input">
                  <input
                    type="text"
                    @click="findAddress"
                    placeholder="서울특별시 강남구"
                    v-model="userInfo.address"
                    readonly
                  />
                </div>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>상세주소</h5>
                <div class="sign__input">
                  <input
                    type="text"
                    id="detailAddress"
                    v-model="userInfo.detailAddress"
                    placeholder="1205호"
                    @blur="address_validate"
                  />
                </div>
                <p v-if="error.address === 1" class="err-msg">필수 입력사항입니다.</p>
                <p v-if="error.address === 2" class="err-msg">상세주소를 입력해주세요.</p>
              </div>
              <button class="e-btn w-100" @click="sendInfo">저장</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { ref, reactive } from "vue";
import axios from "axios"
import AWS from 'aws-sdk'

export default {
  name: "UpdateMyInfo",
  setup() {
    const store = useStore();
    const router = useRouter();

    // userInfo 값 입력
    const userInfo = reactive({
      type: store.state.profile.userCode,
      id: store.state.profile.userId,
      name: store.state.profile.userName,
      email: store.state.profile.userEmail,
      phoneNumber: store.state.profile.userPhoneNumber,
      zonecode: store.state.profile.zipCode,
      address: store.state.profile.firstAddress,
      detailAddress: store.state.profile.lastAddress,
      profileImg: store.state.profile.userProfileImageUrl
    })

    const userInfo2 = reactive({
      firstAddress: '',
      lastAddress: '',
      userCode: '',
      userEmail: '',
      userId: '',
      userName: '',
      userPhoneNumber: '',
      userProfileImageUrl: '',
      userSeq: '',
      zipCode: '',
    })

    // 비밀번호 확인은 pwd와 값만 같으면 valid check 통과
    const pwd2 = ref("");

    // 에러 코드 작성
    const error = reactive({
      type: 0,
      id: 0,
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
    const regex_email =
      /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const regex_phoneNumber = /^[0-9]+$/;


    // 회원가입 시 validation check 하는 부분
    // 에러 코드 1이면 필수 입력 값 없음
    // template v-if p태그 참조

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
      email_validate();
      if (error.email) {
        document.getElementById("email").focus();
        return;
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
    
    const makeType = code => typeName.get(code)
    const typeName = new Map ([ ["UM", "학원장"],
    ["UP", "학부모"],
    ["UT", "강사"]])

    const file = ref(null)

    const imgUpdated = (event) => {
      file.value = event.target.files[0]

      const reader = new FileReader()
      reader.onload = (e) => {
        userInfo.profileImg = e.target.result
      }
      reader.readAsDataURL(file.value)
    }

    const imgDeleted = () => {
      if (confirm('정말 삭제하시겠습니까?')) {
        const reader = new FileReader()
        reader.onload = (e) => {
          userInfo.profileImg = e.target.result
        }
        file.value = null
      }
    }

    const imgUrl = () => {
      return new Promise((resolve, reject) => {
        AWS.config.update({
          region: 'ap-northeast-2',
          credentials: new AWS.CognitoIdentityCredentials({
            IdentityPoolId: 'ap-northeast-2:dd6b4d5b-8da0-448d-96c9-a1d68d4385d1',
          })
        })

        const s3 = new AWS.S3({
          apiVersion: '2012-10-17',
          params: {
            Bucket: 'ssafy-hot-six'
          }
        })

        s3.upload({
          Key: file.value.name,
          Body: file.value
        }, (err, data) => {
          if (err) {
            reject(err)
            return alert('There was an error uploading your photo: ', err.message)
          }
          return resolve(data.Location)
        })
      })
    }

    // 정보 보내기 전에 validation check
    const sendInfo = async () => {
      validation_check()

      if (file.value) {
        userInfo.profileImg = await imgUrl()
      }

      if (Object.values(error).every((v) => v === 0)) {
        console.log('asdfas')
        axios({
          method: 'put',
          url: `${store.state.API_URL}/api/v1/users/${store.state.myseq}`,
          data: {
            userName: userInfo.name,
            userPhoneNumber: userInfo.phoneNumber,
            zipCode: userInfo.zonecode,
            firstAddress: userInfo.address,
            lastAddress: userInfo.detailAddress,
            userEmail: userInfo.email,
            userProfileImageUrl: userInfo.profileImg
          },
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        }).then(() => {
          alert("회원정보 수정 완료");
          userInfo2.firstAddress = userInfo.address,
          userInfo2.lastAddress = userInfo.detailAddress,
          userInfo2.userCode = store.state.profile.userCode
          userInfo2.userEmail = userInfo.email,
          userInfo2.userId = store.state.profile.userId
          userInfo2.userName = userInfo.name,
          userInfo2.userPhoneNumber = userInfo.phoneNumber,
          userInfo2.userProfileImageUrl = userInfo.profileImg,
          userInfo2.userSeq = store.state.profile.userSeq
          userInfo2.zipCode = userInfo.zonecode
          store.dispatch('saveProfile', userInfo2)
          router.push({ name: "mypage" });
        });
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
      userInfo2,
      findAddress,

      imgUpdated,
      imgDeleted,

      email_validate,
      phoneNumber_validate,
      address_validate,

      sendInfo,
      makeType,
      typeName
    };
  },
};
</script>

<style scoped>
.sign__input input {
  width: 100%;
  height: 50px;
  line-height: 52px;
  padding: 0 20px;
  font-size: 14px;
  border: 2px solid transparent;
  background: #f6f6f7;
  color: #000000;
  border-radius: 6px;
}

.myInfo {
  width: 100%;
  height: 50px;
  line-height: 52px;
  padding: 0 20px;
  font-size: 14px;
  border: 2px solid transparent;
  background: #f6f6f7;
  color: #000000;
  border-radius: 6px;
  font-weight: 600;
}

.sign__input-wrapper p {
  margin: 5px 15px;
  font-size: 14px;
}

.profileImg {
  width: 120px;
  height: 120px;
  margin: auto;
}

.profileImg img {
  width: 120px;
  height: 120px;
  border: 2px solid rgb(177, 177, 177);
  border-radius: 50%;
}

.profileInput label {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 7px 10px;
  color: rgb(255, 255, 255);
  background-color: #737373;
  border: 1px solid rgb(171, 171, 171);
  height: 35px;
  margin: auto;
  border-radius: 6px;
  width: 110px;
  font-size: 15px;
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
