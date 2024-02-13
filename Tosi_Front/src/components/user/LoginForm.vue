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
import router from "@/router";

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
}

.login-button {
  background-color: #abef8a;
  border: none;
  color: white; /* 텍스트 색상 */
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-top: 0px;
  cursor: pointer; /* 마우스 커서를 포인터로 변경하여 클릭 가능한 상태로 표시 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
  margin-left: auto; /* 오른쪽 가장자리로 이동 */
}

.login-button:hover {
  background-color: #45a049; /* 마우스 호버시 배경색 변경 */
}

.checkbox-container {
  display: flex;
  align-items: center;
  margin-right: 20px;
}
</style>