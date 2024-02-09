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
  justify-content: center; /* 버튼을 가로 상으로 중앙 정렬 */
}

button {
  background-color: transparent;
  border: none;
  font-size: 16px;
  cursor: pointer;
  padding: 8px;
  position: relative;
  width: calc(15% - 10px); /* taleList의 가로길이의 절반 */
  border-top-left-radius: 10px; /* 왼쪽 상단 모서리 둥글게 처리 */
  border-top-right-radius: 10px; /* 오른쪽 상단 모서리 둥글게 처리 */
}

.favoriteListButton {
  background-color: #c4ecb0; /* 즐겨찾기 동화 목록 버튼 배경색 */
}

.customListButton {
  background-color: #d6b0ec; /* 내가 만든 동화 목록 버튼 배경색 */
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
  justify-content: center; /* taleListButtonContainer 내부 컨텐츠를 가로 상으로 중앙 정렬 */
  width: calc(100% - 70px);
}

.taleList {
  margin-top: 0px;
}
</style>
