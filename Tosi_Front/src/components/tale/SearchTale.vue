<template>
  <div class="talelistContainer">
    <div class="topOfTaleList">
      <h2>검색 결과</h2>
    </div>
    <div class="taleContainer">
      <ul v-for="tale in Talestore.taleList" :key="tale.taleId">
        <div class="oneTale">
          <RouterLink :to="`/tales/${tale.taleId}`"
            ><img class="thumbnail" :src="tale.thumbnail"
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
import { onMounted, ref, watch } from "vue";
const Talestore = useTaleStore();
const sortOption = ref("title");
const sortedTaleList = ref([]);

const sortTaleList = () => {
  switch (sortOption.value) {
    case "title":
      sortedTaleList.value = Talestore.taleList.sort((a, b) => a.title.localeCompare(b.title));
  }
};

onMounted(() => {
  Talestore.getTaleList();
  watch(() => Talestore.taleList, sortTaleList, { immediate: true });
  watch(sortOption, sortTaleList);
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