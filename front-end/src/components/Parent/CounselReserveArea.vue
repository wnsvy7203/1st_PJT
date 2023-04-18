<template>
  <div>
    <h5>학생과 학원을 선택하세요</h5>
    <select name="selectChild" id="selectChild" v-model="student_id" @change="getClass()">
      <option v-for="child in children" :key="child" :value="child.student_id">{{ child.student_name }}</option>
    </select>
    <div class="list-group" v-for="item in classes" :key="item" @click="getSep(item.clas_id)">
      <button style="text-align:left;" class="list-group-item">{{ item.academy_name }} {{ item.class_name }}</button>
    </div>
  </div>

  <div v-if="ishidden3!==true">
    <h5 style="margin-top: 25px;">상담을 원하시는 선생님을 선택해주세요</h5>
    <div class="list-group d-flex">
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" @click="masterSelected()">학원장</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" @click="teacherSelected()">강사님</button>
    </div>
    <div v-if="ishidden2!==true">
      <h5 style="margin-top: 25px;">희망 상담일자를 선택해주세요</h5>
      <input type="date" v-model="selectedDay" @change="selectDay()" id="myDate">
    </div>
  
    <div v-if="ishidden!==true">
      <div class="list-group">
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(900)!==true" @click="timekeep(9)">09:00 ~ 10:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1000)!==true" @click="timekeep(10)">10:00 ~ 11:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1100)!==true" @click="timekeep(11)">11:00 ~ 12:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1200)!==true" @click="timekeep(12)">12:00 ~ 13:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1300)!==true" @click="timekeep(13)">13:00 ~ 14:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1400)!==true" @click="timekeep(14)">14:00 ~ 15:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1500)!==true" @click="timekeep(15)">15:00 ~ 16:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1600)!==true" @click="timekeep(16)">16:00 ~ 17:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1700)!==true" @click="timekeep(17)">17:00 ~ 18:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1800)!==true" @click="timekeep(18)">18:00 ~ 19:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(1900)!==true" @click="timekeep(19)">19:00 ~ 20:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(2000)!==true" @click="timekeep(20)">20:00 ~ 21:00</button>
        <button type="button" class="list-group-item list-group-item-action" data-bs-toggle="list" v-if="istime(2100)!==true" @click="timekeep(21)">21:00 ~ 22:00</button>
      </div>
    </div>
  </div>

  <button type="button"  @click="reserveCounsel()" class="reserveBtn">예약</button>
</template>
  
