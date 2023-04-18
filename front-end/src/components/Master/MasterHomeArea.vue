<template>
<section class="course__area pt-120 pb-120">
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 cen">
      <h2>
        <div class="row">
          <div class="col-1"></div>
          <div class="col-10">{{ academyInfo.academy_name }}</div>
          <!-- <div class="col-1"><i class="fad fa fa-edit" style="cursor: pointer"></i></div> -->
        </div>
        </h2>
      <h4>{{ academyInfo.academy_introduce }}</h4>
      <hr>
    </div>
    <div class="col-2">
    </div>
    </div>
    <div class="row">
    <div class="col-2"></div>
    <div class="col-8 cen">
      <i class="fas fa-3x fa-map-marker-alt"></i> <h2 style="margin-top: 10px">{{ academyInfo.first_address}}        {{academyInfo.last_address}}</h2>
    </div>
    <div class="col-2"></div>
    </div>
    <div class="row">
    <div class="col-2"></div>
    <div class="col-8 cen">
      <i class="fas fa-3x fa-phone"></i><h2>{{ makeNum(academyInfo.academy_phone_number) }}</h2>
    </div>
    <div class="col-2"></div>
    </div>
</section>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { onBeforeMount, ref } from 'vue'
export default {
   name:'MasterHomeArea',
   setup() {
    const store = useStore()
    const academyInfo = ref({})
    const getInfo = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/masters/${store.state.myseq}/academies`,
        headers: {
          Authorization: 'Bearer ' + store.state.token
        }
      })
      .then((res) => {
        academyInfo.value = res.data.academyInfo
      })
    }
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

    onBeforeMount(() => {
      getInfo()
    })
    return {
      academyInfo,
      makeNum,
    }
   }
};
</script>

<style scoped>
.cen {
  text-align: center;
  padding-top: 20px;
}
</style>