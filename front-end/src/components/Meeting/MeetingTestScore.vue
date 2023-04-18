<template>
  <div class="tab-pane fade" id="testInfo" role="tabpanel" aria-labelledby="test-info">
    <div class="course__description">
      <div>
        <div class="row">
          <div class="col-12">
            <div class="dateTable">
              <div class="tableTestBox">
                <table class="table table-borderless scoreTable" align="center">
                  <thead>
                    <tr align="center">
                      <th>시험명</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      align="center"
                      v-for="test in testList"
                      :key="test"
                      @click="getDetailScore(test.test_info_id)"
                    >
                      <td class="aa">{{ test.test_info_name }}</td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <div class="tableBox">
                <table class="table table-borderless scoreTable" align="center">
                  <thead>
                    <tr align="center">
                      <th>점수</th>
                      <th>평균</th>
                      <th>석차</th>
                    </tr>
                  </thead>

                  <tbody>
                    <tr align="center" v-if="isclicked">
                      <td class="aaa">{{ score.score }}점</td>
                      <td class="aaa">{{ score.avg }}점</td>
                      <td class="aaa">{{ score.rank }}/{{ score.totalCnt }}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useStore } from "vuex";
import { onBeforeMount, ref } from "vue";

export default {
  setup() {
    const store = useStore();

    const isclicked = ref(false);
    const testList = ref([]);
    const score = ref("");

    const meetingData = store.state.meetingData;

    function getScoreList() {
      isclicked.value = false;
      axios({
        method: "get",
        url: `${store.state.API_URL}/api/v1/parents/classes/${meetingData.class_id}/students/${meetingData.student_id}`,
        header: {
          Authorization: `Bearer ${store.state.token}`,
        },
      })
        .then((res) => {
          testList.value = res.data.list
        })
        .catch((err) => {
          console.log(err)
        })
    }

    function getDetailScore(id) {
      isclicked.value = true;

      axios({
        method: "get",
        url: `${store.state.API_URL}/api/v1/parents/students/${meetingData.student_id}/tests/${id}`,
        header: {
          Authorization: `Bearer ${store.state.token}`,
        },
      })
        .then((res) => {
          score.value = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    }

    onBeforeMount(() => {
      getScoreList();
    });

    return {
      getScoreList,
      getDetailScore,

      isclicked,
      testList,
      score,
    };
  },
};
</script>

<style scoped>
.tableBox {
  height: 270px;
  width: 50%;
  float: left;
  border: 1px solid rgba(128, 128, 128, 0.479);
}

.tableTestBox {
  height: 270px;
  width: 50%;
  float: left;
  overflow-y: scroll;
  border: 1px solid rgba(128, 128, 128, 0.479);
}
.tableTestBox::-webkit-scrollbar {
  width: 4px;
}
.tableTestBox::-webkit-scrollbar-thumb {
  background-color: #ffffff;
  border-radius: 10px;
  background-clip: padding-box;
  border: 2px solid transparent;
}
.tableTestBox::-webkit-scrollbar-track {
  background-color: rgb(204, 204, 204);
  border-radius: 10px;
}

.scoreTable th {
  position: sticky;
  top: 0px;
  background-color: #e5e5e5;
  color: rgb(46, 46, 46);
  border-right: 1px solid rgba(128, 128, 128, 0.479);
}

.aaa {
  border-right: 1px solid rgba(128, 128, 128, 0.479);
  height: 227px;
  font-size: 19px;
  vertical-align: middle;
}

.aa {
  border-right: 1px solid rgba(128, 128, 128, 0.479);
  height: 50px;
  font-size: 19px;
  vertical-align: middle;
  cursor: pointer;
}
</style>
