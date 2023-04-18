<template>
  <section class="signup__area po-rel-z1 pt-100 pb-145">
    <div class="sign__shape">
      <img class="man-1" src="../../assets/img/icon/sign/man-3.png" alt="" />
      <img class="man-2 man-22" src="../../assets/img/icon/sign/man-2.png" alt="" />
      <img class="circle" src="../../assets/img/icon/sign/circle.png" alt="" />
      <img class="zigzag" src="../../assets/img/icon/sign/zigzag.png" alt="" />
      <img class="dot" src="../../assets/img/icon/sign/dot.png" alt="" />
      <img class="bg" src="../../assets/img/icon/sign/sign-up.png" alt="" />
      <img class="flower" src="../../assets/img/icon/sign/flower.png" alt="" />
    </div>
    <div class="container">
      <div class="row">
        <div class="col-xxl-8 offset-xxl-2 col-xl-8 offset-xl-2">
          <div class="section__title-wrapper text-center mb-35">
            <h4 class="section__title">반 등록</h4>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xxl-6 offset-xxl-3 col-xl-6 offset-xl-3 col-lg-8 offset-lg-2">
          <div class="sign__wrapper white-bg">
            <div class="sign__form">
              <form @submit.prevent="makeclass">
                <div class="sign__input-wrapper mb-25">
                  <h5>반 이름</h5>
                  <div class="sign__input">
                    <input
                      id="name"
                      v-model="ClassInfo.className"
                      type="text"
                      placeholder="반 이름"
                      @blur="name_validate"
                    />
                    <p v-if="error.name === 1" class="err-msg">필수 입력사항입니다.</p>
                  </div>
                </div>

                <div class="sign__input-wrapper mb-25">
                  <div v-if="teacher_list">
                    <h5>강사 선택</h5>
                    <div class="sign__input" id="selectForm">
                      <select id="teacher" v-model="teacher" class="form-select hero__select mt-5"
                      aria-label="Default select example" @blur="teacher_validate">
                      <option disabled value="" hidden>강사 선택</option>
                        <option
                          v-for="person of teacher_list"
                          :key="person"
                          :value="person.teacherSeq"
                        >
                          {{ person.teacherName }}
                        </option>
                      </select>
                      <p v-if="error.teacher === 1" class="err-msg">필수 입력사항입니다.</p>
                    </div>
                  </div>
                </div>

                <div class="sign__input-wrapper mb-25">
                  <h5>학년</h5>
                  <div class="sign__input" id="selectForm">
                    <select
                      id="grade"
                      class="form-select hero__select mt-5"
                      aria-label="Default select example"
                      v-model="ClassInfo.gradeCode"
                    >
                      <option disabled value="" hidden>학년 선택</option>
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
                    <p v-if="error.subject === 1" class="err-msg">필수 입력사항입니다.</p>
                  </div>
                </div>
                <div class="sign__input-wrapper mb-25">
                  <h5>과목</h5>
                  <div class="sign__input" id="selectForm">
                    <select
                      id="subject"
                      class="form-select hero__select mt-5"
                      aria-label="Default select example"
                      v-model="ClassInfo.subjectCode"
                      @blur="subject_validate"
                    >
                      <option disabled value="" hidden>과목 선택</option>
                      <option value="KO">국어</option>
                      <option value="EN">영어</option>
                      <option value="MA">수학</option>
                      <option value="SI">과학</option>
                      <option value="SO">사회</option>
                      <option value="CS">컴퓨터</option>
                      <option value="AL">알고리즘</option>
                    </select>
                    <p v-if="error.subject === 1" class="err-msg">필수 입력사항입니다.</p>
                  </div>
                </div>

                <div class="sign__input-wrapper mb-25">
                  <form @submit.prevent="addSchedule">
                    <h5>수업시간</h5>
                    <div class="row">
                      <div class="col-2"
                        ><select id="time" class="sel" v-model="selectedDay">
                          <option disabled value="" hidden>요일</option>
                          <option v-for="day in days" :key="day">{{ day }}</option>
                        </select>
                      </div>
                      <div class="col-10">
                        <span><input class="inp" id="inp-first" type="number" v-model.number="startTimeHour" min="9" max="20"/></span>
                        <span>시</span>
                        <span><input class="inp" type="number" v-model.number="startTimeMinute" min="00" max="59"/></span>
                        <span>분 - </span>
                        <span><input class="inp" type="number" v-model.number="endTimeHour" min="9" max="21" /></span>
                        <span>시</span>
                        <span><input class="inp" type="number" v-model.number="endTimeMinute" min="00" max="59"/></span>
                        <span>분</span>
                        <span><button type="submit" class="btn"><i class="far fa-plus-circle"></i></button></span>
                      </div>
                    </div>
                  </form>
                  <p v-if="error.schedule === 1" class="err-msg">필수 입력사항입니다.</p>
                  <ul>
                    <li v-for="(schedule, index) in schedules" :key="index">
                      <div class="row schedule">
                        <div class="col-11">
                          {{ schedule.day }} {{ schedule.startTimeHour }} :
                          {{ schedule.startTimeMinute }} ~ {{ schedule.endTimeHour }} :
                          {{ schedule.endTimeMinute }}
                        </div>
                        <div class="col-1 deleteSchedule" @click="deleteSchedule(index)"><i class="fas fa-trash-alt"></i></div>
                      </div>
                    </li>
                  </ul>
                </div>

                <button class="e-btn w-100">반 등록하기</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import axios from "axios";
