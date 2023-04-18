// 학원장

<template>
  <header>
    <div
      id="header-sticky"
      :class="`${
        isSticky
          ? 'header__area header__transparent header__padding-2 sticky'
          : `${headerShadow ? headerShadow : 'header__area header__transparent header__padding-2'}`
      }`"
    >
      <div class="container">
        <div class="row align-items-center">
          <div class="col-xxl-3 col-xl-3 col-lg-4 col-md-2 col-sm-4 col-6">
            <div class="header__left d-flex">
              <div class="logo">
                <router-link to="/">
                  <img src="../../assets/img/logo/logo.png" alt="logo" />
                </router-link>
              </div>
            </div>
          </div>
          <div class="col-xxl-9 col-xl-9 col-lg-8 col-md-10 col-sm-8 col-6">
            <div class="header__right d-flex justify-content-end align-items-center">
              <div class="main-menu main-menu-2">
                <nav id="mobile-menu" class="d-none d-xl-block">
                  <ul>
                    <li class="has-dropdown">
                      <router-link :to="{ name: 'AcademyClassList', params: { student_id: seq } }"
                        >학원관리</router-link
                      >
                      <ul class="submenu">
                        <li>
                          <router-link
                            :to="{ name: 'AcademyClassList', params: { student_id: seq } }"
                            >반 관리</router-link
                          >
                        </li>
                        <li>
                          <router-link :to="{ name: 'master-teachers', params: { academyId: seq } }"
                            >강사 관리</router-link
                          >
                        </li>
                        <li>
                          <router-link :to="{ name: 'master-students', params: { academyId: seq } }"
                            >원생 관리</router-link
                          >
                        </li>
                      </ul>
                    </li>
                    <li>
                      <router-link to="/teacher/counsel">상담</router-link>
                    </li>
                    <li>
                      <router-link to="/teacher/schedule">스케줄</router-link>
                    </li>
                    <li>
                      <div id="alertBox" @click="alertModalOpen">
                        <div id="alertImgDiv">
                          <img src="../../assets/img/alertIcon.png" id="alertIcon" />
                        </div>
                        <div v-if="isModalOpen" v-click-outside="onClickOutside">
                          <AlertModalArea />
                        </div>
                        <div id="alertCnt">{{ alert_Cnt }}</div>
                      </div>
                    </li>
                    <li class="has-dropdown2">
                      <router-link to="/my">
                        <img v-if=profile.userProfileImageUrl :src="profile.userProfileImageUrl" alt="" style="width: 40px; height: 40px; border-radius: 50%;">
                        <img v-else src="../../assets/img/noProfile.png" id="noProfileImg"/>
                      </router-link>
                      <ul class="submenu">
                        <li><router-link to="/my">마이페이지</router-link></li>
                        <li v-if="academyId"><router-link :to="{ name: 'AcademyIntro', params: { academy_id: academyId } }">내 학원</router-link></li>
                        <li><router-link to="#" @click="logout" id="logoutBtn">로그아웃</router-link
                          >
                        </li>
                      </ul>
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { onMounted, ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import axios from 'axios'
import AlertModalArea from "../../components/common/AlertModalArea.vue";
import vClickOutside from "click-outside-vue3";
      
export default {
  name: "HomeHeader",

  components: { AlertModalArea },

  directives: {
    clickOutside: vClickOutside.directive,
  },

  setup() {
    const isSticky = ref("false")
    const store = useStore()
    const router = useRouter()
    const seq = store.state.myseq
    const alert_Cnt = ref('')
    const academyId = ref(0)

    const profile = store.state.profile

    const logout = async () => {
      store.commit("CLEAR_LOCAL")
      localStorage.clear()
      await router.push({ name: "home" })
      window.location.reload()
    }
    
    const getNotification = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/alerts/users/${store.state.myseq}`
      })
      .then((res) => {
        let tmp = 0
        for (let i of res.data.alertList) {
          if (i.readYn === false) {
            tmp = tmp + 1
          }
        }
        alert_Cnt.value = tmp
      })
      .catch(() => {

      })
    }

    const getAcademy = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/masters/${store.state.myseq}/academies`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
        .then((res) => {
          academyId.value = res.data.academyInfo.academy_id
        })
      
    }
    
    onMounted(() => {
      getAcademy()
      getNotification()
      console.log(profile)
    })

    return {
      isSticky,
      logout,
      seq,
      getNotification,
      alert_Cnt,
      profile,
      academyId
    };
  },
  props: {
    headerShadow: String,
  },
  methods: {
    handleSticky() {
      if (window.scrollY >= 80) {
        this.isSticky = true;
      } else {
        this.isSticky = false;
      }
    },
    alertModalOpen() {
      this.isModalOpen = true;
    },
    onClickOutside() {
      this.isModalOpen = false;
    },
  },
  mounted() {
    window.addEventListener("scroll", this.handleSticky);
  },
  data() {
    return {
      isModalOpen: false,
    };
  },
};
</script>

<style scoped>
#noProfileImg {
  width: 40px;
  height: 40px;
  background-color: #b4b4b441;
  border-radius: 50%;
}
#logoutBtn {
  color: red;
}
#logoutBtn:hover {
  color: white;
}

/* alert */
#alertIcon {
  width: 30x;
  height: 30px;
  margin-right: 8px;
  filter: opacity(0.5) drop-shadow(0 0 0 #7e7d7d);
}
#alertBox {
  position: relative;
  margin-right: 18px;
  cursor: pointer;
}
#alertImgDiv {
  position: absolute;
  top: -5px;
}
#alertCnt {
  position: relative;
  z-index: 10;
  text-align: center;
  padding-top: 1px;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background-color: rgb(255, 0, 0);
  color: white;
  left: 17px;
  top: 9px;
  font-size: 15px;
}
</style>
