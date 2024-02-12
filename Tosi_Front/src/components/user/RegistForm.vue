<template>
  <div class="regist-form">
    <form action="" class="form" @submit.prevent="onsubmit">
      <h1 class="form__title">회원가입</h1>

      <div class="info-form">
        <div class="regist-div">
          <label class="regist-label">이메일</label>
          <input type="email" class="regist-input" placeholder=" " v-model="email">
          <button type="button" @click="checkEmailDuplication" class="check-duplication-button">중복 확인</button>
        </div>
        <div class="regist-div">
          <label class="regist-label">Password</label>
          <input type="password" class="regist-input" placeholder=" " v-model="password">
        </div>
        <div class="regist-div">
          <label class="regist-label">Password Check</label>
          <input type="password" class="regist-input" placeholder=" " v-model="passwordCheck">
        </div>
        <div class="regist-div">
          <label class="regist-label">나의 책장 이름</label>
          <input type="text" class="regist-input" placeholder=" " v-model="bookshelfName">
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
            <input type="radio" class="regist-input" placeholder=" " v-model="child.gender" value="0">
            여자&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" class="regist-input" placeholder=" " v-model="child.gender" value="1"> 남자
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
            <li v-for="(child, index) in childrenList" :key="index" class="one-child" :class="{ 'my-baby': child.myBaby }">
              <img v-if="child.gender === 0" src="@/assets/female-icon.png" alt="Female Icon" class="icon" />
              <img v-else src="@/assets/male-icon.png" alt="Male Icon" class="icon" />
              {{ child.childName }}
            <button @click="deleteChild(index)" class="childDeleteButton">×</button>
            </li>
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

  // 이메일 유효성 검사
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value.trim())) {
    alert("올바른 이메일 주소를 입력하세요.");
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

// const checkEmailDuplication = async function () {
//   if (email.value.trim() === "") {
//     return; // 이메일이 비어있을 경우 건너뛰기
//   }

//   try {
//     await userStore.getUserSearch(email.value);

//     if (userStore.searchResult == true) {
//       alert("이미 등록된 이메일입니다.");
//     } else if (userStore.searchResult == false) {
//       alert("사용 가능한 이메일입니다.");
//     }
//   } catch (error) {
//     console.error("에러 발생:", error);
//   }
// };

const checkEmailDuplication = async function () {
  if (email.value.trim() === "") {
    return; // 이메일이 비어있을 경우 건너뛰기
  }

  // 이메일 유효성 검사
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value.trim())) {
    alert("올바른 이메일 주소를 입력하세요.");
    return;
  }

  try {
    await userStore.getUserSearch(email.value);

    if (userStore.searchResult == true) {
      alert("이미 등록된 이메일입니다.");
    } else if (userStore.searchResult == false) {
      alert("사용 가능한 이메일입니다.");
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

.regist-div {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.regist-label {
  width: 150px;
  /* 레이블의 너비를 줄임 */
  margin-right: 10px;
}

.regist-input {
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
  border: 1px solid #ccc;
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
  border: 1px solid #ccc;
  padding: 5px;
  overflow-y: auto;
  width: 500px;
  /* background-color: #eee58a; */
}

.check-duplication-button,
.register-child-button,
.regist-button {
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

.check-duplication-button:hover,
.register-child-button:hover,
.regist-button:hover {
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
