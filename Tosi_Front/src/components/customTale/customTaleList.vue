<template>
  <div>
    <h1>친구들의 동화 보기</h1>
    <div class="taleContainer">
        <ul v-for="customTale in customTaleStore.customTalesList"
      :key="customTale.customTaleId">
            <div class="oneTale">
              <router-link :to="`/customtale/${customTale.customTaleId}`" >
      <img
        :src="customTale.thumbnail"
        class="img-fluid"
        style="height: 300px"
        alt="커스텀이미지"
      />
        {{ customTale.title }}
        <!-- {{ customTale.time }} -->
      </router-link>
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
}

.oneTale {
    padding: 3em;
    text-align: center;
    margin: 80px;
}
</style>
