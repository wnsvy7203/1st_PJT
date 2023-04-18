<template>
  <div class="tab-pane fade" id="significant" role="tabpanel" aria-labelledby="significant-tab">
    <div class="course__member-item mt-30">
      <div class="row align-items-center">
        <ul aria-labelledby="significant" v-if="userCode !== 'UP'">
          <li v-for="comment of commentLine" :key="comment">
            <span class="col-8">
              {{ comment.note_content }}
            </span>
            <button class="btn btn-outline-danger" @click="deleteNote(comment.note_id)">-</button>
          </li>
        </ul>
        <ul v-else>
          <li v-for="comment of commentLine" :key="comment">{{ comment.note_content }}></li>
        </ul>
      </div>
    </div>
    
    <div class="input-group mb-3" v-if="userCode !== 'UP'">
      <span class="input-group-text" id="basic-addon1"></span>
      <input id="input-data" @keyup.enter="addNote" v-model="note" type="text" class="form-control" placeholder="특이사항을 입력해주세요." aria-label="Username" aria-describedby="basic-addon1" required>
      <button class="btn btn-outline-dark" @click="addNote">+</button>
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

    const addNote = () => {
      if (note.value) {
        axios({
          method: 'post',
          url: `${store.state.API_URL}/api/v1/teachers/notes/classes/${classid}/students/${store.state.student_id}`,
          data: {
            content: note.value
          }
        })
          .then(() => {
            note.value = ''
            getChild()
          })
      } else {
        alert('특이사항을 입력해주세요.')
      }
    }

    const deleteNote = (id) => {
      axios({
        method: 'delete',
        url: `${store.state.API_URL}/api/v1/teachers/notes/${id}`,
      })
        .then(() => {
          getChild()
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
      addNote,
      note,
      deleteNote,
    }
   }
}
</script>

<style>

</style>