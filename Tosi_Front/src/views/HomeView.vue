<template>
    <TheHeaderNav />
    <div class="sideBar">
        <TheSideBar />
    </div>
    <div class="content">
        <RouterView />
    </div>
</template>

<script setup>
import TheHeaderNav from "/src/components/common/TheHeaderNav.vue";
import TheSideBar from "/src/components/common/TheSideBar.vue";
</script>

<style scoped>

.sideBar {
    display: flex;
    width: 15em;
}

.content {
    margin-left: 15em;
}

</style>
