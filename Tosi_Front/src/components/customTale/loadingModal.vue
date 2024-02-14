<!-- LoadingModal.vue -->
<template>
    <div v-if="isLoading" class="loading-modal-background">
    <!-- <img class="loading-modal" src="@/assets/loading.gif" alt="Loading..."> -->
    <img class="loading-modal" src="@/assets/colorloading2.gif" alt="Loading...">
  </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  
  // const isLoading = ref(false);
const props = defineProps(['isLoading']);
  </script>
  
  <style scoped>
  .loading-modal {
    position: fixed;
    top: 50%; /* 화면 세로 중앙에 위치 */
    left: 55%; /* 화면 가로 중앙에 위치 */
    transform: translate(-50%, -50%);
    /* width: 130px;
    height: 130px;
    border-radius: 50px; */
    width: 250px;
    height: 250px;
    border-radius: 500px;
    background: transparent;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }

  .loading-modal-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.5); /* 흰색 배경에 투명도를 조절할 수 있습니다. */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}
  </style>