<template>
  <div>
    <h5>학생과 학원을 선택하세요</h5>
    <select name="selectChild" id="selectChild" v-model="student_id" @change="getClass()">
      <option v-for="child in children" :key="child" :value="child.student_id">{{ child.student_name }}</option>
    </select>
    <div v-for="item in classes" :key="item" @click="getSep(item.clas_id)">
      {{ item.academy_name }} {{ item.class_name }}
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { ref } from '@vue/reactivity'
import { onBeforeMount } from '@vue/runtime-core'

export default {
  name: 'ChildSelect',

  setup() {
    const store = useStore()
    const children = ref('')
    const student_id = ref('')
    const classes = ref('')

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

    function getClass() {
      axios({
          methods: 'get',
          url: `${store.state.API_URL}/api/v1/parents/students/${student_id.value}/classes`,
          header: {
          Authorization: `Bearer ${store.state.token}`
          }
        })
        .then((res) => {
          classes.value = res.data.list
          console.log(res.data, 'asdfasdf')
        })
    }

    function getSep(classId) {
      axios({
        methods: 'get',
        url: `${store.state.API_URL}/api/v1/meetings/classes/${classId}`,
        header: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((res) => {
        store.dispatch('saveSeq', res.data)
        console.log(res.data)
      })
    }
    onBeforeMount(() => {
      getChildren()
    })

    return {
      children,
      student_id,
      getClass,
      classes,
      getSep
    }
  }
}
</script>

<style>

</style>