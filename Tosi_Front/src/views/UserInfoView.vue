<template>
  <div class="formContainer">
    <div class="topOfForm">
      <h2 class="title">마이페이지</h2>
    </div>
    <div class="content">
      <div class="update-form">
        <form action="" class="form" @submit.prevent="onsubmit">
          <div class="password-check-container" v-if="passwordCheck == false">
            <div>비밀번호를 입력해주세요</div>
            <div class="login-div">
              <input type="password" class="set-inp" placeholder=" " v-model="password">
            </div>
            <input type="submit" class="set-btn" value="확인" @click="checkPassword">
          </div>
          <div class="user-info-container">
            <UserInfoUpdate v-if="passwordCheck == true" :userInfo="userInfo" />
          </div>
        </form>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/userStore";
import UserInfoUpdate from "@/components/user/UserInfoUpdate.vue";

const store = useUserStore()

const userInfo = ref({ email: '', bookshelfName: '', childrenList: [] });
const password = ref("");
const passwordCheck = ref(false);

const checkPassword = async function () {
  if (
    password.value === ""
  ) {
    alert("비밀번호를 입력해주세요");
    return;
  }
  await store.getPasswordCheck(password.value);
  passwordCheck.value = store.passwordCheck
};

</script>

<style scoped>
.title {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 #d6b0ec;
  font-size: 40px;
  margin: 30px 0px 0px 50px;
  margin-bottom: 40px;
  line-height: 1;
  text-align: left;
}

.topOfForm {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-left:20px;
  margin-right: 20px;
}

.formContainer {
  justify-content: center;
  background-color: white;
  border-radius: 50px;
  margin: 35px;
  padding-top: 40px;
  padding: 40px;
  opacity: 0.95;
  border: 5px solid #cee8e8;
  width: 80vw;
}

form {
  width: fit-content;
}

.content {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}

.password-change-button,
.check-duplication-button,
.register-child-button,
.update-button {
  background-color: #d6b0ec;
  border: none;
  color: white;
  padding: 7.5px 15px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-right: 5px;
  cursor: pointer;
}

.password-check-container {
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
  border: solid 2px #000000;
  ;
  width: 200px;
  margin: 15px;
}

.set-btn {
  width: 150px;
  margin: 0 auto;
  padding: 5px;
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}

.set-btn:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
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
}</style>
