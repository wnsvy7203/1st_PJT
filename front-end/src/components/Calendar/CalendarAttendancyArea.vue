<template>
  <vue-cal
    :time-from="9 * 60"
    :disable-views="['years', 'year', 'week', 'day']"
    active-view="month"
    hide-weekends
    events-on-month-view="short"
    :show-all-day-events="['short', true, false][showAllDayEvents]"
    :events="events"
    style="height: 600px"
  >
  </vue-cal>
</template>

<script>
import VueCal from "vue-cal";
import "../../assets/css/vuecal.css";
import axios from 'axios';
import { useStore } from 'vuex';
import { ref } from '@vue/reactivity';
import { onBeforeMount } from '@vue/runtime-core';

export default {
  name: 'CalendarAttendancyArea',
  components: {
    VueCal
  },

  setup() {
    const store = useStore()
    const attendanceList = ref('')
    const events = ref([])

    function getAttendanceList() {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/parents/classes/${store.state.classId}/attendancies/students/${store.state.student_id}`
      })
      .then((res) => {
        attendanceList.value = res.data.list
        for (let attendance of attendanceList.value) {
          const date = attendance.attendancyDate.substr(0,4) + '-' + attendance.attendancyDate.substr(4,2) + '-' + attendance.attendancyDate.substr(6,2)
          let classname = 'attendance'
          if (attendance.attendancyCode === 'AA') { 
            classname = 'attendance'
          } else if (attendance.attendancyCode === 'AB' || attendance.attendancyCode === 'AT') {
            classname = 'redattendance'
          } else if (attendance.attendancyCode === 'AE') {
            classname = 'yellowattendance'
          } else if (attendance.attendancyCode === 'AO') {
            classname = 'grayattendance'
          }
          const event = {
            start: date,
            end: date,
            title: checkAttendance(attendance.attendancyCode),
            class: classname
          }
          events.value.push(event)
        }

      })
      .catch((err) => {
        console.log(err)
      })
    }
    function checkAttendance(code) {
      if (code==='AA') {
        return "출석"
      } else if (code==='AB') {
        return "결석"
      } else if (code==='AT') {
        return "지각"
      } else if (code==='AE') {
        return '조퇴'
      } else if (code==='AO') {
        return '외출'
      }
    }
    onBeforeMount(() => {
      getAttendanceList()
    })
    return {
      checkAttendance,
      attendanceList,
      events,
    }
  }
};
</script>

<style scoped>
.vuecal--month-view .vuecal__cell {
  height: 80px;
}

.vuecal--month-view .vuecal__cell-content {
  justify-content: flex-start;
  height: 100%;
  align-items: flex-end;
}

.vuecal--month-view .vuecal__cell-date {
  padding: 4px;
}
.vuecal--month-view .vuecal__no-event {
  display: none;
}

html {
  color: rgb(128, 0, 0);
}
</style>