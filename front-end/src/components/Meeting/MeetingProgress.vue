<template>
  <div class="tab-pane fade" id="curriculum" role="tabpanel" aria-labelledby="curriculum-tab">
    <div class="course__curriculum">

      <div v-for="progress in progressList" :key="progress.id">
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
                <div
                  class="course__curriculum-content d-sm-flex justify-content-between align-items-center"
                >
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
                    <h3><span>{{ progress.content }}</span></h3>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div>
        <button><font-awesome-icon icon="fa-10x fa-light fa-circle-plus" /></button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { ref } from '@vue/reactivity'
import { onMounted } from '@vue/runtime-core'

export default {
  setup() {
    const store = useStore()
    const progressList = ref('')

    function getProgessList() {
      axios({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/parents/classes/${store.state.classId}/progresses`,
        header: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((res) => {
        progressList.value = res.data.list
      })
      .catch((err) => {
        console.log(err)
      })
    }

    onMounted(() => {
      getProgessList()
    })

    return{
      progressList
    }
  }
};
</script>
