<template>
  <section class="hero__area hero__height d-flex align-items-center grey-bg-2 p-relative">
    <div class="container">
      <div class="hero__content-wrapper">
        <div class="align-items-center">
          <div class="">
            <div class="hero__content p-relative z-index-1">
              <div class="parentIntroDiv">
                <h3 class="hero__title font">
                  <span></span>
                  <span class="yellow-shape"
                    >학원장<img
                      src="../../assets/img/shape/yellow-bg.png"
                      alt="yellow-shape"
                    /> </span
                  >를 위한
                </h3>
                <p class="mt-30">Educal로 재원, 퇴원생을 손 쉽게 관리할 수 있습니다.</p>
              </div>
              <div class="masterImgDiv">
                <img src="../../assets/img/masterPage.png" style="height: 600px; widgth: 900px" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "HeroSection",
};
</script>

<style>
@font-face {
  font-family: "baemin";
  src: url("../../assets/fonts/BMHANNAAir_ttf.ttf");
}

.font {
  font-family: "baemin";
}

.parentBox {
  position: relative;
}

.parentIntroDiv {
  position: relative;
  z-index: 999;
  /* width: 50px; */
  /* background-color: #edeef3; */
}

.masterImgDiv {
  position: absolute;
  width: 500px;
  height: 500px;
  left: 480px;
  bottom: -100px;
}
</style>
