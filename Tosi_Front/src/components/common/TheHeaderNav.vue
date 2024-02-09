<template>
    <header>
        <div></div>
        <div>
            <RouterLink to="/tosi">
                <img src="@/assets/logo.png" class="logo" alt="Logo"/>
            </RouterLink>   
        </div>
        <div>
            <div>
                <RouterLink to="/tosi">홈</RouterLink>
                <br>
                <a v-if="isLoggedIn" @click="logout">로그아웃&nbsp;&nbsp;</a>
            </div>
            <div>검색</div>
        </div>
    </header>
</template>

<script setup>
import { useUserStore } from "@/stores/userStore";
import { onMounted, ref } from "vue";
import { useCookieStore } from "@/stores/cookieStore";

const store = useUserStore()
const cookieStore = useCookieStore();

const isLoggedIn = ref('');

onMounted(() => {
    if(sessionStorage.getItem('isLoggedIn') != null || cookieStore.getCookieValue('isLogggedIn') != null) {
        isLoggedIn.value = 'true';
    }
  console.log("Is Authenticated:", isLoggedIn.value);
});

const logout = () => {
  store.getLogout();
};

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

.logo {
    width: 300px;
    margin-top: 25px;
}


a {
  text-decoration: none;
  color: black;
}

</style>
