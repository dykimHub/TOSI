<template>
  <div class="update-form">
    <form action="" class="form" @submit.prevent="onsubmit">
      <h1 class="form__title">마이페이지</h1>

      <div class="update-div">
        <input type="email" class="update-input" placeholder=" " v-model="data.email" readonly>
        <label class="update-label">이메일</label>
      </div>
      <div class="update-div">
        <input type="password" class="update-input" placeholder=" " v-model="data.password">
        <label class="update-label">Password</label>
      </div>
      <div class="update-div">
        <input type="text" class="update-input" placeholder=" " v-model="data.bookshelfName">
        <label class="update-label">나의 책장 이름</label>
      </div>
      <ul class="children-ul">
        <li v-for="(child, index) in children" :key="child.id" class="children-li">
          <div>{{ board.title }}</div>
          <div class="children-childName">{{ children.childName }}</div>
          <div class="children-gender">{{ children.gender }}</div>
          <div class="children-isMyBaby">{{ children.isMyBaby }}</div>
        </li>
      </ul>
      <div>
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
            <li v-for="(child, index) in childrenList" :key="index">
              {{ child.childName }} - 성별: {{ child.gender }} - 내 아이 여부: {{ child.isMyBaby }}
            </li>
          </ul>
        </div>
      </div>
      <button @click="deleteUserInfo">회원탈퇴</button>
      <input type="submit" class="update-button" value="update" @click="update">
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/userStore";


const store = useUserStore()

const userInfo = ref({});
const child = ref({ childName: '', gender: '', isMyBaby: '' });
const childrenList = ref([]);

onMounted(() => {
  user= store.getUser();
  data.email = user.email;
  data.password = user.password;
  data.bookShelfName = user.bookShelfName;
  data.children = user.children;
});

const registerChild = function () {
  childrenList.value.push({ ...child.value });
  console.log(childrenList.value);
}

const data = ref({
  email: userInfo.email,
  password: userInfo.password,
  bookShelfName: userInfo.bookShelfName,
  children: userInfo.children
})

const update = function () {
  console.log(data.value)
  store.updateUser(data.value)
}

const deleteUserInfo = function () {
  console.log(userInfo.email)
  store.deleteUser(userInfo.email)
  userInfo.value = null;
  localStorage.removeItem("loginUser");
  alert("회원탈퇴 완료. \n그동안 고마웠습니다.");
}
</script>

<style scoped>

</style>