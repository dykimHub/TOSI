<template>
    <div class="nonMemberContainer">
        <img src="@/assets/logo.png" class="logo" alt="Logo" />
        <div class="sign">
            <button @click="[openRegistForm(), closeLoginForm()]" class="regist-button">회원가입</button>
            <button @click="[openLoginForm(), closeRegistForm()]" class="login-button">로그인</button>
        </div>
        <div class="toMenus">
    <img
      class="icon"
      @click="toggleIcon('Play')"
      :class="{ 'active': isActiveIcon('Play') }"
      @mouseover="setActiveIcon('Play')"
      @mouseleave="resetActiveIcon"
      src="@/assets/Play.png"
    />
    <img
      class="icon"
      @click="toggleIcon('Maker')"
      :class="{ 'active': isActiveIcon('Maker') }"
      @mouseover="setActiveIcon('Maker')"
      @mouseleave="resetActiveIcon"
      src="@/assets/Maker.png"
    />
    <img
      class="icon"
      @click="toggleIcon('Talk')"
      :class="{ 'active': isActiveIcon('Talk') }"
      @mouseover="setActiveIcon('Talk')"
      @mouseleave="resetActiveIcon"
      src="@/assets/Talk.png"
    />
    <img
      class="icon"
      @click="toggleIcon('Mine')"
      :class="{ 'active': isActiveIcon('Mine') }"
      @mouseover="setActiveIcon('Mine')"
      @mouseleave="resetActiveIcon"
      src="@/assets/Mine.png"
    />
  </div>
        <h5 v-if="!activeIcon">토씨의 특별한 기능에 대해서 알아보려면 누르세요</h5>
        <h5 v-if="activeIcon === 'Play'">동화 재생하기</h5>
        <h5 v-if="activeIcon === 'Maker'">동화 만들기</h5>
        <h5 v-if="activeIcon === 'Talk'">등장인물과의 대화</h5>
        <h5 v-if="activeIcon === 'Mine'">나의 책장</h5>
        <h5 v-if="havePlayed">이제, 회원가입 후 특별한 경험을 시작해보세요</h5>

        <TheFooter />
        <div class="black-bg" v-if="showRegistForm == true || showLoginForm == true">
            <div class="modal" v-if="showRegistForm === true">
                <button @click="closeRegistForm">×</button>
                <RegistForm @closeRegistForm="closeRegistForm" />
            </div>
            <div class="modal" v-if="showLoginForm === true">
                <button @click="closeLoginForm">×</button>
                <LoginForm @closeLoginForm="closeLoginForm" />
            </div>
        </div>
    </div>
</template>

<script setup>
import TheFooter from '@/components/common/TheFooter.vue';
import { ref } from 'vue';
import RegistForm from "@/components/user/RegistForm.vue";
import LoginForm from "@/components/user/LoginForm.vue";
const showRegistForm = ref(false);
const openRegistForm = () => {
    showRegistForm.value = true;
};
const closeRegistForm = () => {
    showRegistForm.value = false;
};
const showLoginForm = ref(false);
const openLoginForm = () => {
    showLoginForm.value = true;
};
const closeLoginForm = () => {
    showLoginForm.value = false;
};

const isActiveIcon = (icon) => {
  return activeIcon.value === icon;
};

const setActiveIcon = (icon) => {
  activeIcon.value = icon;
};

const resetActiveIcon = () => {
  activeIcon.value = '';
};
const havePlayed = ref(false);

const isPlaying = ref(false);
const activeIcon = ref('');
const toggleIcon = async (icon) => {
    if (isPlaying.value) return;
    isPlaying.value = true;
    activeIcon.value = icon;
    const audio = new Audio(`https://talebucket.s3.ap-northeast-2.amazonaws.com/nminyoung-${icon}.mp3`);
    audio.addEventListener('ended', () => {
        isPlaying.value = false;
        activeIcon.value = '';
    })
    await audio.play();
    havePlayed.value = true;
}
</script>

<style scoped>
body {
    margin: 0;
}

div {
    box-sizing: border-box;
}


.black-bg {
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    position: fixed;
    padding: 20px;
}

.modal {
    display: flex;
    flex-direction: column;
    align-items: center;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 40px;
    border-radius: 8px;
    width: 600px;
    height: min-content;
    max-height: 98%;
    z-index: 100;
    overflow-y: auto;
    -ms-overflow-style: none;
}

.modal::-webkit-scrollbar{
  display:none;
}

.modal button {
    background-color: transparent;
    border: none;
    font-size: 20px;
    cursor: pointer;
    position: absolute;
    top: 10px;
    right: 10px;
    color: rgb(5, 5, 5);
}

.logo {
    margin-top: 60px;
    width: 400px;
}

.icon {
    width: 120px;
    margin: 50px;
    filter: drop-shadow(10px 6px 6px #cccccc);
}

.icon:hover {
    cursor: pointer;
}

.icon.active {
    filter: blur(5px);
    opacity: 0.7;
}

.sign {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 50px;
}

.nonMemberContainer {
    display: flex;
    flex-direction: column;
    align-items: center;
}

button {
    margin: 10px 10px 0px 10px;
}

h5 {
    margin-bottom: 50px;
    text-shadow: -1px -1px 2px white, 1px -1px 2px white, -1px 1px 2px white, 1px 1px 2px white;
}

.regist-button,
.login-button {
    background-color: #ffffff;
    border: 2px solid #d0d0d0;
    border-radius: 8px;
    color: rgb(25, 30, 50);
    padding: 7.5px 15px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin-right: 5px;
    cursor: pointer;
    box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
    transition: transform 0.3s, background-color 0.3s, box-shadow 0.3s;
}

.regist-button:hover,
.login-button:hover {
    transform: translateY(-3px);
}</style>