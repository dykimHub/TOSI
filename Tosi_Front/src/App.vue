<template>
    <TheHeaderNav />
    <div class="container">
        <TheSideBar />
        <RouterView />
    </div>
        <TheFooter /> 
</template>

<script setup>
import TheHeaderNav from "./components/common/TheHeaderNav.vue";
import TheFooter from "./components/common/TheFooter.vue";
import TheSideBar from "./components/common/TheSideBar.vue";
</script>

<style scoped>

.container {
    display: flex;
    justify-content: space-around;

}
</style>
