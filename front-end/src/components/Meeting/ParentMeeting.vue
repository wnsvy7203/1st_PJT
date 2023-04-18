<template>
  <div class="wrapper-container">
    <div class="row">
      <div class="col"></div>
      <div class="col-10">
        <div class="row">
          <div class="col-2"><h2>예약 내역</h2></div>
          <div class="col-8"></div>
          <div class="col-2">

            <!-- Button trigger modal -->
            <button type="button" data-bs-toggle="modal" data-bs-target="#exampleModal">
              상담신청
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title" id="exampleModalLabel">상담예약</h1>
                  </div>
                  <div class="modal-body">
                    <CounselReserveArea/>
                    <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <table>
            <thead>
              <tr>
                <th>일시</th>
                <th>학원</th>
                <th>반</th>
                <th>자녀 이름</th>
                <th>상태</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="reserved_meeting in reservedHistory" :key="reserved_meeting.meeting_id">
                <td>{{ reserved_meeting.meeting_start_date }} {{ reserved_meeting.meeting_start_hour }}:{{ reserved_meeting.meeting_start_minute }} ~ {{ reserved_meeting.meeting_start_hour + 1 }}:{{ reserved_meeting.meeting_start_minute }}</td>
                <td>{{ reserved_meeting.academy_name }}</td>
                <td>{{ reserved_meeting.class_name }}</td>
                <td>{{ reserved_meeting.student_name}}</td>
                <td v-if="timecheck(reserved_meeting.meeting_start_date, reserved_meeting.meeting_start_hour, reserved_meeting.meeting_start_minute)">{{ reserved_meeting.state }}</td>
                <td v-if="!timecheck(reserved_meeting.meeting_start_date, reserved_meeting.meeting_start_hour, reserved_meeting.meeting_start_minute)" @click="meetingStart(reserved_meeting)" style="font-weight: bold; cursor: pointer;">입장</td>
              </tr>
            </tbody>
          </table>
          <h2>최근 상담 내역</h2>
          <table class="overflow-scroll">
            <thead>
              <tr>
                <th>일시</th>
                <th>학원</th>
                <th>반</th>
                <th>자녀 이름</th>
                <th>상태</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="meeting in counselHistory_shown" :key="meeting.meeting_id">
                <td>{{ meeting.meeting_start_date }} {{ meeting.meeting_start_hour }}:{{ meeting.meeting_start_minute }} ~ {{ meeting.meeting_start_hour + 1 }}:{{ meeting.meeting_start_minute }}</td>
                <td>{{ meeting.academy_name }}</td>
                <td>{{ meeting.class_name }}</td>
                <td>{{ meeting.student_name }}</td>
                <td>{{ meeting.state }}</td>
              </tr>
            </tbody>
          </table>
      </div>
      <div class="col"></div>
    </div>    
  </div>
</template>

<script>
import CounselReserveArea from "@/components/Parent/CounselReserveArea.vue"
import { ref, reactive, onBeforeMount } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: "ParentMeeting",
  components: {
    CounselReserveArea
  },
  setup() {
    const store = useStore()
    const router = useRouter()

    const reservedHistory = reactive([])
    const counselHistory = reactive([])
    const counselHistory_shown = ref([])

    const getCounselData = () => {
      axios({ 
        method: 'get',
        url: `${store.state.API_URL}/api/v1/meetings/parents/${store.state.myseq}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((response) => {
        for (let i of response.data.parentMeetingList) {
          const meeting_date = `${i.meeting_start_date.slice(0, 4)}.${i.meeting_start_date.slice(4, 6)}.${i.meeting_start_date.slice(6)}.`
          i.meeting_start_date = meeting_date
          
          if (i.meeting_start_minute < 10) {
            i.meeting_start_minute = `0${i.meeting_start_minute}`
          }

          if (i.meeting_code === 'MW') {
            i.state = '상담 대기'
            reservedHistory.push(i)
          // 입장
          } else if (i.meeting_code === 'MA') {
            i.state = '입장'
            reservedHistory.push(i)
          // 상담 중
          } else if (i.meeting_code === 'MI') {
            i.state = '상담 중'
            reservedHistory.push(i)
          // 리뷰쓰기
          } else if (i.meeting_code === 'MR') {
            i.state = '리뷰 쓰기'
            counselHistory.push(i)
          // 상담 완료
          } else if (i.meeting_code === 'MC') {
            i.state = '상담 완료'
            counselHistory.push(i)
          }
        }
        sortData()
      })
    }

    function sortData() {
      reservedHistory.sort(function (x, y) {
        if (x.meeting_start_date === y.meeting_start_date) {
          if (x.meeting_start_hour === y.meeting_start_hour) {
            return x.meeting_start_minute < y.meeting_start_minute ? -1 : x.meeting_start_minute > y.meeting_start_minute ? 1 : 0
          }
          return x.meeting_start_hour < y.meeting_start_hour ? -1 : 1
        }
        return x.meeting_start_date < y.meeting_start_date ? -1 : 1
      })
      counselHistory.sort(function (x, y) {
        if (x.meeting_start_date === y.meeting_start_date) {
          if (x.meeting_start_hour === y.meeting_start_hour) {
            return x.meeting_start_minute < y.meeting_start_minute ? 1 : x.meeting_start_minute > y.meeting_start_minute ? -1 : 0
          }
          return x.meeting_start_hour < y.meeting_start_hour ? 1 : -1
        }
        return x.meeting_start_date < y.meeting_start_date ? 1 : -1
      })

      counselHistory_shown.value = counselHistory.slice(0, 5)
    }

    function meetingStart(meeting) {
      store.dispatch('meetingData', meeting)
      router.push({ name: 'meetingSession' })
    }

    function timecheck(startDate, startHour, startMinute) {
      const dateStr = startDate.replace(/\./g, '-').slice(0, -1)
      const then = new Date(`${dateStr} ${startHour}:${startMinute}:00`)
      const correctedNow = new Date()
      const diffInMs = correctedNow.getTime() - then.getTime()
      const diffInMinutes = diffInMs / 1000 / 60

      // 원래는 시작시간 10분전부터 예약된 상담시간이 끝나기전까지만 입장버튼이 활성화 되어야 하지만
      // 최종 발표 시연 때 해당 기능을 보여주기 위하여 임시로 -59분 ~ 59분으로 값을 수정해둠
      // if (diffInMinutes < 60 && diffInMinutes > -10) {
      if (diffInMinutes < 60 && diffInMinutes > -60) {
        return false
      } else {
        return true
      }
    }
    onBeforeMount(() => {
      getCounselData()
    })

    return {
      reservedHistory,
      counselHistory_shown,
      timecheck,
      meetingStart
    }
  }
};
</script>

<style scoped>
.wrapper-container{
  width: 90%;
  height: auto;
  overflow: hidden;
  margin-top: 30px;
  margin-left: 5%;
  margin-bottom: 5%;
  padding-bottom: 10px;
}
h2 {
  margin-top: 40px;
}
table {
  border: 1px #a39485 solid;
  font-size: 0.9em;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
  margin-top: 20px;
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

button {
  margin-top: 45px;
  margin-left: 49px;
  background-color: white;
  color: black;
  border: 0.5px solid black;
  border-radius: 5px;
  width: 120px;
  height: 40px;
}

</style>