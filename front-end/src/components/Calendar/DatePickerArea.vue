<template>
  <vue-cal
    class="vuecal--date-picker"
    xsmall
    hide-view-selector
    :time="false"
    :transitions="false"
    active-view="month"
    :disable-views="['week']"
    style="width: 230px; height: 250px"
    @cell-click="handleDayClick"
  >
  </vue-cal>
</template>

<script>
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";
import { ref } from 'vue'

export default {
  components: { VueCal },
  setup(props, { emit }) {
    function handleDayClick(date) {
      const picked = ref(date.toISOString().slice(0, 10).split('-'))
      const pickedDate = picked.value[0] + picked.value[1] + (parseInt(picked.value[2])+1).toString()
      return emit('date-updated', pickedDate)
    }

    return {
      handleDayClick
    }
  }
};
</script>
