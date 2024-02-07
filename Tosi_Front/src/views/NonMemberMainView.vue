<template class="nonMemberContainer">
    <img src="@/assets/logo.png" class="logo" alt="Logo"/>
    <div>
        <div>
            <div class="sign">
                <RouterLink to="/regist">회원가입</RouterLink>
                <RouterLink to="/login">로그인</RouterLink>
            </div>
            <div class="toMenus">                   
                <img class="icon" @click="toggleIcon('Play')" :class="{ 'active': activeIcon === 'Play' }" src="@/assets/talelist.png" />
                <img class="icon" @click="toggleIcon('Maker')" :class="{ 'active': activeIcon === 'Maker' }" src="@/assets/custom.png" />
                <img class="icon" @click="toggleIcon('Talk')" :class="{ 'active': activeIcon === 'Talk' }" src="@/assets/conversation.png" />
                <img class="icon" @click="toggleIcon('Mine')" :class="{ 'active': activeIcon === 'Mine' }" src="@/assets/favorite.png" />
                <p>토씨의 특별한 기능에 대해서 알아보려면 누르세요</p>
            </div>
            <TheFooter/>
        </div>
    </div>
</template>

<script setup>
import TheFooter from '@/components/common/TheFooter.vue';
import {ref} from 'vue';

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

p {
    text-shadow: -1px -1px 2px white, 1px -1px 2px white, -1px 1px 2px white, 1px 1px 2px white;
}
</style>