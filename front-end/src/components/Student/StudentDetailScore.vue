<template>
  <table border=1>
    <thead>
      <tr>
        <td>일시</td>
        <td>시험명</td>
        <td>점수</td>
        <router-link to="/test-details"><td>상세보기</td></router-link>
      </tr>
  </thead>
  <tbody>
      <tr v-for="score of scoreList" :key="score">
        <td v-if="score.test_date">{{ getDate(score.test_date) }}</td>
        <td>{{ score.test_info_name }}</td>
        <td>{{ score.score }}</td>
      </tr>
    </tbody>
</table>
</template>

<script>
import { useStore } from 'vuex'
import axios from 'axios'
import { onBeforeMount, ref } from 'vue'
import { useRoute } from 'vue-router'

export default {
  name: 'StudentDetailScore',
  setup() {
    const route = useRoute()
    const aca_name = {route}.route.params.aca_name
    const class_name = {route}.route.params.class_name
    const store = useStore()
    const studentInfo = ref('')
    const attendList = ref('')
    const commentLine = ref('')
    const scoreList = ref('')
    const classid = store.state.classId
    const getChild = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/parents/students/${store.state.student_id}`,
        header: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((res) => {
        studentInfo.value = res.data.studentInfo
        axios({
          method: 'get',
          url: `${store.state.API_URL}/api/v1/parents/classes/${store.state.classId}/students/${store.state.student_id}`,
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
        .then((respons) => {
          scoreList.value = respons.data.list
        })
      })

    }
    
    const getDate = (Date) => {
      return Date.substring(0, 4) + '.' + Date.substring(4, 6) + '.' + Date.substring(6, 8)
   }
    onBeforeMount(() => {
      getChild()
    })
    
    return {
      getChild,
      getDate,
      aca_name,
      class_name,
      studentInfo,
      attendList,
      commentLine,
      scoreList,
      classid
    }
   }
}
</script>

<style>

</style>