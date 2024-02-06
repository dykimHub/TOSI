<template>
  <div class="login-form">
    <form action="" class="form" @submit.prevent="onsubmit">
      <h1 class="form__title">로그인</h1>

      <div class="login-div">
        <input type="text" class="login-input" placeholder=" " v-model="email">
        <label for="" class="login-label">Email</label>
      </div>

      <div class="login-div">
        <input type="password" class="login-input" placeholder=" " v-model="password">
        <label for="" class="login-label">비밀번호</label>
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

const login = () => {
  let user = {
    email: email.value,
    password: password.value
  };
  store.postLogin(user);
};

</script>


<style scoped>

</style>