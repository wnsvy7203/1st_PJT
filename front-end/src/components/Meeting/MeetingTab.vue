<template>
  <section class="page__title-area pb-90">
    <div class="container">
      <div class="row">
        <div>
          <div class="course__wrapper">
            <div class="course__tab-2 mb-45">
              <h3 class="pt-20 pb-10">
                {{ meetingData.academy_name }} {{ meetingData.student_name }}
              </h3>
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
                  >
                    <i class="icon_ribbon_alt"></i> <span>성적 조회</span>
                  </button>
                </li>
                <li class="nav-item-2" role="presentation">
                  <button
                    class="nav-link"
                    id="significant-tab"
                    data-bs-toggle="tab"
                    data-bs-target="#significant"
                    type="button"
                    role="tab"
                    aria-controls="significant"
                    aria-selected="false"
                  >
                    <i class="icon_ribbon_alt"></i> <span>특이사항</span>
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
                    <div>
                      <div class="dateTable">
                        <CalendarAttendancyArea />
                      </div>
                    </div>
                  </div>
                </div>

                <!-- CurriculumTab start -->
                <MeetingProgress />
                <!-- CurriculumTab end -->

                <!-- TestInfoListArea start -->
                <MeetingTestScore />
                <!-- TestInfoListArea end -->

                <MeetingNote />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import MeetingProgress from "@/components/Meeting/MeetingProgress.vue";
import MeetingTestScore from "@/components/Meeting/MeetingTestScore.vue";
import CalendarAttendancyArea from "../Calendar/CalendarAttendancyArea.vue";
import MeetingNote from "@/components/Meeting/MeetingNote.vue";

import { useStore } from "vuex";

export default {
  name: "CourseDetailsArea",
  components: {
    MeetingProgress,
    MeetingTestScore,
    CalendarAttendancyArea,
    MeetingNote,
  },
  setup() {
    const store = useStore();

    const meetingData = store.state.meetingData;

    return {
      meetingData,
    };
  },
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
</style>
