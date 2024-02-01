import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from 'axios';

export const useTaleDetailStore = defineStore('taleDetail', () => {
  const taleId = ref(null);
  const tale = ref(null);

  const getTaleDetail = function () {
    console.log(taleId.value);
    axios
      .get(`http://localhost:8080/tales/${taleId.value}`)
      .then((response) => {
        tale.value = response.data;
      })
      .catch((error) => {
        console.error('Error fetching:', error);
      });
  };

  return { tale, taleId, getTaleDetail };
});
