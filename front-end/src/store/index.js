import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

const API_URL = 'https://i8e206.p.ssafy.io'

export default createStore({
  
  plugins: [
    createPersistedState()
  ],
  state: {
    token: null,
		myseq: null,
    student_id: null,

    classId: null,
    academyId: null,
    academyname: null,
    classInfo: [],
    classDayInfo: [],
    classStudent: [],
    studentID: [],

		API_URL: API_URL,
    profile: {},
    classes: [],
    children: null,
    selectedDate: null,
    test: {},
    meetingData: {},
    seq:{}
  },
  getters: {
  },
  mutations: {
    SAVE_USER(state, payload) {
      state.token = payload.token
      state.myseq = payload.userSeq
      state.profile = payload.profile
		},
    CLEAR_LOCAL(state) {
      state.token = null,
      state.myseq = null,
      state.profile = {},
      state.classes = []
      localStorage.clear()
    },
    SAVE_STUDENTID(state, student_id) {
      state.student_id = student_id
    },
    SAVE_PROFILE(state, profile) {
      state.profile = profile
    },
    SAVE_CLASSES(state, classes) {
      state.classes = classes
    },
    SHOW_CLASS(state, payload) {
      state.classId = payload.classId
      state.academyId = payload.academyId
      state.classInfo = payload.classInfo
      state.classDayInfo = payload.classDayInfo
      state.classStudent = payload.classStudent
      state.studentID = payload.studentID
    },
    SAVE_CLASSID(state, class_id) {
      state.classId = class_id
    },
    SAVE_CHILDREN(state, payload) {
      state.children = payload
    },
    SELECTED_DATE(state, selectedDate) {
      state.selectedDate = selectedDate
    },
    SAVE_CLASS_STUDENT(state, classStudent) {
      state.classStudent = classStudent
    },
    MEETING_DATA(state, meeting) {
      state.meetingData = meeting
    },
    SAVE_ACADEMYID(state, academyId) {
      state.academyId = academyId
    },
    SAVE_ACADEMYNAME(state, academyname) {
      state.academyname = academyname
    },
    SAVE_SEQ(state, seq) {
      state.seq = seq
    },
    SAVE_DATA(state, test) {
      state.test = test
    }
  },
  actions: {
		login(context, payload) {
      context.commit('SAVE_USER', payload)
    },
    saveProfile(context, profile) {
      context.commit('SAVE_PROFILE', profile)
    },
    saveClasses(context, classes) {
      context.commit('SAVE_CLASSES', classes)
    },
    showClass(context, payload) {
      context.commit('SHOW_CLASS', payload)
    },
    saveStudentid(context, student_id) {
      context.commit('SAVE_STUDENTID', student_id)
    },
    saveClassid(context, class_id) {
      context.commit('SAVE_CLASSID', class_id)
    },
    saveChildren(context,payload) {
      context.commit('SAVE_CHILDREN', payload)
    },
    selectedDate(context, selectedDate) {
      context.commit('SELECTED_DATE', selectedDate)
    },
    saveclassStudent(context, classStudent) {
      context.commit('SAVE_CLASS_STUDENT', classStudent)
    },
    meetingData(context, meeting) {
      context.commit('MEETING_DATA', meeting)
    },
    saveAcademyid(context, academyId) {
      context.commit('SAVE_ACADEMYID', academyId)
    },
    saveAcademyname(context, academyname) {
      context.commit('SAVE_ACADEMYNAME', academyname)
    },
    saveSeq(context, seq) {
      context.commit('SAVE_SEQ', seq)
    },
    saveData(context, test) {
      context.commit('SAVE_DATA', test)
    }
  },
  modules: {
  }
})