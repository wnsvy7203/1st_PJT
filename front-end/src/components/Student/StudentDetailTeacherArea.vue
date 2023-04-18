<template>
<section class="teacher__area pt-100 pb-110">
   <div class="container">
      <div class="row">
         <div class="col-xxl-4 col-xl-4 col-lg-4 col-md-6">
            <div class="teacher__details-thumb p-relative w-img pr-30">
               <img src="../../assets/img/teacher/details/teacer-details-1.jpg" alt="">
            </div>
         </div>
         <div class="col-xxl-8 col-xl-8 col-lg-8">
            <div class="teacher__wrapper">
               <div class="teacher__top d-md-flex align-items-end justify-content-between">
                  <div class="teacher__info">
                    <h2>{{ studentInfo.student_name }}</h2>
                    <h3> {{ classname }} 반</h3>
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
                    <table>
                      <thead>
                        <tr>
                          <th>일시</th>
                          <th>시험명</th>
                          <th>점수</th>
                          <th>평균</th>
                          <th>석차</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td>2020.01.01</td>
                          <td>수학 중간고사</td>
                          <td>100점</td>
                          <td>89.5점</td>
                          <td>1/56</td>
                        </tr>
                        <tr>
                          <td>2020.03.01</td>
                          <td>수학 기말고사</td>
                          <td>90점</td>
                          <td>69.7점</td>
                          <td>5/56</td>
                        </tr>
                        <tr>
                          <td>2019.01.01</td>
                          <td>수학 월말평가</td>
                          <td>100점</td>
                          <td>50.5점</td>
                          <td>1/56</td>
                        </tr>
                        <tr>
                          <td>2019.05.01</td>
                          <td>수학 배치고사</td>
                          <td>80점</td>
                          <td>70.1점</td>
                          <td>20/56</td>
                        </tr>
                      </tbody>
                    </table>
                  <br>
               </div>
               <div class="section__title-wrapper mb-30">
                <br>
                     <h1>특이사항</h1>
                  </div>
                  <ul class="note-container">
                    <li><i class="fas fa-comments"></i><span class="note-content">요리를 매우 잘함 특히 돈까스를 너무 잘 튀김</span></li>
                    <li><i class="fas fa-comments"></i><span class="note-content">요리를 매우 잘함 특히 돈까스를 너무 잘 튀김</span></li>
                    <li><i class="fas fa-comments"></i><span class="note-content">요리를 매우 잘함 특히 돈까스를 너무 잘 튀김</span></li>
                    <li><i class="fas fa-comments"></i><span class="note-content">요리를 매우 잘함 특히 돈까스를 너무 잘 튀김</span></li>
                    <li><i class="fas fa-comments"></i><span class="note-content">요리를 매우 잘함 특히 돈까스를 너무 잘 튀김</span></li>
                    <li><i class="fas fa-comments"></i><span class="note-content">요리를 매우 잘함 특히 돈까스를 너무 잘 튀김</span></li>
                    <li><i class="fas fa-comments"></i><span class="note-content">요리를 매우 잘함 특히 돈까스를 너무 잘 튀김</span></li>
                    <li><i class="fas fa-comments"></i><span class="note-content">요리를 매우 잘함 특히 돈까스를 너무 잘 튀김</span></li>
                    <li v-for="comment of commentLine" :key="comment">{{ comment.note_content }}<span @click="comment_delete(comment.note_id)">&nbsp;&nbsp;<img src="../../assets/img/plus-minus/icon-delete.png" width=15 height=15 style="cursor: pointer;"></span></li>
                  </ul>
                  <div class="col-xxl-6 col-xl-6 col-md-6">
                    <div class="contact__form-input aa">
                      <input id="content" v-model ="comment" type="text" placeholder="특이사항을 입력해주세요" v-on:keyup.enter="addComment">
                    </div>
                  </div>
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
      console.log('hi')
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
table {
  border: 1px #a39485 solid;
  font-size: 0.9em;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
  margin-top: 55px;
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

.note-content {
  margin-left: 15px;
}

.note-container {
  margin-left: 30px;
}

.aa {
  margin-top: 20px;
  margin-left: 20px;
}
</style>