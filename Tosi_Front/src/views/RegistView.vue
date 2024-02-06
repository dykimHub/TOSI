<template>
  <div class="regist-form">
    <form action="" class="form" @submit.prevent="onsubmit">
      <h1 class="form__title">회원가입</h1>

      <div class="div">
        <div class="regist-div">
          <input type="email" class="regist-input" placeholder=" " v-model="email">
          <label class="regist-label">이메일</label>
          <button type="button" @click="checkEmailDuplication">이메일 중복 확인</button>

        </div>
        <div class="regist-div">
          <input type="password" class="regist-input" placeholder=" " v-model="password">
          <label class="regist-label">Password</label>
        </div>
        <div class="regist-div">
          <input type="password" class="regist-input" placeholder=" " v-model="passwordCheck">
          <label class="regist-label">Password 확인</label>
        </div>
        <div class="regist-div">
          <input type="text" class="regist-input" placeholder=" " v-model="bookshelfName">
          <label class="regist-label">나의 책장 이름</label>
        </div>


        <div class="regist-div">
          <div>
            <input type="text" class="regist-input" placeholder=" " v-model="child.childName">
            <label class="regist-label">아이 이름</label>
          </div>
          <div>
            <input type="radio" class="regist-input" placeholder=" " v-model="child.gender" value="0"> 여자
            <input type="radio" class="regist-input" placeholder=" " v-model="child.gender" value="1"> 남자
            <label class="regist-label">아이 성별</label>
          </div>
          <div>
            <input type="checkbox" class="regist-input" placeholder=" " v-model="child.isMyBaby">
            <label class="regist-label">내 아이 여부</label>
          </div>
          <button @click="registerChild">등록</button>
        </div>
        <div>
          <h2>등록된 아이 목록</h2>
          <ul>
            <ChildItem v-for="(child, index) in childrenList" :key="index" :child="child" @deleteChild="deleteChild" />
          </ul>
        </div>
      </div>

      <input type="submit" class="regist-button" value="regist" @click="regist">
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/userStore";
import ChildItem from "@/components/user/ChildItem.vue";

const userStore = useUserStore()

const email = ref("");
const password = ref("");
const passwordCheck = ref("");
const bookshelfName = ref("");
const child = ref({ childName: '', gender: 0, isMyBaby: false });
const childrenList = ref([]);

const registerChild = function () {
  childrenList.value.push({ ...child.value });
  console.log(childrenList.value);
  child.childName = "";
}

const regist = () => {
  if (
    email.value === "" ||
    password.value === "" ||
    passwordCheck.value === "" ||
    childrenList.value === ""
  ) {
    alert("모든 내용을 입력해주세요");
    return;
  }

  userStore.getUserSearch(email.value);

  if (userStore.searchResult == true) {
    alert("이미 등록된 이메일입니다.");
    return;
  }

  if (password.value !== passwordCheck.value) {
    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    return;
  }

  const userInfo = {
    email: email.value,
    password: password.value,
    bookshelfName: bookshelfName.value,
    childrenList: childrenList.value
  };

  userStore.postUser(userInfo);
};

const checkEmailDuplication = async function () {
  if (email.value.trim() === "") {
    return; // 이메일이 비어있을 경우 건너뛰기
  }

  try {
    await userStore.getUserSearch(email.value);

    if (userStore.searchResult == true) {
      alert("이미 등록된 이메일입니다.");
    } else {
      alert("사용 가능한 이메일입니다.");
    }
  } catch (error) {
    console.error("에러 발생:", error);
  }
};

const deleteChild = function(child) {
  childrenList.value = childrenList.value.filter(function(item) {
    return item.childName !== child.childName;
  });
};

</script>

<style scoped>

</style>