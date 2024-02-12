<template>
    <div class="nonMemberContainer">
        <div class="black-bg" v-if="showRegistForm == true || showLoginForm == true">
            <!-- <div class="white-bg"> -->
            <div class="modal" v-if="showRegistForm === true">
                <button @click="closeRegistForm">×</button>
                <RegistForm @closeRegistForm="closeRegistForm" />
            </div>
            <div class="modal" v-if="showLoginForm === true">
                <button @click="closeLoginForm">×</button>
                <LoginForm @closeLoginForm="closeLoginForm" />
            </div>
            <!-- </div> -->
        </div>
        <img src="@/assets/logo.png" class="logo" alt="Logo" />
        <div class="sign">
            <button @click="[openRegistForm(), closeLoginForm()]" class="regist-button">회원가입</button>
            <button @click="[openLoginForm(), closeRegistForm()]" class="login-button">로그인</button>
        </div>
        <div v-if="showRegistForm == false && showLoginForm == false" class="toMenus">
            <img class="icon" @click="toggleIcon('Play')" :class="{ 'active': activeIcon === 'Play' }"
                src="@/assets/Play.png" />
            <img class="icon" @click="toggleIcon('Maker')" :class="{ 'active': activeIcon === 'Maker' }"
                src="@/assets/Maker.png" />
            <img class="icon" @click="toggleIcon('Talk')" :class="{ 'active': activeIcon === 'Talk' }"
                src="@/assets/Talk.png" />
            <img class="icon" @click="toggleIcon('Mine')" :class="{ 'active': activeIcon === 'Mine' }"
                src="@/assets/Mine.png" />
        </div>
        <h5 v-if="!isPlaying">토씨의 특별한 기능에 대해서 알아보려면 누르세요</h5>
        <h5 v-if="activeIcon === 'Play'">동화 재생하기</h5>
        <h5 v-if="activeIcon === 'Maker'">동화 만들기</h5>
        <h5 v-if="activeIcon === 'Talk'">등장인물과의 대화</h5>
        <h5 v-if="activeIcon === 'Mine'">나의 책장</h5>
        <TheFooter />
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
}
</script>

<style scoped>
/* RegistForm {
    overflow: auto;
} */

body {
    margin: 0;
}

div {
    box-sizing: border-box;
}

/* 
.black-bg {
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0);
    position: fixed;
    padding: 20px;
} */
/* 
.white-bg {
    width: 100%;
    height: 100;
    background: white;
    border-radius: 8px;
    padding: 20px;
} */

.modal {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    /* position: fixed; */
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 40px;
    border-radius: 8px;
    width: 600px;
    height: min-content;
    /* max-width: 90%; 최대 너비 설정 */
    max-height: 90%;
    /* overflow-y: auto; */
}

/* RegistForm {
    overflow: auto;
}

LoginForm {
    height: min-content;
} */

.modal button {
    background-color: transparent;
    /* 투명 */
    border: none;
    /* 테두리 없음 */
    font-size: 20px;
    /* 텍스트 크기 설정 */
    cursor: pointer;
    /* 커서를 포인터로 변경하여 클릭 가능한 상태로 표시 */
    position: absolute;
    /* 버튼을 모달 안에서 절대 위치로 설정 */
    top: 10px;
    /* 위쪽 여백 설정 */
    right: 10px;
    /* 오른쪽 여백 설정 */
    color: rgb(5, 5, 5);
    /* 텍스트 색상 설정 */
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
    border: none;
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
    transform: translateY(-3px); /* 마우스를 올렸을 때 아래로 이동하는 효과 */
}</style>