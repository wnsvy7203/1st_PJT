<template>
<section class="teacher__area pt-115 pb-110">
  <div class="container">
    <div class="row">
      <div class="col-xxl-6 offset-xxl-3">
          <div class="section__title-wrapper text-center mb-60">
            <h2 class="section__title">{{ academy_info.academy_name }} 학원생<br>
            </h2>
          </div>
      </div>
    </div>
    <div class="row">
      <div v-for="student in student_list" :key="student.id" class="col-xxl-3 col-xl-3 col-lg-3 col-md-6">
        <div class="teacher__item text-center grey-bg-5 transition-3 mb-30">
          <div class="teacher__thumb w-img fix">
            <router-link :to="{ name: 'StudentAcademyList', params: {academy_id: academy_info.academy_id , student_id: student.student_id} }" @click="saveInfo(student.student_id, academy_info.academy_name)">
              <img src="../../assets/img/noProfile.png">
            </router-link>
          </div>
          <div class="teacher__content">
            <h3 class="teacher__title">{{student.student_name}}</h3> 
            <span> &nbsp;&nbsp;{{ student.student_age }}</span>
            <p>{{ student.student_school }}</p>
            <p>{{ makeNum(student.student_phone_number) }}</p>
            <button @click="deleteStudent(student.student_id)" class="delete-teacher-btn">삭제</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { onMounted, ref } from 'vue'

export default {
  name:'StudentArea',
  setup() {
    const store = useStore()
    const academy_info = ref({})
    const student_list = ref({})
    const student_id = ref('')

    // 학원 아이디로 학생 목록 받아옴
    const inq_academy = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/masters/${store.state.myseq}/academies`,
        headers: {
          Authorization: 'Bearer ' + store.state.token
        }
      })
      .then((res) => {
        academy_info.value = res.data.academyInfo
        console.log(academy_info.value)
          axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/masters/academies/${academy_info.value.academy_id}/students`,
          headers: {
            Authorization: 'Bearer ' + store.state.token
          }
          })
        .then((res) => {
          student_list.value = res.data.list
        })
      })
    }

    function deleteStudent(id) {
      if (confirm('정말 삭제하시겠습니까?')) {
        axios ({
          method: 'delete',
          url: `${store.state.API_URL}/api/v1/masters/students/${id}`
        })
        .then( () => {
          inq_academy()
        })
      }
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

    const saveInfo = (id, name) => {
      store.dispatch('saveStudentid', id)
      store.dispatch('saveAcademyname', name)
    }
    onMounted(() => {
      inq_academy()
    })

    return {
      deleteStudent,
      student_list,
      makeNum,
      academy_info,
      saveInfo,
      student_id,
    }
  }
}
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
}
.aa{
  margin: 10px;
}
.aaa {
  margin: 10px;
}
.closeBtn {
  border: 1px solid
}
</style>
