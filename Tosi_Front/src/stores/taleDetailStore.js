import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "@/util/http-common";

export const useTaleDetailStore = defineStore("taleDetail", () => {
  const taleId = ref(null);
  const tale = ref(null);

  const getTaleDetail = function () {
    axios
      .get(`/tales/${taleId.value}`, { withCredentials: true })
      .then((response) => {
        tale.value = response.data;
      })
      .catch((error) => {
        console.error("Error fetching:", error);
      });
  };

  const pages = ref(null);

  return { tale, taleId, getTaleDetail, pages };
});
