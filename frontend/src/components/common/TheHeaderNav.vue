<template>
  <header>
    <div class="headerContainer">
      <div class="empty"></div>
      <div class="logo-container">
        <RouterLink to="/tosi">
          <img src="@/assets/logo.png" class="logo" alt="Logo" />
        </RouterLink>
      </div>
      <div class="homeMenus">
        <div class="home">
          <RouterLink to="/tosi"
            ><img src="@/assets/home.png" class="homeimg" /> &nbsp | &nbsp
          </RouterLink>
        </div>
        <button v-if="isLoggedIn" @click="logout" class="logoutButton">로그아웃</button>
      </div>
    </div>
  </header>
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
.home {
  display: flex;
  justify-content: column;
  align-items: center;
}
.homeimg {
  width: 20px;
  height: 20px;
  cursor: pointer;
}
.homeMenus {
  display: flex;
  justify-content: right;
  margin-right: 40px;
  /* flex-direction: column;
  align-items: center; */
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
