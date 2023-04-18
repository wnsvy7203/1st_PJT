<template>
        <vue-cal
            class="vuecal--date-picker"
            xsmall
            hide-view-selector
            :time="false"
            :transitions="false"
            active-view="month"
            today-button
            :disable-views="['week']"
            @cell-click="handleDayClick"
            >
        </vue-cal>
        <vue-cal
            class="vuecal--scheduler vuecal--full-height-delete"
            v-bind:selected-date="selectedDate"
            :time-from="9 * 60"
            :time-to="21 * 60"
            today-button
            :disable-views="['years', 'year', 'month', 'week']"
            hide-weekends
            :editable-events = "{ title: false, drag: false, resize: false, delete: true, create: false }" 
            @selection="onSelection"
            @event-delete="deleteSchedule"
            :events="events">
        </vue-cal>

        <div>
          
        </div>
        <button type="button" class="btn btn-primary aa" data-bs-toggle="modal" data-bs-target="#exampleModal">
        스케줄 등록
      </button>

      <!-- Modal -->
      <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title" id="exampleModalLabel">스케줄 등록</h4>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <div class="video-modal-wrapper">
                <ScheduleModal :selectedDate = selectedDate />
              </div>
            </div>
          </div>
        </div>
      </div>



</template>

<script>
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import { ref, onBeforeMount } from 'vue'
import { useStore } from 'vuex'
import axios from 'axios'
import ScheduleModal from './ScheduleModal.vue'

