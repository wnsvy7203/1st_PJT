<template>
  <!-- 
    < alertCode >
    강사가 학원장에게 등록 신청 알림	A1
    학생이 학원장에게 등록 신청 알림	A2
    학부모가 강사에게 상담 신청 알림	A3
    상담 10분 전 알림 (학원장)         	A4
    상담 10분 전 알림 (학부모) 	        A5
    상담 10분 전 알림 (강사) 	        A6
    
    < alertDate >
    2023-01-24T16:11:19.595 형태로 들어오면 
    now(현재 시간) 에서 빼기
    - 한 시간 이내일 경우 : 몇 분 전
    - 또는 몇 시간 전
    - 며칠 전
    - 몇달 전 

    < approveYn >
    승인 완료일 경우 버튼 : approveBtn
    승인 대기 중일 경우 버튼 : approveWaitBtn

   -->
  <div class="modal-container">
    <div class="container">
      <div class="row pb-10">
        <div class="col alertH5"><h5>알림</h5></div>
        <div class="col dot">
          <div class="deleteMode" @click="deleteState = deleteState ? false : true">
            <i class="fa-lg far fa-trash-alt" v-if="!deleteState"></i>
            <i class="fa-lg fas fa-trash-alt" v-if="deleteState"></i>
          </div>
        </div>
      </div>
    </div>
    <hr />
    <div class="alertBox">
      <div v-if="alert_list.length < 1">
        <p style="text-align:center; margin-top: 50%;">등록된 알림이 없습니다</p>
      </div>
      <div class="alert" v-for="alert of alert_list" :key="alert">
        <div v-if="alert.alertCode === 'A1'" :style="{ opacity: alert.readYn ? 0.4 : 1 }">
          <div class="contentAndDate">
            <div class="content">{{ alert.name}} 강사 등록 신청</div>
            <div class="date">{{ getDate(alert.alertDate) }}</div>
          </div>
          <div class="deleteBtn" v-if="deleteState" @click="deleteAlert(alert.id)">✖</div>
          <div class="approveWaitBtn" v-if="alert.approveYn === null" @click="check_no_teacher(alert.id)">반려</div>
          <div class="approveWaitBtn" v-if="alert.approveYn === null" @click="check_ok_teacher(alert.id)">승인</div>
          <div class="approveBtn" v-if="alert.approveYn === false">완료</div>
          <div class="approveBtn" v-if="alert.approveYn === true">완료</div>
        </div>
        <div v-if="alert.alertCode === 'A2'" :style="{ opacity: alert.readYn ? 0.4 : 1 }">
          <div class="contentAndDate">
            <div class="content">{{ alert.name}} 학부모 학생 등록 신청</div>
            <div class="date">{{ getDate(alert.alertDate) }}</div>
          </div>
          <div class="deleteBtn" v-if="deleteState" @click="deleteAlert(alert.id)">✖</div>
          <div class="approveWaitBtn" v-if="alert.approveYn === null" @click="check_no_student(alert.id)">반려</div>
          <div class="approveWaitBtn" v-if="alert.approveYn === null" @click="check_ok_student(alert.id)">승인</div>
          <div class="approveBtn" v-if="alert.approveYn === false">완료</div>
          <div class="approveBtn" v-if="alert.approveYn === true">완료</div>
        </div>
        <div v-if="alert.alertCode === 'A3'" :style="{ opacity: alert.readYn ? 0.4 : 1, cursor: alert.readYn ? 'default' : 'pointer'}">
          <div class="contentAndDate" @click= "check(alert.id)">
            <div class="content">{{ alert.name}} 학부모 상담 신청</div>
            <div class="date">{{ getDate(alert.alertDate) }}</div>
          </div>
          <div class="deleteBtn" v-if="deleteState" @click="deleteAlert(alert.id)">✖</div>
        </div>
        <!-- <div v-if="alert.alertCode === 'A4'" :style="{ opacity: alert.readYn ? 0.4 : 1 }">
          <div class="contentAndDate">
            <div class="content">{{ alert.name}} 학부모 상담 10분 전</div>
            <div class="date">{{ getDate(alert.alertDate) }}</div>
          </div>
          <div class="deleteBtn" v-if="deleteState" @click="deleteAlert(alert.id)">✖</div>
          <div class="approveWaitBtn" v-if="alert.approveYn === null">입장</div>
          <div class="approveBtn" v-if="alert.approveYn === true">완료</div>
        </div>
        <div v-if="alert.alertCode === 'A5'" :style="{ opacity: alert.readYn ? 0.4 : 1 }">
          <div class="contentAndDate">
            <div class="content">{{ alert.name}} 학원 상담 10분 전</div>
            <div class="date">{{ getDate(alert.alertDate) }}</div>
          </div>
          <div class="deleteBtn" v-if="deleteState" @click="deleteAlert(alert.id)">✖</div>
          <div class="approveWaitBtn" v-if="alert.approveYn === null">입장</div>
          <div class="approveBtn" v-if="alert.approveYn === true">완료</div>
        </div>
        <div v-if="alert.alertCode === 'A6'" :style="{ opacity: alert.readYn ? 0.4 : 1 }">
          <div class="contentAndDate">
            <div class="content">{{ alert.name}} 학부모 상담 10분 전</div>
            <div class="date">{{ getDate(alert.alertDate) }}</div>
          </div>
          <div class="deleteBtn" v-if="deleteState" @click="deleteAlert(alert.id)">✖</div>
          <div class="approveWaitBtn" v-if="alert.approveYn === null">입장</div>
          <div class="approveBtn" v-if="alert.approveYn === true">완료</div>
        </div> -->
        </div>
      </div>
    </div>
