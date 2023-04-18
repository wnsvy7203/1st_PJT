<template>
	<div v-if="streamManager">
		<ov-video :stream-manager="streamManager"/>
	</div>
</template>

<script>
import OvVideo from './OvVideo';
import { computed } from 'vue'

export default {
	name: 'UserVideo',
	components: {
		OvVideo,
	},
  props: {
    streamManager: Object,
  },
  setup(props) {
    function getConnectionData() {
      const { connection } = props.streamManager.stream
      return JSON.parse(connection.data)
    }

    const clientData = computed(() => {
      const { clientData } = getConnectionData()
      return clientData
    })

    return {
      clientData,
    }
  }
}
</script>
