<template>
  <section class="hero__area hero__height d-flex align-items-center grey-bg-2 p-relative">
    <div class="container">
      <div class="hero__content-wrapper">
        <div class="align-items-center">
          <div class="">
            <div class="hero__content p-relative z-index-1 parentBox">
              <div class="parentIntroDiv">
                <h3 class="hero__title font">
                  <span></span>
                  <span class="yellow-shape"
                    >학부모<img
                      src="../../assets/img/shape/yellow-bg.png"
                      alt="yellow-shape"
                    /> </span
                  >를 위한
                </h3>
                <p class="mt-30" style="padding-right: 0">
                  아이의 학원생활을 쉽게 알 수 있게 해줍니다.
                </p>
              </div>
              <div class="meetingImgDiv">
                <img src="../../assets/img/meetingPage.png" style="height: 600px; widgth: 900px" />
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

.meetingImgDiv {
  position: absolute;
  width: 200px;
  height: 100px;
  left: 480px;
  bottom: 220px;
}
</style>
