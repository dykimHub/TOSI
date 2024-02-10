<template>
    <div class="nonMemberContainer">
        <img src="@/assets/logo.png" class="logo" alt="Logo"/>

            <div class="sign">
                <button @click="openRegistForm">회원가입</button>
                <button @click="openLoginForm">로그인</button>
            </div>
            <div class="toMenus">
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
            <TheFooter/>
            <div v-if="showRegistForm === true" >
                <button @click="closeRegistForm">닫기</button>
                <RegistForm @closeRegistForm="closeRegistForm"/>
            </div>
            <div v-if="showLoginForm === true" >
                <button @click="closeLoginForm">닫기</button>
                <LoginForm @closeLoginForm="closeLoginForm"/>
            </div>
    </div>

</template>
<script setup>
import TheFooter from '@/components/common/TheFooter.vue';
import {ref} from 'vue';
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
const toggleIcon = async(icon) => {
    if(isPlaying.value) return;
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
</style>