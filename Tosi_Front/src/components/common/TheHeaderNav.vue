<template>
<<<<<<< HEAD
    <header>
        <div class="headerContainer">
            <div class="empty"></div>
            <div class="logo-container">
                <RouterLink to="/tosi">
                    <img src="@/assets/logo.png" class="logo" alt="Logo" />
                </RouterLink>
            </div>
            <div class="homeMenus">
                <RouterLink to="/tosi">홈</RouterLink>
                <a v-if="isLoggedIn" @click="logout">로그아웃</a>
            </div>
        </div>
    </header>
=======
  <header>
    <div class="headerContainer">
      <div class="empty"></div>
      <div class="logo-container">
        <RouterLink to="/tosi">
          <img src="@/assets/logo.png" class="logo" alt="Logo" />
        </RouterLink>
      </div>
      <div class="homeMenus">
        <RouterLink to="/tosi">홈</RouterLink>
        <button v-if="isLoggedIn" @click="logout" class="logoutButton">로그아웃</button>
      </div>
    </div>
  </header>
>>>>>>> 2dbaaa2cf80aaeb187b40dafb0e9623980f53e94
</template>

<script setup>
import { useUserStore } from "@/stores/userStore";
import { onMounted, ref } from "vue";

const store = useUserStore();

const isLoggedIn = ref("");

onMounted(() => {
    if (sessionStorage.getItem("isLoggedIn") != null || localStorage.getItem("isLoggedIn") != null) {
        isLoggedIn.value = "true";
    }
    console.log("Is Authenticated:", isLoggedIn.value);
});

const logout = () => {
    store.getLogout();
};
</script>

<style scoped>
.headerContainer {
    display: flex;
    margin-top: 25px;
    justify-content: center;
    /* align-items: center; */
}
.logo {
    flex-grow: 1;
    width: 300px;
    justify-self: center;
    margin-left: auto;
    margin-right: auto;
}
a {
    text-decoration: none;
    color: black;
}
.homeMenus {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.empty,
.homeMenus,
.headerContainer {
    flex: 1;
}

.logoutButton {
  background-color: transparent;
  border: none;
}
</style>
