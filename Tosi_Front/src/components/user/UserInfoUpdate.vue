<template>
  <div class="update-form">
    <form action="" class="form" @submit.prevent="onsubmit">
      <div class="info-form">
        <h1 class="form__title">마이페이지</h1>
        <div class="update-div">
          <label class="update-label">이메일</label>
          <input type="email" class="update-input" placeholder="" v-model="userInfo.email" readonly>
        </div>
        <div class="update-div">
          <label class="update-label">Password</label>
          <input v-if="passwordForm == true" type="password" class="update-input" placeholder=" " v-model="userInfo.password">
          <button v-if="passwordForm == false" @click="exposePasswordForm" class="password-change-button">변경</button>
        </div>
        <div class="update-div" v-if="passwordForm == true">
          <label class="update-label">Password Check</label>
          <input type="password" class="update-input" placeholder=" " v-model="passwordCheck">
        </div>
        <div class="update-div">
          <label class="update-label">나의 책장 이름</label>
          <input type="text" class="update-input" :placeholder="userInfo.bookshelfName ? userInfo.bookshelfName : ' '"
            v-model="userInfo.bookshelfName">
        </div>
      </div>

      <div class="child-form">
        <div class="update-div">
          <div>
            <label class="update-label">아이 이름</label>
            <input type="text" class="update-input" placeholder=" " v-model="child.childName">
            <button @click="registerChild" class="register-child-button">등록</button>
          </div>
        </div>
        <div class="update-div">
          <div>
            <label class="update-label">아이 성별</label>
            <input type="radio" class="update-input" placeholder=" " v-model="child.gender" value="0">
            여자&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" class="update-input" placeholder=" " v-model="child.gender" value="1"> 남자
          </div>
        </div>
        <div class="update-div">
          <div>
            <label class="update-label">내 아이 여부</label>
            <input type="checkbox" class="update-input" placeholder=" " v-model="child.myBaby">* 내 아이인 경우 노란색으로 표시됩니다.
          </div>
        </div>

        <div class="children-list-container">
          <ul class="children-ul">
            <li v-for="(child, index) in userInfo.childrenList" :key="index" class="one-child" :class="{ 'my-baby': child.myBaby }">
              <img v-if="child.gender === 0" src="@/assets/female-icon.png" alt="Female Icon" class="icon" />
              <img v-else src="@/assets/male-icon.png" alt="Male Icon" class="icon" />
              {{ child.childName }}
            <button @click="deleteChild(index)" class="childDeleteButton">×</button>
            </li>
          </ul>
        </div>
      </div>

      <input type="submit" class="update-button" value="update" @click="update">
      <button class="remove-user-button" @click="deleteUserInfo">회원탈퇴</button>
    </form>
  </div>
</template>
<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/userStore";

const store = useUserStore();
const userInfo = ref({ email: '', password: '', bookshelfName: '', childrenList: [] });
const child = ref({ childName: '', gender: 0, myBaby: false });
const passwordCheck = ref("");
const passwordForm = ref(false);

onMounted(async () => {
  await store.getUser();
  userInfo.value = store.userInfo;
});

const exposePasswordForm = function() {
  passwordForm.value = true;
}

//아이 등록
const registerChild = function () {
  userInfo.value.childrenList.push({ ...child.value });
  console.log(userInfo.value.childrenList);
  child.value.childName = "";
}
const deleteChild = function (index) {
  userInfo.value.childrenList.splice(index, 1);
};

//업데이트
const update = function () {
  if (userInfo.value.password && passwordCheck.value && userInfo.value.password !== passwordCheck.value) {
    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    return;
  }
  store.updateUser(userInfo.value);
}

//탈퇴
const deleteUserInfo = function () {
  alert("정말 회원 탈퇴하시겠습니까?")
  console.log(userInfo.value.email);
  store.deleteUser(userInfo.value.email);
  userInfo.value = null;
  localStorage.removeItem('isLoggedIn');
  console.log("isLoggedIn:" + localStorage.getItem('isLoggedIn'));
  alert("회원탈퇴 완료.");
}

</script>
<style scoped>

.update-div {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.update-label {
  width: 150px;
  /* 레이블의 너비를 줄임 */
  margin-right: 10px;
}

.update-input {
  flex: 1;
  /* 입력 칸이 남은 공간을 모두 차지하도록 설정 */
  padding: 5px;
  max-width: 300px;
  /* 최대 가로길이 설정 */
  margin-right: 10px;
}

.my-baby {
  background-color: #eee58a;
}

.icon {
  width: 24px;
  height: 24px;
  margin-right: 8px;
}

.one-child {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 10px; /* 각 아이템 간 간격 조정 */
  margin-bottom: 10px;
  width: fit-content;
  width: max-content;
  height: 30px;
  border: 3px solid #eee58a;
  border-radius: 15px;
  /* border-color: #eee58a; */
}

.one-child button {
  margin-left: 10px;
}

.info-form,
.child-form {
  border: 1px solid #000000;
  padding: 20px;
  margin-bottom: 20px;
}

.children-ul {
  list-style-type: none; /* 리스트 스타일 제거 */
  padding: 0; /* 내부 여백 제거 */
  display: flex; /* 수평으로 배치되도록 설정 */
  flex-wrap: wrap; /* 넘칠 경우 줄 바꿈 */
}

.children-list-container {
  border: 1px solid #000000;
  padding: 5px;
  overflow: auto;
  width: 500px;
  /* background-color: #eee58a; */
}

.password-change-button,
.check-duplication-button,
.register-child-button,
.update-button {
  background-color: #f1a8bc;
  /* Green */
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

.update-button {
  float: left; /* 왼쪽으로 배치 */
  margin-right: 10px; /* 오른쪽 여백 추가 */
}

.remove-user-button {
  float: right; /* 오른쪽으로 배치 */
  background-color: transparent; /* 배경색을 투명하게 */
  border: none; /* 테두리 제거 */
}

.check-duplication-button:hover,
.register-child-button:hover,
.update-button:hover {
  background-color: #f1a8bc;
}

.register-child-button {
  margin-left: 20;
}

.childDeleteButton {
  background-color: transparent;
  /* 배경색을 투명하게 설정 */
  border: none;
  /* 테두리 제거 */
  font-size: 20px;
  /* 텍스트 크기 설정 */
  cursor: pointer;
  /* 커서를 포인터로 변경하여 클릭 가능한 상태로 표시 */
}
</style>