import { reactive, ref, onBeforeMount } from "vue";

export default {
  name: "CreateClass",

  setup() {
    const route = useRoute()
    const router = useRouter()
    const store = useStore()
    const academyId = { route }.route.params.academy_id
    const teacher_list = ref({})
    const teacher = ref("")
    const days = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
    const selectedDay = ref("")
    const startTimeHour = ref(0)
    const startTimeMinute = ref(0)
    const endTimeHour = ref(0)
    const endTimeMinute = ref(0)
    const schedules = ref([])
    const ClassInfo = reactive({
      className: "",
      subjectCode: "",
      gradeCode: "",
      day: [],
      startTimeHour: [],
      startTimeMinute: [],
      endTimeHour: [],
      endTimeMinute: [],
    });

    // 강사 목록 받아오기
    function getTeachers() {
      axios({
        method: "get",
        url: `${store.state.API_URL}/api/v1/masters/academies/${academyId}/teachers`,
      }).then((res) => {
        teacher_list.value = res.data.list;
      });
    }

    // 스케쥴 1개 추가
    const addSchedule = () => {
      if (startTimeMinute.value < 10) {
        startTimeMinute.value = `0${startTimeMinute.value}`
      }

      if (endTimeMinute.value < 10) {
        endTimeMinute.value = `0${endTimeMinute.value}`
      }
      // 스케쥴에 우선 추가해서 makeclass 함수를 통해 서버로 보낼 예정
      schedules.value.push({
        day: selectedDay.value,
        startTimeHour: startTimeHour.value,
        startTimeMinute: startTimeMinute.value,
        endTimeHour: endTimeHour.value,
        endTimeMinute: endTimeMinute.value,
      });

      // 값 입력 후 초기화
      selectedDay.value = "";
      startTimeHour.value = 0;
      startTimeMinute.value = 0;
      endTimeHour.value = 0;
      endTimeMinute.value = 0;
    };

    function deleteSchedule(idx) {
      schedules.value.splice(idx, 1);
    }

    const makeclass = () => {
      validation_check()
      // classinfo에 모두 담기
      if (Object.values(error).every((v) => v === 0)) {
        for (let sc of schedules.value) {
          ClassInfo.day.push(sc.day);
          ClassInfo.startTimeHour.push(sc.startTimeHour);
          ClassInfo.startTimeMinute.push(sc.startTimeMinute);
          ClassInfo.endTimeHour.push(sc.endTimeHour);
          ClassInfo.endTimeMinute.push(sc.endTimeMinute);
        }
        axios({
          method: "post",
          url: `${store.state.API_URL}/api/v1/masters/classes/academies/${academyId}/teachers/${teacher.value}`,
          data: {
            className: ClassInfo.className,
            subjectCode: ClassInfo.subjectCode,
            gradeCode: ClassInfo.gradeCode,
            day: ClassInfo.day,
            startTimeHour: ClassInfo.startTimeHour,
            startTimeMinute: ClassInfo.startTimeMinute,
            endTimeHour: ClassInfo.endTimeHour,
            endTimeMinute: ClassInfo.endTimeMinute,
          },
        }).then(() => {
          alert('반이 개설되었습니다')
          router.go(-1);
        })
      }
    };

    const error = reactive({
      name: 0,
      teacher: 0,
      grade: 0,
      subject: 0,
      schedule: 0
    })

    function name_validate() {
      if (!ClassInfo.className) {
        error.name = 1
      } else {
        error.name = 0
      }
    }
    
    function teacher_validate() {
      if (!teacher.value) {
        error.teacher = 1
      } else {
        error.teacher = 0
      }
    }
    
    function grade_validate() {
      if (!ClassInfo.gradeCode) {
        error.grade = 1
      } else {
        error.grade = 0
      }
    }
    
    function subject_validate() {
      if(!ClassInfo.subjectCode) {
        error.subject = 1
      } else {
        error.subject = 0
      }
    }

    function schedule_validate() {
      if (!schedules.value) {
        error.schedule = 1
      } else {
        error.schedule = 0
      }
    }

    function validation_check() {
      name_validate()
      if (error.name) {
        document.getElementById('name').focus()
        return
      }

      teacher_validate()
      if (error.teacher) {
        document.getElementById('teacher').focus()
        return
      }

      grade_validate()
      if (error.grade) {
        document.getElementById('grade').focus()
        return
      }

      subject_validate()
      if(error.subject) {
        document.getElementById('subject').focus()
        return
      }

      schedule_validate()
      if(error.schedule) {
        document.getElementById('time').focus()
        return
      }

    }

    onBeforeMount(() => {
      getTeachers();
    });

    return {
      days,
      selectedDay,
      startTimeHour,
      startTimeMinute,
      endTimeHour,
      endTimeMinute,
      schedules,
      addSchedule,
      getTeachers,
      makeclass,
      deleteSchedule,
      ClassInfo,
      teacher,
      teacher_list,

      error,

      name_validate,
      teacher_validate,
      grade_validate,
      subject_validate,
      schedule_validate
    };
  },
};
</script>

