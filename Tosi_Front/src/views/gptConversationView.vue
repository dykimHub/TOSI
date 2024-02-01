<template>
    <div id="app" class="container">
      <h1 class="text-center my-4">🎀등장인물과의 대화🎀</h1>
      <div id="body">
        <h1>채팅 내역💬</h1>
        <ul style="list-style:none;">
          <li v-for="item in chatMemory">
            <div :class="[item.role=='user' ? 'bubble user': 'bubble assistant']">
              <p>{{item.message}}</p>
            </div>
          </li>
        </ul>
      </div>
      <div class="mb-3">
        <label for="prompt" class="form-label">입력:</label>
        <div class="input-group">
          <input type="text" id="message" v-model="message" class="form-control" />
          <button @click="generateChat" class="btn btn-primary">
            가라 GPT몬
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  let chatMemory=[];
  
  function createMemory(messages) {
    const memory = [];
    for (const msg of messages) {
      memory.push({ role: msg.role, content: msg.content });
      }
      return memory;
  }
  
  export default {
    data() {
      return {
        gptMessage: "",
        chatMemory
      };
    },
    methods: {
      async generateChat() {
        if (this.message === "") {
          alert("메세지를 입력해주세요🎨");
          return;
        }
  
        const API_KEY = "api_key";
        const API_URL = "http://localhost:8080/gptapi/input";
  
        try {
          let test2=["1","2","3"];
          console.log("배열출력:"+test2);
  
          let test=[...chatMemory,{"콘솔테스트":"dsfa"}];
  
          console.log("이건 챗 메모리:"+chatMemory);
          console.log("테스트:"+test);
  
          chatMemory.push({
                role:"user",
                message:this.message
          });
  
          this.$forceUpdate();
  
          const response = await fetch(API_URL, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              userMessages:[
                ...chatMemory,
                {
                  role:"user",
                  message:this.message,
                }
              ],
              taleScript:"옛날에 비만에 걸린 어른이 있었어. 걔는 어릴 때부터 삼시세끼 도너츠를 먹었어. 아무래도 직장에서 스트레스를많이 받았기 때문이었겠지. 어쨌든 그 어른은 100세까지 잘 지냈다고 해.",
              playName:"어른",
              chatCount:1,
            }),
          });
  
          if (response.ok) {
            const data = await response.json();
            
            console.log(data);
            if (data) {
              this.gptMessage = data.message;
              chatMemory.push({
                role:"assistant",
                message:data.message
              });
  
              this.$forceUpdate();
  
              return chatMemory;
            } else {
              alert("Failed to generate the image. Please try again.");
            }
          } else {
            alert("Error generating image. Please check the console for details.");
          }
        } catch (error) {
          console.log("Error generating image:", error);
          alert("Error generating image. Please check the console for details.");
        }
      },
    },
  };
  </script>
  
  <style>
  .bubble {
    --r: 25px; /* the radius */
    --t: 30px; /* the size of the tail */
    
    max-width: 300px;
    padding: calc(2*var(--r)/3);
    -webkit-mask: 
      radial-gradient(var(--t) at var(--_d) 0,#0000 98%,#000 102%) 
        var(--_d) 100%/calc(100% - var(--r)) var(--t) no-repeat,
      conic-gradient(at var(--r) var(--r),#000 75%,#0000 0) 
        calc(var(--r)/-2) calc(var(--r)/-2) padding-box, 
      radial-gradient(50% 50%,#000 98%,#0000 101%) 
        0 0/var(--r) var(--r) space padding-box;
    color: #000000;
  }
  .assistant {
    background: linear-gradient(135deg,#fbff00,rgb(205, 255, 24)) border-box;
    --_d: 0%;
    border-left: var(--t) solid #0000;
    margin-right: var(--t);
    place-self: start;
  }
  .user {
    background: linear-gradient(135deg,#ffea00,#fbff00) border-box;
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