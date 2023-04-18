<template>
  <div class="test_container">
    <h3 class="test_title">
      <span v-if="classInfo">{{ classInfo.class_name }} </span><span> 성적 분석</span>
    </h3>
    <hr />
    <div class="container">
      <div class="row">
        <div class="col">
          <div class="d-flex justify-content-between">
            <h3>{{ testInfo.test_info_name }}</h3>
            <h4>시행 : {{ pickedDay }}</h4>
          </div>
          <table>
            <thead>
              <tr>
                <th>점수</th>
                <th>평균</th>
                <th>석차</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{{ testData.score }}점</td>
                <td>{{ testData.average }}점</td>
                <td>{{ testData.rank }}/{{ testData.totalCnt }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="col">
          <canvas ref="myChartCanvas" width="400"></canvas>
        </div>
      </div>
      <hr />
      <div class="row">
        <div class="col">
          <h3>최근 성적 추이</h3>
          <table>
            <thead>
              <tr>
                <th>시험명</th>
                <th>일시</th>
                <th>점수</th>
                <th>평균</th>
                <th>석차</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="data of recentTestData" :key="data">
                <td>{{ data.testName }}</td>
                <td>{{ data.testDate }}</td>
                <td>{{ data.score }}점</td>
                <td>{{ data.average }}점</td>
                <td>{{ data.rank }}/{{ data.totalCnt }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="col" id="line-chart">
          <canvas ref="myChartCanvas2" width="400"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { ref, onBeforeMount } from 'vue'
import { useStore } from 'vuex'
import axios from 'axios'
import Chart from 'chart.js/auto';
export default {
  name: 'StudentTestDetail',
  props: {
    test: Object,
    day: String
  },
  setup (props) {
    const store = useStore()

    const classInfo = ref('')
    const testInfo = ref(props.test)
    const pickedDay = ref(props.day)

    const testData = ref({})
    const recentTestData = ref([])

    const myChartCanvas = ref(null)
    const myChartCanvas2 = ref(null)
    
    const getClassData = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}`
      })
        .then((res) => {
          classInfo.value = res.data.teacherClassInfo[0]
        })
    }

    const getTestData = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/parents/classes/${store.state.classId}/students/${store.state.student_id}/testInfos/${testInfo.value.test_info_id}`
      })
        .then((res) => {
          testData.value = res.data.retInfo.myTestInfo
          recentTestData.value = res.data.retInfo.myTestTrend
          getChart()
        })
    }

    onBeforeMount(() => {
      getClassData()
      getTestData()
    })

    const getChart = () => {
      const ctx = myChartCanvas.value.getContext('2d');
      const ctx2 = myChartCanvas2.value.getContext('2d');
      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: ['내 자녀', '평균'],
          datasets: [
            {
              label: '점수',
              data: [testData.value.score , testData.value.average],
              backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
              ],
              borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
              ],
              borderWidth: 1,
            },
          ],
        },
        options: {
          maintainAspectRatio: true,
        },
      })
    const labels = recentTestData.value.slice(0, 5).map(data => data.testName).reverse()
    const scores = recentTestData.value.slice(0, 5).map(data => data.score).reverse();
    const avgscores = recentTestData.value.slice(0, 5).map(data => data.average).reverse()
    new Chart(ctx2, {
      type: 'line',
      data: {
        labels: labels,
        datasets: [
          {
            label: '내 자녀',
            data: scores,
            fill: false,
            borderColor: 'rgba(255,99,132,1)',
            tension: 0.1
          },
          {
            label: '평균',
            data: avgscores,
            fill: false,
            borderColor: 'rgba(54, 162, 235, 1)',
            tension: 0.1
          }
        ]
      },
      options: {
        scales: {
          y: {
            min: 0,
            max: 100,
            ticks: {
              stepSize: 10
            }
          }
        }
      },
      })
    }

    return {
      classInfo,
      testInfo,
      testData,
      recentTestData,
      pickedDay,
      
      myChartCanvas,
      myChartCanvas2,

      getClassData,
      getTestData,
    }
  },
}
</script>

<style scoped>
.test_container {
  margin-top: 30px;
  margin-left: 30px;
  margin-right: 30px;
  margin-bottom: 30px;
  padding-top: 30px;
  padding-left: 30px;
  padding-right: 30px;
  padding-bottom: 30px;
  border: 1px solid #eeeeee;
  border-radius: 5px;
  background-color: #eeeeee;
}
.test_title {
  font-size: 30px;
  font: bold;
  margin-top: 20px;
  margin-bottom: 40px;
}
#test_date_info {
  float: right;
}
.test_title:first-child {
  padding: 0px 5px 0px;
  border-bottom: 0px solid #000;
  border-left: 10px solid #ff8f21;
  color: rgb(0, 0, 0);
}

table {
  border: 1px #a39485 solid;
  font-size: 0.9em;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  width: 400px;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
  margin-top: 25px;
}

th {
  text-align: center;
}

td {
  text-align: center;
}

thead {
  font-weight: bold;
  color: #fff;
  background: #73685d;
}

td,
th {
  padding: 1em 0.5em;
  vertical-align: middle;
}

td {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  background: #fff;
}

#test_bar {
  width: 500px;
}

</style>