<template>
  <div class="tab-pane fade" id="testInfo" role="tabpanel" aria-labelledby="test-info">
    <div class="course__description row">
      <div class="dateTable col-3">
        <DatePickerMonthArea @month-updated="updateMonth" />
      </div>
      <div class="dateTable col-9">
        <div class="tableBox">
          <table class="table table-borderless scoreTable" align="center">
            <thead>
              <tr align="center">
                <th>시험명</th>
              </tr>
            </thead>

            <tbody>
              <tr
                align="center"
                v-for="test in testData"
                :key="test"
                @click="getScore(test.test_info_id)"
                class="test_name"
              >
                <td>{{ test.test_info_name }}</td>
              </tr>
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
              <tr align="center" v-for="student in studentScore" :key="student">
                <td>{{ student.student_name }}</td>
                <td>{{ student.score }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DatePickerMonthArea from "../Calendar/DatePickerMonthArea.vue";

import axios from "axios";
import { onBeforeMount, ref } from "vue";
import { useStore } from "vuex";

export default {
  name: "RegisterTestTab",
  components: {
    DatePickerMonthArea,
  },
  setup() {
    const store = useStore();
    const testData = ref([]);
    const studentScore = ref([]);
    const currentMonth = ref(0);
    const todaydate = new Date()

    function changedate(todaydate) {
      const year = todaydate.getFullYear()
      let month = todaydate.getMonth() + 1
      if (month < 10) {
        month = '0' + month
      }
      return year + month
    }


    function updateMonth(month) {
      if (month.substr(4,2) === '12') {
        month = String(parseInt(month.substr(0,4)) + 1) + '01'
      } else {
        month = String(parseInt(month) + 1)
      }
      currentMonth.value = month
      getScoreList()
    }
    function getScoreList() {
      if (currentMonth.value) {
        axios({
          method: "get",
          url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}/tests/${currentMonth.value}`,
          headers: {
            Authorization: `Bearer ${store.state.token}`,
          },
        }).then((res) => {
          testData.value = res.data.list;
        });
      } else {
        axios({
          method: "get",
          url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}/tests/${changedate(todaydate)}`,
          headers: {
            Authorization: `Bearer ${store.state.token}`,
          },
        })
        .then((res) => {
          testData.value = res.data.list;
        })
      }
    }
    function getScore(id) {
      axios({
        method: "get",
        url: `${store.state.API_URL}/api/v1/teachers/tests/${id}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`,
        },
      }).then((res) => {
        studentScore.value = res.data.list;
      })
      .finally(() => {
        studentScore.value.sort((a, b) => {
          if (a.student_name < b.student_name) {
            return -1
          }
          if (a.student_name > b.student_name) {
            return 1
          }
          return 0
        })
      })
    }
    onBeforeMount(() => {
      getScoreList()
    })
    return {
      getScore,
      testData,
      studentScore,
      updateMonth,
      currentMonth,
      todaydate,
      changedate,
    };
  },
};
</script>

<style scoped>
.table {
  float: left;
  border-collapse: collapse;
}

.tableBox {
  height: 250px;
  width: 50%;
  float: left;
  overflow-y: scroll;
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

.test_name:hover { 
  background-color: #FF8F21;
  color: #ffffff;
}
</style>
