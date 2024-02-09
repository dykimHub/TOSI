<template>
    <div class="login-form">
      <form action="" class="form" @submit.prevent="onsubmit">
        <h1 class="form__title">로그인</h1>
  
        <div class="login-div">
          <label for="" class="login-label">Email</label>
          <input type="text" class="login-input" placeholder=" " v-model="email">
        </div>
  
        <div class="login-div">
          <label for="" class="login-label">비밀번호</label>
          <input type="password" class="login-input" placeholder=" " v-model="password">
        </div>

        <div class="login-div">
        <input type="checkbox" id="autoLogin" v-model="autoLoginChecked">
        <label for="autoLogin" class="login-label">자동 로그인</label>
        </div>
  
        <input type="submit" class="login-button" value="로그인" @click="login">
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import { useUserStore } from "@/stores/userStore";
  
  const store = useUserStore()
  
  const email = ref("");
  const password = ref("");
  const autoLoginChecked = ref(false);
  
  const login = () => {
    let user = {
      email: email.value,
      password: password.value
    };
    
    store.postLogin(user);
  };

// import { ref, defineProps, defineEmits } from 'vue';

// const props = defineProps([]);
// const emits = defineEmits(['closeLoginFormModal']);

//   import { defineProps, defineEmits } from 'vue';

// // const { child } = defineProps(['child']);
// const { emit } = defineEmits();

// // const deleteChild = (child) => {
// //   emit('deleteChild', child);
// // };
  
  </script>
  
  
  <style scoped>
  .login-div {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.login-label {
  width: 200px; /* 레이블의 너비를 일정하게 설정 */
  margin-right: 10px;
}

.login-input {
  flex: 1; /* 입력 칸이 남은 공간을 모두 차지하도록 설정 */
  padding: 5px;
}

  </style>