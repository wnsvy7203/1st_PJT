<template>
  <div class="tab-pane fade" id="registerTest" role="tabpanel" aria-labelledby="register-test-tab">
    <div class="course__description row">
      <div class="dateTable col-3">
        <DatePickerArea @date-updated="updateDate" />
      </div>
      <div class="dateTable col-9">
        <div class="noScrollTableBox">
          <table class="table table-borderless ScoreTable" align="center">
            <thead>
              <tr align="center">
                <th>시험명</th>
              </tr>
            </thead>

            <tbody>
              <td>
                <input
                  type="text"
                  id="testInput"
                  placeholder="시험명을 입력하세요."
                  v-model="test"
                />
              </td>
            </tbody>
          </table>
        </div>

        <div class="tableBox">
          <table class="table table-borderless scoreTable" align="center">
            <thead>
              <tr align="center">
                <th>이름</th>
                <th>점수</th>
              </tr>
            </thead>

            <tbody>
              <tr align="center" v-for="student in student_list" :key="student">
                <td>{{ student.name }}</td>
                <td><input type="number" v-model="student.score" min="0" max="100" @blur="score_validate(student)"/></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <button class="register" @click="sendData()">등록</button>
  </div>
</template>

<script>
import DatePickerArea from "../Calendar/DatePickerArea.vue";

import axios from "axios";
import { ref, onBeforeMount } from "vue";
import { useStore } from "vuex";

export default {
  name: "RegisterTestTab",
  components: {
    DatePickerArea,
  },
  setup() {
    const store = useStore();
    const student_list = ref([])
    const test = ref("")
    const studentData = ref([])
    const scoreData = ref([])
    const currentDate =  ref(new Date().toISOString().substring(0, 10).replace(/-/g, ''))

    const get_student = () => {
      student_list.value = []
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
            student_list.value.push({
              id: response.data.studentInfo.student_id,
              name: response.data.studentInfo.student_name,
              score: null
            })
          })
          .finally(() => {
            student_list.value.sort((a, b) => {
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

    function score_validate(student) {
      const score = student.score
      if (isNaN(Number(score))) {
        alert('점수는 숫자로만 입력 가능합니다.')
        student.score = null
      } else if (Number(score) > 100 || Number(score) < 0) {
        alert('점수는 0에서 100까지의 숫자로만 입력 가능합니다.')
        student.score = null
      }
    }

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
      currentDate.value = date
    }

    function sendData() {
      studentData.value = []
      scoreData.value = []

      for (let i of student_list.value) {
        if (i.score === null) {
          continue
        } else if (isNaN(i.score)){
          continue
        } else if (!i.score) {
          continue
        } else {
          studentData.value.push(i.id)
          scoreData.value.push(i.score)
        }
      }

      if (!test.value) {
        alert('시험명을 입력하세요.')
      } else if (!studentData.value.length) {
        alert('학생과 점수를 등록해주세요.')
      } else {
        axios({
          method: "post",
          url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}/tests`,
          data: {
            testDate: currentDate.value,
            testName: test.value,
            studentId: studentData.value,
            studentScore: scoreData.value,
          },
          headers: {
            Authorization: `Bearer ${store.state.token}`,
          },
        })
          .then(() => {
            alert("성적 등록이 완료되었습니다");
            window.location.reload()
          })
      }
    }

    onBeforeMount(() => {
      get_student()
    })

    return {
      get_student,
      test,
      student_list,
      sendData,
      updateDate,
      studentData,
      scoreData,
      currentDate,

      score_validate
    }
  },
}
</script>

<style scoped>
#testInput {
  height: 200px;
  width: 100%;
  border: none;
}

::placeholder {
  color: grey;
}

.table {
  float: left;
  border-collapse: collapse;
}

.noScrollTableBox {
  float: left;
  border-collapse: collapse;
  height: 250px;
  width: 50%;
  float: left;
  border-right: 1px solid #E5E5E5;
  border-left: 1px solid #E5E5E5;
  border-bottom: 1px solid #E5E5E5;
}

.tableBox {
  height: 250px;
  width: 50%;
  float: left;
  overflow-y: scroll;
  border-bottom: 1px solid #E5E5E5;
}
.tableBox::-webkit-scrollbar {
  width: 10px;
}
.tableBox::-webkit-scrollbar-thumb {
  background-color: #ffffff;
  border-radius: 10px;
  background-clip: padding-box;
  border: 2px solid transparent;
}
.tableBox::-webkit-scrollbar-track {
  background-color: rgb(204, 204, 204);
  border-radius: 10px;
}

.scoreTable th {
  position: sticky;
  top: 0px;
  background-color: #e5e5e5;
  color: rgb(46, 46, 46);
}
.scoreTable td {
  border-bottom: 1px solid rgb(192, 192, 192);
}

.noScrollTableBox table th {
  background-color: #e5e5e5;
  color: rgb(46, 46, 46);
  border-right: 1px solid white;
}

.register {
  width: 55px;
  height: 35px;
  float: right;
  margin-bottom: 15px;
  border-radius: 8%;
  background-color: #0e1133;
  color: white;
  margin-top: 10px;
}
</style>
