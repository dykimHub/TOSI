<template>
  <div id="app" class="container">
    <h1 class="text-center my-4">🎀등장인물과의 대화🎀</h1>
    <div id="body">
      <h1>채팅 내역💬</h1>
      <ul style="list-style: none">
        <li v-for="item in chatMemory" :key="item.message">
          <div :class="[item.role == 'user' ? 'bubble user' : 'bubble assistant']">
            <p>{{ item.message }}</p>
          </div>
        </li>
      </ul>
    </div>
    <div class="mb-3">
      <label for="prompt" class="form-label">입력:</label>
      <div class="input-group">
        <input type="text" id="message" v-model="message" class="form-control" />
        <button @click="generateChat" class="btn btn-primary">가라 GPT몬</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useTaleDetailStore } from "@/stores/taleDetailStore";

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
    alert("메세지를 입력해주세요🎨");
    return;
  }

  const API_KEY = "api_key"; // Example API key
  const API_URL = "http://localhost:8080/gptapi/input";

  chatMemory.value.push({
    role: "user",
    message: message.value,
  });

  // Dummy API call example
  fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userMessages: [...chatMemory.value, { role: "user", message: message.value }],
      taleScript: "옛날에 비만에 걸린 어른이 있었어...",
      playName: "어른",
      chatCount: 1,
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
    })
    .catch((error) => {
      console.error("There has been a problem with your fetch operation:", error);
    });
}
</script>

<style>
.bubble {
  --r: 25px; /* the radius */
  --t: 30px; /* the size of the tail */

  max-width: 300px;
  padding: calc(2 * var(--r) / 3);
  -webkit-mask: radial-gradient(var(--t) at var(--_d) 0, #0000 98%, #000 102%) var(--_d) 100% /
      calc(100% - var(--r)) var(--t) no-repeat,
    conic-gradient(at var(--r) var(--r), #000 75%, #0000 0) calc(var(--r) / -2) calc(var(--r) / -2)
      padding-box,
    radial-gradient(50% 50%, #000 98%, #0000 101%) 0 0 / var(--r) var(--r) space padding-box;
  color: #000000;
}
.assistant {
  background: linear-gradient(135deg, #fbff00, rgb(205, 255, 24)) border-box;
  --_d: 0%;
  border-left: var(--t) solid #0000;
  margin-right: var(--t);
  place-self: start;
}
.user {
  background: linear-gradient(135deg, #ffea00, #fbff00) border-box;
  --_d: 100%;
  border-right: var(--t) solid #0000;
  margin-left: var(--t);
  place-self: end;
}

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
}

body {
  margin: 0;
  display: grid;
  place-content: center;
  gap: 20%;
  font-family: system-ui, sans-serif;
  font-size: 20px;
}
</style>
