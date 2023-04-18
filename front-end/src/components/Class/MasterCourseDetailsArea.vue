<template>
  <section class="page__title-area pb-90">
    <div class="container">
      <div class="row">
        <div>
          <div class="course__wrapper">
            <div class="course__tab-2 mb-45">
              <ul class="nav nav-tabs" id="courseTab" role="tablist">
                <li class="nav-item" role="presentation">
                  <button
                    class="nav-link active"
                    id="description-tab"
                    data-bs-toggle="tab"
                    data-bs-target="#description"
                    type="button"
                    role="tab"
                    aria-controls="description"
                    aria-selected="true"
                  >
                    <i class="icon_ribbon_alt"></i> <span>출결</span>
                  </button>
                </li>
                <li class="nav-item" role="presentation">
                  <button
                    class="nav-link"
                    id="member-tab"
                    data-bs-toggle="tab"
                    data-bs-target="#member"
                    type="button"
                    role="tab"
                    aria-controls="member"
                    aria-selected="false"
                  >
                    <i class="icon_book_alt"></i> <span>원생 목록</span>
                  </button>
                </li>
                <li class="nav-item" role="presentation">
                  <button
                    class="nav-link"
                    id="curriculum-tab"
                    data-bs-toggle="tab"
                    data-bs-target="#curriculum"
                    type="button"
                    role="tab"
                    aria-controls="curriculum"
                    aria-selected="false"
                  >
                    <i class="icon_star_alt"></i> <span>진도</span>
                  </button>
                </li>
                <li class="nav-item" role="presentation">
                  <button
                    class="nav-link"
                    id="register-test-tab"
                    data-bs-toggle="tab"
                    data-bs-target="#registerTest"
                    type="button"
                    role="tab"
                    aria-controls="register-test-tab"
                    aria-selected="false"
                  >
                    <span>성적 등록</span>
                  </button>
                </li>
                <li class="nav-item" role="presentation">
                  <button
                    class="nav-link"
                    id="test-info"
                    data-bs-toggle="tab"
                    data-bs-target="#testInfo"
                    type="button"
                    role="tab"
                    aria-controls="test-info"
                    aria-selected="false"
                  >
                    <span>성적 조회</span>
                  </button>
                </li>
              </ul>
            </div>

            <!-- 출결 탭 시작 -->
            <div class="course__tab-content mb-95">
              <div class="tab-content" id="courseTabContent">
                <div
                  class="tab-pane fade show active"
                  id="description"
                  role="tabpanel"
                  aria-labelledby="description-tab"
                >
                  <div class="course__description">
                    <div class="d-flex flex-row">
                      <div class="dateTable">
                        <DatePickerArea @date-updated="updateDate"/>
                      </div>
                      <div class="dateTable">
                        <table class="table table-bordered aa" align="center">
                          <thead>
                            <tr align="center">
                              <th width="185px">이름</th>
                              <th width="700px">출결사항</th>
                            </tr>
                          </thead>

                          <tbody>
                            <tr 
                              align="center"
                              v-for="(student, idx) in studentAttendancy"
                              :key="idx"
                            >
                              <td>{{ student.name }}</td>
                              <td>
                                <div>
                                  <div class="form-check form-check-inline attendancyCheck">
                                    <input
                                      class="form-check-input"
                                      type="radio"
                                      :name="`flexRadioDefault${student.id}`"
                                      id="flexRadioDefault1"
                                      value="AA"
                                      v-model="pick[idx]"
                                      @change="handleChange(student.id, pick[idx])"
                                      :checked="checkbtn(student.id, 'AA')"
                                    />
                                    <label class="form-check-label" for="flexRadioDefault1">
                                      출석
                                    </label>
                                  </div>
                                  <div class="form-check form-check-inline attendancyCheck">
                                    <input
                                      class="form-check-input"
                                      type="radio"
                                      :name="`flexRadioDefault${student.id}`"
                                      id="flexRadioDefault2"
                                      value="AB"
                                      v-model="pick[idx]"
                                      @change="handleChange(student.id, pick[idx])"
                                      :checked="checkbtn(student.id, 'AB')"
                                    />
                                    <label class="form-check-label" for="flexRadioDefault2">
                                      결석
                                    </label>
                                  </div>
                                  <div class="form-check form-check-inline attendancyCheck">
                                    <input
                                      class="form-check-input"
                                      type="radio"
                                      :name="`flexRadioDefault${student.id}`"
                                      id="flexRadioDefault3"
                                      value="AT"
                                      v-model="pick[idx]"
                                      @change="handleChange(student.id, pick[idx])"
                                      :checked="checkbtn(student.id, 'AT')"
                                    />
                                    <label class="form-check-label" for="flexRadioDefault3">
                                      지각
                                    </label>
                                  </div>
                                  <div class="form-check form-check-inline attendancyCheck">
                                    <input
                                      class="form-check-input"
                                      type="radio"
                                      :name="`flexRadioDefault${student.id}`"
                                      id="flexRadioDefault4"
                                      value="AE"
                                      v-model="pick[idx]"
                                      @change="handleChange(student.id, pick[idx])"
                                      :checked="checkbtn(student.id, 'AE')"
                                    />
                                    <label class="form-check-label" for="flexRadioDefault4">
                                      조퇴
                                    </label>
                                  </div>
                                  <div class="form-check form-check-inline attendancyCheck">
                                    <input
                                      class="form-check-input"
                                      type="radio"
                                      :name="`flexRadioDefault${student.id}`"
                                      id="flexRadioDefault5"
                                      value="AO"
                                      v-model="pick[idx]"
                                      @change="handleChange(student.id, pick[idx])"
                                      :checked="checkbtn(student.id, 'AO')"
                                    />
                                    <label class="form-check-label" for="flexRadioDefault5">
                                      외출
                                    </label>
                                  </div>
                                </div>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                    <button class="register" @click=addStudentData>수정</button>
                  </div>
                </div>

                <!-- RegisterTestInfoArea start -->
                <RegisterTestInfoArea />
                <!-- RegisterTestInfoArea end -->

                <!-- CurriculumTab start -->
                <ClassProgressArea />
                <!-- CurriculumTab end -->

                <!-- MemberTab start -->
                <StudentArea />
                <!-- MemberTab end -->

                <!-- TestInfoListArea start -->
                <TestInfoListArea />
                <!-- TestInfoListArea end -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import ClassProgressArea from "./ClassProgressArea.vue";
