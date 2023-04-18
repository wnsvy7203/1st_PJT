<template>
  <section class="teacher__area pt-100 pb-50">
    <div class="container">
      <div class="row">
        <div class="col-xxl-2 col-xl-2 col-lg-2 col-md-2">
          <div class="teacher__details-thumb p-relative w-img pr-30">
            <img v-if=profile.userProfileImageUrl :src="profile.userProfileImageUrl" alt="">
            <img v-else src="../../assets/img/noProfile.png" alt=""/>
          </div>
        </div>
        <div class="col-xxl-8 col-xl-8 col-lg-8 mt-15">
          <div class="teacher__wrapper">
            <div class="d-md-flex align-items-end justify-content-between">
              <div class="teacher__info">
                <h4>{{ profile.userName }}</h4>
              </div>
            </div>
          </div>

          <table class="mt-40 ml-10">
            <tr>
              <th>연락처</th>
              <th>이메일</th>
              <th>과목</th>
              <th>학년</th>
              <th>일시</th>
            </tr>
            <tr>
              <td class="pr-80"><span>{{ makeNum(profile.userPhoneNumber) }}</span></td>
              <td class="pr-80"><span>{{ profile.userEmail }}</span></td>
              <td class="pr-80"><span v-if="info">{{ checkSubject(info[0].subject_code) }}</span></td>
              <td class="pr-80"><span v-if="info">{{ checkGrade(info[0].grade_code) }}</span></td>
              <td>
                <div v-if="info">
                  <div v-for="(date, index) in info" :key="index">
                    {{ checkDay(date.day_of_the_week) }} {{date.start_time_hour.toString().padStart(2, '0')}}:{{date.start_time_minute.toString().padStart(2, '0')}} ~ {{date.end_time_hour.toString().padStart(2, '0')}}:{{date.end_time_minute.toString().padStart(2, '0')}}
                  </div>
                </div>
              </td>
            </tr>
          </table>
        </div>

        <hr class="mt-30" />
      </div>
    </div>
  </section>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { onBeforeMount, ref } from 'vue'
export default {
  name: "InstructorDetailsArea",
  setup() {
    const store = useStore()
    const info = ref('')
    const profile = store.state.profile
    const getClass = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}`
      })
      .then((res) => {
        info.value = res.data.teacherClassInfo
      })
    }
    const checkGrade = code => gradeLevels.get(code)
    const gradeLevels = new Map([  ["E1", "초1"],
      ["E2", "초2"],
      ["E3", "초3"],
      ["E4", "초4"],
      ["E5", "초5"],
      ["E6", "초6"],
      ["M1", "중1"],
      ["M2", "중2"],
      ["M3", "중3"],
      ["H1", "고1"],
      ["H2", "고2"],
      ["H3", "고3"]
    ]);
    const checkSubject = code => subjectName.get(code)
    const subjectName = new Map ([ ["KO", "국어"],
    ["EN", "영어"],
    ["MA", "수학"],
    ["SI", "과학"],
    ["SO", "사회"],
    ["CS", "컴퓨터"],
    ["AL", "알고리즘"]])

    const checkDay = day => dayName.get(day)
    const dayName = new Map ([
      ["Mon", "월"],
      ["Tue", "화"],
      ["Wed", "수"],
      ["Thu", "목"],
      ["Fri", "금"],
      ["Sat", "토"],
      ["Sun", "일"],
    ])

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
      getClass()
    })

    return {
      profile,
      info,
      getClass,
      checkGrade,
      checkSubject,
      checkDay,
      makeNum
    }
  }
};
</script>

<style scoped>
.work_history {
  font-size: 2rem;
  margin-top: 1rem;
}
.work_history_detail {
  margin-top: 1rem;
}
</style>
