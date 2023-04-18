<template>
    <vue-cal
        small
        :time="false"
        hide-view-selector
        active-view="week"
        :disable-views="['years', 'year', 'month']"
        :selected-date="selectedDate"
        class="vuecal--blue-theme"
        style="max-width: 360px;height: 260px"
        :events="events">
    </vue-cal>
</template>

<script>
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import { onBeforeMount, ref } from 'vue'

export default {
  name: 'CalendarAttend',
  components: { VueCal },
  props: ['attendList'],
  setup(props) {
    const selectedDate =  ref('')
    const events = ref([])

    const datereturn = (word) => {
      return word.substring(0,4) + '-' + word.substring(4,6) + '-' + word.substring(6,8) 
    }

    const getCode = code => attendancy.get(code)
    const attendancy = new Map ([ ["AA", "출석"],
    ["AB", "결석"],
    ["AT", "지각"],
    ["AE", "조퇴"],
    ["AO", "외출"]])

    const pushEvent = () => {
      for (let attend of props.attendList) {
        events.value.push({
          start: datereturn(attend.attendancyDate),
          end: datereturn(attend.attendancyDate),
          title: getCode(attend.attendancyCode)
        })
      }
    }

    onBeforeMount(() => {
      pushEvent()
    })
    return {
      selectedDate,
      events
    }
  },
}
</script>


<style scoped>

</style>