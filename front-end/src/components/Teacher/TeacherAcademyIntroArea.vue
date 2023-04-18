<template>
<section class="course__area pt-120 pb-120">
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 cen">
      <h2>
        <div class="row">
          <div class="col-2"></div>
          <div class="col-8">{{ academyInfo.academyName}}</div>
          <div class="col-2"><button class="b" @click="register">등록신청</button></div>
        </div>
        </h2>
      <h4>{{ academyInfo.academyIntroduce}}</h4>
      <hr>
    </div>
    <div class="col-2">
    </div>
    </div>
    <div class="row">
    <div class="col-2"></div>
    <div class="col-8 cen">
      <i class="fas fa-3x fa-map-marker-alt"></i> <h2 style="margin-top: 10px">{{ academyInfo.firstAddress}}        {{academyInfo.lastAddress}}</h2>
    </div>
    <div class="col-2"></div>
    </div>
    <div class="row">
    <div class="col-2"></div>
    <div class="col-8 cen">
      <i class="fas fa-3x fa-phone"></i><h2>{{ makeNum(academyInfo.academyPhoneNumber) }}</h2>
    </div>
    <div class="col-2"></div>
    </div>
</section>
</template>

<script>
import axios from 'axios'
import {useStore} from 'vuex'
import {useRoute, useRouter} from 'vue-router'
import {onBeforeMount, ref} from 'vue'
export default {
   name:'AcademyIntroArea',
   setup() {
    const store = useStore()
    const route = useRoute()
    const router = useRouter()
    const academy_id = {route}.route.params.academy_id
    const academyInfo = ref('')
    const getInfo = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/academies/${academy_id}`,
        headers: {
          Authorization: 'Bearer ' + store.state.token
        }
      })
      .then((res) => {
        academyInfo.value = res.data
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

    const register = () => {
      axios({
        method:'get',
        url: `${store.state.API_URL}/api/v1/teachers/${store.state.myseq}/academies/${academy_id}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`,
        },
      })
      .then(() => {
        alert(`${academyInfo.value.academyName}학원 등록 신청되었습니다.`)
        router.push({ name : 'SearchAcademy' })
      })
    }
    onBeforeMount(() => {
      getInfo()
    })
    return {
      academyInfo,
      makeNum,
      register
    }
   }
};
</script>

<style scoped>
.cen {
  text-align: center;
  padding-top: 20px;
}

.b {
  font-size: 20px;
  border: 1px solid black;
  border-radius: 3px;
  padding: 4px 4px 4px 4px;
}
</style>