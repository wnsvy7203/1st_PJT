<template>
  <section class="teacher__area pt-100 pb-110" :style="{ marginTop: isSticky ? '0' : '90px' }">
    <div class="container">
      <div class="row mt-30">
        <div class="d-flex">
          <div class="teacher__details-thumb p-relative w-img pr-30 col-xxl-4 col-xl-4 col-lg-4 col-md-6">
            <img v-if="profileImg" :src="profileImg" style="width: 100%">
            <img v-else src="../../assets/img/noProfile.png" alt="" style="width: 100%">
          </div>
          <div class="teacher__wrapper col-xxl-8 col-xl-8 col-lg-8 col-md-6">
            <div class="teacher__top d-md-flex align-items-end justify-content-between">
              <div class="teacher__info">
                <h4>{{ userInfo.userName }}</h4>
                <hr>
                <div><h5>과목 : {{ checkSubject(teacherInfo.subjectCode) }}</h5></div>
                <div><h5>연락처 : {{ makeNum(userInfo.userPhoneNumber) }}</h5></div>
                <div><h5>이메일 : {{ userInfo.userEmail }}</h5></div>     
              </div>
            </div>
            <div class="teacher__bio">
              <h3 style="font-size: 18px;">강사 소개</h3>
              <p>{{ teacherInfo.introduce }}</p>
            </div>
          </div>
        </div>
        <div>
          <h3 class="mt-30">이력 사항</h3>
          <ul class="work_history_detail">
            <li v-for="history of historyInfo" :key="history" class="course__member-item">
              <span>
                <div class="row">
                  <div class="col-10">
                    <h5>{{ history.academyName }} {{ history.startDateYear }}.{{ history.startDateMonth }} ~ {{ history.endDateYear }}.{{ history.endDateMonth }}</h5>
                  </div>
                  <div class="col-1">
                    <button v-if="userCode === 'UT'" @click="putStatus(history)" class="history">수정</button>
                  </div>
                  <div class="col-1">
                    <button v-if="userCode === 'UT'" @click="deleteHistory(history.id)" class="history col-1">삭제</button>
                  </div>
                </div>
                <div v-if="history.state === true && userCode === 'UT'">
                  <span class="input-group-text" id="basic-addon1">
                    <select v-model.number="putStartedYear" size="1">
                      <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
                    </select>
                    년
                    <select v-model.number="putStartedMonth" size="1">
                      <option v-for="month in months" :key="month" :value="month">{{ month }}</option>
                    </select>
                    월 ~ 
                    <select v-model.number="putEndedYear" size="1">
                      <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
                    </select>
                    년
                    <select v-model.number="putEndedMonth" size="1">
                      <option v-for="month in months" :key="month" :value="month">{{ month }}</option>
                    </select>
                    월
                    <input id="input-data" @keyup.enter="putHistory(history.id)" v-model.trim="putHistoryContent" type="text" class="form-control" placeholder="학원 이름을 입력해주세요." aria-label="Username" aria-describedby="basic-addon1">
                    <button class="btn btn-outline-dark" @click="putHistory(history.id)">수정</button>
                  </span>
                </div>   
              </span>
            </li>
          </ul>
          <div class="accordion-body">
            <div class="course__curriculum-content d-flex justify-content-between align-items-center">
              <div class="course__curriculum-info input-group">
                <span class="input-group-text ccc" id="basic-addon1" v-if="userCode === 'UT'">
                  <select v-model.number="startedYear" size="1">
                    <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
                  </select>
                    &nbsp;년&nbsp;
                  <select v-model.number="startedMonth" size="1">
                    <option v-for="month in months" :key="month" :value="month">{{ month }}</option>
                  </select>
                    &nbsp;월 ~ &nbsp;
                  <select v-model.number="endedYear" size="1">
                    <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
                  </select>
                    &nbsp;년&nbsp;
                  <select v-model.number="endedMonth" size="1">
                    <option v-for="month in months" :key="month" :value="month">{{ month }}</option>
                  </select>
                    &nbsp;월&nbsp;
                  <input id="input-data" @keyup.enter="addHistory" v-model.trim="historyContent" type="text" class="form-control" placeholder="학원 이름을 입력해주세요." aria-label="Username" aria-describedby="basic-addon1">
                  <button class="btn btn-outline-dark" @click="addHistory">+</button>
                </span>
              </div>
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
import { useRoute } from 'vue-router'
import { ref, onBeforeMount, onMounted } from 'vue'
export default {
  name:'TeacherDetail',
  setup() {
    const isSticky = ref('false')
    const store = useStore()
    const route = useRoute()
    const userSeq = route.params.userSeq
    const userInfo = ref({})
    const teacherInfo = ref({})
    const historyInfo = ref([])
    const tempList = ref([])
    const academyInfo = ref([])
    const profileImg = ref('')

    const putStartedYear = ref(0)
    const putStartedMonth = ref(0)
    const putEndedYear = ref(0)
    const putEndedMonth = ref(0)
    const putHistoryContent = ref('')

    const startedYear = ref(0)
    const startedMonth = ref(0)
    const endedYear = ref(0)
    const endedMonth = ref(0)
    const historyContent = ref('')

    const userCode = store.state.profile.userCode

    const years = []
    for (let i=1970; i<=2049; i++) {
      years.push(i)
    }

    const months = []
    for (let i=1; i<=12; i++) {
      months.push(i)
    }

    const getUser = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/users/${userSeq}`,
      })
        .then((res) => {
          userInfo.value = res.data
        })

      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/${userSeq}`,
      })
        .then((res) => {
          teacherInfo.value = res.data
        })
      
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/${userSeq}/info`,
      })
        .then((res) => {
          tempList.value = res.data.list
          console.log(tempList.value)
          // tempList에서 학원부터 뽑아내기
          // v-for로 학원 먼저 뱉은 다음 tempList를 반 이름 기준으로 정렬
          // 이중 v-for로 tempList 순회하면서 학원이름이 같으면 출력
          for (let i = 0; i < tempList.value.length; i++) {
            if (!academyInfo.value.includes(tempList.value[i].academy_name)) {
              academyInfo.value.push(tempList.value[i].academy_name)
            }
          }
          tempList.value.sort(function(a, b) {
            if (a.class_name < b.class_name) {
              return -1
            }
            if (a.class_name > b.class_name) {
              return 1
            }
              return 0
          })
        })
    }

    function getHistory() {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/${userSeq}/histories`,
      })
        .then((res) => {
          historyInfo.value = res.data.list
          for (let i of historyInfo.value) {
            i['state'] = false
          }
        })
    }

    const getProfileImg = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/users/${userSeq}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
        .then((res) => {
          profileImg.value = res.data.userProfileImageUrl
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

    const addHistory = () => {
      if ( startedMonth.value === 0 || startedYear.value === 0 || endedMonth.value === 0 || endedYear.value === 0 ) {
        return alert('정확한 연/월을 입력하세요.')
      } else if ( startedYear.value > endedYear.value) {
        return alert('연도를 정확히 입력하세요.')
      } else if ( startedYear.value === endedYear.value && startedMonth.value > endedMonth.value ) {
        return alert('정확한 월 정보를 입력하세요.')
      } else if (historyContent.value === '') {
        return alert('학원 이름을 입력하세요.')
      } else {
        axios({
          method: 'post',
          url: `${store.state.API_URL}/api/v1/teachers/${userSeq}/histories`,
          data: {
            academyName : historyContent.value,
            startDateYear : startedYear.value,
            startDateMonth : startedMonth.value,
            endDateYear : endedYear.value,
            endDateMonth : endedMonth.value
          },
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
          .then(() => {
            getHistory()
          })
      }
    }

    const putStatus = (history) => {
      if (!history.state) {
        history.state = true
      } else {
        history.state = false
      }
    }

    const putHistory = (id) => {
      if ( startedMonth.value === 0 || startedYear.value === 0 || endedMonth.value === 0 || endedYear.value === 0 ) {
        return alert('정확한 연/월을 입력하세요.')
      } else if ( startedYear.value > endedYear.value) {
        return alert('연도를 정확히 입력하세요.')
      } else if ( startedYear.value === endedYear.value && startedMonth.value > endedMonth.value ) {
        return alert('정확한 월 정보를 입력하세요.')
      } else if (historyContent.value === '') {
        return alert('학원 이름을 입력하세요.')
      } else {
        axios({
          method: 'put',
          url: `${store.state.API_URL}/api/v1/teachers/histories/${id}`,
          data: {
            academyName : putHistoryContent.value,
            startDateYear : putStartedYear.value,
            startDateMonth : putStartedMonth.value,
            endDateYear : putEndedYear.value,
            endDateMonth : putEndedMonth.value
          },
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
          .then(() => {
            getHistory()
          })
      }
    }

    const deleteHistory = (id) => {
      axios({
        method: 'delete',
        url: `${store.state.API_URL}/api/v1/teachers/histories/${id}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
        .then(() => {
          getHistory()
        })
    }

    onMounted(() => {
      getProfileImg()
    })
    
    onBeforeMount(() => {
      getUser()
      getHistory()
    })

    return {
      userSeq,

      userInfo,
      teacherInfo,
      historyInfo,
      academyInfo,
      profileImg,
      userCode,

      getUser,

      checkSubject,
      makeNum,

      tempList,
      isSticky,
      
      addHistory,

      years,
      months,

      putStartedYear,
      putStartedMonth,
      putEndedYear,
      putEndedMonth,
      putHistoryContent,

      startedMonth,
      startedYear,
      endedMonth,
      endedYear,
      historyContent,

      putStatus,
      putHistory,
      deleteHistory
    }
  }
}
</script>

<style scoped>
.work_history {
  font-size: 2rem;
  margin-top: 1rem;
}
.work_history_detail {
  margin-top: 1rem;
}


.custom-select option {
  height: 20px;
}

.custom-select {
  height: 22px;
  width: 100%;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 5px;
}
.ccc {
  width: 100%;
}
.history {
  cursor: pointer;
  width: 100%;
  height: 100%;
  background-color: #0e1133;
  color: white;
  border-radius: 3px;
  text-align: center;
  cursor: pointer;

  position: relative;
}
</style>
