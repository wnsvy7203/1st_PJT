<template>
  <div class="box">
    <div class="con" v-if="!session">
      <div id="holder">
        <div class="button" @click="joinSession()">
          <p class="btnText">입장하기</p>
          <div class="btnTwo">
            <p class="btnText2">GO!</p>
          </div>
        </div>
        <div class="button" @click="goBack">
          <p class="btnText">나가기</p>
          <div class="btnTwo">
            <p class="btnText2">X</p>
          </div>
        </div>
      </div>
    </div>

    <div class="container text-center" v-else-if="session">
      <div class="row">
        <div class="video-container col-5">
          <user-video
            class="video-frame your-video"
            id="cameraview"
            :stream-manager="publisher"
            @click="updateMainVideoStreamManager(publisher)"
          />
          <user-video
            class="video-frame my-video"
            id="cameraview"
            :stream-manager="subscriber"
            @click="updateMainVideoStreamManager(subscriber)"
          />
        </div>
        <div class="col-7">
          <div class="portfolio-container"><MeetingTab /></div>
        </div>
      </div>
      <div class="row">
        <div class="button-container">
          <!-- 카메라가 켜져 있을 경우 -->
          <button v-if="videoState === true" @click="changeVideoStatus" id="video-controller">
            <i class="fas fa-2x fa-video"></i>
          </button>
          <!-- 카메라 꺼져 있을 경우 -->
          <button
            v-if="videoState === false"
            @click="changeVideoStatus"
            id="video-controller-no-video"
          >
            <i class="fas fa-2x fa-video-slash"></i>
          </button>
          <!-- 소리가 켜져 있을 경우 -->
          <button v-if="audioState === true" @click="changeAudioStatus" id="sound-controller">
            <i class="fas fa-2x fa-volume"></i>
          </button>
          <!-- 소리가 꺼져 있을 경우 -->
          <button
            v-if="audioState === false"
            @click="changeAudioStatus"
            id="sound-controller-no-sound"
          >
            <i class="fas fa-2x fa-volume-mute"></i>
          </button>
          <button v-if="profile.userCode === 'UP'" @click="leaveSession" id="exit-btn">
            <i class="far fa-2x fa-sign-out"></i>
          </button>
          <button
            v-else-if="profile.userCode !== 'UP'"
            @click="[leaveSession, deleteSession(mySessionId)]"
            id="exit-btn"
          >
            <i class="far fa-2x fa-sign-out"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { OpenVidu } from "openvidu-browser";
import axios from "axios";
import UserVideo from "@/components/Video/UserVideo.vue";
import MeetingTab from "@/components/Meeting/MeetingTab.vue";

import { ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "App",
  components: {
    UserVideo,
    MeetingTab,
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    const profile = store.state.profile;

    const videoState = ref(true);
    const audioState = ref(true);

    const OV = ref(undefined);
    const session = ref(undefined);
    const mainStreamManager = ref(undefined);
    const publisher = ref(undefined);
    const subscriber = ref(undefined)
    const meetingData = store.state.meetingData;

    // Join form
    const mySessionId = `Session${meetingData.meeting_id}`;
    const myUserName = store.state.myseq;

    function joinSession() {
      // OpenVidu object
      OV.value = new OpenVidu();

      // 세션 초기화
      session.value = OV.value.initSession();

      // 스트리밍 시작
      session.value.on("streamCreated", ({ stream }) => {
        subscriber.value = session.value.subscribe(stream);
      });

      // 스트리밍 종료
      session.value.on("streamDestroyed", () => {
        subscriber.value = undefined
      });

      // 비동기 예외 처리
      session.value.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      getToken(mySessionId).then((token) => {
        session.value
          .connect(token, { clientData: myUserName })
          .then(() => {
            let publisher_func = OV.value.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: true, // Whether to mirror your local video or not
            });

            mainStreamManager.value = publisher_func;
            publisher.value = publisher_func;

            session.value.publish(publisher.value);
          })
          .catch((error) => {
            console.log("There was an error connecting to the session:", error.code, error.message);
          });
      });

      window.addEventListener("beforeunload", leaveSession);
    }

    function leaveSession() {
      if (session.value) {
        session.value.disconnect();
      }

      session.value = undefined;
      mainStreamManager.value = undefined;
      publisher.value = undefined;
      subscriber.value = undefined;
      OV.value = undefined;

      window.removeEventListener("beforeunload", leaveSession);
    }

    function updateMainVideoStreamManager(stream) {
      if (mainStreamManager.value === stream) return;
      mainStreamManager.value = stream;
    }

    const getToken = async (mySessionId) => {
      const sessionId = await createSession(mySessionId);
      return await createToken(sessionId);
    };

    const createSession = async (sessionId) => {
      try {
        const response = await axios.post(
          `${store.state.API_URL}/openvidu/api/sessions`,
          { customSessionId: sessionId },
          {
            headers: {
              Authorization: `Basic ${btoa("OPENVIDUAPP:HOT6")}`,
              "Content-Type": "application/json",
            },
          }
        );
        return response.data.sessionId;
      } catch (error) {
        return sessionId;
      }
    };

    const createToken = async (sessionId) => {
      const response = await axios.post(
        `${store.state.API_URL}/openvidu/api/sessions/${sessionId}/connection`,
        {
          headers: {
            Authorization: `Basic ${btoa("OPENVIDUAPP:HOT6")}`,
            "Content-Type": "application/json",
          },
        }
      );
      return response.data.token;
    };

    const deleteSession = async (sessionId) => {
      if (profile.userCode !== "UP") {
        await axios.delete(`${store.state.API_URL}/openvidu/api/sessions/${sessionId}`, {
          headers: {
            Authorization: `Basic ${btoa("OPENVIDUAPP:HOT6")}`,
          },
        });
        window.location.reload();
      }

      axios({
        method: "put",
        url: `${store.state.API_URL}/api/v1/meetings/${meetingData.meeting_id}`,
        data: {
          meetingCode: "MC",
        },
        headers: {
          Authorization: `Bearer ${store.state.token}`,
        },
      });
    };

    function changeVideoStatus() {
      if (videoState.value === true) {
        publisher.value.publishVideo(false);
        videoState.value = false;
      } else {
        publisher.value.publishVideo(true);
        videoState.value = true;
      }
    }

    function changeAudioStatus() {
      if (audioState.value === true) {
        publisher.value.publishAudio(false);
        audioState.value = false;
      } else {
        publisher.value.publishAudio(true);
        audioState.value = true;
      }
    }

    const goBack = () => {
      if (profile.userCode === "UP") {
        router.push({ name: "parent-counsel" });
      } else {
        router.push({ name: "teacher-counsel" });
      }
    };

    return {
      OV,
      session,
      mainStreamManager,
      publisher,
      subscriber,

      updateMainVideoStreamManager,

      mySessionId,
      myUserName,

      joinSession,
      leaveSession,
      deleteSession,

      getToken,
      createSession,
      createToken,

      changeVideoStatus,
      changeAudioStatus,

      audioState,
      videoState,

      goBack,

      profile,
    };
  },
};
</script>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Roboto:700);
html,
body {
  background: #fbc02d;
  font-family: Roboto;
  height: 100%;
  width: 100%;
  margin: 0;
}