</template>

<script>
import { ref, onBeforeMount } from 'vue'
import { useStore } from "vuex";
import axios from 'axios'

export default {
  name: "AlertModalTab",
  setup() {
    const store = useStore()
    const alert_list = ref([])
    const deleteState = ref(false)
    const now = ref(new Date())
    const getNotification = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/alerts/users/${store.state.myseq}`
      })
      .then((res) => {
        alert_list.value = res.data.alertList
        alert_list.value.reverse()
      })
      .catch(() => {

      })
    }

    const deleteAlert = (id) => {
      axios ({
        method: 'delete',
        url: `${store.state.API_URL}/api/v1/alerts/${id}`
      })
      .then( () => {
        getNotification()
      })
    }

    const getDate = (date1) => {
      const then = new Date(date1[0],date1[1]-1,date1[2],date1[3]+9,date1[4],date1[5])
      const correctedNow = new Date(now.value.getTime())
      const diffInMs = correctedNow.getTime() - then.getTime()
      const yearInMs = 365 * 24 * 60 * 60 * 1000
      const monthInMs = 30 * 24 * 60 * 60 * 1000
      const weekInMs = 7 * 24 * 60 * 60 * 1000
      const dayInMs = 24 * 60 * 60 * 1000
      const hourInMs = 60 * 60 * 1000
      const minuteInMs = 60 * 1000
      if (diffInMs >= yearInMs) {
        return `${Math.floor(diffInMs / yearInMs)}년 전`
      } else if (diffInMs >= monthInMs) {
        return `${Math.floor(diffInMs / monthInMs)}달 전`
      } else if (diffInMs >= weekInMs) {
        return `${Math.floor(diffInMs / weekInMs)}주 전`
      } else if (diffInMs >= dayInMs) {
        return `${Math.floor(diffInMs / dayInMs)}일 전`
      } else if (diffInMs >= hourInMs) {
        return `${Math.floor(diffInMs / hourInMs)}시간 전`
      } else if (diffInMs >= minuteInMs) {
        return `${Math.floor(diffInMs / minuteInMs)}분 전`
      } else {
        return '0분 전'
      }
    }

    const check = (id) => {
      axios({
        method: 'put',
        url: `${store.state.API_URL}/api/v1/alerts/${id}`
      })
      .then( () => {
          getNotification()
        })
    }

    const check_ok_teacher = ((id) => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/masters/approveteacher/alerts/${id}`
      })
      .then(() => {
        axios({
          method: 'put',
          url: `${store.state.API_URL}/api/v1/alerts/${id}`
        })
        .then(() => {
          getNotification()
        })
      })
    })
    
    const check_no_teacher = ((id) => {
      axios({
        method: 'delete',
        url: `${store.state.API_URL}/api/v1/masters/dismissteacher/alerts/${id}`
      })
      .then(() => {
        axios({
          method: 'put',
          url: `${store.state.API_URL}/api/v1/alerts/${id}`
        })
        .then(() => {
          getNotification()
        })
      })
    })
    
    const check_ok_student = ((id) => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/masters/approvestudent/alerts/${id}`
      })
      .then(() => {
        axios({
          method: 'put',
          url: `${store.state.API_URL}/api/v1/alerts/${id}`
        })
        .then(() => {
          getNotification()
        })
      })
    })
    
    const check_no_student = ((id) => {
      axios({
        method: 'delete',
        url: `${store.state.API_URL}/api/v1/masters/dismissstudent/alerts/${id}`
      })
      .then(() => {
        axios({
          method: 'put',
          url: `${store.state.API_URL}/api/v1/alerts/${id}`
        })
        .then(() => {
          getNotification()
        })
      })
    })

    onBeforeMount(() => {
      getNotification()
    })

    return {
      alert_list,
      deleteState,
      deleteAlert,
      getNotification,
      // getUser,
      getDate,
      check,
      check_ok_teacher,
      check_no_teacher,
      check_ok_student,
      check_no_student
    }
  }
}
</script>

<style scoped>
.modal-container {
  width: 400px;
  height: 500px;
  position: absolute;
  right: 0px;
  top: 25px;
  z-index: 999;
  background-color: rgb(255, 255, 255);
  border: 1px solid rgb(203, 203, 203);
  border-radius: 5px;
  color: black;
  cursor: default;
  padding: 20px;
}

.deleteMode {
  width: 43px;
  height: 30px;
  font-size: 14px;
  cursor: pointer;
  /* border: 1px solid grey; */
  position: absolute;
  right: 35px;
  border-radius: 3px;
  padding: 2px 6px 0 0;
}

.dot {
  text-align: right;
}

h5 {
  margin-bottom: 0;
  position: relative;
  top: 7px;
}

hr {
  margin-bottom: 0;
}

.alertH5 {
  position: relative;
}

/* scroll */
.alertBox {
  height: 410px;
  overflow-y: scroll;
}
.alertBox::-webkit-scrollbar {
  width: 5px; /* 스크롤바의 너비 */
}
.alertBox::-webkit-scrollbar-thumb {
  height: 20%; /* 스크롤바의 길이 */
  background: #ffb26586; /* 스크롤바의 색상 */

  border-radius: 10%;
}
.alertBox::-webkit-scrollbar-track {
  background: #63636336; /*스크롤바 뒷 배경 색상*/
  border-radius: 3%;
}

.alert {
  width: 100%;
  height: 90px;
  padding: 10px;
  margin: 15px 0;

  border-bottom: 1px solid rgba(219, 219, 219, 0.713);
}

.date {
  margin-top: 10px;
  font-size: 13px;
  color: grey;
}

.contentAndDate {
  float: left;
}

.approveBtn {
  float: right;
  text-align: center;
  padding-top: 4px;
  background-color: rgb(213, 213, 213);
  width: 75px;
  height: 33px;
  border-radius: 3px;
  margin-top: 33px;
  font-size: 14px;
  margin-right: 10px;
  cursor: pointer;
}

.approveWaitBtn {
  float: right;
  text-align: center;
  padding-top: 4px;
  background-color: #ff8f21;
  color: white;
  width: 52px;
  height: 33px;
  border-radius: 3px;
  margin-top: 33px;
  font-size: 14px;
  margin-right: 10px;
  cursor: pointer;
}

.deleteBtn {
  position: absolute;
  top: 3px;
  right: 30px;
  width: 10px;
  height: 10px;
  color: rgb(0, 0, 0);
  cursor: pointer;
}
</style>
