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
            <h2 class="section__title">내 정보 관리</h2>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xxl-6 offset-xxl-3 col-xl-6 offset-xl-3 col-lg-8 offset-lg-2">
          <div class="sign__wrapper white-bg">
            <div class="sign__form">
              <div class="sign__input-wrapper mb-25">
                <!-- 프로필 이미지 -->
                <div class="profileImg">
                  <img v-if=profile.userProfileImageUrl :src="profile.userProfileImageUrl" alt="" style="border-radius: 50%;">
                  <img v-else src="../../assets/img/noProfile.png" />
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
                  <div class="myInfo">{{ makeNum(userInfo.phone) }}</div>
                </div>
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
                  <div class="myInfo">{{ userInfo.email }}</div>
                </div>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>우편번호</h5>
                <div class="sign__input">
                  <div class="myInfo">{{ userInfo.zipcode }}</div>
                </div>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>기본주소</h5>
                <div class="sign__input">
                  <div class="myInfo">{{ userInfo.firstaddress }}</div>
                </div>
              </div>
              <div class="sign__input-wrapper mb-25">
                <h5>상세주소</h5>
                <div class="sign__input">
                  <div class="myInfo">{{ userInfo.lastaddress}}</div>
                </div>
              </div>
              <button class="e-btn w-100">
                <router-link to="/updateMyInfo">정보 수정하기</router-link>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { useStore } from "vuex";
import { reactive } from "vue";

export default {
  name: "MypageArea",
  setup() {
    const store = useStore();
    const userInfo = reactive({
      type: store.state.profile.userCode,
      name: store.state.profile.userName,
      phone: store.state.profile.userPhoneNumber,
      id: store.state.profile.userId,
      email: store.state.profile.userEmail,
      zipcode: store.state.profile.zipCode,
      firstaddress: store.state.profile.firstAddress,
      lastaddress: store.state.profile.lastAddress,
    })

    const profile = store.state.profile


    const makeNum = (num) => {
      if (!num) return;

      const match11 = num.match(/^(\d{3})(\d{4})(\d{4})$/);
      if (match11) return match11.slice(1).join('-');

      const match10_s = num.match(/^(0[2])(\d{4})(\d{4})$/);
      if (match10_s) return match10_s.slice(1).join('-');

      const match10 = num.match(/^(\d{3})(\d{3})(\d{4})$/);
      if (match10) return match10.slice(1).join('-');

      const match9 = num.match(/^(\d{2})(\d{3})(\d{4})$/);
      if (match9) return match9.slice(1).join('-');

      return num;
    }

    const makeType = code => typeName.get(code)
    const typeName = new Map ([ ["UM", "학원장"],
    ["UP", "학부모"],
    ["UT", "강사"]])
    
    return {
      userInfo,
      makeNum,
      makeType,
      profile
    };
  },
};
</script>

<style scoped>
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