<template>
    <header>
        <RouterLink to="/tosi">
            <img src="@/assets/logo.png" alt="Logo"/>
        </RouterLink>   
        <div class="container">
            <ul>
                <RouterLink to="/tosi">홈</RouterLink>
                <!--TODO 로그아웃 버튼 추가-->
                <div>검색</div>





            <div class="log-reg">
                <a href="#" class="my-link" v-if="getUser" @click="logout">로그아웃&nbsp;&nbsp;</a>
                <RouterLink class="login-link" :class="{ click: isClick[1].value }" :to="{ name: 'login' }" v-else
                    @click="togggle(1)">
                    로그인</RouterLink>
                <RouterLink class="regist-link" :class="{ click: isClick[2].value }" :to="{ name: 'regist' }"
                    v-if="!getUser" @click="togggle(2)">회원가입</RouterLink>
                <RouterLink class="my-link" :class="{ click: isClick[3].value }" :to="{ name: 'setting' }" v-if="getUser"
                    @click="togggle(3)">
                    설정</RouterLink>
            </div>


            </ul>
        </div>
    </header>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from "@/stores/userStore";
import { onMounted, computed, ref, onBeforeUnmount } from "vue";
import axios from 'axios'

onMounted(() => {
})

const store = useUserStore()

const isClick = ref([ref(false), ref(false), ref(false), ref(false), ref(false)])

const togggle = function (a) {
    console.log(isClick.value[a].value)
    for (let i = 0; i < 4; i++) {
        if (i == a) {
            isClick.value[i].value = true
        } else {
            isClick.value[i].value = false
        }
    }
}

</script>

<style scoped>
header {
    display: flex; 
    flex-direction: row;
    align-items: center;
    justify-content: space-around;
}

img {
    width: 200px;
}

</style>
