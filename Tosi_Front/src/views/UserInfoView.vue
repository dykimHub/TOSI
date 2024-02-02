<template>
  <div class="set">
    <div>비밀번호를 입력해주세요</div>
    <div class="login-div">
      <input type="password" class="set-inp" placeholder=" " v-model="password">
      <label for="" class="label">비밀번호</label>
    </div>
    <input type="submit" class="set-btn" value="확인" @click="checkPassword">
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/userStore";

const store = useUserStore()

const password = ref("");

const checkPassword = () => {
  if (
    password.value === ""
  ) {
    alert("비밀번호를 입력해주세요");
    return;
  }
  store.getPasswordCheck(password);
};


</script>

<style scoped>
.set {
  width: 500px;
  background-color: var(--header);
  text-align: center;
  margin: 100px auto;
  border-radius: 20px;
  padding: 20px;
}

.set-inp {
  padding: 10px;
  border-radius: 10px;
  border: solid 2px var(--primary-200);
  width: 200px;
  margin: 15px;
}

.set-btn {
  background-color: var(--primary-200);
  width: 150px;
  margin: 0 auto;
  padding: 5px;
  border-radius: 10px;
  transition: 0.2s;
}

.set-btn:hover {
  background-color: var(--primary-100);
  color: var(--bg-100);
}

.label {
  position: absolute;
  left: 1rem;
  top: 1.3rem;
  padding: 0 0.25rem;
  background-color: var(--header2);
  color: var(--text-200);
  font-size: 1rem;
  transition: 0.3s;
  border-radius: 10px;
}
</style>