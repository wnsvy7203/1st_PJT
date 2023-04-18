<template>
  <div class="tab-pane fade" id="significant" role="tabpanel" aria-labelledby="significant-tab">
    <div class="course__member-item mt-30">
      <div class="row align-items-center">
        <ul>
          <li v-for="comment of commentLine" :key="comment">{{ comment.note_content }}></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex'
import axios from 'axios'
import { onBeforeMount, ref } from 'vue'

export default {
  setup() {
    const store = useStore()
    const studentInfo = ref('')
    const commentLine = ref('')
    const classid = store.state.classId
    const note = ref('')

    const userCode = store.state.profile.userCode

    const getChild = () => {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/notes/classes/${store.state.classId}/students/${store.state.student_id}`,
        headers: {
        Authorization: `Bearer ${store.state.token}`
        }
      })
        .then((res) => {
          commentLine.value = res.data.list
          console.log(commentLine.value, '특이사항')
        })
    }
    
    onBeforeMount(() => {
      getChild()
    })
    
    return {
      getChild,
      studentInfo,
      commentLine,
      classid,
      userCode,
      note,
    }
  }
}
</script>

<style>

</style>