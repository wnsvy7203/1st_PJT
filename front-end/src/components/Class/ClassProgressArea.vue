<template>
  <div class="tab-pane fade" id="curriculum" role="tabpanel" aria-labelledby="curriculum-tab">
    <div class="course__curriculum">
      <div v-for="( progress, idx ) in progressList" :key="idx">
        <div class="accordion" id="course__accordion">
          <div class="accordion-item mb-50">
            <h2 class="accordion-header" id="week-01"> 
              <button
                class="accordion-button"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#week-01-content"
                aria-expanded="true"
                aria-controls="week-01-content"
              >
                Week {{ progress.week }}
              </button>
            </h2>
            <div
              id="week-01-content"
              class="accordion-collapse collapse show"
              aria-labelledby="week-01"
              data-bs-parent="#course__accordion"
            >
              <div class="accordion-body">
                <div class="course__curriculum-content d-sm-flex justify-content-between align-items-center">
                  <div class="course__curriculum-info">
                    <svg class="document" viewBox="0 0 24 24">
                      <path
                      class="st0"
                      d="M14,2H6C4.9,2,4,2.9,4,4v16c0,1.1,0.9,2,2,2h12c1.1,0,2-0.9,2-2V8L14,2z"
                      />
                      <polyline class="st0" points="14,2 14,8 20,8 " />
                      <line class="st0" x1="16" y1="13" x2="8" y2="13" />
                      <line class="st0" x1="16" y1="17" x2="8" y2="17" />
                      <polyline class="st0" points="10,9 9,9 8,9 " />
                    </svg>
                    <h3><span>{{ progress.content }}&nbsp;&nbsp;&nbsp;</span></h3>
                    <!-- <img src="@/assets/img/plus-minus/icon-minus.png" alt="minus" class="icon_minus" style="cursor: pointer;" @click='deleteProgress(progress.id)'> -->
                  </div>
                  <i class="far fa-trash-alt trash-icon" style="cursor: pointer;" @click='deleteProgress(progress.id)'></i>
                </div>
              </div>
            </div>
          </div>
        </div>     
      </div>
      <div class="accordion" id="course__accordion-2">
        <div class="accordion-item mb-50">
          <h2 class="accordion-header" id="week-02">
            <button
              class="accordion-button"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#week-02-content"
              aria-expanded="true"
              aria-controls="week-02-content"
            >
              진도 등록
            </button>
          </h2>
          <div
            id="week-02-content"
            class="accordion-collapse collapse show"
            aria-labelledby="week-02"
            data-bs-parent="#course__accordion-2"
          >
            <div class="accordion-body">
              <div class="course__curriculum-content d-flex justify-content-between align-items-center">
                <div class="course__curriculum-info input-group">
                    <span class="input-group-text" id="basic-addon1"></span>
                    <input id="input-data" @keyup.enter="addProgress" v-model.trim="progressContent" type="text" class="form-control" placeholder="진도 사항을 입력해주세요." aria-label="Username" aria-describedby="basic-addon1" required>
                    <button class="btn btn-outline-dark" @click="addProgress">+</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { ref, onBeforeMount } from 'vue'
import { useStore } from 'vuex'

export default {
  name: "classProgressArea",
  setup() {
    const store = useStore()

    const progressList = ref([])
    const progressContent = ref('')

    function getProgress() {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}/progresses`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
        .then((res) => {
          progressList.value = res.data.list
        })
    }

    function addProgress() {
      axios({
        method: 'post',
        url: `${store.state.API_URL}/api/v1/teachers/classes/${store.state.classId}/progresses`,
        data: {
          "week": progressList.value.length + 1,
          "content": progressContent.value
        },
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
        .then(() => {
          getProgress()
          progressContent.value = ''
        })
    }

    function deleteProgress(id) {
      axios({
        method: 'delete',
        url: `${store.state.API_URL}/api/v1/teachers/classes/progresses/${id}`,
        data: {
          "classId": store.state.classId,
          "progressId": id
        },
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
        .then(() => {
          getProgress()
        })
    }

    onBeforeMount(() => {
      getProgress()
    })

    return {
      progressList,
      progressContent,

      addProgress,
      deleteProgress,
    }
  }
}
</script>

<style>
.icon_plus {
  height: 25px;
  border: 2px solid;
  border-radius: 50%
}
.icon_minus {
  height: 20px;
  border: 2px solid;
  border-radius: 50%
}
</style>