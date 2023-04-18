<template>
  <section class="price__area pt-100 pb-130">
    <div class="container">
      <div class="row">
        <div class="col-xxl-4 offset-xxl-4">
          <div class="section__title-wrapper mb-50 text-center">
            <h2 class="section__title">
              지금
              <span class="yellow-bg yellow-bg-big"
                >가입<img src="../../assets/img/shape/yellow-bg.png" alt=""
              /></span>
              하세요!
            </h2>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xxl-10 offset-xxl-1 col-xl-10 offset-xl-1 col-lg-10 offset-lg-1">
          <div class="price__tab-content">
            <div class="tab-content" id="nav-tabContent">
              <div
                class="tab-pane fade show active"
                id="nav-annually"
                role="tabpanel"
                aria-labelledby="nav-annually-tab"
              >
                <div class="row">
                  <div class="price__item grey-bg mb-30 p-relative">
                    <div class="price__head">
                      <h3>학부모</h3>
                    </div>
                    <div class="price__features mb-40">
                      <ul>
                        <li>
                          <i class="far fa-check"></i>포트폴리오 화면 공유를 통한 1:1 비대면 화상
                          상담 서비스
                        </li>
                        <li><i class="far fa-check"></i>학생 출결, 성적 관리</li>
                      </ul>
                    </div>

                     <div class="price__head">
                      <h3>강사</h3>
                    </div>
                    <div class="price__features mb-40">
                      <ul>
                        <li>
                          <i class="far fa-check"></i>포트폴리오 화면 공유를 통한 1:1 비대면 화상
                          상담 서비스
                        </li>
                        <li><i class="far fa-check"></i>원생 포트폴리오 관리</li>
                      </ul>
                    </div>

                     <div class="price__head">
                      <h3>학원장</h3>
                    </div>
                    <div class="price__features mb-40">
                      <ul>
                        <li>
                          <i class="far fa-check"></i>포트폴리오 화면 공유를 통한 1:1 비대면 화상
                          상담 서비스
                        </li>
                        <li><i class="far fa-check"></i>편리한 원생 관리</li>
                      </ul>
                    </div>
                    <div class="btns" v-if="!myseq">
                      <router-link to="/contact" class="e-btn e-btn-4" id="btncolor">학원 등록하기</router-link>
                      <router-link to="/register" class="e-btn e-btn-4 ml-30" id="btncolor"
                        >학부모 / 강사 가입하기</router-link
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { useStore } from 'vuex'
export default {
  name: "PricingArea",
  setup() {
    const store = useStore()
    const myseq = store.state.myseq

    return {
      myseq
    }
  }
};
</script>

<style scoped>
   .btns {
      display: flex;
      justify-content: center;
   }

   #btncolor {
      background-color: rgba(255, 255, 255, 0);
      color: black;
      border-color: #0E1133;
      font-weight: 600;
   }

   #btncolor:hover {
      background-color: #0E1133;
      color: white;
   }
</style>