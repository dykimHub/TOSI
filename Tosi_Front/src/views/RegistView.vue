<template>
  <div class="regist-form">
    <form action="" class="form" @submit.prevent="onsubmit">
      <h1 class="form__title">회원가입</h1>

      <div class="div">
        <div class="regist-div">
          <input type="email" class="regist-input" placeholder=" " v-model="email">
          <label class="regist-label">이메일</label>
        </div>
        <div class="regist-div">
          <input type="password" class="regist-input" placeholder=" " v-model="password">
          <label class="regist-label">Password</label>
        </div>
        <div class="regist-div">
          <input type="text" class="regist-input" placeholder=" " v-model="bookshelfName">
          <label class="regist-label">나의 책장 이름</label>
        </div>
        <div>
          <div class="regist-div">
            <input type="text" class="regist-input" placeholder=" " v-model="child.childName">
            <label class="regist-label">아이 이름</label>
            <input type="text" class="regist-input" placeholder=" " v-model="child.gender">
            <label class="regist-label">아이 성별</label>
            <input type="text" class="regist-input" placeholder=" " v-model="child.isMyBaby">
            <label class="regist-label">내 아이 여부</label>
            <button @click="registerChild">등록</button>
          </div>
          <div>
            <h2>등록된 아이 목록</h2>
            <ul>
              <li v-for="(child, index) in childrenList" :key="index">
                {{ child.childName }} - 성별: {{ child.gender }} - 내 아이 여부: {{ child.isMyBaby }}
              </li>
            </ul>
          </div>
          
        </div>
      </div>

      <input type="submit" class="regist-button" value="regist" @click="regist">
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/userStore";

const store = useUserStore()

const email = ref("");
const password = ref("");
const bookshelfName = ref("");
const child = ref({childName: '', gender: 0, isMyBaby: 0});
const childrenList = ref([]);

const registerChild = function () {
  childrenList.value.push({ ...child.value });
  console.log(childrenList.value);
  }

const regist = () => {
  if (
    email.value === "" ||
    password.value === "" ||
    childrenList.value === ""
  ) {
    alert("모든 내용을 입력해주세요");
    return;
  }

  const userInfo = {
    email: email.value,
    password: password.value,
    bookshelfName: bookshelfName.value,
    childrenList: childrenList.value
  };

  store.postUser(userInfo);
};

</script>

<style scoped>
</style>