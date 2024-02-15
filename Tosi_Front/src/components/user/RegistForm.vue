<template>
  <div class="regist-form">
    <form action="" class="form" @submit.prevent="onsubmit">
      <h1 class="title">회원가입</h1>
      <div class="info-form">
        <div class="regist-div">
          <label class="regist-label">이메일</label>
          <input type="email" class="regist-input" placeholder=" " v-model="email">
          <button type="button" @click="checkEmailDuplication" class="check-duplication-button">중복 확인</button>
        </div>
        <div class="regist-div">
          <label class="regist-label">비밀번호</label>
          <input type="password" class="regist-input" placeholder=" " v-model="password">
        </div>
        <div class="regist-div">
          <label class="regist-label">비밀번호 확인</label>
          <input type="password" class="regist-input" placeholder=" " v-model="passwordCheck">
        </div>
        <div class="regist-div">
          <label class="regist-label">나의 책장 이름</label>
          <input type="text" class="regist-input" placeholder="나의 책장" v-model="bookshelfName">
        </div>
      </div>
      <div class="child-form">
        <div class="regist-div">
          <div>
            <label class="regist-label">아이 이름</label>
            <input type="text" class="regist-input" placeholder=" " v-model="child.childName">
            <button @click="registerChild" class="register-child-button">등록</button>
          </div>
        </div>
        <div class="regist-div">
          <div>
              <label class="regist-label">아이 성별</label>
              <label><input type="radio" class="regist-input" placeholder=" " v-model="child.gender" value="0">여자</label>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <label><input type="radio" class="regist-input" placeholder=" " v-model="child.gender" value="1">남자</label>
          </div>
        </div>
        <div class="regist-div">
          <div>
            <label class="regist-label">내 아이 여부</label>
            <input type="checkbox" class="regist-input" placeholder=" " v-model="child.myBaby">* 내 아이인 경우 노란색으로 표시됩니다.
          </div>
        </div>

        <div class="children-list-container">
          <ul class="children-ul">
            <li v-for="(child, index) in childrenList" :key="index" class="one-child"
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
        <input type="submit" class="regist-button" value="회원가입" @click="regist">
      </div>

    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/userStore";

const userStore = useUserStore()

const email = ref("");
const password = ref("");
const passwordCheck = ref("");
const bookshelfName = ref("");
const child = ref({ childName: '', gender: 0, myBaby: false });
const childrenList = ref([]);

const registerChild = function () {
  childrenList.value.push({ ...child.value });
  console.log(childrenList.value);
  child.value.childName = "";
}

const regist = () => {
  if (
    email.value === "" ||
    password.value === "" ||
    passwordCheck.value === "" ||
    childrenList.value === null ||
    (Array.isArray(childrenList.value) && childrenList.value.length === 0)
  ) {
    alert("모든 내용을 입력해주세요. \n아이를 1명 이상 등록해 주세요.");
    return;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value.trim())) {
    alert("올바른 이메일 주소를 입력하세요.");
    return;
  }

  userStore.getUserSearch(email.value);
  if (userStore.searchResult == true) {
    alert("이미 등록된 이메일이에요.");
    return;
  }

  if (password.value !== passwordCheck.value) {
    alert("비밀번호와 비밀번호 확인이 일치하지 않아요.");
    return;
  }

  if (bookshelfName.value.trim() === "") {
    bookshelfName.value = "나의 책장";
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
    return;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value.trim())) {
    alert("올바른 이메일 주소를 입력하세요.");
    return;
  }

  try {
    await userStore.getUserSearch(email.value);

    if (userStore.searchResult == true) {
      alert("이미 등록된 이메일이에요.");
    } else if (userStore.searchResult == false) {
      alert("사용 가능한 이메일이에요.");
    }
  } catch (error) {
    console.error("에러 발생:", error);
  }
};

const deleteChild = function (index) {
  childrenList.value.splice(index, 1);
};


</script>

<style scoped>
.title {
    text-decoration: none;
    display: inline-block;
    box-shadow: inset 0 -20px 0  #f1a8bc;
    font-size: 40px;
    margin: 30px 0px 0px 50px;
    margin-bottom: 40px;
    line-height: 1;
    text-align: left;
}
.regist-div {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.regist-label {
  width: 150px;
  margin-right: 10px;
}

.regist-input {
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

form {
  width: fit-content;
}

.info-form,
.child-form {
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 20px;
  margin-bottom: 20px;
}

.children-ul {
  list-style-type: none;
  padding: 0;
  display: flex;
  flex-wrap: wrap;
}

.children-list-container {
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 5px;
  overflow-y: auto;
  width: 500px;
}

.check-duplication-button,
.register-child-button,
.regist-button {
  padding: 7.5px 15px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-right: 5px;
  cursor: pointer;
  box-shadow: 3px 3px 5px 0px #0002;
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  transition: all 0.3s ease;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}

.check-duplication-button:hover,
.register-child-button:hover,
.regist-button:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
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
