<template>
  <div class="class-academy-container">
    <h2> {{ name }}의 반 목록</h2>
    <hr>
    <div class="container">
      <div class="row row-cols-3" v-if="classesList">
        <div class="col" v-for="clas of classesList" :key="clas">
          <router-link :to="{name: 'ParentClass', params: { class_id: clas.clas_id } } "><i class="fas fa-5x fa-chalkboard-teacher" @click="saveClass(clas.clas_id)"></i>
          <div class="class-name" @click="saveClass(clas.clas_id)">{{clas.academy_name}} {{ clas.class_name }}</div>
          <button class="btn-hover color-8" @click="saveClass(clas.clas_id)">보기</button></router-link>
        </div>
      </div>
      <div v-if="classesList.length < 1"> 등록된 반이 없습니다 </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {useStore} from 'vuex'
import {ref, onBeforeMount} from 'vue'
export default {
  name:'AcademyClassListArea',
  setup() {
    const store = useStore()
    const classesList = ref([])
    const name = ref('')
    const academy_info = ref('')
    const student_id = store.state.student_id
    const academy_name = store.state.academyname
    const getStudent = () => {
      axios ({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/teachers/students/${student_id}`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
      })
      .then((res) => {
        console.log('hi')
        name.value = res.data.studentInfo.student_name
        axios ({
        method: 'get',
        url: `${store.state.API_URL}/api/v1/parents/students/${student_id}/classes`,
        headers: {
          Authorization: `Bearer ${store.state.token}`
        }
        })
        .then((res) => {
          for (let clas of res.data.list) {
            if (clas.academy_name === academy_name) {
              classesList.value.push(clas)
            }
          }
          console.log(classesList.value)
        })
        console.log('hi2')
      })
    }

    const saveClass = (id) => {
      store.dispatch('saveClassid', id)
    }

    onBeforeMount(() => {
      getStudent()
    })
    return {
      classesList,
      name,
      academy_info,
      getStudent,
      saveClass
    }
  },
}
</script>

<style scoped>
.class-academy-container{
  margin-left: 40px;
  margin-right: 40px;
  margin-top: 40px;
  position: relative;
}

.row {
  margin-top: 20px;
  margin-bottom: 20px;
}

h2 {
  text-align: center;
  padding-top: 20px;
  padding-bottom: 20px;
}

.col {
  text-align: center;
  height: 250px;
  padding-top: 30px;
  padding-bottom: 30px;
}

.class-name {
  margin-top: 25px;
  font-weight: 600;
}

.class-detail-btn {
  padding-top: 5px;
  background-color: #FF8F21;
}

.btn-hover {
    width: 200px;
    font-size: 16px;
    font-weight: 600;
    color: #fff;
    cursor: pointer;
    margin: 20px;
    height: 55px;
    text-align:center;
    border: none;
    background-size: 300% 100%;

    border-radius: 50px;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;
}

.btn-hover:hover {
    background-position: 100% 0;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;
}

.btn-hover:focus {
    outline: none;
}

.btn-hover.color-8 {
    background-image: linear-gradient(to right, #29323c, #485563, #2b5876, #4e4376);
    box-shadow: 0 4px 15px 0 rgba(45, 54, 65, 0.75);
    width: 100px;
    height: 35px;
}

.btn {
  border: none;
  display: block;
  text-align: center;
  cursor: pointer;
  text-transform: uppercase;
  outline: none;
  overflow: hidden;
  position: relative;
  color: black;
  font-weight: 700;
  font-size: 15px;
  background-color: rgba(69, 61, 67, 0);
  padding: 17px 60px;
  margin: 0 auto;
  box-shadow: 0 5px 15px rgba(0,0,0,0.20);
  position: absolute;
  right: 170px;
  top: 10px;
}

.btn span {
  position: relative; 
  z-index: 1;
}

.btn:after {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  height: 490%;
  width: 140%;
  background: #FF8F21;
  -webkit-transition: all .5s ease-in-out;
  transition: all .5s ease-in-out;
  -webkit-transform: translateX(-98%) translateY(-25%) rotate(45deg);
  transform: translateX(-98%) translateY(-25%) rotate(45deg);
}

.btn:hover:after {
  -webkit-transform: translateX(-9%) translateY(-25%) rotate(45deg);
  transform: translateX(-9%) translateY(-25%) rotate(45deg);
}

</style>
