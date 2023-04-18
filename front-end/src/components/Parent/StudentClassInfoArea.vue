<template>
  <section class="teacher__area pt-100 pb-50">
    <div class="container">
      <div class="row">
        <div class="col-xxl-2 col-xl-2 col-lg-2 col-md-2">
          <div class="teacher__details-thumb p-relative w-img pr-30" @click="moveToProfile" style="cursor: pointer">
            <img v-if="teacher.user_profile" :src="teacher.user_profile" alt="" >
            <img v-else src="@/assets/img/noProfile.png" alt="" />
          </div>
        </div>
        <div class="col-xxl-8 col-xl-8 col-lg-8 mt-15">
          <div class="teacher__wrapper">
            <div class="d-md-flex align-items-end justify-content-between">
              <div class="teacher__info">
                <h4> {{ teacher.user_name }} 강사</h4>
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
              <td class="pr-80">
                <span>{{ teacher.user_phone_number }}</span>
              </td>
              <td class="pr-80">
                <span>{{ teacher.user_email }}</span>
              </td>
              <td class="pr-80">
                <span>{{ teacher.subject }}</span>
              </td>
              <td class="pr-80">
                <span>{{ teacher.grade }}</span>
              </td>
              <td>
                <div v-if="teacher">
                  <div v-for="(date, index) in teacher.day" :key="index">
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
import axios from 'axios';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router'
import { ref } from '@vue/reactivity';
import { onBeforeMount } from '@vue/runtime-core';
export default {
  name: "StudentClassInfoArea",
  setup() {
    const store = useStore()
    const router = useRouter()

    const teacher = ref({
      user_name: '',
      user_phone_number: '',
      user_email: '',
      subject: '',
      grade:'',
      day:'',
      user_profile:''
    })
    const teacherId = ref(0)

    function getTeacher() {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}`,
        header: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      
      .then((res) => {
        teacher.value.user_name = res.data.teacherDeatilInfo.user_name
        teacher.value.user_phone_number = makeNum(res.data.teacherDeatilInfo.user_phone_number)
        teacher.value.user_email = res.data.teacherDeatilInfo.user_email
        teacher.value.subject = checkSubject(res.data.teacherClassInfo[0].subject_code)
        teacher.value.grade = checkGrade(res.data.teacherClassInfo[0].grade_code)
        teacher.value.day = res.data.teacherClassInfo
        teacher.value.user_profile = res.data.teacherDeatilInfo.user_profile_image_url
      })
      .catch((err) => {
        console.log(err)
      })
    }

    const getTeacherId = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/meetings/classes/${store.state.classId}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        },
      })
        .then((res) => {
          teacherId.value = res.data.teacherSeq
        })
    }

    const moveToProfile = () => {
      router.push({ name: 'teacher-details', params: { userSeq: teacherId.value }})
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
    
    const checkSubject = code => subjectName.get(code)
    const subjectName = new Map ([ ["KO", "국어"],
    ["EN", "영어"],
    ["MA", "수학"],
    ["SI", "과학"],
    ["SO", "사회"],
    ["CS", "컴퓨터"],
    ["AL", "알고리즘"]])

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

    onBeforeMount(() => {
      getTeacher()
      getTeacherId()
    })

    return{
      teacher,
      checkDay,
      moveToProfile,
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
