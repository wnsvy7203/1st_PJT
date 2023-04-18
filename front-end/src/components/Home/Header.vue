<template>
  <header>
    <div
      id="header-sticky"
      :class="`${
        isSticky
          ? 'header__area header__transparent header__padding sticky'
          : 'header__area header__transparent header__padding'
      } ${header__white && header__white}`"
    >
      <div class="container-fluid">
        <div class="row align-items-center">
          <div class="col-xxl-3 col-xl-3 col-lg-4 col-md-2 col-sm-4 col-6">
            <div class="header__left d-flex">
              <div class="logo">
                <router-link to="/">
                  <img
                    v-if="header__white && !isSticky"
                    src="../../assets/img/logo/logo-2.png"
                    alt="logo"
                  />

                  <img v-else src="../../assets/img/logo/logo.png" alt="logo" />
                </router-link>
              </div>
            </div>
          </div>
          <div class="col-xxl-9 col-xl-9 col-lg-8 col-md-10 col-sm-8 col-6">
            <div class="header__right d-flex justify-content-end align-items-center">
              <div :class="`main-menu ${header__white && 'main-menu-3'}`">
                <nav id="mobile-menu" class="d-none d-xl-block">
                  <ul>
                    <li><router-link to="/contact">상담문의</router-link></li>
                  </ul>
                </nav>
              </div>
              <div class="header__btn ml-30 d-none d-sm-block">
                <router-link to="/login" class="e-btn">로그인</router-link>
              </div>
              <div class="sidebar__menu d-xl-none">
                <div @click="handleSidebar" class="sidebar-toggle-btn ml-30" id="sidebar-toggle">
                  <span class="line"></span>
                  <span class="line"></span>
                  <span class="line"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "HomeHeader",
  props: {
    header__white: String,
  },
  data() {
    return {
      isSticky: false,
    };
  },
  methods: {
    handleSticky() {
      if (window.scrollY > 80) {
        this.isSticky = true;
      } else {
        this.isSticky = false;
      }
    },
  },
  mounted() {
    window.addEventListener("scroll", this.handleSticky);
  },
};
</script>
