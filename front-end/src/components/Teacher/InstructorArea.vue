<template>
  <section class="teacher__area pt-150 pb-110">
    <div class="container">
      <div class="row">
        <div class="col-xxl-6 offset-xxl-3">
          <div class="section__title-wrapper text-center mb-60">
            <h2 class="section__title">{{ academy_info.academy_name }} 강사진<br /></h2>
          </div>
        </div>
      </div>
      <div class="row">
        <div
          v-for="teacher in teacher_list"
          :key="teacher"
          class="col-xxl-3 col-xl-3 col-lg-3 col-md-3"
        >
          <div class="teacher__item text-center grey-bg-5 transition-3 mb-30 a">
            <div class="teacher__thumb w-img fix">
              <router-link
                :to="{ name: 'teacher-details', params: { userSeq: teacher.teacherSeq } }"
              >
                <div class="aa">
                  <img v-if="teacher.profileImg" :src="teacher.profileImg" alt="">
                  <img v-else src="../../assets/img/noProfile.png" />
                </div>
              </router-link>
            </div>
            <div class="teacher__content">
              <h3 class="teacher__title">{{ teacher.teacherName }}</h3>
              <div style="color: black">{{ checkSubject(teacher.subjectCode) }}</div>
              <div style="color: black">
                {{ teacher.teacherPhoneNumber.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`) }}
              </div>

              <button @click="deleteTeacher(teacher.teacherSeq)" class="delete-teacher-btn">
                삭제
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios"
import { useStore } from "vuex"
import { onMounted, ref } from "vue"

export default {
  name: "ManageTeacher",
  setup() {
    const store = useStore()
    const academy_info = ref({})
    const teacher_list = ref({})

    // 학원 아이디로 강사 목록 가져오기
    const inq_academy = () => {
      axios({
        method: "get",
        url: `${store.state.API_URL}/api/v1/masters/${store.state.myseq}/academies`,
        headers: {
          Authorization: "Bearer " + store.state.token,
        },
      }).then((res) => {
        academy_info.value = res.data.academyInfo;
        axios({
          method: "get",
          url: `${store.state.API_URL}/api/v1/masters/academies/${academy_info.value.academy_id}/teachers`,
          headers: {
            Authorization: "Bearer " + store.state.token,
          },
        }).then((res) => {
          teacher_list.value = res.data.list
          console.log(teacher_list.value)
        })
      })
    }
    const checkSubject = code => subjectName.get(code)
    const subjectName = new Map ([ ["KO", "국어"],
    ["EN", "영어"],
    ["MA", "수학"],
    ["SI", "과학"],
    ["SO", "사회"],
    ["CS", "컴퓨터"],
    ["AL", "알고리즘"]])

    const deleteTeacher = (teacherSeq) => {
      if (confirm('정말 삭제하시겠습니까?')) {
        axios({
          method: "delete",
          url: `${store.state.API_URL}/api/v1/masters/teachers/${teacherSeq}`,
        }).then(() => {
          inq_academy();
        })
      }
    }

    onMounted(() => {
      inq_academy();
    });

    return {
      checkSubject,
      deleteTeacher,
      teacher_list,
      academy_info
    };
  },
};
</script>

<style scoped>
.delete-teacher-btn {
  cursor: pointer;
  width: 60px;
  height: 40px;
  background-color: #0e1133;
  color: white;
  border-radius: 3px;
  text-align: center;
  margin-top: 10px;
}

.a {
  height: 450px;
}

.aa {
  height: 200px;
}
</style>