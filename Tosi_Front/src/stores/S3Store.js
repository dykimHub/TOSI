import { defineStore } from "pinia";
import axios from "@/util/http-common";

export const useS3Store = defineStore("s3", () => {
  const downloadFile = ref({});
  const getDownloadFile = function (fileName) {
    axios.get("/tales/s3/download/${fileName}").then((response) => {
      file.value = response.data;
    });
  };

  const uploadFile = function (file, fileName) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("fileName", fileName);
    axios.post("/tales/s3/upload", formData).then((response) => {
      console.log("Upload successful:", response.data);
    });
  };

  return {
    downloadFile,
    getDownloadFile,
    getFile,
    uploadFile,
  };
});
