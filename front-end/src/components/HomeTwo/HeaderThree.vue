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
                      <router-link to="#">자녀 관리</router-link>
                      <ul class="submenu">
                        <li v-for="child of children" :key="child">
                          <router-link
                            :to="{
                              name: 'AcademyClassList',
                              params: { student_id: child.student_id },
                            }"
                            @click="saveChild(child.student_id)"
                            >{{ child.student_name }}</router-link
                          >
                        </li>
                        <li>
                          <router-link to="/ChildRegist">+ 자녀 등록</router-link>
                        </li>
                      </ul>
                    </li>
                    <li>
                      <router-link :to="{name: 'parent-counsel'}">상담관리</router-link>
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
                        <li>
                          <router-link to="#" @click="logout" id="logoutBtn"
                            >로그아웃</router-link
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
import { ref, onBeforeMount, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import axios from "axios";

import AlertModalArea from "../../components/common/AlertModalArea.vue";
import vClickOutside from "click-outside-vue3";

export default {
  name: "HomeThreeHeader",

  components: { AlertModalArea },

  directives: {
    clickOutside: vClickOutside.directive,
  },

  setup() {
    const isSticky = ref("false");
    const store = useStore();
    const router = useRouter();
    const children = ref([]);
    const alert_Cnt = ref('')
    const profile = store.state.profile
    const logout = async () => {
      store.commit("CLEAR_LOCAL")
      localStorage.clear()
      await router.push({ name: "home" })
      window.location.reload()
    }

    function getChild() {
      if (store.state.profile.userCode === "UP") {
        axios({
          method: "get",
          url: `${store.state.API_URL}/api/v1/parents/${store.state.myseq}/students`,
          headers: {
            Authorization: `Bearer ${store.state.token}`,
          },
        }).then((res) => {
          children.value = res.data.list;
          store.dispatch("saveChildren", children.value);
        });
      }
    }

    function saveChild(student_id) {
      store.dispatch("saveStudentid", student_id);
    }

    function deleteChild(student_id) {
      axios({
        method: "delete",
        url: `${store.state.API_URL}/api/v1/parents/students/${student_id}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`,
        },
      })
        .then(() => {
          router.push({ name: "home" });
        })
        .catch((err) => {
          console.log(err);
        });
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
    onBeforeMount(() => {
      getChild();
    })

    onMounted(() => {
      getNotification()
    })
    return {
      isSticky,
      getChild,
      children,
      saveChild,
      logout,
      deleteChild,
      getNotification,
      alert_Cnt,
      profile
    };
  },
  props: {
    headerShadow: String,
  },
  methods: {
    handleSticky() {
      if (window.scrollY > 80) {
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

<style>
.icon_minus {
  height: 20px;
  border: 2px solid;
  border-radius: 50%;
}

.submenu {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

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
