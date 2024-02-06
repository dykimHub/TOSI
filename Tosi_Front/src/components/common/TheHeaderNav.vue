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
<<<<<<< HEAD
                <a href="#" class="my-link" v-if="isLoggedIn" @click="logout">로그아웃&nbsp;&nbsp;</a>
=======
                <a>로그아웃</a>
>>>>>>> 4d40147d770f53e2189b668c4be10f034a658ea0
            </div>
            <div>검색</div>
        </div>
    </header>
</template>

<script setup>
<<<<<<< HEAD
import { useUserStore } from "@/stores/userStore";
import { onMounted, ref } from "vue";

const store = useUserStore()

const isLoggedIn = ref('');

onMounted(() => {
    if(localStorage.getItem('isLoggedIn') != null) {
        isLoggedIn.value = 'true';
    }
  console.log("Is Authenticated:", isLoggedIn.value);
});

const logout = () => {
  store.getLogout();
};

=======

>>>>>>> 4d40147d770f53e2189b668c4be10f034a658ea0
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
    width: 200px;
    margin-top: 40px;
}


a {
  text-decoration: none;
  color: black;
}

<<<<<<< HEAD
</style>
=======
</style>
>>>>>>> 4d40147d770f53e2189b668c4be10f034a658ea0
