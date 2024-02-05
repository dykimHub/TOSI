<template>
    <TheHeaderNav />
    <div class="sideBar">
        <TheSideBar />
    </div>
    <div class="content">
        <RouterView />
    </div>
    <TheFooter/>
</template>

<script setup>
import TheHeaderNav from "@/components/common/TheHeaderNav.vue";
import TheSideBar from "@/components/common/TheSideBar.vue";
import TheFooter from '@/components/common/TheFooter.vue';
</script>

<style scoped>

.sideBar {
    display: flex;
    width: 15em;
}

.content {
    margin-left: 13em;
}

a {
    text-decoration: none;
    color: black;
}

</style>
