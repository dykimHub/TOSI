import { defineStore } from "pinia";
import { ref, computed } from 'vue'
import axios from "@/util/http-common";

export const useS3Store = defineStore("s3", () => {
  const downloadFile = ref({});
  const getDownloadFile = function (fileName) {
    axios.get(`/tales/s3/download/${fileName}`,fileName)
    .then((response) => {
      file.value = response.data;
    });
  };

  return {
    downloadFile,
    getDownloadFile,
  };
});
