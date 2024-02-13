<template>
  <div>
    <div class="buttonContainer">
      <button @click="showFavoriteList" class="favoriteListButton">즐겨찾기 동화 목록</button>
      <button @click="showCustomList" class="customListButton">내가 만든 동화 목록</button>
    </div>
    <div class="talelist-container">
      <FavoriteTaleList v-if="renderingList === 'FavoriteTaleList'" class="taleList"/>
      <FavoriteCustomList v-if="renderingList === 'FavoriteCustomList'" class="taleList"/>
    </div>
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

<style scoped>
.buttonContainer {
  display: flex;
  justify-content: flex-end; /* 왼쪽으로 정렬 */
  margin-right: 220px; /* 왼쪽으로 간격 추가 */
}

button {
  background-color: transparent;
  border: none;
  font-size: 16px;
  cursor: pointer;
  padding: 8px;
  position: relative;
  width: calc(15% - 10px);
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.favoriteListButton {
  background-color: #eee58a;
}

.customListButton {
  background-color: #c4ecb0;
}

button::before {
  content: "";
  position: absolute;
  width: 100%;
  height: 2px;
  background-color: black;
  bottom: -5px;
  left: 0;
  transform: scaleX(0);
  transition: transform 0.3s ease;
}

button:hover::before {
  transform: scaleX(1);
}

.talelist-container {
  padding-left: 40px;
  padding-right: 40px;
  display: flex;
  justify-content: center;
  width: 85vw;
}

.taleList {
  margin-top: 0px;
}

</style>
