<template>
    <form @submit.prevent="sendinfo">
      <div>
        <div class="child_name">
          <div>이름</div>
          <input type="text" id="name" v-model.trim="child.student_name">
        </div>
        <div class="child_phonenumber">
          <div>전화번호</div>
          <input type="text" id="phonenumber" v-model.trim="child.student_phone_number">
        </div>
        <div class="child_age">
          <div>나이</div>
          <input type="text" id="age" v-model.trim="child.student_age">
        </div>
        <div class="child_school">
          <div>학교</div>
          <input type="text" id="name" v-model.trim="child.student_school">
        </div>
        <div class="child_grade">
          <div>학년</div>
          <select name="" id="" v-model.trim="child.grade_code">
            <option value="E1">초1</option>
            <option value="E2">초2</option>
            <option value="E3">초3</option>
            <option value="E4">초4</option>
            <option value="E5">초5</option>
            <option value="E6">초6</option>
            <option value="M1">중1</option>
            <option value="M2">중2</option>
            <option value="M3">중3</option>
            <option value="H1">고1</option>
            <option value="H2">고2</option>
            <option value="H3">고3</option>
          </select>
        </div>
      </div>
      <button type="submit" class="">수정</button>
    </form>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { ref } from '@vue/reactivity'
import { onBeforeMount } from '@vue/runtime-core'

export default {
  name: 'StudentInfoUpdate',
  setup() {
    const store = useStore()
    const child = ref('')

    function getChild() {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/parents/students/${store.state.student_id}`
      })
        .then((res) => {
          child.value = res.data.studentInfo
          console.log(child.value)
        })
        .catch((err) => {
          console.log(err)
        })
    }
    function sendinfo() {
      axios({
        method:'put',
        url: `${store.state.API_URL}/api/v1/parents/students/${store.state.student_id}`,
        data: {
            name: child.value.student_name,
            phoneNumber: child.value.student_phone_number,
            age : child.value.student_age,
            school : child.value.student_school,
            gradeCode: child.value.grade_code
        },
      })
      .then(() => {
        alert('자녀 정보가 수정되었습니다')
      })
      .catch((err) => {
        console.log(err)
      })
    }
    onBeforeMount(() => {
      getChild()
    })
    return{
      sendinfo,
      child
    }
  }

}
</script>

<style>

</style>