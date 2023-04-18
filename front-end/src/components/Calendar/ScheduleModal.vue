<template>
  <div>
    {{ selectedDay }}
  </div>
  <div class="sign__input-wrapper mb-25">
    <form @submit.prevent="addSchedule">
      <h5>시간</h5>
      <div class="row">
        <div class="col-10">
          <span><input class="inp" id="inp-first" type="number" v-model.number="startTimeHour" min="9" max="20"/></span>
          <span>시</span>
          <span><input class="inp" type="number" v-model.number="startTimeMinute" min="00" max="59"/></span>
          <span>분 - </span>
          <span><input class="inp" type="number" v-model.number="endTimeHour" min="9" max="20" /></span>
          <span>시</span>
          <span><input class="inp" type="number" v-model.number="endTimeMinute" min="00" max="59"/></span>
          <span>분</span>
        </div>
        <div>
          <h5 class="aa">항목</h5>
          <select v-model="scheduleCode">
            <option selected>---선택---</option>
            <option value="SC">수업</option>
            <option value="SM">상담</option>
            <option value="SR">휴게</option>
            <option value="SP">개인</option>
            <option value="SE">기타</option>
          </select>
          <span><button type="submit" class="register">등록</button></span>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import { onUpdated, ref } from 'vue'
import axios from 'axios'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  props: {
    selectedDate : String
  },
    setup() {
      const router = useRouter()
      const store = useStore()
      const scheduleCode = ref('')
      const selectedDay = ref('')
      const startTimeHour = ref(0)
      const startTimeMinute = ref(0)
      const endTimeHour = ref(0)
      const endTimeMinute = ref(0)
      

      function addSchedule() {

        const day = selectedDay.value.split('-')
        if (store.state.profile.userCode === 'UT') {
          axios({
            method: 'post',
            url: `${store.state.API_URL}/api/v1/teachers/${store.state.myseq}/schedules`,
            data: {
              scheduleCode : scheduleCode.value,
              scheduleDate : String(day[0] + day[1] + day[2]),
              startTimeHour : startTimeHour.value,
              startTimeMinute : startTimeMinute.value,
              endTimeHour : endTimeHour.value,
              endTimeMinute : endTimeMinute.value
            },
            headers: {
              Authorization: `Bearer ${store.state.token}`
            }
          })
          .then(() => {
            router.go(0)
          })
          .catch((err) => {
            console.log(err)
          })
        } else {
          axios({
            method: 'post',
            url: `${store.state.API_URL}/api/v1/masters/${store.state.myseq}/schedules`,
            data: {
              scheduleCode : scheduleCode.value,
              scheduleDate : String(day[0] + day[1] + day[2]),
              startTimeHour : startTimeHour.value,
              startTimeMinute : startTimeMinute.value,
              endTimeHour : endTimeHour.value,
              endTimeMinute : endTimeMinute.value
            },
            headers: {
              Authorization: `Bearer ${store.state.token}`
            }
          })
          .then(() => {
            router.go(0)
          })
          .catch((err) => {
            console.log(err)
          })
        }
      }

      onUpdated(() => {
        selectedDay.value = store.state.selectedDate
      })

      return {
        scheduleCode,
        selectedDay,
        startTimeHour,
        startTimeMinute,
        endTimeHour,
        endTimeMinute,
        addSchedule
      }
    },
}
</script>

<style scoped>
.register {
  position: relative;
  left: 300px;
  top: 45px;
  width: 55px;
  height: 35px;
  margin-bottom: 15px;
  border-radius: 8%;
  background-color: #0e1133;
  color: white;
}
.aa{
  margin-top: 20px;
  margin-bottom: 0px;
}
</style>