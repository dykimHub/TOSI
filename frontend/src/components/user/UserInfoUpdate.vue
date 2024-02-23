<template>
    <div class="update-form">
      <form action="" class="form" @submit.prevent="onsubmit">
        <div class="info-form">
          <div class="update-div">
            <label class="update-label">이메일</label>
            <input type="email" class="update-input" placeholder="" v-model="userInfo.email" readonly>
          </div>
          <div class="update-div">
            <label class="update-label">비밀번호</label>
            <input v-if="passwordForm == true" type="password" class="update-input" placeholder=" "
              v-model="userInfo.password">
            <button v-if="passwordForm == false" @click="exposePasswordForm" class="password-change-button">변경</button>
          </div>
          <div class="update-div" v-if="passwordForm == true">
            <label class="update-label">비밀번호 확인</label>
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
              <label><input type="radio" class="update-input" placeholder=" " v-model="child.gender" value="0">여자</label>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <label><input type="radio" class="update-input" placeholder=" " v-model="child.gender" value="1">남자</label>
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
              <li v-for="(child, index) in userInfo.childrenList" :key="index" class="one-child"
                :class="{ 'my-baby': child.myBaby }">
                <img v-if="child.gender === 0" src="@/assets/female-icon.png" alt="Female Icon" class="icon" />
                <img v-else src="@/assets/male-icon.png" alt="Male Icon" class="icon" />
                {{ child.childName }}
                <button @click="deleteChild(index)" class="childDeleteButton">×</button>
              </li>
            </ul>
          </div>
        </div>

        <div class="button-container">
          <input type="submit" class="update-button" value="수정완료" @click="update">
        </div>        
      </form>
    </div>
  <button class="remove-user-button" @click="deleteUserInfo">회원탈퇴</button>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/userStore";
import Swal from "sweetalert2";
import { useToast } from "vue-toast-notification";

const toast = useToast();
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
    toast.info("비밀번호와 비밀번호 확인이 달라요.", {
          position: "top",
          duration: 2000,
          queue: true,
          style: {
            backgroundColor: "#f1a8bc",
            color: "white",
          },
        });
    return;
  }
  
  if (
    userInfo.value.childrenList === null ||
    (Array.isArray(userInfo.value.childrenList) && userInfo.value.childrenList.length === 0)
  ) {
    toast.info("아이를 1명 이상 등록해 주세요.", {
          position: "top",
          duration: 2000,
          queue: true,
          style: {
            backgroundColor: "#f1a8bc",
            color: "white",
          },
        });
    return;
  }

  store.updateUser(userInfo.value);
}

//탈퇴
const deleteUserInfo = function () {
  Swal.fire({
    title: "정말 회원 탈퇴하시려고요?",
    showCancelButton: true,
    confirmButtonText: "네",
    cancelButtonText: "아니오",
    confirmButtonColor: '#f1a8bc'
  }).then(async (result) => {
    if (result.isConfirmed) {
      await store.deleteUser(userInfo.value.email);
      userInfo.value = null;
      localStorage.removeItem('isLoggedIn');
      console.log("isLoggedIn:" + localStorage.getItem('isLoggedIn'));
    }
  });
  toast.success("회원탈퇴 완료ㅠㅠ", {
          position: "top",
          duration: 2000,
          queue: true,
          style: {
            backgroundColor: "#f1a8bc",
            color: "white",
          },
        });
}

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
}

.formContainer {
  justify-content: center;
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  padding-top: 40px;
  padding: 40px;
  opacity: 0.95;
  border: 5px solid #cee8e8;
  width: 80vw;
}

.update-form {
  display: flex;
  justify-content: center;
  align-items: center;
}


form {
  width: fit-content;
}

.update-div {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.update-label {
  width: 150px;
  margin-right: 10px;
}

.update-input {
  flex: 1;
  padding: 5px;
  max-width: 300px;
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
  margin-right: 10px;
  margin-bottom: 10px;
  width: fit-content;
  width: max-content;
  height: 30px;
  border: 3px solid #eee58a;
  border-radius: 15px;
}

.one-child button {
  margin-left: 10px;
}

.info-form,
.child-form {
  border: 1px solid #000000;
  border-radius: 4px;
  padding: 20px;
  margin-bottom: 20px;
  width: 540px;
}

.children-ul {
  list-style-type: none;
  padding: 0;
  display: flex;
  flex-wrap: wrap;
}

.children-list-container {
  border: 1px solid #000000;
  border-radius: 4px;
  padding: 5px;
  overflow: auto;
  overflow-x: hidden;
  width: 500px;
}

.password-change-button,
.register-child-button,
.update-button {
  padding: 7.5px 15px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-right: 5px;
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}

.password-change-button:hover,
.register-child-button:hover,
.update-button:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;

}

.update-button {
  float: left;
  margin-right: 10px;
}

.remove-user-button {
  position: absolute;
  bottom: 0px;
  right: 10px;
  background-color: transparent;
  border: none;
}

.register-child-button {
  margin-left: 20;
}

.childDeleteButton {
  background-color: transparent;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.button-container {
  display: flex;
  justify-content: center;
}
</style>