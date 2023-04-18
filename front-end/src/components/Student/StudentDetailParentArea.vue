<template>
<section class="teacher__area pt-100 pb-110">
   <div class="container">
      <div class="row">
         <div class="col-xxl-8 col-xl-8 col-lg-8">
            <div class="teacher__wrapper">
               <div class="teacher__top d-md-flex align-items-end justify-content-between">
                  <div class="teacher__info">
                    <h4>{{aca_name}} {{class_name}}</h4>
                    <h4>{{ studentInfo.student_name }} 포트폴리오</h4>
                    <router-link :to="{ name: 'ParentClass', params:{ class_id: classid } }"><button>반 보기</button></router-link>
                  </div>
               </div>
     
               <section class="page__title-area pb-90">
                <div class="container">
                  <div class="row">
                    <div>
                      <div class="course__wrapper">
                        <div class="course__tab-2 mb-45">
                          <ul class="nav nav-tabs" id="courseTab" role="tablist">
                            <li class="nav-item-2" role="presentation">
                              <button
                                class="nav-link active"
                                id="description-tab"
                                data-bs-toggle="tab"
                                data-bs-target="#description"
                                type="button"
                                role="tab"
                                aria-controls="description"
                                aria-selected="true"
                                @click="isClicked('attend')"
                              >
                                <i class="icon_ribbon_alt"></i> <span>출결</span>
                              </button>
                            </li>
                            <li class="nav-item-2" role="presentation">
                              <button
                                class="nav-link"
                                id="curriculum-tab"
                                data-bs-toggle="tab"
                                data-bs-target="#curriculum"
                                type="button"
                                role="tab"
                                aria-controls="curriculum"
                                aria-selected="false"
                                @click="isClicked('progress')"
                              >
                                <i class="icon_star_alt"></i> <span>진도</span>
                              </button>
                            </li>
                            <li class="nav-item-2" role="presentation">
                              <button
                                class="nav-link"
                                id="test-info"
                                data-bs-toggle="tab"
                                data-bs-target="#testInfo"
                                type="button"
                                role="tab"
                                aria-controls="test-info"
                                aria-selected="false"
                                @click="isClicked('score')"
                              >
                                <span>특이사항</span>
                              </button>
                            </li>
                          </ul>
                        </div>

                        <!-- 출결 탭 시작 -->
                        <div class="course__tab-content mb-95">
                          <div class="tab-content" id="courseTabContent">
                            <div
                              class="tab-pane fade show active"
                              id="description"
                              role="tabpanel"
                              aria-labelledby="description-tab"
                            >
                              <div class="course__description">
                                  <div class="dateTable" v-if="isClick==='attend'" >
                                    <CalendarAllAttendancyArea :attendList="attendList" v-if="attendList"/>
                                  </div>
                              </div>
                            </div>

                            <!-- CurriculumTab start -->
                            <StudentDetailScore v-if="isClick==='progress'" />
                            <!-- CurriculumTab end -->

                            <!-- TestInfoListArea start -->
                            <StudentDetailNote  v-if="isClick==='score'"/>
                            <!-- TestInfoListArea end -->
                          
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </section>           
         </div>
      </div>
   </div>
   </div>
</section>
</template>

<script>
import StudentDetailScore from "@/components/Student/StudentDetailScore.vue"
import StudentDetailNote from "@/components/Student/StudentDetailNote.vue"
import CalendarAllAttendancyArea from "../Calendar/CalendarAllAttendancyArea.vue";
import { useStore } from 'vuex'
import axios from 'axios'
import { onBeforeMount, ref } from 'vue'
import { useRoute } from 'vue-router'

export default {
   name:'StudentDetail',
   components: { 
    CalendarAllAttendancyArea,
    StudentDetailScore,
    StudentDetailNote
  },
   setup() {
    const route = useRoute()
    const aca_name = {route}.route.params.aca_name
    const class_name = {route}.route.params.class_name
    const store = useStore()
    const studentInfo = ref('')
    const attendList = ref('')
    const classid = store.state.classId
    const isClick = ref('')

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
          url: `${store.state.API_URL}/api/v1/parents/classes/${store.state.classId}/attendancies/students/${store.state.student_id}`,
          headers: {
            Authorization: `Bearer ${store.state.token}`
          }
        })
        .then((response) => {
          attendList.value = response.data.list
          console.log(attendList.value)
          })
      })
    }

    function isClicked(btn) {
      isClick.value = btn
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
      classid,
      isClicked,
      isClick
    }
   }
};
</script>

<style scoped>
.register {
  width: 55px;
  height: 35px;
  float: right;
  margin-bottom: 15px;
  border-radius: 8%;
  background-color: #0e1133;
  color: white;
}
.attendancyCheck {
  width: 13%;
}
.tableScroll {
  overflow-y: scroll;
}
.dateTable {
  /* float: left; */
  display: inline;
}
.course__wrapper {
  /* border: 1px solid black; */
  /* background-color: rgb(209, 209, 209); */
}
</style>