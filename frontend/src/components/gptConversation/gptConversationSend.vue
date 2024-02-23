<template>
  <div class="talelistContainer">
    <div class="bigwelcome"></div>
    <div class="welcome">{{ props.bname }}의 채팅방💬</div>
    <div id="app" class="container" ref="container">
      <div id="Hi">
        <div class="chattop">{{ props.cname }}님이 입장했습니다!</div>
      </div>
      <div id="body">
        <ul style="list-style: none">
          <div class="bubble">
            <p class="from-them">채팅 최대 횟수는 5회입니다. 등장인물과 대화를 시도해 보세요! 😊</p>
          </div>
          <li v-for="item in chatMemory">
            <div class="bubble">
              <p :class="[item.role == 'user' ? 'from-me' : 'from-them']">{{ item.message }}</p>
            </div>
          </li>
        </ul>
      </div>
      <div v-if="chatCount == 6" id="Hi">
        <p>{{ props.cname }}님이 나갔습니다.</p>
      </div>
    </div>
    <div class="mb-3 inputmsg">
      <div class="input-group">
        <input
          @keyup.enter="generateChat"
          type="text"
          id="message"
          v-model="message"
          class="form-control"
          placeholder="메세지를 입력하세요."
        />
        <button class="generate" @click="generateChat">
          <div class="svg-wrapper-1">
            <div class="svg-wrapper">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path
                  fill="currentColor"
                  d="M1.946 9.315c-.522-.174-.527-.455.01-.634l19.087-6.362c.529-.176.832.12.684.638l-5.454 19.086c-.15.529-.455.547-.679.045L12 14l6-8-8 6-8.054-2.685z"
                ></path>
              </svg>
            </div>
          </div>
          <span></span>
        </button>
      </div>
      <div class="gotobtn">
        <button class="gotolist" @click="goToList">나가기</button>
      </div>
    </div>
    <div class="biginput"></div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, nextTick } from "vue";
import { useTaleDetailStore } from "@/stores/taleDetailStore";
import router from "@/router";
import { useToast } from "vue-toast-notification";

const chatCount = ref(0);

const chatMemory = ref([]);
const message = ref("");
const gptMessage = ref("");
const taleDetailStore = useTaleDetailStore();

const props = defineProps({
  cname: String,
  bname: String,
});

console.log(props.cname);
console.log(props.bname);
console.log(taleDetailStore.tale.total_contents);

function generateChat() {
  if (message.value === "") {
    toast.info("메세지를 입력해주세요🎨", {
          position: "top",
          duration: 2000,
          queue: true,
          style: {
            backgroundColor: "#f1a8bc",
            color: "white",
          },
        });
    return;
  }

  if (chatCount.value == 6) {
    toast.info(props.cname + "님은 쉬는 중이에요. 다른 친구를 만나러 가 볼까요?", {
          position: "top",
          duration: 2000,
          queue: true,
          style: {
            backgroundColor: "#f1a8bc",
            color: "white",
          },
        });
    return;
  }


  const API_URL = "http://i10b306.p.ssafy.io:8080/gptapi/";

  chatMemory.value.push({
    role: "user",
    message: message.value,
  });

  message.value = ""; // 인풋 비우기
  scrollToBottom();

  console.log("채팅 카운트:" + chatCount.value);

  fetch(API_URL + "input", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userMessages: [...chatMemory.value],
      taleScript: taleDetailStore.tale.total_contents,
      playName: props.cname,
      userName: props.bname,
    }),
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw new Error("Network response was not ok.");
    })
    .then((data) => {
      gptMessage.value = data.message;
      chatMemory.value.push({
        role: "assistant",
        message: data.message,
      });

      chatCount.value += 1;
      console.log(chatCount.value + "번째 응답입니다.");
      scrollToBottom();

      // 채팅 카운트가 모두 소모됐을 때 인사를 추가한다.
      if (chatCount.value == 5) {
        fetch(API_URL + "bye", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            userMessages: [...chatMemory.value],
            taleScript: taleDetailStore.tale.total_contents,
            playName: props.cname,
            userName: props.bname,
          }),
        })
          .then((response) => {
            if (response.ok) {
              return response.json();
            }
            throw new Error("Network response was not ok.");
          })
          .then((data) => {
            gptMessage.value = data.message;
            chatMemory.value.push({
              role: "assistant",
              message: data.message,
            });

            console.log(chatCount.value + "번째 응답입니다.");

            chatMemory.value.push({
              role: "assistant",
              message: "대화 가능한 횟수가 모두 소모 되어 대화를 종료합니다. 🍀",
            });
            scrollToBottom();

            // div 업데이트를 위한 카운트
            chatCount.value += 1;
          })
          .catch((error) => {
        toast.info("에러가 발생했어요.", {
          position: "top",
          duration: 2000,
          queue: true,
          style: {
            backgroundColor: "#f1a8bc",
            color: "white",
          },
        });
            console.error("에러가 났어요.", error);
          });
      }
    })
    .catch((error) => {
      toast.info("에러가 발생했어요.", {
          position: "top",
          duration: 2000,
          queue: true,
          style: {
            backgroundColor: "#f1a8bc",
            color: "white",
          },
        });
      console.error("에러가 났어요.", error);
    });
}