import RegisterTestInfoArea from "./RegisterTestInfoArea.vue";
import TestInfoListArea from "./TestInfoListArea.vue";
import StudentArea from "./StudentArea.vue";
import DatePickerArea from "../Calendar/DatePickerArea.vue";
import {useRoute } from 'vue-router'
import axios from "axios";
import { useStore } from "vuex";
import { ref, onBeforeMount } from "vue";

export default {
  name: "CourseDetailsArea",
  components: {
    ClassProgressArea,
    RegisterTestInfoArea,
    TestInfoListArea,
    StudentArea,
    DatePickerArea,
  },
  setup() {
    const store = useStore();
    const students = ref('')
    const studentAttendancy = ref([]);
    const route = useRoute()
    const classid = {route}.route.params.classId
    const currentDate = ref(new Date().toISOString().substring(0, 10).replace(/-/g, ''))

    const get_student = () => {
      store.dispatch('saveClassid', classid)
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/classes/${classid}/students`,
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
            studentAttendancy.value.push({
              id: response.data.studentInfo.student_id,
              name: response.data.studentInfo.student_name,
              attendance: ''
            })
          })
          .finally(() => {
            studentAttendancy.value.sort((a, b) => {
              if (a.name < b.name) {
                return -1
              }
              if (a.name > b.name) {
                return 1
              }
              return 0
            })
          })
        }
      })
    }

    const state = ref(false)

    const getStudent = ref([])
    const pick = ref([])


    function updateDate(date) {
      if (date.length < 8) {
        date = date.substr(0,6) + '0' + date.substr(6,1)
      }
      if (date.substr(4,2) === '01' && date.substr(6,2) === '32') {
        date = date.substr(0,4) + '0' + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '03' && date.substr(6,2) === '32') {
        date = date.substr(0,4) + '0' + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '05' && date.substr(6,2) === '32') {
        date = date.substr(0,4) + '0' + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '07' && date.substr(6,2) === '32') {
        date = date.substr(0,4) + '0' + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '08' && date.substr(6,2) === '32') {
        date = date.substr(0,4) + '0' + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '10' && date.substr(6,2) === '32') {
        date = date.substr(0,4) + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '02' && parseInt(date.substr(0,4)) % 4 === 0 && date.substr(6,2)=== '30' ) {
        date = date.substr(0,4) + '0301'
      } else if (date.substr(4,2) === '02' && parseInt(date.substr(0,4)) % 4 !== 0 && date.substr(6,2)=== '29' ) {
        date = date.substr(0,4) + '0301'
      } else if (date.substr(4,2) === '04' && date.substr(6,2) === '31') {
        date = date.substr(0,4) + '0' + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '06' && date.substr(6,2) === '31' ) {
        date = date.substr(0,4) + '0' + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '09' && date.substr(6,2) === '31') {
        date = date.substr(0,4) + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '11' && date.substr(6,2) === '31') {
        date = date.substr(0,4) + String(parseInt(date.substr(4,2)) + 1) + '01'
      } else if (date.substr(4,2) === '12' && date.substr(6,2) === '32') {
        date = String(parseInt(date.substr(0,4)) + 1) + '0101'
      }
      currentDate.value = date;
      const radioButtons = document.getElementsByClassName("form-check-input")
      for (var i = 0; i < radioButtons.length; i++) {
        radioButtons[i].checked = false;
      }
      attendancy.value = []
      getattendancy()
    }

    function getStudentData() {
      axios({
        method: "post",
        url: `${store.state.API_URL}/api/v1/teachers/attendancies/classes/${classid}/dates`,
        data: {
          "attendancyDate": currentDate.value
        },
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
        .then((res) => {
          getStudent.value = [];

          for (let i of res.data.list) {
            const info = ref({})
            info.value[i.id] = i.attendancyCode
            getStudent.value.push(info);
          }
          state.value = true
        })
        .catch(() => {
          state.value = false
        })
    }

    function handleChange(id, value) {
      for (let one of studentAttendancy.value) {
        if (one.id === id) {
          one.attendance = value;
        }
      }
    }

    const addStudent = ref([])
    const addAttendance = ref([])

    function addStudentData() {
      for (let one of studentAttendancy.value) {
        if (one.attendance) {
          addStudent.value.push(one.id)
          addAttendance.value.push(one.attendance)
        }
      }
      if (state.value === false) {
        axios({
          method: "post",
          url: `${store.state.API_URL}/api/v1/teachers/attendancies/classes/${classid}`,
          data: {
            "attendancyDate": currentDate.value,
            "idList": addStudent.value,
            "codeList": addAttendance.value
          },
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
          .then(() => {
            alert('등록 완료')
            getStudentData()
          })
      } else {
        axios({
          method: 'put',
          url: `${store.state.API_URL}/api/v1/teachers/attendancies/classes/${store.state.classId}`,
          data: {
            "attendancyDate": currentDate.value,
            "idList": addStudent.value,
            "codeList": addAttendance.value
          },
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
          .then(() => {
            alert('수정 완료')
            getStudentData()
          })
      }
    }

    const attendancy = ref([])

    function getattendancy() {
      for (let item of studentAttendancy.value) {
        axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/parents/classes/${classid}/attendancies/students/${item.id}`
        })
        .then((res) => {
          for (let attend_list of res.data.list) {
            if (currentDate.value === attend_list.attendancyDate && !(item.id in attendancy.value)) {
              attendancy.value.push({
                id: item.id,
                code: attend_list.attendancyCode
              })
            }
          }      
        })
      }
      
    }

    function checkbtn(id, code) {
      for (let item of attendancy.value) {
        if (item.id === id && code === item.code) {
          return true
        }
      }
    }

    onBeforeMount(() => {
      get_student()
    });

    return {
      currentDate,
      handleChange,

      students,
      studentAttendancy,

      updateDate,
      get_student,
      getStudentData,
      addStudentData,
      pick,

      getattendancy,
      attendancy,
      checkbtn
    };
  },
};
</script>

<style scoped>
.register {
  width: 55px;
  height: 35px;
  float: right;
  margin-bottom: 15px;
  border-radius: 8%;
  background-color: #0e1133;
  color: white;
}
.attendancyCheck {
  width: 13%;
}
.tableScroll {
  overflow-y: scroll;
}
.dateTable {
  /* float: left; */
  display: inline;
}
.aa {
  margin: 20px;
}
</style>
