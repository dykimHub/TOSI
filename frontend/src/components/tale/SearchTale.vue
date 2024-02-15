<template>
  <div class="talelistContainer">
    <div class="topOfTaleList">
        <div class="title">검색 결과</div>
      <div class="box3">
        <div class="searchContainer">
          <input v-model="searchQuery" type="text" @keyup.enter="searchTaleByTitle"/>
          <button @click="searchTaleByTitle">검색</button>
        </div>
      </div>
    </div>
    <div class="taleContainer">
        <ul v-for="tale in searchResults" :key="tale.taleId">
          <div class="oneTale">
          <RouterLink :to="`/tales/${tale.taleId}`"
            ><img class="thumbnail" :src="tale.images[1]"
          /></RouterLink>
          <br />
          <RouterLink :to="`/tales/${tale.taleId}`">{{ tale.title }}</RouterLink>
          <br />
          재생 시간: {{ tale.time }}분
        </div>
        </ul>
    </div>
  </div>
</template>

<script setup>
import { useTaleStore } from "@/stores/taleStore";
import { onMounted, ref, watch, computed } from "vue";
import { useRouter, useRoute } from "vue-router";

const route = useRoute();
const Talestore = useTaleStore();;
const router = useRouter();
const searchResults = ref([]);
const searchQuery = ref("");
const title = ref("");

const searchTaleByTitle = async () => {
  try {
    title.value = searchQuery.value;

    const response = await Talestore.searchTaleByTitle(title.value);
    console.log('Response:', response);
    searchResults.value = response.data;
    router.push({ name: "search", query: { title: title.value } });
    
  } catch (error) {
    console.error('검색 오류:', error);
  }
};

onMounted( async() => {
  const route = router.currentRoute.value;
  
  if (route.query.title) {
    title.value = route.query.title.toString();
    let value = await Talestore.searchTaleByTitle(title.value);
    searchResults.value = value.data;
    // console.log("TEST", JSON.stringify(searchResults.value));
    console.log(searchResults.value.thumbnail);
  }
});
</script>

<style scoped>
.taleContainer {
  display: flex;
  justify-content: center;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: flex-start;
  margin-left: 5vw;
}

.title {
    text-decoration: none;
    display: inline-block;
    box-shadow: inset 0 -20px 0 #f1a8bc;
    font-size: 40px;
    margin: 30px 0px 0px 50px;
    margin-bottom: 40px;
    line-height: 1;
    text-align: left;
}

.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  opacity: 0.95;
  padding: 40px 60px;
  border: 5px solid #cee8e8;
  width: 80vw;
}

.thumbnail {
  width: 200px;
}

.oneTale {
  width: 13em;
  text-align: center;
  margin: 3em;
}

.selecSort {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 0.5em;
}
.searchContainer {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.topOfTaleList {
  display: flex;
  justify-content: space-between;
}
a {
  text-decoration: none;
  color: black;
}

select {
  margin-left: 1em;
}

input {
  width: 7em;
  height: 2em;
}

button {
  width: 3em;
  margin-left: 1em;
  height: 2em;
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}

button:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
}


</style>