export default {
  name: 'CalendarScheduleArea',
  components: {
    VueCal,
    ScheduleModal
  },
  setup() {
    const store = useStore()
    const events = ref([])
    const lst = ref([])
    
    const today_list = new Date().toISOString().slice(0, 10).split('-')
    const selectedDate = ref(today_list[0] + '-' + today_list[1] + '-' + today_list[2])

    const getCounsel = () => {
      if (store.state.profile.userCode === 'UT') {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/meetings/counselor/${store.state.myseq}`
      })
      .then((res) => {
        for (let i of res.data.teacherMasterMeetingList) {
        events.value.push({
          start: new Date(i.meeting_start_date.substring(0, 4), i.meeting_start_date.substring(4, 6) - 1, i.meeting_start_date.substring(6, 8), i.meeting_start_hour, 0),
                end: new Date(i.meeting_start_date.substring(0, 4), i.meeting_start_date.substring(4, 6) - 1, i.meeting_start_date.substring(6, 8), i.meeting_start_hour+1, 0),
                title: '상담',     
                id: i.meeting_id
        })
        }
      })
      .catch(() => {
        
      })
    }}

    const getClass = (selectedDate) => {
      let id_list = ref([])
      const date = new Date(selectedDate)
      const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
      const dayOfWeek = days[date.getDay()]
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/${store.state.myseq}/info`
      })
      .then((res) => {
        for (let j of res.data.list) {
          id_list.value.push(j.clas_id)
        }
        for (let k of id_list.value) {
          axios({
            method: 'get',
            url: `${store.state.API_URL}/api/v1/teachers/classes/${k}`
          })
          .then((response) => {
            for (let check of response.data.teacherClassInfo) {
              if (check.day_of_the_week === dayOfWeek) {
                events.value.push({
                  start: new Date(selectedDate.substring(0, 4), selectedDate.substring(5, 7) - 1, selectedDate.substring(8, 10), check.start_time_hour, check.start_time_minute),
                  end: new Date(selectedDate.substring(0, 4), selectedDate.substring(5, 7) - 1, selectedDate.substring(8, 10), check.end_time_hour, check.end_time_minute),
                  title: '수업'
                })
              }
            }
          })
          }
        })
      }
      
    function getSchedule() {
      if (store.state.profile.userCode === 'UT') {
        axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/teachers/${store.state.myseq}/schedules/${dateString(selectedDate)}`,
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
          .then((res) => {
            lst.value = res.data.list
            events.value = []
            for (let schedule of lst.value) {
              events.value.push({
                start: new Date(schedule.scheduleDate.substring(0, 4), schedule.scheduleDate.substring(4, 6) - 1, schedule.scheduleDate.substring(6, 8), schedule.startTimeHour, schedule.startTimeMinute),
                end: new Date(schedule.scheduleDate.substring(0, 4), schedule.scheduleDate.substring(4, 6) - 1, schedule.scheduleDate.substring(6, 8), schedule.endTimeHour, schedule.endTimeMinute),
                title: stateCode(schedule.scheduleCode),
                id: schedule.id
              })
            }  
          })
      } else {
        axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/masters/${store.state.myseq}/schedules/${dateString(selectedDate)}`,
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
          .then((res) => {
            lst.value = res.data.list
            events.value = []
            for (let schedule of lst.value) {
              events.value.push({
                start: new Date(schedule.scheduleDate.substring(0, 4), schedule.scheduleDate.substring(4, 6) - 1, schedule.scheduleDate.substring(6, 8), schedule.startTimeHour, schedule.startTimeMinute),
                end: new Date(schedule.scheduleDate.substring(0, 4), schedule.scheduleDate.substring(4, 6) - 1, schedule.scheduleDate.substring(6, 8), schedule.endTimeHour, schedule.endTimeMinute),
                title: stateCode(schedule.scheduleCode),
                id: schedule.id
              })
            }
            
            getCounsel()
          })
      }
    } 

    function deleteSchedule(event) {
      if (store.state.profile.userCode === 'UT') {
        axios({
          method: 'delete',
          url: `${store.state.API_URL}/api/v1/teachers/schedules/${event.id}`
        })
          .then(() => {
            getSchedule()
          })
      } else if (store.state.profile.userCode === 'UM') {
        axios({
          method: 'delete',
          url: `${store.state.API_URL}/api/v1/masters/schedules/${event.id}`
        })
          .then(() => {
            getSchedule()
          })
      }
    }

    const dateString = (word) => {
      const temp = ref(word.value.split('-'))
      return temp.value[0] + temp.value[1] + temp.value[2]
    }
    const handleDayClick = (date) => {
      const array = date.toString().split(" ")
      showSchedule(array[3], array[1], array[2])
      getSchedule()
    }

    const showSchedule = (year, month, day) => {
      let numMonth = 0
      if (month == "Jan") {
        numMonth = 1;
      } else if (month == "Feb") {
        numMonth = 2;
      } else if (month == "Mar") {
        numMonth = 3;
      } else if (month == "Apr") {
        numMonth = 4;
        }else if (month == "May") {
        numMonth = 5;
      } else if (month == "Jun") {
        numMonth = 6;
      } else if (month == "Jul") {
        numMonth = 7;
      } else if (month == "Aug") {
        numMonth = 8;
      } else if (month == "Sep") {
        numMonth = 9;
      } else if (month == "Oct") {
        numMonth = 10;
      } else if (month == "Nov") {
        numMonth = 11;
      } else if (month == "Dec") {
        numMonth = 12;
      }
      const selectedDateValue = `${year}-${numMonth.toString().padStart(2, '0')}-${day}`
      selectedDate.value = selectedDateValue
      store.dispatch('selectedDate', selectedDate)
      getClass(selectedDateValue)
    }

    const stateCode = (code) => {
      if (code === 'SC') {
        return '수업'
      } else if (code === 'SM') {
        return '상담'
      } else if (code === 'SR') {
        return '휴게시간'
      } else if (code === 'SP') {
        return '개인일정'
      } else if (code === 'SE') {
        return '기타'
      }
    }

    onBeforeMount(() => {
      getSchedule()
    })
    return {
      events,
      lst,
      getSchedule,
      deleteSchedule,
      handleDayClick,
      selectedDate,
      stateCode
    }

  }
}
</script>

<style scoped>
.vuecal--date-picker {
    width: 80%;
    margin-left: 10%;
    margin-top: 5%;
}
.vuecal--scheduler {
    width: 80%;
    margin-left: 10%;
    margin-top: 3%;
    margin-bottom: 5%;
}
.aa {
  position: relative;
  top: -40px;
  left: 150px;
  width: 1223px;
}
</style>