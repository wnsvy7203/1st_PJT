<template>
  <section class="teacher__area pt-100 pb-50">
    <div class="container">
      <div class="row">
        <div class="col-xxl-2 col-xl-2 col-lg-2 col-md-2">
          <div class="teacher__details-thumb p-relative w-img pr-30">
            <img v-if="teacherInfo.user_profile_image_url" :src="teacherInfo.user_profile_image_url" alt="">
            <img v-else src="../../assets/img/noProfile.png" alt="" />
          </div>
        </div>
        <div class="col-xxl-8 col-xl-8 col-lg-8 mt-15">
          <div class="teacher__wrapper">
            <div class="d-md-flex align-items-end justify-content-between">
              <div class="teacher__info">
                <h4>{{ teacherInfo.user_name }}</h4>
                <div class="row mt-2">
                    <button class="insertStudent" data-bs-toggle="modal" data-bs-target="#exampleModal2" style="width: 100px;">강사 수정</button>
                    <button class="insertStudent aaa" @click="deleteClass">반 삭제</button>
                </div>
              </div>
            </div>
          </div>
          <table class="mt-40 ml-10">
            <tr v-if="classInfo.length"> 
              <th>연락처</th>
              <th>이메일</th>
              <th>과목</th>
              <th>학년</th>
              <th>일시</th>
            </tr>
            <tr v-if="classInfo.length > 0">
              <td class="pr-80"><span>{{ makeNum(teacherInfo.user_phone_number) }}</span></td>
              <td class="pr-80"><span>{{ teacherInfo.user_email }}</span></td>
              <td class="pr-80"><span>{{ checkSubject(classInfo[0].subject_code) }}</span></td>
              <td class="pr-80"><span>{{ checkGrade(classInfo[0].grade_code) }}</span></td>
              <td>
                <div v-if="classInfo">
                  <div v-for="(date, index) in classInfo" :key="index">
                    {{ checkDay(date.day_of_the_week) }} {{date.start_time_hour.toString().padStart(2, '0')}}:{{date.start_time_minute.toString().padStart(2, '0')}} ~ {{date.end_time_hour.toString().padStart(2, '0')}}:{{date.end_time_minute.toString().padStart(2, '0')}}
                  </div>
                </div>
              </td>
            </tr>
          </table>
        </div>
        <!-- 모달 -->
        <div
          class="modal fade"
          id="exampleModal2"
          tabindex="-1"
          aria-labelledby="exampleModalLabel2"
          aria-hidden="true"
        >
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header" @click="resetlist">
                <h4 class="modal-title" id="exampleModalLabel2">강사 변경</h4>
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div class="modal-body">
                <div class="overflow-auto">
                  <ul class="list-group">
                    <li class="list-group-item" v-for="teacher of teacher_list" :key="teacher.teacherSeq">
                      {{ teacher.teacherName }} <button type="button" class="btn registerBtn" @click="register(teacher.teacherSeq)">변경</button>
                    </li>
                  </ul>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn closeBtn" data-bs-dismiss="modal" @click="reload">닫기</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <hr class="mt-30" />
      </div>
    </div>
  </section>
</template>

<script>
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'
import axios from 'axios'
import {ref, onBeforeMount} from 'vue'

export default {
  name: "InstructorDetailsArea",
  setup() {
    const store = useStore()
    const route = useRoute()
    const router = useRouter()
    const class_id = {route}.route.params.classId
    const classInfo = ref('')
    const teacherInfo = ref('')
    const teacherName = ref('')

    const editState = ref(false)

    const teacher_list = ref([])

    const get_teacher = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/masters/academies/${store.state.academyId}/teachers`
      })
        .then((res) => {
          teacher_list.value = res.data.list
        })
    }

    const get_Class = () => {
      axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/teachers/classes/${class_id}`
      })
        .then((res) => {
            classInfo.value = res.data.teacherClassInfo
            teacherInfo.value = res.data.teacherDeatilInfo
        })
        .catch(() => {
            return
        })
    }

    function register(id) {
      axios({
        method: 'put',
        url: `${store.state.API_URL}/api/v1/masters/classes/${class_id}`,
        data: {
          teacherSeq: id
        },
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
        .then(() => {
          get_Class()
        })
    }

    function findTeacher(name) {
      for ( let i of teacher_list.value) {
        if (i.teacherName === name) {
          return true
        } 
      }
      return false
    }

    const reload = () => {
      window.location.reload()
      return
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

    const deleteClass = () => {
      if (confirm('정말 삭제하시겠습니까? 한 번 삭제하면 되돌릴 수 없습니다.')) {
        axios ({
          method: 'delete',
          url: `${store.state.API_URL}/api/v1/masters/classes/${store.state.classId}`
        })
        .then(() => {
          router.go(-1)
        })
      }
    }

    function editClass() {
      if (editState.value === false) {
        editState.value = true
      } else {
        editState.value = false
      }
    }

    onBeforeMount(() => {
      get_Class()
      get_teacher()
    })
    return {
      class_id,
      classInfo,
      teacherInfo,
      get_Class,
      checkGrade,
      checkSubject,
      deleteClass,
      checkDay,
      makeNum,
      editState,
      editClass,

      reload,
      register,


      teacherName,
      teacher_list,
      findTeacher
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
.closeBtn {
  border: 1px solid black;
}
.registerBtn {
  background: #ff8f21;
  color: white;
  float: right;
}
.insertStudent {
  cursor: pointer;
  width: 82px;
  height: 40px;
  background-color: #0e1133;
  color: white;
  border-radius: 3px;
  text-align: center;
  padding-top: 9px;

  position: relative;
  top: -55px;
  left: 800px
}
.aaa {
  margin-left: 20px;
}
</style>
