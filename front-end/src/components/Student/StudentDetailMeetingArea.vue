<template>
<section class="teacher__area pt-100 pb-110">
   <div class="container">
      <div class="row">
         <div class="col-xxl-12 col-xl-12 col-lg-12">
            <div class="teacher__wrapper">
               <div class="teacher__top d-md-flex align-items-end justify-content-between">
                  <div class="teacher__info">
                    <h4>{{ classname }}</h4>
                    <h4>{{ studentInfo.student_name }}</h4>
                  </div>
                  <div class="teacher__info">
                     <div>학교 : {{ studentInfo.student_school }}</div>
                     <div>연락처 : {{ studentInfo.student_phone_number }}</div>
                  </div>
                  <div class="teacher__info">
                     <div>학년 : {{ studentInfo.grade_code }}</div>
                     <div>나이 : {{ studentInfo.student_age}}세</div>
                  </div>
               </div>
               <div class="teacher__bio">
                  <h1>출석</h1>
                  <CalendarAttendancyArea/>
                  <template>
                    <b-row>
                        <b-col md="auto">
                        <b-calendar v-model="value" @context="onContext" locale="en-US"></b-calendar>
                        </b-col>
                        <b-col>
                        <p>Value: <b>'{{ value }}'</b></p>
                        <p class="mb-0">Context:</p>
                        <pre class="small">{{ context }}</pre>
                        </b-col>
                    </b-row>
                </template>
               </div>
               <div class="teacher__courses pt-55 teacher__bio">
                  <div class="section__title-wrapper mb-30">
                    <h1>성적</h1>
                  </div>
                    <table border=1>
                        <thead>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;일시&nbsp;&nbsp;&nbsp;</td>
                                <td>시험명&nbsp;&nbsp;&nbsp;</td>
                                <td>점수&nbsp;&nbsp;&nbsp;</td>
                                <td>평균&nbsp;&nbsp;&nbsp;</td>
                                <td>석차&nbsp;&nbsp;&nbsp;</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="score of scoreList" :key="score">
                                <td>{{ getDate(score.testDate) }}</td>
                                <td>{{ score.testInfoName }}</td>
                                <td>{{ score.score }}</td>
                                <td>{{ score.avgScore }}</td>
                                <td>{{ score.rank}} / {{ score.totalCnt}}</td>
                            </tr>
                        </tbody>
                    </table>
                  <br>
               </div>
               <div class="section__title-wrapper mb-30">
                <br>
                     <h1>특이사항</h1>
                  </div>
                  <ul>
                    <li v-for="comment of commentLine" :key="comment">{{ comment.note_content }}<span @click="comment_delete(comment.note_id)">&nbsp;&nbsp;<img src="../../assets/img/plus-minus/icon-delete.png" width=15 height=15 style="cursor: pointer;"></span></li>
                  </ul>
            </div>
         </div>
      </div>
   </div>
</section>
</template>

<script>
import CalendarAttendancyArea from "../Calendar/CalendarAttendancyArea.vue";
import {useStore} from 'vuex'
import axios from 'axios'
import { onBeforeMount, ref } from 'vue'

export default {
   name:'StudentDetail',
   components: { CalendarAttendancyArea },
   setup() {
    const store = useStore()
    const studentInfo = ref('')
    const attendancyList = ref('')
    const scoreList = ref('')
    const commentLine = ref('')
    const comment = ref('')
    const classname = ref('')
    const getStudent = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/students/${store.state.student_id}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((res) => {
        studentInfo.value = res.data.studentInfo
        axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}/attendancies/students/${store.state.student_id}`,
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
        .then((response) => {
          attendancyList.value = response.data.list
          axios({
            method: 'get',
            url: `${store.state.API_URL}/api/v1/teachers/scores/classes/${store.state.classId}/students/${store.state.student_id}`,
            headers: {
              Authorization: `Bearer ${store.state.token}`
            }
          })
          .then((respond) => {
            scoreList.value = respond.data.list
            console.log(scoreList.value)
            axios({
              method: 'get',
              url: `${store.state.API_URL}/api/v1/notes/classes/${store.state.classId}/students/${store.state.student_id}`,
              headers: {
                Authorization: `Bearer ${store.state.token}`
              }
            })
            .then((respons) => {
              commentLine.value = respons.data.list
              axios({
                method: 'get',
                url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}`,
                headers: {
                  Authorization: `Bearer ${store.state.token}`
                }
              })
              .then((responde) => {
                classname.value = responde.data.teacherClassInfo[0].class_name
              })
            })
          })
        })
      })
    }
    const addComment = () => {
      axios({
        method: 'post',
        url: `${store.state.API_URL}/api/v1/teachers/notes/classes/${store.state.classId}/students/${store.state.student_id}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        },
        data: {
            content: comment.value
        }
      })
      .then(() => {
        comment.value = ''
        axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/notes/classes/${store.state.classId}/students/${store.state.student_id}`,
          headers: {
            Authorization: `Bearer ${store.state.token}`
          },
        })
        .then((respons) => {
          commentLine.value = respons.data.list
        })
      })
    }

   const getDate = (Date) => {
      if( Date !== null) {
        return Date.substring(0, 4) + '.' + Date.substring(4, 6) + '.' + Date.substring(6, 8)
      }
      return
   }

   const comment_delete = (id) => {
    axios({
      method: 'delete',
      url: `${store.state.API_URL}/api/v1/teachers/notes/${id}`,
      headers: {
        Authorization: `Bearer ${store.state.token}`
      }
    })
    .then(() => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/notes/classes/${store.state.classId}/students/${store.state.student_id}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((respons) => {
        commentLine.value = respons.data.list
      })
    })
   }
    onBeforeMount(() => {
      getStudent()
    })

    return {
      studentInfo,
      attendancyList,
      scoreList,
      comment,
      commentLine,
      classname,
      getStudent,
      getDate,
      addComment,
      comment_delete
    }
  }
};
</script>

<style scoped>
.teacher__area {
  overflow: auto;
}
</style>