<template>
  <Header header__white="header__white" v-if="userCode === 'UM'" />
  <CreateClassArea />
  <Footer footerPadding="true" />

  <!-- <div>
    <span>반 이름:&nbsp;</span>
    <span><input v-model="ClassInfo.className" type="text" placeholder="반 이름을 입력해주세요"></span>
  </div>
  <div v-if="teacher_list">
    <span>강사 이름: &nbsp;</span>
    <span>
      <select v-model="teacher">
        <option v-for="person of teacher_list" :key="person" :value="person.teacherSeq">{{ person.teacherName }}</option>
      </select>
    </span>
  </div>
  <div>
    <span>학년: &nbsp;</span>
    <span>
      <select v-model="ClassInfo.gradeCode">
        <option value="">--선택--</option>
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
    </span>
  </div>
  <div>
    <span>과목: &nbsp;</span>
    <span>
      <select v-model="ClassInfo.subjectCode">
        <option value="">--선택--</option>
        <option value="KO">국어</option>
        <option value="EN">영어</option>
        <option value="MA">수학</option>
        <option value="SI">과학</option>
        <option value="SO">사회</option>
        <option value="CS">컴퓨터</option>
        <option value="AL">알고리즘</option>
      </select>
    </span>
  </div>
  <div>
    <form @submit.prevent="addSchedule">
      <div>
        <span>수업시간 :</span>
        <span><select v-model="selectedDay">
          <option v-for="day in days" :key="day">{{ day }}</option>
        </select>
        </span>
        <span><input type="number" v-model.number="startTimeHour"></span>
        <span>:</span>
        <span><input type="number" v-model.number="startTimeMinute"></span>
        <span>~</span>
        <span><input type="number" v-model.number="endTimeHour"></span>
        <span>:</span>
        <span><input type="number" v-model.number="endTimeMinute"></span>
        <span><button type="submit">Add Schedule</button></span>
      </div>
    </form>
    <ul>
      <li v-for="schedule in schedules" :key="schedule.day">
        <p>{{ schedule.day }} {{ schedule.startTimeHour }} : {{ schedule.startTimeMinute }}  ~ {{ schedule.endTimeHour }} : {{ schedule.endTimeMinute }}</p>
      </li>
    </ul>
  </div>
  <button @click="makeclass">반 생성</button> -->
</template>

<script>
// import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
// import axios from "axios";
// import { reactive, ref, onBeforeMount } from "vue";

import Header from "../components/HomeTwo/Header.vue";
import CreateClassArea from "../components/Master/CreateClassArea.vue";
import Footer from "../components/Home/Footer.vue";

export default {
  name: "MakeClass",

  components: {
    Header,
    CreateClassArea,
    Footer,
  },

  setup() {
    const store = useStore();
    const userCode = store.state.profile.userCode;
    return {
      userCode,
    };
  },

  // setup() {
  //   const route = useRoute();
  //   const router = useRouter();
  //   const store = useStore();
  //   const academyId = { route }.route.params.academy_id;
  //   const teacher_list = ref({});
  //   const teacher = ref("");
  //   const days = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  //   const selectedDay = ref("");
  //   const startTimeHour = ref(0);
  //   const startTimeMinute = ref(0);
  //   const endTimeHour = ref(0);
  //   const endTimeMinute = ref(0);
  //   const schedules = ref([]);
  //   const ClassInfo = reactive({
  //     className: "",
  //     subjectCode: "",
  //     gradeCode: "",
  //     day: [],
  //     startTimeHour: [],
  //     startTimeMinute: [],
  //     endTimeHour: [],
  //     endTimeMinute: [],
  //   });

  //   // 강사 목록 받아오기
  //   function getTeachers() {
  //     axios({
  //       method: "get",
  //       url: `${store.state.API_URL}/api/v1/masters/academies/${academyId}/teachers`,
  //     }).then((res) => {
  //       teacher_list.value = res.data.list;
  //     });
  //   }

  //   // 스케쥴 1개 추가
  //   const addSchedule = () => {
  //     // 스케쥴에 우선 추가해서 makeclass 함수를 통해 서버로 보낼 예정
  //     schedules.value.push({
  //       day: selectedDay.value,
  //       startTimeHour: startTimeHour.value,
  //       startTimeMinute: startTimeMinute.value,
  //       endTimeHour: endTimeHour.value,
  //       endTimeMinute: endTimeMinute.value,
  //     });

  //     // 값 입력 후 초기화
  //     selectedDay.value = "";
  //     startTimeHour.value = 0;
  //     startTimeMinute.value = 0;
  //     endTimeHour.value = 0;
  //     endTimeMinute.value = 0;
  //   };
  //   const makeclass = () => {
  //     // classinfo에 모두 담기
  //     for (let sc of schedules.value) {
  //       ClassInfo.day.push(sc.day);
  //       ClassInfo.startTimeHour.push(sc.startTimeHour);
  //       ClassInfo.startTimeMinute.push(sc.startTimeMinute);
  //       ClassInfo.endTimeHour.push(sc.endTimeHour);
  //       ClassInfo.endTimeMinute.push(sc.endTimeMinute);
  //     }
  //     axios({
  //       method: "post",
  //       url: `${store.state.API_URL}/api/v1/masters/classes/academies/${academyId}/teachers/${teacher.value}`,
  //       data: {
  //         className: ClassInfo.className,
  //         subjectCode: ClassInfo.subjectCode,
  //         gradeCode: ClassInfo.gradeCode,
  //         day: ClassInfo.day,
  //         startTimeHour: ClassInfo.startTimeHour,
  //         startTimeMinute: ClassInfo.startTimeMinute,
  //         endTimeHour: ClassInfo.endTimeHour,
  //         endTimeMinute: ClassInfo.endTimeMinute,
  //       },
  //     }).then(() => {
  //       router.go(-1);
  //     });
  //   };
  //   onBeforeMount(() => {
  //     getTeachers();
  //   });
  //   return {
  //     days,
  //     selectedDay,
  //     startTimeHour,
  //     startTimeMinute,
  //     endTimeHour,
  //     endTimeMinute,
  //     schedules,
  //     addSchedule,
  //     getTeachers,
  //     makeclass,
  //     ClassInfo,
  //     teacher,
  //     teacher_list,
  //   };
  // },
};
</script>

<style></style>