const container = ref(null);

function scrollToBottom() {
  nextTick(() => {
    const element = container.value;
    if (element) {
      console.log("Scrolling to bottom", element.scrollHeight); // 디버깅을 위한 로그
      element.scrollTop = element.scrollHeight;
    }
  });
}

const goToList = () => {
  router.push({ name: "tales" });
};
</script>

<style scoped>
/* 전체 */
.input-group {
  margin-top: 30px;
}
#app {
  background-color: #fff7f1;
  border-radius: 30px;
  padding-top: 100px;
  padding-bottom: 100px;
  box-shadow: rgba(0, 0, 0, 0.09) 0px 2px 1px, rgba(0, 0, 0, 0.09) 0px 4px 2px,
    rgb(255, 230, 230) 0px 2px 4px, rgb(231, 242, 255) 0px 2px 8px, rgb(141, 255, 238) 0px 0px 5px;
  /* margin-bottom: 50px; */
  margin-top: -50px;
  height: 50vw;
}

#top {
  background-color: #fff;
  border-radius: 30px;
  width: 600px;
  align-items: center;
  margin-bottom: -100px;
  z-index: 10;
}

/* 헤더 */
.welcome {
  box-shadow: rgba(0, 0, 0, 0.09) 0px 2px 1px, rgba(0, 0, 0, 0.09) 0px 4px 2px,
    rgba(0, 0, 0, 0.09) 0px 2px 4px, rgb(166, 206, 255) 0px 4px 8px, rgb(166, 255, 243) 0px 0px 5px;
  border-radius: 30px;
  padding: 20px;
  text-align: center;
  font-size: 30px;
  z-index: 5;
  background-color: white;
  margin-bottom: -50px;
  margin-top: 30px;
  /* margin-bottom: 30px; */
  width: 600px;
}

#Hi {
  background-color: rgb(255, 255, 255);
  box-shadow: rgba(0, 0, 0, 0.09) 0px 2px 1px, rgba(0, 0, 0, 0.09) 0px 4px 2px,
    rgb(255, 230, 230) 0px 2px 4px, rgb(231, 242, 255) 0px 2px 8px, rgb(172, 255, 244) 0px 0px 5px;
  height: 30px;
  border-radius: 10px;
  margin: 20px auto 0;
  text-align: center;
  width: 550px;
}
/* 메세지 */
.bubble {
  border-radius: 0.25rem;
  display: flex;
  flex-direction: column;
  font-family: "SanFrancisco";
  font-size: 1.25rem;
  margin: 0 auto 1rem;
  max-width: 600px;
  padding: 0.6rem 1.5rem;
}

.bubble p {
  border-radius: 1.15rem;
  line-height: 1.25;
  max-width: 75%;
  padding: 0.5rem 0.875rem;
  position: relative;
  word-wrap: break-word;
}

.bubble p::before,
.bubble p::after {
  bottom: -0.1rem;
  content: "";
  height: 1rem;
  position: absolute;
}

p.from-me {
  align-self: flex-end;
  background-color: #ffb3be;
  color: #000;
}

p.from-me::before {
  border-bottom-left-radius: 0.8rem 0.7rem;
  border-right: 1rem solid #ffb3be;
  right: -0.35rem;
  transform: translate(0, -0.1rem);
}

p.from-me::after {
  background-color: #fff7f1;
  border-bottom-left-radius: 0.5rem;
  right: -40px;
  transform: translate(-30px, -2px);
  width: 10px;
}

p[class^="from-"] {
  margin: 0.5rem 0;
  width: fit-content;
}

p.from-me ~ p.from-me {
  margin: 0.25rem 0 0;
}

p.from-me ~ p.from-me:not(:last-child) {
  margin: 0.25rem 0 0;
}

p.from-me ~ p.from-me:last-child {
  margin-bottom: 0.5rem;
}