<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { onBeforeMount, ref } from '@vue/runtime-core'
import { useRouter } from 'vue-router'
export default {
  name: 'CounselApplicationView',
  setup() {
    const router = useRouter()
    const store = useStore()
    const isSelectd = ref('')
    const selectedDay = ref('')
    const teacher_schedule = ref([])
    const master_schedule = ref([])
    const time1 = ref([])
    const time2 = ref([])
    const time3 = ref([])
    const len = ref('')
    const start_Time_Minute = ref('')
    const end_time_minute = ref('')
    const counselorSeq = ref('')
    const childname = ref('')
    const start_time_hour = ref('')
    const ishidden = ref(true)
    const ishidden2 = ref(true)
    const ishidden3 = ref(true)
    const children = ref('')
    const student_id = ref('')
    const class_id = ref('')
    const classes = ref('')
    const counsel_schedule = ref([])
    const class_schedule = ref([])
    const daysOfWeek = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
    function getChildren() {
    axios({
      methods: 'get',
      url : `${store.state.API_URL}/api/v1/parents/${store.state.myseq}/students`,
      header: {
        Authorization: `Bearer ${store.state.token}`
      }
    })
    .then((res) => {
      children.value = res.data.list
    })
  } 
  
  // 해당 학생의 반 조회
  function getClass() {
    ishidden3.value = true
    selectedDay.value = null
    ishidden.value = true
    axios({
        methods: 'get',
        url: `${store.state.API_URL}/api/v1/parents/students/${student_id.value}/classes`,
        header: {
        Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((res) => {
        classes.value = res.data.list
      })
  }

  // 반에 관련된 학원장 및 강사 정보 저장
  function getSep(classId) {
    class_id.value = classId
    ishidden3.value = false
    axios({
      methods: 'get',
      url: `${store.state.API_URL}/api/v1/meetings/classes/${classId}`,
      header: {
        Authorization: `Bearer ${store.state.token}`
      }
    })
    .then((res) => {
      store.dispatch('saveSeq', res.data)
    })
  }

    // 선생님 스케줄 조회
    function getTeacherSchedule(day) {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/${store.state.seq.teacherSeq}/schedules/${day}`
      })
      .then((res) => {
        teacher_schedule.value = res.data.list
        len.value = teacher_schedule.value.length
        for (let i = 0; i < len.value; i++ ) {
          time1.value.push(teacher_schedule.value[i])
        }
      })
    }

    // 학원장 스케줄 조회
    function getMasterSchedule(day) {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/masters/${store.state.seq.masterSeq}/schedules/${day}`
      })
      .then((res) => {
        master_schedule.value = res.data.list
        len.value = master_schedule.value.length
        for (let i = 0; i < len.value; i++ ) {
          time1.value.push(master_schedule.value[i])
        }
      })
    }

    // 강사/학원장의 상담 예약내역 조회
    function getCounselSchedule(id,day) {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/meetings/counselor/${id}`
      })
      .then((res) => {
        counsel_schedule.value = res.data.teacherMasterMeetingList
        len.value = counsel_schedule.value.length
        for (let i = 0; i < len.value; i++ ) {
          if (day === counsel_schedule.value[i].meeting_start_date) {
            time2.value.push(counsel_schedule.value[i])
          }
        }
      })
    }
    // 강사의 담당반 조회
    function getClassSchedule(id, day) {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/parents/schedules/teachers/${id}`
      })
      .then((res) => {
        class_schedule.value = res.data.list
        len.value = class_schedule.value.length
        const formattedDate = `${day.substr(0, 4)}-${day.substr(4, 2)}-${day.substr(6, 2)}`
        const date = new Date(formattedDate)
        const dayOfWeek = daysOfWeek[date.getDay()]
        for (let i = 0; i < len.value; i++ ) {
          if (dayOfWeek == class_schedule.value[i].day_of_the_week) {
            time3.value.push(class_schedule.value[i])
          }
        }
      })
    }

    // 학원장이 선택되었을때 기본세팅
    function masterSelected() {
      isSelectd.value = 'UM'
      counselorSeq.value = store.state.seq.masterSeq
      ishidden2.value = false
      selectedDay.value = null
      ishidden.value = true
    }
    // 강사가 선택되었을 때 기본 세팅
    function teacherSelected() {
      isSelectd.value = 'UT'
      counselorSeq.value = store.state.seq.teacherSeq
      ishidden2.value = false
      selectedDay.value = null
      ishidden.value = true
    }

    // 비어있는 시간 확인을 하기 위해 일정 담는 array들 초기화
    function cleartime(){
      time1.value = []
      time2.value = []
      time3.value = []
    }

    const selectDay = () => {
      // 날짜 고르면 일단 array 초기화
      cleartime()

      // day는 오늘날짜, tomorrowString은 내일날짜
      const day = selectedDay.value.split('-')[0] + selectedDay.value.split('-')[1] + selectedDay.value.split('-')[2]
      
      const todayList = new Date().toISOString().slice(0, 10).split('-')
      const today = todayList[0] + todayList[1] + todayList[2]

      if ((Number(today) - Number(day)) > 0) {
        alert('이미 지난 날짜는 선택할 수 없습니다.')
        ishidden.value = true
        return document.getElementById('myDate').value = new Date().toISOString().slice(0, 10)
      } else {
        // 강사일 때 선택한 날짜의 일정
        if (isSelectd.value==='UT') {
          getTeacherSchedule(day)
          getCounselSchedule(store.state.seq.teacherSeq, day)
          getClassSchedule(store.state.seq.teacherSeq, day)
  
        // 학원장일때 선택한 날짜의 일정
        } else {
          getMasterSchedule(day)
          getCounselSchedule(store.state.seq.masterSeq, day)
        }
        ishidden.value = false
      }
    }
      

    // 비는 시간 찾기 (비는시간이 아닐경우 true)
    function istime(date) {
      const len2 = time1.value.length
      for (let i=0; i <len2; i++) {
        if (time1.value[i].startTimeMinute < 10) {
          start_Time_Minute.value = '0' + String(time1.value[i].startTimeMinute)
        }
        if (time1.value[i].endTimeMinute < 10) {
          end_time_minute.value = '0' + String(time1.value[i].endTimeMinute)
        }
        const startTime = parseInt(String(time1.value[i].startTimeHour) + String(start_Time_Minute.value))
        const endTime = parseInt(String(time1.value[i].endTimeHour) + String(end_time_minute.value))
        if (startTime <= date && date < endTime) {
          return true
        }
      }
      for ( let j of time2.value) {
        start_Time_Minute.value = '00'
        end_time_minute.value = '00'
        const startTime = parseInt(String(j.meeting_start_hour) + String(start_Time_Minute.value))
        const endTime = parseInt(String(j.meeting_start_hour+1) + String(end_time_minute.value))
        if (startTime <= date && date < endTime) {
          return true
        }
      }

      for ( let k of time3.value) {
        if (k.start_time_minute < 10) {
          start_Time_Minute.value = '0' + String(k.start_time_minute)
        }
        if (k.end_time_minute < 10) {
          end_time_minute.value = '0' + String(k.end_time_minute)
        }
        const startTime = parseInt(String(k.start_time_hour) + String(start_Time_Minute.value))
        const endTime = parseInt(String(k.end_time_hour) + String(end_time_minute.value))
        if (startTime <= date && date < endTime) {
          return true
        }
      }
      
    }
    function timekeep(starttime) {
      start_time_hour.value = starttime
    }

    function reserveCounsel() {
      const day = selectedDay.value.split('-')[0] + selectedDay.value.split('-')[1] + selectedDay.value.split('-')[2]
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/parents/students/${student_id.value}`
      })
      .then((res) => {
        childname.value = res.data.studentInfo.student_name
        axios({
          method: 'post',
          url: `${store.state.API_URL}/api/v1/meetings/parents/${store.state.myseq}`,
          data: {
            counselorSeq: counselorSeq.value,
            meetingCode: 'MW',
            academyName: store.state.seq.academyName,
            className: store.state.seq.className,
            studentName: childname.value,
            startTimeHour:  start_time_hour.value,
            startTimeMinute: 0,
            startDate: day,
            classId: class_id.value,
            studentId: student_id.value
          }
        })
        .then(() => {
          alert('상담 예약이 완료되었습니다')
          router.go(0)
        })
      })
    }

    function cancelbtn() {
      router.go(0)
    }
    
    onBeforeMount(() => {
      getChildren()
    })
  
    return {
      reserveCounsel,
      selectedDay,
      selectDay,
      masterSelected,
      teacherSelected,
      teacher_schedule,
      master_schedule,
      time1,
      time2,
      time3,
      len,
      istime,
      start_Time_Minute,
      end_time_minute,
      cleartime,
      counselorSeq,
      childname,
      timekeep,
      ishidden,
      ishidden2,
      ishidden3,
      children,
      student_id,
      classes,
      getClass,
      getSep,
      cancelbtn,
    }
  }
}
</script>
  
<style scoped>
.reserveBtn {
  margin-top: 45px;
  margin-left: 49px;
  background-color: white;
  color: black;
  border: 0.5px solid black;
  border-radius: 5px;
  width: 120px;
  height: 40px;
  position: relative;
  top: 23px;
}
.list-group-item-action.active {
    background-color: #ff8f21;
  }
</style>