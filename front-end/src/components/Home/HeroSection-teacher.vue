<template>
  <section class="hero__area hero__height d-flex align-items-center p-relative">
    <div class="container">
      <div class="hero__content-wrapper">
        <div class="align-items-center">
          <div class="">
            <div class="p-relative z-index-1" dir="rtl">
              <div class="parentIntroDiv">
                <h3 class="hero__title font">
                  <span></span>
                  <span class="yellow-shape"
                    >강사<img
                      src="../../assets/img/shape/yellow-bg.png"
                      alt="yellow-shape"
                    /> </span
                  >를 위한
                </h3>
                <p class="mt-30 teacherInt" style="widht: 300px">
                  .스케쥴 관리 및 학부모 상담이 가능합니다
                </p>
              </div>
              <div class="teacherImgDiv">
                <img src="../../assets/img/teacherPage.png" style="height: 600px; widgth: 900px" />
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
  margin-right: 0;
}

.teacherImgDiv {
  position: absolute;
  width: 200px;
  height: 100px;
  left: 520px;
  bottom: 190px;
}

.teacherInt {
  font-size: 22px;
  color: #0e1133;
  line-height: 30px;
  margin-bottom: 28px;
}
</style>
