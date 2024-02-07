<template>
  <div>
    <button @click="showFavoriteList">즐겨찾기 동화 목록</button>
    <button @click="showCustomList">내가 만든 동화 목록</button>
    <FavoriteCustomList v-if="renderingList === 'FavoriteCustomList'" />
    <FavoriteTaleList v-if="renderingList === 'FavoriteTaleList'" />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import FavoriteCustomList from '@/components/bookshelf/favoriteCustomList.vue';
import FavoriteTaleList from '@/components/bookshelf/favoriteTaleList.vue';

const renderingList = ref('FavoriteTaleList');

const showCustomList = () => {
  renderingList.value = 'FavoriteCustomList';
};

const showFavoriteList = () => {
  renderingList.value = 'FavoriteTaleList';
};

</script>

<style scoped></style>
