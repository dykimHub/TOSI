import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from "@/util/http-common";

export const useTaleStore = defineStore('tale', () => {
  //동화 목록
  const taleList = ref([])
  const getTaleList = async function () {
    await axios.get(`/tales`, { withCredentials: true })
      .then((response) => {
        // console.log(response.data)
        taleList.value = response.data
      })
      .catch((error) => {
        console.error('Error fetching tales:', error)
      })
  }

  //동화 검색
  const searchList = ref([])
const searchTaleByTitle = function (title) {
  return new Promise((resolve, reject) => {
    axios.get(`/search?title=${title}`, { withCredentials: true })
      .then((response) => {
        console.log('API 호출 성공:', response);
        searchList.value = response.data;
        resolve(response); // Promise를 성공 상태로 처리하고 응답 데이터 전달
      })
      .catch((error) => {
        window.alert('일치하는 검색 결과가 없습니다.')
        console.error('API 호출 실패:', error);
        reject(error); // Promise를 실패 상태로 처리하고 에러 전달
      });
  });
};


  return {searchList, taleList, getTaleList, searchTaleByTitle }
})