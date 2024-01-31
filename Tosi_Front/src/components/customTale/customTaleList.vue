<template>
  <div>
    <h1>Custom Tales</h1>
    <ul>
      <li
        v-for="customTale in customTaleStore.customTalesList"
        :key="customTale.customTaleId"
      >
        ({{ customTale.title }}) {{ customTale.content }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, computed, ref } from "vue";
const customTaleStore = useCustomTaleStore();

onMounted(() => {
  customTaleStore.getCustomTalesList(true);
  console.log(customTaleStore.customTalesList);
});
</script>

<style></style>
