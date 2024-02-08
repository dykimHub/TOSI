<template>
  <div class="update-form">
    <form action="" class="form" @submit.prevent="onsubmit">
      <div>
      <h1 class="form__title">마이페이지</h1>

      <div class="update-div">
        <input type="email" class="update-input" placeholder="" v-model="userInfo.email" readonly>
        <label class="update-label">이메일</label>
      </div>
      <div class="regist-div">
          <input type="password" class="regist-input" placeholder=" " v-model="password">
          <label class="regist-label">Password</label>
        </div>
        <div class="regist-div">
          <input type="password" class="regist-input" placeholder=" " v-model="passwordCheck">
          <label class="regist-label">Password 확인</label>
        </div>
      <div class="update-div">
        <input type="text" class="update-input" :placeholder="userInfo.bookshelfName ? userInfo.bookshelfName : ' '"
          v-model="userInfo.bookshelfName">
        <label class="update-label">나의 책장 이름</label>
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
            <input type="checkbox" class="regist-input" placeholder=" " v-model="child.myBaby">
            <label class="regist-label">내 아이 여부</label>
          </div>
          <button @click="registerChild">등록</button>
        </div>
        <!-- <div>
          <h2>등록된 아이 목록</h2>
          <ul>
            <ChildItem v-for="(child, index) in childrenList" :key="index" :child="child" @deleteChild="deleteChild" />
          </ul>
        </div> -->
        <ul class="children-ul">
          <li v-for="(child, index) in userInfo.childrenList" :key="index" class="children-li">
            <ChildItem :key="index" :child="child"/>
            <button @click="deleteChild(index)">삭제</button>
          </li>
        </ul>
        <!-- <ul class="children-ul">
          <li v-for="(child, index) in userInfo.childrenList" :key="index" class="children-li">
            {{ child.childName }} - 성별: {{ child.gender }} - 내 아이 여부: {{ child.myBaby }}
          </li>
        </ul> -->

      </div>
      <button @click="deleteUserInfo">회원탈퇴</button>
      <input type="submit" class="update-button" value="update" @click="update">
    </form>

  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/userStore";
import ChildItem from "@/components/user/ChildItem.vue";

const store = useUserStore();
const userInfo = ref({ email: '', bookshelfName: '', childrenList: [] });
const child = ref({ childName: '', gender: 0, myBaby: false });
const childrenList = ref([]);
const email = ref("");
const password = ref("");
const passwordCheck = ref("");
const bookshelfName = ref("");

onMounted(() => {
  store.getUser();
  const user = store.userInfo;
  userInfo.value = user;
  // userInfo.value.email = user.email;
  // userInfo.value.bookshelfName = user.bookshelfName;
  // userInfo.value.childrenList = user.childrenList;
});

const registerChild = function () {
  userInfo.value.childrenList.push({ ...child.value });
  console.log(userInfo.value.childrenList);
  child.value.childName = "";
}

const update = function () {
  if (password.value && passwordCheck.value && password.value !== passwordCheck.value) {
    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    return;
  }
  store.updateUser(userInfo.value);
}

const deleteUserInfo = function () {
  console.log(userInfo.value.email);
  store.deleteUser(userInfo.value.email);
  userInfo.value = null;
  localStorage.removeItem('isLoggedIn');
  console.log("isLoggedIn:" + localStorage.getItem('isLoggedIn'));
  alert("회원탈퇴 완료.");
}

const deleteChild = function(index) {
  userInfo.value.childrenList.splice(index, 1);
};

</script>

<style scoped></style>