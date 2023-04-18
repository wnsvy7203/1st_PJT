<template>
  <div class="container pt-100 pb-145">
    <div class="con pt-50 pb-110">
      <h1>학원검색</h1>
      <div class="search-container">
        <div class="container">
          <div class="row">
            <div class="col"></div>
            <div class="col-6">
              <div class="input-group mb-3 input-container">
                <input
                  v-model="searchName"
                  type="text"
                  class="form-control"
                  placeholder="학원을 검색하세요"
                  aria-label="Recipient's username"
                  aria-describedby="button-addon2"
                  @keyup.enter="search(searchName)"
                />
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="button-addon2"
                  @click="search(searchName)"
                >
                  검색
                </button>
              </div>
            </div>
            <div class="col"></div>
          </div>
        </div>
        <div class="container">
          <div class="row">
            <div class="col-3"></div>
            <div class="col">
              <select
                class="form-select"
                aria-label="Default select example"
                v-model="sidoCode"
                id="sido"
                @change="gugunList"
              >
                <option disabled value="" hidden>시도 선택</option>
                <option v-for="sido in sidos" :key="sido.value" :value="sido.code">
                  {{ sido.name }}
                </option>
              </select>
            </div>
            <div class="col">
              <select
                class="form-select"
                aria-label="Default select example"
                v-model="gugunCode"
                id="gugun"
                @change="academyList"
              >
                <option disabled value="" hidden>구군 선택</option>
                <option v-for="gugun in guguns" :key="gugun.value" :value="gugun.gugunCode">
                  {{ gugun.gugunName }}
                </option>
              </select>
            </div>
            <div class="col-3"></div>
          </div>
        </div>
        <hr />
        <div class="result-container">
          <div class="row">
            <div class="col"></div>
            <div class="col-8">
              <table v-if="results.length > 0">
                <thead>
                  <tr>
                    <th>학원명</th>
                    <th>주소</th>
                    <th>연락처</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="result of results" :key="result" @click="goroute(result.academy_id)" style="cursor:pointer">
                    <td>{{ result.academy_name }}</td>
                    <td>{{ result.first_address }} {{ result.last_address }}</td>
                    <td>{{ makeNum(result.academy_phone_number) }}</td>
                  </tr>
                </tbody>
              </table>

              <table v-if="resultss.length > 0">
                <thead>
                  <tr>
                    <th>학원명</th>
                    <th>주소</th>
                    <th>연락처</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="result of resultss" :key="result" @click="goroute(result.id)" style="cursor:pointer">
                    <td>{{ result.name }}</td>
                    <td>{{ result.address.firstAddress }} {{ result.address.lastAddress }}</td>
                    <td>{{ makeNum(result.phoneNumber) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="col"></div>
          </div>
          <ul v-if="results.length < 1 && resultss.length < 1">
            <li>검색 결과가 없습니다</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed, onBeforeMount, ref } from "vue";
import axios from "axios";
import { useStore } from "vuex";
import {useRouter} from 'vue-router'

export default {
  name: "SearchAcademy",
  setup() {
    const sidos = ref([]);
    const sidoCode = ref("");
    const guguns = ref([]);
    const gugunCode = ref("");
    const store = useStore();
    const router = useRouter();
    const searchName = ref("");
    const academyAll = ref("");
    const results = ref("");
    const resultss = ref("");
    const selectedSido = computed(() => {
      return sidos.value.find((sido) => sido.code === sidoCode.value);
    });
    const selectedGugun = computed(() => {
      return guguns.value.find((gugun) => gugun.gugunCode === gugunCode.value);
    });
    const getAcademy = () => {
      axios({
        method: "get",
        url: `${store.state.API_URL}/api/v1/academies`,
      }).then((res) => {
        academyAll.value = res.data.list;
      });
    };
    const getSido = () => {
      axios({
        method: "get",
        url: `${store.state.API_URL}/api/v1/sido`,
      }).then((res) => {
        sidos.value = res.data.list;
      });
    };

    const gugunList = () => {
      axios({
        method: "get",
        url: `${store.state.API_URL}/api/v1/gugun/${sidoCode.value}`,
      }).then((res) => {
        guguns.value = res.data.list;
      });
    };

    const academyList = () => {
      results.value = [];
      resultss.value = [];
      axios({
        method: "post",
        url: `${store.state.API_URL}/api/v1/academies`,
        data: {
          sido: selectedSido.value.name,
          gugun: selectedGugun.value.gugunName,
        },
      }).then((res) => {
        results.value = res.data.list;
      });
    };

    const search = (word) => {
      results.value = [];
      resultss.value = [];
      if (word.length < 1) {
        return;
      }
      for (const academy of academyAll.value) {
        if (academy.name.replace(/\s/g, "").indexOf(word.replace(/\s/g, "")) !== -1) {
          resultss.value.push(academy);
        }
      }
    };

    const goroute = (academyid) => {
      router.push({ name: 'AcademyIntro', params: { academy_id: academyid } })
    }

    onBeforeMount(() => {
      getAcademy();
      getSido();
    });

    const makeNum = (num) => {
      if (!num) return;

      const match11 = num.match(/^(\d{3})(\d{4})(\d{4})$/);
      if (match11) return match11.slice(1).join('-');

      const match10_s = num.match(/^(0[2])(\d{4})(\d{4})$/);
      if (match10_s) return match10_s.slice(1).join('-');

      const match10 = num.match(/^(\d{3})(\d{3})(\d{4})$/);
      if (match10) return match10.slice(1).join('-');

      const match9 = num.match(/^(\d{2})(\d{3})(\d{4})$/);
      if (match9) return match9.slice(1).join('-');

      return num;
    }

    return {
      sidoCode,
      sidos,
      gugunCode,
      guguns,
      searchName,
      academyList,
      getAcademy,
      search,
      getSido,
      gugunList,
      makeNum,
      results,
      resultss,
      selectedSido,
      selectedGugun,
      goroute
    };
  },
};
</script>

<style scoped>
.con {
  width: 90%;
  margin-left: 5%;
  margin-right: 5%;
}
h1 {
  text-align: center;
}
.search-container {
  border: 0.5px solid #eeeeee;
  border-radius: 1px;
  margin-top: 20px;
  margin-bottom: 20px;
  background-color: #eeeeee;
  padding-top: 30px;
  text-align: center;
}
.result-container {
  overflow: hidden;
  height: auto;
  min-height: 100px;
}
.category {
  font-weight: 600;
}

table {
  border: 1px #a39485 solid;
  font-size: 0.9em;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
  margin-top: 20px;
  margin-bottom: 20px;
}

th {
  text-align: center;
}

td {
  text-align: center;
}

thead {
  font-weight: bold;
  color: #fff;
  background: #73685d;
}

td,
th {
  padding: 1em 0.5em;
  vertical-align: middle;
}

td {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  background: #fff;
}
</style>