p.from-them {
  align-items: flex-start;
  background-color: #ffebbb;
  color: #000;
  z-index: 1;
}

p.from-them:before {
  border-bottom-right-radius: 0.8rem 0.7rem;
  border-left: 1rem solid #ffebbb;
  left: -0.35rem;
  transform: translate(0, -0.1rem);
}

p.from-them::after {
  background-color: #fff7f1;
  border-bottom-right-radius: 0.5rem;
  left: 20px;
  transform: translate(-30px, -2px);
  width: 10px;
}

p[class^="from-"].emoji {
  background: none;
  font-size: 2.5rem;
}

p[class^="from-"].emoji::before {
  content: none;
}

.no-tail::before {
  display: none;
}

.margin-b_none {
  margin-bottom: 0 !important;
}

.margin-b_one {
  margin-bottom: 1rem !important;
}

.margin-t_one {
  margin-top: 1rem !important;
}

/* general styling */
@font-face {
  font-family: "SanFrancisco";
  src: url("https://cdn.rawgit.com/AllThingsSmitty/fonts/25983b71/SanFrancisco/sanfranciscodisplay-regular-webfont.woff2")
      format("woff2"),
    url("https://cdn.rawgit.com/AllThingsSmitty/fonts/25983b71/SanFrancisco/sanfranciscodisplay-regular-webfont.woff")
      format("woff");
}

body {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell,
    "Helvetica Neue", sans-serif;
  font-weight: normal;
  margin: 0;
}

.container {
  /* margin: 0 auto; */
  /* max-width: 600px; */
  max-width: 800px;
  padding: 1rem;
  overflow-x: hidden;
  overflow-y: auto;
  -ms-overflow-style: none;
}
.container::-webkit-scrollbar {
  display: none;
}

h1 {
  font-weight: normal;
  margin-bottom: 0.5rem;
}

h2 {
  border-bottom: 1px solid #e5e5ea;
  color: #666;
  font-weight: normal;
  margin-top: 0;
  padding-bottom: 1.5rem;
}

.comment {
  color: #222;
  font-size: 1.25rem;
  line-height: 1.5;
  margin-bottom: 1.25rem;
  max-width: 100%;
  padding: 0;
}

@media screen and (max-width: 800px) {
  body {
    margin: 0 0.5rem;
  }

  .container {
    padding: 0.5rem;
  }

  .bubble {
    font-size: 1.05rem;
    margin: 0 auto 1rem;
    max-width: 600px;
    padding: 0.25rem 0rem;
  }

  .bubble p {
    margin: 0.5rem 0;
  }
}

/* 인풋 버튼 */

.generate {
  font-family: inherit;
  font-size: 20px;
  background: #d2edea;
  color: white;
  padding: 0.7em 1em;
  padding-left: 0.9em;
  display: flex;
  align-items: center;
  border: none;
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.2s;
  cursor: pointer;
  z-index: 3;
}

.generate span {
  display: block;
  margin-left: 0.3em;
  transition: all 0.3s ease-in-out;
}

.generate svg {
  display: block;
  transform-origin: center center;
  transition: transform 0.3s ease-in-out;
}

.generate:hover .svg-wrapper {
  animation: fly-1 0.6s ease-in-out infinite alternate;
}

.generate:hover svg {
  transform: translateX(1.2em) rotate(45deg) scale(1.1);
}

.generate:hover span {
  transform: translateX(5em);
}

.generate:active {
  transform: scale(0.95);
}

.talelistContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  /* justify-content: center; */
  background-color: white;
  border-radius: 50px;
  border: 5px solid #cee8e8;
  width: 80vw;
  height: 43vw;
  padding-top: 50px;
  padding-bottom: 50px;
}

@keyframes fly-1 {
  from {
    transform: translateY(0.1em);
  }

  to {
    transform: translateY(-0.1em);
  }
}

#app ul {
  margin-left: 0;
  padding-left: 0;
}
.inputmsg {
  width: 600px;
  margin-top: -100px;
  z-index: 3;
}
.bigwelcome {
  background-color: #fff7f1;
  width: 700px;
  height: 30px;
  margin-bottom: -34px;
  z-index: 2;
}
.biginput {
  background-color: #fff7f1;
  width: 700px;
  height: 30px;
  margin-top: -22px;
  z-index: 2;
}
.gotobtn {
  margin-left: 615px;
  margin-top: -55px;
}
.gotolist {
  border: 2px solid #dee2e6;
  border-radius: 50%;
  background-color: white;
  cursor: pointer;
  width: 60px;
  height: 60px;
}
</style>
