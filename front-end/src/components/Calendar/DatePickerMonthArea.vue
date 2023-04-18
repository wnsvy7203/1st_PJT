<template>
  <vue-cal
    class="vuecal--date-picker"
    xsmall
    hide-view-selector
    :time="false"
    :transitions="false"
    active-view="month"
    :disable-views="['years', 'week', 'day','month']"
    style="width: 250px; height: 270px"
    @cell-click="handleDayClick"
  >
  </vue-cal>
</template>

<script>
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";

import { ref } from "vue";

export default {
  components: { VueCal },
  setup(props, { emit }) {
    function handleDayClick(date) {
      const picked = ref(date.toISOString().slice(0, 7).split("-"));
      const pickedMonth = picked.value[0] + picked.value[1];

      return emit("month-updated", pickedMonth);
    }

    return {
      handleDayClick,
    };
  },
};
</script>
