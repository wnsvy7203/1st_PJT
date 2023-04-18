<template>
  <div class="tab-pane fade" id="member" role="tabpanel" aria-labelledby="member-tab">
    <div class="course__member mb-45" style="position: relative">
      <div class="course__member-item mt-30" v-for="student in student_list" :key="student">
        <div class="row align-items-center">
          <div class="col-xxl-3 col-xl-3 col-lg-3 col-md-3 col-sm-3">
            <div class="course__member-thumb d-flex align-items-center">
              <img src="../../assets/img/course/instructor/course-instructor-1.jpg" alt="" />
              <div class="course__member-name ml-20">
                <h5>{{ student.student_name }}</h5>
              </div>
            </div>
          </div>
          <div class="col-xxl-4 col-xl-4 col-lg-4 col-md-4 col-sm-4 col-5">
            <div class="course__member-info pl-40">
              <h5 v-if="student">{{ makeNum(student.student_phone_number) }}</h5>
            </div>
          </div>
          <div class="col-xxl-2 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-5">
            <div class="course__member-info pl-10">
              <h5>{{ getGrade(student.grade_code) }}</h5>
            </div>
          </div>
          <div class="col-xxl-2 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-5">
            <div class="course__member-info pl-10">
              <router-link :to="{ name: 'ParentClass', params: { class_id: classId } }" @click="saveStudentId(student.student_id)"><h5>상세보기</h5></router-link>
            </div>
          </div>
          <div class="col-xxl-1 col-xl-1 col-lg-1 col-md-1 col-sm-1 col-1">
            <div class="course__member-info pl-10" @click="deleteStudent(student.student_id)"> 
              <button v-if="userCode === 'UM'">삭제</button>
            </div>
          </div>
        </div>
      </div>
      <div class="insertStudent" data-bs-toggle="modal" data-bs-target="#exampleModal" v-if="userCode === 'UM'">
        원생 등록
      </div>
      <!-- Modal -->
      <div
        class="modal fade"
        id="exampleModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
        @hidden="reload"
      >
        <div class="modal-dialog modal-dialog-centered" v-if="userCode === 'UM'">
          <div class="modal-content">
            <div class="modal-header" @click="resetlist">
              <h4 class="modal-title" id="exampleModalLabel">원생 등록</h4>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <div class="selectStudent">
                <form @submit.prevent="searchName(studentName)">
                  <input
                    class="form-control me-2"
                    type="search"
                    placeholder="원생 이름을 입력하세요. "
                    aria-label="Search"
                    v-model = studentName
                  />
                </form>
              </div>
              <div class="searchList mt-20" v-if="search_list.length === 0" >
                <ul class="list-group">
                  <li class="list-group-item" v-for="student1 of master_list" :key="student1">
                    {{ student1.student_name }}
                    <button type="button" class="btn registerBtn" v-if="!isIn(student1.student_id)" @click="register(student1.student_id)">등록</button>
                  </li>
                </ul>
              </div>
              <div v-if="search_list">
                <ul class="list-group">
                  <li class="list-group-item" v-for="student1 of search_list" :key="student1">
                    {{ student1.student_name }}
                    <button type="button" class="btn registerBtn" v-if="!isIn(student1.student_id)" @click="register(student1.student_id)">등록</button>
                  </li>
                </ul>
              </div>
            <div class="modal-footer">
              <button type="button" class="btn closeBtn" data-bs-dismiss="modal" @click="reload">닫기</button>
            </div>
          </div>
        </div>
      </div>
      <!-- 원생 목록 -->
    </div>
  </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import axios from 'axios'
import { ref, onBeforeMount,} from 'vue'
export default {
  name: "MemberTab",
  setup() {
    const store = useStore();
    const student_list = ref([])
    const userCode = store.state.profile.userCode
    const classId = store.state.classId
    const master_list = ref('')
    const studentName = ref('')
    const search_list = ref([])
    const new_list = ref('')
    function checkstudent() {
      for ( let i of student_list.value) {
        for ( let j of master_list.value) {
          if (i.student_id === j.student_id ) {
            master_list.value.splice(master_list.value.indexOf(j), 1)
          }
        }
      }
    }
    function getAllStuedent() {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/masters/academies/${store.state.academyId}/students`
      })
      .then((res) => {
        master_list.value = res.data.list
      })
    }

    function get_student() {
      student_list.value = []
      if (store.state.profile.userCode === 'UM') {
        getAllStuedent()
      }
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}/students`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((res) => {
        for (let id of res.data.list) {
          axios({
            method: 'get',
            url: `${store.state.API_URL}/api/v1/teachers/students/${id.student_id}`,
            headers: {
              Authorization: `Bearer ${store.state.token}`
            }
          })
          .then((response) => {
            student_list.value.push(response.data.studentInfo)
            checkstudent()
          })
        }
      })
    }

    const isIn = (id) => {
      for ( let i of student_list.value) {
        if (i.student_id === id) {
          return true
        } 
      }
      return false
    }

    const searchName = (word) => {
      search_list.value = [];
      if (word.length < 1) {
        return;
      }
      for (let i of master_list.value) {
        if (i.student_name.replace(/\s/g, "").indexOf(word.replace(/\s/g, "")) !== -1) {
          search_list.value.push(i);
        }
        
      }
    }

    
    const resetlist = () => {
      search_list.value = []
      return
    }
    
    const register = (id) => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}/students/${id}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then(() => {
        alert('등록되었습니다')
        get_student()
      })
    }

    const reload = () => {
      window.location.reload()
      return
    }

    const getGrade = (grade_code) => {
      if (grade_code==='E1') {
        return '초1'
      } else if (grade_code==='E2') {
        return '초2'
      } else if (grade_code==='E3') {
        return '초3'
      } else if (grade_code==='E4') {
        return '초4'
      } else if (grade_code==='E5') {
        return '초5'
      } else if (grade_code==='E6') {
        return '초6'
      } else if (grade_code==='M1') {
        return '중1'
      } else if (grade_code==='M2') {
        return '중2'
      } else if (grade_code==='M3') {
        return '중3'
      } else if (grade_code==='H1') {
        return '고1'
      } else if (grade_code==='H2') {
        return '고2'
      } else if (grade_code==='H3') {
        return '고3'
      }
    }

    const saveStudentId = (id) => {
      store.dispatch("saveStudentid", id)
    }

    function deleteStudent(id) {
      axios({
        method: 'delete',
        url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}/students/${id}/delete`
      })
      .then(() => {
        get_student()
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
      get_student()
    })


    return {
      get_student,
      isIn,
      searchName,
      resetlist,
      register,
      reload,
      getGrade,
      saveStudentId,
      deleteStudent,
      checkstudent,
      getAllStuedent,
      student_list,
      studentName,
      userCode,
      master_list,
      search_list,
      classId,
      new_list,
      makeNum
    };
  },
};
</script>

<style scoped>
.insertStudent {
  cursor: pointer;
  width: 82px;
  height: 40px;
  background-color: #0e1133;
  color: white;
  border-radius: 3px;
  text-align: center;
  padding-top: 9px;

  position: absolute;
  right: 0;
}

.closeBtn {
  border: 1px solid black;
}

.registerBtn {
  background: #ff8f21;
  color: white;
  float: right;
}
</style>