.con {
  padding-top: 400px;
}

#holder {
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  background: white;
  padding: 10px;
  width: 250px;
  margin: 0 auto;
  position: relative;
  top: 50%;
  transform: translateY(-65%);
  box-shadow: 0px 3px 8px rgba(0, 0, 0, 0.25);
  border-radius: 2px;
}
.button {
  background: #3d4c53;
  margin: 20px auto;
  width: 200px;
  height: 50px;
  overflow: hidden;
  text-align: center;
  transition: 0.2s;
  cursor: pointer;
  border-radius: 3px;
  box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.2);
}
.btnTwo {
  position: relative;
  width: 200px;
  height: 100px;
  margin-top: -100px;
  padding-top: 2px;
  background: #ff8f21;
  left: -250px;
  transition: 0.3s;
}
.btnText {
  color: white;
  transition: 0.3s;
  padding-top: 13px;
}
.btnText2 {
  margin-top: 58px;
  margin-right: -130px;
  color: #fff;
}
.button:hover .btnTwo {
  /*When hovering over .button change .btnTwo*/
  left: -130px;
}
.button:hover .btnText {
  /*When hovering over .button change .btnText*/
  margin-left: 65px;
}
.button:active {
  /*Clicked and held*/
  box-shadow: 0px 5px 6px rgba(0, 0, 0, 0.3);
}
.video-frame {
  width: 400px;
  height: 300px;
  /* border: 5px solid white; */
  border-radius: 10px;
}

.video-container {
  margin-top: 40px;
}

.button-container {
  margin-top: 10px;
}

.portfolio-container {
  width: 670px;
  height: 610px;
  border: 5px solid white;
  border-radius: 10px;
  margin-top: 40px;
  background-color: white;
  z-index: -1;
  overflow-y: scroll;
}

#video-controller {
  border: 5px solid white;
  border-radius: 50%;
  width: 70px;
  height: 70px;
  background-color: black;
  margin-right: 50px;
}
#video-controller-no-video {
  border: 5px solid white;
  border-radius: 50%;
  width: 70px;
  height: 70px;
  background-color: black;
  margin-right: 50px;
}

#sound-controller {
  border: 5px solid white;
  border-radius: 50%;
  width: 70px;
  height: 70px;
  background-color: black;
  margin-right: 50px;
  color: white;
}

#sound-controller-no-sound {
  border: 5px solid white;
  border-radius: 50%;
  width: 70px;
  height: 70px;
  background-color: black;
  margin-right: 50px;
}

#exit-btn {
  border: 5px solid white;
  border-radius: 50%;
  width: 70px;
  height: 70px;
  background-color: black;
}
.box {
  background-color: #0e1133;
  overflow: hidden;
  width: 100%;
  height: 100vh;
  margin: 0;
  /* padding-bottom: 20px; */
}
</style>
