<template>
  <div class="tab-pane fade" id="testInfo" role="tabpanel" aria-labelledby="test-info">
    <div class="course__description">
      <div>
        <div class="row">
          <div class="col-3">
            <div class="dateTable">
              <DatePickerMonthArea @month-updated="updateMonth"/>
            </div>
          </div>
          <div class="col-9">
            <div class="dateTable">
              <div class="tableTestBox">
                <table class="table table-borderless scoreTable" align="center">
                  <thead>
                    <tr align="center">
                      <th>시험명</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      align="left"
                      v-for="test in testList"
                      :key="test"
                    >
                      <td class="aa">
                        <div class="row abb">
                          <div class="col-1">
                          </div>
                          <div class="col-8 test_info_name" @click="getDetailScore(test.test_info_id)">
                            {{ test.test_info_name }}
                          </div>
                          <div class="col" align="center">
                            <button class="test-detail-btn" data-bs-toggle="modal" data-bs-target="#exampleModal">보기</button>
                            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                              <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                  <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">상세보기</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                  </div>
                                  <div class="modal-body">
                                    <StudentTestDetail :test="test" :day="pickedDay"/>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <div class="tableBox">
                <table class="table table-borderless scoreTable" align="center">
                  <thead>
                    <tr align="center">
                      <th>점수</th>
                      <th>평균</th>
                      <th>석차</th>
                    </tr>
                  </thead>

                  <tbody>
                    <tr align="center" v-if="isclicked" >
                      <td class="aaa">{{ score.score }}점</td>
                      <td class="aaa">{{ score.avg }}점</td>
                      <td class="aaa">{{ score.rank }}/{{ score.totalCnt }}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DatePickerMonthArea from "../Calendar/DatePickerMonthArea.vue"
import StudentTestDetail from '@/components/Student/StudentTestDetail.vue'

import axios from "axios";
import { useStore } from "vuex";
import { onBeforeMount, ref } from "vue";

export default {
  name: "RegisterTestTab",
  components: {
    DatePickerMonthArea,
    StudentTestDetail
  },
  setup() {
    const todaydate = new Date()
    const store = useStore()
    const isclicked = ref(false)
    const selectedDay = ref('')
    const testList = ref('')
    const score = ref('')
    const temp = store.state.test
    const pickedDay = ref('')
    function changedate(todaydate) {
      const year = todaydate.getFullYear()
      let month = todaydate.getMonth() + 1
      if (month < 10) {
        month = '0' + month
      }
      return year + month
    }

    function updateMonth(date) {
      if (date.substr(4,2) === '12') {
        date = String(parseInt(date.substr(0,4)) + 1) + '01'
      } else {
        date = String(parseInt(date) + 1)
      }
      selectedDay.value = date
      getScoreList()
    }

    function getScoreList() {
      isclicked.value = false
      if (selectedDay.value) {
        axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/parents/classes/${store.state.classId}/tests/${selectedDay.value}`,
          header: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
        .then((res) => {
          testList.value = res.data.list.reverse()
          pickedDay.value = selectedDay.value
        })
        .catch((err) => {
          console.log(err)
        })
      } else {
        const dayData = changedate(todaydate)
        axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/parents/classes/${store.state.classId}/tests/${dayData}`,
          header: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
        .then((res) => {
          testList.value = res.data.list.reverse()
          pickedDay.value = dayData
        })
        .catch((err) => {
          console.log(err)
        })
      }
    }

    function getDetailScore(id) {
      isclicked.value = true
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/parents/students/${store.state.student_id}/tests/${id}`,
        header: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((res) => {
        score.value = res.data
      })
      .catch((err) => {
        console.log(err)
      })
    }
    const saveData = async (test) => {
      await store.dispatch('saveData', test)
    }
    onBeforeMount(() => {
      getScoreList()
    })

    return{
      updateMonth,
      getScoreList,
      getDetailScore,
      isclicked,
      selectedDay,
      testList,
      score,
      todaydate,
      changedate,
      pickedDay,
      saveData,
      temp
    }
  }
};
</script>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Josefin+Slab:100,300,400,600,700);

.tableBox {
  height: 270px;
  width: 50%;
  float: left;
  border: 1px solid rgba(128, 128, 128, 0.479);
}

.tableTestBox {
  height: 270px;
  width: 50%;
  float: left;
  overflow-y: scroll;
  border: 1px solid rgba(128, 128, 128, 0.479);
}
.tableTestBox::-webkit-scrollbar {
  width: 4px;
}
.tableTestBox::-webkit-scrollbar-thumb {
  background-color: #ffffff;
  border-radius: 10px;
  background-clip: padding-box;
  border: 2px solid transparent;
}
.tableTestBox::-webkit-scrollbar-track {
  background-color: rgb(204, 204, 204);
  border-radius: 10px;
}

.scoreTable th {
  position: sticky;
  top: 0px;
  background-color: #e5e5e5;
  color: rgb(46, 46, 46);
  border-right: 1px solid rgba(128, 128, 128, 0.479);
}

.aaa {
  border-right: 1px solid rgba(128, 128, 128, 0.479);
  height: 227px;
  font-size: 19px;
  vertical-align: middle;
}

.aa {
  border-right: 1px solid rgba(128, 128, 128, 0.479);
  border-bottom: 1px solid rgba(128, 128, 128, 0.479);
  height: 50px;
  font-size: 19px;
  vertical-align: middle;
  cursor: pointer;
}

.abb {
  width: 100%;
}

.detail-btn {
  background-color: rgba(128, 128, 128, 0.479);
  width: 40px;
  height: 30px;
  color: white;
}

.test_info_name {
  border-right: 1px solid rgba(128, 128, 128, 0.479);
  margin-top: 8px;
}

.test-detail-btn {
  background-color: #FF8F21;
  color: white;
  border-radius: 5px;
  border: 5px solid #FF8F21;
  margin-left: 20px;
  font-size: 15px;
}
</style>
