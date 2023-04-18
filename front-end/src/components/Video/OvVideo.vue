<template>
	<video class="video-frame" autoplay/>
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>

<style scoped>
.video-frame {
	width: 400px;
	height: 300px;
	border: 5px solid white;
	border-radius: 10px;
}
</style>