import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "@/util/http-common";
import router from "@/router";
import { useS3Store } from "@/stores/S3Store";

export const useCookieStore = defineStore("cookie", () => {
  
function getCookieValue(key) {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i].trim();
      if (cookie.startsWith(key + '=')) {
        return cookie.substring(key.length + 1);
      }
    }
    return null; // 해당 키를 찾지 못한 경우
  }
  
  function setCookie(name, value, days) {
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + days); // 유효기간을 days일 후로 설정
    const cookieString = `${name}=${value}; expires=${expirationDate.toUTCString()}; path=/`;
    document.cookie = cookieString;
  }

  return {
    getCookieValue, setCookie,
  }
});