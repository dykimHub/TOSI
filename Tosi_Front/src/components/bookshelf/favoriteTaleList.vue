<template>
  <ul v-for="favorite in favorites" :key="favorite.id">
    <div class="oneTale">
      <RouterLink :to="`/tales/${favorite.taleId}`"
        ><img class="thumbnail" :src="favorite.thumbnail"
      /></RouterLink>
      <br />
      <RouterLink :to="`/tales/${favorite.taleId}`">{{ favorite.title }}</RouterLink>
      <br />
      재생 시간: {{ favorite.time }}
    </div>
    <button @click="deleteFavorite(favorite.taleId)">삭제</button>
  </ul>
</template>

<script setup>
import { onMounted, ref, watch, computed } from "vue";
import { useUserStore } from "@/stores/userStore";
import axios from "axios";

const userStore = useUserStore();
userStore.getUser();

const favoriteList = ref([]);
const favorites = computed(() => favoriteList.value);

watch(
  () => userStore.userInfo,
  (userInfo) => {
    if (userInfo && userInfo.userId) {
      axios
        .get(`http://localhost:8080/favorites/${userInfo.userId}`)
        .then((res) => (favoriteList.value = res.data))
        .catch((err) => console.error(err));
    }
  },
  { immediate: true }
);

const deleteFavorite = (taleId) => {
  axios
    .get(`http://localhost:8080/favorites/${userStore.userInfo.userId}/${taleId}`)
    .then((res) => {
      const favoriteId = res.data;
      return axios.delete(`http://localhost:8080/favorites/${favoriteId}`);
    })
    .then(() => {
      favoriteList.value = favoriteList.value.filter((favorite) => favorite.taleId !== taleId);
    })
    .catch((err) => console.error(err));
};

// console.log(favoriteList.value);

// import { useFavoriteStore } from '@/stores/favoriteStore';
// import { onMounted, ref, watch } from 'vue';

// const favoriteStore = useFavoriteStore();

// const sortOptionC = ref('title');
// const sortedTaleList = ref([]);

// const sortTaleList = () => {
//   switch (sortOptionC.value) {
//     case 'title':
//       sortedTaleList.value = favoriteStore.myFavoriteTalesList.sort((a, b) => a.taleDto.title.localeCompare(b.taleDto.title));
//       break;
//     case 'likeCnt':
//       sortedTaleList.value = favoriteStore.myFavoriteTalesList.sort((a, b) => b.taleDto.likeCnt - a.taleDto.likeCnt);
//       break;
//     case 'random':
//       sortedTaleList.value = favoriteStore.myFavoriteTalesList.sort(() => Math.random() - 0.5);
//       break;
//     default:
//       break;
//   }
// };

// const deleteTale = function (favoriteId) {
//   favoriteStore.deleteMyFavoriteTale(favoriteId);
//   // sortedTaleList.value.splice(taleId, taleId);
//   // sortedTaleList.value = sortedTaleList.value.filter(tale => favorite.favoriteId !== taleId);
//   // window.location.reload()
// }

// onMounted(() => {
//   favoriteStore.getMyFavoriteTalesList();
//   // console.log(tale.title.value);
//   watch(() => favoriteStore.myFavoriteTalesList, sortTaleList, { immediate: true });
//   watch(sortOptionC, sortTaleList);
// });
</script>

<style scoped>
.taleContainer {
  display: flex;
  justify-content: center;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
}

.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  padding-top: 40px;
  opacity: 0.95;
}

.thumbnail {
  width: 200px;
}

.oneTale {
  width: 13em;
  text-align: center;
  margin: 2em;
}

.selecSort {
  display: flex;
  flex-direction: column;
  margin: 1em;
}

.topOfTaleList {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

a {
  text-decoration: none;
  color: black;
}
</style>
