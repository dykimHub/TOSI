<template>
  <div class="talelistContainer">
    <div class="topOfTaleList">
      <!-- <div class="box1"></div> -->
      <!-- <div class="box2"> -->
        <div class="title">검색 결과</div>
      <!-- </div> -->
      <div class="box3">
        <div class="searchContainer">
          <input v-model="searchQuery" type="text" />
          <a @click="searchTaleByTitle">검색</a>
        </div>
      </div>
    </div>
    <div class="taleContainer">
      <!-- <div v-if="searchResults.length > 0"> -->
        <ul v-for="tale in searchResults" :key="tale.taleId">
          <div class="oneTale">
            <RouterLink :to="`/tales/${tale.taleId}`"><img class="thumbnail" :src="tale.thumbnail" /></RouterLink>
            <br />
            <RouterLink :to="`/tales/${tale.taleId}`">{{ tale.title }}</RouterLink>
            <br />
            재생 시간: {{ tale.time }}분
          </div>
        </ul>
      <!-- </div> -->
      <!-- <p v-else>일치하는 검색 결과가 없습니다.</p> -->
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
    // 라우터로 이동
    router.push({ name: "search", query: { title: title.value } });
    
  } catch (error) {
    console.error('검색 오류:', error);
  }
};

onMounted(() => {
  // 현재 라우터 정보 확인
  const route = router.currentRoute.value;
  
  if (route.query.title) {
    title.value = route.query.title.toString();
  }
  Talestore.getTaleList(title.value);
  console.log(title.value)
  console.log("검색 결과: "+searchResults.value)
});
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
}

.thumbnail {
  width: 200px;
}

.oneTale {
  width: 13em;
  text-align: center;
  margin: 3em;
}

.searchContainer {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.topOfTaleList {
  display: flex;
    justify-content: space-between;
    /* margin-left: 10%;
    margin-right: 10%; */
}

a {
  text-decoration: none;
  color: black;
}
</style>