<style scoped>
.form-select {
  margin-right: 5px;
  display: inline-block;
}
#selectForm {
  width: 100%;
  display: flex;
  justify-content: center;
}
.form-select {
  border-radius: 6px;
  height: 60px;
  border: 2px solid transparent;
  background-color: #f6f6f7;
  padding: 0;
  font-size: 16px;
  color: #0e1133;
  padding: 0 20px;
}

.sel {
  margin-right: 5px;
  display: inline-block;
  border-radius: 6px;
  height: 30px;
  border: 2px solid transparent;
  background-color: #f6f6f7;
  font-size: 8px;
  color: #0e1133;
  padding: 0 20px;
  margin-top: 4px;
}

.inp {
  /* padding: 5px 10px; */
  color: rgb(64, 64, 64);
  background-color: #f6f6f7;
  border: 1px solid rgb(171, 171, 171);
  height: 30px;
  border-radius: 6px;
  width: 35px;
  font-size: 15px;
  margin-bottom: 10px;
  text-align: center;
}

#inp-first {
  margin-left: 30px;
}
.sign__input-wrapper p {
  font-size: 14px;
}
.profileInput label {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 5px 10px;
  color: rgb(64, 64, 64);
  background-color: #f6f6f7;
  border: 1px solid rgb(171, 171, 171);
  height: 45px;
  margin-left: 10px;
  border-radius: 6px;
  width: 20%;
  font-size: 15px;
}
.profileInput .upload-name {
  display: inline-block;
  height: 45px;
  padding: 0 10px;
  vertical-align: middle;
  border: 1px solid #dddddd;
  width: 80%;
  color: #999999;
  border-radius: 6px;
}
.profileInput input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}
.schedule {
  padding: 5px;
  border-bottom: 1px solid grey;
  margin: 0 7px;
}
.deleteSchedule {
  color: red;
  cursor: pointer;
  border-radius: 5px;
  /* padding-left: 80px; */
}
.sign__input input, .sign__input select {
  height: 50px;
}
#name {
  padding: 4px 0 0 22px;
}
</style>
