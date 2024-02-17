<template>
  <div class="login-form">
    <form action="" class="form" @submit.prevent="onsubmit">
      <h1 class="title">로그인</h1>

      <div class="login-div">
        <label for="" class="login-label">Email</label>
        <input type="text" class="login-input" placeholder=" " v-model="email">
      </div>

      <div class="login-div">
        <label for="" class="login-label">비밀번호</label>
        <input type="password" class="login-input" placeholder=" " v-model="password">
      </div>

      <div class="login-div">
        <div class="checkbox-container">
          <input type="checkbox" id="autoLogin" v-model="autoLogin">
          <label for="autoLogin" class="login-label">자동 로그인</label>
        </div>
        <input type="submit" class="login-button" value="로그인" @click="login">
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/userStore";

const store = useUserStore()

const email = ref("");
const password = ref("");
const autoLogin = ref(true);

const login = () => {
  let loginInfo = {
    email: email.value,
    password: password.value,
    autoLogin: autoLogin.value
  };

  store.postLogin(loginInfo);
};

</script>


<style scoped>
.title {
    text-decoration: none;
    display: inline-block;
    box-shadow: inset 0 -20px 0  #c4ecb0;
    font-size: 40px;
    margin-bottom: 40px;
    line-height: 1;
    text-align: left;
}
.login-div {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.login-label {
  width: 200px;
  margin-right: 10px;
}

.login-input {
  flex: 1;
  padding: 5px;
  height: 40px;
}

.login-button {
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-top: 0px;
  margin-left: auto;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  transition: all 0.3s ease;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}

.login-button:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
}

.checkbox-container {
  display: flex;
  align-items: center;
  margin-right: 20px;
}
</style>