<template>
<div class="talelistContainer">
    <div class = topOfTaleList>
      <h2>친구들의 동화 보기</h2>
    </div>
    <div class="taleContainer">
        <ul v-for="customTale in customTaleStore.customTalesList"
      :key="customTale.customTaleId">
            <div class="oneTale">
                <RouterLink :to="`/customtale/${customTale.customTaleId}`">
                  <img class="thumbnail" :src="customTale.thumbnail" /></RouterLink>
                <br>
                <RouterLink :to="`/customtale/${customTale.customTaleId}`">{{ customTale.title }}</RouterLink>
                <br>
                재생 시간: {{ customTale.time }}
            </div>
        </ul>
    </div>
  </div>
</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, computed, ref } from "vue";
const customTaleStore = useCustomTaleStore();

onMounted(() => {
  customTaleStore.getCustomTalesList(true);
});
</script>

<style>
.taleContainer {
    display: flex; 
    justify-content: center;
    flex-direction: row;
    flex-wrap: wrap;
    align-items: center;
    justify-content: space-around;
}

.talelistContainer{
  background-color:white;
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

.topOfTaleList{
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

a {
  text-decoration: none;
  color: black;
}
</style>
